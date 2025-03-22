const net = require("net");

function createTcpServer(port) {
    const server = net.createServer((socket) => {
        console.log("クライアントが接続しました。");

        // クライアントからデータを受信したときの処理
        socket.on("data", (data) => {
            console.log("クライアントから受信:", data.toString());
            socket.write(`サーバーからの応答: ${data.toString()}`);
        });

        // クライアントが切断したときの処理
        socket.on("end", () => {
            console.log("クライアントが切断しました。");
        });

        // エラー発生時の処理
        socket.on("error", (err) => {
            console.error("エラー:", err.message);
        });
    });

    // サーバーを指定したポートで待機
    server.listen(port, () => {
        console.log(`TCPサーバーがポート${port}で待機中...`);
    });

    return server;
}

module.exports = { createTcpServer };
