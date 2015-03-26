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

        <!-- if subDomain exists use subDomain, otherwise use domain -->
        <c:choose>
            <c:when test="${subDomain != null}">
                <spring:message code="${subDomain}.label" var="entity"/>

                <c:if test="${controller != null}">
                    <li>
                        <a href="${rootURL}${controller}/${subDomain}">
                            <spring:message code="manage.label" arguments="${entity}"/>
                        </a>
                    </li>
                </c:if>
                <c:if test="${action != null}">
                    <li>
                        <a href="${rootURL}${controller}/${action}${subDomain}">
                            <spring:message code="${action}.label" arguments="${entity}"/>
                        </a>
                    </li>
                </c:if>
            </c:when>

            <c:otherwise>
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
                            <a href="${rootURL}${controller}/${action}">
                                <spring:message code="${action}.label" arguments="${entity}"/>
                            </a>
                        </li>
                    </c:if>
                </c:if>
            </c:otherwise>
        </c:choose>


    </ol>
</div>

<!-- breadcrumb goes here -->
<jsp:include page="${_view}.jsp"/>
<jsp:include page="_footer.jsp"/>
</body>