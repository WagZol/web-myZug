/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.zoltwagner.myPage.Contoller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.zoltwagner.myPage.Dto.UserProfileDto;
import com.zoltwagner.myPage.Dao.User;
import com.zoltwagner.myPage.Service.UserService;

import javax.validation.Valid;


/**
 *
 * @author Zoltán
 */
@Controller
    public class ProfileController {     
	
	@Autowired
	UserService userService;

	private User authenticatedUser;
	
	@GetMapping("/profile")
	public String getProfilePage() {
		return "profile";
	}
	
	@PostMapping("/profile")
	public String registerUserAccount(@ModelAttribute("authenticatedUser") @Valid UserProfileDto updatedUserProfile,
									  BindingResult result){
		if(updatedUserProfile.getPassword()=="")
			updatedUserProfile.setPassword(authenticatedUser.getPassword());

		User userWithSameNameInDatabase=userService.getUserModelByUsername(updatedUserProfile.getUserName());
		boolean isUserNameAlreadyInUse=userWithSameNameInDatabase!=null &&
				!(authenticatedUser.equals(userWithSameNameInDatabase));


		if (isUserNameAlreadyInUse)
			result.rejectValue("userName", null, "Ez a felhasználónév már foglalt!");

		if (result.hasErrors()){
			return "profile";
		}

		userService.updateUserModel(authenticatedUser.getId(), updatedUserProfile);
		userService.authenticateUser(updatedUserProfile.getUserName(), updatedUserProfile.getPassword());
		//user adat modositaskor automatikusan kijelentkeztet a szerver, igy a jobb felhasznalo elmeny miatt egy
		// automatikus visszza jelentkeztetes lett beillesztve

		return "redirect:/profile?state=success";
	}
	
	@ModelAttribute("authenticatedUser")
	  public UserProfileDto getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String authenticatedUserName = authentication.getName();
		UserProfileDto authenticatedUserProfile=userService.getUserProfileByName(authenticatedUserName);
		authenticatedUser=userService.getUserModelByUsername(authenticatedUserName);
		
		//A bejelentkezett user jelszava semmilyen korulmenyek kozott nem fog kikerulni a klienshez
		authenticatedUserProfile.setPassword("");
		return authenticatedUserProfile;
	  }

	
}

