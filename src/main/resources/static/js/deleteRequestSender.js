function deleteRequest(id, entity) {
    $.post({
        url: '/handbook/' + entity + '/delete/' + id,
        statusCode: {
            200: function () {
                console.log("Row # " + id + " has been removed (" + entity + ").");
            }
        }
    }).done(function (data) {
        document.getElementById("response").innerHTML += data;
        document.getElementById("row-" + id).innerHTML = null;
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
        document.getElementById("response").innerHTML += data;
        document.getElementById("row-" + id).innerHTML = null;
        document.getElementById("info-block" + id).innerHTML = "<div class=\"issue-body-frame no-content\">Запись удалена<p style=\"font-size:12pt;\">Восстановление невозможно.</p></div>";
    });
}