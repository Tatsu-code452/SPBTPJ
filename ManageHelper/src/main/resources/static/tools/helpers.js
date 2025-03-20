const fs = require("fs");
const path = require("path");
const crypto = require("crypto");

// コンテンツタイプを取得する関数
function getContentType(extname) {
    const mimeTypes = {
        ".html": "text/html",
        ".js": "text/javascript",
        ".css": "text/css",
    };
    return mimeTypes[extname] || "application/octet-stream";
}

// ファイルを読み込んでレスポンスを返す関数
function sendFile(res, filePath, contentType) {
    // ファイルパスがディレクトリ外に出ないようにする
    if (!filePath.startsWith(__dirname)) {
        res.writeHead(400, { "Content-Type": "text/plain" });
        res.end("Invalid file path");
        return;
    }

    fs.readFile(filePath, (error, content) => {
        if (error) {
            if (error.code == "ENOENT") {
                fs.readFile(
                    path.join(__dirname, "404.html"),
                    (error, content) => {
                        res.writeHead(200, { "Content-Type": "text/html" });
                        res.end(content, "utf-8");
                    }
                );
            } else {
                res.writeHead(500);
                res.end(
                    `Sorry, check with the site admin for error: ${error.code} ..\n`
                );
            }
        } else {
            res.writeHead(200, { "Content-Type": contentType });
            res.end(content, "utf-8");
        }
    });
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

module.exports = {
    getContentType,
    sendFile,
    generateSessionId,
    sanitizePath,
    escapeHtml,
};
