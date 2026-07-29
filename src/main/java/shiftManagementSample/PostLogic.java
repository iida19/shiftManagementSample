package shiftManagementSample;

public class PostLogic {
	
	
	public static void postToForum( String userId, String body, String imp ) {
		
		boolean important = false;
		
		if ( imp != null ) {
			important = true;
		}
		
		PostBean p = new PostBean( userId, body, important );
		PostDAO.insert( p );
		System.out.println( "書き込み成功しました" );
		
	}
	
	
	public static void deletePosts( String[] deleteIdValue ) {
		
		for ( String s : deleteIdValue ) {
			
			int deleteId = Integer.parseInt( s );
			PostDAO.delete( deleteId );
			
		}
		
	}
	

}
