import { loadCsv, setDragAndDrop } from "./common.js";

const svgNamespace = "http://www.w3.org/2000/svg";

// 画面表示時
document.addEventListener("DOMContentLoaded", () => {
    // ファイルドロップ設定
    setDragAndDrop(document.querySelector("#drop-area"), viewSvg);
    // 拡大縮小ボタン設定
    setZoomButton();
    // CSV読み込み
    loadCsv("./data/data.csv", viewSvg);
});

// 拡大縮小ボタン設定
function setZoomButton() {
    const zoomIn = document.querySelector("#zoomIn");
    const zoomOut = document.querySelector("#zoomOut");
    zoomIn.addEventListener("click", () => zoomSvg(1.2));
    zoomOut.addEventListener("click", () => zoomSvg(0.8));
}

// SVG表示
function viewSvg(data) {
    const svg = createSvg();
    let minX = 1000,
        minY = 800,
        maxX = 0,
        maxY = 0;

    data.split("\n")
        .slice(1)
        .filter((row) => row.trim())
        .forEach((row) => {
            const [id, x, y, width, height, color] = row.split(",");
            if (id && x && y && width && height && color) {
                svg.appendChild(createSvgGroup(id, x, y, width, height, color));
                minX = Math.min(minX, parseFloat(x));
                minY = Math.min(minY, parseFloat(y));
                maxX = Math.max(maxX, parseFloat(x) + parseFloat(width));
                maxY = Math.max(maxY, parseFloat(y) + parseFloat(height));
            } else {
                console.warn("Invalid row:", row);
            }
        });

    resizeSvg(
        svg,
        maxX - minX,
        maxY - minY,
        `0 0 ${maxX - minX + 10} ${maxY - minY + 10}`
    );
    document.querySelector("#svgWrapper").replaceChildren(svg);
    updateSelectionDisplay();
}

// SVG選択
function createSvg() {
    return document.createElementNS(svgNamespace, "svg");
}

// SVGリサイズ
function resizeSvg(svg, width, height, viewBox) {
    Object.entries({ width, height, viewBox }).forEach(([key, value]) =>
        svg.setAttribute(key, value)
    );
}

// SVG矩形
function createSvgRect(id, x, y, width, height, color) {
    const rect = document.createElementNS(svgNamespace, "rect");
    Object.entries({
        id,
        x,
        y,
        width,
        height,
        fill: color,
        stroke: "black",
        "stroke-width": "1",
    }).forEach(([key, value]) => rect.setAttribute(key, value));
    rect.addEventListener("click", () => toggleSelection(rect));
    return rect;
}

// SVGテキスト
function createSvgText(rect, textContent, x, y, width, height) {
    const text = document.createElementNS(svgNamespace, "text");
    text.setAttribute("x", parseFloat(x) + parseFloat(width) / 2);
    text.setAttribute("y", parseFloat(y) + parseFloat(height) / 2);
    text.setAttribute("dominant-baseline", "middle");
    text.setAttribute("text-anchor", "middle");
    text.textContent = textContent;
    text.addEventListener("click", () => toggleSelection(rect));
    return text;
}

// SVGグループ
function createSvgGroup(id, x, y, width, height, color) {
    const rectGroup = document.createElementNS(svgNamespace, "g");
    rectGroup.appendChild(createSvgRect(id, x, y, width, height, color));
    rectGroup.appendChild(
        createSvgText(rectGroup.firstChild, id, x, y, width, height)
    );
    return rectGroup;
}

// 選択切り替え
function toggleSelection(rect) {
    if (rect.classList.contains("selected")) {
        rect.classList.remove("selected");
    } else {
        rect.classList.add("selected");
        rect.dataset.selectionOrder = Date.now(); // 選択順を維持するためのタイムスタンプを追加
    }
    updateSelectionDisplay();
}

// 選択表示更新
function updateSelectionDisplay() {
    const selectedRects = Array.from(
        document.querySelectorAll(".selected")
    ).sort((a, b) => a.dataset.selectionOrder - b.dataset.selectionOrder); // 選択順にソート
    const selectedIds = selectedRects.map((rect) => rect.id);
    document.querySelector("#svgSelect").value = selectedIds.join(",");
}

// SVG拡大縮小
function zoomSvg(scale) {
    const svg = document.querySelector("#svgWrapper svg");
    if (svg) {
        const viewBox = svg.getAttribute("viewBox").split(" ").map(Number);
        const [x, y, width, height] = viewBox;
        const newWidth = width * scale;
        const newHeight = height * scale;
        svg.setAttribute("viewBox", `${x} ${y} ${newWidth} ${newHeight}`);
    }
}
