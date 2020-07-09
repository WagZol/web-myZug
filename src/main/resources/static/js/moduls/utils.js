function getDateFromClient() {
    let currentDateFromClient = new Date();
    let dd = String(currentDateFromClient.getDate()).padStart(2, '0');
    let mm = String(currentDateFromClient.getMonth() + 1).padStart(2, '0'); // January
                                                                            // is
                                                                            // 0!
    let yyyy = currentDateFromClient.getFullYear();

    return `${yyyy}.${mm}.${dd}.`;
}

function customTicker(tickingFunction, delay) {
    const visibilityParameters = _getVisibiltyParametersByBrowserTpye();
    tickingFunction();
    let ticker = setInterval(tickingFunction, delay);

    document.addEventListener(visibilityParameters.visibilityChange, function () {
        if (document[visibilityParameters.hidden]) {
            clearInterval(ticker);
        } else {
            ticker = setInterval(tickingFunction, delay);
        }
    }, false);
}

function _getVisibiltyParametersByBrowserTpye() {
    let visibility={};
    if (typeof document.hidden !== "undefined") { // Opera 12.10 and Firefox 18 and later support
        visibility.hidden = "hidden";
        visibility.visibilityChange = "visibilitychange";
    } else if (typeof document.msHidden !== "undefined") {
        visibility.hidden = "msHidden";
        visibility.visibilityChange = "msvisibilitychange";
    } else if (typeof document.webkitHidden !== "undefined") {
        visibility.hidden = "webkitHidden";
        visibility.visibilityChange = "webkitvisibilitychange";
    }
    return visibility;
}

function getTransitionEndByBrowser() {
    const testerElement = document.createElement('div');
    const transitions = {
        'transition': 'transitionend',
        'OTransition': 'otransitionend',  // oTransitionEnd in very old Opera
        'MozTransition': 'transitionend',
        'webkitTransition': 'webkitTransitionEnd',
        'MsTransition': 'msTransitionEnd'
    };

    for (let transition in transitions) {
        if (transitions.hasOwnProperty(transition) && testerElement.style[transition] !== undefined) {
            return transitions[transition];
        }
    }
}

function refreshTargetNiceScrollbar(target) {
    target.getNiceScroll().resize();
}

function scrollTopTargetNiceScrollbar(target) {
    target.getNiceScroll(0).doScrollTop(0);
}

function scrollLockTargetNiceScrollbar(target) {
    target.getNiceScroll(0).toggleScrollLock();
}

function htmlToElement(htmlCode) {
    const template = document.createElement('template');
    htmlCode = htmlCode.trim();
    template.innerHTML = htmlCode;
    return template.content.firstChild;
}


export {
    getDateFromClient,
    customTicker,
    refreshTargetNiceScrollbar,
    scrollTopTargetNiceScrollbar,
    scrollLockTargetNiceScrollbar,
    getTransitionEndByBrowser,
    htmlToElement
};