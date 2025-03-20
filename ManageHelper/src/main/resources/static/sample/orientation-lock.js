document.addEventListener("DOMContentLoaded", () => {
    lockOrientation = () => {
        if (!window.screen.orientation) {
            const isPortrait = window.innerHeight > window.innerWidth;
            document.body.classList.toggle("rotate-portrait", isPortrait);
            document.body.classList.toggle("rotate-landscape", !isPortrait);
        }
    };
    updateOrientationClass();
    window.addEventListener("resize", updateOrientationClass);
});

function updateOrientationClass() {
    const isPortrait = window.innerHeight > window.innerWidth;
    document.body.classList.toggle("rotate-portrait", isPortrait);
    document.body.classList.toggle("rotate-landscape", !isPortrait);
}
