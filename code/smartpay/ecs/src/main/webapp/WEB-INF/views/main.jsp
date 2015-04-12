<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@include file="taglib.jsp" %>

<html>
<head>
    <jsp:include page="_import.jsp"/>
</head>
<body>

<jsp:include page="_header.jsp"/>
<jsp:include page="_navbar.jsp"/>
<div class='col-sm-12' id='notification'>
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

<!-- content area -->
<jsp:include page="${_view}.jsp"/>

<div class="row-fluid">
    <jsp:include page="_footer.jsp"/>
</div>

</body>