<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="order.label" var="entity"/>

<spring:message code="freeze.confirm.message" arguments="${entity}" var="freezeMsg"/>
<spring:message code="approve.confirm.message" arguments="${entity}" var="approveMsg"/>
<spring:message code="unfreeze.confirm.message" arguments="${entity}" var="unfreezeMsg"/>
<spring:message code="archive.confirm.message" arguments="${entity}" var="archiveMsg"/>
<spring:message code="decline.confirm.message" arguments="${entity}" var="declineMsg"/>
<spring:message code="action.delete.label" var="deleteLabel"/>
<spring:message code="action.cancel.label" var="cancelLabel"/>
<spring:message code="action.freeze.label" var="freezeLabel"/>
<spring:message code="action.unfreeze.label" var="unfreezeLabel"/>
<spring:message code="action.approve.label" var="approveLabel"/>
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
            <table class="table table-bordered" id="order-table">
                <thead>
                <tr>
                    <th><spring:message code="id.label"/></th>
                    <th><spring:message code="merchantNumber.label"/></th>
                    <th><spring:message code="amount.label"/></th>
                    <th><spring:message code="currency.label"/></th>
                    <th><spring:message code="site.label"/><spring:message code="name.label"/></th>
                    <th><spring:message code="customer.label"/><spring:message code="name.label"/></th>
                    <th><spring:message code="createdTime.label"/></th>
                    <th><spring:message code="order.label"/><spring:message code="status.label"/></th>
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

        var orderTable = $('#order-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': true,
            'serverSide': true,
            'paging': true,
            "paginationType": "full_numbers",
            "order": [[0, "desc"]],
            "tableTools": {
                "sSwfPath": "${tableTools}",
                "aButtons": [
                    {
                        "sExtends": "copy",
                        "mColumns": [1, 2, 3, 4, 5, 6, 7]
                    },
                    {
                        "sExtends": "xls",
                        "mColumns": [1, 2, 3, 4, 5, 6, 7]
                    }
                ]
            },

            'ajax': {
                'url': "${rootURL}${controller}/list",
                'type': "GET",
                'dataType': 'json'
            },
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'data': 'id', 'visible': false, 'searchable': false},
                {
                    'name': 'merchantNumber', 'targets': 1, 'data': 'merchantNumber',
                    'render': function (data, type, row) {
                        return '<a href=' + "${rootURL}${controller}" + '/show/'
                                + row['id'] + '>' + data + '</a>';
                    }
                },
                {
                    'name': 'amount', 'targets': 2, 'data': 'amount',
                    'searchable': false, 'orderable': false
                },
                {
                    'name': 'currencyName', 'targets': 3, 'data': 'currencyName',
                    'searchable': false, 'orderable': false
                },
                {
                    'name': 'siteName', 'targets': 4, 'data': 'siteName',
                    'searchable': false, 'orderable': false
                },
                {
                    'name': 'customerName', 'targets': 5, 'data': 'customerName',
                    'searchable': false, 'orderable': false
                },
                {'name': 'createdTime', 'targets': 6, 'searchable': false, 'data': 'createdTime'},
                {
                    'name': 'orderStatusName', 'targets': 7, 'searchable': false,
                    'orderable': false, 'data': 'orderStatusName'
                }
            ]
        });

    });
</script>