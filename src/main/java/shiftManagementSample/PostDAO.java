package shiftManagementSample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class PostDAO {
	
	
	public static List<PostBean> findAll() {
		
		List<PostBean> list = new ArrayList<PostBean>();
		String sql =	"SELECT * FROM forum" +
							" INNER JOIN users ON forum.userId = users.userId" +
							" ORDER BY postdate DESC";
		
		try (
				Connection con = DBManager.getConnection();
	            PreparedStatement pstmt = con.prepareStatement( sql );
	            ResultSet rs = pstmt.executeQuery()
		) {
				
			while ( rs.next() ) {
					
				PostBean p = new PostBean(	rs.getInt( "id" ),
															rs.getString( "userId" ),
															rs.getString( "userName" ),
															rs.getString( "body" ),
															rs.getBoolean( "important" ),
															rs.getTimestamp( "postdate" ) );
				list.add( p );
			}
			
		} catch ( Exception e ) {
			e.printStackTrace( System.out );
			throw new RuntimeException( e );
		}
		return list;
		
	}
	
	
	public static List<PostBean> findImportantPost() {
		
		List<PostBean> list = new ArrayList<PostBean>();
		String sql =	"SELECT * FROM forum" +
							" INNER JOIN users ON forum.userId = users.userId" +
							" WHERE important = TRUE" +
							" ORDER BY postdate DESC";
		
		try (
				Connection con = DBManager.getConnection();
	            PreparedStatement pstmt = con.prepareStatement( sql );
	            ResultSet rs = pstmt.executeQuery()
		) {
				
			while ( rs.next() ) {
					
				PostBean p = new PostBean(	rs.getInt( "id" ),
															rs.getString( "userId" ),
															rs.getString( "userName" ),
															rs.getString( "body" ),
															rs.getBoolean( "important" ),
															rs.getTimestamp( "postdate" ) );
				list.add( p );
			}
			
		} catch ( Exception e ) {
			e.printStackTrace( System.out );
			throw new RuntimeException( e );
		}
		return list;
		
	}
	
	
	public static void insert( PostBean p ) {
		
		String userId = p.getUserId();
		String body = p.getBody();
		boolean important = p.isImportant();
		
		String sql =	"INSERT INTO forum( userId, body, important ) " +
							"VALUES( ?, ?, ? )";
										// id INT AUTO_INCREMENT PRIMARY KEY,
										// postdate DEFAULT CURRENT_TIMESTAMP
										// → H2が自動で入れてくれるので渡す必要なし
		
		try (
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement( sql );
		) {
			pstmt.setString( 1, userId );
			pstmt.setString( 2, body );
			pstmt.setBoolean( 3, important );
			pstmt.executeUpdate();
		} catch ( Exception e ) {
			e.printStackTrace( System.out );
			throw new RuntimeException( e );
		}
		
	}
	
	
	public static void delete( int id ) {
		
		String sql =	"DELETE FROM forum WHERE id = ?";
		
		try (
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement( sql );
		) {
			pstmt.setInt( 1, id );
			pstmt.executeUpdate();
		} catch ( Exception e ) {
			e.printStackTrace( System.out );
			throw new RuntimeException( e );
		}
		
	}
	

}
