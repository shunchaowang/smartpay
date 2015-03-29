<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@include file="taglib.jsp" %>

<html>
<head>
    <jsp:include page="_import.jsp"/>
</head>
<body class="container">
<jsp:include page="_header.jsp"/>

<div class="row" id="breadcrumb">
    <ol class="breadcrumb">
        <li>
            <spring:message code="home.label"/>
        </li>
        <c:if test="${domain != null}">
            <spring:message code="${domain}.label" var="entity"/>

            <li>
                <spring:message code="manage.label" arguments="${entity}"/>
            </li>
            <li>
                <spring:message code="${action}.label" arguments="${entity}"/>
            </li>
        </c:if>
    </ol>
</div>

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

<jsp:include page="${_view}.jsp"/>
<jsp:include page="_footer.jsp"/>
</body>