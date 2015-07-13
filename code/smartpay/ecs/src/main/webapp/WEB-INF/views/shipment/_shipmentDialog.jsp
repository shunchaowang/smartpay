<%@include file="../taglib.jsp" %>
<spring:message var="cancelAction" code="action.cancel.label"/>
<spring:message var="entity" code="shipment.label"/>
<spring:message code="shipment.label" var="shipmentLabel" arguments="${entity}"/>

<div id="shipment-dialog" title="${shipmentLabel}">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-12">
                <form class="form-horizontal" id="new-shipment-form">
                    <input id="orderId" name="orderId" value="${orderId}" type="hidden"/>
                    <!-- carrier -->
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="carrier">
                            <span class="required-indicator">*</span>
                            <spring:message code="carrier.label"/>
                        </label>

                        <div class="col-sm-4">
                            <input size="32" name="carrier" id="carrier" class="text"
                                   required="" placeholder="Carrier"/>
                        </div>
                    </div>
                    <!-- tracking number -->
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="trackingNumber">
                            <span class="required-indicator">*</span>
                            <spring:message code="trackingNumber.label"/>
                        </label>

                        <div class="col-sm-4">
                            <input size="40" name="trackingNumber" id="trackingNumber"
                                   class="text" required=""  placeholder="Tracking Number"/>
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
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $("#new-shipment-form").validate({
            rules: {
                carrier: {required: true, minlength: 3, maxlength: 32},
                trackingNumber: {required: true, minlength: 3, maxlength: 40}
            }
        });
    });
</script>