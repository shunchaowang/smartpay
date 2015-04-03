<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <c:url var="marutiUrl" value="${rootURL}assets/vendor/maruti/"/>
    <title>
        <spring:message code="system.title"/>
    </title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="${marutiUrl}css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${marutiUrl}css/bootstrap-responsive.min.css"/>
    <link rel="stylesheet" href="${marutiUrl}css/maruti-login.css"/>
</head>
<body>
<div class="col-md-6 col-md-offset-2">
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
<div id="logo">
    <img src="${marutiUrl}img/login-logo.png" alt=""/>
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
                    <span class="add-on"><i class="icon-user"></i></span>
                    <input type="text" name="username" placeholder="Username"/>
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <div class="main_input_box">
                    <span class="add-on"><i class="icon-lock"></i></span>
                    <input type="password" name="password" placeholder="Password"/>
                </div>
            </div>
        </div>

        <div class="form-actions">
            <!--
            <span class="pull-left"><a href="#" class="flip-link btn btn-warning" id="to-recover">Lost
                password?</a></span>  -->
            <span class="pull-right"><input type="submit" class="btn btn-success"
                                            value="Login"/></span>
        </div>

    </form>
    <!--
    <form id="recoverform" action="#" class="form-vertical">
        <p class="normal_text">Enter your e-mail address below and we will send you instructions
            <br/><font color="#FF6633">how to recover a password.</font></p>

        <div class="controls">
            <div class="main_input_box">
                <span class="add-on"><i class="icon-envelope"></i></span><input type="text"
                                                                                placeholder="E-mail address"/>
            </div>
        </div>

        <div class="form-actions">
            <span class="pull-left"><a href="#" class="flip-link btn btn-warning"
                                       id="to-login">&laquo; Back to login</a></span>
            <span class="pull-right"><input type="submit" class="btn btn-info"
                                            value="Recover"/></span>
        </div>
    </form>
    -->
</div>

<script src="${marutiUrl}js/jquery.min.js"></script>
<script src="${marutiUrl}js/maruti.login.js"></script>
</body>

</html>
