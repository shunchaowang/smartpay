<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="refund.label" var="entity"/>

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
                'url': "${rootURL}${controller}/listResolved",
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
    });
</script>