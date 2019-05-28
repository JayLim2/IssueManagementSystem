//Add request
function addRequest(entity) {
    let serialized = $("#addForm").serialize();

    $.post({
        url: '/handbook/' + entity + '/add',
        data: serialized,
        statusCode: {
            200: function () {
                console.log('added')
            }
        }
    }).done(function (data) {
        let isEmployeeHandbook = data.login && data.password;

        let result = data.info ? data.info : data.error;

        if (isEmployeeHandbook) {
            result += '<div><b>Логин: </b>' + data.login + '<br/><b>Пароль: </b>' + data.password + '</div>';
            result += '<div style="font-weight:bold;">Запомните пароль! В целях безопасности в справочнике хранится хеш-функция пароля!</div>';
        }

        let msgClass = (data.info ? "info" : "err") + '-msg';
        $("#add-response").html("<div class='" + msgClass + "'>" + result + "</div>");

        if (data.info && !isEmployeeHandbook) {
            setTimeout(
                function () {
                    location.reload();
                },
                1500
            )
        }
    }).fail(function (e) {
        console.error(e);
    });
}

//Edit
function editRequest(entity) {
    let serialized = $("#editForm").serialize();

    $.post({
        url: '/handbook/' + entity + '/edit',
        data: serialized,
        statusCode: {
            200: function () {
                console.log('edited');
            }
        }
    }).done(function (data) {
        let result = data.info ? data.info : data.error;
        let msgClass = (data.info ? "info" : "err") + '-msg';
        $("#edit-response").html("<div class='" + msgClass + "'>" + result + "</div>");

        if (data.info) {
            setTimeout(
                function () {
                    location.reload();
                },
                1500
            )
        }
    }).fail(function (e) {
        console.error(e);
    });
}

//Delete request
function deleteRequest(id, entity) {
    $.post({
        url: '/handbook/' + entity + '/delete/',
        data: {id: id},
        statusCode: {
            200: function () {
                console.log("Row # " + id + " has been removed (" + entity + ").");
            }
        }
    }).done(function (data) {
        let result = data.info ? data.info : data.error;
        let msgClass = (data.info ? "info" : "err") + '-msg';
        $("#response").html("<div class='" + msgClass + "'>" + result + "</div>");

        if (data.info) {
            $("#row-" + id).html(null);
        }
    });
}

/* OPERATIVE DATA */

//Add request
function addOperativeDataRequest(entity, formId, responseId) {
    let serialized = $(formId).serialize();

    $.post({
        url: '/' + entity + '/add',
        data: serialized,
        statusCode: {
            200: function () {
                console.log('added')
            }
        }
    }).done(function (data) {
        let isEmployeeHandbook = data.login && data.password;

        let result = data.info ? data.info : data.error;

        if (isEmployeeHandbook) {
            result += '<div><b>Логин: </b>' + data.login + '<br/><b>Пароль: </b>' + data.password + '</div>';
            result += '<div style="font-weight:bold;">Запомните пароль! В целях безопасности в справочнике хранится хеш-функция пароля!</div>';
        }

        let msgClass = (data.info ? "info" : "err") + '-msg';
        $(responseId).html("<div class='" + msgClass + "'>" + result + "</div>");

        if (data.info && !isEmployeeHandbook) {
            setTimeout(
                function () {
                    location.reload();
                },
                1500
            )
        }
    }).fail(function (e) {
        console.error(e);
    });
}

//Edit request
function editOperativeDataRequest(entity, formId, responseId) {
    let serialized = $(formId).serialize();

    $.post({
        url: '/' + entity + '/edit',
        data: serialized,
        statusCode: {
            200: function () {
                console.log('edited');
            }
        }
    }).done(function (data) {
        let result = data.info ? data.info : data.error;
        let msgClass = (data.info ? "info" : "err") + '-msg';
        $(responseId).html("<div class='" + msgClass + "'>" + result + "</div>");

        if (data.info) {
            setTimeout(
                function () {
                    location.reload();
                },
                1500
            )
        }
    }).fail(function (e) {
        console.error(e);
    });
}

