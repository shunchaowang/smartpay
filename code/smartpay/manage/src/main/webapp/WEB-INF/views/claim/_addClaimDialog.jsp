<%@include file="../taglib.jsp" %>
<spring:message code="${domain}.label" var="entity"/>
<spring:message var="formTitle" code="create.label" arguments="${entity}"/>

<style media="screen" type="text/css">

    .dialogClass {
        background-color: #e5e5e5;
        padding: 5px;
    }

    .dialogClass .ui-dialog-titlebar-close {
        display: none;
    }

</style>

<div id="claim-dialog" title="${formTitle}">
    <form class="form-horizontal" id="new-claim-form" enctype="multipart/form-data">
        <input id="paymentId" name="paymentId" value="${paymentId}" type="hidden"/>
        <!-- amount -->
        <div class="control-group">
            <label class="col-sm-1 control-label" for="file">
                <spring:message code="file.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="controls">
                <input type="file" id="file" name="file" size="50"/>
            </div>
        </div>
        <!-- tracking number -->
        <div class="control-group">
            <label class="col-sm-1 control-label" for="remark">
                <spring:message code="remark.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="controls">
                <textarea rows="4" cols="50" name="remark" id="remark"
                       class="text" required="">
                </textarea>
            </div>
        </div>
        <!-- buttons -->
        <div class='form-actions col-sm-offset-4'>
            <button class='tableButton' id='save-button'>
                <spring:message code='action.save.label'/>
            </button>
            <button class='tableButton col-sm-offset-2' id='cancel-button'>
                <spring:message code='action.cancel.label'/>
            </button>
        </div>
    </form>
</div>
