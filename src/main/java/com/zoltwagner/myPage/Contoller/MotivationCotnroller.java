/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.zoltwagner.myPage.Contoller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 *
 * @author Zoltán
 */
@Controller
    public class MotivationCotnroller {     
        @RequestMapping("/motivation")
        public String getMotivationPage(){
            return "motivation";
        }
}

