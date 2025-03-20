import { setupSendButtonHandler, appendMessage } from "./common.js";

const ws = new WebSocket(`ws://${location.host}`);

// 画面表示時
document.addEventListener("DOMContentLoaded", initializeWebSocket);

// WebSocket初期化処理
function initializeWebSocket() {
    setupWebSocketHandlers();
    setupSendButtonHandler(
        "sendButton",
        "messageInput",
        "messages",
        "WebSocket",
        ws
    );
}

// WebSocket関連のイベントハンドラを設定
function setupWebSocketHandlers() {
    ws.addEventListener("open", () =>
        appendMessage("messages", "WebSocket接続が確立されました。")
    );
    ws.addEventListener("message", (event) =>
        appendMessage("messages", `サーバー: ${event.data}`)
    );
    ws.addEventListener("close", () =>
        appendMessage("messages", "WebSocket接続が閉じられました。")
    );
}
