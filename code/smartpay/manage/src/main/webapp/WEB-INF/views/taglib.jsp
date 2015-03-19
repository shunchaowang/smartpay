<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- since taglib will be imported by all parts of page, we put rootURL here -->
<c:url var="rootURL" value="/"/>
<!-- some useful variables and paths -->

<c:set var="currentLocale"
       value="<%=LocaleContextHolder.getLocale().getLanguage()%>"/>
<c:url var="dataTablesLanguage"
       value="${rootURL}assets/vendor/DataTables-1.10.4/localization/messages_${currentLocale}.json"/>

<c:if test="${action == null}">
    <c:set var="action" value="index"/>
</c:if>

<c:set var="_view" value="${action}"/>

<c:if test="${controller != null}">
    <c:set var="_view" value="${controller}/${_view}"/>
</c:if>