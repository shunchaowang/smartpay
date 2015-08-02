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
                    <strong>${merchantCommand.siteCount}</strong>
                    <spring:message code="site.label"/>
                </div>
                <div class="col-sm-4">
                    <strong>${merchantCommand.orderCount}</strong>
                    <spring:message code="transaction.label"/>
                </div>
    </div>
    <!-- count by site -->
    <div class="row">
        <div class="col-sm-12">
            <div>
                <span class="icon"><i class="icon icon-th"></i> </span>
                <h5><spring:message code="orderCountSummary.label"/>
                ${merchantCommand.orderCount}
                </h5>
            </div>
            <div>
                <table class="table table-bordered" id="count-table">
                    <thead>
                    <tr>
                        <th>
                            <spring:message code="site.label"/>
                            <spring:message code="id.label"/>
                        </th>
                        <th>
                            <spring:message code="site.label"/>
                            <spring:message code="identity.label"/>
                        </th>
                        <th>
                            <spring:message code="site.label"/>
                            <spring:message code="name.label"/>
                        </th>
                        <th>
                            <spring:message code="count.label"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>
    </div>
    <!-- amount by site -->
    <div class="row">
        <div class="col-sm-12">
            <div>
                <span class="icon"><i class="icon icon-th"></i> </span>
                <h5><spring:message code="orderCountSummary.label"/>
                ${merchantCommand.orderAmount}
                </h5>
            </div>
            <div>
                <table class="table table-bordered" id="amount-table">
                    <thead>
                    <tr>
                        <th>
                            <spring:message code="site.label"/>
                            <spring:message code="id.label"/>
                        </th>
                        <th>
                            <spring:message code="site.label"/>
                            <spring:message code="identity.label"/>
                        </th>
                        <th>
                            <spring:message code="site.label"/>
                            <spring:message code="name.label"/>
                        </th>
                        <th>
                            <spring:message code="amount.label"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>
    </div>
    <!-- amount by currency -->
    <div class="row">
        <div class="col-sm-12">
            <div>
                <span class="icon"><i class="icon icon-th"></i> </span>
                <h5><spring:message code="orderAmountSummary.label"/>
                ${merchantCommand.orderAmount}
                </h5>
            </div>
            <div>
                <table class="table table-bordered" id="currency-table">
                    <thead>
                    <tr>
                        <th><spring:message code="currency.label"/></th>
                        <th><spring:message code="count.label"/></th>
                        <th><spring:message code="amount.label"/></th>
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
        $('#count-table').DataTable({
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
                'url': "${rootURL}listOrderCount",
                'type': "GET",
                'dataType': 'json'
            },

            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'siteId'},
                {'name': 'identity', 'targets': 1, 'data': 'siteIdentity'},
                {'name': 'name', 'targets': 2, 'data': 'siteName'},
                {'name': 'count', 'targets': 3, 'data': 'orderCount'}
            ]
        });

        $('#amount-table').DataTable({
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
                'url': "${rootURL}listOrderAmount",
                'type': "GET",
                'dataType': 'json'
            },

            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'siteId'},
                {'name': 'identity', 'targets': 1, 'data': 'siteIdentity'},
                {'name': 'name', 'targets': 2, 'data': 'siteName'},
                {'name': 'amount', 'targets': 3, 'data': 'orderAmount'}
            ]
        });

        $('#currency-table').DataTable({
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
                'url': "${rootURL}listOrderCurrency",
                'type': "GET",
                'dataType': 'json'
            },

            'columnDefs': [
                {'name': 'currency', 'targets': 0, 'data': 'currencyName'},
                {'name': 'count', 'targets': 1, 'data': 'orderCount'},
                {'name': 'amount', 'targets': 2, 'data': 'orderAmount'}
            ]
        });
    });
</script>
