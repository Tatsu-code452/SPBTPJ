import { readFile } from "./csvUtils.js";

// イベント設定
function setEvent(target, event, callback) {
    const element =
        typeof target === "string" ? document.querySelector(target) : target;
    if (element) {
        element.addEventListener(event, (e) => {
            e.preventDefault();
            callback(e);
        });
    }
}

// クリックイベント設定
function setClickEvent(target, callback) {
    const element =
        typeof target === "string" ? document.querySelector(target) : target;
    if (element) {
        element.addEventListener("click", callback);
    }
}

// 入力イベント設定
function setInputEvent(target, callback) {
    const element =
        typeof target === "string" ? document.querySelector(target) : target;
    if (element) {
        element.addEventListener("input", callback);
    }
}

// ドラッグアンドドロップ設定
function setDragAndDrop(target, callback) {
    const element =
        typeof target === "string" ? document.querySelector(target) : target;
    ["dragover", "dragleave", "drop"].forEach((eventType) => {
        element.addEventListener(eventType, (event) =>
            handleDragEvent(event, callback)
        );
    });
}

// ドラッグイベント処理
function handleDragEvent(event, callback) {
    event.preventDefault();
    if (event.type === "dragover") {
        toggleHighlight(event, true);
    } else if (event.type === "dragleave") {
        toggleHighlight(event, false);
    } else if (event.type === "drop") {
        toggleHighlight(event, false);
        processDrop(event, callback);
    }
}

// ドロップ処理
function processDrop(event, callback) {
    const file = event.dataTransfer.files[0];
    if (file?.type === "text/csv") {
        readFile(file, callback);
    } else {
        alert("Invalid file type.");
    }
}

// ハイライト切り替え
function toggleHighlight(event, highlight) {
    event.preventDefault();
    event.currentTarget.classList.toggle("highlight", highlight);
}

export { setDragAndDrop, setClickEvent, setInputEvent, setEvent };
