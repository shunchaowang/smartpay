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

        <!-- define domain related vars -->
        <c:set var="merchant">
            <spring:message code='Merchant.label'/>
        </c:set>
        <c:set var="site">
            <spring:message code='Site.label'/>
        </c:set>
        <c:set var="user">
            <spring:message code='User.label'/>
        </c:set>
        <c:set var="adminUser">
            <spring:message code='AdminUser.label'/>
        </c:set>
        <c:set var="merchantAdminUser">
            <spring:message code='MerchantAdminUser.label'/>
        </c:set>
        <c:set var="order">
            <spring:message code='Order.label'/>
        </c:set>
        <c:set var="payment">
            <spring:message code='Payment.label'/>
        </c:set>

        <div class="navbar-collapse collapse navbar-responsive-collapse">
            <ul class="nav navbar-nav">

                <!-- merchant management goes here -->
                <!-- admin can view merchant list, add merchant -->
                <!-- merchant admin can view merchant detail -->
                <!-- admin menu starts -->
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <!-- show merchant management as hierarchical dropdown menu -->
                    <li class="dropdown">
                        <a class="dropdown-toggle" role="button" data-toggle="dropdown"
                           data-target="#" href="#">
                            <spring:message code="manage.label" arguments="${merchant}"/>
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li class="">
                                <a href="${rootURL}merchant">
                                    <i class="glyphicon glyphicon-th-list"></i>
                                    <spring:message code="index.label" arguments="${merchant}"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="${rootURL}merchant">
                                    <i class="glyphicon glyphicon-plus"></i>
                                    <spring:message code="create.label" arguments="${merchant}"/>
                                </a>
                            </li>
                        </ul>
                    </li>
                </sec:authorize>
                <!-- admin menu ends -->

                <!-- merchant admin menu starts -->
                <sec:authorize access="hasRole('ROLE_MERCHANT_ADMIN')">
                    <c:set var="merchantId"
                           value="<%=UserResource.getCurrentUser().getMerchant().getId()%>"/>
                    <li>
                        <a href="${rootURL}merchant/view/${merchantId}"> <!-- id here -->
                            <spring:message code="show.label" arguments="${merchant}"/>
                        </a>
                    </li>
                </sec:authorize>
                <!-- merchant admin menu ends -->

                <!-- site management goes here -->
                <!-- admin is able to view site list, approve site  -->
                <!-- merchant admin/operator is able to view site list of the merchant,
                    add site, edit site.
                -->
                <li class="dropdown">
                    <a class="dropdown-toggle" role="button" data-toggle="dropdown" data-target="#"
                       href="#">
                        <spring:message code="manage.label" arguments="${site}"/>
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li class="">
                            <a href="${rootURL}site">
                                <i class="glyphicon glyphicon-th-list"></i>
                                <spring:message code="index.label" arguments="${site}"/>
                            </a>
                        </li>
                        <!-- admin menu starts -->
                        <sec:authorize
                                access="hasAnyRole('ROLE_ADMIN', 'ROLE_MERCHANT_ADMIN')">
                            <li class="">
                                <a href="${rootURL}site/audit">
                                    <i class="glyphicon glyphicon-wrench"></i>
                                    <spring:message code="audit.label" arguments="${site}"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="${rootURL}site/audit">
                                    <i class="glyphicon glyphicon-wrench"></i>
                                    <spring:message code="freeze.label" arguments="${site}"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="${rootURL}site/audit">
                                    <i class="glyphicon glyphicon-wrench"></i>
                                    <spring:message code="approve.label" arguments="${site}"/>
                                </a>
                            </li>
                        </sec:authorize>
                        <!-- admin menu ends -->

                        <!-- merchant admin/operator starts -->
                        <sec:authorize
                                access="hasAnyRole('ROLE_MERCHANT_ADMIN', 'ROLE_MERCHANT_OPERATOR')">
                            <li class="">
                                <a href="${rootURL}site/create">
                                    <i class="glyphicon glyphicon-plus"></i>
                                    <spring:message code="create.label" arguments="${site}"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="${rootURL}site/edit">
                                    <i class="glyphicon glyphicon-pencil"></i>
                                    <spring:message code="edit.label" arguments="${site}"/>
                                </a>
                            </li>
                        </sec:authorize>
                        <!-- merchant admin/operator ends -->

                    </ul>
                </li>

                <!-- user management goes here -->
                <!-- admin menu goes here -->
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li class="dropdown">
                        <a class="dropdown-toggle" role="button" data-toggle="dropdown"
                           data-target="#" href="#">
                            <spring:message code="manage.label" arguments="${user}"/>
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li class="">
                                <a href="${rootURL}admin/user">
                                    <i class="glyphicon glyphicon-th-list"></i>
                                    <spring:message code="index.label" arguments="${user}"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="${rootURL}admin/user/createAdmin">
                                    <i class="glyphicon glyphicon-plus"></i>
                                    <spring:message code="create.label" arguments="${adminUser}"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="${rootURL}admin/user/createMerchantAdmin">
                                    <i class="glyphicon glyphicon-plus"></i>
                                    <spring:message code="create.label"
                                                    arguments="${merchantAdminUser}"/>
                                </a>
                            </li>
                            <!-- admin menu ends -->
                        </ul>
                    </li>
                </sec:authorize>
                <!-- merchant admin menu goes here -->
                <sec:authorize access="hasRole('ROLE_MERCHANT_ADMIN')">
                    <li class="dropdown">
                        <a class="dropdown-toggle" role="button" data-toggle="dropdown"
                           data-target="#" href="#">
                            <spring:message code="manage.label" arguments="${user}"/>
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li class="">
                                <a href="${rootURL}/user">
                                    <i class="glyphicon glyphicon-th-list"></i>
                                    <spring:message code="index.label" arguments="${user}"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="${rootURL}user/create">
                                    <i class="glyphicon glyphicon-plus"></i>
                                    <spring:message code="create.label" arguments="${user}"/>
                                </a>
                            </li>
                        </ul>
                    </li>
                </sec:authorize>
                <!-- merchant admin menu ends -->
                <!-- user management ends -->

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

