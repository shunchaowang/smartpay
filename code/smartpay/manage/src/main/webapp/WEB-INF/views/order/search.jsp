<%@include file="../taglib.jsp" %>
<c:if test="${domain != null}">
    <spring:message code="${domain}.label" var="entity"/>
</c:if>

<!-- start of search form -->
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

    <div class="container-fluid">
        <div class="row-fluid">
            <div class="col-sm-12">
                <div class="widget-box">
                    <div class="widget-title">
								<span class="icon">
									<i class="icon icon-align-justify"></i>
								</span>
                        <h5><b><spring:message code='search.label' arguments="${entity}"/></b></h5>
                    </div>
                    <div class="widget-content nopadding">
                        <form class="form-horizontal">
                            <div class="row">
                                <div class="control-group">
                                    <label class="col-sm-1" for="id">
                                        <spring:message code="id.label"/>
                                    </label>

                                    <div class="col-sm-3">
                                        <input type="text" class="text" name="id" id="id"/>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="col-sm-1" for="merchantNumber">
                                        <spring:message code="merchantNumber.label"/>
                                    </label>

                                    <div class="col-sm-3">
                                        <input type="text" class="text" name="merchantNumber"
                                               id="merchantNumber"/>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="col-sm-1" for="orderStatus">
                                        <spring:message code="status.label"/>
                                    </label>

                                    <div class="col-sm-3">
                                        <select id="orderStatus" class="text">
                                            <c:forEach items="${orderStatuses}" var="status">
                                                <option value="${status.id}">${status.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <!-- line of id, merchant number and status -->
                                <div class="control-group">
                                    <label class="col-sm-1" for="site">
                                        <spring:message code="Site.label"/>
                                    </label>

                                    <div class="col-sm-3">
                                        <select id="site">
                                            <c:forEach items="${sites}" var="site">
                                                <option value="${site.id}">${site.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="col-sm-1" for="begin-date">
                                        <spring:message code="date.begin.label"/>
                                    </label>

                                    <div class="col-sm-3">
                                        <input id="begin-date" name="begin-date"
                                               class="text datepicker" readonly="true"
                                               style="background:white;"/>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="col-sm-1" for="end-date">
                                        <spring:message code="date.end.label"/>
                                    </label>

                                    <div class="col-sm-3">
                                        <input id="end-date" name="end-date"
                                               class="text datepicker" readonly="true"
                                               style="background:white;"/>
                                    </div>
                                </div>
                                <!-- line of site, begin date and end date -->
                            </div>
                            <div class='row'>
                                <div class="form-group">
                                    <div class="col-sm-2  col-sm-offset-2">
                                        <button class='btn btn-success' id='search-button'
                                                type="submit">
                                            <spring:message code='action.search.label'/>
                                        </button>
                                    </div>
                                    <div class="col-sm-2  col-sm-offset-2">
                                        <button class='btn btn-success col-sm-offset-3'
                                                id='reset-button'
                                                type="reset">
                                            <spring:message code='action.reset.label'/>
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <!-- line of submit and reset buttons -->
                        </form>
                    </div>
                    <!-- end of widget content -->
                </div>
                <!-- end of widget box -->
            </div>
            <!-- end of col-sm-12 -->
        </div>
        <!-- end of row-fluid -->


        <div class="row-fluid">
            <div class="col-sm-12">
                <div class="widget-box">
                    <div class="widget-title">
                        <span class="icon"><i class="icon icon-th"></i> </span>
                        <h5><spring:message code="index.label" arguments="${entity}"/></h5>
                    </div>
                    <div class="widget-content">
                        <table class="table display table-bordered data-table" id="order-table">
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
                </div>
            </div>
        </div>
    </div>
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
                'dom': 'T<""if>rt<"F"lp>',
                "tableTools": {
                    "sSwfPath": "${tableTools}",
                    "aButtons": [
                        {
                            "sExtends": "copy",
                            "mColumns": [1, 2, 3, 4, 5, 6, 7]
                        },
                        {
                            "sExtends": "xls",
                            "mColumns": [1, 2, 3, 4, 5, 6, 7]
                        }
                    ]
                },

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
                        'name': 'currency', 'targets': 3, 'data': 'currencyName',
                        'searchable': false, 'orderable': false
                    },
                    {
                        'name': 'site', 'targets': 4, 'data': 'siteName',
                        'searchable': false, 'orderable': false
                    },
                    {
                        'name': 'customer', 'targets': 5, 'data': 'customerName',
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
                        'orderable': false, 'data': 'orderStatusName'
                    },
                    {
                        'name': 'operation', 'targets': 8, 'searchable': false, 'orderable': false,
                        'render': function (data, type, row) {
                            return '<a href="' + "${rootURL}${controller}" + '/show/'
                                    + row['id'] + '">' +
                                    '<button type="button" name="edit-button"'
                                    + ' class="tableButton"' + '>'
                                    + '<spring:message code="action.show.label"/>'
                                    + '</button></a>';
                        }
                    }
                ]
            });
        });
    });
</script>