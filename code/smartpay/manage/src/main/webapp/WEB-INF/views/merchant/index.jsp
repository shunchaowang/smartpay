<%@include file="../taglib.jsp" %>

<spring:message code="merchant.label" var="entity"/>

<div class="container-fluid">
    <div class="row">
        <ol class="breadcrumb">
            <li>
                <a href="${rootURL}">
                    <i class="glyphicon glyphicon-home"></i>
                    <spring:message code="home.label"/>
                </a>
            </li>
            <li class="active">
                <a href="${rootURL}merchant/index" class="current">
                    <i class="glyphicon glyphicon-list"></i>
                    <spring:message code="index.label" arguments="${entity}"/>
                </a>
            </li>
        </ol>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <table class="table display table-bordered data-table" id="merchant-table">
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
<div class="confirmDialog" id="confirm-dialog" title="Empty the recycle bin?">
    <p>These
        items will be permanently deleted and cannot be recovered. Are you sure?</p>
</div>
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
                'url': "${rootURL}merchant/list",
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
                    'orderable': false, 'data': 'merchantStatus'
                },
                {
                    'name': 'operation', 'targets': 9, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        return '<a href="' + "${rootURL}merchant" + '/edit/'
                                + row['id'] + '">' +
                                '<button type="button" name="edit-button" class="tableButton"'
                                + '">' + '<spring:message code="action.edit.label"/>'
                                + '</button></a>' + ' '
                                + '<a href="' + "${rootURL}merchant" + '/editFee/'
                                + row['id'] + '">' +
                                '<button type="button" name="edit-button" class="tableButton"'
                                + '">' + '<spring:message code="setting.label"/>'
                                + '</button></a>' + ' '
                                + '<button type="button" name="delete-button"'
                                + ' class="tableButton" value="' + row['id'] + '">' +
                                '<spring:message code="action.delete.label"/>' +
                                '</button>';
                    }
                }
            ]
        });

        // add live handler for remove button
        merchantTable.on('click', 'button[type=button][name=delete-button]', function (event) {
            event.preventDefault();
            $("#confirm-dialog").dialog({
                resizable: false,
                height: 140,
                modal: true,
                buttons: {
                    "Delete all items": function () {
                        $(this).dialog("close");
                    },
                    Cancel: function () {
                        $(this).dialog("close");
                    }
                }
            });
            var id = this.value;
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
        });

        // add live handler for freeze button
        merchantTable.on('click', 'button[type=button][name=freeze-button]', function (event) {
            event.preventDefault();
            var id = this.value;
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
        });

        // add live handler for unfreeze button
        merchantTable.on('click', 'button[type=button][name=unfreeze-button]', function (event) {
            event.preventDefault();
            var id = this.value;
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
        });
    });
</script>