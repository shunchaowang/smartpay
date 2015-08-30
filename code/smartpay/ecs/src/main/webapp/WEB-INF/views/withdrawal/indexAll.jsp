<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="withdrawal.label" var="entity"/>

<spring:message code="freeze.confirm.message" arguments="${entity}" var="freezeMsg"/>
<spring:message code="approve.confirm.message" arguments="${entity}" var="approveMsg"/>
<spring:message code="unfreeze.confirm.message" arguments="${entity}" var="unfreezeMsg"/>
<spring:message code="archive.confirm.message" arguments="${entity}" var="archiveMsg"/>
<spring:message code="decline.confirm.message" arguments="${entity}" var="declineMsg"/>
<spring:message code="delete.confirm.message" arguments="${entity}" var="deleteMsg"/>
<spring:message code="action.delete.label" var="deleteLabel"/>
<spring:message code="action.cancel.label" var="cancelLabel"/>
<spring:message code="action.freeze.label" var="freezeLabel"/>
<spring:message code="action.unfreeze.label" var="unfreezeLabel"/>
<spring:message code="action.approve.label" var="approveLabel"/>
<spring:message code="action.decline.label" var="declineLabel"/>
<spring:message code="action.archive.label" var="archiveLabel"/>
<spring:message code="status.created.label" var="createdStatus"/>
<spring:message code="status.withdrawal.label" var="withdrawalLabel"/>
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
    <br>

    <div class="row">
        <div class="col-sm-12">
            <table class="table table-bordered" id="withdrawal-table">
                <thead>
                <tr>
                    <th><spring:message code="id.label"/></th>
                    <th><spring:message code="createdTime.label"/></th>
                    <th><spring:message code="withdrawal.label"/><spring:message code="dateRange.label"/></th>
                    <th><spring:message code="wdrlBalance.label"/></th>
                    <th><spring:message code="securityDeposit.label"/></th>
                    <th><spring:message code="wdrlAmount.label"/></th>

                    <th><spring:message code="wdrlAmountApproved.label"/></th>
                    <th><spring:message code="refundAfterWdrl.label"/></th>
                    <th><spring:message code="chargebackAfterWdrl.label"/></th>
                    <th><spring:message code="wdrlAmountAdjusted.label"/></th>

                    <th><spring:message code="securityWithdrawn.label"/></th>
                    <th><spring:message code="status.label"/></th>
                    <th><spring:message code="status.label"/></th>
                    <th><spring:message code="action.operation.label"/></th>
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
        var withdrawalTable = $('#withdrawal-table').DataTable({
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
                        "mColumns": [1, 2, 3, 4, 5, 6, 7, 8, 9, 11]
                    },
                    {
                        "sExtends": "xls",
                        "mColumns": [1, 2, 3, 4, 5, 6, 7, 8, 9, 11]
                    }
                ]
            },

            'ajax': {
                'url': "${rootURL}withdrawal/list",
                'type': "GET",
                'dataType': 'json'
            },

            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {'name': 'createdTime', 'targets': 1, 'visible': false,'data': 'createdTime'},
                {
                    'name': 'dateRange', 'targets': 2, 'data': 'dateRange',
                    'render': function (data, type, row) {
                        return '<a href=' + "${rootURL}withdrawal" + '/show/'
                                + row['id'] + '>' + data + '</a>';
                    }
                },
                {'name': 'balance', 'targets': 3, 'data': 'balance'},
                {'name': 'securityDeposit', 'targets': 4, 'data': 'securityDeposit'},
                {'name': 'amount', 'targets': 5, 'data': 'amount'},

                {'name': 'wdrlAmountApproved', 'targets': 6, 'data': 'wdrlAmountApproved'},
                {'name': 'refundAfterWdrl', 'targets': 7, 'data': 'refundAfterWdrl'},
                {'name': 'chargebackAfterWdrl', 'targets': 8, 'data': 'chargebackAfterWdrl'},
                {'name': 'wdrlAmountAdjusted', 'targets': 9, 'data': 'wdrlAmountAdjusted'},

                {'name': 'securityWithdrawn', 'targets': 10, 'visible': false, 'data': 'securityWithdrawn' },
                {'name': 'withdrawalStatusName', 'targets': 11, 'orderable': false,
                    'data': 'withdrawalStatusName'},
                {
                    'name': 'withdrawalStatusCode', 'targets': 12, 'visible': false, 'searchable': false,
                     'data': 'withdrawalStatusCode'
                },
                {
                    'name': 'operation', 'targets': 13, 'orderable': false, 'searchable': false,
                    'render': function (data, type, row) {
                        var operations = '';
                        if (row['withdrawalStatusCode'] !='300' ) {
                            operations += '<button type="button" name="delete-button" '
                            + 'class="btn btn-default" value="' + row['id'] + '">'
                            + '<spring:message code="action.delete.label"/>'
                            + '</button>';
                        };
                        if (row['securityWithdrawn'] == 'true' || row['withdrawalStatusCode'] =='300') {
                            operations += '<button type="button" name="save-button" '
                                    + 'class="btn btn-default" value="' + row['id'] + '">'
                                    + '<spring:message code="securityWithdrawn.label"/>'
                                    + '</button>';
                        }
                        return operations;
                    }
                }
            ]
        });

        withdrawalTable.on('click', 'button[type=button][name=delete-button]', function (event) {
            event.preventDefault();
            var id = this.value;
            $("#confirm-dialog").dialog({
                resizable: true,
                height: 'auto',
                width: 'auto',
                modal: true,
                open: function () {
                    var content = "${deleteMsg}";
                    $(this).html(content);
                },
                buttons: {
                    "${deleteLabel}": function () {
                        $(this).dialog("close");
                        $.ajax({
                            type: 'POST',
                            url: "${rootURL}withdrawal" + '/cancelWithdrawal',
                            data: {id: id},
                            dataType: 'JSON',
                            error: function (error) {
                                alert('There was an error');
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
                                withdrawalTable.ajax.reload();
                            }
                        });
                    },
                    "${cancelLabel}": function () {
                        $(this).dialog("close");
                    }
                }
            });
        });

        withdrawalTable.on('click', 'button[type=button][name=save-button]', function (event) {
            event.preventDefault();
            var id = this.value;
            $("#confirm-dialog").dialog({
                resizable: true,
                height: 'auto',
                width: 'auto',
                modal: true,
                open: function () {
                    var content = <spring:message code="securityWithdrawn.label"/>;
                    $(this).html(content);
                },
                buttons: {
                    "${withdrawalLabel}": function () {
                        $(this).dialog("close");
                        $.ajax({
                            type: 'POST',
                            url: "${rootURL}withdrawal" + '/securityWithdrawn',
                            data: {id: id},
                            dataType: 'JSON',
                            error: function (error) {
                                alert('There was an error');
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
                                withdrawalTable.ajax.reload();
                            }
                        });
                    },
                    "${withdrawalLabel}": function () {
                        $(this).dialog("close");
                    }
                }
            });
        });

    });
</script>
