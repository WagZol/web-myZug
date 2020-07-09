package com.zoltwagner.myPage.Service;

import com.zoltwagner.myPage.Dao.PasswordResetToken;
import com.zoltwagner.myPage.Repository.PasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class SecurityService implements ISecurityService{

    @Autowired
    PasswordTokenRepository passwordTokenRepository;

//    @Autowired
//    InspectorTokenRepository inspectorTokenRepository;

    @Override
    public boolean isPasswordResetTokenValid(String token) {
        PasswordResetToken passwordTokenDao=getPasswordResetTokenDao(token);
        if (passwordTokenDao==null) {
            return false;
        }
        return isPasswordResetTokenExpired(passwordTokenDao);
    }

//    @Override
//    public boolean isInspectorTokenValid(String token) {
//        InspectorToken inspectorTokenDao=getInspectorTokenDao(token);
//        if (inspectorTokenDao==null) {
//            return false;
//        }
//        return isInspectorTokenExpired(inspectorTokenDao);
//    }

//    private InspectorToken getInspectorTokenDao(String token) {
//        return inspectorTokenRepository.findByToken(token);
//    }

    private PasswordResetToken getPasswordResetTokenDao(String token){

        return passwordTokenRepository.findByToken(token);
    }

//    private boolean isInspectorTokenExpired(InspectorToken token) {
//        final Calendar cal = Calendar.getInstance();
//        System.out.println(cal.getTime());
//        System.out.println(token.getExpiryDate());
//        System.out.println(token.getExpiryDate().before(cal.getTime()));
//        return !(token.getExpiryDate().before(cal.getTime()));
//    }

    private boolean isPasswordResetTokenExpired(PasswordResetToken token){
        final Calendar cal = Calendar.getInstance();
        return !(token.getExpiryDate().before(cal.getTime()));
    }
}
