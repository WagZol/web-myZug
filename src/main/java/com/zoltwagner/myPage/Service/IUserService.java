package com.zoltwagner.myPage.Service;

import com.zoltwagner.myPage.Service.Authentication.UserDetailsWithAvatar;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.zoltwagner.myPage.Dao.User;
import com.zoltwagner.myPage.Dto.UserProfileDto;
import com.zoltwagner.myPage.Dto.UserRegistrationDto;

public interface IUserService extends UserDetailsService {
	//A UserService kiboviti a UserDetailsService interface-t ami meghatarozza hogy minden ot implementalo service-ben legyen 
	//loadUserByUsername-t megvalosito metodus ami UserDetails-el ter vissza es egy username String-et var a bemenetere

    User save(UserRegistrationDto registration);
    User updateUserModel(Long id, UserProfileDto updatedProfile);
    void authenticateUser(String userName, String password);
    boolean checkCredentials(String userName, String password);
    void createPasswordResetTokenForUser(User user, String token);
    UserProfileDto getUserProfileByName(String userName);
    User getUserModelByPasswordResetToken(String passwordResetToken);
    User getUserModelById(Long id);
    User getUserModelByUsername(String userName);
    UserDetailsWithAvatar loadUserByUsername(String userName);

}
