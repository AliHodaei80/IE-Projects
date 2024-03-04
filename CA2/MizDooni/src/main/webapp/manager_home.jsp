<jsp:include page="header.jsp" />
<%@page import="ir.ie.mizdooni.services.*"%>
<%@ page import="ir.ie.mizdooni.models.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.stream.Collectors" %>
<head>
    <meta charset="UTF-8" />
    <title>Manager Home</title>
  </head>
  <body>
    <%
        String managerName = UserHandler.getInstance().getCurrentUser().getUsername();
    %>
    <h1>
      Welcome <%= managerName %> <a href="/logout" style="color: red">Log Out</a>
    </h1>

    <h2>Your Restaurant Information:</h2>
        <%
            List<Restaurant> restaurants = (List<Restaurant>) request.getAttribute("restaurants");
            Map<String, Map<Long, RestaurantTable>> restaurantsTables = (Map<String, Map<Long, RestaurantTable>>) request.getAttribute("restaurantsTables");
            for (Restaurant restaurant : restaurants) {
        %>
    <ul>

      <li id="id">Id: <%= restaurant.getId() %></li>
      <li id="name">Name: <%= restaurant.getName() %></li>
      <li id="type">Type: <%= restaurant.getType() %></li>
      <li id="time">Time: <%= restaurant.getStartTimeString() %> - <%= restaurant.getEndTimeString() %></li>
      <li id="description">Description: <%= restaurant.getDescription() %></li>
      <li id="address">Address: <%= restaurant.getAddressString() %></li>
      <li id="tables">Tables:</li>

      <ul>
        <%
    List<RestaurantTable> tables = restaurantsTables.get(restaurant.getName()) != null ? restaurantsTables.get(restaurant.getName()).values().stream().collect(Collectors.toList()) : new ArrayList<>();
            for (RestaurantTable table : tables) {
        %>
        <li>table<%= table.getTableNumber() %></li>
        <%
        }
        %>
      </ul>
      </ul>
      <table border="1" cellpadding="10">
            <tr>
              <td>
                <h3>Add Table:</h3>
                <form method="post" action="/addtable">
                  <label>Table Number:</label>
                  <input type="hidden" name="restaurant_name" value=<%= restaurant.getName() %>>
                  <input type="hidden" name="manager_name" value=<%= managerName %>>
                  <input name="table_number" type="number" min="0" />
                  <br />
                  <label>Seats Number:</label>
                  <input name="seats_number" type="number" min="1" />
                  <br />
                  <button type="submit">Add</button>
                </form>
              </td>
            </tr>
          </table>


    <%
        }
        %>
  </body>

 <jsp:include page="footer.jsp" />