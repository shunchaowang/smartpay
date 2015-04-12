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
        <div class="row-fluid">
            <div class="col-sm-12">
                <div class="widget-box">
                    <div class="widget-title">
                        <span class="icon"><i class="icon icon-th"></i> </span>
                        <h5><spring:message code="count.by.site"/></h5>
                        ${merchantCommand.orderAmount}
                    </div>
                    <div class="widget-content">
                        <table class="table display table-bordered data-table" id="amount-table">
                            <thead>
                            <tr>
                                <th>
                                    <spring:message code="Site.label"/>
                                    <spring:message code="id.label"/>
                                </th>
                                <th>
                                    <spring:message code="Site.label"/>
                                    <spring:message code="identity.label"/>
                                </th>
                                <th>
                                    <spring:message code="Site.label"/>
                                    <spring:message code="name.label"/>
                                </th>
                                <th>
                                    <spring:message code="count.label"/>
                                </th>
                                <th>
                                    <spring:message code="amount.label"/>
                                </th>
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
        $('#amount-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': true,
            'serverSide': true,
            'paging': true,
            "paginationType": "full_numbers",
            "jQueryUI": true,
            'dom': '<""if>rt<"F"lp>',


            'ajax': {
                'url': "${rootURL}${controller}/countBySite",
                'type': "GET",
                'dataType': 'json'
            },


            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'siteId'},
                {'name': 'identity', 'targets': 1, 'data': 'siteIdentity'},
                {'name': 'name', 'targets': 2, 'data': 'siteName'},
                {'name': 'count', 'targets': 3, 'data': 'orderCount'},
                {'name': 'amount', 'targets': 4, 'data': 'orderAmount'}
            ]
        });
    });
</script>