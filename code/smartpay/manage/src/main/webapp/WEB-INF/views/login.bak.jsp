<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@include file="taglib.jsp" %>
<html>
<head>
    <jsp:include page="_import.jsp"/>
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

<div class="row">
    <div class="col-md-6 col-md-offset-2">
        <h2>User Login Form</h2>
        <form:form id="loginForm" method="post" action="${rootURL}login" modelAttribute="user"
                   class="form-horizontal" role="form" cssStyle="width: 800px; margin: 0 auto;">
            <div class="form-group">
                <label for="username" class="col-sm-2 control-label">UserName*</label>

                <div class="col-sm-4">
                    <input type="text" id="username" name="username" class="form-control"
                           placeholder="UserName"/>
                </div>
            </div>
            <div class="form-group">
                <label for="password" class="col-sm-2 control-label">Password*</label>

                <div class="col-sm-4">
                    <input type="password" id="password" name="password" class="form-control"
                           placeholder="Password"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-4">
                    <input type="submit" class="btn btn-primary" value="Login">
                </div>
            </div>

        </form:form>
    </div>
</div>
</body>
</html>