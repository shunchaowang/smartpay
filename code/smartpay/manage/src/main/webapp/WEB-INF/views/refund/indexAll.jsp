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
            <a href="${rootURL}${controller}/indexAll">
                <spring:message code="index.label" arguments="${entity}"/>
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
                        <table class="table display table-bordered data-table" id="refund-table">
                            <thead>
                            <tr>
                                <th><spring:message code="Merchant.label"/></th>
                                <th><spring:message code="Site.label"/></th>
                                <th>
                                    <spring:message code="Refund.label"/>
                                    <spring:message code="id.label"/>
                                </th>
                                <th>
                                    <spring:message code="Refund.label"/>
                                    <spring:message code="amount.label"/>
                                </th>
                                <th><spring:message code="createdTime.label"/></th>
                                <th>
                                    <spring:message code="Order.label"/>
                                    <spring:message code="merchantNumber.label"/>
                                </th>
                                <th>
                                    <spring:message code="Order.label"/>
                                    <spring:message code="amount.label"/>
                                </th>
                                <th><spring:message code="currency.label"/></th>
                                <th><spring:message code="custom.label"/></th>
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
        var table = $('#refund-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': true,
            'serverSide': true,
            'paging': true,
            "paginationType": "full_numbers",
            "order": [[2, "desc"]],
            "jQueryUI": true,
            'dom': 'T<""if>rt<"F"lp>',
            "tableTools": {
                "sSwfPath": "${tableTools}",
                "aButtons": [
                    {
                        "sExtends": "copy",
                        "mColumns": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
                    },
                    {
                        "sExtends": "xls",
                        "mColumns": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
                    }
                ]
            },

            'ajax': {
                'url': "${rootURL}${controller}/listAll",
                'type': "GET",
                'dataType': 'json'
            },
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {
                    'name': 'merchant', 'targets': 0, 'visible': true, 'orderable': false,
                    'data': 'merchantIdentity'
                },
                {
                    'name': 'site', 'targets': 1, 'visible': true, 'orderable': false,
                    'data': 'siteIdentity'
                },
                {'name': 'id', 'targets': 2, 'visible': true, 'data': 'id'},
                {
                    'name': 'amount', 'targets': 3, 'data': 'amount',
                    'searchable': false, 'orderable': false
                },
                {
                    'name': 'createdTime', 'targets': 4, 'searchable': false,
                    'data': 'createdTime'
                },
                {
                    'name': 'merchantNumber',
                    'targets': 5,
                    'searchable': false,
                    'orderable': false,
                    'data': 'orderNumber'
                },
                {
                    'name': 'orderAmount',
                    'targets': 6,
                    'searchable': false,
                    'orderable': false,
                    'data': 'orderAmount'
                },
                {
                    'name': 'orderCurrency',
                    'targets': 7,
                    'searchable': false,
                    'orderable': false,
                    'data': 'orderCurrency'
                },
                {
                    'name': 'customer',
                    'targets': 8,
                    'searchable': false,
                    'orderable': false,
                    'data': 'customerName'
                },
                {
                    'name': 'refundStatus',
                    'targets': 9,
                    'searchable': false,
                    'orderable': false,
                    'data': 'refundStatusName'
                }
            ]
        });
    });
</script>