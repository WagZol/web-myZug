/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.zoltwagner.myPage.Contoller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 *
 * @author Zoltán
 */
@Controller
    public class LogoutController {     
	@GetMapping("/logout")
	public ResponseEntity logoutMessage() {
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	
}

