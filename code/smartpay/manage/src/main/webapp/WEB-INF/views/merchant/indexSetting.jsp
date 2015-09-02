<%@include file="../taglib.jsp" %>

<spring:message code="merchant.label" var="entity"/>
<spring:message code="transaction.label" var="transaction"/>

<div class="container-fluid">
    <div class="row">
        <ol class="breadcrumb">
            <li>
                    <i class="glyphicon glyphicon-home"></i>
                    <spring:message code="home.label"/>
            </li>
            <li>
                    <i class="glyphicon glyphicon-list"></i>
                    <spring:message code="index.label" arguments="${entity}"/>
            </li>
            <li class="active">
                    <i class="glyphicon glyphicon-list"></i>
                    <spring:message code="manage.label" arguments="${transaction}"/>
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
                    <th>
                        <spring:message code="commissionVisaFee.label"/>
                        <spring:message code="commission.fee.label"/>
                    </th>
                    <th>
                        <spring:message code="commissionVisaFee.label"/>
                        <spring:message code="commission.fee.label"/>
                        <spring:message code="type.label"/>
                    </th>
                    <th>
                        <spring:message code="commissionMasterFee.label"/>
                        <spring:message code="commission.fee.label"/>
                    </th>
                    <th>
                        <spring:message code="commissionMasterFee.label"/>
                        <spring:message code="commission.fee.label"/>
                        <spring:message code="type.label"/>
                    </th>
                    <th>
                        <spring:message code="commissionJcbFee.label"/>
                        <spring:message code="commission.fee.label"/>
                    </th>
                    <th>
                        <spring:message code="commissionJcbFee.label"/>
                        <spring:message code="commission.fee.label"/>
                        <spring:message code="type.label"/>
                    </th>
                    <th>
                        <spring:message code="withdrawal.label"/>
                        <spring:message code="withdrawalSecurityFee.label"/>
                    </th>
                    <th>
                        <spring:message code="withdrawal.label"/>
                        <spring:message code="withdrawalSecurityFee.label"/>
                        <spring:message code="type.label"/>
                    </th>
                    <th>
                        <spring:message code="withdrawal.label"/>
                        <spring:message code="withdrawalMinDays.label"/>
                    </th>
                    <th>
                        <spring:message code="withdrawal.label"/>
                        <spring:message code="withdrawalMaxDays.label"/>
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
            /*
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
            */
            'ajax': {
                'url': "${rootURL}merchant/list/setting",
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
                    'name': 'commissionVisaFeeValue',
                    'targets': 5,
                    'searchable': false,
                    'orderable': false,
                    'data': 'commissionVisaFeeValue'
                },
                {
                    'name': 'commissionVisaFeeTypeName', 'targets': 6, 'searchable': false,
                    'orderable': false, 'data': 'commissionVisaFeeTypeName'
                },
                {
                    'name': 'commissionMasterFeeValue',
                    'targets': 7,
                    'searchable': false,
                    'orderable': false,
                    'data': 'commissionMasterFeeValue'
                },
                {
                    'name': 'commissionMasterFeeTypeName', 'targets': 8, 'searchable': false,
                    'orderable': false, 'data': 'commissionMasterFeeTypeName'
                },
                {
                    'name': 'commissionJcbFeeValue',
                    'targets': 9,
                    'searchable': false,
                    'orderable': false,
                    'data': 'commissionJcbFeeValue'
                },
                {
                    'name': 'commissionJcbFeeTypeName', 'targets': 10, 'searchable': false,
                    'orderable': false, 'data': 'commissionJcbFeeTypeName'
                },
                {
                    'name': 'withdrawalSecurityFeeValue',
                    'targets': 11,
                    'searchable': false,
                    'orderable': false,
                    'data': 'withdrawalSecurityFeeValue'
                },
                {
                    'name': 'withdrawalSecurityFeeTypeName', 'targets': 12, 'searchable': false,
                    'orderable': false, 'data': 'withdrawalSecurityFeeTypeName'
                },
                {
                    'name': 'withdrawalSettingMinDays',
                    'targets': 13,
                    'searchable': false,
                    'orderable': false,
                    'data': 'withdrawalSettingMinDays'
                },
                {
                    'name': 'withdrawalSettingMaxDays', 'targets': 14, 'searchable': false,
                    'orderable': false, 'data': 'withdrawalSettingMaxDays'
                },

                {
                    'name': 'operation', 'targets': 15, 'searchable': false, 'orderable': false,
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
                url: "${rootURL}merchant/edit/setting",
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
                            url: "${rootURL}merchant/edit/setting",
                            data: {
                                id: $("#merchantId").val(),
                                encryptionKey: $("#encryptionKey").val(),
                                encryptionType: $("#encryptionTypeId").val(),
                                commissionVisaFeeValue: $("#commissionVisaFeeValue").val(),
                                commissionVisaFeeType: $("#commissionVisaFeeTypeId").val(),
                                commissionMasterFeeValue: $("#commissionMasterFeeValue").val(),
                                commissionMasterFeeType: $("#commissionMasterFeeTypeId").val(),
                                commissionJcbFeeValue: $("#commissionJcbFeeValue").val(),
                                commissionJcbFeeType: $("#commissionJcbFeeTypeId").val(),
                                withdrawFeeValue: $("#withdrawFeeValue").val(),
                                withdrawFeeType: $("#withdrawFeeTypeId").val(),

                                withdrawSettingMinDays: $("#withdrawSettingMinDays").val(),
                                withdrawSettingMaxDays: $("#withdrawSettingMaxDays").val(),
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