import { setupSendButtonHandler, appendMessage } from "./common.js";

const ws = new WebSocket(`ws://${location.host}`);

// 画面表示時
document.addEventListener("DOMContentLoaded", initializeWebSocket);

// WebSocket初期化処理
function initializeWebSocket() {
    setupWebSocketHandlers(ws, "messages");
    setupSendButtonHandler(
        "sendButton",
        "messageInput",
        "messages",
        "WebSocket",
        ws
    );
}

// WebSocket関連のイベントハンドラを共通化
function setupWebSocketHandlers(ws, outputId) {
    ws.addEventListener("open", () =>
        appendMessage(outputId, "WebSocket接続が確立されました。")
    );
    ws.addEventListener("message", (event) =>
        appendMessage(outputId, `サーバー: ${event.data}`)
    );
    ws.addEventListener("close", () =>
        appendMessage(outputId, "WebSocket接続が閉じられました。")
    );
    ws.addEventListener("error", (error) =>
        console.error("WebSocketエラー:", error)
    );
}
