<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@include file="taglib.jsp" %>
<html>
<head>
    <jsp:include page="_import.jsp"/>
    <link rel="stylesheet" href="${rootURL}assets/css/login.css"/>
</head>
<body>

<div id="logo">
    <img src="${rootURL}assets/images/login-logo.png" alt=""/>
</div>

<div id="loginbox">
    <form id="loginform" class="form-vertical" action="${rootURL}login" method="post">
        <div class="control-group normal_text">
            <h3>
                <spring:message code="login.title"/>
            </h3>
        </div>
        <div class="control-group">
            <div class="controls">
                <div class="main_input_box">
                    <span class="add-on"><i class="glyphicon glyphicon-user"></i></span>
                    <input type="text" name="username" placeholder="Username"/>
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <div class="main_input_box">
                    <span class="add-on"><i class="glyphicon glyphicon-lock"></i></span>
                    <input type="password" name="password" placeholder="Password"/>
                </div>
            </div>
        </div>

        <div class="form-actions">
            <span class="pull-right"><input type="submit" class="btn btn-success"
                                            value="Login"/></span>
        </div>

    </form>
</div>

<div class="col-sm-12">
    <c:if test="${param.error != null}">
        <div class="alert alert-danger">
            Invalid UserName and Password.
        </div>
    </c:if>
    <c:if test="${param.logout != null}">
        <div class="alert alert-success">
            You have been logged out.
        </div>
    </c:if>
</div>

<script src="${rootURL}assets/js/login.js"></script>


</body>
</html>