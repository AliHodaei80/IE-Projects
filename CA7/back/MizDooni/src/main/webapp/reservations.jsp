<jsp:include page="header.jsp" />
<%@page import="ir.ie.mizdooni.services.*"%>
<%@ page import="ir.ie.mizdooni.models.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.stream.Collectors" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reservations</title>
</head>
<body>
        <%
              String clientName = (String) request.getAttribute("username");
              List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
              Map<String, Object> restaurantsIds = (Map<String, Object>) request.getAttribute("restaurantIds");
          %>
    <p id="username">username: <%= clientName %> <a href="/">Home</a> <a href="/logout" style="color: red">Log Out</a></p>
    <h1>Your Reservations:</h1>
    <br><br>
    <br><br>
    <table style="width:100%; text-align:center;" border="1">
        <tr>
            <th>Reservation Id</th>
            <th>Resturant Name</th>
            <th>Table Number</th>
            <th>Date & Time</th>
            <th>Canceling</th>
        </tr>
        <%
                for(Reservation reservation : reservations) {
                %>
        <tr>
            <td><%= reservation.getReservationId() %></td>
            <td><a href="/restaurants/<%= restaurantsIds.get(reservation.getRestaurantName()) %>"><%= reservation.getRestaurantName() %></a></td>
            <td><%= reservation.getTableNumber() %></td>
            <td><%= reservation.getDatetimeString() %></td>
            <td>
                <form action="" method="POST">
                    <input type="hidden" name="username" value=<%= clientName %>>
                    <button type="submit" name="action" value="<%= reservation.getReservationId() %>">Cancel This</button>
                </form>
            </td>
        </tr>
        <%
                }
        %>
    </table>
</body>
 <jsp:include page="footer.jsp" />