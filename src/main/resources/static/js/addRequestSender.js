function addProjectType() {
    let serialized = $("#addForm").serialize();

    $.post({
        url: '/rest/handbook/projectTypes/add',
        data: serialized,
        statusCode: {
            200: function () {
                console.log('Project type \"' + title + '\" added.');
            }
        },
    }).done(function (data) {
        let result = data.info ? data.info : data.error;
        let msgClass = (data.info ? "info" : "err") + '-msg';
        $("#add-response").html("<div class='" + msgClass + "'>" + result + "</div>");

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