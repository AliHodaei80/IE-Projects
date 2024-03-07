<jsp:include page="header.jsp" />
<%@page import="ir.ie.mizdooni.services.*"%>
<%@ page import="java.util.*" %>
<%@ page import="ir.ie.mizdooni.models.*" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>Restaurant</title>
  </head>
         <%
              String clientName = (String) request.getAttribute("username");
              Restaurant restaurant = (Restaurant) request.getAttribute("restaurant");
              List<RestaurantTable> tables = (List<RestaurantTable>) request.getAttribute("restaurantTables");
              List<Review> reviews = (List<Review>) request.getAttribute("reviews");
          %>
  <body>
    <p id="username">username: <%= clientName %> <a href="/">Home</a> <a href="/logout" style="color: red">Log Out</a></p>
    <br>
    <h2>Restaurant Info:</h2>
    <ul>
      <li id="id">Id: <%= restaurant.getId() %></li>
        <li id="name">Name: <%= restaurant.getName() %></li>
        <li id="type">Type: <%= restaurant.getType() %></li>
        <li id="time">Time: <%= restaurant.getStartTimeString() %> - <%= restaurant.getEndTimeString() %></li>
        <li id="rate">Scores:</li>
        <ul>
          <li>Food: <%= restaurant.getAvgFoodScore() %></li>
          <li>Service: <%= restaurant.getAvgServiceScore() %></li>
          <li>Ambiance: <%= restaurant.getAvgAmbianceScore() %></li>
          <li>Overall: <%= restaurant.getAvgOverallScore() %></li>
        </ul>
        <li id="address">Address:<%= restaurant.getAddressString() %></li>
        <li id="description">Description: <%= restaurant.getDescription() %></li>
    </ul>


    <table border="1" cellpadding="10">
      <tr>
          <td>
              <label>Reserve Table:</label>
              <form action="" method="post">
                <label>Table:</label>
                <select id="table_number" name="table_number">
                    <%
                        for (RestaurantTable table : tables) {
                    %>
                  <option value=<%= table.getTableNumber() %> ><%= table.getTableNumber() %></option>
                    <%
                        }
                    %>
                </select>
                <label>Date & Time:</label>
                <input type="datetime-local" id="date_time" name="date_time">
                <input type="hidden" name="username" value=<%= clientName %>>
                <input type="hidden" name="restaurant_id" value=<%= restaurant.getId() %>>
                <br>
                <button type="submit" name="action" value="reserve">Reserve</button>
              </form>
          </td>
      </tr>
  </table>

    <table border="1" cellpadding="10">
      <tr>
          <td>
              <label>Feedback:</label>
              <form action="" method="post">
                <label>Food Rate:</label>
                <input type="number" id="food_rate" name="food_rate" step="0.1" min="0" max="5">
                <label>Service Rate:</label>
                <input type="number" id="service_rate" name="service_rate" step="0.1" min="0" max="5">
                <label>Ambiance Rate:</label>
                <input type="number" id="ambiance_rate" name="ambiance_rate" step="0.1" min="0" max="5">
                <label>Overall Rate:</label>
                <input type="number" id="overall_rate" name="overall_rate" step="0.1" min="0" max="5">
                <br>
                <label>Comment:</label>
                <textarea name="comment"  id="" cols="30" rows="5" placeholder="Enter your comment"></textarea>
                <!-- <input type="textarea" name="comment" value="" /> -->
                <br>
                <input type="hidden" name="username" value=<%= clientName %>>
                <button type="submit" name="action" value="feedback">Submit</button>
              </form>
          </td>
      </tr>
  </table>

    


    <br>
    
    <br/>
    <table style="width: 100%; text-align: center;" border="1">
      <caption><h2>Feedbacks</h2></caption>
      <tr>
        <th>Username</th>
        <th>Comment</th>
        <th>Date</th>
        <th>Food Rate</th>
        <th>Service Rate</th>
        <th>Ambiance Rate</th>
        <th>Overall Rate</th>
      </tr>
      <%for (Review r : reviews) { %>
      <tr>
        <td><%= r.getUsername()%></td>
        <td><%= r.getComment()%></td>
        <td><%= r.getSubmitDateString()%></td>
        <td><%= r.getFoodRate()%></td>
        <td><%= r.getServiceRate()%></td>
        <td><%= r.getAmbianceRate()%></td>
        <td><%= r.getOverallRate()%></td>
      </tr>
      <%}%>
    </table>
    <br><br>
    
  </body>
</html>
<jsp:include page="footer.jsp" />