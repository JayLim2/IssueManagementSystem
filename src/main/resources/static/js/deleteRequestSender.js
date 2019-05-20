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