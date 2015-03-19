<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<div class="row">
    <div class="col-xs-6 pull-left">
        <h2><b><spring:message code="site.list.label"/></b></h2>
    </div>
    <!-- end of pull-left -->
    <!-- <sec:authorize access="hasRole('ROLE_ADMIN')"> -->
    <div class="col-xs-2 pull-right">
        <a href="${rootURL}${controller}/createSite">
            <button type="button" class="btn btn-primary" id="new-button">
                <spring:message code="site.new.label"/>
            </button>
        </a>
    </div>
    <!-- </sec:authorize> -->
    <!-- end of pull-right -->
</div>
<!-- end of class row -->
<br/>

<div class="row">
    <table class="display cell-border" id="site-table">
        <thead>
        <tr>
            <th><spring:message code="id.label"/></th>
            <th><spring:message code="site.name.label"/></th>
            <th><spring:message code="site.url.label"/></th>
            <th><spring:message code="site.createdTime.label"/></th>
            <th><spring:message code="site.status.label"/></th>
            <th><spring:message code="operation.label"/></th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#site-table').DataTable({
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
                {'name': 'name', 'targets': 1, 'data': 'name'},
                {'name': 'url', 'targets': 2, 'data': 'url'},
                {'name': 'createdTime', 'targets': 3, 'searchable': false, 'data': 'createdTime'},
                {
                    'name': 'siteStatus', 'targets': 4, 'searchable': false,
                    'orderable': false, 'data': 'siteStatus'
                },
                {
                    'name': 'operation', 'targets': 5, 'orderable': false, 'searchable': false,
                    'render': function (data, type, row) {
                        return "<a href=" + "${rootURL}${controller}/audit/" + row['id'] +
                                "><spring:message code="site.audit.label"/></a> "
                    }
                }
            ]
        });
    });
</script>
