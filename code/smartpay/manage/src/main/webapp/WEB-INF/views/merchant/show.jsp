<!DOCTYPE html>
<%@include file="../taglib.jsp" %>

<spring:message code="merchant.label" var="entity"/>


<div class="container-fluid">
    <div class="row">
        <ol class="breadcrumb">
            <li>
                <a href="${rootURL}">
                    <i class="glyphicon glyphicon-home"></i>
                    <spring:message code="home.label"/>
                </a>
            </li>
            <li>
                <a href="${rootURL}merchant/index">
                    <i class="glyphicon glyphicon-list"></i>
                    <spring:message code="manage.label" arguments="${entity}"/>
                </a>
            </li>
            <li class="active">
                <a href="${rootURL}merchant/show">
                    <i class="glyphicon glyphicon-briefcase"></i>
                    <spring:message code="show.label" arguments="${entity}"/>
                </a>
            </li>
        </ol>
    </div>
    <div class="row">
        <div class="row">
            <div class="col-sm-8">
                <table class="table table-bordered">
                    <tr>
                        <td colspan="2"><spring:message code="basic.info.label"/></td>
                    </tr>
                    <c:if test="${not empty merchantCommand.identity}">
                        <tr>
                            <td><spring:message code="identity.label"/></td>
                            <td>${merchantCommand.identity}</td>
                        </tr>
                    </c:if>
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

                    <c:if test="${not empty merchantCommand.merchantStatusName}">
                        <tr>
                            <td><spring:message code="status.label"/></td>
                            <td>${merchantCommand.merchantStatusName}</td>
                        </tr>
                    </c:if>

                    <c:if test="${not empty merchantCommand.remark}">
                        <tr>
                            <td><spring:message code="remark.label"/></td>
                            <td>${merchantCommand.remark}</td>
                        </tr>
                    </c:if>
                </table>
            </div>
        </div>

        <!-- Credential -->
        <div class="row">
            <div class="col-sm-8">
                <table class="table table-bordered">
                    <tr>
                        <td colspan="2"><spring:message code="credential.label"/></td>
                    </tr>
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

        <!-- Encryption -->
        <div class="row">
            <div class="col-sm-8">
                <table class="table table-bordered">
                    <tr>
                        <td colspan="2"><spring:message
                                code="encryption.label"/></td>
                    </tr>
                    <c:if test="${not empty merchantCommand.encryptionTypeName}">
                        <tr>
                            <td><spring:message code="type.label"/></td>
                            <td>${merchantCommand.encryptionTypeName}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty merchantCommand.encryptionKey}">
                        <tr>
                            <td><spring:message code="key.label"/></td>
                            <td>${merchantCommand.encryptionKey}</td>
                        </tr>
                    </c:if>
                </table>
            </div>
        </div>

        <!-- Commission Fee -->
        <div class="row">
            <div class="col-sm-8">
                <table class="table table-bordered">
                    <tr>
                        <td colspan="2"><spring:message
                                code="commission.fee.label"/></td>
                    </tr>
                    <c:if test="${not empty merchantCommand.commissionFeeTypeName}">
                        <tr>
                            <td><spring:message code="type.label"/></td>
                            <td>${merchantCommand.commissionFeeTypeName}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty merchantCommand.commissionFeeValue}">
                        <tr>
                            <td><spring:message code="value.label"/></td>
                            <td>${merchantCommand.commissionFeeValue}</td>
                        </tr>
                    </c:if>
                </table>
            </div>
        </div>

        <!-- Return Fee -->
        <div class="row">
            <div class="col-sm-8">
                <table class="table table-bordered">
                    <tr>
                        <td colspan="2"><spring:message
                                code="return.fee.label"/></td>
                    </tr>
                    <c:if test="${not empty merchantCommand.returnFeeTypeName}">
                        <tr>
                            <td><spring:message code="type.label"/></td>
                            <td>${merchantCommand.returnFeeTypeName}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty merchantCommand.returnFeeValue}">
                        <tr>
                            <td><spring:message code="value.label"/></td>
                            <td>${merchantCommand.returnFeeValue}</td>
                        </tr>
                    </c:if>
                </table>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-2 col-sm-offset-2">
            <a href="${rootURL}merchant/index">
                <button type="button" class="btn btn-default">
                    <spring:message code="action.return.label"/>
                </button>
            </a>
        </div>
    </div>
</div>
