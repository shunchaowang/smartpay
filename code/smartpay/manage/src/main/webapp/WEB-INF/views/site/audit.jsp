<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<div class="row">
    <div class="col-sm-6">
        <h2><b><spring:message code="site.list.label"/></b></h2>
    </div>
    <!-- end of label -->
</div>
<!-- end of class row -->
<br/>

<div class="row">
    <table class="display cell-border" id="audit-table">
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
        $('#audit-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': true,
            'serverSide': true,
            'paging': true,

            'ajax': {
                'url': "${rootURL}${controller}/auditList",
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
                        return '<a href="' + "${rootURL}${controller}/edit" + row['id'] +
                                '">' + '<spring:message code="edit.label"/>' + '</a>' +
                                '<a href="' + "${rootURL}${controller}/delete" + row['id'] +
                                '">' + '<spring:message code="delete.label"/>' + '</a>';
                    }
                }
            ]
        });
    });
</script>
