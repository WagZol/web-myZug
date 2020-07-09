import {getDateFromClient} from '/js/moduls/utils.js';
import {setResizeObserver} from '/js/moduls/observers.js';
import {htmlToElement} from "/js/moduls/utils.js";

const bodyHtml = $("body");

class Navbar {
    constructor(navbarElement, navbarWrapperElement) {
        this.navbarElement = navbarElement;
        this.navbarWrapperElement=navbarWrapperElement;
        this.cityName = "";
        this.handleUpdateByCityName();
        this.handleSidebarSlide();
        setResizeObserver(this.navbarElement[0], (resizeMutationList)=>{this.hanldeResize(resizeMutationList)});
        this.handleBodyResize();
        this.handleUpdteByGeoloaction();
    }


    updateNavbarWithGeolocation(lat, lon) {
        $.ajax({
            url: `/updatenavbarbygeo?lat=${lat}&lon=${lon}`,
            type: 'GET',
            async: true,
            success: function (refreshedNavbarContentsJSON) {
                bodyHtml.trigger("updateNavbar", refreshedNavbarContentsJSON);
            }
        }).fail();
    }

    updateNavbarWithCityName(cityNameParam) {
        $.ajax({
            url: `/updatenavbarbycity?cityName=${cityNameParam}`,
            type: 'GET',
            async: true,
            success: (refreshedNavbarContentsJSON)=>{
                bodyHtml.trigger("updateNavbar", refreshedNavbarContentsJSON);
                this.cityName = cityNameParam;
            },
            statusCode: {
                404: () => {
                    bodyHtml.trigger("invalidCityname")
                },
                400: () => {
                    this.getNavbarContentWithoutAPI();
                },
                500: () => {
                    this.getNavbarContentWithoutAPI();
                },
            }
        });
    }

    getNavbarContentWithoutAPI() {
        let currentDateFromClient = getDateFromClient();
        $.get(`/nameDay?date=${currentDateFromClient}`, (nameday) => {
            const refreshedNavbarContentsWithoutAPI = {
                nameday: nameday,
                date: currentDateFromClient
            }

            bodyHtml.trigger("updateNavbarWithoutAPI", refreshedNavbarContentsWithoutAPI)
        });
    }

    handleUpdateByCityName() {
        bodyHtml.on("updateNavbarByCityname", (event, cityName) => {
            this.updateNavbarWithCityName(cityName);
        })
    }

    handleSidebarSlide() {
        bodyHtml.on("slideSidebar", () => {
            this.navbarElement.toggleClass("maximazed");
        })
    }

    updateNavbar() {
        if (!(this.cityName)) {
            navigator.geolocation.getCurrentPosition((position) => {
                this.updateNavbarWithGeolocation(position.coords.latitude,
                    position.coords.longitude)
            }, ()=>{
                this.updateNavbarWithCityName(this.cityName);
            });

        }else {
            this.updateNavbarWithCityName(this.cityName);
        }
    };

    handleUpdteByGeoloaction() {
        htmlBody.on("updateByGeolocation", ()=>{

            this.cityName = "";
            htmlBody.trigger("hideCitySelectorModal");
        })
    }

    setNavbarSize(){
        let contentHeaderHeight = this.navbarElement.outerHeight();
        this.navbarWrapperElement.height(contentHeaderHeight);
        bodyHtml.trigger("navbarHeightChange", contentHeaderHeight)
    }

    hanldeResize(resizeMutationList, resizeObserver) {
        if (resizeMutationList.length > 0) {
            this.setNavbarSize();
        }
    }

    handleBodyResize(){
        bodyHtml.on("bodyMutated", ()=>{
            this.setNavbarSize();
        })
    }
}

class DateAndNameDayWidget {
    constructor(dateAndNamedayElement) {
        this.dateAndNamedayElement = dateAndNamedayElement;
        this.handleUpdateNavbar()
        this.handleUpdateNavbarWithoutAPI();
    }

    handleUpdateNavbar() {
        bodyHtml.on("updateNavbar", (event, updatedNavbarDataJson) => {
            this.dateAndNamedayElement.html(
                `${updatedNavbarDataJson.date}<br>${updatedNavbarDataJson.nameDay}`);

        })
    }

    handleUpdateNavbarWithoutAPI() {
        bodyHtml.on("updateNavbarWithoutAPI", (event, updatedNavbarData) => {
            this.dateAndNamedayElement.html(
                `${updatedNavbarData.date}<br>${updatedNavbarData.nameday}`);
        })
    }
}

