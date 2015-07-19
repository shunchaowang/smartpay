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
        <div class="col-sm-4 pull-right" id="form-section">
            <form id="login" class="form-horizontal" action="${rootURL}login" method="post">

                <div class="form-group">
                    <label class="col-sm-4 control-label">
                        <spring:message code="login.username.label"/>
                    </label>

                    <div class="col-sm-8">
                        <input type="text" name="username" class="form-control"
                               placeholder="Username">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label">
                        <spring:message code="login.password.label"/>
                    </label>

                    <div class="col-sm-8">
                        <input type="password" name="password" class="form-control"
                               placeholder="Password">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-4 col-sm-4">
                        <button type="submit" class="btn btn-default">
                            <spring:message code="login.label"/>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>


<script type="application/javascript">
    $(document).ready(function () {
        /*
         $('#form-section').on('submit', function (e) {
         e.preventDefault();
         var merchant = $('input[name="merchant"]');
         var username = $('input[name="username"]');
         var password = $('input[name="password"]');
         if(merchant.val().length == 0 || username.val().length == 0 ||
         password.val().length == 0) {
         return;
         }
         var name = username.val();
         username.val(merchant.val() + "/" + name);
         $("#form-section").submit();

         var formData = {
         'username' : merchant.val() + '/' + username.val(),
         'password' : password.val()
         };
         $.ajax({
         type: "POST",
         url: $('#form-section').action,
         data: formData,
         error: function (data) {
         alert("There was an error");
         },
         success: function (data) {

         }
         });

         });
         */
    });
</script>


</body>
</html>