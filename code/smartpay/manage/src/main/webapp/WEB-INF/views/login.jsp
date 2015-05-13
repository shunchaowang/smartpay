<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@include file="taglib.jsp" %>
<html>
<head>
    <jsp:include page="_import.jsp"/>
    <link rel="stylesheet" href="${rootURL}assets/css/login.css"/>
</head>
<body>
<div class="container">
    <div id="logo" class="row">
        <img src=""/>
    </div>
    <div id="notification" class="row">
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
    </div>
    <!-- end of notification -->
    <div class="row">
        <div class="col-sm-6" id="form-section">
            <form id="login" class="form-horizontal" action="${rootURL}login" method="post">

                <div class="form-group">
                    <label class="col-sm-2 control-label">
                        <spring:message code="login.username.label"/>
                    </label>

                    <div class="col-sm-4">
                        <input type="text" name="username" class="form-control" placeholder="Email">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">
                        <spring:message code="login.password.label"/>
                    </label>

                    <div class="col-sm-4">
                        <input type="password" name="password" class="form-control"
                               placeholder="Password">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-4">
                        <button type="submit" class="btn btn-default">
                            <spring:message code="login.label"/>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>


<script type="application/javascript"></script>


</body>
</html>