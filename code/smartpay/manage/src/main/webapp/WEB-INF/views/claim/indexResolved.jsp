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
            <a href="${rootURL}${controller}/indexResolved" class="current">
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
            'dom': '<""if>rt<"F"lp>',

            'ajax': {
                'url': "${rootURL}${controller}/listResolved",
                'type': "GET",
                'dataType': 'json'
            },
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {
                    'name': 'orderNumber', 'targets': 1, 'data': 'orderNumber',
                    'render': function (data, type, row) {
                        return '<a href=' + "${rootURL}${controller}" + '/show/'
                                + row['id'] + '>' + data + '</a>';
                    }
                },
                {
                    'name': 'bankTransactionNumber', 'targets': 2, 'data': 'bankTransactionNumber',
                    'render': function (data, type, row) {
                        return '<a href=' + "${rootURL}${controller}" + '/show/'
                                + row['id'] + '>' + data + '</a>';
                    }
                },
                {
                    'name': 'bankName', 'targets': 3, 'searchable': false, 'orderable': false,
                    'data': 'bankName'
                },
                {
                    'name': 'amount', 'targets': 4, 'data': 'amount',
                    'render': function (data, type, row) {
                        return '<a href=' + "${rootURL}${controller}" + '/show/'
                                + row['id'] + '>' + data + '</a>';
                    }
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
                        return '<a href="' + "${rootURL}${controller}" + '/edit/'
                                + row['id'] + '">' +
                                '<button type="button" name="edit-button" class="btn btn-default"'
                                + '">' + '<spring:message code="action.edit.label"/>'
                                + '</button></a>';
                    }
                }
            ]
        });
    });
</script>