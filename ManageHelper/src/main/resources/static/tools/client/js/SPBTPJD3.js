import { formatDateForInput, formatDate } from "./common/common.js";
import { parseCSV } from "./common/csvUtils.js";
import {
    setDragAndDrop,
    setClickEvent,
} from "./common/eventUtils.js";

import { createInputCell } from "./common/tableUtils.js";

// 画面表示時
document.addEventListener("DOMContentLoaded", initialize);

// 初期化処理
function initialize() {
    setDragAndDrop("#drop-area", handleCsvDrop);
    setClickEvent("#download-ical", handleDownloadClick);
    setTaskTableEvent();
    document
        .querySelector("#task-table")
        .addEventListener("paste", handlePaste);
}

// CSVドロップ時の処理
function handleCsvDrop(data) {
    const tasks = data.map((row) => {
        const cols = row;
        return {
            name: cols[0], // タスク名
            start: cols[1], // 開始日時
            end: cols[2], // 終了日時
        };
    });

    const tbody = document.querySelector("#task-table tbody");
    tbody.innerHTML = ""; // 既存の行をクリア
    tasks.forEach((task) => {
        const newRow = document.createElement("tr");
        newRow.appendChild(createInputCell("text", task.name));
        newRow.appendChild(
            createInputCell("datetime-local", formatDateForInput(task.start))
        );
        newRow.appendChild(
            createInputCell("datetime-local", formatDateForInput(task.end))
        );
        tbody.appendChild(newRow);
    });
}

// テンプレート行作成
function templateRow() {
    const newRow = document.createElement("tr");
    newRow.appendChild(createInputCell("text"));
    newRow.appendChild(createInputCell("datetime-local"));
    newRow.appendChild(createInputCell("datetime-local"));
    document.querySelector("#task-table tbody").appendChild(newRow);
    newRow.querySelector("input").focus(); // 新しい行の最初の入力にフォーカス
}

// タスクテーブルのイベント設定
function setTaskTableEvent() {
    const taskTable = document.querySelector("#task-table");
    taskTable.addEventListener("keydown", handleKeyDown);
    taskTable.addEventListener("focusout", handleFocusOut);
}

// キーダウン時の処理
function handleKeyDown(event) {
    if (event.key === "Enter") {
        event.preventDefault();
        templateRow(); // Enterキーで新しい行を追加
    }
}

// フォーカスアウト時の処理
function handleFocusOut(event) {
    const row = event.target.closest("tr");
    if (row && row.querySelectorAll("input").length > 0) {
        const inputs = row.querySelectorAll("input");
        let isEmpty = inputs[0].value.trim() === ""; // タスク名が空かどうか
        let isExistMultiRow =
            document.querySelector("#task-table tbody").rows.length > 1; // 複数行が存在するかどうか
        if (isEmpty && isExistMultiRow) {
            row.remove(); // タスク名が空で複数行が存在する場合、行を削除
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
    const rows = Array.from(document.querySelector("#task-table tbody").rows);
    return rows.map((row) => {
        const cells = row.cells;
        return {
            name: cells[0].firstChild.value, // タスク名
            start: cells[1].firstChild.value, // 開始日時
            end: cells[2].firstChild.value, // 終了日時
        };
    });
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
    const events = tasks
        .map((task, rowIndex) => {
            return [
                "BEGIN:VEVENT",
                `UID:${rowIndex + 1}`, // 一意の識別子
                `SUMMARY:${task.name}`, // タスク名
                `DTSTART:${formatDate(task.start)}`, // 開始日時
                `DTEND:${formatDate(task.end)}`, // 終了日時
                "END:VEVENT",
            ].join("\n");
        })
        .join("\n");

    return [
        "BEGIN:VCALENDAR",
        "VERSION:2.0",
        "PRODID:-//Your Organization//Your Product//EN",
        events,
        "END:VCALENDAR",
    ].join("\n");
}

function handlePaste(event) {
    event.preventDefault(); // デフォルトのペースト動作を防ぐ
    const rows = parseCSV(event.clipboardData.getData("text"));
    const tbody = document.querySelector("#task-table tbody");

    rows.forEach((cols) => {
        if (cols.length >= 3) {
            const newRow = document.createElement("tr");
            newRow.appendChild(createInputCell("text", cols[0])); // タスク名
            newRow.appendChild(
                createInputCell("datetime-local", formatDateForInput(cols[1]))
            ); // 開始日時
            newRow.appendChild(
                createInputCell("datetime-local", formatDateForInput(cols[2]))
            ); // 終了日時
            tbody.appendChild(newRow);
        }
    });
}
