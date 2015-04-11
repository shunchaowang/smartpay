<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="${domain}.label" var="entity"/>

<style media="screen" type="text/css">
    .dialogClass {
        background-color: #e5e5e5;
        padding: 5px;
    }

    .dialogClass .ui-dialog-titlebar-close {
        display: none;
    }

</style>

<div id="confirm-dialog">
    <form id="new-shipment-form">
        <input id="id" name="id" value="${id}" type="hidden"/>
        <!-- carrier -->
        <div>
            <p>
                <spring:message code="delete.confirm.message"
                                arguments="${entity}, ${username}"/>
            </p>
        </div>
        <!-- buttons -->
        <div class='col-sm-offset-4'>
            <button class='tableButton' id='delete-confirm-button'>
                <spring:message code='action.delete.label'/>
            </button>
            <button class='tableButton col-sm-offset-2' id='cancel-button'>
                <spring:message code='action.cancel.label'/>
            </button>
        </div>
    </form>
</div>
