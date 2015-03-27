<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="${domain}.label" var="entity"/>

<div class='row'>
    <div class='col-sm-4'>
        <h2><b><spring:message code='create.label' arguments="${entity}"/></b></h2>
    </div>
</div>
<br>

<div class="row">
    <form:form action="${rootURL}${controller}/createSite" method="POST"
               commandName="siteCommand" cssClass="form-horizontal" id="new-user-form">
        <!-- site name -->
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

        <!-- site merchant -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="url">
                <spring:message code="Merchant.label"/>
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
                <spring:message code="status.label"/>
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
                <spring:message code="remark.label"/>
            </label>

            <div class="col-sm-6">
                <form:input path="remark" id="remark" cssClass="form-control"/>
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

    });
</script>