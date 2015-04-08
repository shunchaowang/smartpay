<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="${domain}.label" var="entity"/>

<div class="row-fluid">
    <div class="col-sm-12">
        <div class="widget-box">
            <div class="widget-title">
                <span class="icon"><i class="icon icon-th"></i> </span>
                <h5><spring:message code="index.label" arguments="${entity}"/></h5>
            </div>
            <div class="widget-content">
                <table class="table display table-bordered data-table" id="user-table">
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
</div>


<script type="text/javascript">
    $(document).ready(function () {

        $('#user-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
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
                                + row['id'] + '>' + data + '</a>';
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
                        return '<a href="' + "${rootURL}${controller}" + '/edit/'
                                + row['id'] + '">' +
                                '<button type="button" name="edit-button" class="tableButton"'
                                + '">' + '<spring:message code="action.edit.label"/>'
                                + '</button>' + '</a>' + ' '
                                + '<a href="' + "${rootURL}${controller}" + '/delete/'
                                + row['id'] + '">' +
                                '<button type="button" name="delete-button" class="tableButton"'
                                + '">' + '<spring:message code="action.delete.label"/>'
                                + '</button>' + '</a>';
                    }
                }
            ]
        });

    });
</script>