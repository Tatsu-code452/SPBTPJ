const net = require("net");

function createTcpClient(port, host = "127.0.0.1") {
    const client = new net.Socket();

    // サーバーに接続する関数
    function connect() {
        client.connect(port, host, () => {
            console.log("サーバーに接続しました。");
        });
    }

    // サーバーからデータを受信したときの処理
    client.on("data", (data) => {
        console.log("サーバーから受信:", data.toString());
    });

    // 接続が閉じられたときの処理
    client.on("close", () => {
        console.log("接続が閉じられました。");
    });

    // エラー発生時の処理
    client.on("error", (err) => {
        console.error("エラー:", err.message);
        attemptReconnect();
    });

    // 再接続を試みる関数
    function attemptReconnect() {
        console.log("1秒後に再接続を試みます...");
        setTimeout(connect, 1000);
    }

    connect();

    return client;
}

// メッセージを送信する関数
function sendTcp(client, message) {
    client.write(message);
}

module.exports = { createTcpClient, sendTcp };