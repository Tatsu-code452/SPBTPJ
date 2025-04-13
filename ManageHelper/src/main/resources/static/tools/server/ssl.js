const forge = require("node-forge");
const fs = require("fs");
const path = require("path");

// npm install node-forge
// node ssl.js
// 鍵ペアの生成
const keys = forge.pki.rsa.generateKeyPair(2048);
const cert = forge.pki.createCertificate();

// 証明書の基本情報を設定
cert.publicKey = keys.publicKey;
cert.serialNumber = "01";
cert.validity.notBefore = new Date();
cert.validity.notAfter = new Date();
cert.validity.notAfter.setFullYear(cert.validity.notBefore.getFullYear() + 2); // 有効期限を2年に延長

// 証明書のSubjectとIssuerを設定 (自己署名なので同じ)
const attrs = [
    {
        name: "commonName",
        value: "localhost",
    },
    {
        name: "countryName",
        value: "JP",
    },
    {
        shortName: "ST",
        value: "Tokyo",
    },
    {
        name: "localityName",
        value: "Shibuya",
    },
    {
        name: "organizationName",
        value: "My Organization",
    },
    {
        shortName: "OU",
        value: "My Unit",
    },
];
cert.setSubject(attrs);
cert.setIssuer(attrs);

// Subject Alternative Name (SAN) を追加
const extensions = [
    {
        name: "basicConstraints",
        cA: true,
    },
    {
        name: "keyUsage",
        keyCertSign: true,
        digitalSignature: true,
        nonRepudiation: true,
        keyEncipherment: true,
        dataEncipherment: true,
    },
    {
        name: "extKeyUsage",
        serverAuth: true,
        clientAuth: true,
    },
    {
        name: "subjectAltName",
        altNames: [
            { type: 2, value: "localhost" }, // DNS名
            { type: 7, ip: "127.0.0.1" }, // IPアドレス
            { type: 2, value: "localExample.com" }, // 外部ドメイン
            { type: 7, ip: "192.168.100.119" }, // 外部IPアドレス
        ],
    },
];
cert.setExtensions(extensions);

// 証明書に署名
cert.sign(keys.privateKey);

// PEMフォーマットで出力
const privateKeyPem = forge.pki.privateKeyToPem(keys.privateKey);
const certPem = forge.pki.certificateToPem(cert);

// ファイルに保存
const outputDir = path.resolve(__dirname, "certs");
if (!fs.existsSync(outputDir)) {
    fs.mkdirSync(outputDir);
}
fs.writeFileSync(path.join(outputDir, "key.pem"), privateKeyPem);
fs.writeFileSync(path.join(outputDir, "cert.pem"), certPem);

console.log("鍵と証明書を 'certs' フォルダに保存しました。");
