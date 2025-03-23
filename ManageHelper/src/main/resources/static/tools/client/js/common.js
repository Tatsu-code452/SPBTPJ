// テキスト改行分割
function splitTextByBreakeLine(text) {
    return text.split(/\r?\n/);
}

// 日付をdatetime-local用にフォーマット
function formatDateForInput(dateStr) {
    const date = new Date(dateStr);
    const pad = (num) => String(num).padStart(2, "0");
    return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(
        date.getDate()
    )}T${pad(date.getHours())}:${pad(date.getMinutes())}`;
}

// 日付フォーマット
function formatDate(dateStr) {
    const date = new Date(dateStr);
    const offsetDate = new Date(
        date.getTime() - date.getTimezoneOffset() * 60000
    );
    return offsetDate.toISOString().replace(/[-:]/g, "").split(".")[0];
}

// メッセージを追加する関数
function appendMessage(outputId, message) {
    const messagesDiv = document.getElementById(outputId);
    const messageElement = document.createElement("p");
    messageElement.textContent = message;
    messagesDiv.appendChild(messageElement);
}

// 送信ボタンのイベントハンドラを設定
function setupSendButtonHandler(buttonId, inputId, outputId, type, ws) {
    const sendButton = document.getElementById(buttonId);
    sendButton.addEventListener("click", () => {
        const messageInput = document.getElementById(inputId);
        const message = messageInput.value.trim();
        if (message) {
            ws.send(JSON.stringify({ type, message }));
            appendMessage(outputId, `クライアント(${type}): ${message}`);
            messageInput.value = "";
        }
    });
}

export {
    splitTextByBreakeLine,
    formatDateForInput,
    formatDate,
    appendMessage,
    setupSendButtonHandler,
};
