<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<c:if test="${domain != null}">
    <spring:message code="${domain}.label" var="entity"/>
</c:if>
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
            <th><spring:message code="identity.label"/></th>
            <th><spring:message code="name.label"/></th>
            <th><spring:message code="site.url.label"/></th>
            <th><spring:message code="createdTime.label"/></th>
            <th><spring:message code="status.label"/></th>
            <c:if test="${domain.equals('AuditList')}">
                <th><spring:message code="action.operation.label"/></th>
            </c:if>
            <c:if test="${domain.equals('FreezeList')}">
                <th><spring:message code="action.operation.label"/></th>
            </c:if>
            <c:if test="${domain.equals('UnfreezeList')}">
                <th><spring:message code="action.operation.label"/></th>
            </c:if>
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
                'url': "${rootURL}${controller}/list${domain}",
                'type': "GET",
                'dataType': 'json'
            },

            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {'name': 'identity', 'targets': 1, 'data': 'identity'},
                {
                    'name': 'name', 'targets': 2, 'data': 'name',
                    'render': function (data, type, row) {
                        return '<a href=' + "${rootURL}${controller}" + '/show/'
                                + row['id'] + '>' + data + '</a>';
                    }
                },
                {'name': 'url', 'targets': 3, 'data': 'url'},
                {'name': 'createdTime', 'targets': 4, 'searchable': false, 'data': 'createdTime'},
                {
                    'name': 'siteStatus', 'targets': 5, 'searchable': false,
                    'orderable': false, 'data': 'siteStatus'
                },
                {
                    'name': 'operation', 'targets': 6, 'orderable': false, 'searchable': false,
                    'render': function (data, type, row) {
                        return '<a href="' + "${rootURL}${controller}/show/" + row['id'] +
                                '">'
                                + '<spring:message code="action.show.label"/>'
                                + '</a>'
                                + ' ' +
                                '<a href="' + "${rootURL}${controller}/editInfo/" + row['id'] +
                                '">'
                                + '<spring:message code="action.edit.label"/>'
                                + '</a>';

                    }
                },
                <c:if test="${domain.equals('AuditList')}">
                {
                    'name': 'operation', 'targets': 9, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        return '<button type="button" name="audit-button"'
                                + ' class="btn btn-default" value="' + row['id'] + '">' +
                                '<spring:message code="action.audit.label"/>' +
                                '</button>';
                    }
                }
                </c:if>
                <c:if test="${domain.equals('FreezeList')}">
                {
                    'name': 'operation', 'targets': 9, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        return '<button type="button" name="freeze-button"'
                                + ' class="btn btn-default" value="' + row['id'] + '">' +
                                '<spring:message code="action.freeze.label"/>' +
                                '</button>';
                    }
                }
                </c:if>
                <c:if test="${domain.equals('UnfreezeList')}">
                {
                    'name': 'operation', 'targets': 9, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        return '<button type="button" name="unfreeze-button"'
                                + ' class="btn btn-default" value="' + row['id'] + '">' +
                                '<spring:message code="action.unfreeze.label"/>' +
                                '</button>';
                    }
                }
                </c:if>
            ]
        });

        // add live handler for remove button
        merchantTable.on('click', 'button[type=button][name=delete-button]', function (event) {
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
                    merchantTable.ajax.reload();
                }
            });
        });

        // add live handler for freeze button
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

        // add live handler for unfreeze button
        merchantTable.on('click', 'button[type=button][name=unfreeze-button]', function (event) {
            event.preventDefault();
            var id = this.value;
            $.ajax({
                type: 'POST',
                url: "${rootURL}${controller}" + '/unfreeze',
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