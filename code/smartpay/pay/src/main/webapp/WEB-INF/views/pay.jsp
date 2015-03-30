<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@include file="taglib.jsp" %>

<html>
<head>
    <jsp:include page="_import.jsp"/>
</head>
<body class="container">
<nav class="navbar navbar-default" role="navigation" id="nav-header">
    <jsp:include page="_navbar.jsp"/>
</nav>
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
                <spring:message code="payByBitcoin.label"/>
            </a>
        </li>
    </ul>
</div>
<!-- end of nav-tabs div -->
<br/>

<div id="content" class="tab-content">
    <div class="tab-pane fade active in" id="card-tab">
        <div class="row">
            <p>Credit Card Form</p>

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
                        <h3>Details Of Your Order</h3>

                        <table class="table table-striped">
                            <tr>
                                <td>Merchant's website: ${orderCommand.referer}</td>
                            </tr>
                            <tr>
                                <td>Order Number: ${orderCommand.orderNo}</td>
                            </tr>
                            <tr>
                                <td>Your order summary:<p></p>
                                    ${orderCommand.goodsName}
                                    ${orderCommand.goodsNumber}
                                    ${orderCommand.goodsPrice}
                                </td>
                            </tr>
                            <tr>
                                <td>Total Payment Amount:
                                    ${orderCommand.currency}${orderCommand.amount}
                                </td>
                            </tr>
                        </table>
                    </div>

                    <div class="container-fluid table-bordered col-sm-8">
                        <h3>Payment Information</h3>

                        <form name='pay' action='/payByCard' method='POST' class='form-horizontal'
                              id='pay-form'>
                            <table class="table table-hover">
                                <tr>
                                    <td>
                                        <div class='form-group'>
                                            <label for='payMethod' class='col-sm-4 control-label'>*
                                                Pay
                                                Method </label>

                                            <div class="radio">
                                                <label>
                                                    <input type="radio" name="payMethod"
                                                           id="payMethod"
                                                           value="0" checked>
                                                    Credit Card </label>

                                                <label>
                                                    <input type="radio" name="payMethod"
                                                           id="payMethod2"
                                                           value="1">
                                                    Debit Card </label>
                                            </div>


                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class='form-group'>
                                            <label for='cardNo' class='col-sm-4 control-label'>*
                                                Card
                                                Number</label>

                                            <div class='col-sm-8'>
                                                <input type="text" name='cardNo' id='cardNo'
                                                       placeholder='Card Number' value=''>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class='form-group'>
                                            <label for='cvv' class='col-sm-4 control-label'>*
                                                CVV/CVV2</label>

                                            <div class='col-sm-8'>
                                                <input type="text" name='cvv' id='cvv'
                                                       placeholder='Card CVV'
                                                       value=''>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class='form-group'>
                                            <label for='expireMonth' class='col-sm-4 control-label'>*
                                                Expire
                                                Month </label>

                                            <div class='col-sm-8'>
                                                <input type="text" name='expireMonth'
                                                       id='expirationMonth'
                                                       placeholder='Month' value=''>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class='form-group'>
                                            <label for=expireYear class='col-sm-4 control-label'>*
                                                Expiration
                                                Year </label>

                                            <div class='col-sm-8'>
                                                <input type="text" name='expireYear' id='expireYear'
                                                       placeholder='Year' value=''>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class='form-group'>
                                            <label for=issuingBank class='col-sm-4 control-label'>*
                                                Issuing
                                                Bank</label>

                                            <div class='col-sm-8'>
                                                <input type="text" name='issuingBank'
                                                       id='issuingBank'
                                                       placeholder='VISA/MASTER/OTHERS' value=''>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class='row' align="center">
                                            <input type='submit' value='Submit'/>
                                            <input type='reset' value='Reset'/>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- end of class row -->
        <br/>
    </div>
    <!-- end of bundleTab -->
    <div class="tab-pane fade" id="bitcoin-tab">
        <p>Bitcoin Link</p>
    </div>
    <!-- end of connectionTab -->
</div>
<!-- end of homeContent -->

<jsp:include page="_footer.jsp"/>

<script type="text/javascript">
    $(document).ready(function () {
        activeTab('card-tab');
    });
</script>

</body>