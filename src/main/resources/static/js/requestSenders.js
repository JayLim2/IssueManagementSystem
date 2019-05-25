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
function addOperativeDataRequest(entity) {
    let serialized = $("#addForm").serialize();

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

//Edit request
function editOperativeDataRequest(entity) {
    let serialized = $("#editForm").serialize();

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
        $("#response").html("<div class='" + msgClass + "' style='margin:0 35px'>" + result + "</div>");

        if (data.info) {
            $("#row-" + id).html(null);
        }
    });
}