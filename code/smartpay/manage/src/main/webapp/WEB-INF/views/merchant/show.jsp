<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
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
            <c:if test="${not empty merchantCommand.name}">
                <tr>
                    <td><spring:message code="name.label"/></td>
                    <td>${merchantCommand.name}</td>
                </tr>
            </c:if>
            <c:if test="${not empty merchantCommand.address}">
                <tr>
                    <td><spring:message code="address.label"/></td>
                    <td>${merchantCommand.address}</td>
                </tr>
            </c:if>
            <c:if test="${not empty merchantCommand.contact}">
                <tr>
                    <td><spring:message code="contact.label"/></td>
                    <td>${merchantCommand.contact}</td>
                </tr>
            </c:if>
            <c:if test="${not empty merchantCommand.tel}">
                <tr>
                    <td><spring:message code="tel.label"/></td>
                    <td>${merchantCommand.tel}</td>
                </tr>
            </c:if>
            <c:if test="${not empty merchantCommand.email}">
                <tr>
                    <td><spring:message code="email.label"/></td>
                    <td>${merchantCommand.email}</td>
                </tr>
            </c:if>
            <!--
            <c:if test="${not empty merchantCommand.merchantStatusName}">
                <tr>
                    <td><spring:message code="status.label"/></td>
                    <td>${merchantCommand.merchantStatusName}</td>
                </tr>
            </c:if>
            -->
            <c:if test="${not empty merchantCommand.remark}">
                <tr>
                    <td><spring:message code="remark.label"/></td>
                    <td>${merchantCommand.remark}</td>
                </tr>
            </c:if>
        </table>
    </div>

    <!-- Credential -->
    <div class="col-sm-8">
        <table class="table table-bordered">
            <c:if test="${not empty merchantCommand.credentialTypeName}">
                <tr>
                    <td><spring:message code="type.label"/></td>
                    <td>${merchantCommand.credentialTypeName}</td>
                </tr>
            </c:if>
            <c:if test="${not empty merchantCommand.credentialContent}">
                <tr>
                    <td><spring:message code="content.label"/></td>
                    <td>${merchantCommand.credentialContent}</td>
                </tr>
            </c:if>
            <c:if test="${not empty merchantCommand.credentialExpirationTime}">
                <tr>
                    <td><spring:message code="validation.label"/></td>
                    <td>${merchantCommand.credentialExpirationTime}</td>
                </tr>
            </c:if>
            <c:if test="${not empty merchantCommand.credentialStatusName}">
                <tr>
                    <td><spring:message code="status.label"/></td>
                    <td>${merchantCommand.credentialStatusName}</td>
                </tr>
            </c:if>
        </table>
    </div>
</div>
<!-- button area -->

<div class="row">
    <div class="col-sm-2 col-sm-offset-2">
        <a href="${rootURL}${controller}/index${domain}">
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
