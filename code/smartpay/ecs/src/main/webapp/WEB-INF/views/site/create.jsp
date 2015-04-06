<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<c:if test="${domain != null}">
    <spring:message code="${domain}.label" var="entity"/>
</c:if>

<div class="widget-box">
    <div class="widget-title">
								<span class="icon">
									<i class="icon icon-plus"></i>
								</span>
        <h5><spring:message code='create.label' arguments="${entity}"></spring:message></h5>
    </div>
    <div class="widget-content nopadding">
        <form:form class="form-horizontal" action="${rootURL}${controller}/create" method="POST"
                   commandName="siteCommand" cssClass="form-horizontal" id="auditSite-form">
            <form:input path="id" id="id" type="hidden" value="${siteCommand.id}"/>

            <!-- identity -->
            <div class="control-group">
                <label class="col-sm-3 control-label" for="identity" >
                    <span class="required-indicator">*</span>
                    <spring:message code="identity.label"/>
                </label>

                <div class="controls">
                    <form:input path="identity" id="identity" class="text"
                                value="${siteCommand.identity}"/>
                </div>
            </div>

            <!-- name -->
            <div class="control-group">
                <label class="col-sm-3 control-label" for="name">
                    <span class="required-indicator">*</span>
                    <spring:message code="name.label"/>
                </label>

                <div class="controls">
                    <form:input path="name" id="name" class="span18"
                                value="${siteCommand.name}"/>
                </div>
            </div>
            <!-- url -->
            <div class="control-group">
                <label class="col-sm-3 control-label" for="url">
                    <span class="required-indicator">*</span>
                    <spring:message code="site.url.label"/>
                </label>
                <div class="control">
                    <form:input path="url" id="url" class="text" required=""
                                value="${siteCommand.url}"/>
                </div>
            </div>
            <!-- remark -->
            <div class="control-group">
                <label class="col-sm-3 control-label" for="remark">
                    <spring:message code="remark.label"/>
                </label>

                <div class="control">
                    <form:textarea rows="5" path="remark" id="remark" class="span20"
                                   value="${siteCommand.remark}"/>
                </div>
            </div>
            <!-- buttons -->
            <div class='form-actions'>
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
