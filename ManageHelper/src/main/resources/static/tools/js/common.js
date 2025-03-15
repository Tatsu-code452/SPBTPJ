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
    dropArea.addEventListener("dragover", (event) =>
        toggleHighlight(event, true)
    );
    dropArea.addEventListener("dragleave", (event) =>
        toggleHighlight(event, false)
    );
    dropArea.addEventListener("drop", (event) => handleDrop(event, callback));
}

// ハイライト切り替え
function toggleHighlight(event, highlight) {
    event.preventDefault();
    event.currentTarget.classList.toggle("highlight", highlight);
}

// ドロップ処理
function handleDrop(event, callback) {
    event.preventDefault();
    toggleHighlight(event, false);

    const file = event.dataTransfer.files[0];
    if (file?.type === "text/csv") {
        const reader = new FileReader();
        reader.onload = (e) => callback(parseCSV(e.target.result));
        reader.readAsText(file);
    } else {
        alert("Invalid file type.");
    }
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
    if (rect.classList.contains("selected")) {
        rect.classList.remove("selected");
    } else {
        rect.classList.add("selected");
        rect.dataset.selectionOrder = Date.now(); // 選択順を維持するためのタイムスタンプを追加
    }
    updateSelectionDisplay();
}

// 選択表示更新
function updateSelectionDisplay() {
    const selectedRects = Array.from(
        document.querySelectorAll(".selected")
    ).sort((a, b) => a.dataset.selectionOrder - b.dataset.selectionOrder); // 選択順にソート
    const selectedIds = selectedRects.map((rect) => rect.id);
    document.querySelector("#svgSelect").value = selectedIds.join(",");
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
    return date.toISOString().replace(/[-:]/g, "").split(".")[0];
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
};
