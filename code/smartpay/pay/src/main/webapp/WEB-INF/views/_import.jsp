<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>

<title><spring:message code="application.title"/></title>

<!-- jquery -->
<script type="text/javascript"
        src="${pageContext.request.contextPath}/assets/vendor/jquery-2.1.3/jquery-2.1.3.min.js"></script>

<!-- bootstrap depending on jquery -->
<link type="text/css" rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/vendor/bootstrap-3.3.2/css/bootstrap.min.css"/>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/assets/vendor/bootstrap-3.3.2/js/bootstrap.min.js"></script>

<!-- jquery-validation depending on jquery -->
<script type="text/javascript"
        src="${pageContext.request.contextPath}/assets/vendor/jquery-validation-1.13.1/jquery.validate.min.js"></script>
<!-- jquery validation locale -->
<script type="text/javascript"
        src="${pageContext.request.contextPath}/assets/vendor/jquery-validation-1.13.1/localization/messages_${currentLocale}.min.js"></script>

<!-- customized css and js -->
<link type="text/css" rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/app.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/app.js"></script>
