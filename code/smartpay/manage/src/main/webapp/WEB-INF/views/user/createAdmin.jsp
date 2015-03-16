<!DOCTYPE html>
<%@include file="../taglib.jsp" %>

<div class="row">
    <div class="col-xs-6 pull-left">
        <h2><b><spring:message code="user.new.label"/></b></h2>
    </div>
    <!-- end of pull-left -->
</div>
<!-- end of class row -->
<div class='row' id='notification'>
    <c:if test="${not empty message}">
        <div class="alert alert-warning alert-dismissable" role="alert">
            <button type="button" class="close" data-dismiss="alert">
                <span aria-hidden="true">&times;</span>
                <span class="sr-only"><spring:message code="close.button.label"/> </span>
                    ${message}
            </button>
        </div>
    </c:if>
</div>
<!-- end of notification -->
<br/>
<div class='row'>
    <div class='col-sm-2'>
        <h2><b><spring:message code='user.new.admin.label'/></b></h2>
    </div>
</div>
<br>
<div class="row">
    <form:form action="${rootURL}/${controller}/createAdmin" method="POST"
               commandName="userCommand" cssClass="form-horizontal" id="new-user-form">
        <div class="form-group">
            <label class="col-sm-3 control-label" for="username">
                <spring:message code="user.username.label"/>
                <span class="required-indicator">*</span>
            </label>
            <div class="col-sm-6">
                <form:input path="username" id="username" cssClass="form-control" required=""
                    placeholder="Username"/>
            </div>
        </div>
    </form:form>
</div>

<script type="text/javascript">
    $(document).ready(function () {

    });
</script>