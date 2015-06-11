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
            <a href="${rootURL}${controller}/index">
                <spring:message code="manage.label" arguments="${entity}"/>
            </a>
            <a href="${rootURL}${controller}/${action}" class="current">
                <spring:message code="create.label" arguments="${entity}"/>
            </a>
        </div>
    </div>
    <!-- close of content-header -->
    <div class="container-fluid">
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
                        <form:form action="${rootURL}${controller}/create" method="POST"
                                   commandName="announcementCommand" cssClass="form-horizontal"
                                   id="new-announcement-form">
                            <div class="control-group">
                                <h5>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <spring:message code="basic.info.label"/>
                                </h5>
                            </div>
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="titleName">
                                    <span class="required-indicator">*</span>
                                    <spring:message code="announcement.title.lable"/>
                                </label>

                                <div class="controls">
                                    <form:input size="80" path="title" id="titleName" cssClass="text"
                                                required=""
                                                placeholder="TitleName"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="col-sm-3 control-label" for="announcementContent">
                                    <span class="required-indicator">*</span>
                                    <spring:message code="announcement.content.lable"/>
                                </label>
                                <div class="controls">
                                    <form:textarea cols="120" rows="10" path="content" id="announcementContent"/>
                                </div>
                            </div>
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
        $('#new-announcement-form').validate({
            rules: {
                title: {required: true, minlength: 3, maxlength: 60},
                content: {required: true}
            }
        });

        $('.datepicker').datepicker();
    });
</script>