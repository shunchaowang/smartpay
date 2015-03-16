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
    <!--TODO ADD BREADCRUMB -->
    <!--
    <div class="col-sm-6">
        <ol class="breadcrumb">
            <c:if test="${controller != null}">
                <li>
                    <a href="${rootURL}${controller}">
                        <spring:message code="user.profile.label"/>
                    </a>
                </li>
            </c:if>
        </ol>
    </div>
    -->
</div>
<!-- breadcrumb goes here -->
<jsp:include page="${_view}.jsp"/>
<jsp:include page="_footer.jsp"/>
</body>