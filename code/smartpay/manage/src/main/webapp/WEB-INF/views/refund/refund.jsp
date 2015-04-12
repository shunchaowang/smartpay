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
                <table class="table display table-bordered data-table" id="refund-table">
                    <thead>
                    <tr>
                        <th><spring:message code="id.label"/></th>
                        <th><spring:message code="orderNumber.label"/></th>
                        <th><spring:message code="bankTransactionNumber.label"/></th>
                        <th><spring:message code="bankName.label"/></th>
                        <th><spring:message code="createdTime.label"/></th>
                        <th><spring:message code="site.url.label"/></th>
                        <th><spring:message code="status.label"/></th>
                        <th><spring:message code="Customer.label"/></th>
                        <th><spring:message code="Address.label"/></th>
                        <th><spring:message code="Refund.label"/></th>
                        <th><spring:message code="remark.label"/></th>
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
                    'name': 'createdTime', 'targets': 4, 'searchable': false,
                    'data': 'createdTime'
                },
                {
                    'name': 'siteUrl', 'targets': 5, 'searchable': true,
                    'orderable': false, 'data': 'siteUrl'
                },
                {
                    'name': 'orderStatus', 'targets': 6, 'searchable': false,
                    'orderable': false, 'data': 'orderStatusName'
                },
                {
                    'name': 'customerName', 'targets': 7, 'searchable': false,
                    'orderable': false, 'data': 'customerName'
                },
                {
                    'name': 'customerAddress', 'targets': 8, 'searchable': false,
                    'orderable': false, 'data': 'customerAddress'
                },
                {
                    'name': 'refundAmount', 'targets': 9, 'searchable': false,
                    'orderable': false, 'data': 'refundAmount'
                },
                {
                    'name': 'refundRemark', 'targets': 10, 'searchable': false,
                    'orderable': false, 'data': 'refundRemark'
                },
                    /*
                {
                    'name': 'operation', 'targets': 9, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        return '<button class="tableButton" type="button" name="refund-button"'
                                + ' value="' + row['orderId'] + '">'
                                + '<spring:message code="refund.label"/>' + '</button>';
                    }
                }
                */
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
                    orderId: this.value
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
                                minlength: 2,
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