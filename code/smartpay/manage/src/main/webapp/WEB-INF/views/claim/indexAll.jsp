<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="claim.label" var="entity"/>
<spring:message code="details.label" arguments="${entity}"/>


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
    <br>

    <!-- close of content-header -->
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
        </div>
    </div>
    <div id="dialog-area"></div>
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
            'dom': 'T<""if>rt<"F"lp>',
            "tableTools": {
                "sSwfPath": "${tableTools}",
                "aButtons": [
                    {
                        "sExtends": "copy",
                        "mColumns": [1, 2, 3, 4, 5, 6, 7, 8, 10]
                    },
                    {
                        "sExtends": "xls",
                        "mColumns": [1, 2, 3, 4, 5, 6, 7, 8, 10]
                    }
                ]
            },

            'ajax': {
                'url': "${rootURL}claim/list/all",
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
                    'name': 'paymentStatusName',
                    'targets': 9,
                    'visible': false,
                    'data': 'paymentStatusName'
                },
                {
                    'name': 'paymentTypeName',
                    'targets': 10,
                    'searchable': false,
                    'orderable': false,
                    'data': 'paymentTypeName'
                },
                {
                    'name': 'operation', 'targets': 11, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {

                        var operations = '';
                        if (row['paymentStatus'] == 'Approved') { // if the merchant is Initiated
                            operations += '<button type="button" name="new-claim-button"'
                            + ' data-identity="' + row['id'] + '"'
                            + ' class="btn btn-default" value="' + row['id'] + '">' +
                            "${newClaimLabel}"
                            + '</button>';
                        } else if(row['paymentStatus'] == 'Process') {
                        // if the merchant is Initiated{
                            operations += '<button type="button" name="show-claim-button"'
                            + ' data-identity="' + row['id'] + '"'
                            + ' class="btn btn-default" value="' + row['id'] + '">' +
                            "${showClaimLabel}"
                            + '</button>';
                        } else if(row['paymentStatus'] == 'Resolved') {
                        // if the merchant is Initiated{
                            operations += '<button type="button" name="show-claim-button"'
                            + ' data-identity="' + row['id'] + '"'
                            + ' class="btn btn-default" value="' + row['id'] + '">' +
                            "${showClaimLabel}"
                            + '</button>';
                            operations +=' ';

                            operations += '<button type="button" name="add-claim-button"'
                            + ' data-identity="' + row['id'] + '"'
                            + ' class="btn btn-default" value="' + row['id'] + '">' +
                            "${addClaimLabel}"
                            + '</button>';
                            operations +=' ';

                            operations += '<button type="button" name="audit-claim-button"'
                            + ' data-identity="' + row['id'] + '"'
                            + ' class="btn btn-default" value="' + row['id'] + '">' +
                            "${auditClaimLabel}"
                            + '</button>';
                        }
                        return operations;
                    }
                }
            ]
        });

        // add live handler for add refund button
        paymentTable.on('click', 'button[type=button][name=new-claim-button]', function
                (event) {
            event.preventDefault();
            $.ajax({
                type: 'get',
                url: "${rootURL}claim/addClaim",
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
                        var fileSize = (file.size/1024/1024).toFixed(2);
                        if(fileSize > 20) {
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
                            url: "${rootURL}claim/addClaim",
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

        // add live handler for audit claim button
        paymentTable.on('click', 'button[type=button][name=audit-claim-button]', function
                (event) {
            event.preventDefault();
            $.ajax({
                type: 'post',
                url: "${rootURL}claim/auditClaim",
                data: {
                    paymentId: this.value
                },
                error: function () {
                    alert('There was an error.');
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
                    paymentTable.ajax.reload();
                }
            });
        });

        // add live handler for show button
        paymentTable.on('click', 'button[type=button][name=show-claim-button]', function
                (event) {
            event.preventDefault();
            $.ajax({
                type: 'get',
                url: "${rootURL}claim/showClaim",
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
                        //dialogClass: "dialogClass",
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
    });
</script>