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
                <table class="table display table-bordered data-table" id="shipment-table">
                    <thead>
                    <tr>
                        <th><spring:message code="id.label"/></th>
                        <th><spring:message code="orderNumber.label"/></th>
                        <th><spring:message code="bankTransactionNumber.label"/></th>
                        <th><spring:message code="bankName.label"/></th>
                        <th><spring:message code="amount.label"/></th>
                        <th><spring:message code="currency.label"/></th>
                        <th><spring:message code="createdTime.label"/></th>
                        <th><spring:message code="site.url.label"/></th>
                        <th><spring:message code="carrier.label"/></th>
                        <th><spring:message code="trackingNumber.label"/></th>
                        <th><spring:message code="status.label"/></th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>
    </div>
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
                    'name': 'bankTransactionNumber', 'targets': 2, 'searchable': true,
                    'orderable': false, 'data': 'bankTransactionNumber'
                },
                {
                    'name': 'bankName', 'targets': 3, 'searchable': true,
                    'orderable': false, 'data': 'bankName'
                },
                {
                    'name': 'orderAmount', 'targets': 4, 'searchable': true,
                    'orderable': false, 'data': 'orderAmount'
                },
                {
                    'name': 'orderCurrency', 'targets': 5, 'searchable': true,
                    'orderable': false, 'data': 'orderCurrency'
                },
                {
                    'name': 'createdTime', 'targets': 6, 'searchable': false,
                    'data': 'createdTime'
                },
                {
                    'name': 'siteUrl', 'targets': 7, 'searchable': true,
                    'orderable': false, 'data': 'siteUrl'
                },
                {
                    'name': 'carrier', 'targets': 8, 'searchable': false,
                    'orderable': false, 'data': 'carrier'
                },
                {
                    'name': 'trackingNumber', 'targets': 9, 'searchable': false,
                    'orderable': false, 'data': 'trackingNumber'
                },
                {
                    'name': 'orderStatus', 'targets': 10, 'searchable': false,
                    'orderable': false, 'data': 'orderStatusName'
                }
            ]
        });


        // add live handler for add shipment button
        shipmentTable.on('click', 'button[type=button][name=addShipment-button]', function
                (event) {
            event.preventDefault();
            $.ajax({
                type: 'get',
                url: "${rootURL}${controller}/addShipment",
                data: {
                    orderId: this.value
                },
                error: function () {
                    alert('There was an error.');
                },
                success: function (data) {
                    $('#dialog-area').append(data);

                    // define dialog
                    var shipmentDialog = $("#shipment-dialog").dialog({
                        autoOpen: false,
                        height: 'auto',
                        width: 600,
                        modal: true,
                        dialogClass: "dialogClass",
                        open: function (event, ui) {
                            $(".ui-dialog-titlebar-close", ui.dialog || ui).hide();
                        },
                        close: function () {
                            shipmentDialog.dialog("destroy").remove();
                        }
                    }).dialog("open");

                    $("#cancel-button").click(function (event) {
                        event.preventDefault();
                        shipmentDialog.dialog("close");
                    });

                    $("#save-button").click(function (event) {
                        event.preventDefault();

                        $.ajax({
                            type: 'post',
                            url: "${rootURL}${controller}/addShipment",
                            data: {
                                orderId: $("#orderId").val(),
                                carrier: $("#carrier").val(),
                                trackingNumber: $("#trackingNumber").val()
                            },
                            error: function () {
                                alert('There was an error.');
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
                                shipmentDialog.dialog("close");
                                shipmentTable.ajax.reload();
                            }
                        });
                    });

                    $("#new-shipment-form").validate({
                        rules: {
                            carrier: {
                                required: true,
                                minlength: 2,
                                maxlength: 32
                            },
                            trackingNumber: {
                                required: true,
                                minlength: 5,
                                maxlength: 32
                            }
                        }
                    });

                }
            });
        });
    });
</script>