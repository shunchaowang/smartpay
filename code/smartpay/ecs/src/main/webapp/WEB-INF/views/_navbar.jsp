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

        <!-- define domain related vars -->
        <spring:message code='Merchant.label' var="merchant"/>
        <spring:message code='Site.label' var="site"/>
        <spring:message code='User.label' var="user"/>
        <spring:message code='Order.label' var="order"/>
        <spring:message code='Payment.label' var="payment"/>
        <spring:message code='operator.label' var="operator"/>


        <div class="navbar-collapse collapse navbar-responsive-collapse">
            <ul class="nav navbar-nav">

                <!-- merchant management goes here -->
                <!-- merchant admin can view merchant detail -->
                <!-- merchant operator cannot see merchant nav -->
                <!-- merchant admin menu starts -->
                <sec:authorize access="hasRole('ROLE_MERCHANT_ADMIN')">
                    <li class="dropdown">
                        <a class="dropdown-toggle" role="button" data-toggle="dropdown" data-target="#"
                           href="#">
                            <spring:message code="manage.label" arguments="${merchant}"/>
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li class="">
                                <a href="${rootURL}merchant">
                                    <spring:message code="show.label" arguments="${merchant}"/>
                                </a>
                                <a href="${rootURL}user">
                                    <spring:message code="show.label" arguments="${operator}"/>
                                </a>
                                <a href="${rootURL}site">
                                    <spring:message code="show.label" arguments="${site}"/>
                                </a>
                            </li>
                        </ul>

                    </li>
                </sec:authorize>
                <!-- merchant admin menu ends -->

                <li class="dropdown">
                    <a class="dropdown-toggle" role="button" data-toggle="dropdown" data-target="#"
                       href="#">
                        <spring:message code="manage.label" arguments="${merchant}"/>
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li class="">
                            <a href="${rootURL}user/create">
                                <i class="glyphicon glyphicon-plus"></i>
                                <spring:message code="create.label" arguments="${operator}"/>
                            </a>
                        </li>
                        <li class="">
                            <a href="${rootURL}user/index">
                                <i class="glyphicon glyphicon-th-list"></i>
                                <spring:message code="manage.label" arguments="${operator}"/>
                            </a>
                        </li>
                        <li class="">
                            <a href="${rootURL}merchant/edit">
                                <i class="glyphicon glyphicon-th-list"></i>
                                <spring:message code="manage.label" arguments="${merchant}"/>
                            </a>
                        </li>

                    </ul>
                </li>


                <!-- site management goes here -->
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
                            <a href="${rootURL}site/create">
                                <i class="glyphicon glyphicon-plus"></i>
                                <spring:message code="create.label" arguments="${site}"/>
                            </a>
                        </li>
                        <li class="">
                            <a href="${rootURL}site/index">
                                <i class="glyphicon glyphicon-th-list"></i>
                                <spring:message code="manage.label" arguments="${site}"/>
                            </a>
                        </li>
                    </ul>
                </li>


                <li>
                    <a href="${rootURL}order">
                        <spring:message code="manage.label" arguments="${order}"/>
                    </a>
                </li>
                <li>
                    <a href="${rootURL}payment">
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

