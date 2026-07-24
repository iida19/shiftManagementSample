package shiftManagementSample;

public class UserBean {
	
	
	private String userId;
	private String userName;
	private String password;
	private String role;
	
	
	public UserBean() {}
	
	// データベースからの読み込み時
	public UserBean( String userId, String userName, String password, String role ) {
			
		this.setUserId( userId );
		this.setUserName( userName );
		this.setPassword( password );
		this.setRole( role );
			
	}
	
	// 新規登録時
	public UserBean( String userId, String userName, String role ) {
		
		this.setUserId( userId );
		this.setUserName( userName );
		this.setPassword( "1234" );
		this.setRole( role );
		
	}
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId( String userId ) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName( String userName ) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword( String password ) {
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole( String role ) {
		this.role = role;
	}

}