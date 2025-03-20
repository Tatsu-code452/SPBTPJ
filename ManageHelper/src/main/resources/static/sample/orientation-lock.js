document.addEventListener("DOMContentLoaded", () => {
	function lockOrientation() {
		if (!window.screen.orientation) {
			updateOrientationClass();
		}
	}

	function updateOrientationClass() {
		const isPortrait = window.innerHeight > window.innerWidth;
		document.body.classList.toggle("rotate-portrait", isPortrait);
		document.body.classList.toggle("rotate-landscape", !isPortrait);
	}

	lockOrientation();
	updateOrientationClass();
	window.addEventListener("resize", updateOrientationClass);
});
