<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<c:if test="${domain != null}">
    <spring:message code="${domain}.label" var="entity"/>
</c:if>

<div class='row' id='notification'>
    <c:if test="${not empty message}">
        <div class="alert alert-danger alert-dismissable" role="alert">
            <button type="button" class="close" data-dismiss="alert">
                <span aria-hidden="true">&times;</span>
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

        <!-- site status -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="siteStatusId">
                <spring:message code="site.merchant.label"/>
            </label>
            <div class="col-sm-6">
                <form:select path="merchant" id="merchant" cssClass="form-control"
                             required="" placeholder="Merchant">
                    <c:forEach items="${allMerchants}" var="merchant">
                        <form:option value="${merchant.id}">${merchant.name}</form:option>
                    </c:forEach>
                </form:select>
            </div>
        </div>

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
        <!-- site status -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="siteStatusId">
                <spring:message code="status.label"/>
            </label>
            <div class="col-sm-6">
                <form:select path="siteStatusId" id="siteStatusId" cssClass="form-control"
                             required="" placeholder="Status">
                    <c:forEach items="${siteStatuses}" var="status">
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
            }
        });

        $('.datepicker').datepicker();
    });
</script>
