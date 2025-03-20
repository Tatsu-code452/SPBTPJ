const WebSocket = require("ws");

function createWebSocketServer(server) {
    const wss = new WebSocket.Server({ server });

    wss.on("connection", (ws) => {
        console.log("WebSocket connection established");

        ws.on("message", (message) => {
            // 別途設定;
        });

        // 接続が閉じられた場合の処理
        ws.on("close", () => {
            console.log("WebSocket connection closed");
        });

        // サーバーから定期的にメッセージを送信
        const intervalId = setInterval(() => {
            if (ws.readyState === WebSocket.OPEN) {
                ws.send("サーバーからの定期メッセージ");
            }
        }, 5000);

        ws.on("close", () => {
            clearInterval(intervalId); // 接続が閉じられたらタイマーをクリア
        });
    });
    return wss;
}

module.exports = { createWebSocketServer };
