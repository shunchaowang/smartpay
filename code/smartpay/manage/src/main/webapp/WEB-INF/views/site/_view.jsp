<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<%@include file="../taglib.jsp" %>
<spring:message var="cancelAction" code="action.cancel.label"/>
<spring:message var="entity" code="refuse.label"/>
<%--<spring:message code="claim.label" var="claimLabel" arguments="${entity}"/>--%>

<div class="row">
    <div class="col-sm-2 pull-left">
        <button class="btn btn-default" name="import-button"  id="import-button">
            <i class="glyphicon glyphicon-import"></i>
            <spring:message code="action.import.label" />
        </button>
    </div>
</div>