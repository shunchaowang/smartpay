<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<c:if test="${domain != null}">
    <spring:message code="${domain}.label" var="entity"/>
</c:if>
<spring:message code="Shipment.label" var="shipment"/>

<div id="content">
    <div id="content-header">
        <div id="breadcrumb">
            <a href="${rootURL}">
                <i class="icon icon-home"></i>
                <spring:message code="home.label"/>
            </a>
            <a href="${rootURL}${controller}/index">
                <spring:message code="index.label" arguments="${shipment}"/>
            </a>
            <a href="${rootURL}${controller}/${action}" class="current">
                <spring:message code="${action}.label" arguments="${entity}"/>
            </a>
        </div>
    </div>
    <!-- reserved for notification -->
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
                                <th><spring:message code="custom.label"/></th>
                                <th><spring:message code="address.label"/></th>
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
            'dom': 'T<""i>rt<"F"lp>',
            "tableTools": {
                "sSwfPath": "${tableTools}",
                "aButtons": [
                    {
                        "sExtends": "copy",
                        "mColumns": [1, 2, 3, 4, 5, 6, 7]
                    },
                    {
                        "sExtends": "xls",
                        "mColumns": [1, 2, 3, 4, 5, 6, 7]
                    }
                ]
            },

            'ajax': {
                'url': "${rootURL}${controller}/listWaitForShipping",
                'type': "GET",
                'dataType': 'json'
            },
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {
                    'name': 'merchantNumber', 'targets': 1, 'data': 'merchantNumber',
                    'render': function (data, type, row) {
                        return '<a href=' + "${rootURL}order" + '/show/'
                                + row['id'] + '>' + data + '</a>';
                    }
                },
                {
                    'name': 'amount', 'targets': 2, 'searchable': true,
                    'orderable': false, 'data': 'amount'
                },
                {
                    'name': 'currency', 'targets': 3, 'searchable': true,
                    'orderable': false, 'data': 'currencyName'
                },
                {
                    'name': 'createdTime', 'targets': 4, 'searchable': false,
                    'data': 'createdTime'
                },
                {
                    'name': 'customer', 'targets': 5, 'searchable': true,
                    'orderable': false, 'data': 'customerName'
                },
                {
                    'name': 'address', 'targets': 6, 'searchable': false,
                    'orderable': false, 'data': 'customerAddress'
                },
                {
                    'name': 'orderStatus', 'targets': 7, 'searchable': false,
                    'orderable': false, 'data': 'orderStatusName'
                },
                {
                    'name': 'operation', 'targets': 8, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        return '<button type="button" name="addShipment-button"'
                                + ' class="tableButton" value="' + row['id'] + '">'
                                + '<spring:message code="action.ship.label"/>'
                                + '</button>';
                    }
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
                    console.log("data: " + data);
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