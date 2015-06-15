<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>

<sec:authorize access="isAuthenticated()">
    <!-- navbar -->

    <!-- define domain related vars -->
    <spring:message code='merchant.label' var="merchant"/>
    <spring:message code='operator.label' var="operator"/>
    <spring:message code='credential.label' var="credential"/>
    <spring:message code='transaction.label' var="transaction"/>
    <spring:message code='site.label' var="site"/>
    <spring:message code='user.label' var="user"/>
    <spring:message code='admin.label' var="admin"/>
    <spring:message code='merchantAdmin.label' var="merchantAdmin"/>
    <spring:message code='merchantOperator.label' var="merchantOperator"/>
    <spring:message code='transaction.label' var="transaction"/>
    <spring:message code='order.label' var="order"/>
    <spring:message code='payment.label' var="payment"/>
    <spring:message code='shipment.label' var="shipment"/>
    <spring:message code='refund.label' var="refund"/>
    <spring:message code='complain.label' var="complain"/>
    <spring:message code='refuse.label' var="refuse"/>
    <spring:message code='announcement.label' var="announcement"/>
    <spring:message code='log.label' var="log"/>
    <spring:message code='system.label' var="system"/>
    <spring:message code='permission.label' var="permission"/>

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
                                <a href="${rootURL}user/permission">
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

</sec:authorize>

<!-- end navbar-collapse -->


