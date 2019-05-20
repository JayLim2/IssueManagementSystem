function deleteEmployeePosition(id) {
    $.post({
        url: '/handbook/employeePositions/delete/' + id,
        statusCode: {
            200: function () {
                console.log("Employee position # " + id + " has been removed.");
            }
        }
    }).done(function (data) {
        document.getElementById("response").innerHTML += data;
        document.getElementById("row-" + id).innerHTML = null;
    });
}

function deleteEmployee(id) {
    $.post({
        url: '/handbook/employees/delete/' + id,
        statusCode: {
            200: function () {
                console.log("Employee # " + id + " has been removed.");
            }
        }
    }).done(function (data) {
        document.getElementById("response").innerHTML += data;
        document.getElementById("row-" + id).innerHTML = null;
    });
}