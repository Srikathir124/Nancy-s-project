package adminServices;

import java.util.ArrayList;

import userServices.DbConnection;

public class AdminServices {

	public void updateMasterSheet(ArrayList<ArrayList<String>> masterSheet) {
		DbConnection dbConnection = new DbConnection();
		try {
			dbConnection.updateMasterSheet(masterSheet);
		}
		catch (Exception e) {e.printStackTrace();}
		finally {dbConnection.closeDbConnection();}
	}
	
	public void updateBaseSheet(ArrayList<ArrayList<String>> masterSheet) {
		DbConnection dbConnection = new DbConnection();
		try {
			dbConnection.updateBaseSheet(masterSheet);
		}
		catch (Exception e) {e.printStackTrace();}
		finally {dbConnection.closeDbConnection();}
	}
	
	public ArrayList<String> getDownloadLogs(){
		DbConnection dbConnection = new DbConnection();
		try {
			return dbConnection.getDownloadLogs();
		}
		catch (Exception e) {e.printStackTrace();}
		finally {dbConnection.closeDbConnection();}
		return null;
	}
}
