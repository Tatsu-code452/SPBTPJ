const fs = require("fs");
const path = require("path");
const crypto = require("crypto");
const cookie = require("cookie");

// コンテンツタイプを取得する関数
function getContentType(extname) {
    const mimeTypes = {
        ".html": "text/html",
        ".js": "text/javascript",
        ".css": "text/css",
        ".json": "application/json",
        ".png": "image/png",
        ".jpg": "image/jpeg",
        ".jpeg": "image/jpeg",
        ".gif": "image/gif",
        ".svg": "image/svg+xml",
        ".ico": "image/x-icon",
        ".woff": "font/woff",
        ".woff2": "font/woff2",
        ".ttf": "font/ttf",
        ".eot": "application/vnd.ms-fontobject",
        ".otf": "font/otf",
        ".xml": "application/xml",
        ".pdf": "application/pdf",
        ".zip": "application/zip",
        ".txt": "text/plain"
    };
    return mimeTypes[extname] || "application/octet-stream";
}

// ファイルを読み込んでレスポンスを返す関数
function sendFile(res, filePath, contentType) {
    const baseDir = path.resolve(__dirname, "../");
    const sanitizedPath = sanitizePath(filePath);

    if (!sanitizedPath.startsWith(baseDir)) {
        return sendError(res, 400, "Invalid file path");
    }

    fs.stat(sanitizedPath, (err, stats) => {
        if (err) {
            return sendError(res, 500, "Error accessing file or directory");
        }

        if (stats.isDirectory()) {
            fs.readdir(sanitizedPath, (err, files) => {
                if (err) {
                    return sendError(res, 500, "Error reading directory");
                }
                res.writeHead(200, { "Content-Type": contentType });
                const csvContent = files.join(",");
                res.end(csvContent, "utf-8");
                return;
                
            });
        } else if (stats.isFile()) {
            fs.readFile(sanitizedPath, (error, content) => {
                if (error) {
                    if (error.code === "ENOENT") {
                        return send404Page(res, baseDir);
                    }
                    return sendError(res, 500, `Error: ${error.code}`);
                }
                res.writeHead(200, { "Content-Type": contentType });
                res.end(content, "utf-8");
            });
        } else {
            sendError(res, 400, "Invalid file or directory");
        }
    });
}

// 404ページを送信する関数
function send404Page(res, baseDir) {
    fs.readFile(path.join(baseDir, "404.html"), (error, content) => {
        res.writeHead(404, { "Content-Type": "text/html" });
        res.end(content || "404 Not Found", "utf-8");
    });
}

// エラーレスポンスを送信する関数
function sendError(res, statusCode, message) {
    res.writeHead(statusCode, { "Content-Type": "text/plain" });
    res.end(message);
}

// セッションIDを生成する関数
function generateSessionId() {
    return crypto.randomBytes(16).toString("hex");
}

// セキュリティ対策: ディレクトリトラバーサル攻撃を防ぐためにファイルパスを正規化
function sanitizePath(filePath) {
    return path.normalize(filePath).replace(/^(\.\.[\/\\])+/, "");
}

// セキュリティ対策: XSS攻撃を防ぐためにHTMLエスケープ関数を追加
function escapeHtml(unsafe) {
    return unsafe
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}

// セキュリティヘッダーを設定する関数
function setSecurityHeaders(res) {
    res.setHeader("X-Content-Type-Options", "nosniff");
    res.setHeader("X-Frame-Options", "DENY");
    res.setHeader("X-XSS-Protection", "1; mode=block");
}

const sessions = {}; // セッション管理用の変数

// セッション管理処理
function handleSession(req, res) {
    const cookies = cookie.parse(req.headers.cookie || "");
    let sessionId = cookies.sessionId;
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
function sendHtmlFileList(res, baseDir) {
    fs.readdir(baseDir, (err, files) => {
        if (err) {
            return sendError(res, 500, "Error reading directory");
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

module.exports = {
    getContentType,
    sendFile,
    generateSessionId,
    sanitizePath,
    escapeHtml,
    setSecurityHeaders,
    handleSession,
    sendHtmlFileList,
};
