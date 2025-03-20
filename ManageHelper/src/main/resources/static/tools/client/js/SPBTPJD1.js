import {
    loadCsv,
    setDragAndDrop,
    createSvg,
    resizeSvg,
    createSvgGroup,
    updateSelectionDisplay,
} from "./common.js";

// 画面表示時
document.addEventListener("DOMContentLoaded", initialize);

// 初期化処理
function initialize() {
    // ファイルドロップ設定
    setDragAndDrop(document.querySelector("#drop-area"), viewSvg);
    // 拡大縮小ボタン設定
    setZoomButton();
    // CSV読み込み
    loadCsv("./data/data.csv", viewSvg);
}

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

    data.slice(1).forEach((row) => {
        const [id, x, y, width, height, color] = row;
        if (id && x && y && width && height && color) {
            svg.appendChild(createSvgGroup(id, x, y, width, height, color));
            minX = Math.min(minX, parseFloat(x));
            minY = Math.min(minY, parseFloat(y));
            maxX = Math.max(maxX, parseFloat(x) + parseFloat(width));
            maxY = Math.max(maxY, parseFloat(y) + parseFloat(height));
        }
    });

    const svgWidth = maxX - minX + 10;
    const svgHeight = maxY - minY + 10;
    resizeSvg(svg, svgWidth, svgHeight, `0 0 ${svgWidth} ${svgHeight}`);
    document.querySelector("#svgWrapper").replaceChildren(svg);
    updateSelectionDisplay();
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
