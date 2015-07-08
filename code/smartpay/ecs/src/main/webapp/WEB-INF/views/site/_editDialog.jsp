<%@include file="../taglib.jsp" %>
<spring:message var="cancelAction" code="action.cancel.label"/>
<spring:message var="entity" code="site.label"/>
<spring:message code="edit.label" var="editLabel" arguments="${entity}"/>


<div id="edit-dialog" title="${editLabel}">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-12">
                <form class="form-horizontal" id="edit-form">
                    <input type="hidden" name="siteId" id="siteId"
                           value="${siteCommand.id}"/>

                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="name">
                            <span class="required-indicator">*</span>
                            <spring:message code="name.label"/>
                        </label>

                        <div class="col-sm-4">
                            <input size="32" name="name" id="name" class="form-control"
                                   required="" value="${siteCommand.name}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="returnUrl">
                            <span class="required-indicator">*</span>
                            <spring:message code="site.returnUrl.label"/>
                        </label>

                        <div class="col-sm-4">
                            <input size="80" id="returnUrl" name="returnUrl"
                                   class="form-control"
                                   required="" value="${siteCommand.returnUrl}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="url">
                            <span class="required-indicator">*</span>
                            <spring:message code="site.url.label"/>
                        </label>

                        <div class="col-sm-4">
                            <input size="80" id="url" name="url"
                                   class="form-control"
                                   required="" value="${siteCommand.url}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="remark">
                            <spring:message code="remark.label"/>
                        </label>

                        <div class="col-sm-4">
                            <textarea cols="100" rows="5" name="remark"
                                      id="remark" class="form-control">
                                ${siteCommand.remark}
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
                name: {required: true, minlength: 3, maxlength: 32},
                returnUrl: {required: true, minlength: 3, maxlength: 32},
                url: {required: true, minlength: 3, maxlength: 32}
            }
        });
    });
</script>