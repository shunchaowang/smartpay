<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="announcement.label" var="entity"/>
<spring:message code="details.label" arguments="{entity}"/>

<div class="container-fluid">
    <div class="row">
        <ol class="breadcrumb">
            <li>
                <i class="glyphicon glyphicon-home"></i>
                <spring:message code="home.label"/>
            </li>
            <li class="active">
                <i class="glyphicon glyphicon-list"></i>
                <spring:message code="index.label" arguments="${entity}"/>
            </li>
        </ol>
    </div>
    <div class="row">
        <div class="col-sm-2 pull-left">
            <a href="${rootURL}announcement/create">
                <button class="btn btn-default">
                    <i class="glyphicon glyphicon-wrench"></i>
                    <spring:message code="create.label" arguments="${entity}"/>
                </button>
            </a>
        </div>
    </div>
    <br>

    <!-- close of content-header -->
    <div class="row">
        <div class="col-sm-12">
            <table class="table table-bordered" id="announcement-table">
                <thead>
                <tr>
                                <th><spring:message code="id.label"/></th>
                                <th><spring:message code="announcement.label"/></th>
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
            'dom': '<""if>rt<"F"lp>',
            'ajax': {
                'url': "${rootURL}announcement/list/all",
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
                        var operations = '<button type="button" name="edit-button" '
                                + 'class="btn btn-default" value="' + row['id'] + '">'
                                + '<spring:message code="action.edit.label"/>'
                                + '</button>';
                        operations += '<button type="button" name="delete-button" '
                        + 'class="btn btn-default" value="' + row['id'] + '">'
                        + '<spring:message code="action.delete.label"/>'
                        + '</button>';
                        return operations;

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