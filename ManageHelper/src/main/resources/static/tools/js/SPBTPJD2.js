import { loadCsv, setDragAndDrop, splitTextByBreakeLine } from "./common.js";

document.addEventListener("DOMContentLoaded", function () {
    // ファイルドロップ設定
    setDragAndDrop(document.querySelector("#drop-area"), parseCSV);
    // CSV読み込み
    loadCsv("./data/parse.csv", parseCSV);

    const textArea = document.querySelector("#textArea");
    // テキストエリア入力設定
    textArea.addEventListener("input", () => splitTextArea(textArea));
});

// CSV解析
function parseCSV(text) {
    const table = document.createElement("table");
    table.id = "parsedTable";
    table.appendChild(createHeader());
    table.appendChild(createBody(text));
    const tableWrapper = document.querySelector("#tableWrapper");
    tableWrapper.replaceChildren(table);

    const textArea = document.querySelector("#textArea");
    if (textArea.value) {
        splitTextArea(textArea);
    }
}

// ヘッダー行作成
function createHeader() {
    // ヘッダー行を追加
    const tableHeader = document.createElement("thead");
    const headerRow = document.createElement("tr");
    const headers = ["項目名", "桁数"];
    headers.forEach((header) => {
        const th = document.createElement("th");
        th.textContent = header;
        headerRow.appendChild(th);
    });
    tableHeader.appendChild(headerRow);
    return tableHeader;
}

// ボディー行作成
function createBody(text) {
    const tableBody = document.createElement("tbody");
    const rows = splitTextByBreakeLine(text);
    rows.forEach((row) => {
        const cols = row.split(",");
        const tr = document.createElement("tr");
        cols.forEach((col) => {
            const td = document.createElement("td");
            td.textContent = col;
            tr.appendChild(td);
        });
        tableBody.appendChild(tr);
    });
    return tableBody;
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
