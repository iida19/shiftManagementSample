package shiftManagementSample;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShiftLogic {
	

	public static List<LocalTime> getOpeningHours() {
		
		List<LocalTime> openingHours = new ArrayList<LocalTime>();
		
		int openTime = 9;
		int closeTime = 22;
		
		for ( int i = openTime; i <= closeTime; i ++ ) {
			LocalTime hour = LocalTime.of( i, 0 );
			openingHours.add( hour );
		}
		return openingHours;
	}
	
	
	public static List<ShiftBean> getTodaysShift( LocalDate today ) {
		
		String table = "confirmedShift";
		LocalDate end = today.plusDays(1);
		
		List<ShiftBean> list = ShiftDAO.findByPeriod( table, today, end );
		
		return list;
		
	}
	
	
	public static List<ShiftBean> getShiftOfPeriod( String table, LocalDate targetDay ) {
		
		String[] monthPeriod = new String[2];
		
		monthPeriod[0] = String.valueOf( targetDay.getYear() );
		monthPeriod[1] = String.valueOf( targetDay.getMonthValue() );
	
		int year = Integer.parseInt( monthPeriod[0] );
		int month = Integer.parseInt( monthPeriod[1] );
		
		LocalDate start = LocalDate.of( year, month, 1 );
		LocalDate end = start.plusMonths( 1 );
		
		List<ShiftBean> list = ShiftDAO.findByPeriod( table, start, end );
		
		return list;
		
	}
	
	
	public static List<ShiftBean> getShiftOfUser( String table, String userId, LocalDate targetDay ) {
		
		String[] monthPeriod = new String[2];
		
		monthPeriod[0] = String.valueOf( targetDay.getYear() );
		monthPeriod[1] = String.valueOf( targetDay.getMonthValue() );
	
		int year = Integer.parseInt( monthPeriod[0] );
		int month = Integer.parseInt( monthPeriod[1] );
		
		LocalDate start = LocalDate.of( year, month, 1 );
		LocalDate end = start.plusMonths( 1 );
		
		List<ShiftBean> list = ShiftDAO.findByUserAndPeriod( table, userId, start, end );
		
		return list;
		
	}
	
	
	public static Map<LocalDate, ShiftBean> makeUserShiftMap( List<ShiftBean> listOfPeriod, List<LocalDate> periodList ) {
		
		Map<LocalDate, ShiftBean> shiftMap = new LinkedHashMap<>();
		
		for ( LocalDate date : periodList ) {
			
			for ( ShiftBean shift : listOfPeriod ) {
				
				if ( date.equals( shift.getShiftDate() ) ) {
					shiftMap.put( date, shift );
				}
				
			}
		}
		return shiftMap;
	}
	
	
	public static Map<LocalDate, List<ShiftBean>> makeShiftMap( List<ShiftBean> listOfPeriod, List<LocalDate> periodList ) {
		
		Map<LocalDate, List<ShiftBean>> shiftMap = new LinkedHashMap<>();
		
		for ( LocalDate date : periodList ) {
			
			List<ShiftBean> shiftListOfTheDay = new ArrayList<ShiftBean>();
			
			for ( ShiftBean shift : listOfPeriod ) {
				
				if ( date.equals( shift.getShiftDate() ) ) {
					shiftListOfTheDay.add( shift );
				}
				
			}
			shiftMap.put( date, shiftListOfTheDay );
		}
		return shiftMap;
	}
	
	
	public static List<LocalDate> createDateList( LocalDate targetDay ) {
		
		List<LocalDate> list = new ArrayList<LocalDate>();
		
		int yearOfPeriod = targetDay.getYear();
		int monthOfPeriod = targetDay.getMonthValue();
		
		for ( int i = 1; i <= targetDay.lengthOfMonth(); i ++ ) {
			
			LocalDate date = LocalDate.of( yearOfPeriod, monthOfPeriod, i );
			list.add( date );
			
		}
			
		return list;
		
	}
	
	
	public static void registerShiftList( List<ShiftBean> shiftList, String dbPath ) {
		
		for ( ShiftBean s : shiftList ) {
			ShiftDAO.save( s, dbPath );
		}
		
	}


}