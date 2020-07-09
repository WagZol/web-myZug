import {
    customTicker,
} from '/js/moduls/utils.js';

import {
    setResizeObserver
} from '/js/moduls/observers.js'

import {
    Navbar,
    CitySelectorModal,
    CitySelectorWidget,
    DateAndNameDayWidget,
    WeatherWidget,
    SidebarCollapserWidget
} from '/js/moduls/navbar.js';

import {
    Sidebar,
    ThemeChangerWidget,
    HomeWidget,
    UserdataWidget
} from '/js/moduls/sidebar.js'

import {
    ContentWrapper
} from '/js/moduls/contentWrapper.js'

import {
    Authentication,
    LoginModal
} from "./moduls/authentication.js";

const htmlBody = $('body');

$(document)
    .ready(
        function () {
            setNavbar();
            setSidebar();
            setContenWrapper();
            setBasicAuthentication()

            handleCsrfRefresh();
            setCsrfForPosts();
            setResizeObserver(document.querySelector('body'), bodyResizeMutationCallbacks);
            handleWindowResize();
        });

function setNavbar() {
    const navbar = new Navbar($('.content-header'), $('#content-header-wrapper'));
    const citySelectorModal = new CitySelectorModal($('#citySelectorModal'));
    const citySelectorWidget = new CitySelectorWidget($('.city-selector-btn'));
    const dateAndNameDayWidget = new DateAndNameDayWidget($('.date'));
    const weatherWidget = new WeatherWidget($('.temperature'), $('.weatherIcon'));
    const sidebarCollapserWidget = new SidebarCollapserWidget($('#sidebarCollapse'))
    customTicker
    (() => {
        navbar.updateNavbar()
    }, 10000);
}

function setSidebar() {
    const sidebar = new Sidebar($('#sidebar'), '.navLink', '#logout-button-sidebar');
    const themeChangerWidget = new ThemeChangerWidget($('.theme-toggle'));
    const homeWidget = new HomeWidget($('.sidebar-header .logo'));
    const userdataWidget = new UserdataWidget($('#auth-informations-and-handlers'))
}

function setContenWrapper() {
    const contentWrapper = new ContentWrapper($('.content-wrapper'), $("meta#page_param"))
}

function setBasicAuthentication() {
    const authentication = new Authentication(["profile", "notes"]);
    const loginModal = new LoginModal($("#loginModal"));
}

function refreshCsrfToken() {
    $.ajax({
        url: '/csrf',
        type: 'GET',
        async: true,
        success: function (csrf) {
            $("meta[name='csrf_token']").attr('content', csrf.token);
            $("meta[name='csrf_header']").attr('content', csrf.headerName);
        }
    })
}

function handleCsrfRefresh() {
    htmlBody.on("refreshCSRFToken", () => {
        refreshCsrfToken();
    })
}


function setCsrfForPosts() {
    $(document).ajaxSend(function (event, request, settings) {
        if (settings.type == "POST") {
            const token = $("meta[name='csrf_token']").attr("content");
            const headerName = $("meta[name='csrf_header']").attr("content");
            request.setRequestHeader(headerName, token);
            refreshCsrfToken();
        }
    })
}

function bodyResizeMutationCallbacks(resizeMutationList, resizeObserver) {
    if (resizeMutationList.length > 0) {
        htmlBody.trigger("bodyMutated");
    }
};

function handleWindowResize(){
    $(window).resize(()=>{
        $('body').css("height", window.innerHeight);
    })
}
// function handleAccessValidationWithModal(accessValidationModal) {
//     $(document).on("accessValidation"), (event, urlToGoAfterValidation) => {
//         accessValidationModal.modal("show");
//         accessValidationModal.find("form").attr("targetUrl", urlToGoAfterValidation);
//     }
// }

$(window).on('beforeunload', function () {
    return "Do you want to leave this page?";
});


