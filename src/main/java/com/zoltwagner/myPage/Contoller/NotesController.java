/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.zoltwagner.myPage.Contoller;

import com.zoltwagner.myPage.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Zoltán
 */
@Controller
@RequestMapping("/notes")
public class NotesController {

	@Autowired
	UserService userService;

	@GetMapping
	//@PreAuthorize("hasRole('INSPECTOR') or hasRole('ADMIN')")
	public String getViewableNotes() {
		return "notes";
	}
}
