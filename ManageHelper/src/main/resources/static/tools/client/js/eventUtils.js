import { readFile } from "./csvUtils.js";

// 初期化処理を共通化
function initializeDragAndDrop(dropAreaSelector, callback) {
    const dropArea = document.querySelector(dropAreaSelector);
    if (dropArea) {
        setDragAndDrop(dropArea, callback);
    }
}

function initializeButton(buttonSelector, callback) {
    const button = document.querySelector(buttonSelector);
    if (button) {
        button.addEventListener("click", callback);
    }
}

function initializeTextArea(textAreaSelector, callback) {
    const textArea = document.querySelector(textAreaSelector);
    if (textArea) {
        textArea.addEventListener("input", callback);
    }
}

// ドラッグアンドドロップ設定
function setDragAndDrop(dropArea, callback) {
    ["dragover", "dragleave", "drop"].forEach((eventType) => {
        dropArea.addEventListener(eventType, (event) =>
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

export {
    setDragAndDrop,
    initializeDragAndDrop,
    initializeButton,
    initializeTextArea,
};
