package shiftManagementSample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class SMSListener implements ServletContextListener {
	
	public void contextInitialized( ServletContextEvent sce ) {
		
		System.out.println( "Listenerが起動しました" );

	    try {
	    	
	        Class.forName( "org.h2.Driver" );
	        
	        String path = sce.getServletContext().getRealPath( "/WEB-INF/db/shiftManagementSample" );
	        String url = "jdbc:h2:file:" + path;
	        DBManager.setUrl( url );

	        try (
	            Connection con = DBManager.getConnection();
	            Statement st = con.createStatement()
	        ) {
	            st.execute("""
	                CREATE TABLE IF NOT EXISTS users(
	                    userId VARCHAR(10),
	                    userName VARCHAR(50),
	                    password VARCHAR(50),
	                    role VARCHAR(2)
	                )
	            """);

	            st.execute("""
	                CREATE TABLE IF NOT EXISTS forum(
	                    id INT AUTO_INCREMENT PRIMARY KEY,
	                    userName VARCHAR(50),
	                    body VARCHAR(280),
	                    postdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
	                )
	            """);
	            
	            st.execute("""
		            CREATE TABLE IF NOT EXISTS requestShift(
		            	shiftId INT AUTO_INCREMENT PRIMARY KEY,
		            	userID VARCHAR(10),
		            	shiftDate DATE NOT NULL,
		            	startTime TIME,
		            	endTime TIME,
		            	dayOff BOOLEAN NOT NULL,
		            	
		            	UNIQUE ( userId, shiftDate )
		            )		
		        """);
	            
	            st.execute("""
	            	CREATE TABLE IF NOT EXISTS confirmedShift(
	            		shiftId INT AUTO_INCREMENT PRIMARY KEY,
	            		userID VARCHAR(10),
	            		shiftDate DATE NOT NULL,
	            		startTime TIME,
	            		endTime TIME,
	            		dayOff BOOLEAN NOT NULL,
	            		
	            		UNIQUE ( userId, shiftDate )
	            	)		
	            """);

	            initUsers( con );
	            initPosts( con );
	            initConfirmedShift( con );
	        }

	    } catch ( Exception e ) {
	        e.printStackTrace();
	        throw new RuntimeException( e );
	    }
	}


	private void initUsers( Connection con ) throws Exception {

	    String countSql = "SELECT COUNT(*) FROM users";

	    try (
	        PreparedStatement pstmt = con.prepareStatement( countSql );
	        ResultSet rs = pstmt.executeQuery()
	    ) {
	        rs.next();
	        int count = rs.getInt(1);

	        if ( count == 0 ) {
	        	
	        	String setSampleSql =	"INSERT INTO users( userId, userName, password, role ) " +
	        										"VALUES( ?, ?, ?, ? )";
	            
	        	try (
	        		PreparedStatement pstmt2 = con.prepareStatement( setSampleSql );
	        	) {
	        	
	        		List<UserBean> sampleList = List.of(
	        			new UserBean( "00001", "店長 タクマ", "1234", "0" ),
	        			new UserBean( "00002", "店員 ミナト", "1234", "1" ),
	        			new UserBean( "00003", "店員 メグミ", "1234", "1" ),
	        			new UserBean( "00004", "店員 アユミ", "1234", "1" ),
	        			new UserBean( "00005", "店員 イズミ", "1234", "1" )
	        			);
	        	
	        		for ( UserBean u : sampleList ) {
	        			pstmt2.setString( 1, u.getUserId() );
	        			pstmt2.setString( 2, u.getUserName() );
	        			pstmt2.setString( 3, u.getPassword() );
	        			pstmt2.setString( 4, u.getRole() );
	        			pstmt2.executeUpdate();
	        		}
	        	
	        	}
	        }
	        
	    } catch ( Exception e ) {
	        e.printStackTrace();
	        throw new RuntimeException( e );
	    }
	}
	
	
	private void initPosts( Connection con ) throws Exception {

	    String countSql = "SELECT COUNT(*) FROM forum";

	    try (
	        PreparedStatement pstmt = con.prepareStatement( countSql );
	        ResultSet rs = pstmt.executeQuery()
	    ) {
	        rs.next();
	        int count = rs.getInt(1);

	        if ( count == 0 ) {
	        	
	        	String setSampleSql =	"INSERT INTO forum( userName, body ) " +
													"VALUES( ?, ? )";

	        	try (
	        		PreparedStatement pstmt2 = con.prepareStatement( setSampleSql );
	        	) {
	            
	        		List<PostBean> sampleList = List.of(
	        				new PostBean( "タクマ", "シフト希望は月末までに出してください" ),
	        				new PostBean( "ミナト", "すみません、誰か今日のシフト替わってください！" ),
	        				new PostBean( "メグミ", "私出れますよ！" )
	        				);
	        	
	        		for ( PostBean p : sampleList ) {
	        			pstmt2.setString( 1, p.getUserName() );
	        			pstmt2.setString( 2, p.getBody() );
	        			pstmt2.executeUpdate();
	        		}
	        		
	        		System.out.println("掲示板の初期データを登録しました");
	        		
	        	}
	        }
	    } catch ( Exception e ) {
	        e.printStackTrace();
	        throw new RuntimeException( e );
	    } 
	}
	
	
	private void initConfirmedShift( Connection con ) {
		
		String countSql = "SELECT COUNT(*) FROM confirmedShift";

	    try (
	        PreparedStatement pstmt = con.prepareStatement( countSql );
	        ResultSet rs = pstmt.executeQuery()
	    ) {
	        rs.next();
	        int count = rs.getInt(1);

	        if ( count == 0 ) {
	        	
	        	String setSampleSql =	"INSERT INTO confirmedShift" + 
	        										"( userId, shiftDate, startTime, endTime, dayOff ) " +
	        										"VALUES( ?, ?, ?, ?, ? )";
	            
	        	try (
	        		PreparedStatement pstmt2 = con.prepareStatement( setSampleSql );
	        	) {
	        	
	        		List<ShiftBean> sampleList = List.of(
	        			new ShiftBean(	"00002",
	        									LocalDate.of( 2026, 7, 26 ), LocalTime.of( 9, 0 ), LocalTime.of( 14, 0 ) ),
	        			new ShiftBean(	"00003", LocalDate.of( 2026, 7, 26 ), true ),
	        			new ShiftBean(	"00004",
												LocalDate.of( 2026, 7, 26 ), LocalTime.of( 17, 0 ), LocalTime.of( 22, 0 ) ),
	        			new ShiftBean(	"00005",
								LocalDate.of( 2026, 7, 26 ), LocalTime.of( 9, 0 ), LocalTime.of( 22, 0 ) )
	        			);
	        	
	        		for ( ShiftBean s : sampleList ) {
	        			pstmt2.setString( 1, s.getUserId() );
	        			pstmt2.setObject( 2, s.getShiftDate() );
	        			pstmt2.setObject( 3, s.getStartTime() );
	        			pstmt2.setObject( 4, s.getEndTime() );
	        			pstmt2.setBoolean( 5, s.isDayOff() );
	        			pstmt2.executeUpdate();
	        		}
	        	
	        	}
	        }
	        
	    } catch ( Exception e ) {
	        e.printStackTrace();
	        throw new RuntimeException( e );
	    }
	}
}