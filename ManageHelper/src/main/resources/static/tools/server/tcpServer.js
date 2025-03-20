const net = require("net");

function createTcpServer(port) {
    const server = net.createServer((socket) => {
        console.log("クライアントが接続しました。");

        socket.on("data", (data) => {
            console.log("クライアントから受信:", data.toString());
            socket.write(`サーバーからの応答: ${data.toString()}`);
        });

        socket.on("end", () => {
            console.log("クライアントが切断しました。");
        });

        socket.on("error", (err) => {
            console.error("エラー:", err.message);
        });
    });

    server.listen(port, () => {
        console.log(`TCPサーバーがポート${port}で待機中...`);
    });

    return server;
}

module.exports = { createTcpServer };
