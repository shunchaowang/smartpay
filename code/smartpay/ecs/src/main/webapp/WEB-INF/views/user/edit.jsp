<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="${domain}.label" var="entity"/>

<div id="content">
    <div id="content-header">
        <div id="breadcrumb">
            <a href="${rootURL}">
                <i class="icon icon-home"></i>
                <spring:message code="home.label"/>
            </a>
            <a href="${rootURL}${controller}/${action}" class="current">
                <spring:message code="${action}.label" arguments="${entity}"/>
            </a>
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
                        <h5><b><spring:message code='edit.label' arguments="${entity}"/></b></h5>
                    </div>
                    <div class="widget-content nopadding">
                        <form:form action="${rootURL}${controller}/edit" method="POST"
                                   commandName="userCommand" cssClass="form-horizontal"
                                   id="edit-user-form">
                            <form:input path="id" id="id" type="hidden" value="${userCommand.id}"/>

                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="username">
                                    <spring:message code="username.label"/>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="username" id="username"
                                                cssClass="text"
                                                value="${userCommand.username}" readonly="true"/>
                                </div>
                            </div>

                            <!-- first name -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="firstName">
                                    <spring:message code="firstName.label"/>
                                    <span class="required-indicator">*</span>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="firstName" id="firstName"
                                                cssClass="text"
                                                required=""
                                                value="${userCommand.firstName}"/>
                                </div>
                            </div>
                            <!-- last name -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="lastName">
                                    <spring:message code="lastName.label"/>
                                    <span class="required-indicator">*</span>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="lastName" id="lastName"
                                                cssClass="text" required=""
                                                value="${userCommand.lastName}"/>
                                </div>
                            </div>
                            <!-- email -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="email">
                                    <spring:message code="email.label"/>
                                    <span class="required-indicator">*</span>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="email" id="email" cssClass="text"
                                                required=""
                                                value="${userCommand.email}"/>
                                </div>
                            </div>
                            <!-- remark -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="remark">
                                    <spring:message code="remark.label"/>
                                </label>

                                <div class="controls">
                                    <form:textarea cols="100" rows="5" path="remark" id="remark"
                                                   value="${userCommand.remark}"/>
                                </div>
                            </div>
                            <div class='form-actions col-lg-offset-2'>
                                <button class='btn btn-success' id='create-button' type="submit">
                                    <spring:message code='action.save.label'/>
                                </button>
                                <a href="${rootURL}${controller}/index">
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

<script type="text/javascript">
    $(document).ready(function () {
        $('#edit-user-form').validate({
            rules: {
                firstName: {required: true, minlength: 3, maxlength: 32},
                lastName: {required: true, minlength: 3, maxlength: 32},
                email: {required: true, minlength: 3, maxlength: 32},
                userStatus: {required: true}
            }
        });
    });
</script>