<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="order.label" var="entity"/>

<div class="container-fluid">
    <div class="row">
        <ol class="breadcrumb">
            <li>
                <i class="glyphicon glyphicon-home"></i>
                <spring:message code="home.label"/>
            </li>
            <li>
                <i class="glyphicon glyphicon-list"></i>
                <spring:message code="manage.label" arguments="${entity}"/>
            </li>
            <li class="active">
                <i class="glyphicon glyphicon-briefcase"></i>
                <spring:message code="show.label" arguments="${entity}"/>
            </li>
        </ol>
    </div>
    <div class="row">
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
                            <td><spring:message code="site.label"/></td>
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
                            <td><spring:message code="customer.label"/></td>
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
    </div>
    <div class="row">
        <div class="col-sm-2 col-sm-offset-2">
            <a href="${rootURL}order/index/all">
                <button type="button" class="btn btn-default">
                    <spring:message code="action.return.label"/>
                </button>
            </a>
        </div>
    </div>
</div>