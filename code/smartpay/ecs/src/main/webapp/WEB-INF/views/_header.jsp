<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>
    <div id="header" class="row">
        <div class="col-sm-6">
            <a href="${rootURL}"><spring:message code="application.name"/> </a>
        </div>
        <div class="col-sm-6">
            <jsp:include page="_profile.jsp"/>
        </div>
    </div>
