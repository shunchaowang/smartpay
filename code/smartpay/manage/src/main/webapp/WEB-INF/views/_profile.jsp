<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>
<li class="dropdown">
    <a class="dropdown-toggle" role="button" data-toggle="dropdown" data-target="#" href="#">
        <!-- TODO: Only show menu items based on permissions (e.g., Guest has no account page) -->
        <i class="glyphicon glyphicon-user icon-white"></i>
        <sec:authentication property="principal.username"/>
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu" role="menu">
        <li class="">
            <a href="${rootURL}logout">
                <i class="glyphicon glyphicon-off"></i>
                <spring:message code="user.logout.label"/>
            </a>
        </li>
    </ul>
    <%----%>
</li>