class CitySelectorWidget {
    constructor(citySelectorElement) {
        this.citySelectorElement = citySelectorElement;
        this.handleUpdateNavbar()
    }

    handleUpdateNavbar() {
        bodyHtml.on("updateNavbar", (event, updatedNavbarDataJson) => {
            this.citySelectorElement.html(updatedNavbarDataJson.city);
        })
    }
}

class WeatherWidget {

    constructor(temperatureElement, weatherIconElement) {
        this.temperatureElement = temperatureElement;
        this.weatherIconElement = weatherIconElement;
        this.handleUpdateNavbar();
    }


    handleUpdateNavbar() {
        bodyHtml.on("updateNavbar", (event, updatedNavbarDataJson) => {
            this.temperatureElement.html(`${updatedNavbarDataJson.metricTemperature}°C`);
            this.weatherIconElement.attr('src', updatedNavbarDataJson.weatherIconUrl);
        })
    }
}

class CitySelectorModal {
    constructor(citySelectorModalElement) {
        this.citySelectorModalElement = citySelectorModalElement;
        this.citySelectorModalElement.searchbar = this.citySelectorModalElement.find('.city-searchBar')
        this.citySelectorModalElement.citiesResultDiv = this.citySelectorModalElement.find('.cities')
        this.citySelectorModalElement.cityChoosenButton = this.citySelectorModalElement.find('.city-choosen-btn')
        this.citySelectorModalElement.cityLink = this.citySelectorModalElement.find('.city')
        this.handleHideCitySelectorModal();
        this.handleCitySelectionByLink(this.citySelectorModalElement.cityLink)
        this.handleCitySelectionBySubmit();
        this.handleSearchbarChange();
        this.handleInvalidCityname();
    }

    handleHideCitySelectorModal() {
        bodyHtml.on("hideCitySelectorModal", () => {
            this.citySelectorModalElement.modal("hide");
        })
    }

    handleCitySelectionByLink(cityLink) {
        cityLink.click((event)=>{
            event.preventDefault();

            const cityName = $(event.target).html();

            bodyHtml.trigger("updateNavbarByCityname", cityName);
            bodyHtml.one("updateNavbar", ()=>{
                this.citySelectorModalElement.modal("hide");
            })
        });
    }

    handleCitySelectionBySubmit() {
        this.citySelectorModalElement.cityChoosenButton.click((event) => {
            event.preventDefault();

            const cityName = this.citySelectorModalElement.searchbar.val();
            bodyHtml.trigger("updateNavbarByCityname", cityName);
        });
    }

    handleSearchbarChange() {
        let cityToSearch;
        this.citySelectorModalElement.searchbar.on('input',
            () => {
                this.citySelectorModalElement.searchbar.removeClass('is-invalid');
                cityToSearch = this.citySelectorModalElement.searchbar.val().toLowerCase();

                if (cityToSearch === "") {
                    this.citySelectorModalElement.citiesResultDiv.children().remove();
                    return;
                }
                $.get(`/citysearch?cityName=${cityToSearch}`, (citySearchResultList) => {
                    this.citySelectorModalElement.citiesResultDiv.children().remove();

                    let cityLink;
                    for (let cityNum in citySearchResultList) {
                        cityLink = document.createElement('a');
                        cityLink.innerHTML = citySearchResultList[cityNum];
                        cityLink.className = "city";
                        this.citySelectorModalElement.citiesResultDiv.append(cityLink);
                        this.citySelectorModalElement.citiesResultDiv.append('<br>');
                        this.handleCitySelectionByLink($(cityLink));
                    }
                });
            });
    }

    handleInvalidCityname() {
        bodyHtml.on("invalidCityname", () => {
            this.citySelectorModalElement.searchbar.addClass("is-invalid");
        })
    }
}

class SidebarCollapserWidget {

    constructor(sidbarCollapserButton) {
        this.sidbarCollapserButton = sidbarCollapserButton;
        this.handleSidbarCollapserButtonClick();

    }

    handleSidbarCollapserButtonClick() {
        this.sidbarCollapserButton.click(() => {
            let slideType = "";

            this.sidbarCollapserButton.toggleClass('down');
            if (this.sidbarCollapserButton.hasClass('down'))
                slideType = "slideIn";
            else
                slideType = "slideOut";
            bodyHtml.trigger("slideSidebar", slideType);
            bodyHtml.trigger("setMobileContent");
        })
    }
}

export {
    SidebarCollapserWidget,
    CitySelectorModal,
    CitySelectorWidget,
    DateAndNameDayWidget,
    Navbar,
    WeatherWidget
};