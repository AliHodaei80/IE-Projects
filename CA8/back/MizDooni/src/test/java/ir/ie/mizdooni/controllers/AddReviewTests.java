package ir.ie.mizdooni.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.ie.mizdooni.commons.Request;
import ir.ie.mizdooni.commons.Response;
import ir.ie.mizdooni.models.Restaurant;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static ir.ie.mizdooni.definitions.Commands.OP_ADD_REVIEW;
import static ir.ie.mizdooni.definitions.Errors.*;
import static ir.ie.mizdooni.definitions.RequestKeys.*;
import static ir.ie.mizdooni.definitions.Successes.REVIEW_ADDED_SUCCESSFULLY;
import static ir.ie.mizdooni.definitions.TimeFormats.RESTAURANT_TIME_FORMAT;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AddReviewTests {
    private MizDooniController controller;

    @BeforeEach
    void resetSingleton() throws NoSuchFieldException, IllegalAccessException {
        Field controllerInstance = MizDooniController.class.getDeclaredField("mizDooniController");
        controllerInstance.setAccessible(true);
        controllerInstance.set(null, null);

        Field userHandlerInstance = UserHandler.class.getDeclaredField("userHandler");
        userHandlerInstance.setAccessible(true);
        userHandlerInstance.set(null, null);

        Field restaurantHandlerInstance = RestaurantHandler.class.getDeclaredField("restaurantHandler");
        restaurantHandlerInstance.setAccessible(true);
        restaurantHandlerInstance.set(null, null);

        Field reviewHandlerInstance = ReviewHandler.class.getDeclaredField("reviewHandler");
        reviewHandlerInstance.setAccessible(true);
        reviewHandlerInstance.set(null, null);

        Field reservationHandlerInstance = ReservationHandler.class.getDeclaredField("reservationHandler");
        reservationHandlerInstance.setAccessible(true);
        reservationHandlerInstance.set(null, null);

        Field restaurantTableHandlerInstance = RestaurantTableHandler.class.getDeclaredField("restaurantTableHandler");
        restaurantTableHandlerInstance.setAccessible(true);
        restaurantTableHandlerInstance.set(null, null);

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

//    User createAnonymousClientUser() {
//        Map<String, String> address = new HashMap<>();
//        address.put("city", "Tehran");
//        address.put("country", "Iran");
//        return new User("user1", "123", "user@gmail.com", address, UserRole.CLIENT);
//    }
//
//
//    Restaurant createAnonymousRestaurant() {
//        Map<String, String> address = new HashMap<>();
//        address.put("city", "Tehran");
//        address.put("country", "Iran");
//        address.put("street", "Ferdoos");
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(RESTAURANT_TIME_FORMAT);
//        LocalTime startTime = LocalTime.parse("08:00", formatter);
//        LocalTime endTime = LocalTime.parse("23:00", formatter);
//
//        return new Restaurant("r1", startTime, endTime, "Iranian", "Nothing", "m1", address, 1L);
//    }



//    @Test
//    void successTest() {
//        // Initiate values
//        Map<String, Object> data = new HashMap<>();
//        User user = createAnonymousClientUser();
//        Map<String, User> usersFake = new HashMap<>();
//        usersFake.put(user.getUsername(), user);
//        UserHandler userHandler = UserHandler.getInstance();
//        userHandler.getUsers().setUsers(usersFake);
//
//        Restaurant restaurant = createAnonymousRestaurant();
//        Map<String, Restaurant> restaurantsFake = new HashMap<>();
//        restaurantsFake.put(restaurant.getName(), restaurant);
//        RestaurantHandler restaurantHandler = RestaurantHandler.getInstance();
//        restaurantHandler.getRestaurants().setRestaurants(restaurantsFake);
//
//        double ambianceRate = 4.5;
//        double serviceRate = 4;
//        double overallRate = 4;
//        double foodRate = 4.5;
//        String comment = "Not bad!";
//
//
//        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
//        data.put(USERNAME_KEY, user.getUsername());
//        data.put(AMBIANCE_RATE_KEY, ambianceRate);
//        data.put(OVERALL_RATE_KEY, overallRate);
//        data.put(SERVICE_RATE_KEY, serviceRate);
//        data.put(FOOD_RATE_KEY, foodRate);
//        data.put(COMMENT_KEY, comment);
//
//        // Exercise
//        Response response = controller.handleRequest(new Request(OP_ADD_REVIEW, convertMapToString(data)));
//
//        // Validate
//        assertTrue(response.isSuccess());
//        assertInstanceOf(String.class, response.getData());
//        assertEquals(REVIEW_ADDED_SUCCESSFULLY, (String) response.getData());
//        assertEquals(ReviewHandler.getInstance().getReviews().getAllReviews().size(), 1);
//        assertEquals(ReviewHandler.getInstance().getReviews().getAllReviews().get(0).getRestaurantName(), restaurant.getName());
//        assertEquals(ReviewHandler.getInstance().getReviews().getAllReviews().get(0).getUsername(), user.getUsername());
//        assertEquals(ReviewHandler.getInstance().getReviews().getAllReviews().get(0).getAmbianceRate(), ambianceRate);
//        assertEquals(ReviewHandler.getInstance().getReviews().getAllReviews().get(0).getComment(), comment);
//        assertEquals(ReviewHandler.getInstance().getReviews().getAllReviews().get(0).getFoodRate(), foodRate);
//        assertEquals(ReviewHandler.getInstance().getReviews().getAllReviews().get(0).getOverallRate(), overallRate);
//        assertEquals(ReviewHandler.getInstance().getReviews().getAllReviews().get(0).getServiceRate(), serviceRate);
//    }
//
//    @Test
//    void restaurantNotExistTest() {
//        // Initiate values
//        Map<String, Object> data = new HashMap<>();
//        User user = createAnonymousClientUser();
//        Map<String, User> usersFake = new HashMap<>();
//        usersFake.put(user.getUsername(), user);
//        UserHandler userHandler = UserHandler.getInstance();
//        userHandler.getUsers().setUsers(usersFake);
//
//        Restaurant restaurant = createAnonymousRestaurant();
//
//        double ambianceRate = 4.5;
//        double serviceRate = 4;
//        double overallRate = 4;
//        double foodRate = 4.5;
//        String comment = "Not bad!";
//
//
//        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
//        data.put(USERNAME_KEY, user.getUsername());
//        data.put(AMBIANCE_RATE_KEY, ambianceRate);
//        data.put(OVERALL_RATE_KEY, overallRate);
//        data.put(SERVICE_RATE_KEY, serviceRate);
//        data.put(FOOD_RATE_KEY, foodRate);
//        data.put(COMMENT_KEY, comment);
//
//        // Exercise
//        Response response = controller.handleRequest(new Request(OP_ADD_REVIEW, convertMapToString(data)));
//
//        // Validate
//        assertFalse(response.isSuccess());
//        assertInstanceOf(String.class, response.getData());
//        assertEquals(RESTUARANT_NOT_FOUND, (String) response.getData());
//        assertEquals(ReviewHandler.getInstance().getReviews().getAllReviews().size(), 0);
//    }
//
//    @Test
//    void userNotExistTest() {
//        // Initiate values
//        Map<String, Object> data = new HashMap<>();
//        User user = createAnonymousClientUser();
//
//        Restaurant restaurant = createAnonymousRestaurant();
//        Map<String, Restaurant> restaurantsFake = new HashMap<>();
//        restaurantsFake.put(restaurant.getName(), restaurant);
//        RestaurantHandler restaurantHandler = RestaurantHandler.getInstance();
//        restaurantHandler.getRestaurants().setRestaurants(restaurantsFake);
//
//        double ambianceRate = 4.5;
//        double serviceRate = 4;
//        double overallRate = 4;
//        double foodRate = 4.5;
//        String comment = "Not bad!";
//
//
//        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
//        data.put(USERNAME_KEY, user.getUsername());
//        data.put(AMBIANCE_RATE_KEY, ambianceRate);
//        data.put(OVERALL_RATE_KEY, overallRate);
//        data.put(SERVICE_RATE_KEY, serviceRate);
//        data.put(FOOD_RATE_KEY, foodRate);
//        data.put(COMMENT_KEY, comment);
//
//        // Exercise
//        Response response = controller.handleRequest(new Request(OP_ADD_REVIEW, convertMapToString(data)));
//
//        // Validate
//        assertFalse(response.isSuccess());
//        assertInstanceOf(String.class, response.getData());
//        assertEquals(USER_NOT_FOUND, (String) response.getData());
//        assertEquals(ReviewHandler.getInstance().getReviews().getAllReviews().size(), 0);
//    }
//
//    @Test
//    void userRoleNotValidTest() {
//        // Initiate values
//        Map<String, Object> data = new HashMap<>();
//        User user = createAnonymousClientUser();
//        user.setRole(UserRole.MANAGER);
//        Map<String, User> usersFake = new HashMap<>();
//        usersFake.put(user.getUsername(), user);
//        UserHandler userHandler = UserHandler.getInstance();
//        userHandler.getUsers().setUsers(usersFake);
//
//        Restaurant restaurant = createAnonymousRestaurant();
//        Map<String, Restaurant> restaurantsFake = new HashMap<>();
//        restaurantsFake.put(restaurant.getName(), restaurant);
//        RestaurantHandler restaurantHandler = RestaurantHandler.getInstance();
//        restaurantHandler.getRestaurants().setRestaurants(restaurantsFake);
//
//        double ambianceRate = 4.5;
//        double serviceRate = 4;
//        double overallRate = 4;
//        double foodRate = 4.5;
//        String comment = "Not bad!";
//
//
//        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
//        data.put(USERNAME_KEY, user.getUsername());
//        data.put(AMBIANCE_RATE_KEY, ambianceRate);
//        data.put(OVERALL_RATE_KEY, overallRate);
//        data.put(SERVICE_RATE_KEY, serviceRate);
//        data.put(FOOD_RATE_KEY, foodRate);
//        data.put(COMMENT_KEY, comment);
//
//        // Exercise
//        Response response = controller.handleRequest(new Request(OP_ADD_REVIEW, convertMapToString(data)));
//
//        // Validate
//        assertFalse(response.isSuccess());
//        assertInstanceOf(String.class, response.getData());
//        assertEquals(INVALID_USER_ROLE, (String) response.getData());
//        assertEquals(ReviewHandler.getInstance().getReviews().getAllReviews().size(), 0);
//    }
//
//    @Test
//    void foodRateOutOfBoundUpperTest() {
//        // Initiate values
//        Map<String, Object> data = new HashMap<>();
//        User user = createAnonymousClientUser();
//        Map<String, User> usersFake = new HashMap<>();
//        usersFake.put(user.getUsername(), user);
//        UserHandler userHandler = UserHandler.getInstance();
//        userHandler.getUsers().setUsers(usersFake);
//
//        Restaurant restaurant = createAnonymousRestaurant();
//        Map<String, Restaurant> restaurantsFake = new HashMap<>();
//        restaurantsFake.put(restaurant.getName(), restaurant);
//        RestaurantHandler restaurantHandler = RestaurantHandler.getInstance();
//        restaurantHandler.getRestaurants().setRestaurants(restaurantsFake);
//
//        double ambianceRate = 4.5;
//        double serviceRate = 4;
//        double overallRate = 4;
//        double foodRate = 10;
//        String comment = "Not bad!";
//
//
//        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
//        data.put(USERNAME_KEY, user.getUsername());
//        data.put(AMBIANCE_RATE_KEY, ambianceRate);
//        data.put(OVERALL_RATE_KEY, overallRate);
//        data.put(SERVICE_RATE_KEY, serviceRate);
//        data.put(FOOD_RATE_KEY, foodRate);
//        data.put(COMMENT_KEY, comment);
//
//        // Exercise
//        Response response = controller.handleRequest(new Request(OP_ADD_REVIEW, convertMapToString(data)));
//
//        // Validate
//        assertFalse(response.isSuccess());
//        assertInstanceOf(String.class, response.getData());
//        assertEquals(INVALID_RATING_FORMAT + " Field : " + FOOD_RATE_KEY, (String) response.getData());
//        assertEquals(ReviewHandler.getInstance().getReviews().getAllReviews().size(), 0);
//    }
//
//    @Test
//    void foodRateOutOfBoundLowerTest() {
//        // Initiate values
//        Map<String, Object> data = new HashMap<>();
//        User user = createAnonymousClientUser();
//        Map<String, User> usersFake = new HashMap<>();
//        usersFake.put(user.getUsername(), user);
//        UserHandler userHandler = UserHandler.getInstance();
//        userHandler.getUsers().setUsers(usersFake);
//
//        Restaurant restaurant = createAnonymousRestaurant();
//        Map<String, Restaurant> restaurantsFake = new HashMap<>();
//        restaurantsFake.put(restaurant.getName(), restaurant);
//        RestaurantHandler restaurantHandler = RestaurantHandler.getInstance();
//        restaurantHandler.getRestaurants().setRestaurants(restaurantsFake);
//
//        double ambianceRate = 4.5;
//        double serviceRate = 4;
//        double overallRate = 4;
//        double foodRate = -1;
//        String comment = "Not bad!";
//
//
//        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
//        data.put(USERNAME_KEY, user.getUsername());
//        data.put(AMBIANCE_RATE_KEY, ambianceRate);
//        data.put(OVERALL_RATE_KEY, overallRate);
//        data.put(SERVICE_RATE_KEY, serviceRate);
//        data.put(FOOD_RATE_KEY, foodRate);
//        data.put(COMMENT_KEY, comment);
//
//        // Exercise
//        Response response = controller.handleRequest(new Request(OP_ADD_REVIEW, convertMapToString(data)));
//
//        // Validate
//        assertFalse(response.isSuccess());
//        assertInstanceOf(String.class, response.getData());
//        assertEquals(INVALID_RATING_FORMAT + " Field : " + FOOD_RATE_KEY, (String) response.getData());
//        assertEquals(ReviewHandler.getInstance().getReviews().getAllReviews().size(), 0);
//    }
//
//    @Test
//    void serviceRateOutOfBoundLowerTest() {
//        // Initiate values
//        Map<String, Object> data = new HashMap<>();
//        User user = createAnonymousClientUser();
//        Map<String, User> usersFake = new HashMap<>();
//        usersFake.put(user.getUsername(), user);
//        UserHandler userHandler = UserHandler.getInstance();
//        userHandler.getUsers().setUsers(usersFake);
//
//        Restaurant restaurant = createAnonymousRestaurant();
//        Map<String, Restaurant> restaurantsFake = new HashMap<>();
//        restaurantsFake.put(restaurant.getName(), restaurant);
//        RestaurantHandler restaurantHandler = RestaurantHandler.getInstance();
//        restaurantHandler.getRestaurants().setRestaurants(restaurantsFake);
//
//        double ambianceRate = 4.5;
//        double serviceRate = -1;
//        double overallRate = 4;
//        double foodRate = 4;
//        String comment = "Not bad!";
//
//
//        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
//        data.put(USERNAME_KEY, user.getUsername());
//        data.put(AMBIANCE_RATE_KEY, ambianceRate);
//        data.put(OVERALL_RATE_KEY, overallRate);
//        data.put(SERVICE_RATE_KEY, serviceRate);
//        data.put(FOOD_RATE_KEY, foodRate);
//        data.put(COMMENT_KEY, comment);
//
//        // Exercise
//        Response response = controller.handleRequest(new Request(OP_ADD_REVIEW, convertMapToString(data)));
//
//        // Validate
//        assertFalse(response.isSuccess());
//        assertInstanceOf(String.class, response.getData());
//        assertEquals(INVALID_RATING_FORMAT + " Field : " + SERVICE_RATE_KEY, (String) response.getData());
//        assertEquals(ReviewHandler.getInstance().getReviews().getAllReviews().size(), 0);
//    }
//
//    @Test
//    void serviceRateOutOfBoundUpperTest() {
//        // Initiate values
//        Map<String, Object> data = new HashMap<>();
//        User user = createAnonymousClientUser();
//        Map<String, User> usersFake = new HashMap<>();
//        usersFake.put(user.getUsername(), user);
//        UserHandler userHandler = UserHandler.getInstance();
//        userHandler.getUsers().setUsers(usersFake);
//
//        Restaurant restaurant = createAnonymousRestaurant();
//        Map<String, Restaurant> restaurantsFake = new HashMap<>();
//        restaurantsFake.put(restaurant.getName(), restaurant);
//        RestaurantHandler restaurantHandler = RestaurantHandler.getInstance();
//        restaurantHandler.getRestaurants().setRestaurants(restaurantsFake);
//
//        double ambianceRate = 4.5;
//        double serviceRate = 6;
//        double overallRate = 4;
//        double foodRate = 4;
//        String comment = "Not bad!";
//
//        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
//        data.put(USERNAME_KEY, user.getUsername());
//        data.put(AMBIANCE_RATE_KEY, ambianceRate);
//        data.put(OVERALL_RATE_KEY, overallRate);
//        data.put(SERVICE_RATE_KEY, serviceRate);
//        data.put(FOOD_RATE_KEY, foodRate);
//        data.put(COMMENT_KEY, comment);
//
//        // Exercise
//        Response response = controller.handleRequest(new Request(OP_ADD_REVIEW, convertMapToString(data)));
//
//        // Validate
//        assertFalse(response.isSuccess());
//        assertInstanceOf(String.class, response.getData());
//        assertEquals(INVALID_RATING_FORMAT + " Field : " + SERVICE_RATE_KEY, (String) response.getData());
//        assertEquals(ReviewHandler.getInstance().getReviews().getAllReviews().size(), 0);
//    }
//
//    @Test
//    void ambianceRateOutOfBoundUpperTest() {
//        // Initiate values
//        Map<String, Object> data = new HashMap<>();
//        User user = createAnonymousClientUser();
//        Map<String, User> usersFake = new HashMap<>();
//        usersFake.put(user.getUsername(), user);
//        UserHandler userHandler = UserHandler.getInstance();
//        userHandler.getUsers().setUsers(usersFake);
//
//        Restaurant restaurant = createAnonymousRestaurant();
//        Map<String, Restaurant> restaurantsFake = new HashMap<>();
//        restaurantsFake.put(restaurant.getName(), restaurant);
//        RestaurantHandler restaurantHandler = RestaurantHandler.getInstance();
//        restaurantHandler.getRestaurants().setRestaurants(restaurantsFake);
//
//        double ambianceRate = 5.1;
//        double serviceRate = 4;
//        double overallRate = 4;
//        double foodRate = 4;
//        String comment = "Not bad!";
//
//        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
//        data.put(USERNAME_KEY, user.getUsername());
//        data.put(AMBIANCE_RATE_KEY, ambianceRate);
//        data.put(OVERALL_RATE_KEY, overallRate);
//        data.put(SERVICE_RATE_KEY, serviceRate);
//        data.put(FOOD_RATE_KEY, foodRate);
//        data.put(COMMENT_KEY, comment);
//
//        // Exercise
//        Response response = controller.handleRequest(new Request(OP_ADD_REVIEW, convertMapToString(data)));
//
//        // Validate
//        assertFalse(response.isSuccess());
//        assertInstanceOf(String.class, response.getData());
//        assertEquals(INVALID_RATING_FORMAT + " Field : " + AMBIANCE_RATE_KEY, (String) response.getData());
//        assertEquals(ReviewHandler.getInstance().getReviews().getAllReviews().size(), 0);
//    }
//
//    @Test
//    void ambianceRateOutOfBoundLowerTest() {
//        // Initiate values
//        Map<String, Object> data = new HashMap<>();
//        User user = createAnonymousClientUser();
//        Map<String, User> usersFake = new HashMap<>();
//        usersFake.put(user.getUsername(), user);
//        UserHandler userHandler = UserHandler.getInstance();
//        userHandler.getUsers().setUsers(usersFake);
//
//        Restaurant restaurant = createAnonymousRestaurant();
//        Map<String, Restaurant> restaurantsFake = new HashMap<>();
//        restaurantsFake.put(restaurant.getName(), restaurant);
//        RestaurantHandler restaurantHandler = RestaurantHandler.getInstance();
//        restaurantHandler.getRestaurants().setRestaurants(restaurantsFake);
//
//        double ambianceRate = -1.1;
//        double serviceRate = 4;
//        double overallRate = 4;
//        double foodRate = 4;
//        String comment = "Not bad!";
//
//        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
//        data.put(USERNAME_KEY, user.getUsername());
//        data.put(AMBIANCE_RATE_KEY, ambianceRate);
//        data.put(OVERALL_RATE_KEY, overallRate);
//        data.put(SERVICE_RATE_KEY, serviceRate);
//        data.put(FOOD_RATE_KEY, foodRate);
//        data.put(COMMENT_KEY, comment);
//
//        // Exercise
//        Response response = controller.handleRequest(new Request(OP_ADD_REVIEW, convertMapToString(data)));
//
//        // Validate
//        assertFalse(response.isSuccess());
//        assertInstanceOf(String.class, response.getData());
//        assertEquals(INVALID_RATING_FORMAT + " Field : " + AMBIANCE_RATE_KEY, (String) response.getData());
//        assertEquals(ReviewHandler.getInstance().getReviews().getAllReviews().size(), 0);
//    }
//
//    @Test
//    void overallRateOutOfBoundLowerTest() {
//        // Initiate values
//        Map<String, Object> data = new HashMap<>();
//        User user = createAnonymousClientUser();
//        Map<String, User> usersFake = new HashMap<>();
//        usersFake.put(user.getUsername(), user);
//        UserHandler userHandler = UserHandler.getInstance();
//        userHandler.getUsers().setUsers(usersFake);
//
//        Restaurant restaurant = createAnonymousRestaurant();
//        Map<String, Restaurant> restaurantsFake = new HashMap<>();
//        restaurantsFake.put(restaurant.getName(), restaurant);
//        RestaurantHandler restaurantHandler = RestaurantHandler.getInstance();
//        restaurantHandler.getRestaurants().setRestaurants(restaurantsFake);
//
//        double ambianceRate = 4.5;
//        double serviceRate = 4;
//        double overallRate = -4;
//        double foodRate = 4;
//        String comment = "Not bad!";
//
//
//        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
//        data.put(USERNAME_KEY, user.getUsername());
//        data.put(AMBIANCE_RATE_KEY, ambianceRate);
//        data.put(OVERALL_RATE_KEY, overallRate);
//        data.put(SERVICE_RATE_KEY, serviceRate);
//        data.put(FOOD_RATE_KEY, foodRate);
//        data.put(COMMENT_KEY, comment);
//
//        // Exercise
//        Response response = controller.handleRequest(new Request(OP_ADD_REVIEW, convertMapToString(data)));
//
//        // Validate
//        assertFalse(response.isSuccess());
//        assertInstanceOf(String.class, response.getData());
//        assertEquals(INVALID_RATING_FORMAT + " Field : " + OVERALL_RATE_KEY, (String) response.getData());
//        assertEquals(ReviewHandler.getInstance().getReviews().getAllReviews().size(), 0);
//    }
//
//    @Test
//    void overallRateOutOfBoundUpperTest() {
//        // Initiate values
//        Map<String, Object> data = new HashMap<>();
//        User user = createAnonymousClientUser();
//        Map<String, User> usersFake = new HashMap<>();
//        usersFake.put(user.getUsername(), user);
//        UserHandler userHandler = UserHandler.getInstance();
//        userHandler.getUsers().setUsers(usersFake);
//
//        Restaurant restaurant = createAnonymousRestaurant();
//        Map<String, Restaurant> restaurantsFake = new HashMap<>();
//        restaurantsFake.put(restaurant.getName(), restaurant);
//        RestaurantHandler restaurantHandler = RestaurantHandler.getInstance();
//        restaurantHandler.getRestaurants().setRestaurants(restaurantsFake);
//
//        double ambianceRate = 4.5;
//        double serviceRate = 4;
//        double overallRate = 10;
//        double foodRate = 4;
//        String comment = "Not bad!";
//
//
//        data.put(RESTAURANT_NAME_KEY, restaurant.getName());
//        data.put(USERNAME_KEY, user.getUsername());
//        data.put(AMBIANCE_RATE_KEY, ambianceRate);
//        data.put(OVERALL_RATE_KEY, overallRate);
//        data.put(SERVICE_RATE_KEY, serviceRate);
//        data.put(FOOD_RATE_KEY, foodRate);
//        data.put(COMMENT_KEY, comment);
//
//        // Exercise
//        Response response = controller.handleRequest(new Request(OP_ADD_REVIEW, convertMapToString(data)));
//
//        // Validate
//        assertFalse(response.isSuccess());
//        assertInstanceOf(String.class, response.getData());
//        assertEquals(INVALID_RATING_FORMAT + " Field : " + OVERALL_RATE_KEY, (String) response.getData());
//        assertEquals(ReviewHandler.getInstance().getReviews().getAllReviews().size(), 0);
//    }
}
