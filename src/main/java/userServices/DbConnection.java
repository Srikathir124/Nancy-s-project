package userServices;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import adminServices.BaseData;

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
		String query = "insert into Users(UserId,Password,RoleId) values('" + userid + "','" + password + "'," + roleid
				+ ");";
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
			while (rs.next()) {
				Statement stmt1 = conn.createStatement();
				String associateID = rs.getString("Associate ID");
				query = "update Base set `Associate Dept` = case when `FIN Department ID` = '500646' and `IsActive` = 1 and `Associate ID` = '"
						+ associateID + "' then 'CoE' else 'Non CoE' end;";
				stmt1.execute(query);
				String homeManagerID = rs.getString("Home Manager ID");
				query = "select `Associate Name`,`Associate Dept` from Base where `Associate ID` = '" + homeManagerID
						+ "' and `IsActive`=1;";
				Statement stmt2 = conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery(query);
				while (rs2.next()) {
					query = "update Base set `HCM Name` = '" + rs2.getString("Associate Name") + "', `HCM Dept` = '"
							+ rs2.getString("Associate Dept") + "' where `Associate ID` = '" + associateID
							+ "' and `IsActive` = '1';";
					stmt1.execute(query);
				}
				query = "update Base set `Area` = (select `Area` from Master where `Associate ID` = '" + associateID
						+ "'), " + "`Leader` = (select `Leader` from Master where `Associate ID` = '" + associateID
						+ "') " + "where `Associate ID` ='" + associateID + "' and `IsActive` = '1';";
				stmt1.execute(query);
				query = "update Base set `SGA` = case when `Project ID` in "
						+ "('1000329415', '1000319167', '1000360430', '1000360428', '1000320437') and `IsActive` = 1 and `Associate ID` = '"
						+ associateID + "' then 'Yes' else 'No' end;";
				stmt1.execute(query);
				float fte = Integer.parseInt(rs.getString("Percent Allocation")) / 100;
				float cost = fte * Integer.parseInt(rs.getString("Grade"));
				query = "update Base set `FTE` = '" + fte + "', `Cost for SGA` = '" + cost
						+ "' where `Associate Id` = '" + associateID + "' and `IsActive`=1;";
				stmt1.execute(query);
				query = "update Base set `Current Status for SGA` = case when `Project ID` in "
						+ "('1000329415', '1000319167', '1000360430', '1000360428', '1000320437') and `IsActive` = 1 and `Associate ID` = '"
						+ associateID + "' then 'Yes' else 'No' end;";
				stmt1.execute(query);

			}

		} catch (Exception e) {
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

	public ArrayList<BaseData> getBaseData() {
		try {
			ArrayList<BaseData> res = new ArrayList<BaseData>();
			Statement stmt = conn.createStatement();
			String query1 = "select * from Base where `IsActive` = '1'";
			ResultSet rs = stmt.executeQuery(query1);
			while (rs.next()) {
				BaseData bd = new BaseData();
				bd.setSrNo(rs.getString("Sr.No."));
				bd.setAssociateID(rs.getString("Associate ID"));
				bd.setAssociateName(rs.getString("Associate Name"));
				bd.setGradeHR(rs.getString("Grade HR"));
				bd.setAssociateDept(rs.getString("Associate Dept"));
				bd.setOnOff(rs.getString("On/Off"));
				bd.setHomeManagerID(rs.getString("Home Manager ID"));
				bd.setHCMName(rs.getString("HCM Name"));
				bd.setHCMDept(rs.getString("HCM Dept"));
				bd.setArea(rs.getString("Area"));
				bd.setLeader(rs.getString("Leader"));
				bd.setSGA(rs.getString("SGA"));
				bd.setFTE(rs.getString("FTE"));
				bd.setCostforSGA(rs.getString("Cost for SGA"));
				bd.setCurrentStatusforSGA(rs.getString("Current Status for SGA"));
				bd.setProjectID(rs.getString("Project ID"));
				bd.setProjectDescription(rs.getString("Project Description"));
				bd.setPercentAllocation(rs.getString("Percent Allocation"));
				bd.setAssignmentStartDate(rs.getString("Assignment Start Date"));
				bd.setDateofJoining(rs.getString("Date of Joining"));
				bd.setFINDepartmentID(rs.getString("FIN Department ID"));
				bd.setDepartmentName(rs.getString("Department Name"));
				bd.setProjectBillability(rs.getString("Project Billability"));
				bd.setProjectType(rs.getString("Project Type"));
				bd.setProjectStatus(rs.getString("Project Status"));
				bd.setProject_Solution_Type(rs.getString("Project_Solution_Type"));
				bd.setProjectManagerID(rs.getString("Project Manager ID"));
				bd.setProjectManagerName(rs.getString("Project Manager Name"));
				bd.setAccountID(rs.getString("Account ID"));
				bd.setAccountName(rs.getString("Account Name"));
				bd.setParentCustomer(rs.getString("Parent Customer"));
				bd.setPoolDescription(rs.getString("Pool ID"));
				bd.setPoolDescription(rs.getString("Pool Description"));
				bd.setJobCode(rs.getString("JobCode"));
				bd.setDesignation(rs.getString("Designation"));
				bd.setGrade(rs.getString("Grade"));
				bd.setAssociateBaseHiringLocation(rs.getString("Associate Base Hiring Location"));
				bd.setHCMSetID(rs.getString("HCM SetID"));
				bd.setProjectOwningDepartment(rs.getString("Project Owning Department"));
				bd.setProjectOwningPractice(rs.getString("Project Owning Practice"));
				bd.setVertical(rs.getString("Vertical"));
				bd.setBillabilityStatus(rs.getString("Billability Status"));
				bd.setPrimaryStateTag(rs.getString("Primary State Tag"));
				bd.setAssignmentID(rs.getString("Assignment ID"));
				bd.setSOID(rs.getString("SO ID"));
				bd.setSOLine(rs.getString("SO Line"));
				bd.setCriticalFlag(rs.getString("Critical Flag"));
				bd.setLocationID(rs.getString("Location ID"));
				bd.setSEZFlag(rs.getString("SEZ Flag"));
				bd.setCountry(rs.getString("Country"));
				bd.setState(rs.getString("State"));
				bd.setCity(rs.getString("City"));
				bd.setAssignmentEndDate(rs.getString("Assignment End Date"));
				bd.setAssignmentStatus(rs.getString("Assignment Status"));
				bd.setProjectRole(rs.getString("Project Role"));
				bd.setOperationalRole(rs.getString("Operational Role"));
				bd.setAssignmentlocation(rs.getString("Assignment location"));
				bd.setAssgnCity(rs.getString("Assgn City"));
				bd.setAssgnState(rs.getString("Assgn State"));
				bd.setAssgnCountry(rs.getString("Assgn Country"));
				bd.setLocationReasoncode(rs.getString("Location Reason code"));
				bd.setProjectStartDate(rs.getString("Project Start Date"));
				bd.setProjectEndDate(rs.getString("Project End Date"));
				bd.setSubVertical(rs.getString("Sub Vertical"));
				bd.setSBU1(rs.getString("SBU1"));
				bd.setSBU2(rs.getString("SBU2"));
				bd.setContractorEndDate(rs.getString("Contractor End Date"));
				bd.setComment(rs.getString("Comment"));
				bd.setPoolResource(rs.getString("Pool Resource"));
				bd.setCompetency(rs.getString("Competency"));
				bd.setSecondaryStateTag(rs.getString("Secondary State Tag"));
				res.add(bd);
			}
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void logDownload(String log) {
		String query = "insert into Tracker (`DownloadLogs`) values ('"+log+"');";
		try {	conn.createStatement().execute(query);	}
		catch (SQLException e) {	e.printStackTrace();	}
	}
	
	public ArrayList<String> getDownloadLogs(){
		ArrayList arr = new ArrayList();
		try {	
			ResultSet rs = conn.createStatement().executeQuery("select Downloadlogs from Tracker;");
			while(rs.next()) {
				arr.add(rs.getString("DownloadLogs"));
			}
		}
		catch (SQLException e) {	e.printStackTrace();	}
		return arr;
	}
}
