<%@include file="../taglib.jsp" %>
<spring:message var="refundDomain" code="shipment.label"/>
<spring:message var="formTitle" code="create.label" arguments="${refundDomain}"/>
<spring:message var="cancelAction" code="action.cancel.label"/>

<style media="screen" type="text/css">

    .dialogClass {
        background-color: #e5e5e5;
        padding: 5px;
    }

    .dialogClass .ui-dialog-titlebar-close {
        display: none;
    }

</style>

<div id="refund-dialog" title="${formTitle}">
    <form class="form-horizontal" id="new-refund-form">
        <input id="orderId" name="orderId" value="${orderId}" type="hidden"/>
        <!-- amount -->
        <div class="form-group">
            <label class="col-sm-1 control-label" for="amount">
                <spring:message code="amount.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="controls">
                <input size="40" name="amount" id="amount"
                       class="text" required=""
                       placeholder="Amount"/>
            </div>
        </div>
        <!-- tracking number -->
        <div class="form-group">
            <label class="col-sm-1 control-label" for="remark">
                <spring:message code="remark.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="controls">
                <input size="40" name="remark" id="remark"
                       class="text" required=""
                       placeholder="Remark"/>
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
