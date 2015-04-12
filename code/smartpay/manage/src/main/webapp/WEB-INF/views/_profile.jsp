<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>
<div id="user-nav" class="navbar navbar-inverse">
    <ul class="nav">
        <sec:authorize access="isAuthenticated()">
            <li class="dropdown">
                <a class="dropdown-toggle" role="button" data-toggle="dropdown" data-target="#" href="#">
                    <!-- TODO: Only show menu items based on permissions (e.g., Guest has no account page) -->
                    <i class="icon icon-user"></i>
                    <sec:authentication property="principal.username"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu" role="menu">
                    <li class="">
                        <a href="${rootURL}logout">
                            <i class="icon icon-off"></i>
                            <spring:message code="user.logout.label"/>
                        </a>
                    </li>
                </ul>
            </li>
        </sec:authorize>
    </ul>
</div>