//Delete request
function deleteOperativeDataRequest(id, entity) {
    $.post({
        url: '/' + entity + '/delete/',
        data: {id: id},
        statusCode: {
            200: function () {
                console.log("Row # " + id + " has been removed (" + entity + ").");
            }
        }
    }).done(function (data) {
        let result = data.info ? data.info : data.error;
        let msgClass = (data.info ? "info" : "err") + '-msg';
        $("#response").html("<div class='" + msgClass + "' style='margin:0 35px 10px 35px'>" + result + "</div>");

        let rowId = entity === 'projects' ? "#row-" + id : "#issue-row-" + id;
        if (data.info) {
            $(rowId).html(null);
        }
    });
}


/* COMMENTS */

//Add request
function addCommentRequest() {
    let serialized = $("#addComment").serialize();

    $.post({
        url: '/issueActions/add',
        data: serialized,
        statusCode: {
            200: function () {
                console.log('added')
            }
        }
    }).done(function (data) {
        let result = data.info ? data.info : data.error;

        let msgClass = (data.info ? "info" : "err") + '-msg';
        $("#add-comment-response").html("<div class='" + msgClass + "' style='margin-top:20px;'>" + result + "</div>");
    }).fail(function (e) {
        console.error(e);
    });
}

/* REPORTS */

//Build request
function buildReportRequest() {

    $("#build-report-response").html('<div class="warn-msg" style="margin-top:20px;margin-left:10px;">Построение отчета...</div>');
    let defaultLinkBlock = "не подготовлена";
    $("#downloadBlock").html(defaultLinkBlock);

    let serialized = $("#buildReportForm").serialize();

    $.post({
        url: '/reports/build',
        data: serialized,
        statusCode: {
            200: function () {
                console.log('built')
            }
        }
    }).done(function (data) {
        let result = data.info ? data.info : data.error;
        let link = data.link;

        let msgClass = (data.info ? "info" : "err") + '-msg';
        $("#build-report-response").html("<div class='" + msgClass + "' style='margin-top:20px;margin-left:10px;'>" + result + "</div>");

        let linkBlock = data.info ? "<a target='_blank' href='" + link + "'>скачать отчет</a>"
            : "отчет не построен";
        $("#downloadBlock").html(linkBlock);
    }).fail(function (e) {
        console.error(e);
    });
}

/* PROJECT TEAM MEMBERS */

//Add request
function addProjectTeamMemberRequest() {
    let serialized = $("#addProjectTeamMemberForm").serialize();

    $.post({
        url: '/projects/team/add',
        data: serialized,
        statusCode: {
            200: function () {
                console.log('added')
            }
        }
    }).done(function (data) {
        let result = data.info ? data.info : data.error;

        let msgClass = (data.info ? "info" : "err") + '-msg';
        $("#add-project-team-member-response").html("<div class='" + msgClass + "' style='margin-top:20px;'>" + result + "</div>");
    }).fail(function (e) {
        console.error(e);
    });
}

//Edit request
function editProjectTeamMemberRequest() {
    let serialized = $("#editProjectTeamMemberForm").serialize();

    $.post({
        url: '/projects/team/edit',
        data: serialized,
        statusCode: {
            200: function () {
                console.log('edited')
            }
        }
    }).done(function (data) {
        let result = data.info ? data.info : data.error;

        let msgClass = (data.info ? "info" : "err") + '-msg';
        $("#edit-project-team-member-response").html("<div class='" + msgClass + "' style='margin-top:20px;'>" + result + "</div>");
    }).fail(function (e) {
        console.error(e);
    });
}

function deleteProjectTeamMemberRequest(projectId, employeeId) {
    $.post({
        url: '/projects/team/delete/',
        data: {
            projectId: projectId,
            employeeId: employeeId
        },
        statusCode: {
            200: function () {
                console.log("Row # " + id + " has been removed (team member).");
            }
        }
    }).done(function (data) {
        let result = data.info ? data.info : data.error;
        let msgClass = (data.info ? "info" : "err") + '-msg';
        $("#response").html("<div class='" + msgClass + "' style='margin:0 35px 10px 35px'>" + result + "</div>");

        let rowId = entity === 'projects' ? "#row-" + id : "#issue-row-" + id;
        if (data.info) {
            $(rowId).html(null);
        }
    });
}