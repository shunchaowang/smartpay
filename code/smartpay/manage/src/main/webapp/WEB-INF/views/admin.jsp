<!DOCTYPE html>
<%@include file="taglib.jsp" %>
<html>
<head>
    <title>Administrator</title>
    <link rel="stylesheet" href='<spring:url value="resources/vendor/stylesheets/styles.css"/>'/>
    <script type="text/javascript"
            src='<spring:url value="resources/vendor/jquery-2.1.3/jquery-2.1.3.js"/>'></script>
    <script type="text/javascript">

    </script>
</head>
<body>
<h2>Administrator Home Page</h2>

<p><a href="${rootURL}home">Home</a></p>

<p><a href="${rootURL}logout">Logout</a></p>

</body>
</html>