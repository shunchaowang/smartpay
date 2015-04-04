<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>
<div id="sidebar">
    <sec:authorize access="isAuthenticated()">
        <!-- navbar -->

        <!-- define domain related vars -->
        <spring:message code='Merchant.label' var="merchant"/>
        <spring:message code='Site.label' var="site"/>
        <spring:message code='User.label' var="user"/>
        <spring:message code='Order.label' var="order"/>
        <spring:message code='Payment.label' var="payment"/>
        <spring:message code='operator.label' var="operator"/>


        <ul>

            <!-- merchant management goes here -->
            <!-- merchant admin can view merchant detail -->
            <!-- merchant operator cannot see merchant nav -->
            <!-- merchant admin menu starts -->
            <sec:authorize access="hasRole('ROLE_MERCHANT_ADMIN')">
                <li class="dropdown">
                    <a class="dropdown-toggle" role="button" data-toggle="dropdown" data-target="#"
                       href="#">
                            <span>
                            <spring:message code="manage.label" arguments="${merchant}"/>
                            <b class="caret"></b>
                            </span>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li class="">
                            <a href="${rootURL}merchant">
                            <span>
                                <spring:message code="show.label" arguments="${merchant}"/>
                            </span>
                            </a>
                            <a href="${rootURL}user">
                                <spring:message code="show.label" arguments="${operator}"/>
                            </a>
                            <a href="${rootURL}site">
                            <span>
                                <spring:message code="show.label" arguments="${site}"/>
                            </span>
                            </a>
                        </li>
                    </ul>

                </li>
            </sec:authorize>
            <!-- merchant admin menu ends -->

            <li class="dropdown">
                <a class="dropdown-toggle" role="button" data-toggle="dropdown" data-target="#"
                   href="#">
                            <span>
                    <spring:message code="manage.label" arguments="${merchant}"/>
                    <b class="caret"></b>
                            </span>
                </a>
                <ul class="dropdown-menu" role="menu">
                    <li class="">
                        <a href="${rootURL}user/create">
                            <i class="glyphicon glyphicon-plus"></i>
                            <span>
                            <spring:message code="create.label" arguments="${operator}"/>
                            </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}user/index">
                            <i class="glyphicon glyphicon-th-list"></i>
                            <span>
                            <spring:message code="manage.label" arguments="${operator}"/>
                            </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}merchant/edit">
                            <i class="glyphicon glyphicon-th-list"></i>
                            <span>
                            <spring:message code="manage.label" arguments="${merchant}"/>
                            </span>
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
                            <span>
                    <spring:message code="manage.label" arguments="${site}"/>
                            </span>
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu" role="menu">
                    <li class="">
                        <a href="${rootURL}site/create">
                            <i class="glyphicon glyphicon-plus"></i>
                            <span>
                            <spring:message code="create.label" arguments="${site}"/>
                            </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}site/index">
                            <i class="glyphicon glyphicon-th-list"></i>
                            <span>
                            <spring:message code="manage.label" arguments="${site}"/>
                            </span>
                        </a>
                    </li>
                </ul>
            </li>


            <li>
                <a href="${rootURL}order">
                            <span>
                    <spring:message code="manage.label" arguments="${order}"/>
                            </span>
                </a>
            </li>
            <li>
                <a href="${rootURL}payment">
                            <span>
                    <spring:message code="manage.label" arguments="${payment}"/>
                            </span>
                </a>
            </li>
        </ul>

        <!-- end navbar-collapse -->
    </sec:authorize>
</div>
<!-- end container-fluid -->

