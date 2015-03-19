<!DOCTYPE html>
<%@include file="../../taglib.jsp" %>

<div class='row' id='notification'>
    <c:if test="${not empty message}">
        <div class="alert alert-danger alert-dismissable" role="alert">
            <button type="button" class="close" data-dismiss="alert">
                <span aria-hidden="true">&times;</span>
                <span class="sr-only"><spring:message code="close.button.label"/> </span>
            </button>
                ${message}
        </div>
    </c:if>
</div>
<!-- end of notification -->
<br/>

<div class='row'>
    <div class='col-sm-4'>
        <h2><b>
            <spring:message code='user.detail.label'/>
        </b></h2>
    </div>
</div>
<br>

<div class="row">
    <div class="col-sm-8">

        <table class="table table-bordered">
            <c:if test="${not empty userCommand.username}">
                <tr>
                    <td><spring:message code="user.username.label"/></td>
                    <td>${userCommand.username}</td>
                </tr>
            </c:if>
            <c:if test="${not empty userCommand.firstName}">
                <tr>
                    <td><spring:message code="user.firstName.label"/></td>
                    <td>${userCommand.firstName}</td>
                </tr>
            </c:if>
            <c:if test="${not empty userCommand.lastName}">
                <tr>
                    <td><spring:message code="user.lastName.label"/></td>
                    <td>${userCommand.lastName}</td>
                </tr>
            </c:if>
            <c:if test="${not empty userCommand.email}">
                <tr>
                    <td><spring:message code="user.email.label"/></td>
                    <td>${userCommand.email}</td>
                </tr>
            </c:if>
            <c:if test="${not empty userCommand.userStatusName}">
                <tr>
                    <td><spring:message code="user.userStatus.label"/></td>
                    <td>${userCommand.userStatusName}</td>
                </tr>
            </c:if>
            <!--
            <c:if test="${not empty userCommand.active}">
                <tr>
                    <td><spring:message code="active.label"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${userCommand.active}">
                                <spring:message code="yes.label"/>
                            </c:when>
                            <c:otherwise>
                                <spring:message code="no.label"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:if>
            -->
            <c:if test="${not empty userCommand.remark}">
                <tr>
                    <td><spring:message code="user.remark.label"/></td>
                    <td>${userCommand.remark}</td>
                </tr>
            </c:if>
            <c:if test="${not empty userCommand.merchantName}">
                <tr>
                    <td><spring:message code="merchant.label"/></td>
                    <td>${userCommand.merchantName}</td>
                </tr>
            </c:if>
            <c:if test="${not empty userCommand.createdTime}">
                <tr>
                    <td><spring:message code="user.createdTime.label"/></td>
                    <td><fmt:formatDate value="${userCommand.createdTime}"
                                        pattern="MMM dd yyyy"/></td>
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
                <spring:message code="return.label"/>
            </button>
        </a>
    </div>
    <div class="col-sm-2">
        <a href="${rootURL}${controller}/edit/${userCommand.id}">
            <button type="button" class="btn btn-default">
                <spring:message code="edit.label"/>
            </button>
        </a>
    </div>
</div>
