package shiftManagementSample;

public class UserLogic {
	
	public UserLogic() {}
	
	
	public static int loginCheck( String userId, String password ) {
		
		int status = -1;			// 0でログイン成功、1は入力内容違い、2は空欄あり
		
		if ( userId == null || userId.isEmpty() || password == null || password.isEmpty() ) {
			status = 2;
			
		} else {
		
			UserBean u = UserDAO.select( userId, password );
		
			if ( u != null ) {
				status = 0;			
			} else {
				status = 1;
			}
			
		}
		
		return status;
		
	}
	
	
	public static int registerUser( UserBean u ) {
		
		String userId = u.getUserId();
		String userName = u.getUserName();
		String role = u.getRole();
		
		int status = -1;			// 0で登録成功、1はユーザー名重複、2は空欄あり
		
		if (	userId == null || userId.isEmpty()
				|| userName == null || userName.isEmpty()
				|| role == null || role.isEmpty() ) {
			
			status = 2;
			
		} else {
			
			UserBean ub = UserDAO.select( userId );
		
			if ( ub == null ) {
				UserDAO.insert( u );
				status = 0;
				
			} else {
				status = 1;
			}
		}
		return status;		
	}

}
