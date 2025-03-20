const ws = new WebSocket(`ws://${location.host}`);

// 画面表示時
document.addEventListener("DOMContentLoaded", initialize);

// 初期化処理
function initialize() {
    setupWebSocketHandlers();
    setupSendButtonHandler();
}

// WebSocket関連のイベントハンドラを設定
function setupWebSocketHandlers() {
    ws.addEventListener("open", () => handleWebSocketEvent("WebSocket接続が確立されました。"));
    ws.addEventListener("message", (event) => handleWebSocketEvent(event.data, "サーバー"));
    ws.addEventListener("close", () => handleWebSocketEvent("WebSocket接続が閉じられました。"));
}

// WebSocketイベントを処理するヘルパー関数
function handleWebSocketEvent(message, sender = null) {
    sender ? sendMessage(message, sender) : appendMessage(message);
}

// 送信ボタンのイベントハンドラを設定
function setupSendButtonHandler() {
    const sendButton = document.getElementById("sendButton");
    sendButton.addEventListener("click", handleSendButtonClick);
}

// 送信ボタンがクリックされたときの処理
function handleSendButtonClick() {
    const messageInput = document.getElementById("messageInput");
    const message = messageInput.value.trim();
    if (message) {
        sendMessage(message, "クライアント");
        messageInput.value = "";
    }
}

// メッセージを送信するヘルパー関数
function sendMessage(message, sender) {
    appendMessage(`${sender}: ${message}`);
    if (sender === "クライアント") ws.send(message);
}

// メッセージを表示するヘルパー関数
function appendMessage(message) {
    const messagesDiv = document.getElementById("messages");
    const messageElement = createMessageElement(message);
    messagesDiv.appendChild(messageElement);
}

// メッセージ要素を作成するヘルパー関数
function createMessageElement(message) {
    const messageElement = document.createElement("p");
    messageElement.textContent = message;
    return messageElement;
}
