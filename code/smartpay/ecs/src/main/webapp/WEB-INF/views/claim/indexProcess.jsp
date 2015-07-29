<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="refuse.label" var="entity"/>

<spring:message code="freeze.confirm.message" arguments="${entity}" var="freezeMsg"/>
<spring:message code="approve.confirm.message" arguments="${entity}" var="approveMsg"/>
<spring:message code="unfreeze.confirm.message" arguments="${entity}" var="unfreezeMsg"/>
<spring:message code="archive.confirm.message" arguments="${entity}" var="archiveMsg"/>
<spring:message code="decline.confirm.message" arguments="${entity}" var="declineMsg"/>
<spring:message code="action.delete.label" var="deleteLabel"/>
<spring:message code="action.cancel.label" var="cancelLabel"/>
<spring:message code="action.freeze.label" var="freezeLabel"/>
<spring:message code="action.unfreeze.label" var="unfreezeLabel"/>
<spring:message code="action.approve.label" var="approveLabel"/>
<spring:message code="action.decline.label" var="declineLabel"/>
<spring:message code="action.archive.label" var="archiveLabel"/>
<spring:message code="status.created.label" var="createdStatus"/>
<spring:message code="status.frozen.label" var="frozenStatus"/>
<spring:message code="status.approved.label" var="approvedStatus"/>
<spring:message code="status.declined.label" var="declinedStatus"/>

<div class="container-fluid">
    <div class="row">
        <ol class="breadcrumb">
            <li>
                <i class="glyphicon glyphicon-home"></i>
                <spring:message code="home.label"/>
            </li>
            <li class="active">
                <i class="glyphicon glyphicon-list"></i>
                <spring:message code="index.label" arguments="${entity}"/>
            </li>
        </ol>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <table class="table table-bordered" id="payment-table">
                <thead>
                <tr>
                    <th><spring:message code="id.label"/></th>
                    <th><spring:message code="orderNumber.label"/></th>
                    <th><spring:message code="bankTransactionNumber.label"/></th>
                    <th><spring:message code="bankName.label"/></th>
                    <th><spring:message code="amount.label"/></th>
                    <th><spring:message code="currency.label"/></th>
                    <th><spring:message code="createdTime.label"/></th>
                    <th><spring:message code="returnCode.label"/></th>
                    <th><spring:message code="paymentStatusName.label"/></th>
                    <th><spring:message code="paymentTypeName.label"/></th>
                    <th><spring:message code="action.operation.label"/></th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
</div>
<div class="confirmDialog" id="confirm-dialog">
</div>
<div id="dialog-area"></div>


<div id="content">

</div>

