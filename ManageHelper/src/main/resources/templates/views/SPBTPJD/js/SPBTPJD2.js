import { loadCsv, setDragAndDrop } from "./common.js";

document.addEventListener("DOMContentLoaded", function () {
    // ファイルドロップ設定
    setDragAndDrop(document.querySelector("#drop-area"), parseCSV);
    // CSV読み込み
    loadCsv("./data/parse.csv", parseCSV);

    const textArea = document.getElementById("textArea");
    // テキストエリア入力設定
    textArea.addEventListener("input", () => setInputTextArea(textArea));
});

// テキストエリア入力設定
function setInputTextArea(textArea) {
    const textRows = textArea.value.split("\n");
    const parsedTable = document.querySelector("#parsedTable");

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
    if (text.length >= endPos) {
        if (tableRow.cells.length <= textRowIndex + 2) {
            const td = document.createElement("td");
            td.textContent = text.slice(startPos, endPos);
            tableRow.appendChild(td);
        } else {
            tableRow.cells[textRowIndex + 2].textContent = text.slice(
                startPos,
                endPos
            );
        }
    }
}

// CSV表示
function parseCSV(text) {
    const rows = text.split("\r\n");
    const table = document.createElement("table");
    table.id = "parsedTable";
    rows.forEach((row) => {
        const cols = row.split(",");
        const tr = document.createElement("tr");
        cols.forEach((col) => {
            const td = document.createElement("td");
            td.textContent = col;
            tr.appendChild(td);
        });
        table.appendChild(tr);
    });
    const parseWrapper = document.querySelector("#parseWrapper");
    parseWrapper.replaceChildren(table);
}
