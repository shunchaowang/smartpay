<%@include file="../taglib.jsp" %>
<c:if test="${domain != null}">
    <spring:message code="${domain}.label" var="entity"/>
</c:if>

<div id="content">
    <div id="content-header">
        <div id="breadcrumb">
            <a href="${rootURL}">
                <i class="icon icon-home"></i>
                <spring:message code="home.label"/>
            </a>
            <a href="${rootURL}${controller}/indexResolved">
                <spring:message code="index.label" arguments="${entity}"/>
            </a>
            <a href="${rootURL}${controller}/indexProcess" class="current">
                <spring:message code="manage.label" arguments="${entity}"/>
            </a>
        </div>
    </div>

    <!-- close of content-header -->
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="col-sm-12">
                <div class="widget-box">
                    <div class="widget-title">
                        <span class="icon"><i class="icon icon-th"></i> </span>
                        <h5><spring:message code="index.label" arguments="${entity}"/></h5>
                    </div>
                    <div class="widget-content">
                        <table class="table display table-bordered data-table" id="payment-table">
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
            "jQueryUI": true,
            'dom': 'T<""if>rt<"F"lp>',
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
                'url': "${rootURL}${controller}/listProcess",
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
                                + '<spring:message code="action.revoke.label"/>'
                                + '</button>'
                                + '<button type="button" name="audit-claim-button"'
                                + ' class="tableButton" value="' + row['id'] + '">'
                                + '<spring:message code="action.complete.label"/>'
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

        // add live handler for audit claim button
        paymentTable.on('click', 'button[type=button][name=audit-claim-button]', function
                (event) {
            event.preventDefault();
            $.ajax({
                type: 'post',
                url: "${rootURL}${controller}/auditClaim",
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
    });
</script>