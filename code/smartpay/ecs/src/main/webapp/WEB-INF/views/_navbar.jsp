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
        <spring:message code='Return.label' var="return"/>
        <spring:message code='Shipping.label' var="shipping"/>




        <ul>
            <!--    HOME    -->
            <sec:authorize access="hasRole('ROLE_MERCHANT_ADMIN')">
                <li>
                    <a href="${rootURL}"><i class="icon icon-home"></i>
                            <span>
                    <spring:message code="home.label"/>
                            </span>
                    </a>
                </li>
            </sec:authorize>

            <!-- merchant admin menu ends -->

            <li class="dropdown">
                <a class="dropdown-toggle" role="button" data-toggle="dropdown" data-target="#"
                   href="#"><i class="icon icon-refresh"></i>
                            <span>
                    <spring:message code="transaction.label"/>
                    <b class="caret"></b>
                            </span>
                </a>
                <ul class="dropdown-menu" role="menu">
                    <li class="">
                        <a href="${rootURL}payment/index">
                            <span> >>
                            <spring:message code="payment.search.label"/>
                            </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}order/index">
                            <span> >>
                            <spring:message code="order.search.label"/>
                            </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}shipment/shipping">
                            <span> >>
                            <spring:message code="shipping.manage.label"/>
                            </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}refund/refund">
                            <span> >>
                            <spring:message code="return.manage.label"/>
                            </span>
                        </a>
                    </li>
                </ul>
            </li>

            <sec:authorize access="hasRole('ROLE_MERCHANT_ADMIN')">
                <li class="dropdown">
                    <a class="dropdown-toggle" role="button" data-toggle="dropdown" data-target="#"
                       href="#"><i class="icon icon-pencil"></i>
                            <span>
                            <spring:message code="manage.label" arguments="${site}"/>
                            <b class="caret"></b>
                            </span>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li class="">
                            <a href="${rootURL}site/create">
                            <span> >>
                            <spring:message code="create.label" arguments="${site}"/>
                            </span>
                            </a>
                        </li>
                        <li class="">
                            <a href="${rootURL}site/index">
                            <span> >>
                            <spring:message code="manage.label" arguments="${site}"/>
                            </span>
                            </a>
                        </li>
                    </ul>
                </li>
            </sec:authorize>


            <!-- merchant admin menu ends -->

            <li class="dropdown">
                <a class="dropdown-toggle" role="button" data-toggle="dropdown" data-target="#"
                   href="#"><i class="icon icon-search"></i>
                            <span>
                    <spring:message code="info.count.label"/>
                    <b class="caret"></b>
                            </span>
                </a>
                <ul class="dropdown-menu" role="menu">
                    <li class="">
                        <a href="${rootURL}payment/index">
                            <span> >>
                            <spring:message code="payment.search.label"/>
                            </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}order/index">
                            <span> >>
                            <spring:message code="order.search.label"/>
                            </span>
                        </a>
                    </li>
                </ul>
            </li>


            <!-- merchant management goes here -->
            <!-- merchant admin can view merchant detail -->
            <!-- merchant operator cannot see merchant nav -->
            <!-- merchant admin menu starts -->
            <sec:authorize access="hasRole('ROLE_MERCHANT_ADMIN')">
                <li class="dropdown">
                    <a class="dropdown-toggle" role="button" data-toggle="dropdown" data-target="#"
                       href="#"><i class="icon icon-pencil"></i>
                            <span>
                            <spring:message code="manage.label" arguments="${merchant}"/>
                            <b class="caret"></b>
                            </span>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li class="">
                            <a href="${rootURL}user/create">
                            <span> >>
                            <spring:message code="create.label" arguments="${operator}"/>
                            </span>
                            </a>
                        </li>
                        <li class="">
                            <a href="${rootURL}user/index">
                            <span> >>
                            <spring:message code="manage.label" arguments="${operator}"/>
                            </span>
                            </a>
                        </li>
                        <li class="">
                            <a href="${rootURL}merchant/edit">
                            <span> >>
                            <spring:message code="merchant.edit.label" arguments="${merchant}"/>
                            </span>
                            </a>
                        </li>
                    </ul>
                </li>
            </sec:authorize>


            <!-- merchant admin menu ends -->

            <li class="dropdown">
                <a class="dropdown-toggle" role="button" data-toggle="dropdown" data-target="#"
                   href="#"><i class="icon icon-cog"></i>
                            <span>
                    <spring:message code="user.setting.label"/>
                    <b class="caret"></b>
                            </span>
                </a>
                <ul class="dropdown-menu" role="menu">
                    <li class="">
                        <a href="${rootURL}changePassword">
                            <span> >>
                            <spring:message code="user.change.password.label"/>
                            </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}user/edit/0">
                            <span> >>
                            <spring:message code="user.profile.label"/>
                            </span>
                        </a>
                    </li>
                </ul>
            </li>
        </ul>

        <!-- end navbar-collapse -->
    </sec:authorize>
</div>
<!-- end container-fluid -->

