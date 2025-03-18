const http = require("http");
const fs = require("fs");
const path = require("path");
const cookie = require("cookie");
const {
    getContentType,
    sendFile,
    generateSessionId,
    sanitizePath,
    escapeHtml
} = require("./helpers");

// サーバーの設定
const hostname = "127.0.0.1";
const port = 3000;

// セッション管理用の変数
const sessions = {};

// サーバーを作成
const server = http.createServer((req, res) => {
    // セキュリティヘッダーを追加
    res.setHeader("X-Content-Type-Options", "nosniff");
    res.setHeader("X-Frame-Options", "DENY");
    res.setHeader("X-XSS-Protection", "1; mode=block");

    // セッション管理
    const cookies = cookie.parse(req.headers.cookie || "");
    let sessionId = cookies.sessionId;
    console.log("Session ID:", sessionId); // デバッグ用ログ
    if (!sessionId || !sessions[sessionId]) {
        sessionId = generateSessionId();
        sessions[sessionId] = {};
        res.setHeader("Set-Cookie", cookie.serialize("sessionId", sessionId, {
            httpOnly: true,
            secure: true,
            sameSite: "strict",
        }));
    }

    let filePath = sanitizePath(path.join(
        __dirname,
        req.url === "/" ? "index.html" : req.url
    ));
    console.log("File Path:", filePath); // デバッグ用ログ
    let extname = String(path.extname(filePath)).toLowerCase();
    let contentType = getContentType(extname);

    if (req.url === "/") {
        fs.readdir(__dirname, (err, files) => {
            if (err) {
                res.writeHead(500);
                res.end("Error reading directory");
                return;
            }
            let htmlFiles = files.filter(
                (file) => path.extname(file).toLowerCase() === ".html"
            );
            res.writeHead(200, { "Content-Type": "text/html" });
            res.end(
                `<ul>${htmlFiles
                    .map((file) => `<li><a href="${escapeHtml(file)}">${escapeHtml(file)}</a></li>`)
                    .join("")}</ul>`,
                "utf-8"
            );
        });
    } else {
        sendFile(res, filePath, contentType);
    }
});

// サーバーを起動
server.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
});