const https = require('https');
const fs = require('fs');
const path = require('path');
const url = require('url');
const { readFile } = require('fs/promises');
const {
    getContentType,
    sendFile,
    sanitizePath,
    setSecurityHeaders,
    handleSession,
    sendHtmlFileList,
} = require("./helpers.js");

process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0"; // 自己署名証明書を許可（開発環境のみ推奨）

// SSL証明書を読み込む
const options = {
    key: fs.readFileSync(path.join(__dirname, '/certs/key.pem')),  // 秘密鍵
    cert: fs.readFileSync(path.join(__dirname, '/certs/cert.pem')) // SSL証明書
};

// HTTPSサーバーを作成
https.createServer(options, async (req, res) => {
    
    // 静的ファイルを提供
    const baseDir = path.resolve(__dirname, "../client"); // クライアントディレクトリ
    const filePath = sanitizePath(
        path.join(baseDir, req.url === "/" ? "index.html" : req.url)
    );
    const extname = String(path.extname(filePath)).toLowerCase();
    const contentType = getContentType(extname);

    try {
        await sendFile(res, filePath, contentType);
    } catch (err) {
        res.writeHead(404, { "Content-Type": "text/plain" });
        res.end("404 Not Found");
    }
}).listen(443, () => {
    console.log('HTTPS server running at https://localhost');
});
