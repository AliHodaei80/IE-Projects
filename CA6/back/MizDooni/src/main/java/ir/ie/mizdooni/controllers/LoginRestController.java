package ir.ie.mizdooni.controllers;

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

import static ir.ie.mizdooni.definitions.RequestKeys.*;
import static ir.ie.mizdooni.definitions.Successes.*;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validateAddUser;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validateLoginUser;

@RestController
public class LoginRestController {

    private final UserHandler userHandler;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final Logger logger;

    @Autowired
    public LoginRestController(UserHandler userHandler, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userHandler = userHandler;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        logger = LoggerFactory.getLogger(LoginRestController.class);
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
                    new UsernamePasswordAuthenticationToken(data.get(USERNAME_KEY), data.get(PASSWORD_KEY))
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userHandler.loadUserByUsername((String) data.get(USERNAME_KEY));
            String jwt = jwtUtil.generateToken(userDetails.getUsername());

            logger.info("User `" + data.get(USERNAME_KEY) + "` login successfully");
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
        userHandler.logoutUser();
        logger.info("User logout successfully");
        return new ResponseEntity<>(new Response(true, USER_LOGOUT_SUCCESSFULLY), HttpStatus.OK);
    }

    public class JwtResponse {
        private String token;
        private String tokenType;
        private long expiresIn;
        private UserDetails user;

        public JwtResponse(String token, String tokenType, long expiresIn, UserDetails user) {
            this.token = token;
            this.tokenType = tokenType;
            this.expiresIn = expiresIn;
            this.user = user;
        }

        // Getters and Setters
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTokenType() {
            return tokenType;
        }

        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }

        public long getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(long expiresIn) {
            this.expiresIn = expiresIn;
        }

        public UserDetails getUser() {
            return user;
        }

        public void setUser(UserDetails user) {
            this.user = user;
        }
    }
}
