package ir.ie.mizdooni.controllers;
import org.springframework.web.bind.annotation.CrossOrigin;
import ir.ie.mizdooni.commons.Response;
import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.services.UserHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static ir.ie.mizdooni.definitions.RequestKeys.*;
import static ir.ie.mizdooni.definitions.Successes.*;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validateAddUser;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validateLoginUser;

@RestController
public class LoginRestController {
    private static UserHandler userHandler;
    private final Logger logger;


    @Autowired
    public LoginRestController(UserHandler userHandler) {
        this.userHandler = userHandler;
        logger = LoggerFactory.getLogger(LoginRestController.class);
    }

    // addUser
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
            userHandler.loginUser((String) data.get(USERNAME_KEY), (String) data.get(PASSWORD_KEY));
            logger.info("User added successfully");
            return new ResponseEntity<>(new Response(true, USER_ADDED_SUCCESSFULLY), HttpStatus.OK);
        } catch (UserNameAlreadyTaken | InvalidUserRole | EmailAlreadyTaken | InvalidRequestTypeFormat |
                 InvalidUsernameFormat | InvalidRequestFormat | InvalidEmailFormat | UserNotExists | AuthenticationFailed e) {
            logger.error("User addition failed. error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // LoginPageController POST
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Response> loginHandler(@RequestBody Map<String, Object> data) {
        try {
            validateLoginUser(data);
            userHandler.loginUser((String) data.get(USERNAME_KEY), (String) data.get(PASSWORD_KEY));
            logger.info("User `" + (String) data.get(USERNAME_KEY) + "` login successfully");
            return new ResponseEntity<>(new Response(true, USER_LOGIN_SUCCESSFULLY), HttpStatus.OK);
        } catch (UserNotExists | AuthenticationFailed | InvalidRequestTypeFormat | InvalidRequestFormat |
                 InvalidEmailFormat e) {
            logger.error("Login failed for User `" + (String) data.get(USERNAME_KEY) + "`. error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // LogoutPageController POST
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Response> logoutHandler() {
        userHandler.logoutUser();
        ;
        logger.info("User logout successfully");
        return new ResponseEntity<>(new Response(true, USER_LOGOUT_SUCCESSFULLY), HttpStatus.OK);
    }


}
