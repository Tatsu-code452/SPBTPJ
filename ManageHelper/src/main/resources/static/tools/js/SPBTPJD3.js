// 画面表示時
document.addEventListener("DOMContentLoaded", initialize);

// 初期化処理
function initialize() {
    templateRow();
    setTaskTableEvent();
    setDownloadButton();
}

// テンプレート行作成
function templateRow() {
    const newRow = document.createElement("tr");
    newRow.appendChild(createInputCell("text"));
    newRow.appendChild(createInputCell("datetime-local"));
    newRow.appendChild(createInputCell("datetime-local"));
    document.querySelector("#task-table tbody").appendChild(newRow);
    newRow.querySelector("input").focus();
}

// タスクテーブルのイベント設定
function setTaskTableEvent() {
    const taskTable = document.querySelector("#task-table");
    taskTable.addEventListener("keydown", handleKeyDown);
    taskTable.addEventListener("focusout", handleFocusOut);
}

// 入力セル作成
function createInputCell(value) {
    const cell = document.createElement("td");
    const input = document.createElement("input");
    input.type = value;
    cell.appendChild(input);
    return cell;
}

// ダウンロードボタン設定
function setDownloadButton() {
    document
        .querySelector("#download-ical")
        .addEventListener("click", handleDownloadClick);
}

// キーダウン時の処理
function handleKeyDown(event) {
    if (event.key === "Enter") {
        event.preventDefault();
        templateRow();
    }
}

// フォーカスアウト時の処理
function handleFocusOut(event) {
    const row = event.target.closest("tr");
    if (row && row.querySelectorAll("input").length > 0) {
        const inputs = row.querySelectorAll("input");
        let isEmpty = inputs[0].value.trim() === "";
        let isExistMultiRow =
            document.querySelector("#task-table tbody").rows.length > 1;
        if (isEmpty && isExistMultiRow) {
            row.remove();
        }
    }
}

// ダウンロードボタンクリック時の処理
function handleDownloadClick() {
    const tasks = getTasksFromTable();
    const ical = createICal(tasks);
    downloadICal(ical);
}

// テーブルからタスクを取得
function getTasksFromTable() {
    const tasks = [];
    const rows = document.querySelector("#task-table tbody").rows;
    for (let i = 0; i < rows.length; i++) {
        const cells = rows[i].cells;
        tasks.push({
            name: cells[0].firstChild.value,
            start: cells[1].firstChild.value,
            end: cells[2].firstChild.value,
        });
    }
    return tasks;
}

// iCalをダウンロード
function downloadICal(ical) {
    const blob = new Blob([ical], { type: "text/calendar" });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = "tasks.ics";
    a.click();
    URL.revokeObjectURL(url);
}

// iCal作成
function createICal(tasks) {
    let ical =
        "BEGIN:VCALENDAR\nVERSION:2.0\nPRODID:-//Your Organization//Your Product//EN\n";
    tasks.forEach((task, rowIndex) => {
        ical += "BEGIN:VEVENT\n";
        ical += `UID:${rowIndex + 1}\n`;
        ical += `SUMMARY:${task.name}\n`;
        ical += `DTSTART:${formatDate(task.start)}\n`;
        ical += `DTEND:${formatDate(task.end)}\n`;
        ical += "END:VEVENT\n";
    });
    ical += "END:VCALENDAR";
    return ical;
}

// 日付フォーマット
function formatDate(dateStr) {
    const date = new Date(dateStr);
    return date.toISOString().replace(/[-:]/g, "").split(".")[0];
}
