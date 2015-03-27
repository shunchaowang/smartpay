<%@include file="../taglib.jsp" %>
<c:if test="${domain != null}">
    <spring:message code="${domain}.label" var="entity"/>
</c:if>

<div class='row' id='notification'>
    <c:if test="${not empty message}">
        <div class="alert alert-danger alert-dismissable" role="alert">
            <button type="button" class="close" data-dismiss="alert">
                <span aria-hidden="true">&times;</span>
                <span class="sr-only"><spring:message code="action.close.label"/> </span>
            </button>
                ${message}
        </div>
    </c:if>
</div>
<!-- end of notification -->
<div class="row">
    <div class="col-sm-6">
        <h3><b><spring:message code="index.label" arguments="${entity}"/></b></h3>
    </div>
    <!-- end of table title -->
    <div class="col-sm-4 pull-right">
        <a href="${rootURL}${controller}/search">
            <h3><button type="button" class="btn btn-default pull-right">
                <spring:message code="action.advanceSearch.label"/>
            </button></h3>
        </a>
    </div>
</div>
<!-- end of class row -->
<br/>

<div class="row">
    <table class="display cell-border" id="order-table">
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

<script type="text/javascript">
    $(document).ready(function () {

        var orderTable = $('#order-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': true,
            'serverSide': true,
            'paging': true,

            'ajax': {
                'url': "${rootURL}${controller}/list",
                'type': "GET",
                'dataType': 'json'
            },
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'data': 'id', 'searchable': false},
                {
                    'name': 'merchantNumber', 'targets': 1, 'data': 'merchantNumber',
                    'render': function (data, type, row) {
                        return '<a href=' + "${rootURL}${controller}" + '/show/'
                                + row['id'] + '>' + data + '</a>';
                    }
                },
                {
                    'name': 'amount', 'targets': 2, 'data': 'amount',
                    'searchable': false, 'orderable': false
                },
                {
                    'name': 'currency', 'targets': 3, 'data': 'currency',
                    'searchable': false, 'orderable': false
                },
                {
                    'name': 'site', 'targets': 4, 'data': 'site',
                    'searchable': false, 'orderable': false
                },
                {
                    'name': 'customer', 'targets': 5, 'data': 'customer',
                    'searchable': false, 'orderable': false
                },
                {'name': 'createdTime', 'targets': 6, 'searchable': false, 'data': 'createdTime'},
                {
                    'name': 'orderStatus', 'targets': 7, 'searchable': false,
                    'orderable': false, 'data': 'orderStatus'
                },
                {
                    'name': 'operation', 'targets': 8, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        return '<a href="' + "${rootURL}${controller}" + '/edit/'
                                + row['id'] + '">' +
                                '<button type="button" name="edit-button" class="btn btn-default"'
                                + '">' + '<spring:message code="action.edit.label"/>'
                                + '</button></a>' +
                                '<button type="button" name="delete-button"'
                                + ' class="btn btn-default" value="' + row['id'] + '">' +
                                '<spring:message code="action.delete.label"/>' +
                                '</button>';
                    }
                }
            ]
        });

        // add live handler for remove button
        orderTable.on('click', 'button[type=button][name=delete-button]', function (event) {
            event.preventDefault();
            var id = this.value;
            $.ajax({
                type: 'POST',
                url: "${rootURL}${controller}" + '/delete',
                data: {id: id},
                dataType: 'JSON',
                error: function (error) {
                    alert('There was an error');
                },
                success: function (data) {
                    var alert = "<div class='alert alert-warning alert-dismissible' role='alert'>" +
                            "<button type='button' class='close' data-dismiss='alert'>" +
                            "<span aria-hidden='true'>&times;</span>" +
                            "<span class='sr-only'>"
                            + "<spring:message code='action.close.label'/> "
                            + "</span></button>"
                            + data.message + "</div>";
                    $('#notification').append(alert);
                    orderTable.ajax.reload();
                }
            });
        });
    });
</script>