<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<c:if test="${domain != null}">
    <spring:message code="${domain}.label" var="entity"/>
    <spring:message code="Site.label" var="site"/>
</c:if>

<div id="content">
    <div id="content-header">
        <div id="breadcrumb">
            <a href="${rootURL}">
                <i class="icon icon-home"></i>
                <spring:message code="home.label"/>
            </a>
            <a href="${rootURL}${controller}/indexAll">
                <spring:message code="manage.label" arguments="${site}"/>
            </a>
            <a href="${rootURL}${controller}/${action}" class="current">
                <spring:message code="index.label" arguments="${entity}"/>
            </a>
        </div>
    </div>

    <!-- close of content-header -->
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="col-sm-12">
                <div class="widget-box">
                    <div class="widget-title">
                        <span class="icon"><i class="icon icon-th"></i> </span>
                        <h5><spring:message code="index.label" arguments="${entity}"/></h5>
                    </div>
                    <div class="widget-content">
                        <table class="table display table-bordered data-table" id="site-table">
                            <thead>
                            <tr>
                                <th><spring:message code="id.label"/></th>
                                <th><spring:message code="identity.label"/></th>
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
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        var siteTable = $('#site-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': true,
            'serverSide': true,
            'paging': true,
            "paginationType": "full_numbers",
            "order": [[0, "desc"]],
            "jQueryUI": true,
            'dom': 'T<""if>rt<"F"lp>',
            "tableTools": {
                "sSwfPath": "${tableTools}",
                "aButtons": [
                    {
                        "sExtends": "copy",
                        "mColumns": [1, 2, 3, 4, 5]
                    },
                    {
                        "sExtends": "xls",
                        "mColumns": [1, 2, 3, 4, 5]
                    }
                ]
            },

            'ajax': {
                'url': "${rootURL}${controller}/listCreated",
                'type': "GET",
                'dataType': 'json'
            },

            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {'name': 'identity', 'targets': 1, 'data': 'identity'},
                {
                    'name': 'name', 'targets': 2, 'data': 'name'
                },
                {'name': 'url', 'targets': 3, 'data': 'url'},
                {'name': 'createdTime', 'targets': 4, 'searchable': false, 'data': 'createdTime'},
                {
                    'name': 'siteStatus', 'targets': 5, 'searchable': false,
                    'orderable': false, 'data': 'siteStatus'
                },
                {
                    'name': 'operation', 'targets': 6, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        return '<button type="button" name="audit-button"'
                                + ' class="tableButton" value="' + row['id'] + '">' +
                                '<spring:message code="action.audit.label"/>' +
                                '</button>';
                    }
                }
            ]
        });

        // add live handler for audit button
        siteTable.on('click', 'button[type=button][name=audit-button]', function (event) {
            event.preventDefault();
            var id = this.value;
            $.ajax({
                type: 'POST',
                url: "${rootURL}${controller}" + '/audit',
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
                    siteTable.ajax.reload();
                }
            });
        });
    });
</script>
