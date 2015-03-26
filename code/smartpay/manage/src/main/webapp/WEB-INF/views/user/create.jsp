<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<c:if test="${domain != null}">
    <spring:message code="${domain}.label" var="entity"/>
</c:if>

<div class='row' id='notification'>
    <c:if test="${not empty message}">
        <div class="alert alert-danger alert-dismissable" role="alert">
            <button type="button" class="close" data-dismiss="alert">
                <span aria-hidden="true">&times;</span>
                <span class="sr-only"><spring:message code="action.close.label"/> </span>
            </button>
                ${message}
        </div>
    </c:if>
</div>
<!-- end of notification -->
<br/>

<div class='row'>
    <div class='col-sm-4'>
        <h2><b><spring:message code='create.label' arguments="${entity}"/></b></h2>
    </div>
</div>
<br>

<div class="row">
    <form:form action="${rootURL}${controller}/create${domain}" method="POST"
               commandName="userCommand" cssClass="form-horizontal" id="new-user-form">
        <div class="form-group">
            <label class="col-sm-3 control-label" for="username">
                <spring:message code="username.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="col-sm-6">
                <form:input path="username" id="username" cssClass="form-control" required=""
                            placeholder="Username"/>
            </div>
        </div>
        <!-- first name -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="firstName">
                <spring:message code="firstName.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="col-sm-6">
                <form:input path="firstName" id="firstName" cssClass="form-control" required=""
                            placeholder="First Name"/>
            </div>
        </div>
        <!-- last name -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="lastName">
                <spring:message code="lastName.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="col-sm-6">
                <form:input path="lastName" id="lastName" cssClass="form-control" required=""
                            placeholder="Last Name"/>
            </div>
        </div>
        <!-- email -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="email">
                <spring:message code="email.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="col-sm-6">
                <form:input path="email" id="email" cssClass="form-control" required=""
                            placeholder="Email"/>
            </div>
        </div>
        <!-- user status -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="userStatus">
                <spring:message code="status.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="col-sm-6">
                <form:select path="userStatus" id="userStatus" cssClass="form-control" required=""
                             placeholder="Status">
                    <c:forEach items="${userStatuses}" var="status">
                        <form:option value="${status.id}">${status.name}</form:option>
                    </c:forEach>
                </form:select>
            </div>
        </div>
        <!-- if create a merchant admin we need to have merchant selection -->
        <c:if test="${domain == 'MerchantAdmin'}">
            <!-- user status -->
            <div class="form-group">
                <label class="col-sm-3 control-label" for="userStatus">
                    <spring:message code="Merchant.label"/>
                    <span class="required-indicator">*</span>
                </label>

                <div class="col-sm-6">
                    <form:select path="merchant" id="merchant" cssClass="form-control" required="">
                        <c:forEach items="${merchants}" var="merchant">
                            <form:option value="${merchant.id}">${merchant.name}</form:option>
                        </c:forEach>
                    </form:select>
                </div>
            </div>
        </c:if>
        <!-- remark -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="remark">
                <spring:message code="remark.label"/>
            </label>

            <div class="col-sm-6">
                <form:input path="remark" id="remark" cssClass="form-control"/>
            </div>
        </div>
        <div class='form-group'>
            <div class='col-sm-offset-3 col-sm-10'>
                <button class='btn btn-default' id='create-button' type="submit">
                    <spring:message code='action.save.label'/>
                </button>
                <button class='btn btn-default' id='reset-button' type="reset">
                    <spring:message code='action.reset.label'/>
                </button>
            </div>
        </div>
    </form:form>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#new-user-form').validate({
            rules: {
                username: {required: true, minlength: 3, maxlength: 32},
                firstName: {required: true, minlength: 3, maxlength: 32},
                lastName: {required: true, minlength: 3, maxlength: 32},
                email: {required: true, email: true, minlength: 3, maxlength: 32},
                userStatus: {required: true}
            }
        });
    });
</script>