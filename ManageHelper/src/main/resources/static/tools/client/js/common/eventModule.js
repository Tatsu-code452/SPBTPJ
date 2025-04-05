import csvModule from "./csvModule.js";

const eventModule = {
    // イベント設定
    setEvent(target, event, callback) {
        const element =
            typeof target === "string"
                ? document.querySelector(target)
                : target;
        if (element) {
            element.addEventListener(event, (e) => {
                e.preventDefault();
                callback(e);
            });
        }
    },
    // ドラッグアンドドロップ設定
    setDragAndDrop(target, callback) {
        const element =
            typeof target === "string"
                ? document.querySelector(target)
                : target;
        ["dragover", "dragleave", "drop"].forEach((eventType) => {
            element.addEventListener(eventType, (event) =>
                handleDragEvent(event, callback)
            );
        });
    },
    // ドラッグイベント処理
    handleDragEvent(event, callback) {
        event.preventDefault();
        if (event.type === "dragover") {
            toggleHighlight(event, true);
        } else if (event.type === "dragleave") {
            toggleHighlight(event, false);
        } else if (event.type === "drop") {
            toggleHighlight(event, false);
            processDrop(event, callback);
        }
    },
    // ドロップ処理
    processDrop(event, callback) {
        const file = event.dataTransfer.files[0];
        if (file?.type === "text/csv") {
            csvModule.readFile(file, callback);
        } else {
            alert("Invalid file type.");
        }
    },
    // ハイライト切り替え
    toggleHighlight(event, highlight) {
        event.preventDefault();
        event.currentTarget.classList.toggle("highlight", highlight);
    },
};

export default eventModule;
