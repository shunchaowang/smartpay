<!DOCTYPE html>
<%@include file="../taglib.jsp" %>
<spring:message code="withdrawal.label" var="entity"/>

<div class="container-fluid">
    <div class="row">
        <ol class="breadcrumb">
            <li>
                <i class="glyphicon glyphicon-home"></i>
                <spring:message code="home.label"/>
            </li>
            <li>
                <i class="glyphicon glyphicon-list"></i>
                <spring:message code="index.label" arguments="${entity}"/>
            </li>
            <li class="active">
                <i class="glyphicon glyphicon-wrench"></i>
                <spring:message code="create.label" arguments="${entity}"/>
            </li>
        </ol>
    </div>
    <div class="row">
            <div class="col-sm-8">
                <form:form action="${rootURL}withdrawal/saveWithdrawal" method="POST"
                           commandName="withdrawalCommond" cssClass="form-horizontal"
                           id="new-withdrawal-form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="dateRange">
                            <spring:message code="dateRange.label"/>
                        </label>

                        <div class="col-sm-4">
                            <form:input size="80" path="dateRange" id="dateRange"
                                        cssClass="form-control" value="${withdrawalCommond.dateRange}"
                                        readonly="true"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="balance">
                            <spring:message code="wdrlBalance.label"/>
                        </label>

                        <div class="col-sm-4">
                            <form:input size="80" path="balance" id="balance"
                                        cssClass="form-control" value="${withdrawalCommond.balance}"
                                        readonly="true"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="amount">
                            <spring:message code="wdrlAmount.label"/>
                        </label>

                        <div class="col-sm-4">
                            <form:input size="80" path="amount" id="amount"
                                        cssClass="form-control" value="${withdrawalCommond.amount}"
                                        readonly="true"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="securityRate">
                            <spring:message code="securityRate.label"/>
                        </label>

                        <div class="col-sm-4">
                            <form:input size="80" path="securityRate" id="securityRate"
                                        cssClass="form-control" value="${withdrawalCommond.securityRate}"
                                        readonly="true"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="securityDeposit">
                            <spring:message code="securityDeposit.label"/>
                        </label>

                        <div class="col-sm-4">
                            <form:input size="80" path="securityDeposit" id="securityDeposit"
                                        cssClass="form-control" value="${withdrawalCommond.securityDeposit}"
                                        readonly="true"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="requester">
                            <spring:message code="wdrlRequester.label"/>
                        </label>

                        <div class="col-sm-4">
                            <form:input size="80" path="requester" id="requester"
                                        cssClass="form-control" value="${withdrawalCommond.requester}"
                                        readonly="true"/>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="remark">
                            <spring:message code="remark.label"/>
                        </label>

                        <div class="col-sm-4">
                            <form:textarea cols="100" rows="5" path="remark" id="remark"
                                           cssClass="form-control"/>
                        </div>
                    </div>
                    <div class='form-group'>
                        <div class="col-sm-2 col-sm-offset-2">
                            <button class='btn btn-default' id='create-button' type="submit">
                                <spring:message code='action.save.label'/>
                            </button>
                        </div>
                        <div class="col-sm-2">
                            <button class='btn btn-default' id='reset-button' type="reset">
                                <spring:message code='action.reset.label'/>
                            </button>
                        </div>
                    </div>
                </form:form>

            </div>
    </div>
</div>
