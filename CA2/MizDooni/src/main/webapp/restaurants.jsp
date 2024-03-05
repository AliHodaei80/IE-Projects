<jsp:include page="header.jsp" />
<%@page import="ir.ie.mizdooni.services.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ir.ie.mizdooni.models.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Restaurants</title>
</head>
<body>
    <p id="username">username: ali <a href="/">Home</a> <a href="/logout" style="color: red">Log Out</a></p>
    <br><br>
    <form action="" method="POST">
        <label>Search:</label>
        <input type="text" name="search" value="">
        <button type="submit" name="action" value="search_by_type">Search By Type</button>
        <button type="submit" name="action" value="search_by_name">Search By Name</button>
        <button type="submit" name="action" value="search_by_city">Search By City</button>
        <button type="submit" name="action" value="clear">Clear Search</button>
    </form>
    <br><br>
    <form action="" method="POST">
        <label>Sort By:</label>
        <button type="submit" name="action" value="sort_by_rate">Overall Score</button>
    </form>
    <br><br>
    <table style="width:100%; text-align:center;" border="1">
       <tr>
                   <th>Id</th>
                   <th>Name</th>
                   <th>City</th>
                   <th>Type</th>
                   <th>Time</th>
                   <th>Service Score</th>
                   <th>Food Score</th>
                   <th>Ambiance Score</th>
                   <th>Overall Score</th>
        </tr>
        <%
         ArrayList<Restaurant> restaurants = (ArrayList<Restaurant>) request.getAttribute("restaurants");
        for(Restaurant restaurant : restaurants) {
        %>
            <tr>
                <td><%= restaurant.getId() %></td>
                <td><a href="/restaurants/<%= restaurant.getId() %>"><%= restaurant.getName() %></a></td>
                <td><%= restaurant.getCity() %></td>
                <td><%= restaurant.getType() %></td>
                <td><%= restaurant.getActivityPeriod() %></td>
                <td><%= restaurant.getAvgServiceScore() %></td>
                <td><%= restaurant.getAvgFoodScore() %></td>
                <td><%= restaurant.getAvgAmbianceScore() %></td>
                <td><%= restaurant.getAvgOverallScore() %></td>
            </tr>
        <% } %>
    </table>
</body>
</html>
<jsp:include page="footer.jsp" />