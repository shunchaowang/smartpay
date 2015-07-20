<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="shipment.label" var="entity"/>

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
            <table class="table table-bordered" id="shipment-table">
                <thead>
                <tr>
                    <th><spring:message code="id.label"/></th>
                    <th><spring:message code="orderNumber.label"/></th>
                    <th><spring:message code="amount.label"/></th>
                    <th><spring:message code="currency.label"/></th>
                    <th><spring:message code="createdTime.label"/></th>
                    <th><spring:message code="site.url.label"/></th>
                    <th><spring:message code="carrier.label"/></th>
                    <th><spring:message code="trackingNumber.label"/></th>
                    <th><spring:message code="custom.label"/></th>
                    <th><spring:message code="address.label"/></th>
                    <th><spring:message code="status.label"/></th>
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
        var shipmentTable = $('#shipment-table').DataTable({
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
                        "mColumns": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
                    },
                    {
                        "sExtends": "xls",
                        "mColumns": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
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
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'orderId'},
                {
                    'name': 'merchantNumber', 'targets': 1, 'data': 'orderNumber'
                },
                {
                    'name': 'orderAmount', 'targets': 2, 'searchable': true,
                    'orderable': false, 'data': 'orderAmount'
                },
                {
                    'name': 'orderCurrency', 'targets': 3, 'searchable': true,
                    'orderable': false, 'data': 'orderCurrency'
                },
                {
                    'name': 'createdTime', 'targets': 4, 'searchable': false,
                    'data': 'createdTime'
                },
                {
                    'name': 'siteUrl', 'targets': 5, 'searchable': true,
                    'orderable': false, 'data': 'siteUrl'
                },
                {
                    'name': 'carrier', 'targets': 6, 'searchable': false,
                    'orderable': false, 'data': 'carrier'
                },
                {
                    'name': 'trackingNumber', 'targets': 7, 'searchable': false,
                    'orderable': false, 'data': 'trackingNumber'
                },
                {
                    'name': 'customerName', 'targets': 8, 'searchable': false,
                    'orderable': false, 'data': 'customerName'
                },
                {
                    'name': 'customerAddress', 'targets': 9, 'searchable': false,
                    'orderable': false, 'data': 'customerAddress'
                },
                {
                    'name': 'orderStatus', 'targets': 10, 'searchable': false,
                    'orderable': false, 'data': 'orderStatusName'
                }
            ]
        });
    });
</script>
