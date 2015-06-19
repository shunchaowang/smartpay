<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="shipment.label" var="entity"/>
<spring:message code="details.label" arguments="{entity}"/>


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
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="col-sm-12">
                <div class="widget-box">
                    <div class="widget-title">
                        <span class="icon"><i class="icon icon-th"></i> </span>
                        <h5><spring:message code="index.label" arguments="${entity}"/></h5>
                    </div>
                    <div class="widget-content">
                        <table class="table display table-bordered data-table" id="shipment-table">
                            <thead>
                            <tr>
                                <th><spring:message code="id.label"/></th>
                                <th><spring:message code="orderNumber.label"/></th>
                                <th><spring:message code="amount.label"/></th>
                                <th><spring:message code="currency.label"/></th>
                                <th><spring:message code="createdTime.label"/></th>
                                <th><spring:message code="site.url.label"/></th>
                                <th><spring:message code="site.merchant.label"/></th>
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
        </div>
    </div>
</div>

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
            'dom': 'T<""if>rt<"F"lp>',
            "tableTools": {
                "sSwfPath": "${tableTools}",
                "aButtons": [
                    {
                        "sExtends": "copy",
                        "mColumns": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
                    },
                    {
                        "sExtends": "xls",
                        "mColumns": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
                    }
                ]
            },

            'ajax': {
                'url': "${rootURL}shipment/list/all",
                'type': "GET",
                'dataType': 'json'
            },
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
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
                {'name': 'merchantName', 'targets': 6, 'data': 'merchantName'},
                {
                    'name': 'carrier', 'targets': 7, 'searchable': false,
                    'orderable': false, 'data': 'carrier'
                },
                {
                    'name': 'trackingNumber', 'targets': 8, 'searchable': false,
                    'orderable': false, 'data': 'trackingNumber'
                },
                {
                    'name': 'customerName', 'targets': 9, 'searchable': false,
                    'orderable': false, 'data': 'customerName'
                },
                {
                    'name': 'customerAddress', 'targets': 10, 'searchable': false,
                    'orderable': false, 'data': 'customerAddress'
                },
                {
                    'name': 'orderStatus', 'targets': 11, 'searchable': false,
                    'orderable': false, 'data': 'orderStatusName'
                }
            ]
        });
    });
</script>