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
        <spring:message code='Refund.label' var="refund"/>
        <spring:message code='Shipment.label' var="shipment"/>
        <spring:message code='currency.label' var="currency"/>
        <spring:message code='Complain.label' var="complain"/>
        <spring:message code='PaymentRefused.label' var="paymentRefused"/>

        <ul>
            <!--    HOME    -->
            <li>
                <a href="${rootURL}"><i class="icon icon-home"></i>
                            <span>
                    <spring:message code="home.label"/>
                            </span>
                </a>
            </li>

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
                            <spring:message code="info.label" arguments="${payment}"/>
                            </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}shipment/index">
                            <span> >>
                            <spring:message code="info.label" arguments="${shipment}"/>
                            </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}shipment/shipping">
                            <span> >>
                            <spring:message code="manage.label" arguments="${shipment}"/>
                            </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}refund/index">
                            <span> >>
                            <spring:message code="info.label" arguments="${refund}"/>
                            </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}refund/refund">
                            <span> >>
                            <spring:message code="manage.label" arguments="${refund}"/>
                            </span>
                        </a>
                    </li>
                </ul>
            </li>

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
                        <a href="${rootURL}count/currency">
                            <span> >>
                            <spring:message code="countBy.label" arguments="${currency}"/>
                            </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}count/site">
                            <span> >>
                            <spring:message code="countBy.label" arguments="${site}"/>
                            </span>
                        </a>
                    </li>
                </ul>
            </li>


            <!-- merchant management goes here -->
            <!-- merchant admin can view merchant detail -->
            <!-- merchant operator cannot see merchant nav -->
            <!-- merchant admin menu starts -->
            <li class="dropdown">
                <a class="dropdown-toggle" role="button" data-toggle="dropdown" data-target="#"
                   href="#"><i class="icon icon-pencil"></i>
                            <span>
                            <spring:message code="manage.label" arguments="${merchant}"/>
                            <b class="caret"></b>
                            </span>
                </a>

                <ul class="dropdown-menu" role="menu">
                    <sec:authorize access="hasRole('ROLE_MERCHANT_ADMIN')">

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
                    </sec:authorize>

                    <li class="">
                        <a href="${rootURL}merchant/edit">
                            <span> >>
                            <spring:message code="edit.label" arguments="${merchant}"/>
                            </span>
                        </a>
                    </li>
                </ul>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle" role="button" data-toggle="dropdown"
                   data-target="#" href="#"><i class="icon icon-th-list"></i>
                        <span>
                        <spring:message code="manage.label" arguments="${complain}"/>
                        <b class="caret"></b>
                            </span>
                </a>
                <ul class="dropdown-menu" role="menu">
                    <!-- query claim -->
                    <li class="">
                        <a href="${rootURL}claim/indexResolved">
                                <span> >>
                                    <spring:message code="info.label"
                                                    arguments="${paymentRefused}"/>
                                </span>
                        </a>
                    </li>
                    <!-- claim claim -->
                    <li class="">
                        <a href="${rootURL}claim/indexProcess">
                                <span> >>
                                    <spring:message code="claim.label"
                                                    arguments="${paymentRefused}"/>
                                </span>
                        </a>
                    </li>
                </ul>
            </li>

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
                        <a href="${rootURL}profile">
                            <span> >>
                            <spring:message code="user.profile.label"/>
                            </span>
                        </a>
                    </li>
                </ul>
            </li>
        </ul>

    </sec:authorize>

    <!-- end navbar-collapse -->
</div>
<!-- end container-fluid -->

