import {htmlToElement} from '/js/moduls/utils.js';

const bodyHtml = $('body');

class Authentication {
    constructor(authNeededPages) {
        this.authNeededPages=authNeededPages;
        this.handleLogoutUser()
    }

    handleLogoutUser() {
        bodyHtml.on("logout", (event, logoutUrl) => {
            $.ajax({
                url: logoutUrl,
                type: 'POST',
                async: true,
                success: ()=>{
                    bodyHtml.trigger("updateSidebarUserdata");
                    bodyHtml.trigger("backToHomeFromAuthNeededPages", {pages: this.authNeededPages})

                    bodyHtml.trigger("refreshCSRFToken")
                }
            });
        });
    }
}

class LoginModal {
    constructor(modalElement) {
        this.modalElement = modalElement;
        this.loginForm = this.modalElement.find("form");
        this.loginCheckUrl = this.loginForm.attr("action");
        this.loginformInputs = this.modalElement.find("input");
        this.loginTarget = this.loginForm.attr("targetUrl");
        this.loginModalBody = this.modalElement.find(".modal-body");
        this.passwordResetInputs = this.modalElement.find("input#userName");

        this.handleLogin();
        this.handleLoginSuccess();
        this.handleInputChange();
        this.handleLoginSubmit();
        this.handlePasswordReset();
    }

    handleLogin() {
        bodyHtml.on("login", (event, urlToGoAfterLogin) => {
            this.modalElement.modal("show");
            this.modalElement.find("form").attr("targetUrl", urlToGoAfterLogin);
        })
    }


    handleLoginSuccess() {
        this.modalElement.on("loginSucces", (event, redirectUrl) => {
            this.modalElement.modal("hide");
            bodyHtml.trigger("updateSidebarUserdata");

            if (redirectUrl) {
                bodyHtml.trigger("refreshContent", redirectUrl);
            }
            bodyHtml.trigger("refreshCRSFToken");
        })
    }

    handleLoginSubmit() {
        this.modalElement.on("submit", (event) => {
            event.preventDefault();

            this.loginForm=this.modalElement.find("form");
            this.loginformInputs=this.modalElement.find(".form-group input")

            $.ajax({
                url: this.loginCheckUrl,
                type: 'POST',
                async: true,
                data: this.loginformInputs.serialize(),
                success: (checkedForm)=>{
                    const checkedFormElement = htmlToElement(checkedForm);
                    const loginFormHasErrors = checkedFormElement.querySelectorAll(".invalid-feedback").length > 0;

                    if (!loginFormHasErrors) {
                        this.modalElement.trigger("loginSucces", this.loginTarget);
                    }

                    this.loginForm.replaceWith(checkedFormElement);
                }
            })
        })
    }

    handleInputChange() {
        this.modalElement.on("input", (event) => {
            $(event.target).removeClass('is-invalid');
        })
    }

    handlePasswordReset() {
        bodyHtml.on("passwordReset", (event) => {
            event.preventDefault();

            this.passwordResetInputs=this.modalElement.find("input");
            this.loginModalBody = this.modalElement.find(".modal-body");

            $.ajax({
                url: "/passwordReset",
                type: 'POST',
                async: true,
                data: this.passwordResetInputs.serialize(),
                success: (checkedModalBody) => {
                    this.loginModalBody.replaceWith(checkedModalBody);
                }
            })
        })
    }
}

export{
    Authentication,
    LoginModal
}