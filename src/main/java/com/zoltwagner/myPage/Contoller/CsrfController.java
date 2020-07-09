/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.zoltwagner.myPage.Contoller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Zoltán
 */
@Controller
public class CsrfController {
    @GetMapping("/csrf")
    @ResponseBody
    public CsrfToken getToken(CsrfToken token) {
        return token;
    }
}

