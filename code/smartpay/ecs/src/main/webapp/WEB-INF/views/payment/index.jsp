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
            <c:if test="${domain != null}">
                <spring:message code="${domain}.label" var="entity"/>
                <a href="${rootURL}${controller}">
                    <spring:message code="manage.label" arguments="${entity}"/>
                </a>
                <a href="${rootURL}${controller}/${action}" class="current">
                    <spring:message code="${action}.label" arguments="${entity}"/>
                </a>
            </c:if>
        </div>
    </div>
    <!-- reserved for notification -->
    <!-- close of content-header -->
    <div class="container-fluid">
        <!— actual content —>
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
        </div>
    </div>
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
                        "mColumns": [1,2,3,4,5,6,7,8,9,10]
                    },
                    {
                        "sExtends": "xls",
                        "mColumns": [1,2,3,4,5,6,7,8,9,10]
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