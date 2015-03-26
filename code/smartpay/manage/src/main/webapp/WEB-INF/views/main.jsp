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
            <a href="${rootURL}">
                <spring:message code="home.label"/>
            </a>
        </li>


        <c:if test="${domain != null}">
            <spring:message code="${domain}.label" var="entity"/>

            <c:if test="${controller != null}">
                <li>
                    <a href="${rootURL}${controller}/">
                        <spring:message code="manage.label" arguments="${entity}"/>
                    </a>
                </li>
            </c:if>
            <c:if test="${action != null}">
                <li>
                    <spring:message code="${action}.label" arguments="${entity}"/>
                </li>
            </c:if>
        </c:if>
    </ol>
</div>

<!-- breadcrumb goes here -->
<jsp:include page="${_view}.jsp"/>
<jsp:include page="_footer.jsp"/>
</body>