<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>

<div id="sidebar">
    <sec:authorize access="isAuthenticated()">
        <!-- navbar -->


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

        <ul>
            <sec:authorize access="hasRole('ROLE_ADMIN')">

                <li>
                    <a href="${rootURL}"><i class="icon icon-home"></i>
                            <span>
                    <spring:message code="home.label"/>
                            </span>
                    </a>
                </li>
            </sec:authorize>

            <!-- merchant management goes here -->
            <!-- admin can view merchant list, add merchant -->
            <!-- merchant admin can view merchant detail -->
            <!-- admin menu starts -->
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <!-- show merchant management as hierarchical dropdown menu -->
                <li class="dropdown">
                    <a class="dropdown-toggle" role="button" data-toggle="dropdown"
                       data-target="#" href="#">
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
                            <a href="${rootURL}merchant/">
                                <span> >>
                                <spring:message code="merchant.list.label" arguments="${merchant}"/>
                                                            </span>
                            </a>
                        </li>
                        <li class="">
                            <a href="${rootURL}merchant/indexMerchantEdit">
                                <span> >>
                                <spring:message code="merchant.manage.label" arguments="${merchant}"/>
                                                                                        </span>
                            </a>
                        </li>
                        <li class="">
                            <a href="${rootURL}merchant/indexMerchantFee">
                                <span> >>
                                <spring:message code="merchant.setfee.label"
                                                arguments="${merchant}"/>
                                 </span>
                            </a>
                        </li>
                        <li class="">
                            <a href="${rootURL}merchant/indexFreezeList">
                                <span> >>
                                <spring:message code="freeze.label" arguments="${merchant}"/>
                                                            </span>
                            </a>
                        </li>
                        <li class="">
                            <a href="${rootURL}merchant/indexUnfreezeList">
                                <span> >>
                                <spring:message code="unfreeze.label" arguments="${merchant}"/>
                                                                                        </span>
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
                   <span>
 <spring:message code="manage.label" arguments="${site}"/>
                    <b class="caret"></b>
                    </span>
                </a>
                <ul class="dropdown-menu" role="menu">
                    <!-- admin menu starts -->
                    <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                        <li class="">
                            <a href="${rootURL}site/create">
                                <span> >>
                                <spring:message code="create.label" arguments="${site}"/>
                                                                                       </span>
                            </a>
                        </li>
                        <li class="">
                            <a href="${rootURL}site">
                                <span> >>
                                <spring:message code="index.label" arguments="${site}"/>
                                                                                       </span>
                            </a>
                        </li>
                        <li class="">
                            <a href="${rootURL}site/indexAuditList">
                                <span> >>
                                <spring:message code="audit.label" arguments="${site}"/>
                                                               </span>
                            </a>
                        </li>
                        <li class="">
                            <a href="${rootURL}site/indexFreezeList">
                                <span> >>
                                <spring:message code="freeze.label" arguments="${site}"/>
                                                                </span>
                            </a>
                        </li>
                        <li class="">
                            <a href="${rootURL}site/indexUnfreezeList">
                                <span> >>
                                <spring:message code="unfreeze.label" arguments="${site}"/>
                                                                 </span>
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
                        <span>
                        <spring:message code="manage.label" arguments="${user}"/>
                        <b class="caret"></b>
                            </span>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li class="">
                            <a href="${rootURL}user/indexAdmin">
                                <span> >>
                                <spring:message code="index.label" arguments="${admin}"/>
                                                                                            </span>
                            </a>
                        </li>
                        <li class="">
                            <a href="${rootURL}user/indexMerchantAdmin">
                                <span> >>
                                <spring:message code="index.label"
                                                arguments="${merchantAdmin}"/>
                                                                                           </span>
                            </a>
                        </li>
                        <li class="">
                            <a href="${rootURL}user/createAdmin">
                                <span> >>
                                <spring:message code="create.label" arguments="${admin}"/>
                                                                      </span>
                            </a>
                        </li>
                        <li class="">
                            <a href="${rootURL}user/createMerchantAdmin">
                                <span> >>
                                <spring:message code="create.label"
                                                arguments="${merchantAdmin}"/>
                                                                   </span>
                            </a>
                        </li>
                        <!-- admin menu ends -->
                    </ul>
                </li>
            </sec:authorize>
            <!-- user management ends -->

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
