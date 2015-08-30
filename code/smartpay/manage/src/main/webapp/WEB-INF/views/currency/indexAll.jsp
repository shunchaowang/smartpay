<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="currencyExchange.label" var="entity"/>

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
            <table class="table table-bordered" id="exchange-table">
                <thead>
                <tr>
                    <th><spring:message code="id.label"/></th>
                    <th><spring:message code="currency.label"/></th>
                    <th><spring:message code="tradingUnit.label"/></th>
                    <th><spring:message code="RMB.label"/><spring:message code="price.label"/></th>
                    <th><spring:message code="createdTime.label"/></th>
                    <th><spring:message code="updatedTime.label"/></th>
                    <th><spring:message code="remark.label"/></th>
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
        var exchangeTable = $('#exchange-table').DataTable({
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
                        "mColumns": [1, 2, 3, 4, 5, 6]
                    },
                    {
                        "sExtends": "xls",
                        "mColumns": [1, 2, 3, 4, 5, 6]
                    }
                ]
            },

            'ajax': {
                'url': "${rootURL}currency/list/all",
                'type': "GET",
                'dataType': 'json'
            },

            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {'name': 'currency', 'targets': 1, 'data': 'crexCurrencyFrom'},
                {'name': 'tradingUnit', 'targets': 2, 'data': 'crexAmountFrom'},
                {'name': 'exchangePrice', 'targets': 3, 'data': 'crexAmountTo'},
                {'name': 'createdTime', 'targets': 4, 'data': 'createdTime'},
                {'name': 'updatedTime', 'targets': 5, 'searchable': false, 'data': 'updatedTime'},
                {'name': 'remark', 'targets': 6, 'searchable': false, 'data': 'crexRemark'},
                {
                    'name': 'operation', 'targets': 7, 'orderable': false, 'searchable': false,
                    'render': function (data, type, row) {
                        var operations = '<button type="button" name="edit-button" '
                                + 'class="btn btn-default" value="' + row['id'] + '">'
                                + '<spring:message code="action.edit.label"/>'
                                + '</button>';
                        return operations;
                    }
                }
            ]
        });

        // add live handler for edit button
        exchangeTable.on('click', 'button[type=button][name=edit-button]', function
                (event) {
            event.preventDefault();
            $.ajax({
                type: 'get',
                url: "${rootURL}currency/edit",
                data: {
                    exchangeId: this.value
                },
                error: function () {
                    alert('There was an error.');
                },
                success: function (data) {
                    $('#dialog-area').append(data);

                    // define dialog
                    var editDialog = $("#edit-dialog").dialog({
                        autoOpen: false,
                        height: 'auto',
                        width: 'auto',
                        modal: true,
                        close: function () {
                            editDialog.dialog("destroy").remove();
                        }
                    }).dialog("open");

                    $("#cancel-button").click(function (event) {
                        event.preventDefault();
                        editDialog.dialog("close");
                    });

                    $("#save-button").click(function (event) {
                        event.preventDefault();
                        if (!$("#edit-form").valid()) {
                            return;
                        }
                        $.ajax({
                            type: "POST",
                            url: "${rootURL}currency/edit",
                            data: {
                                exchangeId: $("#exchangeId").val(),
                                tradingUnit: $("#tradingUnit").val(),
                                exchangePrice: $("#exchangePrice").val(),
                                remark: $("#remark").val()
                            },
                            dataType: "json",
                            error: function (data) {
                                alert("There was an error");
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
                                editDialog.dialog("close");
                                exchangeTable.ajax.reload();
                            }
                        });
                    });
                }
            });
        });
    });
</script>
