self.addEventListener('install', event => {
    console.log('Service Worker installing...');
    event.waitUntil(self.skipWaiting());
});

self.addEventListener('activate', event => {
    console.log('Service Worker activating...');
});

self.addEventListener('fetch', event => {
    console.log('Fetching:', event.request.url);
});

self.addEventListener('push', (event) => {
    const data = event.data ? event.data.json() : {};
    const title = data.title || '通知';
    const options = {
        body: data.body || '新しい通知が届きました。',
        icon: data.icon || '/icon.png',
        badge: data.badge || '/badge.png',
        data: data.url || '/'
    };

    event.waitUntil(self.registration.showNotification(title, options));
});

self.addEventListener('notificationclick', (event) => {
    event.notification.close();
    event.waitUntil(
        clients.openWindow(event.notification.data)
    );
});