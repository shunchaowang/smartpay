<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="${domain}.label" var="entity"/>

<div id="content">
    <div id="content-header">
        <div id="breadcrumb">
            <a href="${rootURL}">
                <i class="icon icon-home"></i>
                <spring:message code="home.label"/>
            </a>
            <c:if test="${domain != null}">
                <spring:message code="${domain}.label" var="entity"/>
                <a href="${rootURL}${controller}">
                    <spring:message code="manage.label" arguments="${entity}"/>
                </a>
                <a href="${rootURL}${controller}/${action}" class="current">
                    <spring:message code="${action}.label" arguments="${entity}"/>
                </a>
            </c:if>
        </div>
    </div>
    <!-- reserved for notification -->
    <!-- close of content-header -->
    <div class="container-fluid">
        <!— actual content —>
        <div class="row">
            <div class="col-sm-6">
                <h3><b><spring:message code="index.label" arguments="${entity}"/></b></h3>
            </div>
            <!-- end of table title -->
        </div>
        <!-- end of class row -->
        <br/>

        <div class="row">
            <table class="display cell-border" id="merchant-table">
                <thead>
                <tr>
                    <th><spring:message code="id.label"/></th>
                    <th><spring:message code="identity.label"/></th>
                    <th><spring:message code="name.label"/></th>
                    <th><spring:message code="address.label"/></th>
                    <th><spring:message code="contact.label"/></th>
                    <th><spring:message code="tel.label"/></th>
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

<script type="text/javascript">
    $(document).ready(function () {
        var merchantTable = $('#merchant-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': true,
            'serverSide': true,
            'paging': true,

            'ajax': {
                'url': "${rootURL}${controller}/${action}List",
                'type': "GET",
                'dataType': 'json'
            },
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {
                    'name': 'identity', 'targets': 1, 'searchable': false, 'orderable': false,
                    'data': 'identity'
                },
                {
                    'name': 'name', 'targets': 2, 'data': 'name',
                    'render': function (data, type, row) {
                        return '<a href=' + "${rootURL}${controller}" + '/show/'
                                + row['id'] + '>' + data + '</a>';
                    }
                },
                {
                    'name': 'address', 'targets': 3, 'searchable': false, 'orderable': false,
                    'data': 'address'
                },
                {
                    'name': 'contact', 'targets': 4, 'searchable': false, 'orderable': false,
                    'data': 'contact'
                },
                {
                    'name': 'tel', 'targets': 5, 'searchable': false, 'orderable': false,
                    'data': 'tel'
                },
                {'name': 'email', 'targets': 6, 'data': 'email'},
                {
                    'name': 'createdTime', 'targets': 7, 'searchable': false,
                    'data': 'createdTime'
                },
                {
                    'name': 'merchantStatus', 'targets': 8, 'searchable': false,
                    'orderable': false, 'data': 'merchantStatus'
                },
                {
                    'name': 'operation', 'targets': 9, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        return '<button type="button" name="freeze-button"'
                                + ' class="btn btn-default" value="' + row['id'] + '">' +
                                '<spring:message code="action.freeze.label"/>' +
                                '</button>';
                    }
                }
            ]
        });

        // add live handler for remove button
        merchantTable.on('click', 'button[type=button][name=freeze-button]', function (event) {
            event.preventDefault();
            var id = this.value;
            $.ajax({
                type: 'POST',
                url: "${rootURL}${controller}" + '/freeze',
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
                    merchantTable.ajax.reload();
                }
            });
        });
    });
</script>