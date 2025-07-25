import { splitTextByBreakeLine } from "./common/common.js";
import csvModule from "./common/csvModule.js";
import eventModule from "./common/eventModule.js";
import tableModule from "./common/tableModule.js";

document.addEventListener("DOMContentLoaded", initialize);

// 初期化処理
function initialize() {
    eventModule.setDragAndDrop("#drop-area", createTable);
    eventModule.setEvent("#textArea", "input", () =>
        splitTextArea(document.querySelector("#textArea"))
    );
    eventModule.setEvent("#loadFileButton", "click", handleLoadFileClick);
    csvModule.loadCsv("./data/parser/parse.csv", createTable);
    fetchFileList();
}

// テーブル作成
function createTable(data) {
    const table = document.createElement("table");
    table.id = "parsedTable";
    table.appendChild(tableModule.createHeader(["項目名", "桁数"]));
    table.appendChild(tableModule.createBody(data));
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

// Fetch the list of files from /data/parser
async function fetchFileList() {
    try {
        const response = await fetch("./data/parser/");
        if (!response.ok) {
            console.error(
                `Failed to fetch file list. Status: ${response.status}`
            );
            throw new Error("Failed to fetch file list");
        }
        const csvData = await response.text();
        const files = csvModule.parseCSV(csvData);
        const fileSelector = document.getElementById("fileSelector");
        fileSelector.innerHTML = files[0]
            .map((file) => {
                const fileName = file.replace(/\.[^/.]+$/, ""); // Remove extension
                return `<option value="${file}">${fileName}</option>`;
            })
            .join("");
    } catch (error) {
        console.error("Error fetching file list:", error.message); // エラー詳細を出力
    }
}

// Handle load file button click
async function handleLoadFileClick() {
    const fileSelector = document.getElementById("fileSelector");
    const selectedFile = fileSelector.value;
    if (!selectedFile) return alert("Please select a file");
    try {
        csvModule.loadCsv(`/data/parser/${selectedFile}`, createTable);
    } catch (error) {
        console.error("Error loading file:", error);
    }
}
