<%@include file="../taglib.jsp" %>
<c:if test="${domain != null}">
    <spring:message code="${domain}.label" var="entity"/>
</c:if>

<div id="content">
    <div id="content-header">
        <div id="breadcrumb">
            <a href="${rootURL}">
                <i class="icon icon-home"></i>
                <spring:message code="home.label"/>
            </a>
            <a href="${rootURL}${controller}/${action}" class="current">
                <spring:message code="${action}.label" arguments="${entity}"/>
            </a>
        </div>
    </div>
    <!-- reserved for notification -->
    <!-- close of content-header -->
    <div class="container-fluid">
        <!— actual content —>
        <div class="row-fluid">
            <div class="col-sm-12">
                <div class="widget-box">
                    <div class="widget-title">
                        <span class="icon"><i class="icon icon-th"></i> </span>
                        <h5><spring:message code="index.label" arguments="${entity}"/></h5>
                    </div>
                    <div class="widget-content">
                        <table class="table display table-bordered data-table" id="announcement-table">
                            <thead>
                            <tr>
                                <th><spring:message code="id.label"/></th>
                                <th><spring:message code="announcement.title.lable"/></th>
                                <th><spring:message code="createdTime.label"/></th>
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
        var announcementTable = $('#announcement-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': true,
            'serverSide': true,
            'paging': true,
            "paginationType": "full_numbers",
            "bAutoWidth":true,
            "order": [[0, "desc"]],
            "jQueryUI": true,
            'dom': '<""if>rt<"F"lp>',
            'ajax': {
                'url': "${rootURL}${controller}/list",
                'type': "GET",
                'dataType': 'json'
            },
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0,  'data': 'id', 'searchable': false, 'visible': false},
                {
                    'name': 'title', 'targets': 1, 'data': 'title',
                    'searchable': false, 'orderable': false, "sWidth": "73%"
                },
                {
                    'name': 'createdTime', 'targets': 2, 'data': 'createdTime',"sWidth": "12%"
                },
                {
                    'name': 'operation', 'targets': 3, 'searchable': false, 'orderable': false,"sWidth": "15%",
                    'render': function (data, type, row) {
                        return '<a href="' + "${rootURL}${controller}" + '/edit/'
                                + row['id'] + '">' +
                                '<button type="button" name="edit-button" class="tableButton"'
                                + '">' + '<spring:message code="action.edit.label"/>'
                                + '</button></a>' + ' '
                                + '<button type="button" name="delete-button"'
                                + ' class="tableButton" value="' + row['id'] + '">' +
                                '<spring:message code="action.delete.label"/>' +
                                '</button>';
                    }
                }
            ]
        });
        // add live handler for remove button
        announcementTable.on('click', 'button[type=button][name=delete-button]', function (event) {
            event.preventDefault();
            var id = this.value;
            $.ajax({
                type: 'POST',
                url: "${rootURL}${controller}" + '/delete',
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
                    announcementTable.ajax.reload();
                }
            });
        });
    });

</script>