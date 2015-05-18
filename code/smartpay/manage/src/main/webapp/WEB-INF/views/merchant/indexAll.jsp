<%@include file="../taglib.jsp" %>

<spring:message code="merchant.label" var="entity"/>
<spring:message code="freeze.confirm.message" arguments="${entity}" var="freezeMsg"/>
<spring:message code="unfreeze.confirm.message" arguments="${entity}" var="unfreezeMsg"/>
<spring:message code="archive.confirm.message" arguments="${entity}" var="archiveMsg"/>
<spring:message code="action.delete.label" var="deleteLabel"/>
<spring:message code="action.cancel.label" var="cancelLabel"/>
<spring:message code="action.freeze.label" var="freezeLabel"/>
<spring:message code="action.unfreeze.label" var="unfreezeLabel"/>
<spring:message code="action.archive.label" var="archiveLabel"/>
<spring:message code="status.normal.label" var="normalStatus"/>
<spring:message code="status.frozen.label" var="frozenStatus"/>

<div class="container-fluid">
    <div class="row">
        <ol class="breadcrumb">
            <li>
                <i class="glyphicon glyphicon-home"></i>
                <spring:message code="home.label"/>
            </li>
            <li>
                <i class="glyphicon glyphicon-list"></i>
                <spring:message code="manage.label" arguments="${entity}"/>
            </li>
            <li class="active">
                <i class="glyphicon glyphicon-list"></i>
                <spring:message code="index.label" arguments="${entity}"/>
            </li>
        </ol>
    </div>
    <div class="row">
        <div class="col-sm-2 pull-left">
            <a href="${rootURL}merchant/create">
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
            <table class="table table-bordered" id="merchant-table">
                <thead>
                <tr>
                    <th><spring:message code="id.label"/></th>
                    <th><spring:message code="identity.label"/></th>
                    <th><spring:message code="name.label"/></th>
                    <th><spring:message code="address.label"/></th>
                    <th><spring:message code="contact.label"/></th>
                    <th><spring:message code="tel.label"/></th>
                    <th><spring:message code="email.label"/></th>
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
        var merchantTable = $('#merchant-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': true,
            'serverSide': true,
            'paging': true,
            "paginationType": "full_numbers",
            "order": [[1, "desc"]],
            //"jQueryUI": true,
            /*'dom': 'T<""if>rt<"F"lp>',*/
            "tableTools": {
                "sSwfPath": "${tableTools}",
                "aButtons": [
                    {
                        "sExtends": "copy",
                        "mColumns": [1, 2, 3, 4, 5, 6, 7, 8]
                    },
                    {
                        "sExtends": "xls",
                        "mColumns": [1, 2, 3, 4, 5, 6, 7, 8]
                    }
                ]
            },
            'ajax': {
                'url': "${rootURL}merchant/list/all",
                'type': "GET",
                'dataType': 'json'
            },
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {
                    'name': 'identity', 'targets': 1, 'data': 'identity'
                },
                {
                    'name': 'name', 'targets': 2, 'data': 'name', 'orderable': false,
                    'render': function (data, type, row) {
                        return '<a href=' + "${rootURL}merchant" + '/show/'
                                + row['id'] + '>' + data + '</a>';
                    }
                },
                {
                    'name': 'address', 'targets': 3, 'searchable': false, 'orderable': false,
                    'data': 'address'
                },
                {
                    'name': 'contact', 'targets': 4, 'searchable': false, 'orderable': false,
                    'data': 'contact'
                },
                {
                    'name': 'tel', 'targets': 5, 'searchable': false, 'orderable': false,
                    'data': 'tel'
                },
                {'name': 'email', 'targets': 6, 'data': 'email', 'orderable': false},
                {
                    'name': 'createdTime', 'targets': 7, 'searchable': false,
                    'data': 'createdTime'
                },
                {
                    'name': 'merchantStatus', 'targets': 8, 'searchable': false,
                    'orderable': false, 'data': 'merchantStatus',
                    'render': function (data, type, row) {
                        if (data == 'Normal') {
                            return "${normalStatus}";
                        } else if (data == 'Frozen') {
                            return "${frozenStatus}";
                        }
                    }
                },
                {
                    'name': 'operation', 'targets': 9, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        var operations = '<button type="button" name="edit-button" '
                                + 'class="btn btn-default" value="' + row['id'] + '">'
                                + '<spring:message code="action.edit.label"/>'
                                + '</button>';
                        if (row['merchantStatus'] == 'Normal') { // if the merchant is active
                            operations += '<button type="button" name="freeze-button"'
                                    + ' data-identity="' + row['identity'] + '"'
                                    + ' class="btn btn-default" value="' + row['id'] + '">' +
                                    "${freezeLabel}"
                                    + '</button>';
                        } else if (row['merchantStatus'] == 'Frozen') {
                            // if the merchant is deactivated
                            operations += '<button type="button" name="unfreeze-button"'
                                    + ' data-identity="' + row['identity'] + '"'
                                    + ' class="btn btn-default" value="' + row['id'] + '">' +
                                    "${unfreezeLabel}"
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

        // add live handler for freeze button
        merchantTable.on('click', 'button[type=button][name=freeze-button]', function (event) {
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
                            url: "${rootURL}merchant" + '/freeze',
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
                                merchantTable.ajax.reload();
                            }
                        });
                    },
                    "${cancelLabel}": function () {
                        $(this).dialog("close");
                    }
                }
            });
        });

        // add live handler for unfreeze button
        merchantTable.on('click', 'button[type=button][name=unfreeze-button]', function (event) {
            event.preventDefault();
            var id = this.value;
            var identity = $(this).data("identity");
            $("#confirm-dialog").dialog({
                resizable: true,
                height: 'auto',
                width: 'auto',
                modal: true,
                open: function () {
                    var content = "${unfreezeMsg}" + ' ' + identity;
                    $(this).html(content);
                },
                buttons: {
                    "${unfreezeLabel}": function () {
                        $(this).dialog("close");
                        $.ajax({
                            type: 'POST',
                            url: "${rootURL}merchant" + '/unfreeze',
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
                                merchantTable.ajax.reload();
                            }
                        });
                    },
                    "${cancelLabel}": function () {
                        $(this).dialog("close");
                    }
                }
            });
        });

        // add live handler for edit button
        merchantTable.on('click', 'button[type=button][name=edit-button]', function
                (event) {
            event.preventDefault();
            $.ajax({
                type: 'get',
                url: "${rootURL}merchant/edit",
                data: {
                    merchantId: this.value
                },
                error: function () {
                    alert('There was an error.');
                },
                success: function (data) {
                    $('#dialog-area').append(data);

                    // define dialog
                    var basicInfoDialog = $("#basic-info-dialog").dialog({
                        autoOpen: false,
                        height: 'auto',
                        width: 'auto',
                        modal: true,
                        close: function () {
                            basicInfoDialog.dialog("destroy").remove();
                        }
                    }).dialog("open");

                    $("#cancel-button").click(function (event) {
                        event.preventDefault();
                        basicInfoDialog.dialog("close");
                    });

                    $("#save-button").click(function (event) {
                        event.preventDefault();
                        if (!$("#basic-info-form").valid()) {
                            return;
                        }
                        $.ajax({
                            type: "POST",
                            url: "${rootURL}merchant/edit",
                            data: {
                                id: $("#merchantId").val(),
                                name: $("#name").val(),
                                merchantStatus: $("#merchantStatusId").val(),
                                email: $("#email").val(),
                                contact: $("#contact").val(),
                                address: $("#address").val(),
                                tel: $("#tel").val(),
                                credentialContent: $("#credentialContent").val(),
                                credentialStatus: $("#credentialStatusId").val(),
                                credentialExpirationTime: $("#credentialExpirationTime").val(),
                                credentialType: $("#credentialTypeId").val()
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
                                basicInfoDialog.dialog("close");
                                merchantTable.ajax.reload();
                            }
                        });
                    });
                }
            });
        });

        // add live handler for unfreeze button
        merchantTable.on('click', 'button[type=button][name=archive-button]', function (event) {
            event.preventDefault();
            var id = this.value;
            var identity = $(this).data("identity");
            $("#confirm-dialog").dialog({
                resizable: true,
                height: 'auto',
                width: 'auto',
                modal: true,
                open: function () {
                    var content = "${archiveMsg}" + ' ' + identity;
                    $(this).html(content);
                },
                buttons: {
                    "${archiveLabel}": function () {
                        $(this).dialog("close");
                        $.ajax({
                            type: 'POST',
                            url: "${rootURL}merchant" + '/archive',
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
                                merchantTable.ajax.reload();
                            }
                        });
                    },
                    "${cancelLabel}": function () {
                        $(this).dialog("close");
                    }
                }
            });
        });
    });
</script>