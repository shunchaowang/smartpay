<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@include file="taglib.jsp" %>
<html>
<head>
    <jsp:include page="_import.jsp"/>
    <style>
        body {
            background-image: url("${rootURL}assets/images/login-bg-bgline.png");
        }

        #form-section {
            background-image: url("${rootURL}assets/images/login-input-bg.png");
            background-repeat: no-repeat;
        }
    </style>
</head>
<body>

<div class="container-fluid" id="content">
    <div class="col-sm-12">
        <div id="logo" class="row text-center">
            <img src="${rootURL}assets/images/logo-GP.png"/>
        </div>
        <div class="row text-center">
            <img src="${rootURL}assets/images/login-company-name.png"/>
        </div>
    </div>


    <!-- end of notification -->
    <div class="row">

        <div class="col-sm-6 col-sm-offset-3" id="form-section">
            <br>
            <br>
            <br>
            <br>

            <form id="login" class="form-horizontal" action="${rootURL}login" method="post">
                <div class="form-group">

                    <label class="col-sm-3 control-label">
                        <spring:message code="merchant.label"/>
                    </label>

                    <div class="col-sm-6">
                        <input type="text" name="merchant" class="form-control"
                               placeholder="Merchant Number">
                    </div>
                </div>
                <br>

                <div class="form-group">

                    <label class="col-sm-3 control-label">
                        <spring:message code="login.username.label"/>
                    </label>

                    <div class="col-sm-6">
                        <input type="text" name="username" class="form-control"
                               placeholder="Username">
                    </div>
                </div>
                <br>

                <div class="form-group">

                    <label class="col-sm-3 control-label">
                        <spring:message code="login.password.label"/>
                    </label>

                    <div class="col-sm-6">
                        <input type="password" name="password" class="form-control"
                               placeholder="Password">
                    </div>
                </div>
                <br>

                <div class="form-group">
                    <div class="col-sm-offset-4 col-sm-4">
                        <button type="submit" class="btn btn-default">
                            <spring:message code="login.label"/>
                        </button>
                    </div>
                </div>

                <div id="notification" class="row">
                    <div class="col-sm-12 text-center">
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

            </form>
            <br>
            <br>
            <br> <br>
            <br>
            <br>

            <br>
        </div>

    </div>
</div>


<script type="application/javascript">
    $(document).ready(function () {

        $('#login').validate({
            rules: {
                merchant: {required: true, minlength: 3, maxlength: 32},
                username: {required: true, minlength: 1, maxlength: 32},
                password: {required: true, minlength: 1, maxlength: 32},
            },
            messages: {
                merchant: "Enter your merchant",
                username: "Enter your username",
                password: "Enter your password"
            }
        });

        $('button[type="submit"]').on('click', function (e) {
            e.preventDefault();
            var merchant = $('input[name="merchant"]');
            var username = $('input[name="username"]');
            var password = $('input[name="password"]');
            if (merchant.val().length == 0 || username.val().length == 0 ||
                    password.val().length == 0) {
                return;
            }
            var name = username.val();
            username.val(merchant.val() + "/" + name);
            $("#login").submit();
        });

    });
</script>


</body>
</html>