<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
    <spring:message code="site.label" var="entity"/>


<div class="container-fluid">
    <div class="row">
        <ol class="breadcrumb">
            <li>
                <i class="glyphicon glyphicon-home"></i>
                <spring:message code="home.label"/>
            </li>
            <li>
                <i class="glyphicon glyphicon-list"></i>
                <spring:message code="index.label" arguments="${entity}"/>
            </li>
            <li class="active">
                <i class="glyphicon glyphicon-wrench"></i>
                <spring:message code="create.label" arguments="${entity}"/>
            </li>
        </ol>
    </div>
    <div class="row">
        <div class="col-sm-8">
            <form:form action="${rootURL}site/create" method="POST"
                       commandName="siteCommand" cssClass="form-horizontal"
                       id="new-user-form">

                <div class="form-group">
                    <label class="col-sm-2 control-label" for="merchant">
                        <span class="required-indicator">*</span>
                        <spring:message code="site.merchant.label"/>
                    </label>

                    <div class="col-sm-4">
                        <form:select path="merchant" id="merchant" cssClass="form-control"
                                     required="" value="${siteCommand.merchant}">
                            <c:forEach items="${merchants}" var="merchant">
                                <form:option value="${merchant.id}">${merchant.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">
                        <span class="required-indicator">*</span>
                        <spring:message code="name.label"/>
                    </label>

                    <div class="col-sm-4">
                        <form:input size="32" path="name" id="name" cssClass="form-control"
                                    required="" value="${siteCommand.name}"
                                    placeholder="Name"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="returnUrl">
                        <span class="required-indicator">*</span>
                        <spring:message code="site.returnUrl.label"/>
                    </label>

                    <div class="col-sm-4">
                        <form:input size="80" path="returnUrl" id="returnUrl"
                                    cssClass="form-control"
                                    required="" value="${siteCommand.returnUrl}"
                                    placeholder="Return URL"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="url">
                        <span class="required-indicator">*</span>
                        <spring:message code="site.url.label"/>
                    </label>

                    <div class="col-sm-4">
                        <form:input size="80" path="url" id="url"
                                    cssClass="form-control"
                                    required="" value="${siteCommand.url}"
                                    placeholder="URL"/>
                    </div>
                </div>
                <!-- site status -->
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="siteStatusId">
                        <span class="required-indicator">*</span>
                        <spring:message code="status.label"/>
                    </label>

                    <div class="col-sm-4">
                        <form:select path="siteStatusId" id="siteStatusId" cssClass="form-control"
                                     required="" value="${siteCommand.siteStatusId}">
                            <c:forEach items="${siteStatuses}" var="status">
                                <form:option value="${status.id}">${status.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="remark">
                        <spring:message code="remark.label"/>
                    </label>

                    <div class="col-sm-4">
                        <form:textarea cols="100" rows="5" path="remark" id="remark"
                                       cssClass="form-control"/>
                    </div>
                </div>
                <div class='form-group'>
                    <div class="col-sm-2 col-sm-offset-2">
                        <button class='btn btn-default' id='create-button' type="submit">
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


<script type="text/javascript">
    $(document).ready(function () {
        $('#new-site-form').validate({
            rules: {
                merchant: {required: true},
                name: {required: true, minlength: 3, maxlength: 32},
                returnUrl: {required: true, minlength: 3, maxlength: 32},
                url: {required: true, minlength: 3, maxlength: 32},
                siteStatusId: {required: true}
            }
        });
    });
</script>
