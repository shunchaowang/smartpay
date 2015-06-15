<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="site.label" var="entity"/>
<spring:message code="freeze.confirm.message" arguments="${entity}" var="freezeMsg"/>
<spring:message code="unfreeze.confirm.message" arguments="${entity}" var="unfreezeMsg"/>
<spring:message code="archive.confirm.message" arguments="${entity}" var="archiveMsg"/>
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
        <div class="col-sm-2 pull-left">
            <a href="${rootURL}site/create">
                <button class="btn btn-default">
                    <i class="glyphicon glyphicon-wrench"></i>
                    <spring:message code="create.label" arguments="${entity}"/>
                </button>
            </a>
        </div>
    </div>
    <br>

    <div class="row">
        <div class="col-sm-12">
            <table class="table table-bordered" id="site-table">
                <thead>
                <tr>
                    <th><spring:message code="id.label"/></th>
                    <th><spring:message code="identity.label"/></th>
                    <th><spring:message code="name.label"/></th>
                    <th><spring:message code="site.merchant.label"/></th>
                    <th><spring:message code="site.url.label"/></th>
                    <th><spring:message code="createdTime.label"/></th>
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
        var siteTable = $('#site-table').DataTable({
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
                'url': "${rootURL}site/list/all",
                'type': "GET",
                'dataType': 'json'
            },

            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {'name': 'identity', 'targets': 1, 'data': 'identity'},
                {
                    'name': 'name', 'targets': 2, 'data': 'name',
                    'render': function (data, type, row) {
                        return '<a href=' + "${rootURL}site" + '/show/'
                                + row['id'] + '>' + data + '</a>';
                    }
                },
                {'name': 'merchant', 'targets': 3, 'data': 'merchant'},
                {'name': 'url', 'targets': 4, 'data': 'url'},
                {'name': 'createdTime', 'targets': 5, 'searchable': false, 'data': 'createdTime'},
                {
                    'name': 'siteStatus', 'targets': 6, 'searchable': false,
                    'orderable': false, 'data': 'siteStatus',
                    'render': function (data, type, row) {
                        if (data == 'Created') {
                            return "${createdStatus}";
                        } else if (data == 'Frozen') {
                            return "${frozenStatus}";
                        } else if (data == 'Approved') {
                            return "${approvedStatus}";
                        } else if (data == 'Declined') {
                            return "${declinedStatus}";
                        }
                    }
                },
                {
                    // site can be edited, approved and declined under the status of Created;
                    // site can be edited, frozen under the status of Approved;
                    // site can be edited, unfrozen under the status of Frozen;
                    // site can be edited, approved under the status of Declined
                    'name': 'operation', 'targets': 7, 'orderable': false, 'searchable': false,
                    'render': function (data, type, row) {
                        var operations = '<button type="button" name="edit-button" '
                                + 'class="btn btn-default" value="' + row['id'] + '">'
                                + '<spring:message code="action.edit.label"/>'
                                + '</button>';
                        if (row['siteStatus'] == 'Created') { // if the merchant is active
                            operations += '<button type="button" name="approve-button"'
                                    + ' data-identity="' + row['identity'] + '"'
                                    + ' class="btn btn-default" value="' + row['id'] + '">' +
                                    "${approveLabel}"
                                    + '</button>';
                            operations += '<button type="button" name="decline-button"'
                                    + ' data-identity="' + row['identity'] + '"'
                                    + ' class="btn btn-default" value="' + row['id'] + '">' +
                                    "${declineLabel}"
                                    + '</button>';
                        } else if (row['siteStatus'] == 'Frozen') {
                            // if the merchant is deactivated
                            operations += '<button type="button" name="unfreeze-button"'
                                    + ' data-identity="' + row['identity'] + '"'
                                    + ' class="btn btn-default" value="' + row['id'] + '">' +
                                    "${unfreezeLabel}"
                                    + '</button>';
                        } else if (row['siteStatus'] == 'Approved') {
                            // if the merchant is deactivated
                            operations += '<button type="button" name="freeze-button"'
                                    + ' data-identity="' + row['identity'] + '"'
                                    + ' class="btn btn-default" value="' + row['id'] + '">' +
                                    "${freezeLabel}"
                                    + '</button>';
                        } else if (row['siteStatus'] == 'Declined') {
                            operations += '<button type="button" name="approve-button"'
                                    + ' data-identity="' + row['identity'] + '"'
                                    + ' class="btn btn-default" value="' + row['id'] + '">' +
                                    "${approveLabel}"
                                    + '</button>';
                        }
                        operations += '<button type="button" name="archive-button"'
                                + ' data-identity="' + row['identity'] + '"'
                                + ' class="btn btn-default" value="' + row['id'] + '">' +
                                "${archiveLabel}"
                                + '</button>';
                        return operations;
                    }
                }
            ]
        });

        // add live handler for edit button
        siteTable.on('click', 'button[type=button][name=edit-button]', function
                (event) {
            event.preventDefault();
            $.ajax({
                type: 'get',
                url: "${rootURL}site/edit",
                data: {
                    siteId: this.value
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
                            url: "${rootURL}site/edit",
                            data: {
                                id: $("#siteId").val(),
                                name: $("#name").val(),
                                siteStatus: $("#siteStatusId").val(),
                                returnUrl: $("#returnUrl").val(),
                                url: $("#url").val(),
                                remark: $("#remark").val(),
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
                                siteTable.ajax.reload();
                            }
                        });
                    });
                }
            });
        });

        // add live handler for approve button
        siteTable.on('click', 'button[type=button][name=approve-button', function(event) {
            event.preventDefault();
            var id = this.value;
            var identity = $(this).data("identity");
            $("#confirm-dialog").dialog({
                resizable: true,
                height: 'auto',
                width: 'auto',
                modal: true,
                open: function () {
                    var content = "${freezeMsg}" + ' ' + identity;
                    $(this).html(content);
                },
                buttons: {
                    "${freezeLabel}": function () {
                        $(this).dialog("close");
                        $.ajax({
                            type: 'POST',
                            url: "${rootURL}site" + '/approve',
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
                                siteTable.ajax.reload();
                            }
                        });
                    },
                    "${cancelLabel}": function () {
                        $(this).dialog("close");
                    }
                }
            });
        });
        // add live handler for decline button
        siteTable.on('click', 'button[type=button][name=decline-button', function(event) {});
        // add live handler for freeze button
        siteTable.on('click', 'button[type=button][name=freeze-button', function(event) {});
        // add live handler for unfreeze button
        siteTable.on('click', 'button[type=button][name=unfreeze-button', function(event) {});

        // add live handler for remove button
        siteTable.on('click', 'button[type=button][name=delete-button]', function (event) {
            event.preventDefault();
            var id = this.value;
            $.ajax({
                type: 'POST',
                url: "${rootURL}site" + '/delete',
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
                    siteTable.ajax.reload();
                }
            });
        });
    });
</script>
