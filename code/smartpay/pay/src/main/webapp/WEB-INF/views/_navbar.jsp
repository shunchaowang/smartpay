<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>
<div class="container-fluid">
    <div class='navbar-header'>
        <button class="navbar-toggle" data-target=".navbar-responsive-collapse"
                data-toggle="collapse" type="button">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <!-- end collapse buttons -->
        <a class="navbar-brand" href="${rootURL}">
            <spring:message code="application.name"/>
        </a>
    </div>
    <!-- end of navbar-header -->
    <sec:authorize access="isAuthenticated()">
        <!-- navbar -->
        <spring:message code='Order.label' var="order"/>
        <spring:message code='Payment.label' var="payment"/>

        <div class="navbar-collapse collapse navbar-responsive-collapse">
            <ul class="nav navbar-nav">

                <!-- merchant management goes here -->
                <!-- admin can view merchant list, add merchant -->
                <!-- merchant admin can view merchant detail -->
                <!-- admin menu starts -->
                <li>
                    <a href="${rootURL}order/list">
                        <spring:message code="manage.label" arguments="${order}"/>
                    </a>
                </li>
                <li>
                    <a href="${rootURL}payment/list">
                        <spring:message code="manage.label" arguments="${payment}"/>
                    </a>
                </li>
            </ul>
            <!-- include profile here -->

            <jsp:include page="_profile.jsp"/>

        </div>
        <!-- end navbar-collapse -->
    </sec:authorize>
</div>
<!-- end container-fluid -->

