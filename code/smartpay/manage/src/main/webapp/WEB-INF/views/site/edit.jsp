<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<c:if test="${domain != null}">
    <spring:message code="${domain}.label" var="entity"/>
</c:if>

<div id="content">
    <div id="content-header">
        <div id="breadcrumb">
            <a href="${rootURL}">
                <i class="icon icon-home"></i>
                <spring:message code="home.label"/>
            </a>
            <a href="${rootURL}${controller}/indexAll">
                <spring:message code="manage.label" arguments="${entity}"/>
            </a>
            <a href="${rootURL}${controller}/${action}" class="current">
                <spring:message code="${action}.label" arguments="${entity}"/>
            </a>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">
                <div class="widget-box">
                    <div class="widget-title">
								<span class="icon">
									<i class="icon icon-align-justify"></i>
								</span>
                        <h5><b><spring:message code='edit.label' arguments="${entity}"/></b></h5>
                    </div>
                    <div class="widget-content nopadding">
                        <form:form action="${rootURL}${controller}/edit" method="POST"
                                   commandName="siteCommand" cssClass="form-horizontal"
                                   id="auditSite-form">
                            <form:input size="80" path="id" id="id" type="hidden"
                                        value="${siteCommand.id}"/>
                            <!-- site merchant -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="merchant">
                                    <spring:message code="site.merchant.label"/>
                                </label>

                                <div class="controls">
                                    <form:select path="merchant" id="merchant" cssClass="text"
                                                 required="" placeholder="Merchant">
                                        <form:option
                                                value="${siteCommand.merchant}">${siteCommand.merchantName}</form:option>
                                    </form:select>
                                </div>
                            </div>
                            <!-- site identity -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="identity">
                                    <spring:message code="identity.label"/>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="identity" id="identity"
                                                cssClass="text"
                                                value="${siteCommand.identity}" readonly="true"/>
                                </div>
                            </div>
                            <!-- name -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="name">
                                    <spring:message code="name.label"/>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="name" id="name" cssClass="text"
                                                value="${siteCommand.name}"/>
                                </div>
                            </div>
                            <!-- url -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="url">
                                    <spring:message code="site.url.label"/>
                                    <span class="required-indicator">*</span>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="url" id="url" cssClass="text"
                                                required=""
                                                value="${siteCommand.url}"/>
                                </div>
                            </div>
                            <!-- site status -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="siteStatusId">
                                    <spring:message code="status.label"/>
                                    <span class="required-indicator">*</span>
                                </label>

                                <div class="controls">
                                    <form:select path="siteStatusId" id="siteStatusId"
                                                 cssClass="text"
                                                 required="" placeholder="Status">
                                        <form:option
                                                value="${siteCommand.siteStatusId}">${siteCommand.siteStatusName}</form:option>
                                        <c:forEach items="${siteStatuses}" var="status">
                                            <c:if test="${!(siteCommand.siteStatusId.equals(status.id))}">
                                                <form:option
                                                        value="${status.id}">${status.name}</form:option>
                                            </c:if>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>
                            <!-- remark -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="remark">
                                    <spring:message code="remark.label"/>
                                </label>

                                <div class="controls">
                                    <form:textarea cols="100" rows="5" path="remark" id="remark"
                                                   value="${siteCommand.remark}"/>
                                </div>
                            </div>


                            <div class='form-actions col-lg-offset-2'>

                                <button class='btn btn-success' id='create-button' type="submit">
                                    <spring:message code='action.save.label'/>
                                </button>
                                <a href="${rootURL}${controller}/indexAll">
                                    <button type="button" class="btn btn-success">
                                        <spring:message code="action.return.label"/>
                                    </button>
                                </a>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>