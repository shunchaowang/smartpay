<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="${domain}.label" var="entity"/>
<spring:message code="encryption.label" var="encryptionEntity"/>
<spring:message code="commission.fee.label" var="commissionFeeEntity"/>
<spring:message code="return.fee.label" var="returnFeeEntity"/>

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
<!-- end of notification -->
<div class="row">
    <div class="col-sm-6">
        <h3><b><spring:message code="index.label" arguments="${entity}"/></b></h3>
    </div>
    <!-- end of table title -->
</div>
<!-- end of class row -->
<br/>

<div class="row">
    <table class="display cell-border" id="transaction-table">
        <thead>
        <tr>
            <th><spring:message code="id.label"/></th>
            <th><spring:message code="id.label"/></th>
            <th><spring:message code="id.label"/></th>
            <th><spring:message code="id.label"/></th>
            <th><spring:message code="name.label"/></th>
            <th><spring:message code="key.label"/></th>
            <th><spring:message code="type.label"/></th>
            <th><spring:message code="commission.fee.label"/></th>
            <th><spring:message code="type.label"/></th>
            <th><spring:message code="return.fee.label"/></th>
            <th><spring:message code="type.label"/></th>
            <!--<th><spring:message code="action.operation.label"/></th>-->
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>

<!-- modal dialog to edit -->
<div id="modal-area">

</div>


<script type="text/javascript">
    $(document).ready(function () {
        var transactionTable = $('#transaction-table').DataTable({
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
                {
                    'name': 'encryptionId', 'targets': 1,
                    'visible': false, 'data': 'encryptionId'
                },
                {
                    'name': 'commissionFeeId', 'targets': 2,
                    'visible': false, 'data': 'commissionFeeId'
                },
                {
                    'name': 'returnFeeId', 'targets': 3,
                    'visible': false, 'data': 'returnFeeId'
                },
                {
                    'name': 'name', 'targets': 4, 'data': 'name',
                    'render': function (data, type, row) {
                        return '<a href=' + "${rootURL}admin/merchant" + '/show/'
                                + row['id'] + '>' + data + '</a>';
                    }
                },
                {
                    'name': 'encryptionKey', 'targets': 5, 'searchable': false, 'orderable': false,
                    'data': 'encryptionKey'
                },
                {
                    'name': 'encryptionType', 'targets': 6, 'searchable': false, 'orderable': false,
                    'data': 'encryptionType'
                },
                {
                    'name': 'commissionFeeValue', 'targets': 7, 'data': 'commissionFeeValue',
                    'searchable': false, 'orderable': false
                },
                {
                    'name': 'commissionFeeType', 'targets': 8, 'data': 'commissionFeeType',
                    'searchable': false, 'orderable': false
                },
                {
                    'name': 'returnFeeValue', 'targets': 9, 'data': 'returnFeeValue',
                    'searchable': false, 'orderable': false
                },
                {
                    'name': 'returnFeeType', 'targets': 10, 'data': 'returnFeeType',
                    'searchable': false, 'orderable': false
                }/*,
                 {
                 'name': 'operation', 'targets': 11, 'searchable': false, 'orderable': false,
                 'render': function (data, type, row) {
                 return '<button type="button" id="edit-encryption-button" '
                 + ' class="btn btn-default" value="' + row['encryptionId'] + '">'
                 + '<spring:message code="edit.label" arguments="${encryptionEntity}"/>'
                 + '</button>'
                 + '<button type="button" id="edit-commission-fee-button" '
                 + ' class="btn btn-default" value="' + row['commissionFeeId'] + '">'
                 + '<spring:message code="edit.label" arguments="${commissionFeeEntity}"/>'
                 + '</button>'
                 + '<button type="button" id="edit-return-fee-button" '
                 + ' class="btn btn-default" value="' + row['returnFeeId'] + '">'
                 + '<spring:message code="edit.label" arguments="${returnFeeEntity}"/>'
                 + '</button>';
                 }
                 }*/
            ]
        });

        // add edit button modal
        transactionTable.on('click', '#edit-encryption-button', function (event) {
            event.preventDefault();
            var id = this.value;

            $.ajax({
                type: 'GET',
                url: "${rootURL}${controller}/editEncryption",
                data: {
                    id: id
                },
                error: function (data) {
                    alert("There was an error.");
                },
                success: function (data) {
                    $('#modal-area').append(data);
                    $("#edit-encryption-modal").dialog({
                        modal: true,
                        height: 'auto',
                        width: 'auto',
                        resizable: true,
                        autoOpen: false,

                        // horizontal scroll bar fix, not working now
                        open: function (e, ui) {
                            //$('body').css('overflow', 'hidden');
                            //$('.ui-widget-overlay').css('width', '100%');
                        },
                        close: function (e, ui) {
                            // horizontal scroll bar fix, now working now
                            // $('body').css('overflow', 'auto');
                            // destroy the dialog when closed
                            // remove the dialog from parent on closing
                            $('#edit-encryption-modal').dialog('destroy').remove();
                        }
                    }).dialog('open');
                }
            });
        });
    });
</script>