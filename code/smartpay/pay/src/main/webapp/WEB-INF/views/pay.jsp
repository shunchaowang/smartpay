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
                                 class="img-circle"
                                 alt="Responsive image">
                        </div>
                    </div>
                </div>

                <div class="container">
                    <table class='table table-banner2 text-justify '>
                        <tr>
                            <td><h3> SSL ONLINE CREDIT CARD PAYMENT</h3></td>
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
                                            <label for='payMethod' class='col-sm-4 control-label'>
                                                *<spring:message code="payMethod.label"/>
                                            </label>

                                            <div class="col-sm-6 form-control">

                                                <form:radiobutton path="payMethod"
                                                                  id="payMethod"
                                                                  value="0"
                                                                  checked="checked"/>
                                                <spring:message code="creditCard.label"/>

                                                <form:radiobutton path="payMethod"
                                                                  id="payMethod2"
                                                                  value="1"/>
                                                <spring:message code="debitCard.label"/>

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
                                            <label for='cvv' class='col-sm-4 control-label'>
                                                *<spring:message code="cvv.label"/>
                                            </label>

                                            <div class='col-sm-8'>
                                                <form:input path="cvv" type="text"
                                                            name='cvv' id='cvv'
                                                            placeholder='Card CVV'
                                                            value=''/>
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
                                            <label for=expireYear class='col-sm-4 control-label'>
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
                                            <label for=issuingBank class='col-sm-4 control-label'>
                                                *<spring:message code="bankName.label"/>
                                            </label>

                                            <div class='col-sm-8'>
                                                <form:input path="bankName" type="text"
                                                            name='issuingBank'
                                                            id='issuingBank'
                                                            placeholder='VISA/MASTER/OTHERS'
                                                            value=''/>
                                            </div>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td>
                                        <div class='form-group'>
                                            <label for='billFirstName'
                                                   class='col-sm-4 control-label'>
                                                *<spring:message code="firstName.label"/>
                                            </label>

                                            <div class='col-sm-8'>
                                                <form:input path="billFirstName" type="hidden"
                                                            name='billFirstName' id='billFirstName'
                                                            value='${orderCommand.shipFirstName}'/>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class='form-group'>
                                            <label for='billLastName'
                                                   class='col-sm-4 control-label'>
                                                *<spring:message code="lastName.label"/>
                                            </label>

                                            <div class='col-sm-8'>
                                                <form:input path="billLastName" type="hidden"
                                                            name='billLastName' id='billLastName'
                                                            value='${orderCommand.shipLastName}'/>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class='form-group'>
                                            <label for='billAddress1'
                                                   class='col-sm-4 control-label'>
                                                *<spring:message code="address.label"/>
                                            </label>

                                            <div class='col-sm-8'>
                                                <form:input path="billAddress1" type="hidden"
                                                            name='billAddress1' id='billAddress1'
                                                            value='${orderCommand.shipAddress}'/>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class='form-group'>
                                            <label for='billCity' class='col-sm-4 control-label'>
                                                *<spring:message code="city.label"/>
                                            </label>

                                            <div class='col-sm-8'>
                                                <form:input path="billCity" type="hidden"
                                                            name='billCity' id='billCity'
                                                            value='${orderCommand.shipCity}'/>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class='form-group'>
                                            <label for='billState' class='col-sm-4 control-label'>
                                                *<spring:message code="state.label"/>
                                            </label>

                                            <div class='col-sm-8'>
                                                <form:input path="billState" type="hidden"
                                                            name='billState' id='billState'
                                                            value='${orderCommand.shipState}'/>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class='form-group'>
                                            <label for='billZipCode' class='col-sm-4 control-label'>
                                                *<spring:message code="zipCode.label"/>
                                            </label>

                                            <div class='col-sm-8'>
                                                <form:input path="billZipCode" type="hidden"
                                                            name='billZipCode' id='billZipCode'
                                                            value='${orderCommand.shipZipCode}'/>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class='form-group'>
                                            <label for='billCountry' class='col-sm-4 control-label'>
                                                *<spring:message code="country.label"/>
                                            </label>

                                            <div class='col-sm-8'>
                                                <form:input path="billCountry" type="hidden"
                                                            name='billCountry' id='billCountry'
                                                            value='${orderCommand.shipCountry}'/>
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

<jsp:include page="_footer.jsp"/>

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
                },
                bankName: {
                    required: true,
                    minlength: 3,
                    maxlength: 32
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
                    minlength: 'Month must be 4 characters',
                    maxlength: 'Month must be 4 characters'
                },
                bankName: {
                    required: 'Please input issuing bank',
                    minlength: 'Bank must be at least 3 characters',
                    maxlength: 'Bank must be at most 32 characters'
                }
            }
        });
    });
</script>

</body>