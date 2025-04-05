import csvModule from "./common/csvModule.js";
import eventModule from "./common/eventModule.js";
import svgModule from "./common/svgModule.js";

// 画面表示時
document.addEventListener("DOMContentLoaded", initialize);

// 初期化処理
function initialize() {
    eventModule.setDragAndDrop("#drop-area", viewSvg);
    eventModule.setEvent("#zoomIn", "click", () => zoomSvg(0.2));
    eventModule.setEvent("#zoomOut", "click", () => zoomSvg(-0.2));
    csvModule.loadCsv("./data/data.csv", viewSvg);
}

// SVG表示
function viewSvg(data) {
    const svg = svgModule.createSvg();
    let minX = 999999,
        minY = 999999,
        maxX = 0,
        maxY = 0;

    data.slice(1).forEach((row) => {
        const [id, x, y, width, height, color] = row;
        if (id && x && y && width && height && color) {
            svg.appendChild(
                svgModule.createSvgGroup(id, x, y, width, height, color)
            );
            minX = Math.min(minX, parseFloat(x));
            minY = Math.min(minY, parseFloat(y));
            maxX = Math.max(maxX, parseFloat(x) + parseFloat(width));
            maxY = Math.max(maxY, parseFloat(y) + parseFloat(height));
        }
    });

    const svgWidth = maxX - minX + 10;
    const svgHeight = maxY - minY + 10;
    svgModule.resizeSvg(
        svg,
        svgWidth,
        svgHeight,
        `0 0 ${svgWidth} ${svgHeight}`
    );
    document.querySelector("#svgWrapper").replaceChildren(svg);
    svgModule.updateSelectionDisplay();
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
