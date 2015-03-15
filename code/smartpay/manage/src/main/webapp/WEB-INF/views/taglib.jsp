<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- since taglib will be imported by all parts of page, we put rootURL here -->
<c:url var="rootURL" value="/"/>
<c:url var="_view" value="${action}"/>
<c:if test="${controller != null}">
    <c:url var="_view" value="${controller}/${action}"/>
</c:if>