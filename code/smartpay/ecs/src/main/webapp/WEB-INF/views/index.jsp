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
                        <th>
                            <spring:message code="count.label"/>
                        </th>
                        <th>
                            <spring:message code="USD.label"/><spring:message code="count.label"/>
                        </th>
                        <th>
                            <spring:message code="USD.label"/><spring:message code="amount.label"/>
                        </th>
                        <th>
                            <spring:message code="RMB.label"/><spring:message code="count.label"/>
                        </th>
                        <th>
                            <spring:message code="RMB.label"/><spring:message code="amount.label"/>
                        </th>
                        <th>
                            <spring:message code="EUR.label"/><spring:message code="count.label"/>
                        </th>
                        <th>
                            <spring:message code="EUR.label"/><spring:message code="amount.label"/>
                        </th>
                        <th>
                            <spring:message code="JPY.label"/><spring:message code="count.label"/>
                        </th>
                        <th>
                            <spring:message code="JPY.label"/><spring:message code="amount.label"/>
                        </th>
                        <th>
                            <spring:message code="CAD.label"/><spring:message code="count.label"/>
                        </th>
                        <th>
                            <spring:message code="CAD.label"/><spring:message code="amount.label"/>
                        </th>
                        <th>
                            <spring:message code="AUD.label"/><spring:message code="count.label"/>
                        </th>
                        <th>
                            <spring:message code="AUD.label"/><spring:message code="amount.label"/>
                        </th>
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
                        <th>
                            <spring:message code="count.label"/>
                        </th>
                        <th>
                            <spring:message code="USD.label"/><spring:message code="count.label"/>
                        </th>
                        <th>
                            <spring:message code="USD.label"/><spring:message code="amount.label"/>
                        </th>
                        <th>
                            <spring:message code="RMB.label"/><spring:message code="count.label"/>
                        </th>
                        <th>
                            <spring:message code="RMB.label"/><spring:message code="amount.label"/>
                        </th>
                        <th>
                            <spring:message code="EUR.label"/><spring:message code="count.label"/>
                        </th>
                        <th>
                            <spring:message code="EUR.label"/><spring:message code="amount.label"/>
                        </th>
                        <th>
                            <spring:message code="JPY.label"/><spring:message code="count.label"/>
                        </th>
                        <th>
                            <spring:message code="JPY.label"/><spring:message code="amount.label"/>
                        </th>
                        <th>
                            <spring:message code="CAD.label"/><spring:message code="count.label"/>
                        </th>
                        <th>
                            <spring:message code="CAD.label"/><spring:message code="amount.label"/>
                        </th>
                        <th>
                            <spring:message code="AUD.label"/><spring:message code="count.label"/>
                        </th>
                        <th>
                            <spring:message code="AUD.label"/><spring:message code="amount.label"/>
                        </th>
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
                {'name': 'count', 'targets': 3, 'visible': false,  'data': 'orderTotalCnt'},
                {'name': 'USDCnt', 'targets': 4, 'data': 'USDCnt'},
                {'name': 'USDAmt', 'targets': 5, 'data': 'USDAmt'},
                {'name': 'RMBCnt', 'targets': 6, 'data': 'RMBCnt'},
                {'name': 'RMBAmt', 'targets': 7, 'data': 'RMBAmt'},
                {'name': 'EURCnt', 'targets': 8, 'data': 'EURCnt'},
                {'name': 'EURAmt', 'targets': 9, 'data': 'EURAmt'},
                {'name': 'JPYCnt', 'targets': 10, 'data': 'JPYCnt'},
                {'name': 'JPYAmt', 'targets': 11, 'data': 'JPYAmt'},
                {'name': 'CADCnt', 'targets': 12, 'data': 'CADCnt'},
                {'name': 'CADAmt', 'targets': 13, 'data': 'CADAmt'},
                {'name': 'AUDCnt', 'targets': 14, 'data': 'AUDCnt'},
                {'name': 'AUDAmt', 'targets': 15, 'data': 'AUDAmt'}
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
                {'name': 'count', 'targets': 3, 'visible': false,  'data': 'orderTotalCnt'},
                {'name': 'USDCnt', 'targets': 4, 'data': 'USDCnt'},
                {'name': 'USDAmt', 'targets': 5, 'data': 'USDAmt'},
                {'name': 'RMBCnt', 'targets': 6, 'data': 'RMBCnt'},
                {'name': 'RMBAmt', 'targets': 7, 'data': 'RMBAmt'},
                {'name': 'EURCnt', 'targets': 8, 'data': 'EURCnt'},
                {'name': 'EURAmt', 'targets': 9, 'data': 'EURAmt'},
                {'name': 'JPYCnt', 'targets': 10, 'data': 'JPYCnt'},
                {'name': 'JPYAmt', 'targets': 11, 'data': 'JPYAmt'},
                {'name': 'CADCnt', 'targets': 12, 'data': 'CADCnt'},
                {'name': 'CADAmt', 'targets': 13, 'data': 'CADAmt'},
                {'name': 'AUDCnt', 'targets': 14, 'data': 'AUDCnt'},
                {'name': 'AUDAmt', 'targets': 15, 'data': 'AUDAmt'}
            ]
        });
    });
</script>
