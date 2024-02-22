package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.commons.Request;
import ir.ie.mizdooni.commons.Response;
import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.services.ReservationHandler;
import ir.ie.mizdooni.services.RestaurantHandler;
import ir.ie.mizdooni.services.RestaurantTableHandler;
import ir.ie.mizdooni.services.UserHandler;

import java.util.Map;

import static ir.ie.mizdooni.defines.Commands.OP_ADD_USER;
import static ir.ie.mizdooni.defines.Errors.UNSUPPORTED_COMMAND;
import static ir.ie.mizdooni.defines.RequestKeys.*;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validate;

public class MizDooniController {
    private static UserHandler userHandler;
    private static RestaurantTableHandler restaurantTableHandler;
    private static ReservationHandler reservationHandler;
    private static RestaurantHandler restaurantHandler;

    private static MizDooniController mizDooniController;


    private MizDooniController() {
        userHandler = UserHandler.getInstance();
        restaurantHandler = RestaurantHandler.getInstance();
        reservationHandler = ReservationHandler.getInstance();
        restaurantTableHandler = RestaurantTableHandler.getInstance();
    }

    public static MizDooniController getInstance() {
        if (mizDooniController == null)
            mizDooniController = new MizDooniController();
        return mizDooniController;
    }

    public Response addUser(Map<String, Object> data) {
        try {
            userHandler.addUser((String) data.get(USERNAME_KEY),
                    (String) data.get(EMAIL_KEY),
                    (String) data.get(USER_ROLE_KEY),
                    (String) data.get(PASSWORD_KEY),
                    (Map<String, String>) data.get(USER_ADDRESS_KEY)
            );
            return new Response(true, "User added successfully.");
        } catch (
                UserNameAlreadyTaken | EmailAlreadyTaken | InvalidUserRole e) {
            return new Response(false, e.getMessage());
        }

    }

    public Response handleRequest(Request request) {
        String op = request.getOperation();
        Map<String, Object> data = request.getData();
        try {
            validate(request);
        } catch (InvalidUsernameFormat | InvalidRequestFormat | InvalidEmailFormat | InvalidTimeFormat e) {
            return new Response(false, e.getMessage());

        }
        switch (op) {
            case OP_ADD_USER:
                System.out.println("Calling Validate add user");
                return addUser(data);
            default:
                return new Response(false, UNSUPPORTED_COMMAND);
        }
    }
}
