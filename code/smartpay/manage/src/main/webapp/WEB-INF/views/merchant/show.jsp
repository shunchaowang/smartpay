<!DOCTYPE html>
<%@include file="../taglib.jsp" %>

<spring:message code="merchant.label" var="entity"/>

<div class="container-fluid">
    <div class="row">
        <ol class="breadcrumb">
            <li>
                <i class="glyphicon glyphicon-home"></i>
                <spring:message code="home.label"/>
            </li>
            <li>
                <i class="glyphicon glyphicon-list"></i>
                <spring:message code="manage.label" arguments="${entity}"/>
            </li>
            <li class="active">
                <i class="glyphicon glyphicon-briefcase"></i>
                <spring:message code="show.label" arguments="${entity}"/>
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
                        <td colspan="3"><spring:message code="commission.fee.label"/></td>
                    </tr>
                    <tr>
                        <td><spring:message code="category.label"/></td>
                        <td><spring:message code="type.label"/></td>
                        <td><spring:message code="value.label"/></td>
                    </tr>
                    <!-- visa -->
                    <tr>
                        <td><spring:message code="commissionVisaFee.label"/></td>
                        <td>
                            <c:if test="${not empty merchantCommand.commissionVisaFeeTypeName}">
                                ${merchantCommand.commissionVisaFeeTypeName}
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${not empty merchantCommand.commissionVisaFeeValue}">
                                ${merchantCommand.commissionVisaFeeValue}
                            </c:if>
                        </td>
                    </tr>
                    <!-- master -->
                    <tr>
                        <td><spring:message code="commissionMasterFee.label"/></td>
                        <td>
                            <c:if test="${not empty merchantCommand.commissionMasterFeeTypeName}">
                                ${merchantCommand.commissionMasterFeeTypeName}
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${not empty merchantCommand.commissionMasterFeeValue}">
                                ${merchantCommand.commissionMasterFeeValue}
                            </c:if>
                        </td>
                    </tr>
                    <!-- jcb -->
                    <tr>
                        <td><spring:message code="commissionJcbFee.label"/></td>
                        <td>
                            <c:if test="${not empty merchantCommand.commissionJcbFeeTypeName}">
                                ${merchantCommand.commissionJcbFeeTypeName}
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${not empty merchantCommand.commissionJcbFeeValue}">
                                ${merchantCommand.commissionJcbFeeValue}
                            </c:if>
                        </td>
                    </tr>
                    <!-- withdrawal security -->
                    <tr>
                        <td><spring:message code="withdrawalSecurityFee.label"/></td>
                        <td>
                            <c:if test="${not empty merchantCommand.withdrawFeeTypeName}">
                                ${merchantCommand.withdrawFeeTypeName}
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${not empty merchantCommand.withdrawFeeValue}">
                                ${merchantCommand.withdrawFeeValue}
                            </c:if>
                        </td>
                    </tr>
                </table>
            </div>
        </div>

        <!-- Withdrawal Setting -->
        <div class="row">
            <div class="col-sm-8">
                <table class="table table-bordered">
                    <tr>
                        <td colspan="2"><spring:message code="withdrawal.label"/></td>
                    </tr>
                    <c:if test="${not empty merchantCommand.withdrawSettingMinDays}">
                        <tr>
                            <td><spring:message code="withdrawalMinDays.label"/></td>
                            <td>${merchantCommand.withdrawSettingMinDays}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty merchantCommand.withdrawSettingMaxDays}">
                        <tr>
                            <td><spring:message code="withdrawalMaxDays.label"/></td>
                            <td>${merchantCommand.withdrawSettingMaxDays}</td>
                        </tr>
                    </c:if>
                </table>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-2 col-sm-offset-2">
            <a href="${rootURL}merchant/index/all">
                <button type="button" class="btn btn-default">
                    <spring:message code="action.return.label"/>
                </button>
            </a>
        </div>
    </div>
</div>
