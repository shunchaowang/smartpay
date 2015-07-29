<%@include file="../taglib.jsp" %>
<spring:message var="cancelAction" code="action.cancel.label"/>
<spring:message var="entity" code="refuse.label"/>
<spring:message code="claim.label" var="claimLabel" arguments="${entity}"/>

<div id="claim-dialog" title="${claimLabel}">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-12">
                <form class="form-horizontal" id="new-claim-form">
                    <input id="paymentId" name="paymentId" value="${paymentId}" type="hidden"/>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="file">
                            <spring:message code="file.label"/>
                        </label>

                        <div class="controls">
                            <input type="file" id="file" name="file" size="50"/>
                        </div>
                    </div>
                    <!-- tracking number -->
                    <div class="form-group">
                        <label class="col-sm-1 control-label" for="remark">
                            <spring:message code="remark.label"/>
                            <span class="required-indicator">*</span>
                        </label>

                        <div class="controls">
                            <textarea rows="4" cols="50" name="remark" id="remark"
                          class="text" required=""></textarea>
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
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>