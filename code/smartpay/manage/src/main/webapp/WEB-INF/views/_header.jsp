<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>
<c:if test="${!noNavBar}">
    <nav class="navbar navbar-default" role="navigation" id="nav-header">
        <jsp:include page="_navbar.jsp"/>
    </nav>
    <!-- end navbar -->
</c:if>