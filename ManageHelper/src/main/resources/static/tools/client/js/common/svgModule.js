const svgNamespace = "http://www.w3.org/2000/svg";
const svgModule = {
    // SVG作成
    createSvg() {
        return document.createElementNS(svgNamespace, "svg");
    },
    // SVGリサイズ
    resizeSvg(svg, width, height, viewBox) {
        Object.entries({ width, height, viewBox }).forEach(([key, value]) =>
            svg.setAttribute(key, value)
        );
    },
    // SVGグループ作成
    createSvgGroup(id, x, y, width, height, color) {
        const rectGroup = document.createElementNS(svgNamespace, "g");
        rectGroup.appendChild(this.createSvgRect(id, x, y, width, height, color));
        rectGroup.appendChild(
            this.createSvgText(rectGroup.firstChild, id, x, y, width, height)
        );
        return rectGroup;
    },
    // SVG矩形作成
    createSvgRect(id, x, y, width, height, color) {
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
        rect.addEventListener("click", () => this.toggleSelection(rect));
        return rect;
    },
    // SVGテキスト作成
    createSvgText(rect, textContent, x, y, width, height) {
        const text = document.createElementNS(svgNamespace, "text");
        text.setAttribute("x", parseFloat(x) + parseFloat(width) / 2);
        text.setAttribute("y", parseFloat(y) + parseFloat(height) / 2);
        text.setAttribute("dominant-baseline", "middle");
        text.setAttribute("text-anchor", "middle");
        text.textContent = textContent;
        text.addEventListener("click", () => this.toggleSelection(rect));
        return text;
    },
    // 選択切り替え
    toggleSelection(rect) {
        rect.classList.toggle("selected");
        if (rect.classList.contains("selected")) {
            rect.dataset.selectionOrder = Date.now();
        }
        this.updateSelectionDisplay();
    },
    // 選択表示更新
    updateSelectionDisplay() {
        const selectedRects = [...document.querySelectorAll(".selected")].sort(
            (a, b) => a.dataset.selectionOrder - b.dataset.selectionOrder
        );
        document.querySelector("#svgSelect").value = selectedRects
            .map((rect) => rect.id)
            .join(",");
    },
};

export default svgModule;
