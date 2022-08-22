package userServices;

public class User {
	public int roleid;
	public String status;
	public String token;
	public User(String status) {
		this.status=status;
	}
	public User(int i, String status) {
		this.roleid=i;
		this.status=status;
	}
	
}