<script type="text/javascript">
    $(document).ready(function () {
        var paymentTable = $('#payment-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': true,
            'serverSide': true,
            'paging': true,
            "paginationType": "full_numbers",
            "order": [[0, "desc"]],
            "tableTools": {
                "sSwfPath": "${tableTools}",
                "aButtons": [
                    {
                        "sExtends": "copy",
                        "mColumns": [1, 2, 3, 4, 5, 6, 7, 8, 9]
                    },
                    {
                        "sExtends": "xls",
                        "mColumns": [1, 2, 3, 4, 5, 6, 7, 8, 9]
                    }
                ]
            },

            'ajax': {
                'url': "${rootURL}${controller}/list/refuse",
                'type': "GET",
                'dataType': 'json'
            },
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {
                    'name': 'orderNumber', 'targets': 1, 'data': 'orderNumber'
                },
                {
                    'name': 'bankTransactionNumber', 'targets': 2, 'data': 'bankTransactionNumber'
                },
                {
                    'name': 'bankName', 'targets': 3, 'searchable': false, 'orderable': false,
                    'data': 'bankName'
                },
                {
                    'name': 'amount', 'targets': 4, 'data': 'amount'
                },
                {
                    'name': 'currencyName', 'targets': 5, 'searchable': false, 'orderable': false,
                    'data': 'currencyName'
                },
                {
                    'name': 'createdTime', 'targets': 6, 'searchable': false,
                    'data': 'createdTime'
                },
                {
                    'name': 'bankReturnCode', 'targets': 7, 'searchable': false, 'orderable': false,
                    'data': 'bankReturnCode'
                },
                {
                    'name': 'paymentStatusName',
                    'targets': 8,
                    'searchable': false,
                    'orderable': false,
                    'data': 'paymentStatusName'
                },
                {
                    'name': 'paymentTypeName',
                    'targets': 9,
                    'searchable': false,
                    'orderable': false,
                    'data': 'paymentTypeName'
                },
                {
                    'name': 'operation', 'targets': 10, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        return '<button type="button" name="show-claim-button"'
                                + ' class="tableButton" value="' + row['id'] + '">'
                                + '<spring:message code="action.show.label"/>'
                                + '</button>'
                                + '<button type="button" name="add-claim-button"'
                                + ' class="tableButton" value="' + row['id'] + '">'
                                + '<spring:message code="action.claim.label"/>'
                                + '</button>';
                    }
                }
            ]
        });

        // add live handler for show button
        paymentTable.on('click', 'button[type=button][name=show-claim-button]', function
                (event) {
            event.preventDefault();
            $.ajax({
                type: 'get',
                url: "${rootURL}${controller}/showClaim",
                data: {
                    paymentId: this.value
                },
                error: function () {
                    alert('There was an error.');
                },
                success: function (data) {
                    $('#dialog-area').append(data);

                    // define dialog
                    var claimDialog = $("#claim-dialog").dialog({
                        autoOpen: false,
                        height: 'auto',
                        width: 600,
                        modal: true,
                        dialogClass: "dialogClass",
                        open: function (event, ui) {
                            $(".ui-dialog-titlebar-close", ui.dialog || ui).hide();
                        },
                        close: function () {
                            claimDialog.dialog("destroy").remove();
                        }
                    }).dialog("open");

                    $("#close-button").click(function (event) {
                        event.preventDefault();
                        claimDialog.dialog("close");
                    });
                }
            });
        });

        // add live handler for add claim button
        paymentTable.on('click', 'button[type=button][name=add-claim-button]', function
                (event) {
            event.preventDefault();
            $.ajax({
                type: 'get',
                url: "${rootURL}${controller}/addClaim",
                data: {
                    paymentId: this.value
                },
                error: function () {
                    alert('There was an error.');
                },
                success: function (data) {
                    $('#dialog-area').append(data);

                    // define dialog
                    var claimDialog = $("#claim-dialog").dialog({
                        autoOpen: false,
                        height: 'auto',
                        width: 600,
                        modal: true,
                        dialogClass: "dialogClass",
                        open: function (event, ui) {
                            $(".ui-dialog-titlebar-close", ui.dialog || ui).hide();
                        },
                        close: function () {
                            claimDialog.dialog("destroy").remove();
                        }
                    }).dialog("open");

                    $("#cancel-button").click(function (event) {
                        event.preventDefault();
                        claimDialog.dialog("close");
                    });

                    $("#save-button").click(function (event) {
                        event.preventDefault();

                        // update submit button to indicate busy
                        //$(this).find("#save-button").attr("disabled","disabled").addClass("busy");
                        // get the data of the file
                        var formData = new FormData();

                        var file = $("#file")[0].files[0];
                        var fileSize = (file.size / 1024 / 1024).toFixed(2);
                        if (fileSize > 20) {
                            alert('The size of file ' + file.name + ' is ' +
                                    fileSize + 'M exceeding 20M limit ' +
                                    'file size.');
                            return;
                        }

                        formData.append("paymentId", $("#paymentId").val());
                        formData.append("remark", $("#remark").val());
                        formData.append("file", file);
                        $.ajax({
                            type: "POST",
                            url: "${rootURL}${controller}/addClaim",
                            // set mimeType to be multipart form to
                            // upload file
                            mimeType: "multipart/form-data",
                            // set content type to false or jQuery will tell
                            // the server its a query string request
                            contentType: false,
                            // don't process the files or jQuery will convert
                            // the files array into strings, and server
                            // cannot pick it up
                            processData: false,
                            cache: false,
                            data: formData,
                            dataType: "json",
                            error: function (data) {
                                alert("There was an error");
                            },
                            success: function (data) {
                                var alert = "<div class='alert alert-warning alert-dismissible' role='alert'>" +
                                        "<button type='button' class='close' data-dismiss='alert'>" +
                                        "<span aria-hidden='true'>&times;</span>" +
                                        "<span class='sr-only'>"
                                        + "<spring:message code='action.close.label'/> "
                                        + "</span></button>"
                                        + data.message + "</div>";
                                $('#notification').append(alert);
                                claimDialog.dialog("close");
                                paymentTable.ajax.reload();
                            }
                        });
                        //});
                    });

                    $("#new-claim-form").validate({
                        rules: {
                            remark: {
                                required: true,
                                minlength: 5,
                                maxlength: 32
                            }
                        }
                    });
                }
            });
        });
    });
</script>