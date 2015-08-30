<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="order.label" var="entity"/>

<spring:message code="freeze.confirm.message" arguments="${entity}" var="freezeMsg"/>
<spring:message code="approve.confirm.message" arguments="${entity}" var="approveMsg"/>
<spring:message code="unfreeze.confirm.message" arguments="${entity}" var="unfreezeMsg"/>
<spring:message code="archive.confirm.message" arguments="${entity}" var="archiveMsg"/>
<spring:message code="decline.confirm.message" arguments="${entity}" var="declineMsg"/>
<spring:message code="action.delete.label" var="deleteLabel"/>
<spring:message code="action.cancel.label" var="cancelLabel"/>
<spring:message code="action.freeze.label" var="freezeLabel"/>
<spring:message code="action.unfreeze.label" var="unfreezeLabel"/>
<spring:message code="action.ship.label" var="shipLabel"/>
<spring:message code="action.decline.label" var="declineLabel"/>
<spring:message code="action.archive.label" var="archiveLabel"/>
<spring:message code="status.created.label" var="createdStatus"/>
<spring:message code="status.frozen.label" var="frozenStatus"/>
<spring:message code="status.approved.label" var="approvedStatus"/>
<spring:message code="status.declined.label" var="declinedStatus"/>

<div class="container-fluid">
    <div class="row">
        <ol class="breadcrumb">
            <li>
                <i class="glyphicon glyphicon-home"></i>
                <spring:message code="home.label"/>
            </li>
            <li class="active">
                <i class="glyphicon glyphicon-list"></i>
                <spring:message code="index.label" arguments="${entity}"/>
            </li>
        </ol>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <span class="icon">
                <i class="icon icon-align-justify"></i>
            </span>

            <form class="form-horizontal">
                <div class="row">
                    <div class="control-group">
                        <label class="col-sm-1" for="merchantNumber">
                            <spring:message code="orderNumber.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input type="text" class="text" name="merchantNumber"
                                   id="merchantNumber"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="col-sm-1" for="orderStatus">
                            <spring:message code="order.label"/><spring:message
                                code="status.label"/>
                        </label>

                        <div class="col-sm-3">
                            <select id="orderStatus" class="text">
                                <option value=""></option>
                                <c:forEach items="${orderStatuses}" var="status">
                                    <option value="${status.id}">${status.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="col-sm-1" for="site">
                            <spring:message code="site.url.label"/>
                        </label>

                        <div class="col-sm-3">
                            <select id="site">
                                <option value=""></option>
                                <c:forEach items="${sites}" var="site">
                                    <option value="${site.id}">${site.url}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="control-group">
                        <label class="col-sm-1" for="begin-date">
                            <spring:message code="date.begin.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input id="begin-date" name="begin-date"
                                   class="text datepicker" readonly="true"
                                   style="background:white;"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="col-sm-1" for="end-date">
                            <spring:message code="date.end.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input id="end-date" name="end-date"
                                   class="text datepicker" readonly="true"
                                   style="background:white;"/>
                        </div>
                    </div>
                </div>
                <br>
                <div class='row'>
                    <div class='form-group'>
                        <div class="col-sm-2 col-sm-offset-2">
                            <button class='btn btn-default' id='search-button' type="submit">
                                <spring:message code='action.search.label'/>
                            </button>
                        </div>
                        <div class="col-sm-2">
                            <button class='btn btn-default' id='reset-button' type="reset">
                                <spring:message code='action.reset.label'/>
                            </button>
                        </div>
                    </div>
                </div>
                <!-- line of submit and reset buttons -->
            </form>
        </div>
        <!-- end of col-sm-12 -->
    </div>
    <div class="row">
        <div class="col-sm-12">
            <table class="table table-bordered" id="order-table">
                <thead>
                <tr>
                    <th><spring:message code="id.label"/></th>
                    <th><spring:message code="merchantNumber.label"/></th>
                    <th><spring:message code="amount.label"/></th>
                    <th><spring:message code="currency.label"/></th>
                    <th><spring:message code="site.label"/><spring:message code="name.label"/></th>
                    <th><spring:message code="customer.label"/><spring:message code="name.label"/></th>
                    <th><spring:message code="createdTime.label"/></th>
                    <th><spring:message code="order.label"/><spring:message code="status.label"/></th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {

        var orderTable = $('#order-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': false,
            'serverSide': false,
            'paging': false,
            'searching': false,
            'ordering': false,
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {   'name': 'id', 'targets': 0, 'data': 'id',  'visible': false, 'searchable': false},
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
                {
                    'name': 'createdTime',
                    'targets': 6,
                    'searchable': false,
                    'data': 'createdTime'
                },
                {
                    'name': 'orderStatusName', 'targets': 7, 'searchable': false,
                    'orderable': false, 'data': 'orderStatusName'
                }
            ]
        });

        $('#search-button').click(function (e) {
            e.preventDefault();
            orderTable.destroy();
            orderTable = $('#order-table').DataTable({
                'language': {
                    'url': "${dataTablesLanguage}"
                },
                'processing': true,
                'serverSide': true,
                'paging': false,
                "order": [[0, "desc"]],
                'dom': 'T<""i>rt',
                "tableTools": {
                    "sSwfPath": "${tableTools}",
                    "aButtons": [
                        {
                            "sExtends": "copy",
                            "mColumns": [1, 2, 3, 4, 5, 6, 7]
                        },
                        {
                            "sExtends": "xls",
                            "fnCellRender": function (sValue, iColumn, nTr, iDataIndex) {
                                if (iColumn == 1) {
                                    if (sValue != "") {
                                        return "=" + sValue.replace(/<[^>]*>/g, "\"");
                                    }
                                }
                                return sValue;
                            },
                            "mColumns": [1, 2, 3, 4, 5, 6, 7]
                        }
                    ]
                },

                'ajax': {
                    'url': "${rootURL}${controller}/searchData",
                    'type': "GET",
                    'data': {
                        'merchantNumber': $('#merchantNumber').val(),
                        'orderStatus': $('#orderStatus').val(),
                        'site': $('#site').val(),
                        'timeBeginning': $('#begin-date').val(),
                        'timeEnding': $('#end-date').val()
                    },
                    'dataType': 'json'
                },
                // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
                'columnDefs': [
                    {   'name': 'id', 'targets': 0, 'data': 'id',  'visible': false, 'searchable': false},
                    {
                        'name': 'merchantNumber', 'targets': 1, 'data': 'merchantNumber',
                        'searchable': false, 'orderable': false
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
                    {
                        'name': 'createdTime',  'targets': 6,  'searchable': false,
                        'data': 'createdTime'
                    },
                    {
                        'name': 'orderStatusName', 'targets': 7, 'searchable': false,
                        'orderable': false, 'data': 'orderStatusName'
                    }
                ]
            });
        });

        orderTable.on('click', 'button[type=button][name=addShipment-button]', function
                (event) {
            event.preventDefault();
            $.ajax({
                type: 'get',
                url: "${rootURL}order/addShipment",
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
                        width: 'auto',
                        modal: true,
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
                        if (!$("#new-shipment-form").valid()) {
                            return;
                        }
                        $.ajax({
                            type: "POST",
                            url: "${rootURL}order/saveShipment",
                            data: {
                                orderId: $("#orderId").val(),
                                carrier: $("#carrier").val(),
                                trackingNumber: $("#trackingNumber").val()
                            },
                            dataType: "json",
                            error: function (data) {
                                alert("There was an error");
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
                                orderTable.ajax.reload();
                            }
                        });
                    });
                }
            });
        });

        $('.datepicker').datepicker();
    });
</script>