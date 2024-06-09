<jsp:include page="header.jsp" />
<head>
    <meta charset="UTF-8">
    <title>400 Error</title>
</head>
<body style="text-align:center">
    <h1>400<br> <%= request.getAttribute("error") %></h1>
</body>
<jsp:include page="footer.jsp" />