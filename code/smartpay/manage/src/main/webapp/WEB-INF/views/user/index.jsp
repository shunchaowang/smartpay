<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<div class="row">
    <div class="col-sm-6">
        <h3><b><spring:message code="user.list.label"/></b></h3>
    </div>
    <!-- end of table title -->
</div>
<!-- end of class row -->
<br/>

<div class="row">
    <table class="display cell-border" id="user-table">
        <thead>
        <tr>
            <th><spring:message code="id.label"/></th>
            <th><spring:message code="user.username.label"/></th>
            <th><spring:message code="user.firstName.label"/></th>
            <th><spring:message code="user.lastName.label"/></th>
            <th><spring:message code="user.email.label"/></th>
            <th><spring:message code="user.createdTime.label"/></th>
            <th><spring:message code="user.userStatus.label"/></th>
            <th><spring:message code="operation.label"/></th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>

<script type="text/javascript">
    <spring:message var="operationLabel" code="operation.label"/>
    <spring:message var="editLabel" code="edit.label"/>
    $(document).ready(function () {
        $('#user-table').DataTable({
            'processing': true,
            'serverSide': true,
            'paging': true,

            'ajax': {
                'url': "${rootURL}${controller}/list",
                'type': "GET",
                'dataType': 'json'
            },
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {
                    'name': 'username', 'targets': 1, 'data': 'username',
                    'render': function (data, type, row) {
                        return '<a href=' + "${rootURL}${controller}" + '/show/'
                                + row[0] + '>' + data + '</a>';
                    }
                },
                {'name': 'firstName', 'targets': 2, 'data': 'firstName'},
                {'name': 'lastName', 'targets': 3, 'data': 'lastName'},
                {'name': 'email', 'targets': 4, 'data': 'email'},
                {'name': 'createdTime', 'targets': 5, 'searchable': false, 'data': 'createdTime'},
                {
                    'name': 'userStatus', 'targets': 6, 'searchable': false,
                    'orderable': false, 'data': 'userStatus'
                },
                {
                    'name': 'operation', 'targets': 7, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        return '<button type="button" name="edit-button" class="btn btn-default" '
                                + ' value="' + "${rootURL}${controller}" + '/show/'
                                + row[0] + '">' + "${editLabel}" + '</button>';
                    }
                }
            ]
        });
    });
</script>