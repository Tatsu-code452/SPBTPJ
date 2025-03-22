const svgNamespace = "http://www.w3.org/2000/svg";

// CSV読み込み
function loadCsv(url, callback) {
    fetch(url)
        .then((response) => response.text())
        .then((text) => callback(parseCSV(text)))
        .catch((error) => console.error("Error loading CSV:", error));
}

// CSV解析
function parseCSV(text) {
    return text
        .replace(/"/g, "")
        .split(/\r?\n/)
        .map((row) => row.split(","));
}

// ドラッグアンドドロップ設定
function setDragAndDrop(dropArea, callback) {
    ["dragover", "dragleave", "drop"].forEach((eventType) => {
        dropArea.addEventListener(eventType, (event) =>
            handleDragEvent(event, callback)
        );
    });
}

// ドラッグイベント処理
function handleDragEvent(event, callback) {
    event.preventDefault();
    if (event.type === "dragover") {
        toggleHighlight(event, true);
    } else if (event.type === "dragleave") {
        toggleHighlight(event, false);
    } else if (event.type === "drop") {
        toggleHighlight(event, false);
        processDrop(event, callback);
    }
}

// ドロップ処理
function processDrop(event, callback) {
    const file = event.dataTransfer.files[0];
    if (file?.type === "text/csv") {
        readCsvFile(file, callback);
    } else {
        alert("Invalid file type.");
    }
}

// CSVファイル読み込み
function readCsvFile(file, callback) {
    const reader = new FileReader();
    reader.onload = (e) => callback(parseCSV(e.target.result));
    reader.readAsText(file);
}

// ハイライト切り替え
function toggleHighlight(event, highlight) {
    event.preventDefault();
    event.currentTarget.classList.toggle("highlight", highlight);
}

// テキスト改行分割
function splitTextByBreakeLine(text) {
    return text.split(/\r?\n/);
}

// 入力セル作成
function createInputCell(type, value = "") {
    const cell = document.createElement("td");
    const input = document.createElement("input");
    input.type = type;
    input.value = value;
    cell.appendChild(input);
    return cell;
}

// ヘッダー行作成
function createHeader(headers) {
    const tableHeader = document.createElement("thead");
    const headerRow = document.createElement("tr");
    headers.forEach((header) => {
        const th = document.createElement("th");
        th.textContent = header;
        headerRow.appendChild(th);
    });
    tableHeader.appendChild(headerRow);
    return tableHeader;
}

// ボディー行作成
function createBody(data) {
    const tableBody = document.createElement("tbody");
    data.forEach((row) => {
        const tr = document.createElement("tr");
        row.forEach((col) => {
            const td = document.createElement("td");
            td.textContent = col;
            tr.appendChild(td);
        });
        tableBody.appendChild(tr);
    });
    return tableBody;
}

// SVG作成
function createSvg() {
    return document.createElementNS(svgNamespace, "svg");
}

// SVGリサイズ
function resizeSvg(svg, width, height, viewBox) {
    Object.entries({ width, height, viewBox }).forEach(([key, value]) =>
        svg.setAttribute(key, value)
    );
}

// SVGグループ作成
function createSvgGroup(id, x, y, width, height, color) {
    const rectGroup = document.createElementNS(svgNamespace, "g");
    rectGroup.appendChild(createSvgRect(id, x, y, width, height, color));
    rectGroup.appendChild(
        createSvgText(rectGroup.firstChild, id, x, y, width, height)
    );
    return rectGroup;
}

// SVG矩形作成
function createSvgRect(id, x, y, width, height, color) {
    const rect = document.createElementNS(svgNamespace, "rect");
    Object.entries({
        id,
        x,
        y,
        width,
        height,
        fill: color,
        stroke: "black",
        "stroke-width": "1",
    }).forEach(([key, value]) => rect.setAttribute(key, value));
    rect.addEventListener("click", () => toggleSelection(rect));
    return rect;
}

// SVGテキスト作成
function createSvgText(rect, textContent, x, y, width, height) {
    const text = document.createElementNS(svgNamespace, "text");
    text.setAttribute("x", parseFloat(x) + parseFloat(width) / 2);
    text.setAttribute("y", parseFloat(y) + parseFloat(height) / 2);
    text.setAttribute("dominant-baseline", "middle");
    text.setAttribute("text-anchor", "middle");
    text.textContent = textContent;
    text.addEventListener("click", () => toggleSelection(rect));
    return text;
}

// 選択切り替え
function toggleSelection(rect) {
    rect.classList.toggle("selected");
    if (rect.classList.contains("selected")) {
        rect.dataset.selectionOrder = Date.now();
    }
    updateSelectionDisplay();
}

// 選択表示更新
function updateSelectionDisplay() {
    const selectedRects = [...document.querySelectorAll(".selected")].sort(
        (a, b) => a.dataset.selectionOrder - b.dataset.selectionOrder
    );
    document.querySelector("#svgSelect").value = selectedRects
        .map((rect) => rect.id)
        .join(",");
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

// 初期化処理を共通化
function initializeDragAndDrop(dropAreaSelector, callback) {
    const dropArea = document.querySelector(dropAreaSelector);
    if (dropArea) {
        setDragAndDrop(dropArea, callback);
    }
}

function initializeButton(buttonSelector, callback) {
    const button = document.querySelector(buttonSelector);
    if (button) {
        button.addEventListener("click", callback);
    }
}

function initializeTextArea(textAreaSelector, callback) {
    const textArea = document.querySelector(textAreaSelector);
    if (textArea) {
        textArea.addEventListener("input", callback);
    }
}

export {
    loadCsv,
    setDragAndDrop,
    splitTextByBreakeLine,
    parseCSV,
    createInputCell,
    createHeader,
    createBody,
    createSvg,
    resizeSvg,
    createSvgGroup,
    createSvgRect,
    createSvgText,
    toggleSelection,
    updateSelectionDisplay,
    formatDateForInput,
    formatDate,
    appendMessage,
    setupSendButtonHandler,
    initializeDragAndDrop,
    initializeButton,
    initializeTextArea,
};
