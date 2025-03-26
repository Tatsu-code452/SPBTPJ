// Electronモジュールの読み込み
const { app, BrowserWindow, globalShortcut } = require("electron");

// ウィンドウの参照を保持する変数
let mainWindow;

// アプリが準備完了した際に呼び出されるイベント
app.on("ready", () => {
    // 新しいウィンドウを作成
    mainWindow = new BrowserWindow({
        width: 1920, // ウィンドウ幅
        height: 1080, // ウィンドウ高さ
        webPreferences: {
            nodeIntegration: true, // Node.jsモジュールの利用を許可
        },
    });

    // ウィンドウにHTMLファイルを読み込む
    mainWindow.loadFile("native/index.html");

    // Alt + 左矢印のショートカットを登録
    globalShortcut.register("Alt+Left", () => {
        if (mainWindow.webContents.canGoBack()) {
            mainWindow.webContents.goBack();
        }
    });

    // ウィンドウが閉じられたときにメモリを解放
    mainWindow.on("closed", () => {
        mainWindow = null;
    });
});

// アプリ終了時にショートカットを解除
app.on("will-quit", () => {
    globalShortcut.unregisterAll();
});

// 全てのウィンドウが閉じられたときの処理 (macOS以外)
app.on("window-all-closed", () => {
    if (process.platform !== "darwin") {
        app.quit();
    }
});

// macOSでアプリが再びアクティブになったときの処理
app.on("activate", () => {
    if (mainWindow === null) {
        // 新しいウィンドウを再作成
        mainWindow = new BrowserWindow({
            width: 800,
            height: 600,
            webPreferences: {
                nodeIntegration: true,
            },
        });
        mainWindow.loadFile("native/index.html");
    }
});
