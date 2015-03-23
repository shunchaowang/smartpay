<!DOCTYPE html>
<%@include file="../../taglib.jsp" %>
<spring:message code="${domain}.label" var="entity"/>

<div class="row">
    <div class="col-sm-6">
        <h2><b><spring:message code="index.label" arguments="${entity}"/></b></h2>
    </div>
    <!-- end of label -->
</div>
<!-- end of class row -->
<br/>

<div class="row">
    <table class="display cell-border" id="site-table">
        <thead>
        <tr>
            <th><spring:message code="id.label"/></th>
            <th><spring:message code="name.label"/></th>
            <th><spring:message code="site.url.label"/></th>
            <th><spring:message code="createdTime.label"/></th>
            <th><spring:message code="status.label"/></th>
            <th><spring:message code="action.operation.label"/></th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#site-table').DataTable({
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
                        return '<a href="' + "${rootURL}${controller}/showInfo/" + row['id'] +
                                '">'
                                + '<spring:message code="show.label"/>'
                                + '</a>'
                                + ' ' +
                                '<a href="' + "${rootURL}${controller}/editInfo/" + row['id'] +
                                '">'
                                + '<spring:message code="edit.label"/>'
                                + '</a>';

                    }
                }
            ]
        });
    });
</script>
