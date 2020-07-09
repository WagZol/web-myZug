$(document)
	.ready(function() {
		init();
		changeThemeOnTiddlyWiki($('#notes'));
	});

function createRandomNum(){
	return Math.round(Math.random() * 10000000);
}

function selectThemeForTiddlyWiki(themeName, noteHolderElement){
	noteHolderElement.attr("src", `https://wagzol.gitlab.io/notes/?${themeName}&cacheId${createRandomNum()}`);
}

function changeThemeOnTiddlyWiki(noteHolderElement){
	htmlBody.on('themeChanged', function(event, themeName){
		selectThemeForTiddlyWiki(themeName, noteHolderElement);
	});
}

function init(){
	selectThemeForTiddlyWiki("deepOcean", $('#notes'));
}