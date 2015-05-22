<!DOCTYPE html>
<%@include file="taglib.jsp" %>
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

    <!-- close of content-header -->
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="col-sm-12">
                <div class="widget-box">
                    <div class="widget-title">
									<span class="icon">
										<i class="icon icon-align-justify"></i>
									</span>
                        <h5><spring:message code="user.change.password.label"/></h5>
                    </div>
                    <div class="widget-content">
                        <form:form class="form-horizontal" method="post"
                                   commandName="passwordCommand"
                                   action="${rootURL}changePassword"
                                   id="change-password">
                            <form:input path="id" type='hidden' value="${passwordCommand.id}"/>
                            <div class="form-group">
                                <label class="control-label col-sm-3">
                                    <span class="required-indicator">*</span>
                                    <spring:message code="currentPassword.label"/>
                                </label>

                                <div class="controls">
                                    <form:input type="password" size="32" path="currentPassword"
                                                class="text"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-3">
                                    <span class="required-indicator">*</span>
                                    <spring:message code="password.label"/>
                                </label>

                                <div class="controls">
                                    <form:input type="password" size="32" path="password"
                                                id="password"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-3">
                                    <span class="required-indicator">*</span>
                                    <spring:message code="confirmPassword.label"/>
                                </label>

                                <div class="controls">
                                    <form:input type="password" size="32" path="confirmPassword"
                                                id="confirmPassword"/>
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
        $("#change-password").validate({
            rules: {
                currentPassword: {
                    required: true,
                    minlength: 5,
                    maxlength: 32
                },
                password: {
                    required: true,
                    minlength: 5,
                    maxlength: 32
                },
                confirmPassword: {
                    required: true,
                    minlength: 5,
                    maxlength: 32,
                    equalTo: "#password"
                }
            }
        });
    });
</script>
