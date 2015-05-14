<%@include file="../taglib.jsp" %>
<spring:message var="cancelAction" code="action.cancel.label"/>
<spring:message code="basic.info.label" var="basicInfoLabel"/>


<div id="basic-info-dialog" title="${basicInfoLabel}">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-12">
                <form class="form-horizontal" id="basic-info-form">
                    <input type="hidden" name="merchantId" id="merchantId"
                           value="${merchantCommand.id}"/>

                    <div class="row">
                        <label class="col-sm-1 control-label" for="name">
                            <span class="required-indicator">*</span>
                            <spring:message code="name.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input size="64" name="name" id="name" class="form-control"
                                   required="" value="${merchantCommand.name}"/>
                        </div>
                        <label class="col-sm-1 control-label" for="merchantStatusId">
                            <span class="required-indicator">*</span>
                            <spring:message code="status.label"/>
                        </label>

                        <div class="col-sm-3">
                            <select name="merchantStatusId" id="merchantStatusId"
                                    class="form-control" required="">
                                <c:forEach items="${merchantStatuses}" var="status">
                                    <c:choose>
                                        <c:when test="${status.id == merchantCommand.merchantStatusId}">
                                            <option value="${status.id}" selected>
                                                    ${status.name}
                                            </option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${status.id}">
                                                    ${status.name}
                                            </option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <br>

                    <div class="row">
                        <label class="col-sm-1 control-label" for="email">
                            <spring:message code="email.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input size="32" name="email" id="email" class="form-control"
                                   value="${merchantCommand.email}"/>
                        </div>
                        <label class="col-sm-1 control-label" for="contact">
                            <spring:message code="contact.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input size="32" name="contact" id="contact"
                                   class="form-control"
                                   value="${merchantCommand.contact}"/>
                        </div>
                    </div>
                    <br>

                    <div class="row">
                        <label class="col-sm-1 control-label" for="address">
                            <spring:message code="address.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input size="64" name="address" id="address"
                                   class="form-control"
                                   value="${merchantCommand.address}"/>
                        </div>
                        <label class="col-sm-1 control-label" for="tel">
                            <spring:message code="tel.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input size="16" name="tel" id="tel" class="form-control"
                                   value="${merchantCommand.tel}"/>
                        </div>
                    </div>
                    <!-- basic info -->
                    <br>
                    <!-- credential -->
                    <div class="row">
                        <label class="col-sm-1 control-label" for="credentialContent">
                            <span class="required-indicator">*</span>
                            <spring:message code="content.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input size="32" name="credentialContent"
                                   id="credentialContent"
                                   class="form-control" required=""
                                   value="${merchantCommand.credentialContent}"/>
                        </div>
                        <label class="col-sm-1 control-label" for="credentialStatusId">
                            <span class="required-indicator">*</span>
                            <spring:message code="status.label"/>
                        </label>

                        <div class="col-sm-3">
                            <select name="credentialStatusId" id="credentialStatusId"
                                    class="form-control"
                                    required="">
                                <c:forEach items="${credentialStatuses}" var="status">
                                    <c:choose>
                                        <c:when test="{status.id == merchantCommand.credentialStatusId}">
                                            <option value="${status.id}" selected>
                                                    ${status.name}
                                            </option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${status.id}">
                                                    ${status.name}
                                            </option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <br>

                    <div class="row">
                        <label class="col-sm-1 control-label"
                               for="credentialExpirationTime">
                            <span class="required-indicator">*</span>
                            <spring:message code="validation.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input size="32" name="credentialExpirationTime"
                                   id="credentialExpirationTime"
                                   class="form-control datepicker" required=""
                                   value="${merchantCommand.credentialExpirationTime}"/>
                        </div>
                        <label class="col-sm-1 control-label" for="credentialTypeId">
                            <span class="required-indicator">*</span>
                            <spring:message code="type.label"/>
                        </label>

                        <div class="col-sm-3">
                            <select name="credentialTypeId" id="credentialTypeId"
                                    class="form-control"
                                    required="">
                                <c:forEach items="${credentialTypes}" var="type">
                                    <c:choose>
                                        <c:when test="${type.id == merchantCommand.credentialTypeId}">
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
                    <!-- remark -->
                    <div class="row">
                        <label class="col-sm-1 control-label" for="remark">
                            <spring:message code="remark.label"/>
                        </label>

                        <div class="col-sm-5">
                            <textarea cols="100" rows="5" class="form-control"
                                      name="remark" id="remark" value="">
                                ${merchantCommand.remark}
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
        $('.datepicker').datepicker();
        $("#basic-info-form").validate({
            rules: {
                name: {
                    required: true,
                    minlength: 3,
                    maxlength: 32
                }
            },
            merchantStatusId: {
                required: true
            },
            credentialContent: {
                required: true,
                minlength: 5,
                maxlength: 16
            },
            credentialStatusId: {
                required: true
            },
            credentialExpirationTime: {
                required: true,
                minlength: 3,
                maxlength: 16
            },
            credentialTypeId: {
                required: true,
            }
        });
    });
</script>