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
    <div class="tab-pane fade" id="card-tab">
        <div class="row">
            <p>Credit Card Form</p>
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
</body>