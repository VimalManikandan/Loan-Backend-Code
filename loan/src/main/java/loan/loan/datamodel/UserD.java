package loan.loan.datamodel;


public class UserD {

	private int uid;
	private String username;
	private String userpwd;
	private String usertype;
	private String jwtToken;

	public UserD() {
		super();

	}

	public UserD(int uid, String username, String userpwd, String usertype,String jwtToken) {
		super();
		this.uid = uid;
		this.username = username;
		this.userpwd = userpwd;
		this.usertype = usertype;
		this.jwtToken=jwtToken;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", userpwd=" + userpwd + ", usertype=" + usertype + "]";
	}

}
