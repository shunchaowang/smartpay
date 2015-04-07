<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@include file="taglib.jsp" %>
<div class="row-fluid">
    <!-- order count by site -->
    <div class="col-sm-4">
        <div class="widget-box">
            <div class="widget-title">
                <span class="icon"><i class="icon icon-th"></i> </span>
                <h5><spring:message code="orderCountSummary.label"/></h5>
                ${merchantCommand.orderCount}
            </div>
            <div class="widget-content nopadding">
                <table class="table display table-bordered data-table" id="count-table">
                    <thead>
                    <tr>
                        <th>
                            <spring:message code="Site.label"/>
                            <spring:message code="id.label"/>
                        </th>
                        <th>
                            <spring:message code="Site.label"/>
                            <spring:message code="identity.label"/>
                        </th>
                        <th>
                            <spring:message code="Site.label"/>
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
    <!-- order amount by site -->
    <div class="col-sm-4">
        <div class="widget-box">
            <div class="widget-title">
                <span class="icon"><i class="icon icon-th"></i> </span>
                <h5><spring:message code="orderAmountSummary.label"/></h5>
                ${merchantCommand.orderAmount}
            </div>
            <div class="widget-content nopadding">
                <table class="table display table-bordered data-table" id="amount-table">
                    <thead>
                    <tr>
                        <th><spring:message code="id.label"/></th>
                        <th><spring:message code="identity.label"/></th>
                        <th><spring:message code="name.label"/></th>
                        <th><spring:message code="amount.label"/></th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>
    </div>
    <!-- order amount by currency -->
    <div class="col-sm-4">
        <div class="widget-box">
            <div class="widget-title">
                <span class="icon"><i class="icon icon-th"></i> </span>
                <h5><spring:message code="orderAmountSummary.label"/></h5>
                ${merchantCommand.orderAmount}
            </div>
            <div class="widget-content nopadding">
                <table class="table display table-bordered data-table" id="currency-table">
                    <thead>
                    <tr>
                        <th><spring:message code="currency.label"/></th>
                        <th><spring:message code="amount.label"/></th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>
    </div>
</div>

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
    });
</script>
