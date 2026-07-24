package shiftManagementSample;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class ShiftBean implements Serializable {

    private int shiftId;
    private String userId;
    private String userName;
    private LocalDate shiftDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean dayOff;

    public ShiftBean() {}

    // データベース初期登録用
    public ShiftBean( String userId, LocalDate shiftDate, LocalTime startTime, LocalTime endTime ) {

    	this.setUserId( userId );
    	this.setShiftDate( shiftDate );
    	this.setStartTime( startTime );
    	this.setEndTime( endTime );
    	this.setDayOff( false );
    	
    }
    public ShiftBean( String userId, LocalDate shiftDate, boolean dayOff ) {

    	this.setUserId( userId );
    	this.setShiftDate( shiftDate );
    	this.setDayOff( dayOff );
    	
    }


    public int getShiftId() {
        return shiftId;
    }
    public void setShiftId( int shiftId ) {
        this.shiftId = shiftId;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId( String userId ) {
        this.userId = userId;
    }

    public String getUserName() {
		return userName;
	}
	public void setUserName( String userName ) {
		this.userName = userName;
	}
	
	public LocalDate getShiftDate() {
        return shiftDate;
    }
    public void setShiftDate( int shiftYear, int shiftMonth, int shiftDay ) {
        this.shiftDate = LocalDate.of( shiftYear, shiftMonth, shiftDay );
    }
    public void setShiftDate( LocalDate shiftDate ) {
        this.shiftDate = shiftDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime( String sTime, int sMinute ) {
        int sHour = Integer.parseInt( sTime );
        this.startTime = LocalTime.of( sHour, sMinute );
    }
    public void setStartTime( LocalTime startTime ) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime( String eTime, int eMinute ) {
        int eHour = Integer.parseInt( eTime );
        this.endTime = LocalTime.of( eHour, eMinute );
    }
    public void setEndTime( LocalTime endTime ) {
        this.endTime = endTime;
    }

    public boolean isDayOff() {
        return dayOff;
    }
    public void setDayOff( boolean dayOff ) {
        this.dayOff = dayOff;
    }
}