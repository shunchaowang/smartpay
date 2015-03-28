<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="${domain}.label" var="entity"/>

<div class='row' id='notification'>
    <c:if test="${not empty message}">
        <div class="alert alert-danger alert-dismissable" role="alert">
            <button type="button" class="close" data-dismiss="alert">
                <span aria-hidden="true">&times;</sfpan>
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
        <h2><b><spring:message code='edit.label' arguments="${entity}"/></b></h2>
    </div>
</div>
<br>

<div class="row">
    <form:form action="${rootURL}${controller}/edit" method="POST"
               commandName="merchantCommand" cssClass="form-horizontal" id="new-merchant-form">
        <hr/>
        <h4>
            <spring:message code="basic.info.label"/>
        </h4>

        <form:hidden path="id" id="id" cssClass="form-control" required=""
                    placeholder="Id" value="${merchantCommand.id}"/>

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
        <!-- first name -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="address">
                <spring:message code="address.label"/>
            </label>

            <div class="col-sm-6">
                <form:input path="address" id="address" cssClass="form-control"
                            placeholder="Address" value="${merchantCommand.address}"/>
            </div>
        </div>
        <!-- last name -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="contact">
                <spring:message code="contact.label"/>
            </label>

            <div class="col-sm-6">
                <form:input path="contact" id="contact" cssClass="form-control"
                            placeholder="Contact" value="${merchantCommand.contact}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label" for="tel">
                <spring:message code="tel.label"/>
            </label>

            <div class="col-sm-6">
                <form:input path="tel" id="tel" cssClass="form-control"
                            placeholder="Telephone" value="${merchantCommand.tel}"/>
            </div>
        </div>
        <!-- email -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="email">
                <spring:message code="email.label"/>
            </label>

            <div class="col-sm-6">
                <form:input path="email" id="email" cssClass="form-control"
                            placeholder="Email" value="${merchantCommand.email}"/>
            </div>
        </div>
        <!-- merchant status -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="merchantStatusId">
                <spring:message code="status.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="col-sm-6">
                <form:select path="merchantStatusId" id="merchantStatusId" cssClass="form-control"
                             required="" placeholder="Status">
                    <form:option
                            value="${merchantCommand.merchantStatusId}">${merchantCommand.merchantStatusName}</form:option>
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
                <form:input path="remark" id="remark" cssClass="form-control"
                            value="${merchantCommand.remark}"/>
            </div>
        </div>

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