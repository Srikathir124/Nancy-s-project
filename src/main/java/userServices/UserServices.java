package userServices;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import adminServices.BaseData;

@Component
public class UserServices {
	
	private static EncryptionService encryptionService = new EncryptionService();
	public String addUser(String string, String password,int role) {
		password = encryptionService.encrypt(password);
		DbConnection dbc = new DbConnection();
		try {
			if(!dbc.checkUserExists(string)) {
				dbc.insertUser(string, password,role);
				return "Success";//User Added
			}
			else {
				return "User Already Exists";//User Exists
			}
		} 
		catch (Exception e) {e.printStackTrace();}
		finally {dbc.closeDbConnection();}
		return "Error Occured";//Error Occurred
	}

	public User authenticate(String string, String password) {
		password = encryptionService.encrypt(password);
		DbConnection dbc = new DbConnection();
		try {
			if(dbc.checkUserExists(string)) {
				User user = dbc.authenticate(string, password);
				return user;
			}
			else {
				return new User("User Not Found");
			}
		}
		catch (Exception e) {e.printStackTrace();}
		finally {dbc.closeDbConnection();}
		return new User("Error Occcured");
	}
	
	public ArrayList<BaseData> getBaseData() {
		DbConnection dbConnection = new DbConnection();
		try {
			return dbConnection.getBaseData();
		}
		catch (Exception e) {e.printStackTrace();}
		finally {dbConnection.closeDbConnection();}
		return null;
	}
	
	public void logDownload(String log) {
		DbConnection dbc = new DbConnection();
		try {
			dbc.logDownload(log);
		}
		catch (Exception e) {e.printStackTrace();}
		finally {dbc.closeDbConnection();}
	}
	
}