// ヘッダー行作成
function createHeader(headers) {
    const tableHeader = document.createElement("thead");
    const headerRow = document.createElement("tr");
    headers.forEach((header) => {
        const th = document.createElement("th");
        th.textContent = header;
        headerRow.appendChild(th);
    });
    tableHeader.appendChild(headerRow);
    return tableHeader;
}

// ボディー行作成
function createBody(data) {
    const tableBody = document.createElement("tbody");
    data.forEach((row) => {
        const tr = document.createElement("tr");
        row.forEach((col) => {
            const td = document.createElement("td");
            td.textContent = col;
            tr.appendChild(td);
        });
        tableBody.appendChild(tr);
    });
    return tableBody;
}

// 入力セル作成
function createInputCell(type, value = "") {
    const cell = document.createElement("td");
    const input = document.createElement("input");
    input.type = type;
    input.value = value;
    cell.appendChild(input);
    return cell;
}

export { createInputCell, createHeader, createBody };
