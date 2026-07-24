package shiftManagementSample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
	
	
	public static List<UserBean> findAll() {
		
		List<UserBean> list = new ArrayList<UserBean>();
		String sql = "SELECT * FROM users";
		
		try (
			Connection con = DBManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement( sql );
            ResultSet rs = pstmt.executeQuery()
		) {
			
			while ( rs.next() ) {
				
				UserBean u = new UserBean(	rs.getString( "userId" ),
															rs.getString( "userName" ),
															rs.getString( "password" ),
															rs.getString( "role" ) );
				list.add( u );
			}
																	// close()いらない（tryの引数にすると閉じてくれる）
			
		} catch ( Exception e ) {
			e.printStackTrace( System.out );			// Eclipseでは統一してくれるので分からないが
																	// 標準出力に流す
			
			throw new RuntimeException( e );		// CLIアプリと違って"行き場がある"ため
																	// Servlet（呼び出し元）に例外を伝えるために
																	// エラーを投げ直さないといけない
		}
		return list;
	}
	
	
	// ログインチェック
	public static UserBean select( String userId, String password ) {
		
		UserBean u = null;
		String sql =	"SELECT * FROM users " + 
							"WHERE userId = ? AND password = ?";
		
		try (
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement( sql );
		) {
			
			pstmt.setString( 1, userId );
			pstmt.setString( 2, password );
			
			try ( ResultSet rs = pstmt.executeQuery() ) {
				
				if ( rs.next() ) {
					
					u = new UserBean(		rs.getString( "userId" ),
													rs.getString( "userName" ),
													rs.getString( "password" ),
													rs.getString( "role" ) );
				}
			}
			
		} catch ( Exception e ) {
			e.printStackTrace( System.out );
			throw new RuntimeException( e );
		}
		return u;
		
	}
	
	
	// 新規登録時（重複ID確認）
	public static UserBean select( String userId ) {
		
		UserBean u = null;
		String sql = "SELECT * FROM users WHERE userId = ?";
		
		try (
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement( sql );
		) {
			
			pstmt.setString( 1, userId );
			
			try ( ResultSet rs = pstmt.executeQuery() ) {
				
				if ( rs.next() ) {
					
					u = new UserBean(		rs.getString( "userId" ),
													rs.getString( "userName" ),
													rs.getString( "password" ),
													rs.getString( "role" ) );
				}
			}
			
		} catch ( Exception e ) {
			e.printStackTrace( System.out );
			throw new RuntimeException( e );
		}
		return u;
		
	}
	
	
	public static void insert( UserBean u ) {
		
		String userId = u.getUserId();
		String userName = u.getUserName();
		String password = u.getPassword();
		String role = u.getRole();
		
		String sql =	"INSERT INTO users( userId, userName, password, role ) " +	
																							// 最後に空白を！連結すると隙間がなくなる
							"VALUES( ?, ?, ?, ? )";
		
		try (
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement( sql );
		) {
			pstmt.setString( 1, userId );
			pstmt.setString( 2, userName );
			pstmt.setString( 3, password );
			pstmt.setString( 4, role );
			pstmt.executeUpdate();
			
		} catch ( Exception e ) {
			e.printStackTrace( System.out );
			throw new RuntimeException( e );
		}
		
	}
	
	
	public static void delete( String userId ) {
		
		String sql =	"DELETE FROM users WHERE userId = ?";
		
		try (
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement( sql );
		) {
			pstmt.setString( 1, userId );
			pstmt.executeUpdate();
			
		} catch ( Exception e ) {
			e.printStackTrace( System.out );
			throw new RuntimeException( e );
		}
		
	}

}