(function() {
	let logoHeight = document.querySelector('.logo').offsetHeight;
	setLogoBackgroundHeight('.logo-background', logoHeight * 2);
})();

function setLogoBackgroundHeight(logoBackgroundSelector, height) {
	let logoBackground = document.querySelector(logoBackgroundSelector);
	logoBackground.style.height = `${height}px`;
}