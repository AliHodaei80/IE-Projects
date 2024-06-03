package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.commons.JwtResponse;
import ir.ie.mizdooni.commons.Response;
import ir.ie.mizdooni.configs.JwtUtil;
import ir.ie.mizdooni.exceptions.CantSigninByGoogle;
import ir.ie.mizdooni.exceptions.EmailAlreadyTaken;
import ir.ie.mizdooni.exceptions.InvalidUserRole;
import ir.ie.mizdooni.exceptions.UserNameAlreadyTaken;
import ir.ie.mizdooni.services.UserHandler;
import ir.ie.mizdooni.utils.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static ir.ie.mizdooni.commons.HttpRequestSender.sendGetRequest;
import static ir.ie.mizdooni.commons.HttpRequestSender.sendPostRequest;
import static ir.ie.mizdooni.commons.PasswordHasher.hashAndEncodePassword;
import static ir.ie.mizdooni.definitions.RequestKeys.PASSWORD_KEY;
import static ir.ie.mizdooni.definitions.RequestKeys.USERNAME_KEY;

@RestController
public class GoogleAuthController {
    private final String clinet_id;
    private final String client_secret;
    private final String redirect_uri;
    private final String default_password;
    private final AuthenticationManager authenticationManager;
    private final UserHandler userHandler;
    private final JwtUtil jwtUtil;
    private final Logger logger;

    @Autowired
    public GoogleAuthController(@Value("${google.client_id}") String client_id,
                                @Value("${google.client_secret}") String client_secret,
                                @Value("${google.redirect_uri}") String redirect_uri,
                                @Value("${google.default_password}") String default_password,
                                AuthenticationManager authenticationManager,
                                UserHandler userHandler,
                                JwtUtil jwtUtil) {
        this.clinet_id = client_id;
        this.client_secret = client_secret;
        this.redirect_uri = redirect_uri;
        this.default_password = default_password;
        this.authenticationManager = authenticationManager;
        this.userHandler = userHandler;
        this.jwtUtil = jwtUtil;
        this.logger = LoggerFactory.getLogger(GoogleAuthController.class);
        System.out.println(client_id);
        System.out.println(client_secret);
        System.out.println(redirect_uri);
    }

    @RequestMapping(value = "/Callback", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> googleOauthHandler(@RequestParam String code) {
        try {
            System.out.println(code);
            String grant_type = "authorization_code";
            String getAccessTokenResponseString = sendPostRequest("https://oauth2.googleapis.com/token",
                    Map.of("Accept", "application/json", "Content-Type", "application/x-www-form-urlencoded"),
                    new HashMap<>(),
                    Map.of("code", code, "client_id", clinet_id, "client_secret", client_secret, "redirect_uri", redirect_uri, "grant_type", grant_type)
            );
            Map<String, Object> getAccessTokenResponse = Parser.parseStringToJsonObject(getAccessTokenResponseString);
            String accessToken = (String) getAccessTokenResponse.get("access_token");

            String getUserInfoResponseString = sendGetRequest("https://www.googleapis.com/oauth2/v2/userinfo",
                    Map.of("Authorization", "Bearer " + accessToken, "Accept", "application/json"),
                    new HashMap<>()
            );

            Map<String, Object> getUserInfoResponse = Parser.parseStringToJsonObject(getUserInfoResponseString);
            System.out.println(getUserInfoResponse);
            System.out.println("Email: " + getUserInfoResponse.get("email"));
            System.out.println("Name: " + getUserInfoResponse.get("name"));

            String modifiedUsername = ((String) getUserInfoResponse.get("name")).replace(" ", "-");
            userHandler.updateOrAddUser(modifiedUsername, (String) getUserInfoResponse.get("email"), "client", default_password, new HashMap<>());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(modifiedUsername, hashAndEncodePassword(default_password))
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userHandler.loadUserByUsername((String) modifiedUsername);
            String jwt = jwtUtil.generateToken(userDetails.getUsername());

            logger.info("User `" + modifiedUsername + "` login by Google successfully");
            JwtResponse jwtResponse = new JwtResponse(jwt, "Bearer", jwtUtil.extractExpiration(jwt).toInstant().toEpochMilli(), userDetails);
            return ResponseEntity.ok(jwtResponse);

        } catch (UserNameAlreadyTaken | EmailAlreadyTaken | InvalidUserRole | CantSigninByGoogle e) {
            logger.error("User addition by google failed. error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("User addition by google failed. error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
