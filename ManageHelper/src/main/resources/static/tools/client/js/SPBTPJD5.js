import csvModule from "./common/csvModule.js";
import tableModule from "./common/tableModule.js";
import eventModule from "./common/eventModule.js";

// CSVデータを保持するグローバル変数
let csvHeader = [];
let csvData = [];
let selectIdx = new Array();

// 画面表示時
document.addEventListener("DOMContentLoaded", initialize);

// 初期化処理
function initialize() {
    csvModule.loadCsv("./data/table.csv", ([header, ...data]) => {
        csvHeader = header;
        csvData = data;
        renderTable();
    });
}

// テーブルのレンダリング
function renderTable() {
    const table = document.createElement("table");
    table.id = "parsedTable";

    // ヘッダー生成＆イベント登録
    const headerElem = tableModule.createHeader(csvHeader);
    attachHeaderEvents(headerElem);
    addCheckboxesToHeaders(headerElem);
    table.appendChild(headerElem);

    // ボディ生成（初回はすべての行を表示）
    table.appendChild(tableModule.createBody(csvData));

    const tableWrapper = document.querySelector("#tableWrapper");
    tableWrapper.replaceChildren(table);
}

// ヘッダーにイベントを登録
function attachHeaderEvents(headerElem) {
    const headerCells = headerElem.querySelectorAll("th");
    headerCells.forEach((th) => {
        th.style.cursor = "pointer";
        eventModule.setEvent(th, "click", () => handleHeaderClick());
    });
}

// ヘッダークリック時の処理
function handleHeaderClick() {
    selectIdx = getCheckedHeaderIndices();
    updateGroupCondition();

    const tableBody = tableModule.createBody(groupTableByColumn());
    const table = document.getElementById("parsedTable");
    table.replaceChild(tableBody, table.querySelector("tbody"));

    if (selectIdx.length) {
        csvHeader.forEach((_, i) => {
            if (!selectIdx.includes(i) && i < csvHeader.length - 1) {
                hideColumn(i);
            }
        });
    } else {
        initialize();
    }
}

// グループ条件を更新
function updateGroupCondition() {
    const groupCondition = selectIdx.map((i) => csvHeader[i]).join(", ");
    document.querySelector("#groupCondition").textContent = groupCondition;
}

// テーブルの列でグループ化
function groupTableByColumn() {
    let groupedData = new Map();

    csvData.forEach((row) => {
        const key = selectIdx.map((i) => row[i]).join(", ");
        if (!groupedData.has(key)) {
            groupedData.set(key, [...row]);
        } else {
            const lastColumn = row.length - 1;
            const targetCellValue = parseInt(groupedData.get(key)[lastColumn]);
            groupedData.get(key)[lastColumn] =
                targetCellValue + parseInt(row[lastColumn], 10);
        }
    });

    return Array.from(groupedData.values());
}

// チェックされたヘッダーインデックスを取得
function getCheckedHeaderIndices() {
    return Array.from(document.querySelectorAll("th input[type='checkbox']"))
        .map((checkbox, idx) => (checkbox.checked ? idx : -1))
        .filter((idx) => idx !== -1);
}

// 列を非表示にする
function hideColumn(columnIndex) {
    document
        .querySelectorAll(
            `#parsedTable th:nth-child(${columnIndex + 1}),
                               #parsedTable td:nth-child(${columnIndex + 1})`
        )
        .forEach((cell) => (cell.style.display = "none"));
}

// ヘッダーにチェックボックスを追加
function addCheckboxesToHeaders(headerElem) {
    headerElem.querySelectorAll("th").forEach((th, colIndex) => {
        if (colIndex === csvHeader.length - 1) return; // 最後の列はスキップ
        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.checked = true;
        th.appendChild(checkbox);
    });
}
