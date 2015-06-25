<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>


<sec:authorize access="isAuthenticated()">
    <!-- navbar -->

    <!-- define domain related vars -->
    <spring:message code='merchant.label' var="merchant"/>
    <spring:message code='site.label' var="site"/>
    <spring:message code='user.label' var="user"/>
    <spring:message code='order.label' var="order"/>
    <spring:message code='payment.label' var="payment"/>
    <spring:message code='operator.label' var="operator"/>
    <spring:message code='refund.label' var="refund"/>
    <spring:message code='shipment.label' var="shipment"/>
    <spring:message code='currency.label' var="currency"/>
    <spring:message code='complain.label' var="complain"/>
    <spring:message code='refuse.label' var="refuse"/>



    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${rootURL}">
                    <!--<img alt="Brand" src="">-->
                    <spring:message code="application.name"/>
                </a>
            </div>
            <!-- end of navbar header -->

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="navbar-collapse">
                <ul class="nav navbar-nav">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-expanded="false">
                            <spring:message code="manage.label" arguments="${merchant}"/>
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li>
                                <a href="${rootURL}merchant/index/all">
                                    <spring:message code="manage.label" arguments="${merchant}"/>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="${rootURL}merchant/index/setting">
                                    <spring:message code="manage.label" arguments="${transaction}"/>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="${rootURL}merchant/index/archive">
                                    <spring:message code="archive.label" arguments="${merchant}"/>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <!-- end of merchant management -->

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-expanded="false">
                            <spring:message code="manage.label" arguments="${site}"/>
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li>
                                <a href="${rootURL}site/index/all">
                                    <spring:message code="manage.label" arguments="${site}"/>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="${rootURL}site/index/archive">
                                    <spring:message code="archive.label" arguments="${site}"/>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <!-- end of site management -->

                    <!-- user management -->
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-expanded="false">
                            <spring:message code="manage.label" arguments="${user}"/>
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li>
                                <a href="${rootURL}user/index/operator">
                                    <spring:message code="manage.label" arguments="${operator}"/>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="${rootURL}user/index/merchantAdmin">
                                    <spring:message code="manage.label"
                                                    arguments="${merchantAdmin}"/>
                                </a>
                            </li>
                            <li>
                                <a href="${rootURL}user/index/merchantOperator">
                                    <spring:message code="manage.label"
                                                    arguments="${merchantOperator}"/>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="${rootURL}user/manage/permission">
                                    <spring:message code="manage.label" arguments="${permission}"/>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="${rootURL}user/index/archive">
                                    <spring:message code="archive.label" arguments="${user}"/>
                                </a>
                            </li>
                        </ul>
                    </li>

                    <!-- order management -->
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-expanded="false">
                            <spring:message code="manage.label" arguments="${order}"/>
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li>
                                <a href="${rootURL}order/index/all">
                                    <spring:message code="manage.label" arguments="${order}"/>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="${rootURL}shipment/index/all">
                                    <spring:message code="manage.label" arguments="${shipment}"/>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="${rootURL}order/index/archive">
                                    <spring:message code="archive.label" arguments="${order}"/>
                                </a>
                            </li>
                            <li>
                                <a href="${rootURL}shipment/index/archive">
                                    <spring:message code="archive.label" arguments="${shipment}"/>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <!-- payment management -->
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-expanded="false">
                            <spring:message code="manage.label" arguments="${payment}"/>
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li>
                                <a href="${rootURL}payment/index/all">
                                    <spring:message code="manage.label" arguments="${payment}"/>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="${rootURL}refund/index/all">
                                    <spring:message code="manage.label" arguments="${refund}"/>
                                </a>
                            </li>
                            <li>
                                <a href="${rootURL}refund/audit">
                                    <spring:message code="audit.label" arguments="${refund}"/>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="${rootURL}payment/index/archive">
                                    <spring:message code="archive.label" arguments="${payment}"/>
                                </a>
                            </li>
                            <li>
                                <a href="${rootURL}refund/index/archive">
                                    <spring:message code="archive.label" arguments="${refund}"/>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <!-- claim management -->
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-expanded="false">
                            <spring:message code="manage.label" arguments="${complain}"/>
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li>
                                <a href="${rootURL}claim/index/all">
                                    <spring:message code="manage.label" arguments="${refuse}"/>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="${rootURL}claim/audit">
                                    <spring:message code="audit.label" arguments="${refuse}"/>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="${rootURL}claim/index/archive">
                                    <spring:message code="archive.label" arguments="${refuse}"/>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <!-- system management -->
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-expanded="false">
                            <spring:message code="manage.label" arguments="${system}"/>
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li>
                                <a href="${rootURL}announcement/index/all">
                                    <spring:message code="manage.label"
                                                    arguments="${announcement}"/>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="${rootURL}log/index">
                                    <spring:message code="index.label" arguments="${log}"/>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="${rootURL}announcement/index/archive">
                                    <spring:message code="archive.label"
                                                    arguments="${announcement}"/>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <!-- system management -->
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-expanded="false">
                            <spring:message code="statistics.label"/>
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li>
                                <a href="${rootURL}search/index/all">
                                    <spring:message code="manage.label"
                                                    arguments="${announcement}"/>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="${rootURL}search/index">
                                    <spring:message code="index.label" arguments="${log}"/>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="${rootURL}search/index/archive">
                                    <spring:message code="archive.label"
                                                    arguments="${announcement}"/>
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a class="dropdown-toggle" role="button" data-toggle="dropdown"
                           data-target="#" href="#">
                            <!-- TODO: Only show menu items based on permissions (e.g., Guest has no account page) -->
                            <i class="icon icon-user"></i>
                            <sec:authentication property="principal.username"/>
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li>
                                <a href="${rootURL}logout">
                                    <i class="icon icon-off"></i>
                                    <spring:message code="user.logout.label"/>
                                </a>
                            </li>
                            <li>
                                <a href="${rootURL}changePassword">
                                    <spring:message code="user.change.password.label"/>
                                </a>
                            </li>
                            <li>
                                <a href="${rootURL}profile">
                                    <spring:message code="user.profile.label"/>
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
                <!-- end of user profile -->
            </div>
            <!-- end of navbar-collapse -->
        </div>
        <!-- end of container-fluid -->
    </nav>


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
<!-- end container-fluid -->

