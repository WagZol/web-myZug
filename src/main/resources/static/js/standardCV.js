$(document).ready(function() {
	handleDownloadCV($('.btn-download'));
	handlePrintCV($('.btn-print'), $('.content-wrapper'));
});

function handleDownloadCV(downloadButton){
	downloadButton.click(function(event) {
		event.preventDefault();

		let fileDownloadPath = getActualPdfFilepath();
		$(this).attr("href", fileDownloadPath);
	});
}

function getActualPdfFilepath(){
	const actualThemeName = getActualThemeName();
	return `../pdf/standardCv${actualThemeName}.pdf`;
}

function handlePrintCV(printButton){
	printButton.click(function(event){
		event.preventDefault();

		htmlBody.trigger("changeContentOuterHeight", ['', ()=>{console.log("print");window.print();}]);
	});
}

function getActualThemeName(){
	let themeClass=($('body').attr("class").match (/\btheme-\S+/g))[0] || "";
	let themeName=themeClass.replace("theme-", "");
	return themeName;
}

