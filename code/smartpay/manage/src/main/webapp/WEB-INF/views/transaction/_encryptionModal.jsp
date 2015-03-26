<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@include file="../taglib.jsp" %>

<div id="edit-encryption-modal">
    <form method="POST"
          class="form-horizontal" id="edit-encryption-form">
        <input id="id" type="hidden" value="${encryptionCommand.id}"/>

        <div class="form-group">
            <label class="col-sm-3 control-label" for="encryptionKey">
                <spring:message code="key.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="col-sm-6">
                <input id="encryptionKey"
                       class="form-control" value="${encryptionCommand.encryptionKey}"/>
            </div>
        </div>
        <!-- merchant status -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="encryptionTypeId">
                <spring:message code="type.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="col-sm-6">
                <select id="encryptionTypeId"
                        value="${encryptionCommand.encryptionTypeId}"
                        class="form-control" required="">
                    <c:forEach items="${encryptionTypes}" var="type">
                        <option value="${type.id}">${type.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class='form-group'>
            <div class='col-sm-offset-3 col-sm-10'>
                <button class='btn btn-default' id='save-encryption-button' type="submit">
                    <spring:message code='action.save.label'/>
                </button>
                <button class='btn btn-default' id='reset-button' type="reset">
                    <spring:message code='action.reset.label'/>
                </button>
            </div>
        </div>
    </form>
    <!-- end of form-control -->
</div>

<script type="text/javascript">
    $(document).ready(function () {

        // save encryption handler
        $("#save-encryption-button").click(function (e) {
            e.preventDefault();

            /*
             if (!editForm.valid()) {
             return
             }
             */
            var id = $("#id").val();
            var encryptionKey = $("#encryptionKey").val();
            var encryptionTypeId = $("#encryptionTypeId").val();

            $.ajax({
                type: 'POST',
                url: "${rootURL}${controller}/editEncryption",
                data: {
                    id: id,
                    encryptionKey: encryptionKey,
                    encryptionTypeId: encryptionTypeId
                },
                error: function (data) {
                    alert("There was an error.");
                },
                success: function (data) {
                    $("#edit-encryption-modal").dialog("close");
                    var alert = "<div class='alert alert-warning alert-dismissible' role='alert'>" +
                            "<button type='button' class='close' data-dismiss='alert'>" +
                            "<span aria-hidden='true'>&times;</span>" +
                            "<span class='sr-only'>"
                            + "<spring:message code='action.close.label'/> "
                            + "</span></button>"
                            + data.message + "</div>";
                    $('#notification').append(alert);
                }
            });
        });

    });
</script>
