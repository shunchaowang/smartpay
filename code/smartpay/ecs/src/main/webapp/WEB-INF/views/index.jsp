<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.lambo.smartpay.ecs.web.controller.UserResource" %>
<!DOCTYPE html>
<%@include file="taglib.jsp" %>

<c:set var="username" value="<%=UserResource.getCurrentUser().getUsername()%>" scope="session"/>
<h2>Welcome ${username}
</h2>

<h2>Authorities <%=UserResource.getCurrentUser().getAuthorities()%>
</h2>

<h2>Controller: ${controller}</h2>

<h2>Action: ${action}</h2>

<h2>View: ${_view}</h2>

<h3>Name: <sec:authentication property="name"/></h3>

<h3>
    <sec:authorize access="hasRole('ROLE_MERCHANT_ADMIN')">
        <a href="${rootURL}admin">Administration</a>
    </sec:authorize>
</h3>

<p><a href="${rootURL}logout">Logout</a></p>
