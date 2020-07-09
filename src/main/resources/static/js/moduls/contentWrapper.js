import {
    refreshTargetNiceScrollbar,
    scrollTopTargetNiceScrollbar,
    scrollLockTargetNiceScrollbar,
    getTransitionEndByBrowser
} from '/js/moduls/utils.js';

import {
    setMutationObserver
} from '/js/moduls/observers.js';

const bodyHtml = $("body");

class ContentWrapper {
    constructor(contentWrapperElement, pageParam) {
        this.contentWrapperElement = contentWrapperElement;
        this.pageParam = pageParam;
        this.handleContentRefresh();
        this.loadInitPagebyPageParam();
        this.setContentNiceScrollbar();
        setMutationObserver(this.contentWrapperElement[0], (resizeMutationList) => {
            this.handleContentMutation(resizeMutationList)
        });
        this.handleNavbarHeightChange();
        this.handleBackToHomeFromAuthNeededPages();
        this.handleMobileContent();
        this.handleChangeOuterHeight();
        this.handleNavToHomePage();
        this.handleBodyResize();
    }

    setContentNiceScrollbar() {
        this.contentWrapperElement.niceScroll({
            cursorwidth: '10px',
            autohidemode: false,
            zindex: 1001
        });
        $(this.contentWrapperElement).getNiceScroll(0).show();
    }

    handleContentMutation(resizeMutationList, resizeObserver) {
        if (resizeMutationList.length > 0) {
            refreshTargetNiceScrollbar(this.contentWrapperElement);
        }
    }

    handleBodyResize() {
        htmlBody.on("bodyMutated", () => {
            refreshTargetNiceScrollbar(this.contentWrapperElement);
        })
    }

    handleNavbarHeightChange() {
        bodyHtml.on("navbarHeightChange", (event, navbarHeight) => {
            const calculatedContentHeight = window.innerHeight - navbarHeight;
            this.contentWrapperElement.outerHeight(calculatedContentHeight);
            this.contentWrapperElement.one(getTransitionEndByBrowser(), () => {
                refreshTargetNiceScrollbar(this.contentWrapperElement)
            });
        })
    }

    loadInitPagebyPageParam() {
        const initPage = this.pageParam.attr("content") || "/home";

        $.ajax({
            url: initPage,
            type: 'GET',
            async: true,
            success: function (content) {
                bodyHtml.trigger("refreshContent", content);
            }
        });
    }

    handleContentRefresh() {
        bodyHtml.on("refreshContent", (event, newContent) => {
            this.setContent(newContent, () => {
                refreshTargetNiceScrollbar(this.contentWrapperElement);
                scrollTopTargetNiceScrollbar(this.contentWrapperElement);
            });
        })
    }

    setContent(newContent, onScriptLoaded) {
        this.contentWrapperElement.children().remove();
        this.contentWrapperElement.html(newContent);
        const scriptName = $($.parseHTML(newContent)).filter('title').text();
        $.getScript(`js/${scriptName}.js`, () => {
            onScriptLoaded(this.contentWrapperElement)
        });
    }

    handleBackToHomeFromAuthNeededPages(){
        bodyHtml.on("backToHomeFromAuthNeededPages", (event, authNeededPages)=>{
            let pageLoaded = this.contentWrapperElement.find("title").html()
            if (authNeededPages.pages.includes(pageLoaded)) {
                bodyHtml.trigger("refreshContentToHome");
            }
        })
    }

    handleNavToHomePage(){
        bodyHtml.on("refreshContentToHome", ()=>{
            $.get( "/home", (content)=>{
                htmlBody.trigger("refreshContent", content);
            })
        })
    }

    handleMobileContent(){
        htmlBody.on("setMobileContent", ()=>{
            if ($(window).width() <= 767) {
                htmlBody.toggleClass('disabled')
                this.contentWrapperElement.toggleClass('disabled')
                scrollLockTargetNiceScrollbar(this.contentWrapperElement);
            }
        })
    }

    handleChangeOuterHeight(){
        htmlBody.on("changeContentOuterHeight", (event, heightToChange, callbackAfterChange)=>{
            this.contentWrapperElement.outerHeight(heightToChange);
            this.contentWrapperElement.one(getTransitionEndByBrowser(), ()=>{
                callbackAfterChange();
            })

        })
    }
}

export {
    ContentWrapper
};