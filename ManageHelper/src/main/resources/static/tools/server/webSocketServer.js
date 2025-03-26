const WebSocket = require("ws");

function createWebSocketServer(server) {
    const wss = new WebSocket.Server({ server });

    // WebSocket接続時の処理
    wss.on("connection", (ws) => {
        console.log("WebSocket接続");

        // クライアントからのメッセージ受信時の処理
        ws.on("message", (message) => {
            const text = JSON.parse(message.toString()).message;
            console.log("WebSocket受信:", text);
            console.log("WebSocket送信:", text);
            ws.send(text);
        });

        // 接続が閉じられた場合の処理
        ws.on("close", () => {
            console.log("WebSocket切断");
        });

        // サーバーから定期的にメッセージを送信
        const intervalId = setInterval(() => {
            if (ws.readyState === WebSocket.OPEN) {
                const message = "サーバーからの定期メッセージ";
                console.log("WebSocket送信:", message);
                ws.send(message);
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
