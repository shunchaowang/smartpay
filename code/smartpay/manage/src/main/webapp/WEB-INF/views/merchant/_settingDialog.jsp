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

                    <div class="row">
                        <label class="col-sm-2 control-label" for="commissionFeeValue">
                            <span>*</span>
                            <spring:message code="commission.fee.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input size="32" name="commissionFeeValue" id="commissionFeeValue"
                                   class="form-control"
                                   required="" value="${merchantCommand.commissionFeeValue}"/>
                        </div>
                        <label class="col-sm-2 control-label" for="commissionFeeTypeId">
                            <span>*</span>
                            <spring:message code="type.label"/>
                        </label>

                        <div class="col-sm-3">
                            <select name="commissionFeeTypeId" id="commissionFeeTypeId"
                                    class="form-control" required="">
                                <c:forEach items="${feeTypes}" var="type">
                                    <c:choose>
                                        <c:when test="${type.id == merchantCommand.commissionFeeTypeId}">
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

                    <div class="row">
                        <label class="col-sm-2 control-label" for="returnFeeValue">
                            <span>*</span>
                            <spring:message code="return.fee.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input size="32" name="returnFeeValue" id="returnFeeValue"
                                   class="form-control"
                                   required="" value="${merchantCommand.returnFeeValue}"/>
                        </div>
                        <label class="col-sm-2 control-label" for="returnFeeTypeId">
                            <span>*</span>
                            <spring:message code="type.label"/>
                        </label>

                        <div class="col-sm-3">
                            <select name="returnFeeTypeId" id="returnFeeTypeId"
                                    class="form-control" required="">
                                <c:forEach items="${feeTypes}" var="type">
                                    <c:choose>
                                        <c:when test="${type.id == merchantCommand.returnFeeTypeId}">
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
                commissionFeeValue: {
                    required: true,
                    minlength: 1,
                    maxlength: 8
                },
                commissionFeeTypeId: {
                    required: true
                },
                returnFeeValue: {
                    required: true,
                    minlength: 1,
                    maxlength: 8
                },
                returnFeeTypeId: {
                    required: true
                }
            }
        });
    });
</script>