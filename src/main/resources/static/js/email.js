$(document).ready(function() {
	const formInputs=$('#email-form .form-group input, textarea');

	submitEmailForm($('#email-form'), formInputs)
	handleMessageSendSucces($("meta[name='state']"), $('#modal-activator-button'));
	handleFormInputChange(formInputs);

});

function submitEmailForm(form, formInputs){
	form.submit(event=>{
		event.preventDefault();

		$.ajax({
			type: "POST",
			url: form.attr("action"),
			async: true,
			data: formInputs.serialize(),
			success: function (newContent) {
				htmlBody.trigger("refreshContent", newContent);
			}
		});
	});
};

function handleFormInputChange(formInputs){
	formInputs.on('input', function(event){
		event.preventDefault()

		event.target.classList.remove("is-invalid");
	})
}

function handleMessageSendSucces(stateMeta, modalActivatorButton){
	if (stateMeta.attr("content")=="success")
		modalActivatorButton.click();
}
