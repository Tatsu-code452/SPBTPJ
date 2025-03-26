import { loadCsv } from "./common/csvUtils.js";
import { createHeader, createBody } from "./common/tableUtils.js";
import { setClickEvent } from "./common/eventUtils.js";

// CSVデータを保持するグローバル変数
let csvHeader = [];
let csvData = [];
let selectIdx = new Array();

// 画面表示時
document.addEventListener("DOMContentLoaded", initialize);

// 初期化処理
function initialize() {
    loadCsv("./data/dataDictionary.csv", ([header, ...data]) => {
        csvHeader = header;
        csvData = data;
        renderTable();
    });
}

// テーブルのレンダリング
function renderTable() {
    const table = document.createElement("table");
    table.id = "parsedTable";

    // ヘッダー生成
    const headerElem = createHeader(csvHeader);
    table.appendChild(headerElem);

    // ボディ生成
    table.appendChild(createBody(csvData));

    const tableWrapper = document.querySelector("#tableWrapper");
    tableWrapper.replaceChildren(table);
}

