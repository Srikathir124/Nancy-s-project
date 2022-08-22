package com.cognizant.AllocationApp;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import adminServices.AdminServices;

@CrossOrigin(origins="http://localhost:4200/")
@RestController
public class AdminServicesController {
	
	private AdminServices adminService = new AdminServices();
	
	@PostMapping("update-basesheet")
	void updateBaseSheet(@RequestBody ArrayList<ArrayList<String>> baseSheet) {
		adminService.updateBaseSheet(baseSheet);
	}
	
	@PostMapping("update-mastersheet")
	void updateMasterSheet(@RequestBody ArrayList<ArrayList<String>> masterSheet) {
		adminService.updateMasterSheet(masterSheet);
	}
}
