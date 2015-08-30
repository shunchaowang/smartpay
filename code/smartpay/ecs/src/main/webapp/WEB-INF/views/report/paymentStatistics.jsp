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
                    <th><spring:message code="month.label"/></th>
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
                {'name': 'statisticsDate', 'targets': 0, 'data': 'statisticsDate'},
                {'name': 'paidCount', 'targets': 1, 'data': 'paidCount'},
                {'name': 'paidAmount', 'targets': 2, 'data': 'paidAmount'},
                {'name': 'refuseCount', 'targets': 3, 'data': 'refuseCount'},
                {'name': 'refuseAmount', 'targets': 4, 'data': 'refuseAmount'},
                {'name': 'refundCount', 'targets': 5, 'data': 'refundCount'},
                {'name': 'refundAmount', 'targets': 6, 'data': 'refundAmount'}
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
                            "mColumns": [0, 1, 2, 3, 5, 6]
                        },
                        {
                            "sExtends": "xls",
                            "mColumns": [0, 1, 2, 3, 5, 6]
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
                    {'name': 'statisticsDate', 'targets': 0, 'data': 'statisticsDate'},
                    {'name': 'paidCount', 'targets': 1, 'data': 'paidCount'},
                    {'name': 'paidAmount', 'targets': 2, 'data': 'paidAmount'},
                    {'name': 'refuseCount', 'targets': 3, 'data': 'refuseCount'},
                    {'name': 'refuseAmount', 'targets': 4, 'data': 'refuseAmount'},
                    {'name': 'refundCount', 'targets': 5, 'data': 'refundCount'},
                    {'name': 'refundAmount', 'targets': 6, 'data': 'refundAmount'}
                ]
            });
        });
        $('.datepicker').datepicker();
    });
</script>