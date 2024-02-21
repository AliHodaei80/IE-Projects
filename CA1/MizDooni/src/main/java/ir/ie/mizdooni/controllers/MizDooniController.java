package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.commons.Request;
import ir.ie.mizdooni.commons.Response;
import ir.ie.mizdooni.services.ReservationHandler;
import ir.ie.mizdooni.services.RestaurantHandler;
import ir.ie.mizdooni.services.RestaurantTableHandler;
import ir.ie.mizdooni.services.UserHandler;

import java.util.Map;
import java.util.Objects;

import static ir.ie.mizdooni.defines.Commands.OP_ADD_RESTAURANT;
import static ir.ie.mizdooni.defines.Commands.OP_ADD_USER;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validateAddUser;

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
            userHandler.addUser((String) data.get("username"),
                    (String) data.get("email"),
                    (String) data.get("role"),
                    (String) data.get("password"),
                    (Map<String, String>) data.get("address")
            );
            return new Response(true, "User added successfully.");
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }

    }

    public Response handleRequest(Request request) {
        String op = request.getOperation();
        Map<String, Object> data = request.getData();
        switch (op) {
            case OP_ADD_USER:
                System.out.println("Calling Validate add user");
                try {
                    validateAddUser(data);
                    return addUser(data);
                } catch (Exception e) {
                    return new Response(false, e.getMessage());
                }
            default:
                return new Response(false, "Unsupported command");
        }
    }
}
