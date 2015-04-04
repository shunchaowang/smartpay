<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>
<!--top-Header-menu-->
<div id="user-nav" class="navbar navbar-inverse">
    <ul class="nav">
        <li class="">
            <a href="${rootURL}logout">
                <i class="glyphicon glyphicon-off"></i>
                            <span class="text">
                <spring:message code="user.logout.label"/>
                            </span>
            </a>
        </li>
    </ul>
</div>

