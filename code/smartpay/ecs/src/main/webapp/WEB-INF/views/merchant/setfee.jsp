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
        <!— actual content —>
        <div class='row'>
            <div class='col-sm-4'>
                <h2><b><spring:message code='edit.label' arguments="${entity}"/></b></h2>
            </div>
        </div>
        <br>

        <div class="row">
            <form:form action="${rootURL}${controller}/setfee" method="POST"
                       commandName="merchantCommand" cssClass="form-horizontal"
                       id="new-merchant-form">
                <hr/>
                <h4>
                    <spring:message code="basic.info.label"/>
                </h4>

                <form:hidden path="id" id="id" cssClass="form-control" required=""
                             placeholder="Id" value="${merchantCommand.id}"/>

                <div class="form-group">
                    <label class="col-sm-3 control-label" for="identity">
                        <spring:message code="identity.label"/>
                        <span class="required-indicator">*</span>
                    </label>

                    <div class="col-sm-6">
                        <form:input path="identity" id="identity" cssClass="form-control"
                                    required=""
                                    placeholder="Identity" value="${merchantCommand.identity}"
                                    readonly="true"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label" for="name">
                        <spring:message code="name.label"/>
                        <span class="required-indicator">*</span>
                    </label>

                    <div class="col-sm-6">
                        <form:input path="name" id="name" cssClass="form-control" required=""
                                    placeholder="Name" value="${merchantCommand.name}"/>
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
                                    placeholder="Key" value="${merchantCommand.encryptionKey}"/>
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
                            <form:option
                                    value="${merchantCommand.encryptionTypeId}">${merchantCommand.encryptionTypeName}</form:option>
                            <c:forEach items="${encryptionTypes}" var="type">
                                <c:if test="${!(merchantCommand.encryptionTypeId.equals(type.id))}">
                                    <form:option value="${type.id}">${type.name}</form:option>
                                </c:if>
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
                                    placeholder="Value"
                                    value="${merchantCommand.commissionFeeValue}"/>
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
                            <form:option
                                    value="${merchantCommand.commissionFeeTypeId}">${merchantCommand.commissionFeeTypeName}</form:option>
                            <c:forEach items="${feeTypes}" var="type">
                                <c:if test="${!(merchantCommand.commissionFeeTypeId.equals(type.id))}">
                                    <form:option value="${type.id}">${type.name}</form:option>
                                </c:if>
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
                                    placeholder="Value" value="${merchantCommand.returnFeeValue}"/>
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
                            <form:option
                                    value="${merchantCommand.returnFeeTypeId}">${merchantCommand.returnFeeTypeName}</form:option>
                            <c:forEach items="${feeTypes}" var="type">
                                <c:if test="${!(merchantCommand.returnFeeTypeId.equals(type.id))}">
                                    <form:option value="${type.id}">${type.name}</form:option>
                                </c:if>
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