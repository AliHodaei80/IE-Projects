package ir.ie.mizdooni.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.ie.mizdooni.commons.Request;
import ir.ie.mizdooni.commons.Response;
import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.RestaurantTable;
import ir.ie.mizdooni.models.User;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.services.*;
import ir.ie.mizdooni.utils.DateTimeSerializer;
import ir.ie.mizdooni.utils.TimeSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static ir.ie.mizdooni.definitions.Commands.OP_RESERVE_TABLE;
import static ir.ie.mizdooni.definitions.Errors.*;
import static ir.ie.mizdooni.definitions.RequestKeys.*;
import static ir.ie.mizdooni.definitions.TimeFormats.RESERVE_DATETIME_FORMAT;
import static ir.ie.mizdooni.definitions.TimeFormats.RESTAURANT_TIME_FORMAT;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TableReservationTests {
    private MizDooniController controller;

    @BeforeEach
    void resetSingleton() throws NoSuchFieldException, IllegalAccessException {
        Field controllerInstance = MizDooniController.class.getDeclaredField("mizDooniController");
        controllerInstance.setAccessible(true);
        controllerInstance.set(null, null);

        Field userHandlerInstance = UserHandler.class.getDeclaredField("userHandler");
        userHandlerInstance.setAccessible(true);
        userHandlerInstance.set(null, null);

        Field reservationHandlerInstance = ReservationHandler.class.getDeclaredField("reservationHandler");
        reservationHandlerInstance.setAccessible(true);
        reservationHandlerInstance.set(null, null);

        Field restaurantTableHandlerInstance = RestaurantTableHandler.class.getDeclaredField("restaurantTableHandler");
        restaurantTableHandlerInstance.setAccessible(true);
        restaurantTableHandlerInstance.set(null, null);

        Field restaurantHandlerInstance = RestaurantHandler.class.getDeclaredField("restaurantHandler");
        restaurantHandlerInstance.setAccessible(true);
        restaurantHandlerInstance.set(null, null);

        Field reviewHandlerInstance = ReviewHandler.class.getDeclaredField("reviewHandler");
        reviewHandlerInstance.setAccessible(true);
        reviewHandlerInstance.set(null, null);

        controller = MizDooniController.getInstance();
    }

    String convertMapToString(Map<String, Object> data) {
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new DateTimeSerializer())
                .registerTypeAdapter(LocalTime.class, new TimeSerializer())
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return g.toJson(data);
    }

    User createAnonymousClientUser() {
        Map<String, String> address = new HashMap<>();
        address.put("city", "Tehran");
        address.put("country", "Iran");
        return new User("user1", "123", "user@gmail.com", address, UserRole.CLIENT);
    }


    Restaurant createAnonymousRestaurant() {
        Map<String, String> address = new HashMap<>();
        address.put("city", "Tehran");
        address.put("country", "Iran");
        address.put("street", "Ferdoos");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(RESTAURANT_TIME_FORMAT);
        LocalTime startTime = LocalTime.parse("08:00", formatter);
        LocalTime endTime = LocalTime.parse("23:00", formatter);

        return new Restaurant("r1", startTime, endTime, "Iranian", "Nothing", "m1", address);
    }

    RestaurantTable creteRestaurantTable(String restName, String managerUsername, long tableNum, int seatsNum) {
        return new RestaurantTable(tableNum, restName, managerUsername, seatsNum);
    }

    String getCurrDateTimePlus(int plusHours, int plusDays, LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(RESERVE_DATETIME_FORMAT);
        LocalDateTime currentDateTime;
        if (time == null) {
            currentDateTime = LocalDateTime.now();
        } else {
            LocalDate date = LocalDate.now();
            currentDateTime = date.atTime(time);
        }
        currentDateTime = currentDateTime.plusHours(plusHours);
        currentDateTime = currentDateTime.plusDays(plusDays);
        return currentDateTime.format(formatter);
    }

    @Test
    void successTest() {
        // Initiate values
        Map<String, Object> data = new HashMap<>();
        User user = createAnonymousClientUser();
        Map<String, User> usersFake = new HashMap<>();
        usersFake.put(user.getUsername(), user);
        UserHandler userHandler = UserHandler.getInstance();
        userHandler.getUsers().setUsers(usersFake);

        Restaurant restaurant = createAnonymousRestaurant();
        Map<String, Restaurant> restaurantsFake = new HashMap<>();
        restaurantsFake.put(restaurant.getName(), restaurant);
        RestaurantHandler restaurantHandler = RestaurantHandler.getInstance();
        restaurantHandler.getRestaurants().setRestaurants(restaurantsFake);

        RestaurantTable restaurantTable = creteRestaurantTable(restaurant.getName(), restaurant.getManagerUsername(), 1, 1);
        Map<String, Map<Long, RestaurantTable>> restaurantTablesFake = new HashMap<>();
        restaurantTablesFake.put(restaurant.getName(), new HashMap<>());
        restaurantTablesFake.get(restaurant.getName()).put(restaurantTable.getTableNumber(), restaurantTable);
        RestaurantTableHandler restaurantTableHandler = RestaurantTableHandler.getInstance();
        restaurantTableHandler.getRestaurantTables().setRestaurantTables(restaurantTablesFake);


        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
        data.put(USERNAME_KEY, user.getUsername());
        data.put(TABLE_NUM_KEY, restaurantTable.getTableNumber().doubleValue());
        data.put(DATETIME_KEY, getCurrDateTimePlus(1, 1, restaurant.getStartTime()));

        // Exercise
        Response response = controller.handleRequest(new Request(OP_RESERVE_TABLE, convertMapToString(data)));

        // Validate
        assertTrue(response.isSuccess());
        assertInstanceOf(Map.class, response.getData());
        assertTrue(((Map<String, Object>) response.getData()).containsKey(RESERVATION_NUM_KEY));
        assertInstanceOf(Long.class, ((Map<String, Object>) response.getData()).get(RESERVATION_NUM_KEY));
    }

    @Test
    void restaurantNotExistTest() {
        // Initiate values
        Map<String, Object> data = new HashMap<>();
        User user = createAnonymousClientUser();
        Map<String, User> usersFake = new HashMap<>();
        usersFake.put(user.getUsername(), user);
        UserHandler userHandler = UserHandler.getInstance();
        userHandler.getUsers().setUsers(usersFake);

        Restaurant restaurant = createAnonymousRestaurant();

        RestaurantTable restaurantTable = creteRestaurantTable(restaurant.getName(), restaurant.getManagerUsername(), 1, 1);
        Map<String, Map<Long, RestaurantTable>> restaurantTablesFake = new HashMap<>();
        restaurantTablesFake.put(restaurant.getName(), new HashMap<>());
        restaurantTablesFake.get(restaurant.getName()).put(restaurantTable.getTableNumber(), restaurantTable);
        RestaurantTableHandler restaurantTableHandler = RestaurantTableHandler.getInstance();
        restaurantTableHandler.getRestaurantTables().setRestaurantTables(restaurantTablesFake);


        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
        data.put(USERNAME_KEY, user.getUsername());
        data.put(TABLE_NUM_KEY, restaurantTable.getTableNumber().doubleValue());
        data.put(DATETIME_KEY, getCurrDateTimePlus(1, 1, restaurant.getStartTime()));

        // Exercise
        Response response = controller.handleRequest(new Request(OP_RESERVE_TABLE, convertMapToString(data)));

        // Validate
        assertFalse(response.isSuccess());
        assertInstanceOf(String.class, response.getData());
        assertEquals(RESTUARANT_NOT_FOUND, (String) response.getData());
    }

    @Test
    void tableNotExistTest() {
        // Initiate values
        Map<String, Object> data = new HashMap<>();
        User user = createAnonymousClientUser();
        Map<String, User> usersFake = new HashMap<>();
        usersFake.put(user.getUsername(), user);
        UserHandler userHandler = UserHandler.getInstance();
        userHandler.getUsers().setUsers(usersFake);

        Restaurant restaurant = createAnonymousRestaurant();
        Map<String, Restaurant> restaurantsFake = new HashMap<>();
        restaurantsFake.put(restaurant.getName(), restaurant);
        RestaurantHandler restaurantHandler = RestaurantHandler.getInstance();
        restaurantHandler.getRestaurants().setRestaurants(restaurantsFake);

        RestaurantTable restaurantTable = creteRestaurantTable(restaurant.getName(), restaurant.getManagerUsername(), 1, 1);

        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
        data.put(USERNAME_KEY, user.getUsername());
        data.put(TABLE_NUM_KEY, restaurantTable.getTableNumber().doubleValue());
        data.put(DATETIME_KEY, getCurrDateTimePlus(1, 1, restaurant.getStartTime()));

        // Exercise
        Response response = controller.handleRequest(new Request(OP_RESERVE_TABLE, convertMapToString(data)));

        // Validate
        assertFalse(response.isSuccess());
        assertInstanceOf(String.class, response.getData());
        assertEquals(TABLE_NOT_FOUND, (String) response.getData());
    }

    @Test
    void reservationTimeBeforeCurrentTest() {
        // Initiate values
        Map<String, Object> data = new HashMap<>();
        User user = createAnonymousClientUser();
        Map<String, User> usersFake = new HashMap<>();
        usersFake.put(user.getUsername(), user);
        UserHandler userHandler = UserHandler.getInstance();
        userHandler.getUsers().setUsers(usersFake);

        Restaurant restaurant = createAnonymousRestaurant();
        Map<String, Restaurant> restaurantsFake = new HashMap<>();
        restaurantsFake.put(restaurant.getName(), restaurant);
        RestaurantHandler restaurantHandler = RestaurantHandler.getInstance();
        restaurantHandler.getRestaurants().setRestaurants(restaurantsFake);

        RestaurantTable restaurantTable = creteRestaurantTable(restaurant.getName(), restaurant.getManagerUsername(), 1, 1);
        Map<String, Map<Long, RestaurantTable>> restaurantTablesFake = new HashMap<>();
        restaurantTablesFake.put(restaurant.getName(), new HashMap<>());
        restaurantTablesFake.get(restaurant.getName()).put(restaurantTable.getTableNumber(), restaurantTable);
        RestaurantTableHandler restaurantTableHandler = RestaurantTableHandler.getInstance();
        restaurantTableHandler.getRestaurantTables().setRestaurantTables(restaurantTablesFake);


        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
        data.put(USERNAME_KEY, user.getUsername());
        data.put(TABLE_NUM_KEY, restaurantTable.getTableNumber().doubleValue());
        data.put(DATETIME_KEY, getCurrDateTimePlus(-1, 0, null));

        // Exercise
        Response response = controller.handleRequest(new Request(OP_RESERVE_TABLE, convertMapToString(data)));

        // Validate
        assertFalse(response.isSuccess());
        assertInstanceOf(String.class, response.getData());
        assertEquals(INVALID_DATETIME, (String) response.getData());
    }


    @Test
    void reservationTimeOutOfRestaurantWorkHoursTest() {
        // Initiate values
        Map<String, Object> data = new HashMap<>();
        User user = createAnonymousClientUser();
        Map<String, User> usersFake = new HashMap<>();
        usersFake.put(user.getUsername(), user);
        UserHandler userHandler = UserHandler.getInstance();
        userHandler.getUsers().setUsers(usersFake);

        Restaurant restaurant = createAnonymousRestaurant();
        Map<String, Restaurant> restaurantsFake = new HashMap<>();
        restaurantsFake.put(restaurant.getName(), restaurant);
        RestaurantHandler restaurantHandler = RestaurantHandler.getInstance();
        restaurantHandler.getRestaurants().setRestaurants(restaurantsFake);

        RestaurantTable restaurantTable = creteRestaurantTable(restaurant.getName(), restaurant.getManagerUsername(), 1, 1);
        Map<String, Map<Long, RestaurantTable>> restaurantTablesFake = new HashMap<>();
        restaurantTablesFake.put(restaurant.getName(), new HashMap<>());
        restaurantTablesFake.get(restaurant.getName()).put(restaurantTable.getTableNumber(), restaurantTable);
        RestaurantTableHandler restaurantTableHandler = RestaurantTableHandler.getInstance();
        restaurantTableHandler.getRestaurantTables().setRestaurantTables(restaurantTablesFake);


        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
        data.put(USERNAME_KEY, user.getUsername());
        data.put(TABLE_NUM_KEY, restaurantTable.getTableNumber().doubleValue());
        data.put(DATETIME_KEY, getCurrDateTimePlus(1, 1, restaurant.getEndTime()));

        // Exercise
        Response response = controller.handleRequest(new Request(OP_RESERVE_TABLE, convertMapToString(data)));

        // Validate
        assertFalse(response.isSuccess());
        assertInstanceOf(String.class, response.getData());
        assertEquals(DATETIME_NOT_IN_RANGE, (String) response.getData());
    }

    @Test
    void reservationTimeShouldBeRoundTest() {
        // Initiate values
        Map<String, Object> data = new HashMap<>();
        User user = createAnonymousClientUser();
        Map<String, User> usersFake = new HashMap<>();
        usersFake.put(user.getUsername(), user);
        UserHandler userHandler = UserHandler.getInstance();
        userHandler.getUsers().setUsers(usersFake);

        Restaurant restaurant = createAnonymousRestaurant();
        Map<String, Restaurant> restaurantsFake = new HashMap<>();
        restaurantsFake.put(restaurant.getName(), restaurant);
        RestaurantHandler restaurantHandler = RestaurantHandler.getInstance();
        restaurantHandler.getRestaurants().setRestaurants(restaurantsFake);

        RestaurantTable restaurantTable = creteRestaurantTable(restaurant.getName(), restaurant.getManagerUsername(), 1, 1);
        Map<String, Map<Long, RestaurantTable>> restaurantTablesFake = new HashMap<>();
        restaurantTablesFake.put(restaurant.getName(), new HashMap<>());
        restaurantTablesFake.get(restaurant.getName()).put(restaurantTable.getTableNumber(), restaurantTable);
        RestaurantTableHandler restaurantTableHandler = RestaurantTableHandler.getInstance();
        restaurantTableHandler.getRestaurantTables().setRestaurantTables(restaurantTablesFake);


        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
        data.put(USERNAME_KEY, user.getUsername());
        data.put(TABLE_NUM_KEY, restaurantTable.getTableNumber().doubleValue());
        String date = getCurrDateTimePlus(1, 1, restaurant.getStartTime());
        data.put(DATETIME_KEY, date.substring(0, date.length() - 1) + "1");

        // Exercise
        Response response = controller.handleRequest(new Request(OP_RESERVE_TABLE, convertMapToString(data)));

        // Validate
        assertFalse(response.isSuccess());
        assertInstanceOf(String.class, response.getData());
        assertEquals(INVALID_TIME_FORMAT + RESERVE_DATETIME_FORMAT, (String) response.getData());
    }

    @Test
    void userNotExistTest() {
        // Initiate values
        Map<String, Object> data = new HashMap<>();
        User user = createAnonymousClientUser();

        Restaurant restaurant = createAnonymousRestaurant();
        Map<String, Restaurant> restaurantsFake = new HashMap<>();
        restaurantsFake.put(restaurant.getName(), restaurant);
        RestaurantHandler restaurantHandler = RestaurantHandler.getInstance();
        restaurantHandler.getRestaurants().setRestaurants(restaurantsFake);

        RestaurantTable restaurantTable = creteRestaurantTable(restaurant.getName(), restaurant.getManagerUsername(), 1, 1);
        Map<String, Map<Long, RestaurantTable>> restaurantTablesFake = new HashMap<>();
        restaurantTablesFake.put(restaurant.getName(), new HashMap<>());
        restaurantTablesFake.get(restaurant.getName()).put(restaurantTable.getTableNumber(), restaurantTable);
        RestaurantTableHandler restaurantTableHandler = RestaurantTableHandler.getInstance();
        restaurantTableHandler.getRestaurantTables().setRestaurantTables(restaurantTablesFake);


        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
        data.put(USERNAME_KEY, user.getUsername());
        data.put(TABLE_NUM_KEY, restaurantTable.getTableNumber().doubleValue());
        data.put(DATETIME_KEY, getCurrDateTimePlus(1, 0, null));

        // Exercise
        Response response = controller.handleRequest(new Request(OP_RESERVE_TABLE, convertMapToString(data)));

        // Validate
        assertFalse(response.isSuccess());
        assertInstanceOf(String.class, response.getData());
        assertEquals(USER_NOT_EXISTS, (String) response.getData());
    }

    @Test
    void userRoleNotValidTest() {
        // Initiate values
        Map<String, Object> data = new HashMap<>();
        User user = createAnonymousClientUser();
        user.setRole(UserRole.MANAGER);
        Map<String, User> usersFake = new HashMap<>();
        usersFake.put(user.getUsername(), user);
        UserHandler userHandler = UserHandler.getInstance();
        userHandler.getUsers().setUsers(usersFake);

        Restaurant restaurant = createAnonymousRestaurant();
        Map<String, Restaurant> restaurantsFake = new HashMap<>();
        restaurantsFake.put(restaurant.getName(), restaurant);
        RestaurantHandler restaurantHandler = RestaurantHandler.getInstance();
        restaurantHandler.getRestaurants().setRestaurants(restaurantsFake);

        RestaurantTable restaurantTable = creteRestaurantTable(restaurant.getName(), restaurant.getManagerUsername(), 1, 1);
        Map<String, Map<Long, RestaurantTable>> restaurantTablesFake = new HashMap<>();
        restaurantTablesFake.put(restaurant.getName(), new HashMap<>());
        restaurantTablesFake.get(restaurant.getName()).put(restaurantTable.getTableNumber(), restaurantTable);
        RestaurantTableHandler restaurantTableHandler = RestaurantTableHandler.getInstance();
        restaurantTableHandler.getRestaurantTables().setRestaurantTables(restaurantTablesFake);


        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
        data.put(USERNAME_KEY, user.getUsername());
        data.put(TABLE_NUM_KEY, restaurantTable.getTableNumber().doubleValue());
        data.put(DATETIME_KEY, getCurrDateTimePlus(1, 0, null));

        // Exercise
        Response response = controller.handleRequest(new Request(OP_RESERVE_TABLE, convertMapToString(data)));

        // Validate
        assertFalse(response.isSuccess());
        assertInstanceOf(String.class, response.getData());
        assertEquals(INVALID_USER_ROLE, (String) response.getData());
    }

}
