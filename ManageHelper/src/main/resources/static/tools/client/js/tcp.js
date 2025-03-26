import { setupSendButtonHandler, appendMessage } from "./common/common.js";

const ws = new WebSocket(`ws://${location.host}`);

// 画面表示時
document.addEventListener("DOMContentLoaded", initializeTcp);

// TCP初期化処理
function initializeTcp() {
    setupWebSocketHandlers(ws, "tcpMessages");
    setupSendButtonHandler(
        "tcpSendButton",
        "tcpMessageInput",
        "tcpMessages",
        "tcp",
        ws
    );
}

// WebSocket関連のイベントハンドラを共通化
function setupWebSocketHandlers(ws, outputId) {
    ws.addEventListener("open", () =>
        appendMessage(outputId, "WebSocket(TCP)接続")
    );
    ws.addEventListener("message", (event) =>
        appendMessage(outputId, `サーバー(TCP): ${event.data}`)
    );
    ws.addEventListener("close", () =>
        appendMessage(outputId, "WebSocket(TCP)切断")
    );
    ws.addEventListener("error", (error) =>
        console.error("WebSocketエラー:", error)
    );
}
