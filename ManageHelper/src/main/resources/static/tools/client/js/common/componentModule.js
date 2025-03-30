const componentModule = {
    /**
     * 新しい要素を作成する
     * @param {string} tagName - 作成するタグ名
     * @param {Object} properties - プロパティオブジェクト
     * @returns {HTMLElement} - 作成された要素
     */
    createElement(tagName, properties = {}) {
        return Object.assign(document.createElement(tagName), properties);
    },

    /**
     * 新しい要素を作成する
     * @param {string} tagName - 作成するタグ名
     * @param {Object} attributes - 属性オブジェクト
     * @returns {HTMLElement} - 作成された要素
     */
    createElementWithAttr(tagName, attributes = {}) {
        const element = document.createElement(tagName);
        Object.entries(attributes).forEach(([key, value]) => {
            element.setAttribute(key, value);
        });
        return element;
    },

    /**
     * 要素を取得する
     * @param {string} selector - CSSセレクター
     * @returns {HTMLElement|null} - 該当する要素
     */
    getElement(selector) {
        return document.querySelector(selector);
    },

    /**
     * 複数の要素を取得する
     * @param {string} selector - CSSセレクター
     * @returns {NodeList} - 該当する要素のリスト
     */
    getElements(selector) {
        return document.querySelectorAll(selector);
    },

    /**
     * 要素のテキストを設定する
     * @param {HTMLElement} element - 対象の要素
     * @param {string} text - 設定するテキスト
     */
    setText(element, text) {
        if (element) {
            element.textContent = text;
        }
    },

    /**
     * 要素のHTMLを設定する
     * @param {HTMLElement} element - 対象の要素
     * @param {string} html - 設定するHTML
     */
    setHTML(element, html) {
        if (element) {
            element.innerHTML = html;
        }
    },

    /**
     * 要素にイベントリスナーを追加する
     * @param {HTMLElement} element - 対象の要素
     * @param {string} eventType - イベントタイプ
     * @param {Function} callback - コールバック関数
     */
    addEventListener(element, eventType, callback) {
        if (element) {
            element.addEventListener(eventType, callback);
        }
    },

    /**
     * 要素にイベントリスナーを追加する
     * @param {string} id - 対象のid
     * @param {string} eventType - イベントタイプ
     * @param {Function} callback - コールバック関数
     */
    addEventListenerById(id, event, callback) {
        const element = this.getElement(id);
        if (element) {
            this.addEventListener(element, event, (e) => {
                e.preventDefault();
                callback(e);
            });
        }
    },

    /**
     * 子要素を追加する
     * @param {HTMLElement} parent - 親要素
     * @param {HTMLElement} child - 子要素
     */
    appendChild(parent, child) {
        if (parent && child) {
            parent.appendChild(child);
        }
    },

    /**
     * 要素を削除する
     * @param {HTMLElement} element - 削除する要素
     */
    removeElement(element) {
        if (element && element.parentNode) {
            element.parentNode.removeChild(element);
        }
    },
};

export default componentModule;
