<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<c:if test="${domain != null}">
    <spring:message code="${domain}.label" var="entity"/>
</c:if>

<div class='row'>
    <div class='col-sm-4'>
        <h2><b>
            <spring:message code='edit.label' arguments="${entity}"/>
        </b></h2>
    </div>
</div>
<br>

<div class="row">
    <form:form action="${rootURL}${controller}/edit" method="POST"
               commandName="siteCommand" cssClass="form-horizontal" id="auditSite-form">
        <form:input path="id" id="id" type="hidden" value="${siteCommand.id}"/>
        <!-- site merchant -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="merchant">
                <spring:message code="site.merchant.label"/>
            </label>
            <div class="col-sm-6">
                <form:select path="merchant" id="merchant" cssClass="form-control"
                             required="" placeholder="Merchant">
                        <form:option
                                value="${siteCommand.merchant}">${siteCommand.merchantName}</form:option>
                </form:select>
            </div>
        </div>
        <!-- site identity -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="identity">
                <spring:message code="identity.label"/>
            </label>

            <div class="col-sm-6">
                <form:input path="identity" id="identity" cssClass="form-control"
                            value="${siteCommand.identity}" readonly="true"/>
            </div>
        </div>
        <!-- name -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="name">
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
            <label class="col-sm-3 control-label" for="siteStatusId">
                <spring:message code="status.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="col-sm-6">
                <form:select path="siteStatusId" id="siteStatusId" cssClass="form-control"
                             required="" placeholder="Status">
                    <form:option
                            value="${siteCommand.siteStatusId}">${siteCommand.siteStatusName}</form:option>
                    <c:forEach items="${siteStatuses}" var="status">
                        <c:if test="${!(siteCommand.siteStatusId.equals(status.id))}">
                            <form:option value="${status.id}">${status.name}</form:option>
                        </c:if>
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
                <button class='btn btn-default' id='create-button' type="submit">
                    <spring:message code='action.save.label'/>
                </button>
                <a href="${rootURL}${controller}">
                    <button type="button" class="btn btn-default">
                        <spring:message code="action.return.label"/>
                    </button>
                </a>
            </div>
        </div>
    </form:form>
</div>

