package userServices;

public class Credentials {
	private String userId;
	private String password;
	private int roleId;
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleid) {
		this.roleId = roleid;
	}
	public Credentials(String userId, String password) {
		super();
		this.userId = userId;
		this.password = password;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
