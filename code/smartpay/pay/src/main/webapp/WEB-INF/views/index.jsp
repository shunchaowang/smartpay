<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.lambo.smartpay.pay.web.controller.UserResource" %>
<!DOCTYPE html>
<%@include file="taglib.jsp" %>

<h2>Welcome to Pay</h2>

<h2>Controller: ${controller}</h2>

<h2>Action: ${action}</h2>

<h2>View: ${_view}</h2>
<sec:authorize access="isAuthenticated()">
    <c:set var="username" value="<%=UserResource.getCurrentUser().getUsername()%>" scope="session"/>


    <h2>Authorities <%=UserResource.getCurrentUser().getAuthorities()%>
    </h2>

    <h3>Name: <sec:authentication property="name"/></h3>

    <p><a href="${rootURL}logout">Logout</a></p>
</sec:authorize>