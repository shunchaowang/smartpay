<%@include file="../taglib.jsp" %>
<c:if test="${domain != null}">
    <spring:message code="${domain}.label" var="entity"/>
</c:if>



<div class="row-fluid">
    <div class="col-sm-12">
        <div class="widget-box">
            <div class="widget-title">
                <span class="icon"><i class="icon icon-th"></i> </span>
                <h5><spring:message code="orderAmountSummary.label"/></h5>
                ${merchantCommand.orderAmount}
            </div>
            <div class="widget-content">
                <div class="widget-content">
                    <table class="table display table-bordered data-table" id="currency-table">
                        <thead>
                        <tr>
                            <th><spring:message code="currency.label"/></th>
                            <th><spring:message code="count.label"/></th>
                            <th><spring:message code="amount.label"/></th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#currency-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': false,
            'serverSide': false,
            'info': false,
            'paging': false,
            'searching': false,
            'ordering': false,


            'ajax': {
                'url': "${rootURL}countByCurrency",
                'type': "GET",
                'dataType': 'json'
            },

            'columnDefs': [
                {'name': 'currency', 'targets': 0, 'data': 'currencyName'},
                {'name': 'count', 'targets': 1, 'data': 'orderCount'},
                {'name': 'amount', 'targets': 2, 'data': 'orderAmount'}
            ]
        });
    });
</script>
