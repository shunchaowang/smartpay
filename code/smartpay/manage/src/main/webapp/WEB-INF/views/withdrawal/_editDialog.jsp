<%@include file="../taglib.jsp" %>
<spring:message var="cancelAction" code="action.cancel.label"/>
<spring:message var="entity" code="withdrawal.label"/>
<spring:message code="edit.label" var="editLabel" arguments="${entity}"/>


<div id="edit-dialog" title="${editLabel}">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-12">
                <form class="form-horizontal" id="edit-form">
                    <input type="hidden" name="withdrawalId" id="withdrawalId"
                           value="${withdrawalCommand.id}"/>

                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="balance">
                            <span class="required-indicator">*</span>
                            <spring:message code="wdrlBalance.label"/>
                        </label>

                        <div class="col-sm-4">
                            <input size="32" name="balance" id="balance" class="form-control"
                                   required="" value="${withdrawalCommand.balance}" readonly ="true"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="securityDeposit">
                            <span class="required-indicator">*</span>
                            <spring:message code="securityDeposit.label"/>
                        </label>

                        <div class="col-sm-4">
                            <input size="80" id="securityDeposit" name="securityDeposit"
                                   class="form-control"
                                   required="" value="${withdrawalCommand.securityDeposit}"  readonly ="true"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="amount">
                            <span class="required-indicator">*</span>
                            <spring:message code="wdrlAmount.label"/>
                        </label>

                        <div class="col-sm-4">
                            <input size="80" id="amount" name="amount"
                                   class="form-control"
                                   required="" value="${withdrawalCommand.amount}"  readonly ="true"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="adjustAmt">
                            <span class="required-indicator">*</span>
                            <spring:message code="wdrlAmountAdjusted.label"/>
                        </label>

                        <div class="col-sm-4">
                            <input size="80" id="adjustAmt" name="adjustAmt"
                                   class="form-control"
                                   required="" value="${withdrawalCommand.amountAdjusted}"
                                   max="${amount}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="remark">
                            <spring:message code="remark.label"/>
                        </label>

                        <div class="col-sm-4">
                            <textarea cols="100" rows="5" name="remark"
                                      id="remark" class="form-control">
                                ${withdrawalCommand.remark}
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