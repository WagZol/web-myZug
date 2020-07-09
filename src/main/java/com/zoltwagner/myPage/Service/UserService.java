package com.zoltwagner.myPage.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import com.zoltwagner.myPage.Dao.PasswordResetToken;
import com.zoltwagner.myPage.Repository.PasswordTokenRepository;
import com.zoltwagner.myPage.Repository.RoleRepository;
import com.zoltwagner.myPage.Service.Authentication.UserDetailsWithAvatar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.zoltwagner.myPage.Dto.UserProfileDto;
import com.zoltwagner.myPage.Dto.UserRegistrationDto;
import com.zoltwagner.myPage.Dao.Role;
import com.zoltwagner.myPage.Dao.User;
import com.zoltwagner.myPage.Repository.UserRepository;


@Service
//A componentscan-nek jeloljuk hogy ez egy olyan bean ami a service retegben mukodik
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    //UserRepository tipus szerinti injektalasa. Ha tobb UserRepository class lenne hibat dobna

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    //BCryptPasswordEncoder tipus szerinti injektalasa. Ha tobb BCryptPasswordEncoder class lenne hibat dobna

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    
    public User save(UserRegistrationDto registration){
        User user = new User();
        Role userRole = roleRepository.findByName("user");

        user.setUserName(registration.getUserName());
        user.setEmail(registration.getEmail());
        user.setAvatar(registration.getAvatar());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setRoles(Arrays.asList(userRole));
        return userRepository.save(user);
    }

    @Override
    public UserDetailsWithAvatar loadUserByUsername(String userName) throws UsernameNotFoundException {
    	//ez a metodus betolti username alapjen(email) a user-t, ha nem lesz sikeres a betoltes jeloljuk throws-al hogy 
    	//UsernamNotFoundException lesz dobva, el kell azt kapnia az ot hivo metodusnak.
        User user = userRepository.findByUserName(userName);
        //megprobaljuk a repository findbyEmail metodussal megkeresni a user-t
        if (user == null){
        	//a repository a talalot egy User Entity-be(olyan Bean ami az adatbazis 1 rekodrjanak adatait tartalmazza) menti
            throw new UsernameNotFoundException("Invalid username or password.");
            //Ha a repository nem talalja a user-t es null erteket dob a User Entity akkor UsernameNotFoundException-t dobunk
        }
        return new UserDetailsWithAvatar(user.getUserName(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()),
                user.getAvatar());
        //Ha a repository talalt a kriteriumnak megfelelo user-t, akkor egy altalunk meghatorozott User Entity class-bol kikerjuk a user adatait, tobbek kozt a role-t is és az itt megtalahto
        //mapRolesToAuthorities privat metodussal autentikacio listava alakitjuk azt, es ezzel egyutt a user minden ertkevel letrehozunk egy uj 
        //SpringSecurityban mar elore megirt user peldanyt. Ez a user mar kezelni tudja az autentikaciokat, osszehasonlítasokat, lock.olast stb stb.
        
    }
    
    public User getUserModelByUsername(String userName) throws UsernameNotFoundException{
    	User userModel=userRepository.findByUserName(userName);
    	/*if (userModel == null){
        	throw new UsernameNotFoundException("Invalid username or password.");
        }*/
    	return userModel;
    }
    
    public User getUserModelById(Long id){
    	Optional<User> userModel=userRepository.findById(id);
    	if (userModel.isPresent()){
    		return userModel.get();
        }
    	throw new UsernameNotFoundException("Invalid id");  	
    }

    public User getUserModelByPasswordResetToken(String passwordResetToken){
        PasswordResetToken passwordResetTokenDao=passwordTokenRepository.findByToken(passwordResetToken);
        return passwordResetTokenDao.getUser();
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
    	//Ez a metudos megkapja a user role-jait collection formajaban. Minden role tartalmaz egy id-t es egy megnevezest.
    	//A metodus visszateresi erteke egy olyan lista aminek minden eleme egy szerepkor nev alapjan peldanyositott SimpleGrantedAuthority object peldany.
    	//Minden ilyen peldany a GrantedAuthority interface-t valositja meg, igy ha el akarjuk kerlni a folytonos modositast arra az esetre ha
    	//masfele Authority class-t hasznalnank, azt adjuk meg GENERIC-el a visszateresi erteknek hogy egy olyan Collection aminek tagjai a GrantedAuthority "utodjai"
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    public UserProfileDto getUserProfileByName(String userName){
        User userModel=userRepository.findByUserName(userName);

        UserProfileDto authenticatedUserProfile=new UserProfileDto(
                userModel.getUserName(),
                userModel.getEmail(),
                userModel.getPassword(),
                userModel.getAvatar()
        );
        return authenticatedUserProfile;
    }

	@Override
	public User updateUserModel(Long authenticatedUserId, UserProfileDto updatedUserProfile) {
		User userToUpdate=getUserModelById(authenticatedUserId);
		userToUpdate.setAvatar(updatedUserProfile.getAvatar());
		userToUpdate.setUserName(updatedUserProfile.getUserName());
		userToUpdate.setEmail(updatedUserProfile.getEmail());
		return userRepository.save(userToUpdate);
	}

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    public void authenticateUser(String userName, String password) {
        UserDetails userDetails = loadUserByUsername(userName);
        Authentication authentication
                = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
    }


    public boolean checkCredentials(String userName, String password){
        User registeredUser=getUserModelByUsername(userName);
        if (registeredUser==null)
            return false;
        boolean isPasswordsMatch=passwordEncoder.matches(password, registeredUser.getPassword());
        return isPasswordsMatch;
    }


}
