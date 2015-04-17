<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="${domain}.label" var="entity"/>

<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <div class="widget-box">
                <div class="widget-title">
								<span class="icon">
									<i class="icon icon-align-justify"></i>
								</span>
                    <h5><b><spring:message code='create.label' arguments="${entity}"/></b></h5>
                </div>
                <div class="widget-content nopadding">
                    <form:form action="${rootURL}${controller}/create" method="POST"
                               commandName="merchantCommand" cssClass="form-horizontal" id="new-merchant-form">
                        <div class="control-group">
                            <h5>&nbsp;&nbsp;&nbsp;&nbsp;
                                <spring:message code="basic.info.label"/>
                            </h5>
                        </div>

                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="identity">
                                <span class="required-indicator">*</span>
                                <spring:message code="identity.label"/>
                            </label>

                            <div class="controls">
                                <form:input size="80" path="identity" id="identity" cssClass="text" required=""
                                            value="${merchantCommand.identity}" readonly="true"/>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="name">
                                <span class="required-indicator">*</span>
                                <spring:message code="name.label"/>
                            </label>

                            <div class="controls">
                                <form:input size="80" path="name" id="name" cssClass="text" required=""
                                            placeholder="Name"/>
                            </div>
                        </div>
                        <!-- first name -->
                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="address">
                                <spring:message code="address.label"/>
                            </label>

                            <div class="controls">
                                <form:input size="80" path="address" id="address" cssClass="text"
                                            placeholder="Address"/>
                            </div>
                        </div>
                        <!-- last name -->
                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="contact">
                                <spring:message code="contact.label"/>
                            </label>

                            <div class="controls">
                                <form:input size="80" path="contact" id="contact" cssClass="text"
                                            placeholder="Contact"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="tel">
                                <spring:message code="tel.label"/>
                            </label>

                            <div class="controls">
                                <form:input size="80" path="tel" id="tel" cssClass="text"
                                            placeholder="Telephone"/>
                            </div>
                        </div>
                        <!-- email -->
                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="email">
                                <spring:message code="email.label"/>
                            </label>

                            <div class="controls">
                                <form:input size="80" path="email" id="email" cssClass="text"
                                            placeholder="Email"/>
                            </div>
                        </div>
                        <!-- merchant status -->
                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="merchantStatusId">
                                <span class="required-indicator">*</span>
                                <spring:message code="status.label"/>
                            </label>

                            <div class="controls">
                                <form:select path="merchantStatusId" id="merchantStatusId" cssClass="text"
                                             required="" placeholder="Status">
                                    <c:forEach items="${merchantStatuses}" var="status">
                                        <form:option value="${status.id}">${status.name}</form:option>
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
                                <form:textarea cols="100" rows="5"  path="remark" id="remark"/>
                            </div>
                        </div>

                        <div class="control-group">
                            <h5>&nbsp;&nbsp;&nbsp;&nbsp;
                                <spring:message code="credential.label"/>
                            </h5>
                        </div>

                        <!-- relationships -->
                        <!-- credential -->
                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="credentialContent">
                                <span class="required-indicator">*</span>
                                <spring:message code="content.label"/>
                            </label>

                            <div class="controls">
                                <form:input size="80" path="credentialContent" id="credentialContent"
                                            cssClass="text" required=""
                                            placeholder="Content"/>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="credentialExpirationTime">
                                <span class="required-indicator">*</span>
                                <spring:message code="validation.label"/>
                            </label>

                            <div class="controls">
                                <form:input size="80" path="credentialExpirationTime" id="credentialExpirationTime"
                                            cssClass="text datepicker" required="" readonly="true"
                                            style="background:white;"/>
                            </div>
                        </div>
                        <!-- credential type -->
                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="credentialTypeId">
                                <span class="required-indicator">*</span>
                                <spring:message code="type.label"/>
                            </label>

                            <div class="controls">
                                <form:select path="credentialTypeId" id="credentialTypeId" cssClass="text"
                                             required="" placeholder="Type">
                                    <c:forEach items="${credentialTypes}" var="type">
                                        <form:option value="${type.id}">${type.name}</form:option>
                                    </c:forEach>
                                </form:select>
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
                                    <c:forEach items="${credentialStatuses}" var="status">
                                        <form:option value="${status.id}">${status.name}</form:option>
                                    </c:forEach>
                                </form:select>
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
                                <span class="required-indicator">*</span>
                                <spring:message code="key.label"/>
                            </label>

                            <div class="controls">
                                <form:input size="80" path="encryptionKey" id="encryptionKey" cssClass="text"
                                            placeholder="Key"/>
                            </div>
                        </div>
                        <!-- merchant status -->
                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="encryptionTypeId">
                                <span class="required-indicator">*</span>
                                <spring:message code="type.label"/>
                            </label>

                            <div class="controls">
                                <form:select path="encryptionTypeId" id="encryptionTypeId" cssClass="text"
                                             required="" placeholder="Type">
                                    <c:forEach items="${encryptionTypes}" var="type">
                                        <form:option value="${type.id}">${type.name}</form:option>
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
                                <span class="required-indicator">*</span>
                                <spring:message code="value.label"/>
                            </label>

                            <div class="controls">
                                <form:input size="80" path="commissionFeeValue" id="commissionFeeValue"
                                            cssClass="text"
                                            placeholder="Value"/>
                            </div>
                        </div>
                        <!-- merchant status -->
                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="commissionFeeTypeId">
                                <span class="required-indicator">*</span>
                                <spring:message code="type.label"/>
                            </label>

                            <div class="controls">
                                <form:select path="commissionFeeTypeId" id="commissionFeeTypeId"
                                             cssClass="text"
                                             required="" placeholder="Type">
                                    <c:forEach items="${feeTypes}" var="type">
                                        <form:option value="${type.id}">${type.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>

                        <!-- return fee -->
                        <hr/>
                        <h5>&nbsp;&nbsp;&nbsp;&nbsp;
                            <spring:message code="return.fee.label"/>
                        </h5>

                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="returnFeeValue">
                                <span class="required-indicator">*</span>
                                <spring:message code="value.label"/>
                            </label>

                            <div class="controls">
                                <form:input size="80" path="returnFeeValue" id="returnFeeValue" cssClass="text"
                                            placeholder="Value"/>
                            </div>
                        </div>
                        <!-- merchant status -->
                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="returnFeeTypeId">
                                <span class="required-indicator">*</span>
                                <spring:message code="type.label"/>
                            </label>

                            <div class="controls">
                                <form:select path="returnFeeTypeId" id="returnFeeTypeId"
                                             cssClass="text"
                                             required="" placeholder="Type">
                                    <c:forEach items="${feeTypes}" var="type">
                                        <form:option value="${type.id}">${type.name}</form:option>
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


<script type="text/javascript">
    $(document).ready(function () {
        $('#new-merchant-form').validate({
            rules: {
                indentity: {required: true, minlength: 3, maxlength: 32},
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