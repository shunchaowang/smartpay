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
            <a href="${rootURL}${controller}/index">
                <spring:message code="manage.label" arguments="${entity}"/>
            </a>
            <a href="${rootURL}${controller}/${action}" class="current">
                <spring:message code="${action}.label" arguments="${entity}"/>
            </a>
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
                                <div class="form-group">
                                    <label class="col-sm-1" for="id">
                                        <spring:message code="id.label"/>
                                    </label>

                                    <div class="col-sm-3">
                                        <input type="text" class="text" name="id" id="id"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1" for="bankTransactionNumber">
                                        <spring:message code="bankTransactionNumber.label"/>
                                    </label>

                                    <div class="col-sm-3">
                                        <input type="text" class="text" name="bankTransactionNumber"
                                               id="bankTransactionNumber"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1" for="paymentStatus">
                                        <spring:message code="status.label"/>
                                    </label>

                                    <div class="col-sm-3">
                                        <select id="paymentStatus" class="text">
                                            <c:forEach items="${paymentStatuses}" var="status">
                                                <option value="${status.id}">${status.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <!-- line of id, merchant number and status -->
                                <div class="form-group">
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
                                <div class="form-group">
                                    <label class="col-sm-1" for="begin-date">
                                        <spring:message code="date.begin.label"/>
                                    </label>

                                    <div class="col-sm-3">
                                        <input id="begin-date" name="begin-date"
                                               class="text datepicker" readonly="true"
                                               style="background:white;"/>
                                    </div>
                                </div>
                                <div class="form-group">
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
                                <th><spring:message code="orderNumber.label"/></th>
                                <th><spring:message code="bankTransactionNumber.label"/></th>
                                <th><spring:message code="bankName.label"/></th>
                                <th><spring:message code="amount.label"/></th>
                                <th><spring:message code="currency.label"/></th>
                                <th><spring:message code="Site.label"/></th>
                                <th><spring:message code="site.merchant.label"/></th>
                                <th><spring:message code="createdTime.label"/></th>
                                <th><spring:message code="returnCode.label"/></th>
                                <th><spring:message code="paymentStatusName.label"/></th>
                                <th><spring:message code="paymentTypeName.label"/></th>
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
                            "mColumns": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
                        },
                        {
                            "sExtends": "xls",
                            "mColumns": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
                        }
                    ]
                },
                'ajax': {
                    'url': "${rootURL}${controller}/searchData",
                    'type': "GET",
                    'data': {
                        'id': $("#id").val(),
                        'bankTransactionNumber': $('#bankTransactionNumber').val(),
                        'paymentStatus': $('#paymentStatus').val(),
                        'site': $('#site').val(),
                        'timeBeginning': $('#begin-date').val(),
                        'timeEnding': $('#end-date').val()
                    },
                    'dataType': 'json'
                },
                // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
                'columnDefs': [
                    {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                    {
                        'name': 'orderNumber', 'targets': 1, 'data': 'orderNumber'
                    },
                    {
                        'name': 'bankTransactionNumber',
                        'targets': 2,
                        'data': 'bankTransactionNumber'
                    },
                    {
                        'name': 'bankName',
                        'targets': 3,
                        'searchable': false,
                        'orderable': false,
                        'data': 'bankName'
                    },
                    {
                        'name': 'amount', 'targets': 4, 'data': 'amount'
                    },
                    {
                        'name': 'currencyName',
                        'targets': 5,
                        'searchable': false,
                        'orderable': false,
                        'data': 'currencyName'
                    },
                    {
                        'name': 'siteName', 'targets': 6, 'data': 'siteName',
                        'searchable': false, 'orderable': false
                    },
                    {'name': 'merchantName', 'targets': 7, 'data': 'merchantName'},

                    {
                        'name': 'createdTime', 'targets': 8, 'searchable': false,
                        'data': 'createdTime'
                    },
                    {
                        'name': 'bankReturnCode',
                        'targets': 9,
                        'searchable': false,
                        'orderable': false,
                        'data': 'bankReturnCode'
                    },
                    {
                        'name': 'paymentStatusName',
                        'targets': 10,
                        'searchable': false,
                        'orderable': false,
                        'data': 'paymentStatusName'
                    },
                    {
                        'name': 'paymentTypeName',
                        'targets': 11,
                        'searchable': false,
                        'orderable': false,
                        'data': 'paymentTypeName'
                    }
                ]
            });
        });
    })
    ;
</script>