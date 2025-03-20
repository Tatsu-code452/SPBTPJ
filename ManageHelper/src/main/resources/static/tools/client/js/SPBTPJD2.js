import {
    loadCsv,
    setDragAndDrop,
    splitTextByBreakeLine,
    createHeader,
    createBody,
} from "./common.js";

document.addEventListener("DOMContentLoaded", initialize);

// 初期化処理
function initialize() {
    // ファイルドロップ設定
    setDragAndDrop(document.querySelector("#drop-area"), createTable);
    // CSV読み込み
    loadCsv("./data/parse.csv", createTable);

    // テキストエリア入力設定
    const textArea = document.querySelector("#textArea");
    textArea.addEventListener("input", () => splitTextArea(textArea));
}

// テーブル作成
function createTable(data) {
    const table = document.createElement("table");
    table.id = "parsedTable";
    table.appendChild(createHeader(["項目名", "桁数"]));
    table.appendChild(createBody(data));
    const tableWrapper = document.querySelector("#tableWrapper");
    tableWrapper.replaceChildren(table);

    const textArea = document.querySelector("#textArea");
    if (textArea.value) {
        splitTextArea(textArea);
    }
}

// テキストエリア分割
function splitTextArea(textArea) {
    const textRows = splitTextByBreakeLine(textArea.value);
    const parsedTable = document.querySelector("#parsedTable");
    const headerRow = parsedTable.rows[0];
    textRows.forEach((text, textRowIndex) => {
        if (headerRow.cells.length <= textRowIndex + 2) {
            const th = document.createElement("th");
            th.textContent = parseInt(textRowIndex) + 1 + "行目";
            headerRow.appendChild(th);
        }
    });

    let beforePos = 0;
    for (let rowIndex = 0; rowIndex < parsedTable.rows.length; rowIndex++) {
        const row = parsedTable.rows[rowIndex];
        const length = parseInt(row.cells[1].textContent, 10);
        if (!isNaN(length)) {
            textRows.forEach((text, textRowIndex) =>
                splitText(
                    row,
                    text,
                    textRowIndex,
                    beforePos,
                    beforePos + length
                )
            );
            beforePos += length;
        }
    }
}

// テキスト分割
function splitText(tableRow, text, textRowIndex, startPos, endPos) {
    const cellIndex = textRowIndex + 2;
    const slicedText = text.slice(startPos, endPos);
    const isError = text.length < endPos;

    updateTableCell(tableRow, cellIndex, slicedText, isError);
}

// テーブルセル更新
function updateTableCell(tableRow, cellIndex, text, isError) {
    if (tableRow.cells.length <= cellIndex) {
        const td = document.createElement("td");
        td.textContent = text;
        tableRow.appendChild(td);
    } else {
        tableRow.cells[cellIndex].textContent = text;
    }
    tableRow.cells[cellIndex].classList.toggle("error", isError);
}
