<!DOCTYPE html>
<%@include file="../../taglib.jsp" %>
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
            <spring:message code='auditSite.label' arguments="${entity}"/>
        </b></h2>
    </div>
</div>
<br>

<div class="row">
    <form:form action="${rootURL}${controller}audit" method="POST"
               commandName="siteCommand" cssClass="form-horizontal" id="auditSite-form">
        <form:input path="id" id="id" type="hidden" value="${siteCommand.id}"/>
        <!-- name -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="name">
                <spring:message code="name.label"/>
            </label>

            <div class="col-sm-6">
                <form:input path="name" id="name" cssClass="form-control"
                            value="${siteCommand.name}" readonly="true"/>
            </div>
        </div>
        <!-- url -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="url">
                <spring:message code="site.url.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="col-sm-6">
                <form:input path="url" id="url" cssClass="form-control" required=""
                            value="${siteCommand.url}"/>
            </div>
        </div>
        <!-- site status -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="siteStatus">
                <spring:message code="status.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="col-sm-6">
                <form:select path="siteStatus" id="siteStatus" cssClass="form-control" required=""
                             value="${siteCommand.siteStatus}">
                    <c:forEach items="${siteStatuses}" var="status">
                        <form:option value="${status.id}">${status.name}</form:option>
                    </c:forEach>
                </form:select>
            </div>
        </div>
        <!-- site merchant -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="siteStatus">
                <spring:message code="site.merchant.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="col-sm-6">
                <form:select path="merchant" id="merchant" cssClass="form-control" required=""
                             value="${siteCommand.merchantName}">
                    <c:forEach items="${merchant}" var="status">
                        <form:option
                                value="${siteCommand.merchant}">${siteCommand.merchantName}</form:option>
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
                <button class='btn btn-default' id='audit-button' type="submit">
                    <spring:message code='action.audit.label'/>
                </button>
                <a href="${rootURL}${controller}/${subDomain}">
                    <button type="button" class="btn btn-default">
                        <spring:message code="action.return.label"/>
                    </button>
                </a>
            </div>
        </div>
    </form:form>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#auditSite-form').validate({
            rules: {
                name: {required: true, minlength: 3, maxlength: 32},
                url: {required: true, minlength: 3, maxlength: 32},
                remark: {required: true, email: true, minlength: 3, maxlength: 32},
                siteStatus: {required: true}
            }
        });
    });
</script>