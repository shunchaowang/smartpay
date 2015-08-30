<%@include file="../taglib.jsp" %>
<spring:message var="transactionLabel" code="transaction.label"/>
<spring:message code="setting.label" arguments="${transactionLabel}" var="settingLabel"/>


<div id="setting-dialog" title="${settingLabel}">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-12">
                <form class="form-horizontal" id="setting-form">
                    <input type="hidden" name="merchantId" id="merchantId"
                           value="${merchantCommand.id}"/>

                    <div class="row">
                        <label class="col-sm-2 control-label" for="encryptionKey">
                            <span>*</span>
                            <spring:message code="key.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input size="32" name="encryptionKey" id="encryptionKey"
                                   class="form-control"
                                   required="" value="${merchantCommand.encryptionKey}"/>
                        </div>
                        <label class="col-sm-2 control-label" for="encryptionTypeId">
                            <span>*</span>
                            <spring:message code="type.label"/>
                        </label>

                        <div class="col-sm-3">
                            <select name="encryptionTypeId" id="encryptionTypeId"
                                    class="form-control" required="">
                                <c:forEach items="${encryptionTypes}" var="type">
                                    <c:choose>
                                        <c:when test="${type.id == merchantCommand.encryptionTypeId}">
                                            <option value="${type.id}" selected>
                                                    ${type.name}
                                            </option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${type.id}">
                                                    ${type.name}
                                            </option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <br>

                    <!-- fees -->
                    <!-- visa -->
                    <div class="row">
                        <label class="col-sm-2 control-label" for="commissionVisaFeeValue">
                            <span>*</span>
                            <spring:message code="commissionVisaFee.label"/>
                            <spring:message code="commission.fee.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input size="32" name="commissionVisaFeeValue"
                                   id="commissionVisaFeeValue"
                                   class="form-control"
                                   required="" value="${merchantCommand.commissionVisaFeeValue}"/>
                        </div>
                        <label class="col-sm-2 control-label" for="commissionVisaFeeTypeId">
                            <span>*</span>
                            <spring:message code="type.label"/>
                        </label>

                        <div class="col-sm-3">
                            <select name="commissionVisaFeeTypeId" id="commissionVisaFeeTypeId"
                                    class="form-control" required="">
                                <c:forEach items="${feeTypes}" var="type">
                                    <c:choose>
                                        <c:when test="${type.id == merchantCommand.commissionVisaFeeTypeId}">
                                            <option value="${type.id}" selected>
                                                    ${type.name}
                                            </option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${type.id}">
                                                    ${type.name}
                                            </option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <br>

                    <!-- master -->
                    <div class="row">
                        <label class="col-sm-2 control-label" for="commissionMasterFeeValue">
                            <span>*</span>
                            <spring:message code="commissionMasterFee.label"/>
                            <spring:message code="commission.fee.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input size="32" name="commissionMasterFeeValue"
                                   id="commissionMasterFeeValue"
                                   class="form-control"
                                   required="" value="${merchantCommand.commissionMasterFeeValue}"/>
                        </div>
                        <label class="col-sm-2 control-label" for="commissionMasterFeeTypeId">
                            <span>*</span>
                            <spring:message code="type.label"/>
                        </label>

                        <div class="col-sm-3">
                            <select name="commissionVisaFeeTypeId" id="commissionMasterFeeTypeId"
                                    class="form-control" required="">
                                <c:forEach items="${feeTypes}" var="type">
                                    <c:choose>
                                        <c:when test="${type.id == merchantCommand.commissionMasterFeeTypeId}">
                                            <option value="${type.id}" selected>
                                                    ${type.name}
                                            </option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${type.id}">
                                                    ${type.name}
                                            </option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <br>
                    <!-- jcb -->
                    <div class="row">
                        <label class="col-sm-2 control-label" for="commissionJcbFeeValue">
                            <span>*</span>
                            <spring:message code="commissionJcbFee.label"/>
                            <spring:message code="commission.fee.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input size="32" name="commissionJcbFeeValue" id="commissionJcbFeeValue"
                                   class="form-control"
                                   required="" value="${merchantCommand.commissionJcbFeeValue}"/>
                        </div>
                        <label class="col-sm-2 control-label" for="commissionJcbFeeTypeId">
                            <span>*</span>
                            <spring:message code="type.label"/>
                        </label>

                        <div class="col-sm-3">
                            <select name="commissionJcbFeeTypeId" id="commissionJcbFeeTypeId"
                                    class="form-control" required="">
                                <c:forEach items="${feeTypes}" var="type">
                                    <c:choose>
                                        <c:when test="${type.id == merchantCommand.commissionJcbFeeTypeId}">
                                            <option value="${type.id}" selected>
                                                    ${type.name}
                                            </option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${type.id}">
                                                    ${type.name}
                                            </option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <br>
                    <!-- withdrawal security fee -->
                    <div class="row">
                        <label class="col-sm-2 control-label" for="withdrawFeeValue">
                            <span>*</span>
                            <spring:message code="withdrawal.label"/>
                            <spring:message code="withdrawalSecurityFee.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input size="32" name="withdrawFeeValue" id="withdrawFeeValue"
                                   class="form-control"
                                   required="" value="${merchantCommand.withdrawFeeValue}"/>
                        </div>
                        <label class="col-sm-2 control-label" for="withdrawFeeTypeId">
                            <span>*</span>
                            <spring:message code="type.label"/>
                        </label>

                        <div class="col-sm-3">
                            <select name="withdrawFeeTypeId" id="withdrawFeeTypeId"
                                    class="form-control" required="">
                                <c:forEach items="${feeTypes}" var="type">
                                    <c:choose>
                                        <c:when test="${type.id == merchantCommand.withdrawFeeTypeId}">
                                            <option value="${type.id}" selected>
                                                    ${type.name}
                                            </option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${type.id}">
                                                    ${type.name}
                                            </option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <br>

                    <!-- withdrawal settings -->
                    <div class="row">
                        <label class="col-sm-2 control-label" for="withdrawSettingMinDays">
                            <span>*</span>
                            <spring:message code="withdrawal.label"/>
                            <spring:message code="withdrawalMinDays.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input size="32" name="withdrawSettingMinDays"
                                        id="withdrawSettingMinDays"
                                        class="form-control"
                                        required=""
                                        value="${merchantCommand.withdrawSettingMinDays}"/>
                        </div>
                        <label class="col-sm-2 control-label" for="withdrawSettingMaxDays">
                            <span>*</span>
                            <spring:message code="withdrawal.label"/>
                            <spring:message code="withdrawalMaxDays.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input size="32" name="withdrawSettingMaxDays"
                                        id="withdrawSettingMaxDays"
                                        class="form-control"
                                        required=""
                                        value="${merchantCommand.withdrawSettingMaxDays}"/>
                        </div>
                    </div>
                    <br>

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
        $("#setting-form").validate({
            rules: {
                encryptionKey: {
                    required: true,
                    minlength: 3,
                    maxlength: 16
                },
                encryptionKeyTypeId: {
                    required: true
                },
                commissionVisaFeeValue: {
                    required: true,
                    minlength: 1,
                    maxlength: 8
                },
                commissionVisaFeeTypeId: {
                    required: true
                },
                commissionMasterFeeValue: {
                    required: true,
                    minlength: 1,
                    maxlength: 8
                },
                commissionMasterFeeTypeId: {
                    required: true
                },
                commissionJcbFeeValue: {
                    required: true,
                    minlength: 1,
                    maxlength: 8
                },
                commissionJcbFeeTypeId: {
                    required: true
                },
                withdrawFeeValue: {
                    required: true,
                    minlength: 1,
                    maxlength: 8
                },
                withdrawFeeTypeId: {
                    required: true
                },
                withdrawSettingMinDays: {
                    required: true,
                    minlength: 1,
                    maxlength: 8
                },
                withdrawSettingMaxDays: {
                    required: true,
                    minlength: 1,
                    maxlength: 8
                }
            }
        });
    });
</script>