package shiftManagementSample;

public class PostLogic {
	
	
	public static void postToForum( String userName, String body, String imp ) {
		
		boolean important = false;
		
		if ( imp != null ) {
			important = true;
		}
		
		PostBean p = new PostBean( userName, body, important );
		
		PostDAO.insert( p );
		
	}
	

}
