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
    <!-- navbar -->

    <sec:authorize access="isAuthenticated()">
        <!-- define domain related vars -->
        <spring:message code='Merchant.label' var="merchant"/>
        <spring:message code='Credential.label' var="merchantCredential"/>
        <spring:message code='Transaction.label' var="merchantTransaction"/>
        <spring:message code='Site.label' var="site"/>
        <spring:message code='User.label' var="user"/>
        <spring:message code='Admin.label' var="admin"/>
        <spring:message code='MerchantAdmin.label' var="merchantAdmin"/>
        <spring:message code='Order.label' var="order"/>
        <spring:message code='Payment.label' var="payment"/>

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
                                <a href="${rootURL}merchant/create">
                                    <i class="glyphicon glyphicon-plus"></i>
                                    <spring:message code="create.label"
                                                    arguments="${merchant}"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="${rootURL}merchant/">
                                    <i class="glyphicon glyphicon-th-list"></i>
                                    <spring:message code="merchant.list.label" arguments="${merchant}"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="${rootURL}merchant/indexMerchantEdit">
                                    <i class="glyphicon glyphicon-th-list"></i>
                                    <spring:message code="merchant.manage.label" arguments="${merchant}"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="${rootURL}merchant/indexMerchantFee">
                                    <i class="glyphicon glyphicon-th-list"></i>
                                    <spring:message code="merchant.setfee.label"
                                                    arguments="${merchant}"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="${rootURL}merchant/freeze">
                                    <i class="glyphicon glyphicon-th-list"></i>
                                    <spring:message code="freeze.label" arguments="${merchant}"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="${rootURL}merchant/unfreeze">
                                    <i class="glyphicon glyphicon-th-list"></i>
                                    <spring:message code="unfreeze.label" arguments="${merchant}"/>
                                </a>
                            </li>
                        </ul>
                    </li>
                </sec:authorize>
                <!-- admin menu ends -->

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
                        <!-- admin menu starts -->
                        <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                            <li class="">
                                <a href="${rootURL}site">
                                    <i class="glyphicon glyphicon-th-list"></i>
                                    <spring:message code="index.label" arguments="${site}"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="${rootURL}site/showAuditList">
                                    <i class="glyphicon glyphicon-wrench"></i>
                                    <spring:message code="audit.label" arguments="${site}"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="${rootURL}site/showFreezeList">
                                    <i class="glyphicon glyphicon-wrench"></i>
                                    <spring:message code="freeze.label" arguments="${site}"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="${rootURL}site/showUnfreezeList">
                                    <i class="glyphicon glyphicon-wrench"></i>
                                    <spring:message code="unfreeze.label" arguments="${site}"/>
                                </a>
                            </li>
                            <!-- TODO this should be on ecs side -->
                            <li class="">
                                <a href="${rootURL}site/create">
                                    <i class="glyphicon glyphicon-wrench"></i>
                                    <spring:message code="create.label" arguments="${site}"/>
                                </a>
                            </li>
                        </sec:authorize>
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
                                <a href="${rootURL}user/indexAdmin">
                                    <i class="glyphicon glyphicon-th-list"></i>
                                    <spring:message code="index.label" arguments="${admin}"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="${rootURL}user/indexMerchantAdmin">
                                    <i class="glyphicon glyphicon-th-list"></i>
                                    <spring:message code="index.label"
                                                    arguments="${merchantAdmin}"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="${rootURL}user/createAdmin">
                                    <i class="glyphicon glyphicon-plus"></i>
                                    <spring:message code="create.label" arguments="${admin}"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="${rootURL}user/createMerchantAdmin">
                                    <i class="glyphicon glyphicon-plus"></i>
                                    <spring:message code="create.label"
                                                    arguments="${merchantAdmin}"/>
                                </a>
                            </li>
                            <!-- admin menu ends -->
                        </ul>
                    </li>
                </sec:authorize>
                <!-- user management ends -->

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

