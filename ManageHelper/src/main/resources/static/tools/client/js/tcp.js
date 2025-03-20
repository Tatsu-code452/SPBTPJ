import { setupSendButtonHandler, appendMessage } from "./common.js";

const ws = new WebSocket(`ws://${location.host}`);

// 画面表示時
document.addEventListener("DOMContentLoaded", initializeTcp);

// TCP初期化処理
function initializeTcp() {
    setupWebSocketHandlers();
    setupSendButtonHandler(
        "tcpSendButton",
        "tcpMessageInput",
        "tcpMessages",
        "tcp",
        ws
    );
}

// WebSocket関連のイベントハンドラを設定
function setupWebSocketHandlers() {
    ws.addEventListener("open", () =>
        appendMessage("tcpMessages", "WebSocket(TCP)接続が確立されました。")
    );
    ws.addEventListener("message", (event) =>
        appendMessage("tcpMessages", `サーバー(TCP): ${event.data}`)
    );
    ws.addEventListener("close", () =>
        appendMessage("tcpMessages", "WebSocket(TCP)接続が閉じられました。")
    );
}
