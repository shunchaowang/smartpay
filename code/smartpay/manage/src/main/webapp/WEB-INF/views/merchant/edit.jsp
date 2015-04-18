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
            <a href="${rootURL}${controller}/indexAll">
                <spring:message code="manage.label" arguments="${entity}"/>
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
                        <form:form action="${rootURL}${controller}/edit" method="POST"
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
                                    <span class="required-indicator">*</span>
                                    <spring:message code="identity.label"/>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="identity" id="identity"
                                                cssClass="text" required=""
                                                placeholder="Identity"
                                                value="${merchantCommand.identity}"
                                                readonly="true"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="name">
                                    <span class="required-indicator">*</span>
                                    <spring:message code="name.label"/>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="name" id="name" cssClass="text"
                                                required=""
                                                placeholder="Name" value="${merchantCommand.name}"/>
                                </div>
                            </div>
                            <!-- first name -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="address">
                                    <spring:message code="address.label"/>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="address" id="address"
                                                cssClass="text"
                                                placeholder="Address"
                                                value="${merchantCommand.address}"/>
                                </div>
                            </div>
                            <!-- last name -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="contact">
                                    <spring:message code="contact.label"/>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="contact" id="contact"
                                                cssClass="text"
                                                placeholder="Contact"
                                                value="${merchantCommand.contact}"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="tel">
                                    <spring:message code="tel.label"/>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="tel" id="tel" cssClass="text"
                                                placeholder="Telephone"
                                                value="${merchantCommand.tel}"/>
                                </div>
                            </div>
                            <!-- email -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="email">
                                    <spring:message code="email.label"/>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="email" id="email" cssClass="text"
                                                placeholder="Email"
                                                value="${merchantCommand.email}"/>
                                </div>
                            </div>
                            <!-- merchant status -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="merchantStatusId">
                                    <span class="required-indicator">*</span>
                                    <spring:message code="status.label"/>
                                </label>

                                <div class="controls">
                                    <form:select path="merchantStatusId" id="merchantStatusId"
                                                 cssClass="text"
                                                 required="" placeholder="Status">
                                        <form:option
                                                value="${merchantCommand.merchantStatusId}">${merchantCommand.merchantStatusName}</form:option>
                                        <c:forEach items="${merchantStatuses}" var="status">
                                            <c:if test="${!(merchantCommand.merchantStatusId.equals(status.id))}">
                                                <form:option
                                                        value="${status.id}">${status.name}</form:option>
                                            </c:if>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>
                            <!-- remark -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="remark">
                                    <spring:message code="remark.label"/>
                                </label>

                                <div class="controls">
                                    <form:textarea cols="100" rows="5" path="remark" id="remark"
                                                   value="${merchantCommand.remark}"/>
                                </div>
                            </div>


                            <!-- relationships -->
                            <div class="control-group">

                                <h5>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <spring:message code="credential.label"/>
                                </h5>
                            </div>

                            <!-- credential type -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="credentialTypeId">
                                    <span class="required-indicator">*</span>
                                    <spring:message code="type.label"/>
                                </label>

                                <div class="controls">
                                    <form:select path="credentialTypeId" id="credentialTypeId"
                                                 cssClass="text"
                                                 required="" placeholder="Type">
                                        <form:option
                                                value="${merchantCommand.credentialTypeId}">${merchantCommand.credentialTypeName}</form:option>
                                        <c:forEach items="${credentialTypes}" var="type">
                                            <c:if test="${!(merchantCommand.credentialTypeId.equals(type.id))}">
                                                <form:option
                                                        value="${type.id}">${type.name}</form:option>
                                            </c:if>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>
                            <!-- credential -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="credentialContent">
                                    <span class="required-indicator">*</span>
                                    <spring:message code="content.label"/>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="credentialContent"
                                                id="credentialContent"
                                                cssClass="text" required=""
                                                placeholder="Content"
                                                value="${merchantCommand.credentialContent}"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="col-sm-3 control-label"
                                       for="credentialExpirationTime">
                                    <span class="required-indicator">*</span>
                                    <spring:message code="validation.label"/>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="credentialExpirationTime"
                                                id="credentialExpirationTime"
                                                cssClass="text datepicker" required=""
                                                readonly="true"
                                                style="background:white;"
                                                value="${merchantCommand.credentialExpirationTime}"/>
                                </div>
                            </div>

                            <!-- credential status -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="credentialStatusId">
                                    <span class="required-indicator">*</span>
                                    <spring:message code="status.label"/>
                                </label>

                                <div class="controls">
                                    <form:select path="credentialStatusId" id="credentialStatusId"
                                                 cssClass="text"
                                                 required="" placeholder="Status">
                                        <form:option
                                                value="${merchantCommand.credentialStatusId}">${merchantCommand.credentialStatusName}</form:option>
                                        <c:forEach items="${credentialStatuses}" var="status">
                                            <c:if test="${!(merchantCommand.credentialStatusId.equals(status.id))}">
                                                <form:option
                                                        value="${status.id}">${status.name}</form:option>
                                            </c:if>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>

                            <!-- ~~~~~~~~~~~~~~~~~~ Buttons ~~~~~~~~~~~~~~~~~~ -->
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