<%@ page import="com.lambo.smartpay.manage.controller.UserResource" %>
<!DOCTYPE html>
<%@include file="taglib.jsp" %>
<html>
<head>
    <jsp:include page="_import.jsp"/>
</head>
<body>

<c:set var="username" value="<%=UserResource.getCurrentUser().getUsername()%>" scope="session"/>
<h2>Welcome ${username}
</h2>

<h2>Authorities <%=UserResource.getCurrentUser().getAuthorities()%>
</h2>

<h3>Name: <sec:authentication property="name"/></h3>

<h3>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <a href="${rootURL}admin">Administration</a>
    </sec:authorize>
</h3>

<a href="${rootURL}user/list">User Table</a><br/>
<a href="${rootURL}user/json">User in Json Array</a>

<p><a href="${rootURL}logout">Logout</a></p>
</body>