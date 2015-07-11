<%@include file="../taglib.jsp" %>

<spring:message code="operator.label" var="entity"/>
<spring:message code="permission.label" var="permissionLabel"/>

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
                <spring:message code="manage.label" arguments="${permissionLabel}"/>
            </li>
        </ol>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <table class="table table-bordered" id="user-table">
                <thead>
                <tr>
                    <th><spring:message code="id.label"/></th>
                    <th><spring:message code="username.label"/></th>
                    <th><spring:message code="firstName.label"/></th>
                    <th><spring:message code="lastName.label"/></th>
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
        var userTable = $('#user-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': true,
            'serverSide': true,
            'paging': true,
            "paginationType": "full_numbers",
            "order": [[0, "desc"]],
            'ajax': {
                'url': "${rootURL}user/list/operator",
                'type': "GET",
                'dataType': 'json'
            },
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {
                    'name': 'username', 'targets': 1, 'data': 'username',
                    'orderable': false/*,
                    'render': function (data, type, row) {
                        return '<a href=' + "${rootURL}user" + '/show/permission/'
                                + row['id'] + '>' + data + '</a>';
                    }*/
                },
                {
                    'name': 'firstName', 'targets': 2, 'data': 'firstName', 'searchable': false,
                    'orderable': false
                },
                {
                    'name': 'lastName', 'targets': 3, 'data': 'lastName', 'searchable': false,
                    'orderable': false
                },
                {
                    'name': 'email', 'targets': 4, 'data': 'email', 'searchable': false,
                    'orderable': false
                },
                {'name': 'createdTime', 'targets': 5, 'searchable': false, 'data': 'createdTime'},
                {
                    'name': 'userStatus', 'targets': 6, 'searchable': false,
                    'orderable': false, 'data': 'userStatus'
                },
                {
                    'name': 'operation', 'targets': 7, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        return '<a href="' + "${rootURL}user" + '/edit/permission/'
                                + row['id'] + '">'
                                + '<button type="button" name="edit-permission-button" '
                                + 'class="btn btn-default" value="' + row['id'] + '">'
                                + '<spring:message code="permission.label"/>'
                                + '</button>'
                                + '</a>';
                    }
                }
            ]
        });
    });
</script>
