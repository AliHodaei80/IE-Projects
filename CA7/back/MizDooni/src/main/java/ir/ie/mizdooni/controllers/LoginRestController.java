package ir.ie.mizdooni.controllers;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.LongCounter;
import ir.ie.mizdooni.commons.JwtResponse;
import ir.ie.mizdooni.commons.Response;
import ir.ie.mizdooni.configs.JwtUtil;
import ir.ie.mizdooni.models.User;
import ir.ie.mizdooni.services.UserHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static ir.ie.mizdooni.commons.PasswordHasher.hashAndEncodePassword;
import static ir.ie.mizdooni.definitions.RequestKeys.*;
import static ir.ie.mizdooni.definitions.Successes.*;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validateAddUser;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validateLoginUser;

@RestController
public class LoginRestController {

    private final UserHandler userHandler;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private LongCounter loginCounter;
    private LongCounter signUpCounter;

    private final Logger logger;

    @Autowired
    public LoginRestController(UserHandler userHandler, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userHandler = userHandler;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        logger = LoggerFactory.getLogger(LoginRestController.class);
        this.loginCounter = GlobalOpenTelemetry
                .getMeter("LoginSignupMetric")
                .counterBuilder("login_counter")
                .setDescription("Total number of logins")
                .build();
        this.signUpCounter = GlobalOpenTelemetry
                .getMeter("LoginSignupMetric")
                .counterBuilder("signup_counter")
                .setDescription("Total number of signups")
                .build();
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Response> signUpHandler(@RequestBody Map<String, Object> data) {
        try {
            validateAddUser(data);
            userHandler.addUser((String) data.get(USERNAME_KEY),
                    (String) data.get(EMAIL_KEY),
                    (String) data.get(USER_ROLE_KEY),
                    (String) data.get(PASSWORD_KEY),
                    (Map<String, String>) data.get(USER_ADDRESS_KEY));
            logger.info("User added successfully");
            signUpCounter.add(1);
            return new ResponseEntity<>(new Response(true, USER_ADDED_SUCCESSFULLY), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("User addition failed. error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> loginHandler(@RequestBody Map<String, Object> data) {
        try {
            validateLoginUser(data);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(data.get(USERNAME_KEY), hashAndEncodePassword((String) data.get(PASSWORD_KEY)))
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userHandler.loadUserByUsername((String) data.get(USERNAME_KEY));
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            logger.info("User `" + data.get(USERNAME_KEY) + "` login successfully");
            loginCounter.add(1);
            return ResponseEntity.ok(new JwtResponse(jwt, "Bearer", jwtUtil.extractExpiration(jwt).toInstant().toEpochMilli(), userDetails));
        } catch (AuthenticationException e) {
            logger.error("Login failed for User `" + data.get(USERNAME_KEY) + "`. error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, "Invalid username or password"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Login failed for User `" + data.get(USERNAME_KEY) + "`. error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Response> logoutHandler() {
//        userHandler.logoutUser();
        logger.info("User logout successfully");
        return new ResponseEntity<>(new Response(true, USER_LOGOUT_SUCCESSFULLY), HttpStatus.OK);
    }


}
