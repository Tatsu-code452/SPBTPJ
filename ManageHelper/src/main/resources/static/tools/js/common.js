// CSV読み込み
function loadCsv(url, callback) {
    fetch(url)
        .then((response) => response.text())
        .then(callback)
        .catch((error) => console.error("Error loading CSV:", error));
}

// CSV解析
function parseCSV(data) {
    return data
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
        reader.onload = (e) => callback(e.target.result);
        reader.readAsText(file);
    } else {
        alert("Invalid file type.");
    }
}

// テキスト改行分割
function splitTextByBreakeLine(text) {
    return text.split(/\r?\n/);
}

export { loadCsv, setDragAndDrop, splitTextByBreakeLine, parseCSV };
