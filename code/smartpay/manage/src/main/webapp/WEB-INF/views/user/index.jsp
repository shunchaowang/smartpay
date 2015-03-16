<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<div class="row">
    <div class="col-xs-6 pull-left">
        <b>My Bundles</b>
    </div>
    <!-- end of pull-left -->
    <div class="col-xs-2 pull-right">
        <button type="button" class="btn btn-primary" id="new-button">
            <spring:message code="user.new.label"/>
        </button>
    </div>
    <!-- end of pull-right -->
</div>
<!-- end of class row -->
<br/>

<div class="row">
    <table class="display cell-border" id="user-table">
        <thead>
        <tr>
            <!--<th><spring:message code="id.label"/></th>-->
            <th><spring:message code="user.username.label"/></th>
            <!--
            <th><spring:message code="user.firstName.label"/></th>
            <th><spring:message code="user.lastName.label"/></th>
            <th><spring:message code="user.email.label"/></th>
            <th><spring:message code="user.createdTime.label"/></th>
            <th><spring:message code="user.userStatus.label"/></th>
            -->
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>

<script type="text/javascript">
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
            'columnDefs': [
                //{'name': 'id', 'targets': 0, 'visible': true},
                {'name': 'username', 'targets': 0}/*,
                {'name': 'firstName', 'targets': 1},
                {'name': 'lastName', 'targets': 2},
                {'name': 'email', 'targets': 3},
                {'name': 'createdTime', 'targets': 4, 'searchable': false},
                {
                    'name': 'userStatus', 'targets': 5, 'searchable': false,
                    'orderable': false
                }*/
            ]
        });
    });
</script>