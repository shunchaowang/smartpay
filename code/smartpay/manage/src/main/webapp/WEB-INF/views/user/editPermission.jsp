<%@include file="../taglib.jsp" %>
<spring:message code="user.label" var="entity"/>
<spring:message code="permission.label" var="permissionLabel"/>

<div class="container-fluid">
    <div class="row">
        <ol class="breadcrumb">
            <li>
                <i class="glyphicon glyphicon-home"></i>
                <spring:message code="home.label"/>
            </li>
            <li>
                <i class="glyphicon glyphicon-list"></i>
                <spring:message code="manage.label" arguments="${entity}"/>
            </li>
            <li>
                <i class="glyphicon glyphicon-list"></i>
                <spring:message code="manage.label" arguments="${permissionLabel}"/>
            </li>
            <li class="active">
                <i class="glyphicon glyphicon-wrench"></i>
                <spring:message code="edit.label" arguments="${permissionLabel}"/>
            </li>
        </ol>
    </div>
    <div class="row">
        <div class="col-sm-2 pull-left">
            <h3>${user.username}${permissionLabel}</h3>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-sm-12">
            <form:form class="form-horizontal" id="edit-form" method="post"
                       action="${rootURL}user/edit/permission"
                       commandName="userPermissionCommand">
                <form:input path="userId" type="hidden" name="userId" id="userId"
                            value="${user.id}"/>

                <div class="form-group">
                    <div class="col-sm-4">
                        <c:forEach items="${permissions}" var="permission">
                            <form:checkbox path="permissions" value="${permission}"/>
                            <spring:message code="${permission}.label"/>
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
                        <button class='btn btn-default' id='reset-button' type="reset">
                            <spring:message code='action.reset.label'/>
                        </button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
<div class="confirmDialog" id="confirm-dialog">
</div>
<div id="dialog-area"></div>
