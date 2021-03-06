<%@include file="../taglib.jsp" %>
<spring:message var="cancelAction" code="action.cancel.label"/>
<spring:message var="entity" code="refuse.label"/>
<spring:message code="claim.label" var="claimLabel" arguments="${entity}"/>

<div id="claim-dialog" title="${claimLabel}">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-12">
                <table class="table table-bordered" id="claim-table">
                    <thead>
                        <tr>
                            <th><spring:message code="id.label"/></th>
                            <th><spring:message code="createdTime.label"/></th>
                            <th><spring:message code="remark.label"/></th>
                            <th><spring:message code="action.operation.label"/></th>
                        </tr>
                    </thead>
                    <body></body>
                </table>
            </div>
        </div>
        <br>
        <div class="row">
            <!-- buttons -->
            <div class='form-group'>
                <div class="col-sm-6 col-sm-offset-6">
                    <button class='btn btn-default' id='close-button'>
                        <spring:message code='action.close.label'/>
                    </button>
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
            'dom': '<"">rt',
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
                {'name': 'createdTime', 'targets': 1, 'searchable': false, 'data': 'createdTime'},
                {'name': 'remark', 'targets': 2, 'searchable': false, 'orderable': false, 'data': 'remark'},
                {'name': 'operation', 'targets': 3, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        if (row['hasAttachment']) {
                            return '<a href="' + "${rootURL}${controller}" + '/downloadClaim/'
                                    + row['id'] + '">' +
                                    '<button type="button" name="download-button" class="text">'
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
