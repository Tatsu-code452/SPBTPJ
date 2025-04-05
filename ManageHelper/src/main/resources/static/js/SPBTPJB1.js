document.addEventListener('DOMContentLoaded', () => {
	let baseDate = document.querySelector("#baseDate");
	baseDate.addEventListener("change", function() {
		clearTable(document.querySelector("#ganttTable"));
		createHeader(document.querySelector("#baseDate").value, 100);
		createBody(document.querySelector("#baseDate").value, 100);
	});

	baseDate.value = formattedDate();
	baseDate.dispatchEvent(new Event('change'));
});

function formattedDate() {
	let today = new Date();
	let year = today.getFullYear();
	let month = ("0" + (today.getMonth() + 1)).slice(-2);
	return `${year}-${month}-01`;
}

function clearTable(table) {
	["thead", "tbody"].forEach(n => {
		let section = table.querySelector(n);
		while (section.firstChild) {
			section.removeChild(section.firstChild);
		}
	});
}

function createHeader(baseDate, span) {
	let ganttHeader = document.querySelector("#ganttTable > thead");
	let ganttHeaderMonth = ganttHeader.insertRow();
	let ganttHeaderDay = ganttHeader.insertRow();

	let calcDate = new Date(baseDate)
	let colSpan = 0;
	let beforeMonth = calcDate.getMonth() + 1;
	Array.from({ length: span }).forEach(() => {
		let cell = ganttHeaderDay.insertCell();
		cell.textContent = calcDate.getDate();
		if (beforeMonth !== calcDate.getMonth() + 1) {
			addMonthCell(ganttHeaderMonth, beforeMonth, colSpan);
			beforeMonth = calcDate.getMonth() + 1;
			colSpan = 1;
		} else {
			colSpan++;
		}
		calcDate.setDate(calcDate.getDate() + 1);
	});

	if (colSpan > 0) {
		addMonthCell(ganttHeaderMonth, beforeMonth, colSpan);
	}
}

function addMonthCell(row, month, colSpan) {
	let cell = row.insertCell();
	cell.textContent = month;
	cell.colSpan = colSpan;
	cell.classList.add("text-start");
}

function createBody(baseDate, span) {
	let taskBody = document.querySelector("#taskTable > tbody");
	let ganttBody = document.querySelector("#ganttTable > tbody");

	Array.from({ length: taskBody.rows.length }).forEach(() => {
		ganttBody.insertRow();
	});

	let calcDate = new Date(baseDate);
	Array.from({ length: span }).forEach(() => {
		for (let rowIdx = 0; rowIdx < taskBody.rows.length;  rowIdx++) {
			addGanttCell(ganttBody.rows[rowIdx], taskBody.rows[rowIdx], calcDate);
		}
		calcDate.setDate(calcDate.getDate() + 1);
	});
}

function addGanttCell(ganttBodyRow, taskBodyRow, calcDate) {
	let cell = ganttBodyRow.insertCell();
	let startDate = new Date(taskBodyRow.querySelector(".startDate").childNodes[1].value)
	let endDate = new Date(taskBodyRow.querySelector(".endDate").childNodes[1].value)
	if (startDate <= calcDate && calcDate <= endDate) {
		if (taskBodyRow.classList.contains("expectedRow")) {
			cell.classList.add("bg-primary");
		} else {
			cell.classList.add("bg-success");
		}
	}
}