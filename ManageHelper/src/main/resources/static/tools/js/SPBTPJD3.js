import {
    loadCsv,
    setDragAndDrop,
    splitTextByBreakeLine,
    parseCSV,
} from "./common.js";

// 画面表示時
document.addEventListener("DOMContentLoaded", () => {
    // ファイルドロップ設定
    setDragAndDrop(document.querySelector("#drop-area"), displayCalendarTable);
    // CSV読み込み
    loadCsv("./data/calendar.csv", displayCalendarTable);

    document
        .querySelector("#download-ical")
        .addEventListener("click", () => downloadICal());
});

function displayCalendarTable(data) {
    const rows = parseCSV(data);

    const table = document.createElement("table");
    table.id = "calendarTable";
    table.appendChild(createHeader(rows.shift()));
    table.appendChild(createBody(rows));

    const tableWrapper = document.querySelector("#parseWrapper");
    tableWrapper.replaceChildren(table);
}

// ヘッダー行作成
function createHeader(headers) {
    const thead = document.createElement("thead");
    const header = document.createElement("tr");
    thead.appendChild(header);

    headers.forEach((headerCell) => {
        const th = document.createElement("th");
        th.textContent = headerCell;
        header.appendChild(th);
    });

    return thead;
}

// ボディー行作成
function createBody(rows) {
    const tbody = document.createElement("tbody");
    rows.forEach((row) => {
        const tr = document.createElement("tr");
        row.forEach((cell) => {
            const td = document.createElement("td");
            td.textContent = cell;
            tr.appendChild(td);
        });
        tbody.appendChild(tr);
    });

    return tbody;
}

// iCal形式でダウンロード
function downloadICal(ical) {
    const table = document.querySelector("#calendarTable");
    if (!table) return;

    const rows = Array.from(table.querySelectorAll("tr"));
    const headers = rows.shift().querySelectorAll("th");
    const events = rows.map((row) => {
        const cells = row.querySelectorAll("td");
        let event = {};
        headers.forEach((header, index) => {
            event[header.textContent] = cells[index].textContent;
        });
        return event;
    });

    const ical = createICal(events);
    downloadICal(ical);
}

// iCal作成
function createICal(events) {
    let ical =
        "BEGIN:VCALENDAR\nVERSION:2.0\nPRODID:-//Your Organization//Your Product//EN\n";
    events.forEach((event) => {
        ical += "BEGIN:VEVENT\n";
        ical += `SUMMARY:${event.Summary}\n`;
        ical += `DTSTART:${formatDate(event.StartDate)}\n`;
        ical += `DTEND:${formatDate(event.EndDate)}\n`;
        ical += `DESCRIPTION:${event.Description}\n`;
        ical += "END:VEVENT\n";
    });
    ical += "END:VCALENDAR";
    return ical;
}

// 日付フォーマット
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toISOString().replace(/[-:]/g, "").split(".")[0];
}

// iCalダウンロード
function downloadICal(ical) {
    const blob = new Blob([ical], { type: "text/calendar" });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = "calendar.ics";
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
}
