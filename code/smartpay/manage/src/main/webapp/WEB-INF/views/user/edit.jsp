<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<c:if test="${domain != null}">
    <spring:message code="${domain}.label" var="entity"/>
</c:if>

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
                               commandName="userCommand" cssClass="form-horizontal" id="edit-user-form">
                        <form:input size="80"  path="id" id="id" type="hidden" value="${userCommand.id}"/>
                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="username">
                                <spring:message code="username.label"/>
                            </label>

                            <div class="controls">
                                <form:input size="80"  path="username" id="username" cssClass="form-control"
                                            value="${userCommand.username}" readonly="true"/>
                            </div>
                        </div>
                        <!-- last name -->
                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="lastName">
                                <span class="required-indicator">*</span>
                                <spring:message code="lastName.label"/>
                            </label>

                            <div class="controls">
                                <form:input size="80"  path="lastName" id="lastName" cssClass="text" required=""
                                            value="${userCommand.lastName}"/>
                            </div>
                        </div>
                        <!-- first name -->
                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="firstName">
                                <span class="required-indicator">*</span>
                                <spring:message code="firstName.label"/>
                            </label>

                            <div class="controls">
                                <form:input size="80"  path="firstName" id="firstName" cssClass="text" required=""
                                            value="${userCommand.firstName}"/>
                            </div>
                        </div>
                        <!-- email -->
                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="email">
                                <span class="required-indicator">*</span>
                                <spring:message code="email.label"/>
                            </label>

                            <div class="controls">
                                <form:input size="80"  path="email" id="email" cssClass="text" required=""
                                            value="${userCommand.email}"/>
                            </div>
                        </div>
                        <!-- user status -->
                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="userStatus">
                                <span class="required-indicator">*</span>
                                <spring:message code="status.label"/>
                            </label>

                            <div class="controls">
                                <form:select path="userStatus" id="userStatus" cssClass="text" required=""
                                             value="${userCommand.userStatus}">
                                    <c:forEach items="${userStatuses}" var="status">
                                        <form:option value="${status.id}">${status.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        <!-- if create a merchant admin we need to have merchant selection -->
                        <c:if test="${domain == 'MerchantAdmin'}">
                            <!-- user status -->
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="userStatus">
                                    <span class="required-indicator">*</span>
                                    <spring:message code="Merchant.label"/>
                                </label>

                                <div class="controls">
                                    <form:select path="merchant" id="merchant" cssClass="text" required=""
                                                 value="${userCommand.merchant}">
                                        <c:forEach items="${merchants}" var="merchant">
                                            <form:option value="${merchant.id}">${merchant.name}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>
                        </c:if>
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
                                <button class='btn btn-success' id='reset-button' type="reset">
                                    <spring:message code='action.reset.label'/>
                                </button>
                                <a href="${rootURL}${controller}/index${domain}">
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

<script type="text/javascript">
    $(document).ready(function () {
        $('#edit-user-form').validate({
            rules: {
                firstName: {required: true, minlength: 3, maxlength: 32},
                lastName: {required: true, minlength: 3, maxlength: 32},
                email: {required: true, email: true, minlength: 3, maxlength: 32},
                userStatus: {required: true}
            }
        });
    });
</script>