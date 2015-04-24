<%@include file="../taglib.jsp" %>
<spring:message code="${domain}.label" var="entity"/>
<div id="claim-dialog">
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="col-sm-12">
                <div class="widget-box">
                    <div class="widget-title">
                        <span class="icon"><i class="icon icon-th"></i> </span>
                        <h5><spring:message code="index.label" arguments="${entity}"/></h5>
                    </div>
                    <div class="widget-content">
                        <table class="table display table-bordered data-table" id="claim-table">
                            <thead>
                            <tr>
                                <th><spring:message code="id.label"/></th>
                                <th><spring:message code="createdTime.label"/></th>
                                <th><spring:message code="remark.label"/></th>
                                <th><spring:message code="action.operation.label"/></th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                        <!-- buttons -->
                        <div class='form-actions col-sm-offset-4'>
                            <button class='tableButton col-sm-offset-2' id='close-button'>
                                <spring:message code='action.close.label'/>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#claim-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': true,
            'serverSide': true,
            //'paging': true,
            "jQueryUI": true,
            'dom': '<"">rt<"F"lp>',

            'ajax': {
                'url': "${rootURL}${controller}/listClaim",
                'type': "GET",
                'dataType': 'json',
                'data': {
                    paymentId: ${paymentId}
                }
            },
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {
                    'name': 'createdTime', 'targets': 1, 'searchable': false,
                    'data': 'createdTime'
                },
                {
                    'name': 'remark', 'targets': 2, 'searchable': false, 'orderable': false,
                    'data': 'remark'
                },
                {
                    'name': 'operation', 'targets': 3, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        if (row['hasAttachment']) {
                            return '<a href="' + "${rootURL}${controller}" + '/downloadClaim/'
                                    + row['id'] + '">' +
                                    '<button type="button" name="download-button" class="tableButton">'
                                    + '<spring:message code="action.download.label"/>'
                                    + '</button></a>';
                        } else {
                            return '';
                        }
                    }
                }
            ]
        });

    });
</script>
