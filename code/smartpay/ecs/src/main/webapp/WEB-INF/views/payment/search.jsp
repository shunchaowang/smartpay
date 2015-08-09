<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="payment.label" var="entity"/>

<spring:message code="freeze.confirm.message" arguments="${entity}" var="freezeMsg"/>
<spring:message code="approve.confirm.message" arguments="${entity}" var="approveMsg"/>
<spring:message code="unfreeze.confirm.message" arguments="${entity}" var="unfreezeMsg"/>
<spring:message code="archive.confirm.message" arguments="${entity}" var="archiveMsg"/>
<spring:message code="decline.confirm.message" arguments="${entity}" var="declineMsg"/>
<spring:message code="action.delete.label" var="deleteLabel"/>
<spring:message code="action.cancel.label" var="cancelLabel"/>
<spring:message code="action.freeze.label" var="freezeLabel"/>
<spring:message code="action.unfreeze.label" var="unfreezeLabel"/>
<spring:message code="action.refund.label" var="refundLabel"/>
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
                        <label class="col-sm-1" for="bankTransactionNumber">
                            <spring:message code="bankTransactionNumber.label"/>
                        </label>

                        <div class="col-sm-3">
                            <input type="text" class="text" name="bankTransactionNumber"
                                   id="bankTransactionNumber"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="col-sm-1" for="paymentStatus">
                            <spring:message code="payment.label"/><spring:message
                                code="status.label"/>
                        </label>

                        <div class="col-sm-3">
                            <select id="paymentStatus" class="text">
                                <option value=""></option>
                                <c:forEach items="${paymentStatuses}" var="status">
                                    <option value="${status.id}">${status.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="col-sm-1" for="siteUrl">
                            <spring:message code="site.url.label"/>
                        </label>
                        <div class="col-sm-3">
                            <input type="text" class="text" name="siteUrl"
                                   id="siteUrl"/>
                        </div>
                    </div>
                </div>
                <br>
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
            <table class="table table-bordered" id="payment-table">
                <thead>
                <tr>
                    <th><spring:message code="id.label"/></th>
                    <th><spring:message code="orderNumber.label"/></th>
                    <th><spring:message code="bankTransactionNumber.label"/></th>
                    <th><spring:message code="amount.label"/></th>
                    <th><spring:message code="currency.label"/></th>
                    <th><spring:message code="paymentCreatedTime.label"/></th>
                    <th><spring:message code="paymentStatusName.label"/></th>
                    <th><spring:message code="paymentTypeName.label"/></th>
                    <th><spring:message code="paymentSiteName.label"/></th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
</div>
<div class="confirmDialog" id="confirm-dialog">
</div>
<div id="dialog-area"></div>

<script type="text/javascript">
    $(document).ready(function () {
        var paymentTable = $('#payment-table').DataTable({
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
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {
                    'name': 'orderNumber', 'targets': 1, 'data': 'orderNumber',
                    'searchable': false, 'orderable': false
                },
                {
                    'name': 'bankTransactionNumber', 'targets': 2, 'data': 'bankTransactionNumber',
                    'orderable': false
                },
                {
                    'name': 'amount', 'targets': 3, 'data': 'amount',
                    'searchable': false, 'orderable': false
                },
                {
                    'name': 'currencyName', 'targets': 4, 'searchable': false, 'orderable': false,
                    'data': 'currencyName'
                },
                {
                    'name': 'createdTime', 'targets': 5, 'searchable': false,
                    'data': 'createdTime'
                },
                {
                    'name': 'paymentStatusName',  'targets': 6, 'searchable': false,
                    'orderable': false,  'data': 'paymentStatusName'
                },
                {
                    'name': 'paymentTypeName', 'targets': 7, 'searchable': false,
                    'orderable': false, 'data': 'paymentTypeName'
                },
                {
                    'name': 'siteName', 'targets': 8, 'searchable': false,
                    'orderable': false, 'data': 'siteName'
                }
            ]
        });
        $('#search-button').click(function (e) {
            e.preventDefault();
            paymentTable.destroy();
            paymentTable = $('#payment-table').DataTable({
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
                            "mColumns": [1, 2, 3, 4, 5, 6, 7, 8]
                        },
                        {
                            "sExtends": "xls",
                            "mColumns": [1, 2, 3, 4, 5, 6, 7, 8]
                        }
                    ]
                },

                'ajax': {
                    'url': "${rootURL}${controller}/searchData",
                    'type': "GET",
                    'data': {
                        'orderNumber': $('#orderNumber').val(),
                        'bankTransactionNumber': $('#bankTransactionNumber').val(),
                        'paymentStatus': $('#paymentStatus').val(),
                        'siteUrl': $('#siteUrl').val(),
                        'timeBeginning': $('#begin-date').val(),
                        'timeEnding': $('#end-date').val()
                    },
                    'dataType': 'json'
                },
                // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
                'columnDefs': [
                    {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                    {
                        'name': 'orderNumber', 'targets': 1, 'data': 'orderNumber',
                        'searchable': false, 'orderable': false,
                        'render': function (data, type, row) {
                            return '<a href=' + "${rootURL}${controller}" + '/show/'
                                    + row['id'] + '>' + data + '</a>';
                        }
                    },
                    {
                        'name': 'bankTransactionNumber', 'targets': 2, 'data': 'bankTransactionNumber',
                        'orderable': false
                    },
                    {
                        'name': 'amount', 'targets': 3, 'data': 'amount',
                        'searchable': false, 'orderable': false
                    },
                    {
                        'name': 'currencyName', 'targets': 4, 'searchable': false, 'orderable': false,
                        'data': 'currencyName'
                    },
                    {
                        'name': 'createdTime', 'targets': 5, 'searchable': false,
                        'data': 'createdTime'
                    },
                    {
                        'name': 'paymentStatusName',  'targets': 6, 'searchable': false,
                        'orderable': false,  'data': 'paymentStatusName'
                    },
                    {
                        'name': 'paymentTypeName', 'targets': 7, 'searchable': false,
                        'orderable': false, 'data': 'paymentTypeName'
                    },
                    {
                        'name': 'siteName', 'targets': 8, 'searchable': false,
                        'orderable': false, 'data': 'siteName'
                    }
                ]
            });
        });
        $('.datepicker').datepicker();
    });
</script>