<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="${domain}.label" var="entity"/>
<spring:message code="Merchant.label" var="merchant"/>
<div id="content">
    <div id="content-header">
        <div id="breadcrumb">
            <a href="${rootURL}">
                <i class="icon icon-home"></i>
                <spring:message code="home.label"/>
            </a>
            <a href="${rootURL}${controller}/indexAll">
                <spring:message code="manage.label" arguments="${merchant}"/>
            </a>
            <a href="${rootURL}${controller}/${action}" class="current">
                <spring:message code="edit.label" arguments="${entity}"/>
            </a>
        </div>
    </div>
    <!-- close of content-header -->
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">
                <div class="widget-box">
                    <div class="widget-title">
								<span class="icon">
									<i class="icon icon-align-justify"></i>
								</span>
                        <h5><b><spring:message code='edit.label' arguments="${entity}"/></b></h5>
                    </div>
                    <div class="widget-content nopadding">
                        <form:form action="${rootURL}${controller}/editFee" method="POST"
                                   commandName="merchantCommand" cssClass="form-horizontal"
                                   id="new-merchant-form">
                            <div class="control-group">

                                <h5>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <spring:message code="basic.info.label"/>
                                </h5>
                            </div>

                            <form:hidden path="id" id="id" cssClass="text" required=""
                                         placeholder="Id" value="${merchantCommand.id}"/>

                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="identity">
                                    <spring:message code="identity.label"/>
                                    <span class="required-indicator">*</span>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="identity" id="identity"
                                                cssClass="text"
                                                required=""
                                                placeholder="Identity"
                                                value="${merchantCommand.identity}"
                                                readonly="true"/>
                                </div>
                            </div>

                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="name">
                                    <spring:message code="name.label"/>
                                    <span class="required-indicator">*</span>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="name" id="name" cssClass="text"
                                                required=""
                                                placeholder="Name" value="${merchantCommand.name}"
                                                readonly="true"/>
                                </div>
                            </div>

                            <!-- encryption -->
                            <div class="control-group">
                                <h5>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <spring:message code="encryption.label"/>
                                </h5>
                            </div>

                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="encryptionKey">
                                    <spring:message code="key.label"/>
                                    <span class="required-indicator">*</span>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="encryptionKey" id="encryptionKey"
                                                cssClass="text"
                                                placeholder="Key"
                                                value="${merchantCommand.encryptionKey}"/>
                                </div>
                            </div>
                            <!-- merchant status -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="encryptionTypeId">
                                    <spring:message code="type.label"/>
                                    <span class="required-indicator">*</span>
                                </label>

                                <div class="controls">
                                    <form:select path="encryptionTypeId" id="encryptionTypeId"
                                                 cssClass="text"
                                                 required="" placeholder="Type">
                                        <form:option
                                                value="${merchantCommand.encryptionTypeId}">${merchantCommand.encryptionTypeName}</form:option>
                                        <c:forEach items="${encryptionTypes}" var="type">
                                            <c:if test="${!(merchantCommand.encryptionTypeId.equals(type.id))}">
                                                <form:option
                                                        value="${type.id}">${type.name}</form:option>
                                            </c:if>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>

                            <!-- commission fee -->
                            <div class="control-group">
                                <h5>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <spring:message code="commission.fee.label"/>
                                </h5>
                            </div>

                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="commissionFeeValue">
                                    <spring:message code="value.label"/>
                                    <span class="required-indicator">*</span>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="commissionFeeValue"
                                                id="commissionFeeValue"
                                                cssClass="text"
                                                placeholder="Value"
                                                value="${merchantCommand.commissionFeeValue}"/>
                                </div>
                            </div>
                            <!-- merchant status -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="commissionFeeTypeId">
                                    <spring:message code="type.label"/>
                                    <span class="required-indicator">*</span>
                                </label>

                                <div class="controls">
                                    <form:select path="commissionFeeTypeId" id="commissionFeeTypeId"
                                                 cssClass="text"
                                                 required="" placeholder="Type">
                                        <form:option
                                                value="${merchantCommand.commissionFeeTypeId}">${merchantCommand.commissionFeeTypeName}</form:option>
                                        <c:forEach items="${feeTypes}" var="type">
                                            <c:if test="${!(merchantCommand.commissionFeeTypeId.equals(type.id))}">
                                                <form:option
                                                        value="${type.id}">${type.name}</form:option>
                                            </c:if>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>

                            <!-- return fee -->
                            <div class="control-group">
                                <h5>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <spring:message code="return.fee.label"/>
                                </h5>
                            </div>

                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="returnFeeValue">
                                    <spring:message code="value.label"/>
                                    <span class="required-indicator">*</span>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="returnFeeValue" id="returnFeeValue"
                                                cssClass="text"
                                                placeholder="Value"
                                                value="${merchantCommand.returnFeeValue}"/>
                                </div>
                            </div>
                            <!-- merchant status -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="returnFeeTypeId">
                                    <spring:message code="type.label"/>
                                    <span class="required-indicator">*</span>
                                </label>

                                <div class="controls">
                                    <form:select path="returnFeeTypeId" id="returnFeeTypeId"
                                                 cssClass="text"
                                                 required="" placeholder="Type">
                                        <form:option
                                                value="${merchantCommand.returnFeeTypeId}">${merchantCommand.returnFeeTypeName}</form:option>
                                        <c:forEach items="${feeTypes}" var="type">
                                            <c:if test="${!(merchantCommand.returnFeeTypeId.equals(type.id))}">
                                                <form:option
                                                        value="${type.id}">${type.name}</form:option>
                                            </c:if>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>

                            <div class='form-actions col-lg-offset-2'>

                                <button class='btn btn-success' id='create-button' type="submit">
                                    <spring:message code='action.save.label'/>
                                </button>
                                <button class='btn btn-success' id='reset-button' type="reset">
                                    <spring:message code='action.reset.label'/>
                                </button>
                            </div>

                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#new-merchant-form').validate({
            rules: {
                name: {required: true, minlength: 3, maxlength: 32},
                email: {email: true},
                tel: {number: true},
                merchantStatus: {required: true},
                credentialContent: {required: true, minlength: 3, maxlength: 32},
                credentialExpirationTime: {required: true, date: true},
                credentialTypeId: {required: true},
                credentialStatusId: {required: true},
                encryptionKey: {required: true, number: true, minlength: 3, maxlength: 32},
                encryptionTypeId: {required: true},
                commissionFeeValue: {required: true, number: true},
                commissionFeeTypeId: {required: true},
                returnFeeValue: {required: true, number: true},
                returnFeeTypeId: {required: true}
            }
        });

        $('.datepicker').datepicker();
    });
</script>