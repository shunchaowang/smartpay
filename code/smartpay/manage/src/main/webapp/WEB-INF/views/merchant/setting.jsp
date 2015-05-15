<%@include file="../taglib.jsp" %>

<spring:message code="merchant.label" var="entity"/>
<spring:message code="transaction.label" var="transaction"/>

<div class="container-fluid">
    <div class="row">
        <ol class="breadcrumb">
            <li>
                <a href="${rootURL}">
                    <i class="glyphicon glyphicon-home"></i>
                    <spring:message code="home.label"/>
                </a>
            </li>
            <li>
                <a href="${rootURL}merchant/index">
                    <i class="glyphicon glyphicon-list"></i>
                    <spring:message code="index.label" arguments="${entity}"/>
                </a>
            </li>
            <li class="active">
                <a href="${rootURL}merchant/setting">
                    <i class="glyphicon glyphicon-list"></i>
                    <spring:message code="manage.label" arguments="${transaction}"/>
                </a>
            </li>
        </ol>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <table class="table table-bordered" id="merchant-table">
                <thead>
                <tr>
                    <th><spring:message code="id.label"/></th>
                    <th><spring:message code="identity.label"/></th>
                    <th><spring:message code="name.label"/></th>
                    <th><spring:message code="key.label"/></th>
                    <th>
                        <spring:message code="key.label"/>
                        <spring:message code="type.label"/>
                    </th>
                    <th><spring:message code="commission.fee.label"/></th>
                    <th>
                        <spring:message code="commission.fee.label"/>
                        <spring:message code="type.label"/>
                    </th>
                    <th><spring:message code="return.fee.label"/></th>
                    <th>
                        <spring:message code="return.fee.label"/>
                        <spring:message code="type.label"/>
                    </th>
                    <th><spring:message code="action.operation.label"/></th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
</div>

<div id="dialog-area"></div>

<script type="text/javascript">
    $(document).ready(function () {
        var merchantTable = $('#merchant-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': true,
            'serverSide': true,
            'paging': true,
            "paginationType": "full_numbers",
            "order": [[1, "desc"]],
            //"jQueryUI": true,
            /*'dom': 'T<""if>rt<"F"lp>',*/
            "tableTools": {
                "sSwfPath": "${tableTools}",
                "aButtons": [
                    {
                        "sExtends": "copy",
                        "mColumns": [1, 2, 3, 4, 5, 6, 7,8]
                    },
                    {
                        "sExtends": "xls",
                        "mColumns": [1, 2, 3, 4, 5, 6, 7,8]
                    }
                ]
            },
            'ajax': {
                'url': "${rootURL}merchant/listSetting",
                'type': "GET",
                'dataType': 'json'
            },
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {
                    'name': 'identity', 'targets': 1, 'data': 'identity'
                },
                {
                    'name': 'name', 'targets': 2, 'data': 'name', 'orderable': false
                },
                {
                    'name': 'encryptionKey', 'targets': 3, 'searchable': false, 'orderable': false,
                    'data': 'encryptionKey'
                },
                {
                    'name': 'encryptionType',
                    'targets': 4,
                    'searchable': false,
                    'orderable': false,
                    'data': 'encryptionType'
                },
                {
                    'name': 'commissionFeeValue',
                    'targets': 5,
                    'searchable': false,
                    'orderable': false,
                    'data': 'commissionFeeValue'
                },
                {
                    'name': 'commissionFeeType', 'targets': 6, 'searchable': false,
                    'orderable': false, 'data': 'commissionFeeType'
                },
                {
                    'name': 'returnFeeValue',
                    'targets': 7,
                    'searchable': false,
                    'orderable': false,
                    'data': 'returnFeeValue'
                },
                {
                    'name': 'returnFeeType', 'targets': 8, 'searchable': false,
                    'orderable': false, 'data': 'returnFeeType'
                },

                {
                    'name': 'operation', 'targets': 9, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        return '<button type="button" name="edit-button" '
                                + 'class="btn btn-default" value="' + row['id'] + '">'
                                + '<spring:message code="action.edit.label"/>'
                                + '</button>';
                    }
                }
            ]
        });

        // add live handler for edit button
        merchantTable.on('click', 'button[type=button][name=edit-button]', function
                (event) {
            event.preventDefault();
            $.ajax({
                type: 'get',
                url: "${rootURL}merchant/editSetting",
                data: {
                    merchantId: this.value
                },
                error: function () {
                    alert('There was an error.');
                },
                success: function (data) {
                    $('#dialog-area').append(data);

                    // define dialog
                    var settingDialog = $("#setting-dialog").dialog({
                        autoOpen: false,
                        height: 'auto',
                        width: 'auto',
                        modal: true,
                        close: function () {
                            settingDialog.dialog("destroy").remove();
                        }
                    }).dialog("open");

                    $("#cancel-button").click(function (event) {
                        event.preventDefault();
                        settingDialog.dialog("close");
                    });

                    $("#save-button").click(function (event) {
                        event.preventDefault();
                        if (!$("#setting-form").valid()) {
                            return;
                        }
                        $.ajax({
                            type: "POST",
                            url: "${rootURL}merchant/editSetting",
                            data: {
                                id: $("#merchantId").val(),
                                encryptionKey: $("#encryptionKey").val(),
                                encryptionType: $("#encryptionTypeId").val(),
                                commissionFeeValue: $("#commissionFeeValue").val(),
                                commissionFeeType: $("#commissionFeeTypeId").val(),
                                returnFeeValue: $("#returnFeeValue").val(),
                                returnFeeType: $("#returnFeeTypeId").val()
                            },
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
                                settingDialog.dialog("close");
                                merchantTable.ajax.reload();
                            }
                        });
                    });
                }
            });
        });

    });
</script>