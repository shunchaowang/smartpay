<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="${domain}.label" var="preEntry"/>
<spring:message code="freeze.label" arguments="${preEntry}" var="entity"/>

<div class='row' id='notification'>
    <c:if test="${not empty message}">
        <div class="alert alert-danger alert-dismissable" role="alert">
            <button type="button" class="close" data-dismiss="alert">
                <span aria-hidden="true">&times;</span>
                <span class="sr-only"><spring:message code="action.close.label"/> </span>
            </button>
                ${message}
        </div>
    </c:if>
</div>

<div class="row">
    <table class="display cell-border" id="freezeList-table">
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
        $('#freezeList-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': true,
            'serverSide': true,
            'paging': true,

            'ajax': {
                'url': "${rootURL}${controller}/showFreezeList",
                'type': "GET",
                'dataType': 'json'
            },

            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {
                    'name': 'name', 'targets': 1, 'data': 'name',
                    'render': function (data, type, row) {
                        return '<a href=' + "${rootURL}${controller}" + '/showFreezeInfo/'
                                + row['id'] + '>' + data + '</a>';
                    }
                },
                {'name': 'url', 'targets': 2, 'data': 'url'},
                {'name': 'createdTime', 'targets': 3, 'searchable': false, 'data': 'createdTime'},
                {
                    'name': 'siteStatus', 'targets': 4, 'searchable': false,
                    'orderable': false, 'data': 'siteStatus'
                },
                {
                    'name': 'operation', 'targets': 5, 'orderable': false, 'searchable': false,
                    'render': function (data, type, row) {
                        return '<a href="' + "${rootURL}${controller}/editSite/freeze/" +
                                row['id'] + '">'
                                + '<spring:message code="action.freeze.label"/>'
                                + '</a>';
                    }
                }
            ]
        });
    });
</script>
