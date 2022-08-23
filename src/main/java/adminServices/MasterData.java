package adminServices;

public class MasterData {
	private String AssociateID;
	private String AssociateName;
	private String HomeManagerID;
	private String HCMName;
	private String HCMDept;
	private String Area;
	private String Leader;
	private String Initiative;
	
	public MasterData(String associateID, String associateName, String homeManagerID, String hCMName, String hCMDept,
			String area, String leader, String initiative) {
		super();
		AssociateID = associateID;
		AssociateName = associateName;
		HomeManagerID = homeManagerID;
		HCMName = hCMName;
		HCMDept = hCMDept;
		Area = area;
		Leader = leader;
		Initiative = initiative;
	}
	public String getAssociateID() {
		return AssociateID;
	}
	public void setAssociateID(String associateID) {
		AssociateID = associateID;
	}
	public String getAssociateName() {
		return AssociateName;
	}
	public void setAssociateName(String associateName) {
		AssociateName = associateName;
	}
	public String getHomeManagerID() {
		return HomeManagerID;
	}
	public void setHomeManagerID(String homeManagerID) {
		HomeManagerID = homeManagerID;
	}
	public String getHCMName() {
		return HCMName;
	}
	public void setHCMName(String hCMName) {
		HCMName = hCMName;
	}
	public String getHCMDept() {
		return HCMDept;
	}
	public void setHCMDept(String hCMDept) {
		HCMDept = hCMDept;
	}
	public String getArea() {
		return Area;
	}
	public void setArea(String area) {
		Area = area;
	}
	public String getLeader() {
		return Leader;
	}
	public void setLeader(String leader) {
		Leader = leader;
	}
	public String getInitiative() {
		return Initiative;
	}
	public void setInitiative(String initiative) {
		Initiative = initiative;
	}
	

}
