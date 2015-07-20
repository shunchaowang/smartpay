<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="payment.label" var="entity"/>

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
        <div class="col-sm-8">
            <table class="table table-bordered">
                <tr>
                    <td colspan="2" align="left">&nbsp;&nbsp;&nbsp;<spring:message
                            code="order.details.label"/>:</td>
                </tr>
                <c:if test="${not empty orderCommand.merchantName}">
                    <tr>
                        <td><spring:message code="merchant.label"/></td>
                        <td>${orderCommand.merchantName}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty orderCommand.merchantNumber}">
                    <tr>
                        <td><spring:message code="merchantNumber.label"/></td>
                        <td>${orderCommand.merchantNumber}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty orderCommand.siteUrl}">
                    <tr>
                        <td><spring:message code="site.label"/></td>
                        <td>${orderCommand.siteUrl}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty orderCommand.amount}">
                    <tr>
                        <td><spring:message code="amount.label"/></td>
                        <td>${orderCommand.amount}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty orderCommand.currencyName}">
                    <tr>
                        <td><spring:message code="currency.label"/></td>
                        <td>${orderCommand.currencyName}</td>
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
            </table>
            <div class="row">
                <div class="col-sm-4">
                    <table class="table table-bordered">
                        <tr>
                            <td colspan="2" align="left">&nbsp;&nbsp;&nbsp;<spring:message
                                    code="payment.details.label"/>:</td>
                        </tr>
                        <c:if test="${not empty paymentCommand.bankName}">
                            <tr>
                                <td><spring:message
                                        code="bankName.label"/></td>
                                <td>${paymentCommand.bankName}</td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty paymentCommand.bankTransactionNumber}">
                            <tr>
                                <td><spring:message
                                        code="bankTransactionNumber.label"/></td>
                                <td>${paymentCommand.bankTransactionNumber}</td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty paymentCommand.bankReturnCode}">
                            <tr>
                                <td><spring:message code="returnCode.label"/></td>
                                <td>${paymentCommand.bankReturnCode}</td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty paymentCommand.paymentStatusName}">
                            <tr>
                                <td><spring:message
                                        code="paymentStatusName.label"/></td>
                                <td>${paymentCommand.paymentStatusName}</td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty paymentCommand.paymentTypeName}">
                            <tr>
                                <td><spring:message code="paymentTypeName.label"/></td>
                                <td>${paymentCommand.paymentTypeName}</td>
                            </tr>
                        </c:if>

                        <c:if test="${not empty paymentCommand.createdTime}">
                            <tr>
                                <td><spring:message code="createdTime.label"/></td>
                                <td>${paymentCommand.createdTime}</td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty paymentCommand.billFirstName}">
                            <tr>
                                <td><spring:message code="custom.label"/></td>
                                <td>${paymentCommand.billFirstName} ${paymentCommand.billLastName}</td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty paymentCommand.billAddress1}">
                            <tr>
                                <td><spring:message code="address.label"/>1</td>
                                <td>${paymentCommand.billAddress1}</td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty paymentCommand.billAddress2}">
                            <tr>
                                <td><spring:message code="address.label"/>2</td>
                                <td>${paymentCommand.billAddress2}</td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty paymentCommand.billCity}">
                            <tr>
                                <td><spring:message code="city.label"/></td>
                                <td>${paymentCommand.billCity}</td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty paymentCommand.billState}">
                            <tr>
                                <td><spring:message code="state.label"/></td>
                                <td>${paymentCommand.billState}</td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty paymentCommand.billCountry}">
                            <tr>
                                <td><spring:message code="country.label"/></td>
                                <td>${paymentCommand.billCountry}</td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty paymentCommand.billZipCode}">
                            <tr>
                                <td><spring:message code="zipCode.label"/></td>
                                <td>${paymentCommand.billZipCode}</td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty paymentCommand.remark}">
                            <tr>
                                <td><spring:message code="remark.label"/></td>
                                <td>${paymentCommand.remark}</td>
                            </tr>
                        </c:if>
                    </table>
                </div>
                <div class="col-sm-4">
                    <table class="table table-bordered">
                        <tr>
                            <td colspan="2" align="left">&nbsp;&nbsp;&nbsp;<spring:message
                                    code="shipment.details.label"/>:</td>
                        </tr>
                        <tr>
                            <td><spring:message
                                    code="status.label"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty shipmentCommand.shipmentStatusName}">
                                        ${shipmentCommand.shipmentStatusName}
                                    </c:when>
                                    <c:otherwise>
                                        <spring:message code="noinfo.label"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td><spring:message
                                    code="carrier.label"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty shipmentCommand.carrier}">
                                        ${shipmentCommand.carrier}
                                    </c:when>
                                    <c:otherwise>
                                        <spring:message code="noinfo.label"/>
                                    </c:otherwise>
                                </c:choose></td>
                        </tr>
                        <tr>
                            <td><spring:message
                                    code="trackingNumber.label"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty
                                                shipmentCommand.trackingNumber}">
                                        ${shipmentCommand.trackingNumber}
                                    </c:when>
                                    <c:otherwise>
                                        <spring:message code="noinfo.label"/>
                                    </c:otherwise>
                                </c:choose></td>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-sm-8">
            <table class="table table-bordered">
                <tr>
                    <td colspan="2" align="left">&nbsp;&nbsp;&nbsp;<spring:message
                            code="payment.details.label"/>:</td>
                </tr>
                <c:if test="${not empty paymentCommand.bankName}">
                    <tr>
                        <td><spring:message
                                code="bankName.label"/></td>
                        <td>${paymentCommand.bankName}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty paymentCommand.bankTransactionNumber}">
                    <tr>
                        <td><spring:message
                                code="bankTransactionNumber.label"/></td>
                        <td>${paymentCommand.bankTransactionNumber}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty paymentCommand.bankReturnCode}">
                    <tr>
                        <td><spring:message code="returnCode.label"/></td>
                        <td>${paymentCommand.bankReturnCode}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty paymentCommand.paymentStatusName}">
                    <tr>
                        <td><spring:message
                                code="paymentStatusName.label"/></td>
                        <td>${paymentCommand.paymentStatusName}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty paymentCommand.paymentTypeName}">
                    <tr>
                        <td><spring:message code="paymentTypeName.label"/></td>
                        <td>${paymentCommand.paymentTypeName}</td>
                    </tr>
                </c:if>

                <c:if test="${not empty paymentCommand.createdTime}">
                    <tr>
                        <td><spring:message code="createdTime.label"/></td>
                        <td>${paymentCommand.createdTime}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty paymentCommand.billFirstName}">
                    <tr>
                        <td><spring:message code="custom.label"/></td>
                        <td>${paymentCommand.billFirstName} ${paymentCommand.billLastName}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty paymentCommand.billAddress1}">
                    <tr>
                        <td><spring:message code="address.label"/>1</td>
                        <td>${paymentCommand.billAddress1}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty paymentCommand.billAddress2}">
                    <tr>
                        <td><spring:message code="address.label"/>2</td>
                        <td>${paymentCommand.billAddress2}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty paymentCommand.billCity}">
                    <tr>
                        <td><spring:message code="city.label"/></td>
                        <td>${paymentCommand.billCity}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty paymentCommand.billState}">
                    <tr>
                        <td><spring:message code="state.label"/></td>
                        <td>${paymentCommand.billState}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty paymentCommand.billCountry}">
                    <tr>
                        <td><spring:message code="country.label"/></td>
                        <td>${paymentCommand.billCountry}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty paymentCommand.billZipCode}">
                    <tr>
                        <td><spring:message code="zipCode.label"/></td>
                        <td>${paymentCommand.billZipCode}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty paymentCommand.remark}">
                    <tr>
                        <td><spring:message code="remark.label"/></td>
                        <td>${paymentCommand.remark}</td>
                    </tr>
                </c:if>
            </table>
        </div>
        <div class="col-sm-8">
            <table class="table table-bordered">
                <tr>
                    <td colspan="2" align="left">&nbsp;&nbsp;&nbsp;<spring:message
                            code="shipment.details.label"/>:</td>
                </tr>
                <tr>
                    <td><spring:message
                            code="status.label"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty shipmentCommand.shipmentStatusName}">
                                ${shipmentCommand.shipmentStatusName}
                            </c:when>
                            <c:otherwise>
                                <spring:message code="noinfo.label"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td><spring:message
                            code="carrier.label"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty shipmentCommand.carrier}">
                                ${shipmentCommand.carrier}
                            </c:when>
                            <c:otherwise>
                                <spring:message code="noinfo.label"/>
                            </c:otherwise>
                        </c:choose></td>
                </tr>
                <tr>
                    <td><spring:message
                            code="trackingNumber.label"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty
                                                shipmentCommand.trackingNumber}">
                                ${shipmentCommand.trackingNumber}
                            </c:when>
                            <c:otherwise>
                                <spring:message code="noinfo.label"/>
                            </c:otherwise>
                        </c:choose></td>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-2 col-sm-offset-2">
            <a href="${rootURL}/payment/index">
                <button type="button" class="btn btn-default">
                    <spring:message code="action.return.label"/>
                </button>
            </a>
        </div>
    </div>
</div>

