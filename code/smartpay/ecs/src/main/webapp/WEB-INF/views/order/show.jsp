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
            <a href="${rootURL}${controller}/index">
                <spring:message code="index.label" arguments="${entity}"/>
            </a>
            <a href="${rootURL}${controller}/${action}" class="current">
                <spring:message code="${action}.label" arguments="${entity}"/>
            </a>
        </div>
    </div>
    <!-- reserved for notification -->
    <!-- close of content-header -->
    <div class="container-fluid">
        <!— actual content —>
        <div class='row'>
            <div class='col-sm-4'>
                <h2><b>
                    <spring:message code='show.label' arguments="${entity}"/>
                </b></h2>
            </div>
        </div>
        <br>

        <div class="row">
            <div class="col-sm-8">

                <table class="table table-bordered">
                    <c:if test="${not empty orderCommand.merchantNumber}">
                        <tr>
                            <td><spring:message code="merchantNumber.label"/></td>
                            <td>${orderCommand.merchantNumber}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty orderCommand.amount}">
                        <tr>
                            <td><spring:message code="amount.label"/></td>
                            <td>${orderCommand.amount}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty orderCommand.goodsName}">
                        <tr>
                            <td><spring:message code="goodsName.label"/></td>
                            <td>${orderCommand.goodsName}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty orderCommand.goodsAmount}">
                        <tr>
                            <td><spring:message code="goodsAmount.label"/></td>
                            <td>${orderCommand.goodsAmount}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty orderCommand.currencyName}">
                        <tr>
                            <td><spring:message code="currency.label"/></td>
                            <td>${orderCommand.currencyName}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty orderCommand.remark}">
                        <tr>
                            <td><spring:message code="remark.label"/></td>
                            <td>${orderCommand.remark}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty orderCommand.siteName}">
                        <tr>
                            <td><spring:message code="Site.label"/></td>
                            <td>${orderCommand.siteName}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty orderCommand.createdTime}">
                        <tr>
                            <td><spring:message code="createdTime.label"/></td>
                            <td>${orderCommand.createdTime}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty orderCommand.customerName}">
                        <tr>
                            <td><spring:message code="Customer.label"/></td>
                            <td>${orderCommand.customerName}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty orderCommand.orderStatusName}">
                        <tr>
                            <td><spring:message code="status.label"/></td>
                            <td>${orderCommand.orderStatusName}</td>
                        </tr>
                    </c:if>
                </table>
            </div>
        </div>
        <!-- button area -->

        <div class="row">
            <div class="col-sm-2 col-sm-offset-2">
                <a href="${rootURL}${controller}/index">
                    <button type="button" class="btn btn-default">
                        <spring:message code="action.return.label"/>
                    </button>
                </a>
            </div>
        </div>

    </div>
</div>
