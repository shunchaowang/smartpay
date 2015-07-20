<%@include file="../taglib.jsp" %>
<spring:message var="cancelAction" code="action.cancel.label"/>
<spring:message var="entity" code="refund.label"/>
<spring:message code="refund.label" var="refundLabel" arguments="${entity}"/>

<div id="refund-dialog" title="${refundLabel}">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-12">
                <form class="form-horizontal" id="new-refund-form">
                    <input id="orderId" name="orderId" value="${orderId}" type="hidden"/>
                    <!-- amount -->
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="amount">
                            <span class="required-indicator">*</span>
                            <spring:message code="amount.label"/>
                        </label>

                        <div class="col-sm-4">
                            <input size="40" name="amount" id="amount"
                                   class="text" required=""  placeholder="amount"/>
                        </div>
                    </div>
                    <!-- tracking number -->
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="remark">
                            <span class="required-indicator">*</span>
                            <spring:message code="remark.label"/>
                        </label>

                        <div class="col-sm-4">
                            <input size="40" name="remark" id="remark"
                                   class="text" required="" placeholder="Remark"/>
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
        $("#new-refund-form").validate({
            rules: {
                carrier: {required: true, minlength: 3, maxlength: 32},
                trackingNumber: {required: true, minlength: 3, maxlength: 40}
            }
        });
    });
</script>
