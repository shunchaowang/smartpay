<%@include file="../taglib.jsp" %>

<spring:message code="merchant.label" var="entity"/>
<spring:message code="delete.confirm.message" arguments="${entity}" var="deleteMsg"/>
<spring:message code="action.delete.label" var="deleteLabel"/>
<spring:message code="action.cancel.label" var="cancelLabel"/>
<spring:message code="action.restore.label" var="restoreLabel"/>
<spring:message code="restore.confirm.message" arguments="${entity}" var="restoreMsg"/>
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
                <i class="glyphicon glyphicon-briefcase"></i>
                <spring:message code="archive.label" arguments="${entity}"/>
            </li>
        </ol>
    </div>
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
                'url': "${rootURL}merchant/list/archive",
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
                    'name': 'name', 'targets': 2, 'data': 'name', 'orderable': false
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
                        return '<button type="button" name="restore-button"'
                                + ' data-identity="' + row['identity'] + '"'
                                + ' class="btn btn-default" value="' + row['id'] + '">' +
                                "${restoreLabel}"
                                + '</button>'
                                + '<button type="button" name="delete-button"'
                                + ' data-identity="' + row['identity'] + '"'
                                + ' class="btn btn-default" value="' + row['id'] + '">' +
                                "${deleteLabel}"
                                + '</button>';
                    }
                }
            ]
        });

        // add live handler for restore button
        merchantTable.on('click', 'button[type=button][name=restore-button]', function (event) {
            event.preventDefault();
            var id = this.value;
            var identity = $(this).data("identity");
            $("#confirm-dialog").dialog({
                resizable: true,
                height: 'auto',
                width: 'auto',
                modal: true,
                open: function () {
                    var content = "${restoreMsg}" + ' ' + identity;
                    $(this).html(content);
                },
                buttons: {
                    "${restoreLabel}": function () {
                        $(this).dialog("close");
                        $.ajax({
                            type: 'POST',
                            url: "${rootURL}merchant" + '/restore',
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

        // add live handler for delete button
        merchantTable.on('click', 'button[type=button][name=delete-button]', function (event) {
            event.preventDefault();
            var id = this.value;
            var identity = $(this).data("identity");
            $("#confirm-dialog").dialog({
                resizable: true,
                height: 'auto',
                width: 'auto',
                modal: true,
                open: function () {
                    var content = "${deleteMsg}" + ' ' + identity;
                    $(this).html(content);
                },
                buttons: {
                    "${deleteLabel}": function () {
                        $(this).dialog("close");
                        $.ajax({
                            type: 'POST',
                            url: "${rootURL}merchant" + '/delete',
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