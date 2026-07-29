package shiftManagementSample;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShiftLogic {
	
	
	public static final int openingTime = 9;
	public static final int closingTime = 22;
	

	public static List<LocalTime> getInputingHours() {
		
		List<LocalTime> openingHours = new ArrayList<LocalTime>();
		
		for ( int i = openingTime; i <= closingTime; i ++ ) {
			LocalTime hour = LocalTime.of( i, 0 );
			openingHours.add( hour );
		}
		return openingHours;
	}
	
	
	public static List<LocalTime> getDisplayingHours() {
		
		List<LocalTime> openingHours = new ArrayList<LocalTime>();
		
		for ( int i = openingTime; i < closingTime; i ++ ) {
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
	
	
	public static LocalDate getTargetDay( String targetPeriodValue ) {
		
		LocalDate date = LocalDate.parse( targetPeriodValue );
		return date;
		
	}
	
	
	public static List<ShiftBean> getShiftOfPeriod( String table, LocalDate targetDay ) {
		
		String targetYear = String.valueOf( targetDay.getYear() );
		String targetMonth = String.valueOf( targetDay.getMonthValue() );
	
		int year = Integer.parseInt( targetYear );
		int month = Integer.parseInt( targetMonth );
		
		LocalDate start = LocalDate.of( year, month, 1 );
		LocalDate end = start.plusMonths( 1 );
		
		List<ShiftBean> list = ShiftDAO.findByPeriod( table, start, end );
		
		return list;
		
	}
	
	
	public static List<ShiftBean> getShiftOfUser( String table, String userId, LocalDate targetDay ) {
		
		String targetYear = String.valueOf( targetDay.getYear() );
		String targetMonth = String.valueOf( targetDay.getMonthValue() );
	
		int year = Integer.parseInt( targetYear );
		int month = Integer.parseInt( targetMonth );
		
		LocalDate start = LocalDate.of( year, month, 1 );
		LocalDate end = start.plusMonths( 1 );
		
		List<ShiftBean> list = ShiftDAO.findByUserAndPeriod( table, userId, start, end );
		
		return list;
		
	}
	
	
	public static List<LocalDate> findConfirmedPeriod( LocalDate targetDay ) {
		
		List<LocalDate> confirmedPeriod = new ArrayList<LocalDate>();
		
		int i = 0;
		int count = 0;	// 当月分がまだ確定していない時、スキップする
		while ( true ) {
			
			LocalDate searchingDay = targetDay.minusMonths( i );
		
			int year = searchingDay.getYear();
			int month = searchingDay.getMonthValue();
			
			LocalDate start = LocalDate.of( year, month, 1 );
			LocalDate end = start.plusMonths( 1 );
			
			boolean found = ShiftDAO.findConfirmedPeriod( start, end );
			if ( found ) {
				confirmedPeriod.add( searchingDay );
				i ++;
			} else {
				if ( count == 0 ) {
					count ++;
					i ++;
					continue;
				} else {
					break;
				}
			}
			
		}
		return confirmedPeriod;
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