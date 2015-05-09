<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@include file="taglib.jsp" %>

<html>
<head>
    <jsp:include page="_import.jsp"/>
</head>
<body class="container">

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
<br/>

<!-- not supported right now
<div class="row">
    <ul class="nav nav-tabs">
        <li class="active">
            <a href="#card-tab" data-toggle="tab">
                <i class="glyphicon glyphicon-usd"></i>
                <spring:message code="payByCard.label"/>
            </a>
        </li>
        <li>
            <a href="#bitcoin-tab" data-toggle="tab">
                <i class="glyphicon glyphicon-briefcase"></i>
                <spring:message code="payByBitCoin.label"/>
            </a>
        </li>
    </ul>
</div>
-->
<!-- end of nav-tabs div -->
<br/>

<div id="content" class="tab-content">
    <div class="tab-pane fade active in" id="card-tab">
        <div class="row">

            <div class="row">
                <div class='container'>
                    <div class=row>
                        <div class='col-sm-4'><img src="${rootURL}assets/images/logo-nowipay.jpg">
                        </div>
                        <div class='col-sm-2'></div>
                        <div class='col-sm-6' align='right'><p>&nbsp;</p>
                            <img src="${rootURL}assets/images/payimages-secure.jpg"
                                 alt="Responsive image">
                        </div>
                    </div>
                </div>
                <div class="container">
                    <table class='table table-banner2 text-justify '>
                        <tr bgcolor="black">
                            <td><h3><span style="color:#bce8f1">SSL ONLINE CREDIT CARD PAYMENT</span></h3></td>
                        </tr>
                    </table>
                </div>

                <div class="container">


                    <div class="container-fluid table-bordered col-sm-4">
                        <h3>
                            <spring:message code="detailOfOrder.label"/>
                        </h3>

                        <table class="table table-striped">
                            <tr>
                                <td>
                                    <spring:message code="orderNumber.label"/>
                                    ${orderCommand.orderNo}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <spring:message code="orderSummary.label"/>
                                    <p></p>
                                    ${orderCommand.goodsName}
                                    ${orderCommand.goodsNumber}
                                    ${orderCommand.goodsPrice}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <spring:message code="totalAmount.label"/>
                                    ${orderCommand.currency}${orderCommand.amount}
                                </td>
                            </tr>
                        </table>
                    </div>

                    <div class="container-fluid table-bordered col-sm-8">
                        <h3><spring:message code="paymentInfo.label"/></h3>

                        <form:form action="${rootURL}payByCard" method="POST"
                                   commandName="paymentCommand"
                                   cssClass="form-horizontal" id="pay-form">
                            <table class="table table-hover">
                                <tr>
                                    <td>
                                        <div class='form-group'>
                                            <label for='cardNo' class='col-sm-4 control-label'>
                                                *<spring:message code="paymentOption.label"/>
                                            </label>

                                            <div class='col-sm-8'>
                                                <form:radiobutton path="bankName"
                                                                  id="bankName"
                                                                  value="VISA"
                                                                  checked="checked"/>
                                                <img src="${rootURL}assets/images/visa.png">

                                                <form:radiobutton path="bankName"
                                                                  id="bankName"
                                                                  value="MASTERCART"/>
                                                <img src="${rootURL}assets/images/mastercard.png">

                                                <form:radiobutton path="bankName"
                                                                  id="bankName  "
                                                                  value="JCB"/>
                                                <img src="${rootURL}assets/images/jcb.png">

                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class='form-group'>
                                            <label for='cardNo' class='col-sm-4 control-label'>
                                                *<spring:message code="cardNumber.label"/>
                                            </label>

                                            <div class='col-sm-8'>
                                                <form:input path="bankAccountNumber" type="text"
                                                            name='cardNo' id='cardNo'
                                                            placeholder='Card Number' value=''/>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class='form-group'>
                                            <label for='expireMonth' class='col-sm-4 control-label'>
                                                *<spring:message code="expireMonth.label"/>
                                            </label>

                                            <div class='col-sm-8'>
                                                <form:input path="expireMonth" type="text"
                                                            name='expireMonth'
                                                            id='expireMonth'
                                                            placeholder='Month' value=''/>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class='form-group'>
                                            <label for='expireYear' class='col-sm-4 control-label'>
                                                *<spring:message code="expireYear.label"/>
                                            </label>

                                            <div class='col-sm-8'>
                                                <form:input path="expireYear" type="text"
                                                            name='expireYear'
                                                            id='expireYear'
                                                            placeholder='Year' value=''/>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class='form-group'>
                                            <label for='cvv' class='col-sm-4 control-label'>
                                                *<spring:message code="cvv.label"/>
                                            </label>

                                            <div class='col-sm-8'>
                                                <form:input path="cvv" type="text"
                                                            name='cvv' id='cvv'
                                                            placeholder='Card CVV'
                                                            value=''/>
                                                <img src="${rootURL}assets/images/cvv2.jpg">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class='form-group'>
                                            <div class='col-sm-offset-3 col-sm-10'>
                                                <button class='btn btn-default' id='submit-button'
                                                        type="submit">
                                                    <spring:message code='action.submit.label'/>
                                                </button>
                                                <button class='btn btn-default' id='reset-button'
                                                        type="reset">
                                                    <spring:message code='action.reset.label'/>
                                                </button>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </table>

                            <form:input type="hidden" path="amount" value="${orderCommand.amount}"/>
                            <form:input type="hidden" path="currencyName"
                                        value="${orderCommand.currency}"/>
                            <form:input type="hidden" path="orderId"
                                        value="${orderCommand.orderId}"/>

                            <form:input type="hidden" path="payMethod"
                                        name='payMethod' id='payMethod'
                                        value='0'/>

                            <form:input type="hidden" path="billFirstName"
                                        name='billFirstName' id='billFirstName'
                                        value='${orderCommand.shipFirstName}'/>

                            <form:input path="billLastName" type="hidden"
                                        name='billLastName' id='billLastName'
                                        value='${orderCommand.shipLastName}'/>

                            <form:input path="billAddress1" type="hidden"
                                        name='billAddress1' id='billAddress1'
                                        value='${orderCommand.shipAddress}'/>

                            <form:input path="billCity" type="hidden"
                                        name='billCity' id='billCity'
                                        value='${orderCommand.shipCity}'/>

                            <form:input path="billState" type="hidden"
                                        name='billState' id='billState'
                                        value='${orderCommand.shipState}'/>

                            <form:input path="billZipCode" type="hidden"
                                        name='billZipCode' id='billZipCode'
                                        value='${orderCommand.shipZipCode}'/>

                            <form:input path="billCountry" type="hidden"
                                        name='billCountry' id='billCountry'
                                        value='${orderCommand.shipCountry}'/>

                            <!-- hidden params for order info -->
                            <input name="merNo" type="hidden" value="${orderCommand.merNo}"/>
                            <input name="referer" type="hidden" value="${orderCommand.referer}"/>
                            <input name="orderNo" type="hidden" value="${orderCommand.orderNo}"/>
                            <input name="returnURL" type="hidden"
                                   value="${orderCommand.returnUrl}"/>
                            <input name="amount" type="hidden" value="${orderCommand.amount}"/>
                            <input name="currency" type="hidden" value="${orderCommand.currency}"/>
                            <input name="productType" type="hidden" value="${orderCommand.productType}"/>
                            <input name="goodsName" type="hidden" value="${orderCommand.goodsName}"/>
                            <input name="goodsNumber" type="hidden" value="${orderCommand.goodsNumber}"/>
                            <input name="goodsPrice" type="hidden" value="${orderCommand.goodsPrice}"/>
                            <input name="email" type="hidden" value="${orderCommand.email}"/>
                            <input name="phone" type="hidden" value="${orderCommand.phone}"/>
                            <input name="shipFirstName" type="hidden" value="${orderCommand.shipFirstName}"/>
                            <input name="shipLastName" type="hidden" value="${orderCommand.shipLastName}"/>
                            <input name="shipAddress" type="hidden" value="${orderCommand.shipAddress}"/>
                            <input name="shipCity" type="hidden" value="${orderCommand.shipCity}"/>
                            <input name="shipState" type="hidden" value="${orderCommand.shipState}"/>
                            <input name="shipCountry" type="hidden" value="${orderCommand.shipCountry}"/>
                            <input name="shipZipCode" type="hidden" value="${orderCommand.shipZipCode}"/>
                        </form:form>

                    </div>
                </div>
            </div>
        </div>
        <!-- end of class row -->
        <br/>
    </div>
    <!-- end of bundleTab -->
    <!-- not supported right now
    <div class="tab-pane fade" id="bitcoin-tab">
        <p>Bitcoin Link</p>
    </div>
    -->
    <!-- end of connectionTab -->
