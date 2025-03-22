const WebSocket = require("ws");

function createWebSocketServer(server) {
    const wss = new WebSocket.Server({ server });

    // WebSocket接続時の処理
    wss.on("connection", (ws) => {
        console.log("WebSocket connection established");

        // クライアントからのメッセージ受信時の処理
        ws.on("message", (message) => {
            console.log("Received message:", message);
            // 必要に応じて処理を追加
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

        // 接続が閉じられたらタイマーをクリア
        ws.on("close", () => {
            clearInterval(intervalId);
        });
    });

    return wss;
}

module.exports = { createWebSocketServer };
