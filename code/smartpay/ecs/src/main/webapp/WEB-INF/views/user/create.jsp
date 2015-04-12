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
                                   commandName="userCommand" id="new-user-form">

                            <!-- user status -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="username">
                                    <spring:message code="username.label"/>
                                    <span class="required-indicator">*</span>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="username" id="username"
                                                cssClass="text" required=""
                                                placeholder="Username"/>
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
                                                cssClass="text" required=""
                                                placeholder="First Name"/>
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
                                                cssclass="text"
                                                required=""
                                                placeholder="Last Name"/>
                                </div>
                            </div>
                            <!-- email -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="email">
                                    <spring:message code="email.label"/>
                                    <span class="required-indicator">*</span>
                                </label>

                                <div class="controls">
                                    <form:input path="email" id="email" size="80" required=""
                                                placeholder="Email"/>
                                </div>
                            </div>

                            <!-- remark -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="remark">
                                    <spring:message code="remark.label"/>
                                </label>

                                <div class="controls">
                                    <form:textarea cols="100" rows="5" path="remark" id="remark"/>
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
        $('#new-user-form').validate({
            rules: {
                username: {required: true, minlength: 3, maxlength: 32},
                firstName: {required: true, minlength: 3, maxlength: 32},
                lastName: {required: true, minlength: 3, maxlength: 32},
                email: {required: true, minlength: 3, maxlength: 32},
                remark: {required: false, minlength: 0, maxlength: 512},
            }
        });
    });
</script>