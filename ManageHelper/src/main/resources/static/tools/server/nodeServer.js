const http = require("http");
const path = require("path");
const { createTcpServer } = require("./tcpServer");
const { createTcpClient } = require("./tcpClient");
const { createWebSocketServer } = require("./webSocketServer.js");
const {
    getContentType,
    sendFile,
    sanitizePath,
    setSecurityHeaders,
    handleSession,
    sendHtmlFileList,
} = require("./helpers.js");

// サーバーの設定
const hostname = "127.0.0.1";
const port = 3000;
const tcpPort = 9000;

// HTTPサーバーを作成
const server = http.createServer((req, res) => {
    // セキュリティヘッダーを追加
    setSecurityHeaders(res);

    // セッション管理
    handleSession(req, res);

    // リクエストされたファイルパスを取得
    const baseDir = path.resolve(__dirname, "../client");
    const filePath = sanitizePath(
        path.join(baseDir, req.url === "/" ? "index.html" : req.url)
    );
    const extname = String(path.extname(filePath)).toLowerCase();
    const contentType = getContentType(extname);

    // ルートパスの場合、HTMLファイル一覧を表示
    if (req.url === "/") {
        sendHtmlFileList(res, baseDir);
    } else {
        // その他のリクエストはファイルを送信
        sendFile(res, filePath, contentType);
    }
});

// TCPサーバーを起動
const tcpServer = createTcpServer(tcpPort); // TCPサーバーを起動
const tcpClient = createTcpClient(tcpPort); // TCPクライアントを作成
// WebSocketサーバーを起動
const WebSocketServer = createWebSocketServer(server);
WebSocketServer.on("connection", (ws) => {
    ws.on("message", (message) => {
        const parsedMessage = JSON.parse(message);
        if (parsedMessage.type === "tcp") {
            tcpClient.write(parsedMessage.message);
        } else {
            ws.send(`サーバー応答: ${parsedMessage.message.toString()}`);
        }
    });
});
// サーバーを起動
server.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
});
