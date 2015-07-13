<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="payment.label" var="entity"/>

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
                    <th><spring:message code="amount.label"/></th>
                    <th><spring:message code="currency.label"/></th>
                    <th><spring:message code="paymentCreatedTime.label"/></th>
                    <th><spring:message code="returnCode.label"/></th>
                    <th><spring:message code="paymentStatusName.label"/></th>
                    <th><spring:message code="paymentTypeName.label"/></th>
                    <th><spring:message code="paymentSiteName.label"/></th>
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
                'url': "${rootURL}${controller}/list",
                'type': "GET",
                'dataType': 'json'
            },
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {
                    'name': 'orderNumber', 'targets': 1, 'data': 'orderNumber',
                    'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        return '<a href=' + "${rootURL}${controller}" + '/show/'
                                + row['id'] + '>' + data + '</a>';
                    }
                },
                {
                    'name': 'bankTransactionNumber', 'targets': 2, 'data': 'bankTransactionNumber',
                    'orderable': false
                },
                {
                    'name': 'amount', 'targets': 3, 'data': 'amount',
                    'searchable': false, 'orderable': false
                },
                {
                    'name': 'currencyName', 'targets': 4, 'searchable': false, 'orderable': false,
                    'data': 'currencyName'
                },
                {
                    'name': 'createdTime', 'targets': 5, 'searchable': false,
                    'data': 'createdTime'
                },
                {
                    'name': 'bankReturnCode', 'targets': 6, 'searchable': false, 'orderable': false,
                    'data': 'bankReturnCode'
                },
                {
                    'name': 'paymentStatusName',
                    'targets': 7,
                    'searchable': false,
                    'orderable': false,
                    'data': 'paymentStatusName'
                },
                {
                    'name': 'paymentTypeName',
                    'targets': 8,
                    'searchable': false,
                    'orderable': false,
                    'data': 'paymentTypeName'
                },
                {
                    'name': 'siteName',
                    'targets': 9,
                    'searchable': false,
                    'orderable': false,
                    'data': 'siteName'
                }

            ]
        });
    });
</script>