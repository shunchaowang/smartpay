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
                        <h5><b><spring:message code='create.label' arguments="${entity}"/></b></h5>
                    </div>
                    <div class="widget-content nopadding">
                        <form:form action="${rootURL}${controller}/create" method="POST"
                                   commandName="siteCommand" cssClass="form-horizontal"
                                   id="new-site-form">

                            <form:input size="80" path="id" id="id" type="hidden"
                                        value="${siteCommand.id}"/>

                            <!-- site status -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="siteStatusId">
                                    <spring:message code="site.merchant.label"/>
                                </label>

                                <div class="controls">
                                    <form:select path="merchant" id="merchant" cssClass="text"
                                                 required="" placeholder="Merchant">
                                        <c:forEach items="${allMerchants}" var="merchant">
                                            <form:option
                                                    value="${merchant.id}">${merchant.name}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>

                            <!-- identity -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="identity">
                                    <span class="required-indicator">*</span>
                                    <spring:message code="identity.label"/>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="identity" id="identity"
                                                cssClass="text"
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
                                    <form:input size="80" path="name" id="name" cssClass="text"
                                                value="${siteCommand.name}"/>
                                </div>
                            </div>
                            <!-- url -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="url">
                                    <span class="required-indicator">*</span>
                                    <spring:message code="site.url.label"/>
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
                                </label>

                                <div class="controls">
                                    <form:select path="siteStatusId" id="siteStatusId"
                                                 cssClass="text"
                                                 required="" placeholder="Status">
                                        <c:forEach items="${siteStatuses}" var="status">
                                            <form:option
                                                    value="${status.id}">${status.name}</form:option>
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
                                <button class='btn btn-success' id='reset-button' type="reset">
                                    <spring:message code='action.reset.label'/>
                                </button>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#new-site-form').validate({
            rules: {
                indentity: {required: true, minlength: 3, maxlength: 32},
                name: {required: true, minlength: 3, maxlength: 32},
            }
        });

        $('.datepicker').datepicker();
    });
</script>
