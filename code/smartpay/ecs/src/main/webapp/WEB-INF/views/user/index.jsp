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

<div id="content">
    <div id="content-header">
        <div id="breadcrumb">
            <a href="${rootURL}">
                <i class="icon icon-home"></i>
                <spring:message code="home.label"/>
            </a>
            <c:if test="${domain != null}">
                <spring:message code="${domain}.label" var="entity"/>
                <a href="${rootURL}${controller}">
                    <spring:message code="manage.label" arguments="${entity}"/>
                </a>
                <a href="${rootURL}${controller}/${action}" class="current">
                    <spring:message code="${action}.label" arguments="${entity}"/>
                </a>
            </c:if>
        </div>
    </div>
    <!-- reserved for notification -->
    <!-- close of content-header -->
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="col-sm-12">
                <div class="widget-box">
                    <div class="widget-title">
                        <span class="icon"><i class="icon icon-th"></i> </span>
                        <h5><spring:message code="index.label" arguments="${entity}"/></h5>
                    </div>
                    <div class="widget-content">
                        <table class="table display table-bordered data-table" id="user-table">
                            <thead>
                            <tr>
                                <th><spring:message code="id.label"/></th>
                                <th><spring:message code="username.label"/></th>
                                <th><spring:message code="firstName.label"/></th>
                                <th><spring:message code="lastName.label"/></th>
                                <th><spring:message code="email.label"/></th>
                                <th><spring:message code="createdTime.label"/></th>
                                <th><spring:message code="status.label"/></th>
                                <th><spring:message code="action.operation.label"/></th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div id="dialog-area">

        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {

        var userTable = $('#user-table').DataTable({
            'language': {
                'url': "${dataTablesLanguage}"
            },
            'processing': true,
            'serverSide': true,
            'paging': true,
            "order": [[0, "desc"]],
            "jQueryUI": true,
            'dom': 'T<""if>rt<"F"lp>',
            "tableTools": {
                "sSwfPath": "${tableTools}",
                "aButtons": [
                    {
                        "sExtends": "copy",
                        "mColumns": [1, 2, 3, 4, 5, 6]
                    },
                    {
                        "sExtends": "xls",
                        "mColumns": [1, 2, 3, 4, 5, 6]
                    }
                ]
            },

            'ajax': {
                'url': "${rootURL}${controller}/list",
                'type': "GET",
                'dataType': 'json'
            },
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {
                    'name': 'username', 'targets': 1, 'data': 'username',
                    'render': function (data, type, row) {
                        return '<a href=' + "${rootURL}${controller}" + '/show/'
                                + row['id'] + '>' + data + '</a>';
                    }
                },
                {'name': 'firstName', 'targets': 2, 'data': 'firstName'},
                {'name': 'lastName', 'targets': 3, 'data': 'lastName'},
                {'name': 'email', 'targets': 4, 'data': 'email'},
                {'name': 'createdTime', 'targets': 5, 'searchable': false, 'data': 'createdTime'},
                {
                    'name': 'userStatus', 'targets': 6, 'searchable': false,
                    'orderable': false, 'data': 'userStatus'
                },
                {
                    'name': 'operation', 'targets': 7, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        return '<a href="' + "${rootURL}${controller}" + '/edit/'
                                + row['id'] + '">' +
                                '<button type="button" name="edit-button" class="tableButton"'
                                + '">' + '<spring:message code="action.edit.label"/>'
                                + '</button>' + '</a>'
                                + '<button type="button" name="delete-button" class="tableButton"'
                                + ' value="' + row['id'] + '">'
                                + '<spring:message code="action.delete.label"/>'
                                + '</button>';
                    }
                }
            ]
        });

        userTable.on('click', 'button[type=button][name=delete-button]', function (event) {
            event.preventDefault();
            $.ajax({
                type: 'get',
                url: "${rootURL}${controller}/delete",
                data: {
                    id: this.value
                },
                error: function () {
                    alert('There was an error.');
                },
                success: function (data) {
                    $('#dialog-area').append(data);

                    // define dialog
                    var confirmModal = $("#confirm-dialog").dialog({
                        autoOpen: false,
                        resizable: false,
                        height: 'auto',
                        width: 300,
                        modal: true,
                        dialogClass: "dialogClass",
                        open: function (event, ui) {
                            $(".ui-dialog-titlebar-close", ui.dialog || ui).hide();
                        },
                        close: function () {
                            confirmModal.dialog("destroy").remove();
                        }
                    }).dialog("open");

                    $("#cancel-button").click(function (event) {
                        event.preventDefault();
                        confirmModal.dialog("close");
                    });

                    $("#delete-confirm-button").click(function (event) {
                        event.preventDefault();

                        $.ajax({
                            type: 'post',
                            url: "${rootURL}${controller}/delete",
                            data: {
                                id: $("#id").val()
                            },
                            error: function () {
                                alert('There was an error.');
                            },
                            success: function (data) {
                                var alert = "<div class='alert alert-warning alert-dismissible' role='alert'>" +
                                        "<button type='button' class='close' data-dismiss='alert'>" +
                                        "<span aria-hidden='true'>&times;</span>" +
                                        "<span class='sr-only'>"
                                        + "<spring:message code='action.close.label'/> "
                                        + "</span></button>"
                                        + data.message + "</div>";
                                $('#notification').append(alert);
                                confirmModal.dialog("close");
                                userTable.ajax.reload();
                            }
                        });
                    });
                }
            });
        });

    });
</script>