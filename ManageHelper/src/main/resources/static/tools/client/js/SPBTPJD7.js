import componentModule from "./common/componentModule.js";
import csvModule from "./common/csvModule.js";
import { setDragAndDrop } from "./common/eventUtils.js";

let tasks = [];

let startDate;
const totalDays = 60; // 表示する日数の範囲

// 画面表示時
document.addEventListener("DOMContentLoaded", initialize);

// 初期化処理
function initialize() {
    setDragAndDrop("#drop-area", ([header, ...data]) => {
        tasks = createTaskData(data);
        createTaskGrid(totalDays, tasks);
    });
    componentModule.addEventListenerById("#drop-area", "paste", (event) => {
        const data = csvModule.parseCSV(event.clipboardData.getData("text"));
        tasks = createTaskData(data);
        createTaskGrid(totalDays, tasks);
    });
    componentModule.addEventListenerById(
        "#start-date-input",
        "change",
        (event) => {
            startDate = new Date(event.target.value);
            createTaskHeader(totalDays);
            createTaskGrid(totalDays, tasks);
        }
    );

    const startInputDate = document.querySelector("#start-date-input");
    startInputDate.value = getInitialDate();
    startDate = new Date(startInputDate.value);
    createTaskHeader(totalDays);
    createTaskGrid(totalDays, tasks);
}

// タスクデータ作成
function createTaskData(data) {
    return data.map((row) => {
        return {
            name: row[0],
            start: row[1],
            end: row[2],
            progressRate: row[3],
        };
    });
}

// ヘッダー行を生成
function createHeaderRow(columns, className) {
    const row = componentModule.createElement("div", { className });
    columns.forEach((colText) => {
        row.appendChild(
            componentModule.createElement("div", { textContent: colText })
        );
    });
    return row;
}

// 年・月・日のヘッダーを作成
function createTaskHeader(totalDays) {
    const headerElement = document.getElementById("header");
    headerElement.innerHTML = "";

    const className = "grid-row grid-row-header";
    const yearRow = createHeaderRow(["", "", ""], className);
    const monthRow = createHeaderRow(["", "", ""], className);
    const dayRow = createHeaderRow(
        ["Task Name", "Start Date", "End Date"],
        className
    );
    headerElement.appendChild(yearRow);
    headerElement.appendChild(monthRow);
    headerElement.appendChild(dayRow);

    let currentYear = null;
    let yearSpan = 0;
    let currentMonth = null;
    let monthSpan = 0;

    for (let i = 0; i < totalDays; i++) {
        const targetDate = new Date(startDate);
        targetDate.setDate(targetDate.getDate() + i);

        // 年
        if (currentYear !== targetDate.getFullYear()) {
            if (currentYear !== null) {
                const yearCell = componentModule.createElement("div", {
                    textContent: currentYear,
                });
                yearCell.style.gridColumn = `span ${yearSpan}`;
                yearRow.appendChild(yearCell);
            }
            currentYear = targetDate.getFullYear();
            yearSpan = 1;
        } else {
            yearSpan++;
        }

        // 月
        if (currentMonth !== targetDate.getMonth()) {
            if (currentMonth !== null) {
                const monthCell = componentModule.createElement("div", {
                    textContent: currentMonth + 1,
                });
                monthCell.style.gridColumn = `span ${monthSpan}`;
                monthRow.appendChild(monthCell);
            }
            currentMonth = targetDate.getMonth();
            monthSpan = 1;
        } else {
            monthSpan++;
        }

        // 日
        const dayCell = componentModule.createElement("div", {
            textContent: targetDate.getDate(),
        });
        dayRow.appendChild(dayCell);
    }

    // 最後のセルを追加
    const finalYearCell = componentModule.createElement("div", {
        textContent: currentYear,
    });
    finalYearCell.style.gridColumn = `span ${yearSpan}`;
    yearRow.appendChild(finalYearCell);

    const finalMonthCell = componentModule.createElement("div", {
        textContent: currentMonth + 1,
    });
    finalMonthCell.style.gridColumn = `span ${monthSpan}`;
    monthRow.appendChild(finalMonthCell);
}

// グリッド内容を生成
function createTaskGrid(totalDays, tasks) {
    const gridElement = document.getElementById("grid");
    gridElement.innerHTML = "";

    tasks.forEach((task, index) => {
        const row = componentModule.createElement("div", {
            className: "grid-row",
        });
        row.style.gridRow = `${index + 1}`;
        gridElement.appendChild(row);

        // タスク情報（名前、開始日、終了日）
        ["name", "start", "end"].forEach((key) => {
            row.appendChild(
                componentModule.createElement("div", {
                    className: key,
                    textContent: task[key],
                })
            );
        });
        const cellLeft = Math.floor(
            row.lastChild.offsetLeft + row.lastChild.clientWidth
        );

        // 各日のセル
        for (let i = 0; i < totalDays; i++) {
            row.appendChild(
                componentModule.createElement("div", { className: "cell" })
            );
        }
        const cellWidth = Math.floor(row.lastChild.clientWidth);

        // タスクバーを追加
        row.appendChild(createTaskBar(task, cellWidth, cellLeft));
    });
}

// タスクバーを作成
function createTaskBar(task, cellWidth, cellLeft) {
    const taskStart = new Date(task.start);
    const taskEnd = new Date(task.end);
    const startOffset = Math.max(
        0,
        Math.floor((taskStart - startDate) / (1000 * 60 * 60 * 24))
    );
    const duration = Math.max(
        1,
        Math.floor((taskEnd - taskStart) / (1000 * 60 * 60 * 24)) + 1
    );

    const bar = componentModule.createElement("div", { className: "bar" });
    bar.style.width = `${(cellWidth + 1) * duration + 1}px`;
    bar.style.left = `${cellLeft + startOffset * (cellWidth + 1)}px`;
    bar.draggable = "true";
    return bar;
}

// 初期日付を取得
function getInitialDate() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, "0"); // 月を2桁に
    return `${year}-${month}-01`;
}
