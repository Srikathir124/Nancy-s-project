package com.cognizant.AllocationApp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import userServices.Credentials;
import userServices.User;
import userServices.UserServices;


@CrossOrigin(origins="http://localhost:4200/")
@RestController
public class UserServicesController {

	private UserServices userService = new UserServices();
	
	@Value("${app.name}")
	private String AppName;
	
	@GetMapping("")
	String helloworld() {
		return AppName;
	}
	
	@PostMapping("add-user")
	String addUserRequest(@RequestBody Credentials cred){
		System.out.println("Received add user request");
		String response = userService.addUser(cred.getUserId(),cred.getPassword(),cred.getRoleId());
		System.out.println(response);
		return(response);
	}
	
	@PostMapping("authenticate")
	User authenticate(@RequestBody Credentials cred) {
		System.out.println("Received login request");
		User response = userService.authenticate(cred.getUserId(),cred.getPassword());
		return response;
	}
	
}