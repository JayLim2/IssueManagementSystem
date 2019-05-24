function editProjectType() {
    let serialized = $("#editForm").serialize();

    $.post({
        url: '/rest/handbook/projectTypes/edit',
        data: serialized,
        statusCode: {
            200: function () {
                console.log('Project type \"' + title + '\" edited.');
            }
        },
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