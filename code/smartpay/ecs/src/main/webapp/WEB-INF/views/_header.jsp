<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>
<!--Header-part-->
<div id="header">
    <h1><a href="${rootURL}"><spring:message code="application.name"/> </a></h1>
</div>
<!--close-Header-part-->

<jsp:include page="_profile.jsp"/>
