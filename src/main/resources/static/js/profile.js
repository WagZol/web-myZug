
(function() {
	const pond=createFilepond('#form-profile-avatar-upload-filepond');
	const pondRoot=$('#profile-form .filepond--root');
	const defaultAvatar=$('#profile-form #filepond--default-avatar');
	const pondFileInput=$('#form_profile_avatar_upload');
	const formInputs=$('#profile-form .form-group input');



	loadOriginalUserAvatar(pondFileInput, pond, handleFilepondFileAdd);
	handleFilepondFileAdd(pondRoot, pondFileInput, defaultAvatar);
	handleFilepondFileRemove(pondRoot, pondFileInput, defaultAvatar);
	handleFilepondFileError(pondRoot, pond.labelFileTypeNotAllowed);
	addDefaultAvatar("#profile-form .filepond--drop-label")
	handleFormInputChange(formInputs, $('#profile-form .btn-send'));
	submitProfileForm($('#profile-form'), formInputs);
	handlePasswordChange($("input#form_profile_password"), $("#password-confirmation-row .form-group"))

	
	
	
	
		
})();

function addDefaultAvatar(filepondLabelSelector){
	const defaultavatarImage=new Image();
	defaultavatarImage.src="/image/defaultAvatar.png";
	defaultavatarImage.id="filepond--default-avatar";
	
	$(filepondLabelSelector).append(defaultavatarImage);
}

function loadOriginalUserAvatar(avatarFileInput, avatarFilepond){
	const avatarImage=avatarFileInput.val();
	
	if (avatarImage) {
		avatarFilepond.addFile(avatarImage);
	}
}

function handleFormInputChange(formInputs, submitButton){
	formInputs.on('input', function(event){
		event.preventDefault()

		event.target.classList.remove("is-invalid");

		if (event.target.type=="password")
			formInputs.filter("[type='password']").removeClass("is-invalid");

		submitButton.prop('disabled', false);
	})
}

function handlePasswordChange(passwordInput, passwordConfirmation){

	const passwordComfirmationInput=passwordConfirmation.find("input");

	//Init
	if (passwordInput.val() || passwordComfirmationInput.hasClass('is-invalid'))
		passwordConfirmation.css("display", "block");
	else
		passwordConfirmation.css("display", "none");


	passwordInput.on("input", ()=>{
		if (passwordInput.val())
			passwordConfirmation.css("display", "block");
		else
			passwordConfirmation.css("display", "none");
	})
}

function handleFilepondFileAdd(filepondRoot, avatarFileInput, defaultAvatar){

	filepondRoot.on('FilePond:addfile', e => {
		const fileStatus=e.detail.file.status;

		//Status 2=> fajl sikeresen betoltodott
		if (fileStatus=="2"){
			const uploadedImage=e.detail.file.getFileEncodeDataURL();
			const originalAvatar=avatarFileInput.val();
			avatarFileInput.val(uploadedImage);

			defaultAvatar.css('display', 'none');

			if(originalAvatar!=uploadedImage){
				avatarFileInput.trigger("input");
			}
		}

	});
}

function handleFilepondFileError(filepondRoot, originalErrorLabelMeassage){

	const errorLabel=document.createElement("div");
	errorLabel.id="filepond-error-label";
	errorLabel.classList.add("center");


	filepondRoot.on('FilePond:error', e => {
		errorLabel.textContent=originalErrorLabelMeassage;
		$('.filepond--file').append(errorLabel);
	})
}

function handleFilepondFileRemove(filepondRoot, avatarFileInput, defaultAvatar){
	filepondRoot.on('FilePond:removefile', e=>{
		defaultAvatar.css('display', 'block');
		avatarFileInput.val("");
		avatarFileInput.trigger("input");
	})
}
function submitProfileForm(form, formInputs){
	form.submit(event=>{
		event.preventDefault();

		$.ajax({
			type: "POST",
			url: form.attr("action"),
			async: true,
			data: formInputs.serialize(),
			success: function (newContent) {
				htmlBody.trigger("refreshContent", newContent);
				htmlBody.trigger("updateSidebarUserdata");
			}
		});
	});
};

function createFilepond(inputSelectorForFilepond){
	/*
	 * We need to register the required plugins to do image manipulation and
	 * previewing.
	 */
	FilePond.registerPlugin(
		// encodes the file as base64 data
	  FilePondPluginFileEncode,
		
		// validates files based on input type
	  FilePondPluginFileValidateType,
	  
		// create poster from file
	  FilePondPluginFilePoster,
		
		// corrects mobile image orientation
	  FilePondPluginImageExifOrientation,
		
		// previews the image
	  FilePondPluginImagePreview,
		
		// crops the image to a certain aspect ratio
	  FilePondPluginImageCrop,
		
		// resizes the image to fit a certain size
	  FilePondPluginImageResize,
		
		// applies crop and resize information on the client
	  FilePondPluginImageTransform
	  
	);
	
			// Select the file input and use create() to turn it into a pond
			// in this example we pass properties along with the create method
			// we could have also put these on the file input element itself
	 return FilePond.create(
		document.querySelector(inputSelectorForFilepond),
		{
			labelIdle: `Húzd ide a képet vagy <span class="filepond--label-action">Böngészd ki</span>`,
			labelFileTypeNotAllowed: `Csak .jpg, .png, .gif fájltípusok engedélyezettek!`,
		    imagePreviewHeight: 140,
		    imageCropAspectRatio: '1:1',
		    imageResizeTargetWidth: 140,
		    imageResizeTargetHeight: 140,
		    stylePanelLayout: 'compact circle',
		    styleLoadIndicatorPosition: 'center bottom',
		    styleButtonRemoveItemPosition: 'center bottom',
		}		
	);
}


