<!DOCTYPE html>
<%@include file="../../taglib.jsp" %>
<spring:message code="${domain}.label" var="entity"/>

<div class='row' id='notification'>
    <c:if test="${not empty message}">
        <div class="alert alert-danger alert-dismissable" role="alert">
            <button type="button" class="close" data-dismiss="alert">
                <span aria-hidden="true">&times;</span>
                <span class="sr-only"><spring:message code="action.close.label"/> </span>
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
            <spring:message code='show.label' arguments="${entity}"/>
        </b></h2>
    </div>
</div>
<br>

<div class="row">
    <div class="col-sm-8">

        <table class="table table-bordered">
            <c:if test="${not empty merchantCommand.merchantname}">
                <tr>
                    <td><spring:message code="name.label"/></td>
                    <td>${merchantCommand.merchantname}</td>
                </tr>
            </c:if>
            <c:if test="${not empty merchantCommand.firstName}">
                <tr>
                    <td><spring:message code="firstName.label"/></td>
                    <td>${merchantCommand.firstName}</td>
                </tr>
            </c:if>
            <c:if test="${not empty merchantCommand.lastName}">
                <tr>
                    <td><spring:message code="lastName.label"/></td>
                    <td>${merchantCommand.lastName}</td>
                </tr>
            </c:if>
            <c:if test="${not empty merchantCommand.email}">
                <tr>
                    <td><spring:message code="email.label"/></td>
                    <td>${merchantCommand.email}</td>
                </tr>
            </c:if>
            <c:if test="${not empty merchantCommand.merchantStatusName}">
                <tr>
                    <td><spring:message code="status.label"/></td>
                    <td>${merchantCommand.merchantStatusName}</td>
                </tr>
            </c:if>
            <!--
            <c:if test="${not empty merchantCommand.active}">
                <tr>
                    <td><spring:message code="active.label"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${merchantCommand.active}">
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
            <c:if test="${not empty merchantCommand.remark}">
                <tr>
                    <td><spring:message code="remark.label"/></td>
                    <td>${merchantCommand.remark}</td>
                </tr>
            </c:if>
            <c:if test="${not empty merchantCommand.merchantName}">
                <tr>
                    <td><spring:message code="Merchant.label"/></td>
                    <td>${merchantCommand.merchantName}</td>
                </tr>
            </c:if>
            <c:if test="${not empty merchantCommand.createdTime}">
                <tr>
                    <td><spring:message code="createdTime.label"/></td>
                    <td><fmt:formatDate value="${merchantCommand.createdTime}"
                                        pattern="MMM dd yyyy"/></td>
                </tr>
            </c:if>
        </table>
    </div>
</div>
<!-- button area -->

<div class="row">
    <div class="col-sm-2 col-sm-offset-2">
        <a href="${rootURL}${controller}/">
            <button type="button" class="btn btn-default">
                <spring:message code="action.return.label"/>
            </button>
        </a>
    </div>
    <div class="col-sm-2">
        <a href="${rootURL}${controller}/edit/${merchantCommand.id}">
            <button type="button" class="btn btn-default">
                <spring:message code="action.edit.label"/>
            </button>
        </a>
    </div>
</div>
