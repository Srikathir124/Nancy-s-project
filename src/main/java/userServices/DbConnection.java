package userServices;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnection {

	private Connection conn = null;

	public DbConnection() {
		try {
			FileReader fr = new FileReader("./src/main/resources/application.properties");
			Properties prop = new Properties();
			prop.load(fr);
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.conn = DriverManager.getConnection(prop.getProperty("dbUrl"), prop.getProperty("dbUserId"),
					prop.getProperty("dbPassword"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeDbConnection() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean checkUserExists(String string) throws SQLException {
		Statement stmt = conn.createStatement();
		String query = "select UserId from Users;";
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			if (string.matches(rs.getString("UserId"))) {
				return true;
			}
		}
		System.out.println("User Not Found: " + string);
		return false;
	}

	public void insertUser(String userid, String password, int roleid) throws SQLException {
		Statement stmt = conn.createStatement();
		String query = "insert into Users(UserId,Password,RoleId) values('" +userid+ "','" +password+ "'," +roleid+ ");";
		stmt.execute(query);
		System.out.println("User Added");
	}

	public User authenticate(String userid, String password) throws SQLException {
		Statement stmt = conn.createStatement();
		String query = "select Password,RoleId from Users where UserId = '" + userid + "' ;";
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			if (password.matches(rs.getString("Password"))) {
				return new User(rs.getInt("RoleId"), "Valid Credentials");
			}
		}
		return new User("Invalid Credentials");
	}

	public void updateBaseSheet(ArrayList<ArrayList<String>> baseSheet) throws SQLException {
		Statement stmt = conn.createStatement();
		for (int i = 1; i < baseSheet.size(); i++) {
			StringBuilder sbr = new StringBuilder();
			if (isAssociateInTable(baseSheet.get(i).get(1), "Base")) {
				String query = "update Base set `IsActive`=0 where `Associate ID` = '" + baseSheet.get(i).get(1) + "';";
				stmt.execute(query);
			}
			sbr.append("Insert into Base (`IsActive`,");
			for (int j = 0; j < 61; j++) {
				sbr.append("`" + baseSheet.get(0).get(j) + "`, ");
			}
			sbr.replace(sbr.length() - 2, sbr.length(), ") values (1,");
			for (int j = 0; j < 61; j++) {
				sbr.append("'" + baseSheet.get(i).get(j) + "', ");
			}
			sbr.replace(sbr.length() - 2, sbr.length(), ");");
			stmt.execute(sbr.toString());
		}
		processTheData();
	}

	public void updateMasterSheet(ArrayList<ArrayList<String>> masterSheet) throws SQLException {
		Statement stmt = conn.createStatement();
		for (int i = 1; i < masterSheet.size(); i++) {
			StringBuilder sbr = new StringBuilder();
			if (isAssociateInTable(masterSheet.get(i).get(0), "Master")) {
				sbr.append("update Master set ");
				for (int j = 1; j < 8; j++) {
					sbr.append("`" + masterSheet.get(0).get(j) + "`= ");
					sbr.append("'" + masterSheet.get(i).get(j) + "', ");
				}
				sbr.replace(sbr.length() - 2, sbr.length(),
						" where `Associate ID` = '" + masterSheet.get(i).get(0) + "';");
				stmt.execute(sbr.toString());
			} else {
				sbr.append("Insert into Master (");
				for (int j = 0; j < 8; j++) {
					sbr.append("`" + masterSheet.get(0).get(j) + "`, ");
				}
				sbr.replace(sbr.length() - 2, sbr.length(), ") values (");
				for (int j = 0; j < 8; j++) {
					sbr.append("'" + masterSheet.get(i).get(j) + "', ");
				}
				sbr.replace(sbr.length() - 2, sbr.length(), ");");
				stmt.execute(sbr.toString());
			}
		}
		processTheData();
	}
	
	public void processTheData() {
		try {
			Statement stmt = conn.createStatement();
			String query = "select `Associate ID`,`Home Manager ID`,`Percent Allocation`,`Grade` from Base where IsActive = 1;";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Statement stmt1 = conn.createStatement();
				String associateID = rs.getString("Associate ID");
				query = "update Base set `Associate Dept` = case when `FIN Department ID` = '500646' and `IsActive` = 1 and `Associate ID` = '"
						+ associateID+ "' then 'CoE' else 'Non CoE' end;";
				stmt1.execute(query);
				String homeManagerID = rs.getString("Home Manager ID");
				query = "select `Associate Name`,`Associate Dept` from Base where `Associate ID` = '"+homeManagerID+"' and `IsActive`=1;";
				Statement stmt2 = conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery(query);
				while(rs2.next()) {
				query = "update Base set `HCM Name` = '"+rs2.getString("Associate Name")+"', `HCM Dept` ="+rs2.getString("Associate Dept");
				stmt1.execute(query);
				}
				query = "update Base set `Area` = (select `Area` from Master where `Associate ID` = '"+associateID+"'), "
						+"`Leader` = (select `Leader` from Master where `Associate ID` = '"+associateID+"') "
						+"where `Associate ID` ='"+associateID+"' and `IsActive` = '1';";			
				stmt1.execute(query);
				query = "update Base set `SGA` = case when `Project ID` in "
						+ "('1000329415', '1000319167', '1000360430', '1000360428', '1000320437') and `IsActive` = 1 and `Associate ID` = '"
						+ associateID + "' then 'Yes' else 'No' end;";
				stmt1.execute(query);
				float fte = Integer.parseInt(rs.getString("Percent Allocation"))/100;
				float cost = fte*Integer.parseInt(rs.getString("Grade"));
				query = "update Base set `FTE` = '" +fte+"', `Cost for SGA` = '"+cost+"' where `Associate Id` = '"+associateID+ "' and `IsActive`=1;";
				stmt1.execute(query);
				query = "update Base set `Current Status for SGA` = case when `Project ID` in "
						+ "('1000329415', '1000319167', '1000360430', '1000360428', '1000320437') and `IsActive` = 1 and `Associate ID` = '"
						+ associateID + "' then 'Yes' else 'No' end;";
				stmt1.execute(query);
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isAssociateInTable(String str, String tableName) {
		try {
			Statement stmt = conn.createStatement();
			String query1 = "select `Associate ID` from " + tableName + " where `Associate ID` = " + str;
			ResultSet rs = stmt.executeQuery(query1);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
