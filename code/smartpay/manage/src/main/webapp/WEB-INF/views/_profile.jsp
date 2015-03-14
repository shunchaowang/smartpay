<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>
<ul class="nav navbar pull-right">
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
                    <i class="glyphicon glyphicon-home"></i>
                    <spring:message code="user.profile.label"/>
                </a>
            </li>
            <li class="">
                <a href="${rootURL}logout">
                    <i class="glyphicon glyphicon-lock"></i>
                    <spring:message code="user.change.password.label"/>
                </a>
            </li>
            <li class="">
                <a href="${rootURL}logout">
                    <i class="glyphicon glyphicon-envelope"></i>
                    <spring:message code="user.change.email.label"/>
                </a>
            </li>
            <li class="">
                <a href="${rootURL}logout">
                    <i class="glyphicon glyphicon-off"></i>
                    <spring:message code="user.logout.label"/>
                </a>
            </li>
        </ul>
    </li>
</ul>
