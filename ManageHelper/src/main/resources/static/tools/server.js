const http = require('http');
const fs = require('fs');
const path = require('path');

// サーバーの設定
const hostname = '127.0.0.1';
const port = 3000;

// サーバーを作成
const server = http.createServer((req, res) => {
    // ファイルのパスを取得
    let filePath = path.join(__dirname, req.url === '/' ? 'index.html' : req.url);
    let extname = String(path.extname(filePath)).toLowerCase();
    // ファイルの拡張子に応じてコンテンツタイプを設定
    let mimeTypes = {
        '.html': 'text/html',
        '.js': 'text/javascript',
        '.css': 'text/css'
    };
    let contentType = mimeTypes[extname] || 'application/octet-stream';

    // ファイルを読み込んでクライアントに送信
    fs.readFile(filePath, (error, content) => {
        if (error) {
            // ファイルが見つからない場合は404.htmlを返す
            if (error.code == 'ENOENT') {
                fs.readFile(path.join(__dirname, '404.html'), (error, content) => {
                    res.writeHead(200, { 'Content-Type': 'text/html' });
                    res.end(content, 'utf-8');
                });
            } else {
                // その他のエラーの場合は500.html
                res.writeHead(500);
                res.end(`Sorry, check with the site admin for error: ${error.code} ..\n`);
                res.end();
            }
        } else {
            // ファイルが見つかった場合はファイルの内容
            res.writeHead(200, { 'Content-Type': contentType });
            res.end(content, 'utf-8');
        }
    });
});

// サーバーを起動
server.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
});