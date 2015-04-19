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
                        <table class="table display table-bordered data-table" id="refund-table">
                            <thead>
                            <tr>
                                <th><spring:message code="id.label"/></th>
                                <th><spring:message code="orderNumber.label"/></th>
                                <th><spring:message code="amount.label"/></th>
                                <th><spring:message code="currency.label"/></th>
                                <th><spring:message code="createdTime.label"/></th>
                                <th><spring:message code="status.label"/></th>
                                <th><spring:message code="Customer.label"/></th>
                                <th><spring:message code="action.operation.label"/></th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div id="dialog-area"></div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        var refundTable = $('#refund-table').DataTable({
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
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {
                    'name': 'merchantNumber', 'targets': 1, 'data': 'merchantNumber'
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
                    'name': 'orderStatus', 'targets': 5, 'searchable': false,
                    'orderable': false, 'data': 'orderStatusName'
                },
                {
                    'name': 'customerName', 'targets': 6, 'searchable': false,
                    'orderable': false, 'data': 'customerName'
                },
                {
                    'name': 'operation', 'targets': 7, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        return '<button class="tableButton" type="button" name="refund-button"'
                                + ' value="' + row['id'] + '">'
                                + '<spring:message code="refund.label"/>' + '</button>';
                    }
                }
            ]
        });


        // add live handler for add refund button
        refundTable.on('click', 'button[type=button][name=refund-button]', function
                (event) {
            event.preventDefault();
            $.ajax({
                type: 'get',
                url: "${rootURL}${controller}/addRefund",
                data: {
                    orderId: this.value,
                    amount: this.amount
                },
                error: function () {
                    alert('There was an error.');
                },
                success: function (data) {
                    $('#dialog-area').append(data);

                    // define dialog
                    var refundDialog = $("#refund-dialog").dialog({
                        autoOpen: false,
                        height: 'auto',
                        width: 600,
                        modal: true,
                        dialogClass: "dialogClass",
                        open: function (event, ui) {
                            $(".ui-dialog-titlebar-close", ui.dialog || ui).hide();
                        },
                        close: function () {
                            refundDialog.dialog("destroy").remove();
                        }
                    }).dialog("open");

                    $("#cancel-button").click(function (event) {
                        event.preventDefault();
                        refundDialog.dialog("close");
                    });

                    $("#save-button").click(function (event) {
                        event.preventDefault();

                        $.ajax({
                            type: 'post',
                            url: "${rootURL}${controller}/addRefund",
                            data: {
                                orderId: $("#orderId").val(),
                                amount: $("#amount").val(),
                                remark: $("#remark").val()
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
                                refundDialog.dialog("close");
                                refundTable.ajax.reload();
                            }
                        });
                    });

                    $("#new-refund-form").validate({
                        rules: {
                            amount: {
                                required: true,
                                minlength: 1,
                                maxlength: 32
                            },
                            remark: {
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