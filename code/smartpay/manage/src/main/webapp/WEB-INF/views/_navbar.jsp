<%@ page import="com.lambo.smartpay.manage.web.controller.UserResource" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>
<div class="container-fluid">
    <sec:authorize access="isAuthenticated()">
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
        <!-- navbar -->
        <div class="navbar-collapse collapse navbar-responsive-collapse">
            <ul class="nav navbar-nav">
                <sec:authorize ifAnyGranted="ROLE_ADMIN">
                    <li>
                        <a href="${rootURL}merchant/list">
                            <spring:message code="merchant.label"/>
                        </a>
                    </li>
                </sec:authorize>
                <sec:authorize ifAnyGranted="ROLE_MERCHANT_ADMIN">
                    <c:set var="merchantId"
                           value="<%=UserResource.getCurrentUser().getMerchant().getId()%>"/>
                    <li>
                        <a href="${rootURL}merchant/view/${merchantId}"> <!-- id here -->
                            <spring:message code="merchant.detail.label"/>
                        </a>
                    </li>
                </sec:authorize>
                <li>
                    <a href="${rootURL}site/list">
                        <spring:message code="site.label"/>
                    </a>
                </li>
                <sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MERCHANT_ROLE">
                    <li>
                        <a href="${rootURL}user/list">
                            <spring:message code="user.label"/>
                        </a>
                    </li>
                </sec:authorize>
                <li>
                    <a href="${rootURL}order/list">
                        <spring:message code="order.label"/>
                    </a>
                </li>
                <li>
                    <a href="${rootURL}payment/list">
                        <spring:message code="payment.label"/>
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

