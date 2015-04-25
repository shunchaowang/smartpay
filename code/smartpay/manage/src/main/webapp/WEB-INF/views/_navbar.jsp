<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>

<div id="sidebar">
    <sec:authorize access="isAuthenticated()">
        <!-- navbar -->

        <!-- define domain related vars -->
        <spring:message code='Merchant.label' var="merchant"/>
        <spring:message code='AdminOperator.label' var="adminOperator"/>
        <spring:message code='Credential.label' var="merchantCredential"/>
        <spring:message code='Transaction.label' var="merchantTransaction"/>
        <spring:message code='Site.label' var="site"/>
        <spring:message code='User.label' var="user"/>
        <spring:message code='manage.admin.label' var="admin"/>
        <spring:message code='ecs.admin.label' var="ecsadmin"/>
        <spring:message code='MerchantAdmin.label' var="merchantAdmin"/>
        <spring:message code='transaction.label' var="transaction"/>
        <spring:message code='Order.label' var="order"/>
        <spring:message code='Payment.label' var="payment"/>
        <spring:message code='Shipment.label' var="shipment"/>
        <spring:message code='Refund.label' var="refund"/>
        <spring:message code='Complain.label' var="complain"/>
        <spring:message code='PaymentRefused.label' var="paymentRefused"/>


        <ul>

            <li>
                <a href="${rootURL}"><i class="icon icon-home"></i>
                            <span>
                    <spring:message code="home.label"/>
                            </span>
                </a>
            </li>

            <!-- show merchant management as hierarchical dropdown menu -->
            <li class="dropdown">
                <a class="dropdown-toggle" role="button" data-toggle="dropdown"
                   data-target="#" href="#"><i class="icon icon-th-list"></i>
                             <span>
                            <spring:message code="manage.label" arguments="${merchant}"/>
                            <b class="caret"></b>
                             </span>
                </a>
                <ul class="dropdown-menu" role="menu">
                    <li class="">
                        <a href="${rootURL}merchant/create">
                                <span> >>
                                <spring:message code="create.label"
                                                arguments="${merchant}"/>
                                </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}merchant/indexAll">
                                <span> >>
                                <spring:message code="manage.label"
                                                arguments="${merchant}"/>
                                </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}merchant/indexNormal">
                                <span> >>
                                <spring:message code="freeze.label" arguments="${merchant}"/>
                                                            </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}merchant/indexFrozen">
                                <span> >>
                                <spring:message code="unfreeze.label" arguments="${merchant}"/>
                                                                                        </span>
                        </a>
                    </li>
                </ul>
            </li>

            <!-- admin menu ends -->

            <!-- site management goes here -->
            <!-- admin is able to view site list, approve site  -->
            <!-- merchant admin/operator is able to view site list of the merchant,
                add site, edit site.
            -->
            <li class="dropdown">
                <a class="dropdown-toggle" role="button" data-toggle="dropdown" data-target="#"
                   href="#"><i class="icon icon-th-list"></i>
                   <span>
                        <spring:message code="manage.label" arguments="${site}"/>
                    <b class="caret"></b>
                    </span>
                </a>
                <ul class="dropdown-menu" role="menu">
                    <!-- admin menu starts -->
                    <li class="">
                        <a href="${rootURL}site/indexAll">
                                <span> >>
                                <spring:message code="manage.label" arguments="${site}"/>
                                                                                       </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}site/indexCreated">
                                <span> >>
                                <spring:message code="audit.label" arguments="${site}"/>
                                                               </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}site/indexApproved">
                                <span> >>
                                <spring:message code="freeze.label" arguments="${site}"/>
                                                                </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}site/indexFrozen">
                                <span> >>
                                <spring:message code="unfreeze.label" arguments="${site}"/>
                                                                 </span>
                        </a>
                    </li>
                </ul>
            </li>

            <!-- user management goes here -->
            <!-- admin menu goes here -->
            <li class="dropdown">
                <a class="dropdown-toggle" role="button" data-toggle="dropdown"
                   data-target="#" href="#"><i class="icon icon-th-list"></i>
                        <span>
                        <spring:message code="manage.label" arguments="${user}"/>
                        <b class="caret"></b>
                            </span>
                </a>
                <ul class="dropdown-menu" role="menu">
                    <sec:authorize access="hasRole('ROLE_ADMIN')">

                        <li class="">
                            <a href="${rootURL}user/createAdminOperator">
                                <span> >>
                                <spring:message code="create.label" arguments="${adminOperator}"/>
                                                                      </span>
                            </a>
                        </li>
                        <li class="">
                            <a href="${rootURL}user/indexAdminOperator">
                                <span> >>
                                <spring:message code="index.label" arguments="${adminOperator}"/>
                                </span>
                            </a>
                        </li>
                    </sec:authorize>
                    <li class="">
                        <a href="${rootURL}user/createMerchantAdmin">
                                <span> >>
                                <spring:message code="create.label"
                                                arguments="${merchantAdmin}"/>
                                                                   </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}user/indexMerchantAdmin">
                                <span> >>
                                <spring:message code="index.label" arguments="${merchantAdmin}"/>
                                </span>
                        </a>
                    </li>

                    <!-- admin menu ends -->
                </ul>
            </li>
            <!-- user management ends -->

            <li class="dropdown">
                <a class="dropdown-toggle" role="button" data-toggle="dropdown"
                   data-target="#" href="#"><i class="icon icon-th-list"></i>
                        <span>
                        <spring:message code="transaction.label"/>
                        <b class="caret"></b>
                            </span>
                </a>
                <ul class="dropdown-menu" role="menu">
                    <li class="">
                        <a href="${rootURL}order/index">
                                <span> >>
                                    <spring:message code="info.label" arguments="${order}"/>
                                </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}order/search">
                                <span> >>
                                    <spring:message code="search.label" arguments="${order}"/>
                                </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}payment/index">
                                <span> >>
                                    <spring:message code="info.label" arguments="${payment}"/>
                                </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="${rootURL}payment/search">
                                <span> >>
                                    <spring:message code="search.label" arguments="${payment}"/>
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
                        <a href="${rootURL}refund/indexAll">
                                <span> >>
                                    <spring:message code="info.label" arguments="${refund}"/>
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
                    <li class="">
                        <a href="${rootURL}refund/indexInitiated">
                                <span> >>
                                    <spring:message code="manage.label" arguments="${refund}"/>
                                </span>
                        </a>
                    </li>
                    <!-- query claim -->
                    <li class="">
                        <a href="${rootURL}claim/indexResolved">
                                <span> >>
                                    <spring:message code="info.label" arguments="${paymentRefused}"/>
                                </span>
                        </a>
                    </li>
                    <!-- manage claim  -->
                    <li class="">
                        <a href="${rootURL}claim/indexApproved">
                                <span> >>
                                    <spring:message code="manage.label" arguments="${paymentRefused}"/>
                                </span>
                        </a>
                    </li>
                    <!-- resolve claim -->
                    <li class="">
                        <a href="${rootURL}claim/indexProcess">
                                <span> >>
                                    <spring:message code="audit.label" arguments="${paymentRefused}"/>
                                </span>
                        </a>
                    </li>
                </ul>
            </li>

            <li class="dropdown">
                <a class="dropdown-toggle" role="button" data-toggle="dropdown"
                   data-target="#" href="#"><i class="icon icon-th-list"></i>
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


    </sec:authorize>

    <!-- end navbar-collapse -->

</div>
<!-- end container-fluid -->
