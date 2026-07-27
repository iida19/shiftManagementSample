package shiftManagementSample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ShiftDAO {


    public static List<ShiftBean> findByPeriod( String table, LocalDate start, LocalDate end ) {

    	List<ShiftBean> list = new ArrayList<ShiftBean>();
    	String sql =	"SELECT " + table + ".*, users.userName " +
        					"FROM " + table +
							" INNER JOIN users ON " + table +".userId = users.userId" +
        					" WHERE shiftDate >= ? AND shiftDate < ?" +
        					" ORDER BY shiftDate ASC, dayOff ASC, startTime ASC, " + table +".userId ASC";

    	try (
    			Connection con = DBManager.getConnection();
    			PreparedStatement pstmt = con.prepareStatement( sql )
    	) {
    		
    		pstmt.setObject( 1, start );
    		pstmt.setObject( 2, end );
        	
    		try ( ResultSet rs = pstmt.executeQuery() ) {

        		while ( rs.next() ) {

        			if ( rs.getBoolean( "dayOff" ) ) {

        				ShiftBean s = new ShiftBean();
        				s.setShiftId( rs.getInt( "shiftId" ) );
        				s.setUserId( rs.getString( "userId" ) );
        				s.setUserName( rs.getString( "userName" ) );
        				s.setShiftDate( rs.getObject( "shiftDate", LocalDate.class ) );
        				s.setDayOff( rs.getBoolean( "dayOff" ) );
        				list.add( s );

        			} else {

        				ShiftBean s = new ShiftBean();
        				s.setShiftId( rs.getInt( "shiftId" ) );
        				s.setUserId( rs.getString( "userId" ) );
        				s.setUserName( rs.getString( "userName" ) );
        				s.setShiftDate( rs.getObject( "shiftDate", LocalDate.class ) );
        				s.setStartTime( rs.getObject( "startTime", LocalTime.class ) );
        				s.setEndTime( rs.getObject( "endTime", LocalTime.class ) );
        				s.setDayOff( rs.getBoolean( "dayOff" ) );
        				list.add( s );

        			}
        		}
        	}

    	} catch ( Exception e ) {
    		e.printStackTrace( System.out );
    		throw new RuntimeException( e );
    	}
    	return list;
    }
    
    
    public static List<ShiftBean> findByUserAndPeriod( String table, String userId, LocalDate start, LocalDate end ) {

    	List<ShiftBean> list = new ArrayList<ShiftBean>();
    	String sql =	"SELECT * FROM " + table +
        					" WHERE userId = ? AND shiftDate >= ? AND shiftDate < ?" +
        					" ORDER BY shiftDate ASC";

    	try (
    			Connection con = DBManager.getConnection();
    			PreparedStatement pstmt = con.prepareStatement( sql )
    	) {
    		
    		pstmt.setString( 1, userId );
    		pstmt.setObject( 2, start );
    		pstmt.setObject( 3, end );
        	
    		try ( ResultSet rs = pstmt.executeQuery() ) {

        		while ( rs.next() ) {

        			if ( rs.getBoolean( "dayOff" ) ) {

        				ShiftBean s = new ShiftBean();
        				s.setShiftId( rs.getInt( "shiftId" ) );
        				s.setUserId( rs.getString( "userId" ) );
        				s.setShiftDate( rs.getObject( "shiftDate", LocalDate.class ) );
        				s.setDayOff( rs.getBoolean( "dayOff" ) );
        				list.add( s );

        			} else {

        				ShiftBean s = new ShiftBean();
        				s.setShiftId( rs.getInt( "shiftId" ) );
        				s.setUserId( rs.getString( "userId" ) );
        				s.setShiftDate( rs.getObject( "shiftDate", LocalDate.class ) );
        				s.setStartTime( rs.getObject( "startTime", LocalTime.class ) );
        				s.setEndTime( rs.getObject( "endTime", LocalTime.class ) );
        				s.setDayOff( rs.getBoolean( "dayOff" ) );
        				list.add( s );

        			}
        		}
        	}

    	} catch ( Exception e ) {
    		e.printStackTrace( System.out );
    		throw new RuntimeException( e );
    	}
    	return list;
    }
    
    
    public static boolean findConfirmedPeriod( LocalDate start, LocalDate end ) {
    	
    	boolean found = false;
    	
    	String sql = "SELECT * FROM" + " confirmedShift" +
    			" WHERE shiftDate >= ? AND shiftDate < ?";
    	
    	try (
    			Connection con = DBManager.getConnection();
    			PreparedStatement pstmt = con.prepareStatement( sql )
    	) {
    		
    		pstmt.setObject( 1, start );
    		pstmt.setObject( 2, end );
    		
    		try ( ResultSet rs = pstmt.executeQuery() ) {
    			
    			if ( rs.next() ) {
    				found = true;
    			}   			
    		}
    		
    	} catch ( Exception e ) {
    		e.printStackTrace( System.out );
    		throw new RuntimeException( e );
    	}
    	return found;
    }


    public static void save( ShiftBean s, String table ) {

		
		String sql = "MERGE INTO " + table +
				"( userId, shiftDate, startTime, endTime, dayOff ) " +
				"KEY( userId, shiftDate ) " +
				"VALUES ( ?, ?, ?, ?, ? )";
		// id INT AUTO_INCREMENT PRIMARY KEY,
		// → H2が自動で入れてくれるので渡す必要なし

		try (
				Connection con = DBManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
		) {
			pstmt.setString( 1, s.getUserId() );
			pstmt.setObject( 2, s.getShiftDate() );
			
			if ( s.isDayOff() ) {
				pstmt.setNull( 3, java.sql.Types.TIME );
				pstmt.setNull( 4, java.sql.Types.TIME );
			} else {
				pstmt.setObject( 3, s.getStartTime() );
				pstmt.setObject( 4, s.getEndTime() );
			}

			pstmt.setBoolean(5, s.isDayOff());
			pstmt.executeUpdate();

		} catch ( Exception e ) {
			e.printStackTrace( System.out );
			throw new RuntimeException( e );
		}
	}
}