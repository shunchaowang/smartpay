<!DOCTYPE html>
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
            <a href="${rootURL}${controller}/${action}" class="current">
                <spring:message code="${action}.label" arguments="${entity}"/>
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
                        <table class="table display table-bordered data-table" id="shipment-table">
                            <thead>
                            <tr>
                                <th><spring:message code="id.label"/></th>
                                <th><spring:message code="orderNumber.label"/></th>
                                <th><spring:message code="amount.label"/></th>
                                <th><spring:message code="currency.label"/></th>
                                <th><spring:message code="createdTime.label"/></th>
                                <th><spring:message code="Site.label"/></th>
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
            "jQueryUI": true,
            'dom': 'T<""if>rt<"F"lp>',
            "tableTools": {
                "sSwfPath": "${tableTools}",
                "aButtons": [
                    {
                        "sExtends": "copy",
                        "mColumns": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
                    },
                    {
                        "sExtends": "xls",
                        "mColumns": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
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
                    'name': 'siteName', 'targets': 5, 'data': 'siteName',
                    'searchable': false, 'orderable': false
                },
                {
                    'name': 'siteUrl', 'targets': 6, 'searchable': true,
                    'orderable': false, 'data': 'siteUrl'
                },
                {'name': 'merchantName', 'targets': 7, 'data': 'merchantName'},
                {
                    'name': 'carrier', 'targets': 8, 'searchable': false,
                    'orderable': false, 'data': 'carrier'
                },
                {
                    'name': 'trackingNumber', 'targets': 9, 'searchable': false,
                    'orderable': false, 'data': 'trackingNumber'
                },
                {
                    'name': 'customerName', 'targets': 10, 'searchable': false,
                    'orderable': false, 'data': 'customerName'
                },
                {
                    'name': 'customerAddress', 'targets': 11, 'searchable': false,
                    'orderable': false, 'data': 'customerAddress'
                },
                {
                    'name': 'orderStatus', 'targets': 12, 'searchable': false,
                    'orderable': false, 'data': 'orderStatusName'
                }
            ]
        });
    });
</script>