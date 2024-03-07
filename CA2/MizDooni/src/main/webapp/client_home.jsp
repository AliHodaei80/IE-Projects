<jsp:include page="header.jsp" />
<%@page import="ir.ie.mizdooni.services.*"%>
<head>
    <meta charset="UTF-8" />
    <title>Client Home</title>
  </head>
  <body>
      <%
          String clientName = (String) request.getAttribute("username");
      %>
    <h1>
      Welcome <%= clientName %> <a href="/logout" style="color: red">Log Out</a>
    </h1>

    <ul type="square">
      <li>
        <a href="/restaurants">Restaurants</a>
      </li>
      <li>
        <a href="/reservations">Reservations</a>
      </li>
    </ul>
  </body>
<jsp:include page="footer.jsp" />