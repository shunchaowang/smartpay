<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<div class="row">
    <div class="col-sm-6">
        <h3><b><spring:message code="user.list.label"/></b></h3>
    </div>
    <!-- end of pull-left -->
    <div class="col-sm-6">
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a href="${rootURL}${controller}/createAdmin">
            <button type="button" class="btn btn-primary" id="new-admin-button">
                <spring:message code="user.new.admin.label"/>
            </button>
            </a>
            <a href="${rootURL}${controller}/createMerchantAdmin">
            <button type="button" class="btn btn-primary" id="new-merchant-admin-button">
                <spring:message code="user.new..merchant.admin.label"/>
            </button>
            </a>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_MERCHANT_ADMIN')">
            <a href="${rootURL}${controller}/createMerchantOperator">
            <button type="button" class="btn btn-primary" id="new-merchant-operator-button">
                <spring:message code="user.new.merchant.operator.label"/>
            </button>
            </a>
        </sec:authorize>
    </div>
    <!-- end of pull-right -->
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
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {'name': 'username', 'targets': 1, 'data': 'username'},
                {'name': 'firstName', 'targets': 2, 'data': 'firstName'},
                {'name': 'lastName', 'targets': 3, 'data': 'lastName'},
                {'name': 'email', 'targets': 4, 'data': 'email'},
                {'name': 'createdTime', 'targets': 5, 'searchable': false, 'data': 'createdTime'},
                {
                    'name': 'userStatus', 'targets': 6, 'searchable': false,
                    'orderable': false, 'data': 'userStatus'
                }
            ]
        });
    });
</script>