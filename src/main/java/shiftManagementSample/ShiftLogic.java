package shiftManagementSample;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
	
	
	public static List<LocalDate> createDateList( LocalDate today ) {
		
		List<LocalDate> list = new ArrayList<LocalDate>();
		
		LocalDate targetMonth = today.plusMonths( 1 );
		int yearOfPeriod = targetMonth.getYear();
		int monthOfPeriod = targetMonth.getMonthValue();
		
		for ( int i = 1; i <= targetMonth.lengthOfMonth(); i ++ ) {
			
			LocalDate date = LocalDate.of( yearOfPeriod, monthOfPeriod, i );
			list.add( date );
			
		}
			
		return list;
		
	}


}