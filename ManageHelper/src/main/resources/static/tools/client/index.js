if ("serviceWorker" in navigator) {
    window.addEventListener("load", () => {
        navigator.serviceWorker
            .register("./serviceWorker.js")
            .then((registration) => {
                console.log(
                    "Service Worker registered successfully:",
                    registration
                );
                if ("PushManager" in window) {
                    document
                        .getElementById("subscribe")
                        .addEventListener("click", async () => {
                            const permission =
                                await Notification.requestPermission();
                            if (permission === "granted") {
                                const subscription =
                                    await reg.pushManager.subscribe({
                                        userVisibleOnly: true,
                                        applicationServerKey:
                                            "<YOUR_PUBLIC_VAPID_KEY>",
                                    });
                                console.log("Push Subscription:", subscription);

                                // サーバーへ購読情報を送信（例: Fetch APIを使用）
                                await fetch("/subscribe", {
                                    method: "POST",
                                    headers: {
                                        "Content-Type": "application/json",
                                    },
                                    body: JSON.stringify(subscription),
                                });
                                alert("Push通知が有効化されました！");
                            } else {
                                alert("Push通知が拒否されました。");
                            }
                        });
                }
            })
            .catch((error) => {
                if (error.name === "SecurityError") {
                    console.error(
                        "Service Worker registration failed due to an SSL certificate error. Ensure HTTPS is enabled.",
                        error
                    );
                    alert(
                        "Service Workerの登録に失敗しました。HTTPSが有効になっていることを確認してください。"
                    );
                } else {
                    console.error("Service Worker registration failed:", error);
                }
            });
    });
}
