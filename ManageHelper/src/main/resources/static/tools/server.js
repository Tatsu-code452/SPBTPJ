const http = require("http");
const fs = require("fs");
const path = require("path");
const cookie = require("cookie");
const WebSocket = require("ws"); // WebSocketモジュールを追加
const {
    getContentType,
    sendFile,
    generateSessionId,
    sanitizePath,
    escapeHtml,
} = require("./helpers");

// サーバーの設定
const hostname = "127.0.0.1";
const port = 3000;

// セッション管理用の変数
const sessions = {};

// HTTPサーバーを作成
const server = http.createServer((req, res) => {
    // セキュリティヘッダーを追加
    res.setHeader("X-Content-Type-Options", "nosniff");
    res.setHeader("X-Frame-Options", "DENY");
    res.setHeader("X-XSS-Protection", "1; mode=block");

    // セッション管理
    handleSession(req, res);

    // リクエストされたファイルパスを取得
    const filePath = sanitizePath(
        path.join(__dirname, req.url === "/" ? "index.html" : req.url)
    );
    const extname = String(path.extname(filePath)).toLowerCase();
    const contentType = getContentType(extname);

    // ルートパスの場合、HTMLファイル一覧を表示
    if (req.url === "/") {
        serveHtmlFileList(res);
    } else {
        // その他のリクエストはファイルを送信
        sendFile(res, filePath, contentType);
    }
});

// セッション管理処理
function handleSession(req, res) {
    const cookies = cookie.parse(req.headers.cookie || "");
    let sessionId = cookies.sessionId;
    console.log("Session ID:", sessionId); // デバッグ用ログ
    if (!sessionId || !sessions[sessionId]) {
        sessionId = generateSessionId();
        sessions[sessionId] = {};
        res.setHeader(
            "Set-Cookie",
            cookie.serialize("sessionId", sessionId, {
                httpOnly: true,
                secure: true,
                sameSite: "strict",
            })
        );
    }
}

// HTMLファイル一覧を表示
function serveHtmlFileList(res) {
    fs.readdir(__dirname, (err, files) => {
        if (err) {
            res.writeHead(500);
            res.end("Error reading directory");
            return;
        }
        const htmlFiles = files.filter(
            (file) => path.extname(file).toLowerCase() === ".html"
        );
        res.writeHead(200, { "Content-Type": "text/html" });
        res.end(
            `<ul>${htmlFiles
                .map(
                    (file) =>
                        `<li><a href="${escapeHtml(file)}">${escapeHtml(
                            file
                        )}</a></li>`
                )
                .join("")}</ul>`,
            "utf-8"
        );
    });
}

// WebSocketサーバーを作成
const wss = new WebSocket.Server({ server });

wss.on("connection", (ws) => {
    console.log("WebSocket connection established");

    // クライアントからのメッセージを処理
    ws.on("message", (message) => {
        console.log("Received:", message);
        ws.send(`Server received: ${message}`);
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

// サーバーを起動
server.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
});
