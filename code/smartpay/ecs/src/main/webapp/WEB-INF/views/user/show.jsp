<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="${domain}.label" var="entity"/>

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
