import {
    refreshTargetNiceScrollbar,
    scrollTopTargetNiceScrollbar,
    getTransitionEndByBrowser
} from '/js/moduls/utils.js';
import {setResizeObserver} from '/js/moduls/observers.js';

const htmlBody = $('body');

class Sidebar {
    constructor(sidebarElement, navLinksSelector, logoutButtonSelector) {
        this.sidebarElement = sidebarElement;
        this.navLinksSelector=navLinksSelector;
        this.logoutButtonSelector=logoutButtonSelector;

        this.setSidebarNiceScrollbar();
        this.handleSlide();
        this.handleDropdownToggleClickedInSidebar();
        this.handleNavLink();
        this.handleBodyResize();
        setResizeObserver(this.sidebarElement[0], () => {
            this.handleSidebarResize()
        });
        this.handleUserLogut();
    }

    handleSlide() {
        htmlBody.on("slideSidebar", () => {
            this.sidebarElement.toggleClass('active');

            htmlBody.one(getTransitionEndByBrowser(), () => {
                refreshTargetNiceScrollbar(this.sidebarElement);
                scrollTopTargetNiceScrollbar(this.sidebarElement);
            })
        })
    }

    handleDropdownToggleClickedInSidebar() {
        const $this=this;
        this.sidebarElement.find('a[data-toggle=collapse]').click(function(){
            $(this).one(getTransitionEndByBrowser(), function(){
                refreshTargetNiceScrollbar($this.sidebarElement);
            })
        })
    }

    handleNavLink() {
        const $this = this

        this.sidebarElement.on("click", this.navLinksSelector, function (event) {
            event.preventDefault();
            $this.changeContent($(this).attr("href"));
        });
    }

    changeContent(pageUrl) {
        $.ajax({
            url: pageUrl,
            type: 'GET',
            async: true,
            success: function (content) {
                htmlBody.trigger("refreshContent", content);
            },
            statusCode: {
                401: function () {
                    htmlBody.trigger("login", pageUrl);
                }
                // 403: function () {
                //     $(document).trigger("accessVadlidation", pageUrl)
                // }
            }
        })
    }

    setSidebarNiceScrollbar() {
        this.sidebarElement.niceScroll({
            cursorwidth: '10px',
            cursoropacitymax: 0,
            zindex: 1001
        });
    }

    handleSidebarResize() {
        refreshTargetNiceScrollbar(this.sidebarElement);
    }

    handleBodyResize() {
        htmlBody.on("bodyMutated", () => {
            this.sidebarElement.css("height", htmlBody.css("height"))
            refreshTargetNiceScrollbar(this.sidebarElement);
        })
    }

    handleUserLogut() {
        this.sidebarElement.on('click', this.logoutButtonSelector, (event) => {
            event.preventDefault();

            const logoutUrl = event.target.href;
            htmlBody.trigger("logout", logoutUrl);
        });
    }
}

class UserdataWidget {
    constructor(userdataHolder) {
        this.userdataHolder = userdataHolder;
        this.handleProfileUpdate();
        this.handleRefreshUserdata();
    }

    handleRefreshUserdata() {
        htmlBody.on("updateSidebarUserdata", () => {
            const userdataHolderId = `#${this.userdataHolder.attr('id')}`;

            this.userdataHolder.load(`/ ${userdataHolderId}>*`, "");


        })
    }

    handleProfileUpdate() {
        htmlBody.on("profileUpdated", () => {
            htmlBody.trigger("updateSidebarUserdata");
        })
    }
}

class HomeWidget {
    constructor(homeLogoElement) {
        this.homeLogoElement = homeLogoElement;
        this.size = "normal";
        if ($(window).width() <= 767) {
            this.size = "truncated";
        }
        this.handleWindowSizeChange();
        this.handleSlideSidebar();
    }

    animatedSizeChangeOnNormalScreen(slideType) {
        if (slideType == "slideIn") {
            this.homeLogoElement.animate({
                width: '110%'
            }, 10, () => {
                this.homeLogoElement.html('mZ');
            });
        } else {
            this.homeLogoElement.animate({
                width: '100%'
            }, 10, () => {
                this.homeLogoElement.html('myZug');
            });
        }
    }

    animatedSizeChangeOnMobileScreen() {
        this.homeLogoElement.animate({
            width: '110%'
        }, 10, () => {
            this.homeLogoElement.html('mZ');
        });
    }

    handleSlideSidebar() {
        htmlBody.on("slideSidebar", (event, slideType) => {
            if (this.size == "normal") {
                this.animatedSizeChangeOnNormalScreen(slideType)
            } else {
                this.animatedSizeChangeOnMobileScreen()
            }
        })
    }

    handleWindowSizeChange() {
        $(window).resize(() => {
            if ($(window).width() <= 767) {
                this.size = "truncated";
                return
            }
            this.size = "normal";
        })
    }
}

class ThemeChangerWidget {
    constructor(themeChangerButtons) {
        this.themeChangerButtons = themeChangerButtons;
        this.handleThemeChangeByButton();
        this.changeTheme('deepOcean')
    }

    changeTheme(themeId) {
        htmlBody.removeClass(function (index, className) {
            return (className.match(/\btheme-\S+/g) || []).join(' '); // remove
            // only the
            // theme
            // classes
        }).addClass(`theme-${themeId}`);
        htmlBody.trigger('themeChanged', themeId);
    }

    handleThemeChangeByButton() {
        const $this = this;
        this.themeChangerButtons.click(function (event) {
            event.preventDefault();
            const themeId = $(this).attr("href");
            $this.changeTheme(themeId);
        });
    }
}

export {
    Sidebar,
    ThemeChangerWidget,
    HomeWidget,
    UserdataWidget
};