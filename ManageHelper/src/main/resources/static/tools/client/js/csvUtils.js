// CSV読み込み
async function loadCsv(url, callback) {
    try {
        const response = await fetch(url);
        if (!response.ok)
            throw new Error(`HTTP error! status: ${response.status}`);
        const text = await response.text();
        callback(parseCSV(text));
    } catch (error) {
        console.error("Error loading CSV:", error);
    }
}

// CSV解析
function parseCSV(text) {
    return text
        .replace(/"/g, "")
        .split(/\r?\n/)
        .map((row) => row.split(","));
}

// ファイル読み込み共通処理
function readFile(file, callback, type = "text") {
    const reader = new FileReader();
    reader.onload = (e) =>
        callback(type === "text" ? parseCSV(e.target.result) : e.target.result);
    reader.readAsText(file);
}

export { loadCsv, parseCSV, readFile };
