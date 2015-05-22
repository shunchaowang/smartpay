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
                <spring:message code="index.label" arguments="${entity}"/>
            </li>
            <li class="active">
                    <i class="glyphicon glyphicon-wrench"></i>
                    <spring:message code="create.label" arguments="${entity}"/>
            </li>
        </ol>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <form:form action="${rootURL}merchant/create" method="POST"
                       commandName="merchantCommand" cssClass="form-horizontal"
                       id="new-merchant-form">

                <div class="form-group">
                    <h4>
                        <spring:message code="basic.info.label"/>
                    </h4>
                </div>
                <div class="row">
                    <label class="col-sm-1 control-label" for="name">
                        <span>*</span>
                        <spring:message code="name.label"/>
                    </label>

                    <div class="col-sm-3">
                        <form:input size="64" path="name" id="name" cssClass="form-control"
                                    required=""
                                    placeholder="Name"/>
                    </div>
                    <label class="col-sm-1 control-label" for="merchantStatusId">
                        <span>*</span>
                        <spring:message code="status.label"/>
                    </label>

                    <div class="col-sm-3">
                        <form:select path="merchantStatusId" id="merchantStatusId"
                                     cssClass="form-control" required="">
                            <c:forEach items="${merchantStatuses}" var="status">
                                <form:option value="${status.id}">
                                    ${status.name}
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <br>

                <div class="row">
                    <label class="col-sm-1 control-label" for="email">
                        <spring:message code="email.label"/>
                    </label>

                    <div class="col-sm-3">
                        <form:input size="32" path="email" id="email" cssClass="form-control"
                                    placeholder="Email"/>
                    </div>
                    <label class="col-sm-1 control-label" for="contact">
                        <spring:message code="contact.label"/>
                    </label>

                    <div class="col-sm-3">
                        <form:input size="32" path="contact" id="contact"
                                    cssClass="form-control"
                                    placeholder="Contact"/>
                    </div>
                </div>
                <br>

                <div class="row">
                    <label class="col-sm-1 control-label" for="address">
                        <spring:message code="address.label"/>
                    </label>

                    <div class="col-sm-3">
                        <form:input size="64" path="address" id="address"
                                    cssClass="form-control"
                                    placeholder="Address"/>
                    </div>
                    <label class="col-sm-1 control-label" for="tel">
                        <spring:message code="tel.label"/>
                    </label>

                    <div class="col-sm-3">
                        <form:input size="16" path="tel" id="tel" cssClass="form-control"
                                    placeholder="Telephone"/>
                    </div>
                </div>
                <!-- basic info -->
                <br>
                <!-- remark -->
                <div class="row">
                    <label class="col-sm-1 control-label" for="remark">
                        <spring:message code="remark.label"/>
                    </label>

                    <div class="col-sm-5">
                        <form:textarea cols="100" rows="5" cssClass="form-control"
                                       path="remark" id="remark"/>
                    </div>
                </div>
                <hr>
                <!-- credential -->
                <div class="form-group">
                    <h4>
                        <spring:message code="credential.label"/>
                    </h4>
                </div>
                <div class="row">
                    <label class="col-sm-1 control-label" for="credentialContent">
                        <span>*</span>
                        <spring:message code="content.label"/>
                    </label>

                    <div class="col-sm-3">
                        <form:input size="32" path="credentialContent"
                                    id="credentialContent"
                                    cssClass="form-control" required=""
                                    placeholder="Content"/>
                    </div>
                    <label class="col-sm-1 control-label" for="credentialStatusId">
                        <span>*</span>
                        <spring:message code="status.label"/>
                    </label>

                    <div class="col-sm-3">
                        <form:select path="credentialStatusId" id="credentialStatusId"
                                     cssClass="form-control"
                                     required="" placeholder="Status">
                            <c:forEach items="${credentialStatuses}" var="status">
                                <form:option value="${status.id}">
                                    ${status.name}
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <br>

                <div class="row">
                    <label class="col-sm-1 control-label"
                           for="credentialExpirationTime">
                        <span>*</span>
                        <spring:message code="validation.label"/>
                    </label>

                    <div class="col-sm-3">
                        <form:input size="32" path="credentialExpirationTime"
                                    id="credentialExpirationTime"
                                    cssClass="form-control datepicker" required=""/>
                    </div>
                    <label class="col-sm-1 control-label" for="credentialTypeId">
                        <span>*</span>
                        <spring:message code="type.label"/>
                    </label>

                    <div class="col-sm-3">
                        <form:select path="credentialTypeId" id="credentialTypeId"
                                     cssClass="form-control"
                                     required="">
                            <c:forEach items="${credentialTypes}" var="type">
                                <form:option value="${type.id}">
                                    ${type.name}
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <hr>
                <!-- encryption -->
                <div class="form-group">
                    <h4>
                        <spring:message code="encryption.label"/>
                    </h4>
                </div>
                <div class="row">
                    <label class="col-sm-1 control-label" for="encryptionKey">
                        <span>*</span>
                        <spring:message code="key.label"/>
                    </label>

                    <div class="col-sm-3">
                        <form:input size="32" path="encryptionKey" id="encryptionKey"
                                    cssClass="form-control"
                                    placeholder="Key"/>
                    </div>
                    <label class="col-sm-1 control-label" for="encryptionTypeId">
                        <span>*</span>
                        <spring:message code="type.label"/>
                    </label>

                    <div class="col-sm-3">
                        <form:select path="encryptionTypeId" id="encryptionTypeId"
                                     cssClass="form-control"
                                     required="">
                            <c:forEach items="${encryptionTypes}" var="type">
                                <form:option value="${type.id}">
                                    ${type.name}
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <hr>

                <!-- commission fee -->
                <div class="form-group">
                    <h4>
                        <spring:message code="commission.fee.label"/>
                    </h4>
                </div>
                <div class="row">
                    <label class="col-sm-1 control-label" for="commissionFeeValue">
                        <span>*</span>
                        <spring:message code="value.label"/>
                    </label>

                    <div class="col-sm-3">
                        <form:input size="32" path="commissionFeeValue"
                                    id="commissionFeeValue"
                                    cssClass="form-control"
                                    placeholder="Value"/>
                    </div>
                    <label class="col-sm-1 control-label" for="commissionFeeTypeId">
                        <span>*</span>
                        <spring:message code="type.label"/>
                    </label>

                    <div class="col-sm-3">
                        <form:select path="commissionFeeTypeId" id="commissionFeeTypeId"
                                     cssClass="form-control"
                                     required="" placeholder="Type">
                            <c:forEach items="${feeTypes}" var="type">
                                <form:option value="${type.id}">
                                    ${type.name}
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <hr>

                <!-- return fee -->
                <h4>
                    <spring:message code="return.fee.label"/>
                </h4>

                <div class="row">
                    <label class="col-sm-1 control-label" for="returnFeeValue">
                        <span>*</span>
                        <spring:message code="value.label"/>
                    </label>

                    <div class="col-sm-3">
                        <form:input size="80" path="returnFeeValue" id="returnFeeValue"
                                    cssClass="form-control"
                                    placeholder="Value"/>
                    </div>
                    <label class="col-sm-1 control-label" for="returnFeeTypeId">
                        <span>*</span>
                        <spring:message code="type.label"/>
                    </label>

                    <div class="col-sm-3">
                        <form:select path="returnFeeTypeId" id="returnFeeTypeId"
                                     cssClass="form-control"
                                     required="" placeholder="Type">
                            <c:forEach items="${feeTypes}" var="type">
                                <form:option value="${type.id}">
                                    ${type.name}
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <hr>

                <div class='form-group'>
                    <div class="col-sm-2 col-sm-offset-2">
                        <button class='btn btn-default' id='create-button' type="submit">
                            <spring:message code='action.save.label'/>
                        </button>
                    </div>
                    <div class="col-sm-2">
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