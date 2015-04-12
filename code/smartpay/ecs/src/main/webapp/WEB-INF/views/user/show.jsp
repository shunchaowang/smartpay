<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="${domain}.label" var="entity"/>

<div id="content">
    <div id="content-header">
        <div id="breadcrumb">
            <a href="${rootURL}">
                <i class="icon icon-home"></i>
                <spring:message code="home.label"/>
            </a>
            <c:if test="${domain != null}">
                <spring:message code="${domain}.label" var="entity"/>
                <a href="${rootURL}${controller}">
                    <spring:message code="manage.label" arguments="${entity}"/>
                </a>
                <a href="${rootURL}${controller}/${action}" class="current">
                    <spring:message code="${action}.label" arguments="${entity}"/>
                </a>
            </c:if>
        </div>
    </div>
    <!-- reserved for notification -->
    <!-- close of content-header -->
    <div class="container-fluid">
        <!— actual content —>
        <div class='row'>
            <div class='col-sm-4'>
                <h2><b>
                    <spring:message code='show.label' arguments="${entity}"/>
                </b></h2>
            </div>
        </div>
        <br>

        <div class="row">
            <div class="col-sm-8">

                <table class="table table-bordered">
                    <c:if test="${not empty userCommand.username}">
                        <tr>
                            <td><spring:message code="username.label"/></td>
                            <td>${userCommand.username}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty userCommand.firstName}">
                        <tr>
                            <td><spring:message code="firstName.label"/></td>
                            <td>${userCommand.firstName}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty userCommand.lastName}">
                        <tr>
                            <td><spring:message code="lastName.label"/></td>
                            <td>${userCommand.lastName}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty userCommand.email}">
                        <tr>
                            <td><spring:message code="email.label"/></td>
                            <td>${userCommand.email}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty userCommand.userStatusName}">
                        <tr>
                            <td><spring:message code="status.label"/></td>
                            <td>${userCommand.userStatusName}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty userCommand.createdTime}">
                        <tr>
                            <td><spring:message code="createdTime.label"/></td>
                            <td>${userCommand.createdTime}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty userCommand.remark}">
                        <tr>
                            <td><spring:message code="remark.label"/></td>
                            <td>${userCommand.remark}</td>
                        </tr>
                    </c:if>
                </table>
            </div>
        </div>
        <!-- button area -->

        <div class="row">
            <div class="col-sm-2 col-sm-offset-2">
                <a href="${rootURL}${controller}">
                    <button type="button" class="btn btn-default">
                        <spring:message code="action.return.label"/>
                    </button>
                </a>
            </div>
        </div>
    </div>
</div>
