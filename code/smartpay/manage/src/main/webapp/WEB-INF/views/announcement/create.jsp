<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="announcement.label" var="entity"/>

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
        <div class="col-sm-12">
            <form:form action="${rootURL}announcement/create" method="POST"
                       commandName="announcementCommand" cssClass="form-horizontal"
                       id="new-announcement-form">


                <div class="row">
                    <label class="col-sm-1 control-label" for="titleName">
                        <span>*</span>
                        <spring:message code="announcement.label"/>
                    </label>


                    <div class="col-sm-3">
                        <form:input size="80" path="title" id="titleName" cssClass="text"
                                    required=""
                                    placeholder="TitleName"/>
                    </div>
                </div>

                <div class="row">

                    <label class="col-sm-1 control-label" for="announcementContent">

                        <span class="required-indicator">*</span>
                        <spring:message code="content.label"/>
                    </label>

                    <div class="col-sm-3">
                        <form:textarea cols="120" rows="10" path="content" id="announcementContent"/>
                    </div>
                </div>

                <div class='form-group'>
                    <div class="col-sm-2 col-sm-offset-2">

                        <button class='btn btn-success' id='create-button' type="submit">
                            <spring:message code='action.save.label'/>
                        </button>
                        <button class='btn btn-success' id='reset-button' type="reset">
                            <spring:message code='action.reset.label'/>
                        </button>
                    </div>
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