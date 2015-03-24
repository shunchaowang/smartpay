<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@include file="../../../taglib.jsp" %>

<div id="edit-encryption-modal">
    <form action="" method="POST" class="form-horizontal" id="editEncryptionForm">
        <div class="form-group">
            <label class="col-sm-3 control-label" for="encryptionKey">
                <spring:message code="key.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="col-sm-6">
                <input id="encryptionKey" class="form-control" placeholder="Key"/>
            </div>
        </div>
        <!-- merchant status -->
        <div class="form-group">
            <label class="col-sm-3 control-label" for="encryptionTypeId">
                <spring:message code="type.label"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="col-sm-6">
                <select id="encryptionTypeId" class="form-control" required="">
                    <c:forEach items="${encryptionTypes}" var="type">
                        <option value="${type.id}">${type.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class='form-group'>
            <div class='col-sm-offset-3 col-sm-10'>
                <button class='btn btn-default' id='save-encryption-button' type="submit">
                    <spring:message code='action.save.label'/>
                </button>
                <button class='btn btn-default' id='reset-button' type="reset">
                    <spring:message code='action.reset.label'/>
                </button>
            </div>
        </div>
    </form>
    <!-- end of form-control -->
</div>
