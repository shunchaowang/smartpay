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

<div class='container'>
    <form action="${rootURL}pay" method="POST" class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-3 control-label" for="merNo">
                <span class="required-indicator">*</span>Merchant No:
            </label>

            <div class="col-sm-6">
                <input name="merNo" id="merNo" class="form-control"
                       value="${orderCommand.merNo}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label" for="referer">
                <span class="required-indicator">*</span>Referer:
            </label>

            <div class="col-sm-6">
                <input name="referer" id="referer" class="form-control"
                       value="${orderCommand.referer}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label" for="orderNo">
                <span class="required-indicator">*</span>order No:
            </label>

            <div class="col-sm-6">
                <input name="orderNo" id="orderNo" class="form-control"
                       value="${orderCommand.orderNo}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label" for="returnURL">
                <span class="required-indicator">*</span>return URL:
            </label>

            <div class="col-sm-6">
                <input name="returnURL" id="returnURL" class="form-control"
                       value="${orderCommand.returnUrl}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label" for="amount">
                <span class="required-indicator">*</span>Amount:
            </label>

            <div class="col-sm-6">
                <input name="amount" id="amount" class="form-control"
                       value="${orderCommand.amount}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label" for="currency">
                <span class="required-indicator">*</span>Currency:
            </label>

            <div class="col-sm-6">
                <input name="currency" id="currency" class="form-control"
                       value="${orderCommand.currency}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label" for="productType">
                <span class="required-indicator">*</span>productType:
            </label>

            <div class="col-sm-6">
                <input name="productType" id="productType" class="form-control"
                       value="${orderCommand.productType}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label" for="goodsName">
                <span class="required-indicator">*</span>goodsName:
            </label>

            <div class="col-sm-6">
                <input name="goodsName" id="goodsName" class="form-control"
                       value="${orderCommand.goodsName}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label" for="goodsNumber">
                <span class="required-indicator">*</span>goodsNumber:
            </label>

            <div class="col-sm-6">
                <input name="goodsNumber" id="goodsNumber" class="form-control"
                       value="${orderCommand.goodsNumber}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label" for="goodsPrice">
                <span class="required-indicator">*</span>goodsPrice:
            </label>

            <div class="col-sm-6">
                <input name="goodsPrice" id="goodsPrice" class="form-control"
                       value="${orderCommand.goodsPrice}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label" for="email">
                <span class="required-indicator">*</span>email:
            </label>

            <div class="col-sm-6">
                <input name="email" id="email" class="form-control"
                       value="${orderCommand.email}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label" for="phone">
                <span class="required-indicator">*</span>phone:
            </label>

            <div class="col-sm-6">
                <input name="phone" id="phone" class="form-control"
                       value="${orderCommand.phone}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label" for="shipFirstName">
                <span class="required-indicator">*</span>shipFirstName:
            </label>

            <div class="col-sm-6">
                <input name="shipFirstName" id="shipFirstName" class="form-control"
                       value="${orderCommand.shipFirstName}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label" for="shipLastName">
                <span class="required-indicator">*</span>shipLastName:
            </label>

            <div class="col-sm-6">
                <input name="shipLastName" id="shipLastName" class="form-control"
                       value="${orderCommand.shipLastName}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label" for="shipAddress">
                <span class="required-indicator">*</span>shipAddress:
            </label>

            <div class="col-sm-6">
                <input name="shipAddress" id="shipAddress" class="form-control"
                       value="${orderCommand.shipAddress}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label" for="shipCity">
                <span class="required-indicator">*</span>shipCity:
            </label>

            <div class="col-sm-6">
                <input name="shipCity" id="shipCity" class="form-control"
                       value="${orderCommand.shipCity}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label" for="shipState">
                <span class="required-indicator">*</span>shipState:
            </label>

            <div class="col-sm-6">
                <input name="shipState" id="shipState" class="form-control"
                       value="${orderCommand.shipState}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label" for="shipCountry">
                <span class="required-indicator">*</span>shipCountry:
            </label>

            <div class="col-sm-6">
                <input name="shipCountry" id="shipCountry" class="form-control"
                       value="${orderCommand.shipCountry}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label" for="shipZipCode">
                <span class="required-indicator">*</span>shipZipCode:
            </label>

            <div class="col-sm-6">
                <input name="shipZipCode" id="shipZipCode" class="form-control"
                       value="${orderCommand.shipZipCode}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label" for="merMd5info">
                <span class="required-indicator">*</span>md5Info:
            </label>

            <div class="col-sm-6">
                <input name="merMd5info" id="merMd5info" class="form-control"
                       value="${md5Info}"/>
            </div>
        </div>

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
    </form>
</div>

<!-- end of homeContent -->

<jsp:include page="_footer.jsp"/>

<script type="text/javascript">

</script>

</body>