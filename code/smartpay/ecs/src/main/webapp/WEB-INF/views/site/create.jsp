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
            <c:if test="${domain != null}">
                <spring:message code="${domain}.label" var="entity"/>
                <a href="${rootURL}${controller}">
                    <spring:message code="manage.label" arguments="${entity}"/>
                </a>
                <a href="${rootURL}${controller}/${action}" class="current">
                    <spring:message code="${action}.label" arguments="${entity}"/>
                </a>
            </c:if>
        </div>
    </div>
    <!-- reserved for notification -->
    <!-- close of content-header -->
    <div class="container-fluid">
        <!— actual content —>
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
                        <form:form class="form-horizontal" action="${rootURL}${controller}/create"
                                   method="POST"
                                   commandName="siteCommand" id="new-site-form">
                            <form:input size="80" path="id" id="id" type="hidden"
                                        value="${siteCommand.id}"/>

                            <!-- identity -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="identity">
                                    <span class="required-indicator">*</span>
                                    <spring:message code="identity.label"/>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="identity" id="identity" class="text"
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
                                    <form:input size="80" path="name" id="name" class="text"
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
                                    <form:input size="80" path="url" id="url" class="text"
                                                required=""
                                                value="${siteCommand.url}"/>
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
                            <!-- buttons -->
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
                identity: {required: true, minlength: 3, maxlength: 32},
                name: {required: true, minlength: 3, maxlength: 32},
                url: {required: true, minlength: 3, maxlength: 128},
                remank: {required: false, minlength: 3, maxlength: 512}
            }
        });

        $('.datepicker').datepicker();
    });
</script>
