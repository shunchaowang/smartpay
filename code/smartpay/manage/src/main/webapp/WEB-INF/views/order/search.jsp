<%@include file="../taglib.jsp" %>
<c:if test="${domain != null}">
    <spring:message code="${domain}.label" var="entity"/>
</c:if>

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
<!-- start of search form -->
<div class="row">
    <form class="form-horizontal">
        <div class="row">
            <label class="col-sm-1 control-label" for="id">
                <spring:message code="id.label"/>
            </label>

            <div class="col-sm-2">
                <input type="text" class="form-control" name="id" id="id"/>
            </div>
            <label class="col-sm-1 control-label" for="merchantNumber">
                <spring:message code="merchantNumber.label"/>
            </label>

            <div class="col-sm-2">
                <input type="text" class="form-control" name="merchantNumber"
                       id="merchantNumber"/>
            </div>
            <label class="col-sm-1 control-label" for="orderStatus">
                <spring:message code="status.label"/>
            </label>

            <div class="col-sm-2">
                <select id="orderStatus" class="form-control">
                    <c:forEach items="${orderStatuses}" var="status">
                        <option value="${status.id}">${status.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <br>
        <!-- line of id, merchant number and status -->
        <div class="row">
            <label class="col-sm-1 control-label" for="site">
                <spring:message code="Site.label"/>
            </label>

            <div class="col-sm-2">
                <select id="site" class="form-control">
                    <c:forEach items="${sites}" var="site">
                        <form:option value="${site.id}">${site.name}</form:option>
                    </c:forEach>
                </select>
            </div>
            <label class="col-sm-1 control-label" for="begin-date">
                <spring:message code="date.begin.label"/>
            </label>

            <div class="col-sm-2">
                <input id="begin-date" name="begin-date"
                       class="form-control datepicker" readonly="true"
                       style="background:white;"/>
            </div>
            <label class="col-sm-1 control-label" for="end-date">
                <spring:message code="date.end.label"/>
            </label>

            <div class="col-sm-2">
                <input id="end-date" name="end-date"
                       class="form-control datepicker" readonly="true"
                       style="background:white;"/>
            </div>
        </div>
        <br>
        <!-- line of site, begin date and end date -->
        <div class="row">
            <div class='col-sm-offset-3 col-sm-10'>
                <button class='btn btn-default' id='search-button' type="submit">
                    <spring:message code='action.search.label'/>
                </button>
                <button class='btn btn-default col-sm-offset-3' id='reset-button' type="reset">
                    <spring:message code='action.reset.label'/>
                </button>
            </div>
        </div>
        <!-- line of submit and reset buttons -->
    </form>
</div>
<!-- end of search form -->
<br/>

<div class="row">
    <table class="display cell-border" id="order-table">
        <thead>
        <tr>
            <th><spring:message code="id.label"/></th>
            <th><spring:message code="merchantNumber.label"/></th>
            <th><spring:message code="amount.label"/></th>
            <th><spring:message code="currency.label"/></th>
            <th><spring:message code="Site.label"/></th>
            <th><spring:message code="Customer.label"/></th>
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

        $('.datepicker').datepicker();

        var orderTable = $('#order-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': false,
            'serverSide': false,
            'paging': false,
            'searching': false,
            'ordering': false
        });

        // add live handler for remove button
        orderTable.on('click', 'button[type=button][name=delete-button]', function (event) {
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
                    orderTable.ajax.reload();
                }
            });
        });

        $('#search-button').click(function (e) {
            e.preventDefault();
            orderTable.destroy();
            orderTable = $('#order-table').DataTable({
                'language': {
                    'url': "${dataTablesLanguage}"
                },
                'processing': true,
                'serverSide': true,
                'paging': true,

                'ajax': {
                    'url': "${rootURL}${controller}/searchData",
                    'type': "GET",
                    'data': {
                        'id': $("#id").val(),
                        'merchantNumber': $('#merchantNumber').val(),
                        'orderStatus': $('#orderStatus').val(),
                        'site': $('#site').val(),
                        'timeBeginning': $('#begin-date').val(),
                        'timeEnding': $('#end-date').val()
                    },
                    'dataType': 'json'
                },
                // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
                'columnDefs': [
                    {'name': 'id', 'targets': 0, 'data': 'id', 'searchable': false},
                    {
                        'name': 'merchantNumber', 'targets': 1, 'data': 'merchantNumber',
                        'render': function (data, type, row) {
                            return '<a href=' + "${rootURL}${controller}" + '/show/'
                                    + row['id'] + '>' + data + '</a>';
                        }
                    },
                    {
                        'name': 'amount', 'targets': 2, 'data': 'amount',
                        'searchable': false, 'orderable': false
                    },
                    {
                        'name': 'currency', 'targets': 3, 'data': 'currency',
                        'searchable': false, 'orderable': false
                    },
                    {
                        'name': 'site', 'targets': 4, 'data': 'site',
                        'searchable': false, 'orderable': false
                    },
                    {
                        'name': 'customer', 'targets': 5, 'data': 'customer',
                        'searchable': false, 'orderable': false
                    },
                    {
                        'name': 'createdTime',
                        'targets': 6,
                        'searchable': false,
                        'data': 'createdTime'
                    },
                    {
                        'name': 'orderStatus', 'targets': 7, 'searchable': false,
                        'orderable': false, 'data': 'orderStatus'
                    },
                    {
                        'name': 'operation', 'targets': 8, 'searchable': false, 'orderable': false,
                        'render': function (data, type, row) {
                            return '<a href="' + "${rootURL}${controller}" + '/edit/'
                                    + row['id'] + '">' +
                                    '<button type="button" name="edit-button" class="btn btn-default"'
                                    + '">' + '<spring:message code="action.edit.label"/>'
                                    + '</button></a>' +
                                    '<button type="button" name="delete-button"'
                                    + ' class="btn btn-default" value="' + row['id'] + '">' +
                                    '<spring:message code="action.delete.label"/>' +
                                    '</button>';
                        }
                    }
                ]
            });
        });
    });
</script>