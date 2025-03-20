const net = require("net");

function createTcpClient(port, host = "127.0.0.1") {
    const client = new net.Socket();

    function connect() {
        client.connect(port, host, () => {
            console.log("サーバーに接続しました。");
        });
    }

    client.on("data", (data) => {
        console.log("サーバーから受信:", data.toString());
    });

    client.on("close", () => {
        console.log("接続が閉じられました。");
    });

    client.on("error", (err) => {
        console.error("エラー:", err.message);
        attemptReconnect();
    });

    function attemptReconnect() {
        console.log("1秒後に再接続を試みます...");
        setTimeout(connect, 1000);
    }

    connect();

    return client;
}

function sendTcp(client, message) {
    client.write(message);
}

module.exports = { createTcpClient, sendTcp };