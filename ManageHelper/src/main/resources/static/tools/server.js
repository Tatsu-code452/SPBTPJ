const http = require("http");
const fs = require("fs");
const path = require("path");

// サーバーの設定
const hostname = "127.0.0.1";
const port = 3000;

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

// サーバーを作成
const server = http.createServer((req, res) => {
    let filePath = path.join(
        __dirname,
        req.url === "/" ? "index.html" : req.url
    );
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
                    .map((file) => `<li><a href="${file}">${file}</a></li>`)
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
