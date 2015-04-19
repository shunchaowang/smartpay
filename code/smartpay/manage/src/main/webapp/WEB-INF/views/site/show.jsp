<!DOCTYPE html>
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
            <a href="${rootURL}${controller}/indexAll">
                <spring:message code="manage.label" arguments="${entity}"/>
            </a>
            <a href="${rootURL}${controller}/${action}" class="current">
                <spring:message code="${action}.label" arguments="${entity}"/>
            </a>
        </div>
    </div>

    <!-- close of content-header -->
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="col-sm-12">
                <div class="widget-box">
                    <div class="widget-title">
                        <span class="icon"><i class="icon icon-th"></i> </span>
                        <h5><spring:message code="index.label" arguments="${entity}"/></h5>
                    </div>
                    <div class="widget-content">
                        <div class="row">
                            <div class="col-sm-8">

                                <table class="table table-bordered">
                                    <tr>
                                        <td colspan="2"><spring:message
                                                code="basic.info.label"/></td>
                                    </tr>
                                    <c:if test="${not empty siteCommand.merchantName}">
                                        <tr>
                                            <td><spring:message code="site.merchant.label"/></td>
                                            <td>${siteCommand.merchantName}</td>
                                        </tr>
                                    </c:if>
                                    <c:if test="${not empty siteCommand.identity}">
                                        <tr>
                                            <td><spring:message code="identity.label"/></td>
                                            <td>${siteCommand.identity}</td>
                                        </tr>
                                    </c:if>
                                    <c:if test="${not empty siteCommand.name}">
                                        <tr>
                                            <td><spring:message code="name.label"/></td>
                                            <td>${siteCommand.name}</td>
                                        </tr>
                                    </c:if>
                                    <c:if test="${not empty siteCommand.url}">
                                        <tr>
                                            <td><spring:message code="site.url.label"/></td>
                                            <td>${siteCommand.url}</td>
                                        </tr>
                                    </c:if>
                                    <c:if test="${not empty siteCommand.siteStatusName}">
                                        <tr>
                                            <td><spring:message code="status.label"/></td>
                                            <td>${siteCommand.siteStatusName}</td>
                                        </tr>
                                    </c:if>
                                    <c:if test="${not empty siteCommand.remark}">
                                        <tr>
                                            <td><spring:message code="remark.label"/></td>
                                            <td>${siteCommand.remark}</td>
                                        </tr>
                                    </c:if>
                                </table>
                            </div>
                        </div>

                        <!-- button area -->

                        <div class="row">
                            <div class="col-sm-2 col-sm-offset-2">
                                <a href="${rootURL}${controller}/indexAll">
                                    <button type="button" class="btn btn-default">
                                        <spring:message code="action.return.label"/>
                                    </button>
                                </a>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
