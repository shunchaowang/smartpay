<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@include file="taglib.jsp" %>
<!-- stats icons -->

<div class="container-fluid">
    <div class="row">
        <ol class="breadcrumb">
            <li>
                <i class="glyphicon glyphicon-home"></i>
                <spring:message code="home.label"/>
            </li>
        </ol>
    </div>

    <!-- summary -->
    <div class="row">
        <div class="col-sm-4">
            <spring:message code="today.label"/><spring:message code="orderCountSummary.label"/>
        </div>
        <div class="col-sm-4">
            <strong>${merchantCommand.siteCount}</strong>
            <spring:message code="site.label"/>
        </div>
    </div>
    <!-- count by site -->
    <div class="row">
        <div class="col-sm-12">
            <div>
                <table class="table table-bordered" id="today-table">
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
    <br>
    <div class="row">
        <div class="col-sm-4">
            <spring:message code="yesterday.label"/><spring:message code="orderCountSummary.label"/>
        </div>
        <div class="col-sm-4">
            <strong>${merchantCommand.siteCount}</strong>
            <spring:message code="site.label"/>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <div>
                <table class="table table-bordered" id="yesterday-table">
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

</div>
<div class="confirmDialog" id="confirm-dialog">
</div>
<div id="dialog-area"></div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#today-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': false,
            'serverSide': false,
            'info': false,
            'paging': false,
            'searching': false,
            'ordering': false,
            'ajax': {
                'url': "${rootURL}listTodayOrder",
                'type': "GET",
                'dataType': 'json'
            },
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
        $('#yesterday-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': false,
            'serverSide': false,
            'info': false,
            'paging': false,
            'searching': false,
            'ordering': false,
            'ajax': {
                'url': "${rootURL}listYesterdayOrder",
                'type': "GET",
                'dataType': 'json'
            },

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
</script>
