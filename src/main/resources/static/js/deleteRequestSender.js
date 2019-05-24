function deleteRequest(id, entity) {
    $.post({
        url: '/handbook/' + entity + '/delete/' + id,
        statusCode: {
            200: function () {
                console.log("Row # " + id + " has been removed (" + entity + ").");
            }
        }
    }).done(function (data) {
        $("#response").html(data);
        $("#row-" + id).html(null);
    });
}

/*
REST-реквест
 */
function deleteRestRequest(id, entity) {
    $.post({
        url: '/rest/handbook/' + entity + '/delete/',
        data: {
            id: id
        },
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

function deleteOperativeDataRequest(id, entity) {
    $.post({
        url: '/' + entity + '/delete/' + id,
        statusCode: {
            200: function () {
                console.log("Row # " + id + " has been removed (" + entity + ").");
            }
        }
    }).done(function (data) {
        $("#response").html(data);
        $("#row-" + id).html(null);
        $("#info-block" + id).html("<div class=\"issue-body-frame no-content\">Запись удалена<p style=\"font-size:12pt;\">Восстановление невозможно.</p></div>");
    });
}