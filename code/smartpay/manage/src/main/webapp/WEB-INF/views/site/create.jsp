<!DOCTYPE html>
<%@include file="../taglib.jsp" %>

<!-- end of class row -->
<div class='row' id='notification'>
    <c:if test="${not empty message}">
        <div class="alert alert-warning alert-dismissable" role="alert">
            <button type="button" class="close" data-dismiss="alert">
                <span aria-hidden="true">&times;</span>
                <span class="sr-only"><spring:message code="close.button.label"/> </span>
                    ${message}
            </button>
        </div>
    </c:if>
</div>
<!-- end of notification -->
<br/>

<div class='row'>
    <div class='col-sm-4'>
        <h2><b><spring:message code='site.new.site.label'/></b></h2>
    </div>
</div>
<br>

<div class="row">
    <form:form action="${rootURL}${controller}/createSite" method="POST"
               commandName="siteCommand" cssClass="form-horizontal" id="new-user-form">
        <!-- site name -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="name">
                <spring:message code="site.name.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="col-sm-6">
                <form:input path="name" id="name" cssClass="form-control" required=""
                            placeholder="Name"/>
            </div>
        </div>

        <!-- site merchant -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="url">
                <spring:message code="site.merchant.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="col-sm-6">
                <form:input path="merchant" id="merchant" cssClass="form-control" required=""
                            placeholder="Merchant"/>
            </div>
        </div>
        <!-- site url -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="url">
                <spring:message code="site.url.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="col-sm-6">
                <form:input path="url" id="url" cssClass="form-control" required=""
                            placeholder="Url"/>
            </div>
        </div>
        <!-- site status -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="siteStatus">
                <spring:message code="site.status.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="col-sm-6">
                <form:select path="siteStatus" id="siteStatus" cssClass="form-control" required=""
                             placeholder="Status">
                    <c:forEach items="${siteStatuses}" var="status">
                        <form:option value="${status.id}">${status.name}</form:option>
                    </c:forEach>
                </form:select>
            </div>
        </div>

        <!-- remark -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="remark">
                <spring:message code="site.remark.label"/>
            </label>

            <div class="col-sm-6">
                <form:input path="remark" id="remark" cssClass="form-control"/>
            </div>
        </div>
        <div class='form-group'>
            <div class='col-sm-offset-3 col-sm-10'>
                <button class='btn btn-default' id='create-button' type="submit">
                    <spring:message code='save.button.label'/>
                </button>
                <button class='btn btn-default' id='reset-button' type="reset">
                    <spring:message code='reset.button.label'/>
                </button>
            </div>
        </div>
    </form:form>
</div>

<script type="text/javascript">
    $(document).ready(function () {

    });
</script>