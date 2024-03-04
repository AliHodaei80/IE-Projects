package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.services.RestaurantTableHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;

import static ir.ie.mizdooni.definitions.Paths.ERROR_JSP_PAGE;
import static ir.ie.mizdooni.definitions.RequestKeys.*;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validateAddTable;

@WebServlet("/addtable")
public class AddTableController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AddTableController.class);
    Map<String, Object> convertRequestToMap(HttpServletRequest request) {
        return Map.of(
                RESTAURANT_NAME_KEY, request.getParameter("restaurant_name"),
                TABLE_NUM_KEY, Long.parseLong(request.getParameter("table_number")),
                SEATS_NUM_KEY, Integer.parseInt(request.getParameter("seats_number")),
                MANAGER_USERNAME_KEY, request.getParameter("manager_name")
        );
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("AddTableController: post request");
        try {
            Map<String, Object> data = convertRequestToMap(request);
            logger.info("data: " + data);
            validateAddTable(data);
            RestaurantTableHandler.getInstance().addRestaurantTable((String) data.get(RESTAURANT_NAME_KEY),
                    (Long) (data.get(TABLE_NUM_KEY)),
                    (int)  (data.get(SEATS_NUM_KEY)),
                    (String) data.get(MANAGER_USERNAME_KEY));
            response.sendRedirect("/");
        } catch (InvalidRequestFormat | InvalidNumType | InvalidRequestTypeFormat
                 | RestaurantManagerNotFound | InvalidUserRole | ManagerUsernameNotMatch | RestaurantNotFound |
                 TableAlreadyExists | NumberFormatException e) {
            logger.error("add table failed: error: " + e.getMessage(), e);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(ERROR_JSP_PAGE).forward(request, response);
        }
    }
}
