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
        <div class='row'>
            <div class='col-sm-4'>
                <h2><b><spring:message code='create.label' arguments="${entity}"/></b></h2>
            </div>
        </div>
        <br>

        <div class="row">
            <form:form action="${rootURL}${controller}/create" method="POST"
                       commandName="merchantCommand" cssClass="form-horizontal"
                       id="new-merchant-form">
                <hr/>
                <h4>
                    <spring:message code="basic.info.label"/>
                </h4>

                <div class="form-group">
                    <label class="col-sm-3 control-label" for="identity">
                        <spring:message code="identity.label"/>
                        <span class="required-indicator">*</span>
                    </label>

                    <div class="col-sm-6">
                        <form:input path="identity" id="identity" cssClass="form-control"
                                    required=""
                                    placeholder="Identity"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label" for="name">
                        <spring:message code="name.label"/>
                        <span class="required-indicator">*</span>
                    </label>

                    <div class="col-sm-6">
                        <form:input path="name" id="name" cssClass="form-control" required=""
                                    placeholder="Name"/>
                    </div>
                </div>
                <!-- first name -->
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="address">
                        <spring:message code="address.label"/>
                    </label>

                    <div class="col-sm-6">
                        <form:input path="address" id="address" cssClass="form-control"
                                    placeholder="Address"/>
                    </div>
                </div>
                <!-- last name -->
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="contact">
                        <spring:message code="contact.label"/>
                    </label>

                    <div class="col-sm-6">
                        <form:input path="contact" id="contact" cssClass="form-control"
                                    placeholder="Contact"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="tel">
                        <spring:message code="tel.label"/>
                    </label>

                    <div class="col-sm-6">
                        <form:input path="tel" id="tel" cssClass="form-control"
                                    placeholder="Telephone"/>
                    </div>
                </div>
                <!-- email -->
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="email">
                        <spring:message code="email.label"/>
                    </label>

                    <div class="col-sm-6">
                        <form:input path="email" id="email" cssClass="form-control"
                                    placeholder="Email"/>
                    </div>
                </div>
                <!-- merchant status -->
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="merchantStatusId">
                        <spring:message code="status.label"/>
                        <span class="required-indicator">*</span>
                    </label>

                    <div class="col-sm-6">
                        <form:select path="merchantStatusId" id="merchantStatusId"
                                     cssClass="form-control"
                                     required="" placeholder="Status">
                            <c:forEach items="${merchantStatuses}" var="status">
                                <form:option value="${status.id}">${status.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <!-- remark -->
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="remark">
                        <spring:message code="remark.label"/>
                    </label>

                    <div class="col-sm-6">
                        <form:input path="remark" id="remark" cssClass="form-control"/>
                    </div>
                </div>

                <hr/>
                <h4>
                    <spring:message code="credential.label"/>
                </h4>
                <!-- relationships -->
                <!-- credential -->
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="credentialContent">
                        <spring:message code="content.label"/>
                        <span class="required-indicator">*</span>
                    </label>

                    <div class="col-sm-6">
                        <form:input path="credentialContent" id="credentialContent"
                                    cssClass="form-control" required=""
                                    placeholder="Content"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label" for="credentialExpirationTime">
                        <spring:message code="validation.label"/>
                        <span class="required-indicator">*</span>
                    </label>

                    <div class="col-sm-6">
                        <form:input path="credentialExpirationTime" id="credentialExpirationTime"
                                    cssClass="form-control datepicker" required="" readonly="true"
                                    style="background:white;"/>
                    </div>
                </div>
                <!-- credential type -->
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="credentialTypeId">
                        <spring:message code="type.label"/>
                        <span class="required-indicator">*</span>
                    </label>

                    <div class="col-sm-6">
                        <form:select path="credentialTypeId" id="credentialTypeId"
                                     cssClass="form-control"
                                     required="" placeholder="Type">
                            <c:forEach items="${credentialTypes}" var="type">
                                <form:option value="${type.id}">${type.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <!-- credential status -->
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="credentialStatusId">
                        <spring:message code="status.label"/>
                        <span class="required-indicator">*</span>
                    </label>

                    <div class="col-sm-6">
                        <form:select path="credentialStatusId" id="credentialStatusId"
                                     cssClass="form-control"
                                     required="" placeholder="Status">
                            <c:forEach items="${credentialStatuses}" var="status">
                                <form:option value="${status.id}">${status.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>

                <!-- encryption -->
                <hr/>
                <h4>
                    <spring:message code="encryption.label"/>
                </h4>

                <div class="form-group">
                    <label class="col-sm-3 control-label" for="encryptionKey">
                        <spring:message code="key.label"/>
                        <span class="required-indicator">*</span>
                    </label>

                    <div class="col-sm-6">
                        <form:input path="encryptionKey" id="encryptionKey" cssClass="form-control"
                                    placeholder="Key"/>
                    </div>
                </div>
                <!-- merchant status -->
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="encryptionTypeId">
                        <spring:message code="type.label"/>
                        <span class="required-indicator">*</span>
                    </label>

                    <div class="col-sm-6">
                        <form:select path="encryptionTypeId" id="encryptionTypeId"
                                     cssClass="form-control"
                                     required="" placeholder="Type">
                            <c:forEach items="${encryptionTypes}" var="type">
                                <form:option value="${type.id}">${type.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>

                <!-- commission fee -->
                <hr/>
                <h4>
                    <spring:message code="commission.fee.label"/>
                </h4>

                <div class="form-group">
                    <label class="col-sm-3 control-label" for="commissionFeeValue">
                        <spring:message code="value.label"/>
                        <span class="required-indicator">*</span>
                    </label>

                    <div class="col-sm-6">
                        <form:input path="commissionFeeValue" id="commissionFeeValue"
                                    cssClass="form-control"
                                    placeholder="Value"/>
                    </div>
                </div>
                <!-- merchant status -->
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="commissionFeeTypeId">
                        <spring:message code="type.label"/>
                        <span class="required-indicator">*</span>
                    </label>

                    <div class="col-sm-6">
                        <form:select path="commissionFeeTypeId" id="commissionFeeTypeId"
                                     cssClass="form-control"
                                     required="" placeholder="Type">
                            <c:forEach items="${feeTypes}" var="type">
                                <form:option value="${type.id}">${type.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>

                <!-- return fee -->
                <hr/>
                <h4>
                    <spring:message code="return.fee.label"/>
                </h4>

                <div class="form-group">
                    <label class="col-sm-3 control-label" for="returnFeeValue">
                        <spring:message code="value.label"/>
                        <span class="required-indicator">*</span>
                    </label>

                    <div class="col-sm-6">
                        <form:input path="returnFeeValue" id="returnFeeValue"
                                    cssClass="form-control"
                                    placeholder="Value"/>
                    </div>
                </div>
                <!-- merchant status -->
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="returnFeeTypeId">
                        <spring:message code="type.label"/>
                        <span class="required-indicator">*</span>
                    </label>

                    <div class="col-sm-6">
                        <form:select path="returnFeeTypeId" id="returnFeeTypeId"
                                     cssClass="form-control"
                                     required="" placeholder="Type">
                            <c:forEach items="${feeTypes}" var="type">
                                <form:option value="${type.id}">${type.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>

                <hr/>
                <div class='form-group'>
                    <div class='col-sm-offset-3 col-sm-10'>
                        <button class='btn btn-default' id='create-button' type="submit">
                            <spring:message code='action.save.label'/>
                        </button>
                        <button class='btn btn-default' id='reset-button' type="reset">
                            <spring:message code='action.reset.label'/>
                        </button>
                    </div>
                </div>
            </form:form>
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