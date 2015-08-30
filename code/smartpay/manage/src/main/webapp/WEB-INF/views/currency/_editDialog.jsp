<%@include file="../taglib.jsp" %>
<spring:message var="cancelAction" code="action.cancel.label"/>
<spring:message var="entity" code="currencyExchange.label"/>
<spring:message code="edit.label" var="editLabel" arguments="${entity}"/>


<div id="edit-dialog" title="${editLabel}">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-12">
                <form class="form-horizontal" id="edit-form">
                    <input type="hidden" name="exchangeId" id="exchangeId"
                           value="${currencyExCommand.id}"/>

                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="currency">
                            <span class="required-indicator">*</span>
                            <spring:message code="currency.label"/>
                        </label>

                        <div class="col-sm-4">
                            <input size="32" name="currency" id="currency" class="form-control"
                                   required="" value="${currencyExCommand.crexCurrencyFrom}" readonly="true"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="tradingUnit">
                            <span class="required-indicator">*</span>
                            <spring:message code="tradingUnit.label"/>
                        </label>

                        <div class="col-sm-4">
                            <input size="80" id="tradingUnit" name="tradingUnit"
                                   class="form-control"
                                   required="" value="${currencyExCommand.crexAmountFrom}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="exchangePrice">
                            <span class="required-indicator">*</span>
                            <spring:message code="currencyExchange.label"/><spring:message code="price.label"/>
                        </label>

                        <div class="col-sm-4">
                            <input size="80" id="exchangePrice" name="exchangePrice"
                                   class="form-control"
                                   required="" value="${currencyExCommand.crexAmountTo}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="remark">
                            <spring:message code="remark.label"/>
                        </label>

                        <div class="col-sm-4">
                            <textarea cols="100" rows="5" name="remark"
                                      id="remark" class="form-control">
                                ${currencyExCommand.crexRemark}
                            </textarea>
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
        $("#edit-form").validate({
            rules: {
                tradingUnit: {required: true, minlength: 1, maxlength: 32},
                exchangePrice: {required: true, minlength: 1, maxlength: 32}
            }
        });
    });
</script>