<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<c:if test="${domain != null}">
    <spring:message code="${domain}.label" var="entity"/>
</c:if>

<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <div class="widget-box">
                <div class="widget-title">
								<span class="icon">
									<i class="icon icon-align-justify"></i>
								</span>
                    <h5><b><spring:message code='index.label' arguments="${entity}"/></b></h5>
                </div>
                <div class="widget-content nopadding">

                    <form:form action="${rootURL}${controller}/payshipping" method="POST"
                               commandName="paymentCommand" cssClass="form-horizontal" id="auditpayment-form">
                        <form:input size="80" path="id" id="id" type="hidden" value="${paymentCommand.id}"/>

                        <div class="control-group">
                            <label class="col-sm-3 control-label" >
                                <span class="required-indicator">*</span>
                                <spring:message code="orderNumber.label"/>
                            </label>
                            <div class="controls">
                                ${paymentCommand.orderNumber}
                            </div>
                        </div>

                        <!-- bankTransactionNumber -->
                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="bankTransactionNumber">
                                <span class="required-indicator">*</span>
                                <spring:message code="bankTransactionNumber.label"/>
                            </label>

                            <div class="controls">
                                <form:input size="80" path="bankTransactionNumber" id="bankTransactionNumber" cssClass="text"
                                            value="${paymentCommand.bankTransactionNumber}"/>
                            </div>
                        </div>
                        <!-- url -->
                        <div class="control-group">
                            <label class="col-sm-3 control-label" for="currency">
                                <span class="required-indicator">*</span>
                                <spring:message code="currency.label"/>
                            </label>

                            <div class="controls">
                                <form:input size="80" path="currencyName" id="currencyName" cssClass="text" required=""
                                            value="${paymentCommand.currencyName}"/>
                            </div>
                        </div>
                        <div class='form-actions col-lg-offset-2'>
                                <button class='btn btn-success' id='create-button' type="submit">
                                    <spring:message code='action.save.label'/>
                                </button>
                                <a href="${rootURL}${controller}">
                                    <button type="button" class="btn btn-success">
                                        <spring:message code="action.return.label"/>
                                    </button>
                                </a>
                        </div>
                    </form:form>
                </div>

            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    $(document).ready(function () {
        $('#edit-payment-form').validate({
            rules: {
                firstName: {required: true, minlength: 3, maxlength: 32},
                lastName: {required: true, minlength: 3, maxlength: 32},
                url: {required: true, minlength: 3, maxlength: 32}
            }
        });
    });
</script>
