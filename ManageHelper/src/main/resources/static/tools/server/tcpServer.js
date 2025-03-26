const net = require("net");

function createTcpServer(port) {
    const server = net.createServer((socket) => {
        console.log("クライアント接続");

        // クライアントからデータを受信したときの処理
        socket.on("data", (data) => {
            console.log("クライアント受信:", data.toString());
            socket.write(`サーバー応答: ${data.toString()}`);
        });

        // クライアントが切断したときの処理
        socket.on("end", () => {
            console.log("クライアント切断");
        });

        // エラー発生時の処理
        socket.on("error", (err) => {
            console.error("エラー:", err.message);
        });
    });

    // サーバーを指定したポートで待機
    server.listen(port, () => {
        console.log(`接続待ち ポート${port}`);
    });

    return server;
}

module.exports = { createTcpServer };
