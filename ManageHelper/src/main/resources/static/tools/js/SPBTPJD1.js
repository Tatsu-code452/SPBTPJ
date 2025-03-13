import { loadCsv, setDragAndDrop, splitTextByBreakeLine } from "./common.js";

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
    zoomIn.addEventListener("click", () => zoomSvg(0.2));
    zoomOut.addEventListener("click", () => zoomSvg(-0.2));
}

// SVG表示
function viewSvg(data) {
    const svg = createSvg();
    let minX = 999999,
        minY = 999999,
        maxX = 0,
        maxY = 0;

    splitTextByBreakeLine(data)
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

    const svgWidth = maxX - minX + 10;
    const svgHeight = maxY - minY + 10;
    resizeSvg(svg, svgWidth, svgHeight, `0 0 ${svgWidth} ${svgHeight}`);
    document.querySelector("#svgWrapper").replaceChildren(svg);
    updateSelectionDisplay();
}

// SVG作成
function createSvg() {
    return document.createElementNS(svgNamespace, "svg");
}

// SVGリサイズ
function resizeSvg(svg, width, height, viewBox) {
    Object.entries({ width, height, viewBox }).forEach(([key, value]) =>
        svg.setAttribute(key, value)
    );
}

// SVGグループ作成
function createSvgGroup(id, x, y, width, height, color) {
    const rectGroup = document.createElementNS(svgNamespace, "g");
    rectGroup.appendChild(createSvgRect(id, x, y, width, height, color));
    rectGroup.appendChild(
        createSvgText(rectGroup.firstChild, id, x, y, width, height)
    );
    return rectGroup;
}

// SVG矩形作成
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

// SVGテキスト作成
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
function zoomSvg(scaleDiff) {
    const svg = document.querySelector("#svgWrapper svg");
    const currentTransform = svg.getAttribute("transform");
    const scaleMatch = currentTransform
        ? currentTransform.match(/scale\(([^)]+)\)/)
        : null;
    const currentScale = scaleMatch ? parseFloat(scaleMatch[1]) : 1;
    const newScale = currentScale + scaleDiff;
    if (newScale >= 0.2) {
        svg.setAttribute("transform", `scale(${newScale})`);
    }
}
