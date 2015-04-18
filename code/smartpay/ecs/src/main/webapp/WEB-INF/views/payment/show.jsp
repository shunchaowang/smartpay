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
                    <c:if test="${not empty paymentCommand.merchantNumber}">
                        <tr>
                            <td><spring:message code="merchantNumber.label"/></td>
                            <td>${paymentCommand.merchantNumber}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty paymentCommand.siteName}">
                        <tr>
                            <td><spring:message code="Site.label"/></td>
                            <td>${paymentCommand.siteName}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty paymentCommand.orderNumber}">
                        <tr>
                            <td><spring:message code="orderNumber.label"/></td>
                            <td>${paymentCommand.orderNumber}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty paymentCommand.bankTransactionNumber}">
                        <tr>
                            <td><spring:message code="bankTransactionNumber.label"/></td>
                            <td>${paymentCommand.bankTransactionNumber}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty paymentCommand.amount}">
                        <tr>
                            <td><spring:message code="amount.label"/></td>
                            <td>${paymentCommand.amount}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty paymentCommand.currencyName}">
                        <tr>
                            <td><spring:message code="currency.label"/></td>
                            <td>${paymentCommand.currencyName}</td>
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
                            <td><spring:message code="paymentStatusName.label"/></td>
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
                            <td><spring:message code="paymentCreatedTime.label"/></td>
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
                            <td><spring:message code="address.label"/></td>
                            <td>${paymentCommand.billAddress1}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty paymentCommand.billAddress2}">
                        <tr>
                            <td><spring:message code="address.label"/></td>
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
                            <td><spring:message code="zipcode.label"/></td>
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
