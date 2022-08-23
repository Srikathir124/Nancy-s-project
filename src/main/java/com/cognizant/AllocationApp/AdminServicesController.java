package com.cognizant.AllocationApp;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import adminServices.AdminServices;
import adminServices.BaseData;

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
	
	@GetMapping("get-download-logs")
	ArrayList<String> getDownloadLogs(){
		return adminService.getDownloadLogs();
	}
}
