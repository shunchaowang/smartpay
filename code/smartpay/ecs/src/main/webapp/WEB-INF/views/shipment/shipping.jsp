<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<c:if test="${domain != null}">
    <spring:message code="${domain}.label" var="entity"/>
</c:if>

<div class="row-fluid">
    <div class="col-sm-12">
        <div class="widget-box">
            <div class="widget-title">
                <span class="icon"><i class="icon icon-th"></i> </span>
                <h5><spring:message code="index.label" arguments="${entity}"/></h5>
            </div>
            <div class="widget-content">
                <table class="table display table-bordered data-table"  id="shipment-table">
                    <thead>
                    <tr>
                        <th><spring:message code="id.label"/></th>
                        <th><spring:message code="orderNumber.label"/></th>
                        <th><spring:message code="createdTime.label"/></th>
                        <th><spring:message code="status.label"/></th>
                        <th><spring:message code="Customer.label"/></th>
                        <th><spring:message code="Address.label"/></th>
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
        var shipmentTable = $('#shipment-table').DataTable({
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
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'orderId'},
                {
                    'name': 'merchantNumber', 'targets': 1, 'data': 'orderNumber',
                    'render': function (data, type, row) {
                        return '<a href=' + "${rootURL}order" + '/show/'
                                + row['orderId'] + '>' + data + '</a>';
                    }
                },
                {
                    'name': 'createdTime', 'targets': 2, 'searchable': false,
                    'data': 'createdTime'
                },
                {
                    'name': 'orderStatus', 'targets': 3, 'searchable': false,
                    'orderable': false, 'data': 'orderStatusName'
                },
                {
                    'name': 'customerName', 'targets': 4, 'searchable': false,
                    'orderable': false, 'data': 'customerName'
                },
                {
                    'name': 'customerAddress', 'targets': 5, 'searchable': false,
                    'orderable': false, 'data': 'customerAddress'
                },
                {
                    'name': 'operation', 'targets': 6, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        return '<button class="tableButton" type="button" name="addShipment-button"'
                                + ' value="' + row['orderId'] + '">'
                                + '<spring:message code="Shipment.label"/>' + '</button>';
                    }
                }
            ]
        });


        // add live handler for add shipment button
        shipmentTable.on('click', 'button[type=button][name=addShipment-button]', function
                (event) {
            event.preventDefault();
            var id = this.value;
            $.ajax({
                type: 'POST',
                url: "${rootURL}${controller}" + '/shipping',
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
                    shipmentTable.ajax.reload();
                }
            });
        });
    });
</script>