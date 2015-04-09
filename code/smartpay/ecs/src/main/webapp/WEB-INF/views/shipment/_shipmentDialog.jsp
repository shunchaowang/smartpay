<%@include file="../taglib.jsp" %>
<spring:message var="shipmentDomain" code="Shipping.label"/>
<spring:message var="formTitle" code="create.label" arguments="${shipmentDomain}"/>
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

<div id="shipment-dialog" title="${formTitle}">
    <form class="form-horizontal" id="new-shipment-form">
        <input id="orderId" name="orderId" value="${orderId}" type="hidden"/>
        <!-- carrier -->
        <div class="control-group">
            <label class="col-sm-1 control-label" for="carrier">
                <spring:message code="carrier.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="controls">
                <input size="40" name="carrier" id="carrier"
                       class="text" required=""
                       placeholder="Carrier"/>
            </div>
        </div>
        <!-- tracking number -->
        <div class="control-group">
            <label class="col-sm-1 control-label" for="trackingNumber">
                <spring:message code="trackingNumber.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="controls">
                <input size="40" name="trackingNumber" id="trackingNumber"
                       class="text" required=""
                       placeholder="Tracking Number"/>
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
