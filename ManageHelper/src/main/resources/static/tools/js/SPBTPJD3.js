// ファイル入力のイベントリスナーを設定
document.getElementById('calendarFileInput').addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
        loadCalendarData(file);
    }
});

// Outlookからエクスポートした予定表データ(CSV)を読み込む関数
function loadCalendarData(file) {
    const reader = new FileReader();
    reader.onload = function(event) {
        const data = event.target.result;
        const parsedData = parseCSV(data);
        convertToICal(parsedData);
    };
    reader.readAsText(file);
}

// CSVデータをパースする関数
function parseCSV(data) {
    const rows = data.split("\r\n");
    const headers = rows[0].split(",");
    return rows.slice(1).map(row => {
        const values = row.split(",");
        return headers.reduce((obj, header, index) => {
            obj[header.trim()] = values[index].trim();
            return obj;
        }, {});
    });
}

// CSVデータをiCalファイルに変換する関数
function convertToICal(data) {
    let icalString = "BEGIN:VCALENDAR\nVERSION:2.0\n";
    data.forEach((row) => {
        icalString += "BEGIN:VEVENT\n";
        icalString += `DTSTART:${formatDate(row['Start Date'])}\n`;
        icalString += `DTEND:${formatDate(row['End Date'])}\n`;
        icalString += `SUMMARY:${row['Subject']}\n`;
        icalString += `DESCRIPTION:${row['Description']}\n`;
        icalString += `LOCATION:${row['Location']}\n`;
        icalString += `ORGANIZER;CN=${row['Organizer']}:MAILTO:${row['Organizer Email']}\n`;
        icalString += "END:VEVENT\n";
    });
    icalString += "END:VCALENDAR";
    saveICal(icalString, 'calendar.ics');
}

// 日付をiCal形式にフォーマットする関数
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toISOString().replace(/[-:]/g, "").split(".")[0];
}

// iCalファイルを保存する関数
function saveICal(calendar, filename) {
    const blob = new Blob([calendar], { type: 'text/calendar' });
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = filename;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}