</div>
<!-- end of homeContent -->


<script type="text/javascript">
    $(document).ready(function () {
        //activeTab('card-tab');

        $('#pay-form').validate({
            rules: {
                payMethod: 'required',
                bankAccountNumber: {
                    required: true,
                    minlength: 15,
                    maxlength: 20
                },
                cvv: {
                    required: true,
                    minlength: 3,
                    maxlength: 3
                },
                expireMonth: {
                    required: true,
                    minlength: 2,
                    maxlength: 2
                },
                expireYear: {

                    required: true,
                    minlength: 4,
                    maxlength: 4
                }
            },
            messages: {
                payMethod: 'Please select pay method',
                bankAccountNumber: {
                    required: 'Please input card number',
                    minlength: 'Card number must be at least 15 characters',
                    maxlength: 'Card number must be at most 20 characters'
                },
                cvv: {
                    required: 'Please input CVV',
                    minlength: 'CVV must be 3 characters',
                    maxlength: 'CVV must be 3 characters'
                },
                expireMonth: {
                    required: 'Please input expiration month',
                    minlength: 'Month must be 2 characters',
                    maxlength: 'Month must be 2 characters'
                },
                expireYear: {
                    required: 'Please input expiration year',
                    minlength: 'Year must be 4 characters',
                    maxlength: 'Year must be 4 characters'
                }
            }
        });
    });
</script>

</body>