<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<c:if test="${domain != null}">
    <spring:message code="${domain}.label" var="entity"/>
</c:if>

<div class='row'>
    <div class='col-sm-4'>
        <h2><b>
            <spring:message code='create.label' arguments="${entity}"/>
        </b></h2>
    </div>
</div>
<br>

<div class="row">
    <form:form action="${rootURL}${controller}/create" method="POST"
               commandName="siteCommand" cssClass="form-horizontal" id="auditSite-form">
        <form:input path="id" id="id" type="hidden" value="${siteCommand.id}"/>

        <!-- identity -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="identity" >
                <span class="required-indicator">*</span>
                <spring:message code="identity.label"/>
            </label>

            <div class="col-sm-6">
                <form:input path="identity" id="identity" cssClass="form-control"
                            value="${siteCommand.identity}"/>
            </div>
        </div>

        <!-- name -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="name">
                <span class="required-indicator">*</span>
                <spring:message code="name.label"/>
            </label>

            <div class="col-sm-6">
                <form:input path="name" id="name" cssClass="form-control"
                            value="${siteCommand.name}"/>
            </div>
        </div>
        <!-- url -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="url">
                <span class="required-indicator">*</span>
                <spring:message code="site.url.label"/>
            </label>

            <div class="col-sm-6">
                <form:input path="url" id="url" cssClass="form-control" required=""
                            value="${siteCommand.url}"/>
            </div>
        </div>
        <!-- remark -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="remark">
                <spring:message code="remark.label"/>
            </label>

            <div class="col-sm-6">
                <form:textarea rows="5" path="remark" id="remark" cssClass="form-control"
                            value="${siteCommand.remark}"/>
            </div>
        </div>
        <div class='form-group'>
            <div class='col-sm-offset-3 col-sm-10'>
                <div class='col-sm-offset-3 col-sm-10'>
                    <button class='btn btn-default' id='create-button' type="submit">
                        <spring:message code='action.save.label'/>
                    </button>
                    <button class='btn btn-default' id='reset-button' type="reset">
                        <spring:message code='action.reset.label'/>
                    </button>
                </div>
            </div>
        </div>
    </form:form>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#new-merchant-form').validate({
            rules: {
                indentity: {required: true, minlength: 3, maxlength: 32},
                name: {required: true, minlength: 3, maxlength: 32},
                url: {required: true, minlength: 3, maxlength: 128},
                remank: {required: false, minlength: 3, maxlength: 512}
            }
        });

        $('.datepicker').datepicker();
    });
</script>
