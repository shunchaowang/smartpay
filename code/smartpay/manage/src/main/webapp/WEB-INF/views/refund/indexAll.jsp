<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="refund.label" var="entity"/>
<spring:message code="details.label" arguments="${entity}"/>


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
    <br>


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
                        <table class="table display table-bordered data-table" id="refund-table">
                            <thead>
                            <tr>
                                <th><spring:message code="merchant.label"/></th>
                                <th><spring:message code="site.label"/></th>
                                <th><spring:message code="refund.label"/><spring:message
                                        code="id.label"/></th>
                                <th>
                                    <spring:message code="refund.label"/><spring:message
                                        code="amount.label"/>
                                </th>
                                <th><spring:message code="createdTime.label"/></th>
                                <th>
                                    <spring:message code="order.label"/><spring:message
                                        code="merchantNumber.label"/>
                                </th>
                                <th>
                                    <spring:message code="order.label"/><spring:message
                                        code="amount.label"/>
                                </th>
                                <th><spring:message code="currency.label"/></th>
                                <th><spring:message code="custom.label"/></th>
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

<script type="text/javascript">
    $(document).ready(function () {
        var table = $('#refund-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': true,
            'serverSide': true,
            'paging': true,
            "paginationType": "full_numbers",
            "order": [[2, "desc"]],
            'dom': 'T<""if>rt<"F"lp>',
            "tableTools": {
                "sSwfPath": "${tableTools}",
                "aButtons": [
                    {
                        "sExtends": "copy",
                        "mColumns": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
                    },
                    {
                        "sExtends": "xls",
                        "mColumns": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
                    }
                ]
            },

            'ajax': {
                'url': "${rootURL}refund/list/all",
                'type': "GET",
                'dataType': 'json'
            },
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {
                    'name': 'merchant', 'targets': 0, 'visible': true, 'orderable': false,
                    'data': 'merchantIdentity'
                },
                {
                    'name': 'site', 'targets': 1, 'visible': true, 'orderable': false,
                    'data': 'siteIdentity'
                },
                {'name': 'id', 'targets': 2, 'visible': true, 'data': 'id'},
                {
                    'name': 'amount', 'targets': 3, 'data': 'amount',
                    'searchable': false, 'orderable': false
                },
                {
                    'name': 'createdTime', 'targets': 4, 'searchable': false,
                    'data': 'createdTime'
                },
                {
                    'name': 'merchantNumber',
                    'targets': 5,
                    'searchable': false,
                    'orderable': false,
                    'data': 'orderNumber'
                },
                {
                    'name': 'orderAmount',
                    'targets': 6,
                    'searchable': false,
                    'orderable': false,
                    'data': 'orderAmount'
                },
                {
                    'name': 'orderCurrency',
                    'targets': 7,
                    'searchable': false,
                    'orderable': false,
                    'data': 'orderCurrency'
                },
                {
                    'name': 'customer',
                    'targets': 8,
                    'searchable': false,
                    'orderable': false,
                    'data': 'customerName'
                },
                {
                    'name': 'refundStatus',
                    'targets': 9,
                    'searchable': false,
                    'orderable': false,
                    'data': 'refundStatusName'
                },
                {
                    'name': 'operation', 'targets': 10, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        var operations = '';
                        if (row['refundStatus'] == 'Initiated') { // if the merchant is Initiated
                            operations += '<button type="button" name="audit-button"'
                            + ' data-identity="' + row['id'] + '"'
                            + ' class="btn btn-default" value="' + row['id'] + '">' +
                            "${auditLabel}"
                            + '</button>';
                        }
                        return operations;
                    }
                }
            ]
        });


        // add live handler for add refund button
        table.on('click', 'button[type=button][name=audit-button]', function
                (event) {
            event.preventDefault();
            $.ajax({
                type: 'POST',
                url: "${rootURL}refund/approveRefund",
                data: {
                    id: this.value
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
                    table.ajax.reload();
                }
            });
        });
    });
</script>