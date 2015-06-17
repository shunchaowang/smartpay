<%@include file="../taglib.jsp" %>
<spring:message var="cancelAction" code="action.cancel.label"/>
<spring:message var="entity" code="user.label"/>
<spring:message code="edit.label" var="editLabel" arguments="${entity}"/>

<div id="edit-dialog" title="${editLabel}">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-12">
                <form:form class="form-horizontal" id="edit-form" method="post"
                        action="${rootURL}user/edit/permission"
                           commandName="userPermissionCommand">
                    <form:input path="userId" type="hidden" name="userId" id="userId"
                           value="${userId}"/>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">
                            <span class="required-indicator">*</span>
                            <spring:message code="permission.label"/>
                        </label>

                        <div class="col-sm-4">
                            <c:forEach items="${permissions}" var="permission">
                                <form:checkbox path="permissions" value="${permission}"/>
                                ${permission.name}
                            </c:forEach>
                        </div>
                    </div>
                    <div class='form-group'>
                        <div class="col-sm-2 col-sm-offset-2">
                            <button class='btn btn-default' id='save-button' type="submit">
                                <spring:message code='action.save.label'/>
                            </button>
                        </div>
                        <div class="col-sm-2">
                            <button class='btn btn-default' id='cancel-button'>
                                <spring:message code='action.cancel.label'/>
                            </button>
                        </div>
                        <div class="col-sm-2">
                            <button class='btn btn-default' id='reset-button' type="reset">
                                <spring:message code='action.reset.label'/>
                            </button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
