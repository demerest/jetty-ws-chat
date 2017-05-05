function connect() {
    var userName = $("#name").val();
    var socket = new WebSocket("ws://" + window.location.host + "/chat/msg?user=" + userName);
    socket.onmessage = function(e) {
        var data = $.parseJSON(e.data);
        $("#chatArea").html(data.log);
        $("#usersArea").html(data.users);
    };

    socket.onclose = function(e) {
        $("body").html("Connection closed: " + e.reason);
    };

    socket.onerror = function(err) {
        $("body").html("Connection error: " + err.message);
    };

    $("#chatBlock").show();
    $("#connectBlock").hide();

    $("#send").click(function() {
        socket.send($("#message").val());
        $("#message").val("");
    })
}