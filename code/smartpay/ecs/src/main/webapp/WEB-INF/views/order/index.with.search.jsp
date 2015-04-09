<%@include file="../taglib.jsp" %>
<c:if test="${domain != null}">
    <spring:message code="${domain}.label" var="entity"/>
</c:if>


<div class="row-fluid">
    <div class="col-sm-12">
        <div class="col-sm-4 pull-right">
            <a href="${rootURL}${controller}/search">
                <h3>
                    <button type="button" class="btn btn-default pull-right">
                        <spring:message code="action.advanceSearch.label"/>
                    </button>
                </h3>
            </a>
        </div>
        <div class="widget-box">
            <div class="widget-title">
                <span class="icon"><i class="icon icon-th"></i> </span>
                <h5><spring:message code="index.label" arguments="${entity}"/></h5>
            </div>
            <div class="widget-content">
                <table class="table display table-bordered data-table" id="order-table">

                    <thead>
                    <tr>
                        <th><spring:message code="id.label"/></th>
                        <th><spring:message code="merchantNumber.label"/></th>
                        <th><spring:message code="amount.label"/></th>
                        <th><spring:message code="currency.label"/></th>
                        <th><spring:message code="Site.label"/></th>
                        <th><spring:message code="Customer.label"/></th>
                        <th><spring:message code="createdTime.label"/></th>
                        <th><spring:message code="status.label"/></th>
                        <th><spring:message code="action.operation.label"/></th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {

        var orderTable = $('#order-table').DataTable({
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
                'url': "${rootURL}${controller}/list${domain}",
                'type': "GET",
                'dataType': 'json'
            },
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'data': 'id', 'searchable': false},
                {
                    'name': 'merchantNumber', 'targets': 1, 'data': 'merchantNumber',
                    'render': function (data, type, row) {
                        return '<a href=' + "${rootURL}${controller}" + '/show${domain}/'
                                + row['id'] + '>' + data + '</a>';
                    }
                },
                {
                    'name': 'amount', 'targets': 2, 'data': 'amount',
                    'searchable': false, 'orderable': false
                },
                {
                    'name': 'currencyName', 'targets': 3, 'data': 'currencyName',
                    'searchable': false, 'orderable': false
                },
                {
                    'name': 'siteName', 'targets': 4, 'data': 'siteName',
                    'searchable': false, 'orderable': false
                },
                {
                    'name': 'customerName', 'targets': 5, 'data': 'customerName',
                    'searchable': false, 'orderable': false
                },
                {'name': 'createdTime', 'targets': 6, 'searchable': false, 'data': 'createdTime'},
                {
                    'name': 'orderStatusName', 'targets': 7, 'searchable': false,
                    'orderable': false, 'data': 'orderStatusName'
                },
                {
                    'name': 'operation', 'targets': 8, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        return '<a href="' + "${rootURL}${controller}" + '/show${domain}/'
                                + row['id'] + '">' +
                                '<button type="button" name="show-button" class="btn btn-default"'
                                + '">' + '<spring:message code="action.show.label"/>'
                                + '</button></a>'
                    }
                }
            ]
        });

    });
</script>