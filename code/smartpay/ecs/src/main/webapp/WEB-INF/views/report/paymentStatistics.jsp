<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="paymentStatistics.label" var="entity"/>

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
                                <spring:message code='action.summary.label'/>
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
                    <th>
                        <spring:message code="site.label"/><spring:message code="id.label"/>
                    </th>
                    <th>
                        <spring:message code="site.label"/><spring:message code="identity.label"/>
                    </th>
                    <th>
                        <spring:message code="site.label"/><spring:message code="name.label"/>
                    </th>
                    <th><spring:message code="order.label"/><spring:message code="count.label"/></th>
                    <th><spring:message code="order.label"/><spring:message code="amount.label"/></th>
                    <th><spring:message code="payment.label"/><spring:message code="count.label"/></th>
                    <th><spring:message code="payment.label"/><spring:message code="amount.label"/></th>
                    <th><spring:message code="refuse.label"/><spring:message code="count.label"/></th>
                    <th><spring:message code="refuse.label"/><spring:message code="amount.label"/></th>
                    <th><spring:message code="refund.label"/><spring:message code="count.label"/></th>
                    <th><spring:message code="refund.label"/><spring:message code="amount.label"/></th>
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
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'siteId'},
                {'name': 'identity', 'targets': 1, 'data': 'siteIdentity'},
                {'name': 'name', 'targets': 2, 'data': 'siteName'},
                {'name': 'orderCount', 'targets': 3, 'data': 'orderCount'},
                {'name': 'orderAmount', 'targets': 4, 'visible': false, 'data': 'orderAmount'},
                {'name': 'paidCount', 'targets': 5, 'data': 'paidCount'},
                {'name': 'paidAmount', 'targets': 6, 'data': 'paidAmount'},
                {'name': 'refuseCount', 'targets': 7, 'data': 'refuseCount'},
                {'name': 'refuseAmount', 'targets': 8, 'data': 'refuseAmount'},
                {'name': 'refundCount', 'targets': 9, 'data': 'refundCount'},
                {'name': 'refundAmount', 'targets': 10, 'data': 'refundAmount'}
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
                            "mColumns": [1, 2, 3, 5, 6, 7, 8, 9, 10]
                        },
                        {
                            "sExtends": "xls",
                            "mColumns": [1, 2, 3, 5, 6, 7, 8, 9, 10]
                        }
                    ]
                },

                'ajax': {
                    'url': "${rootURL}${controller}/summaryData",
                    'type': "GET",
                    'data': {
                        'timeBeginning': $('#begin-date').val(),
                        'timeEnding': $('#end-date').val()
                    },
                    'dataType': 'json'
                },
                // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
                'columnDefs': [
                    {'name': 'id', 'targets': 0, 'visible': false, 'data': 'siteId'},
                    {'name': 'identity', 'targets': 1, 'data': 'siteIdentity'},
                    {'name': 'name', 'targets': 2, 'data': 'siteName'},
                    {'name': 'orderCount', 'targets': 3, 'data': 'orderCount'},
                    {'name': 'orderAmount', 'targets': 4, 'visible': false, 'data': 'orderAmount'},
                    {'name': 'paidCount', 'targets': 5, 'data': 'paidCount'},
                    {'name': 'paidAmount', 'targets': 6, 'data': 'paidAmount'},
                    {'name': 'refuseCount', 'targets': 7, 'data': 'refuseCount'},
                    {'name': 'refuseAmount', 'targets': 8, 'data': 'refuseAmount'},
                    {'name': 'refundCount', 'targets': 9, 'data': 'refundCount'},
                    {'name': 'refundAmount', 'targets': 10, 'data': 'refundAmount'}
                ]
            });
        });
        $('.datepicker').datepicker();
    });
</script>