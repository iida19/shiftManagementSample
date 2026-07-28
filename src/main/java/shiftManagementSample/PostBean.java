package shiftManagementSample;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostBean implements Serializable {
	
	
	private int id;
	private String userId;
	private String userName;
	private String body;
	private boolean important;
	private LocalDateTime postdate;
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern( "yyyy/MM/dd HH:mm" );
	
	public PostBean() {}
	
	// 投稿時
	public PostBean( String userId, String body, boolean important ) {
		
		this.setUserId( userId );
		this.setBody( body );
		this.setImportant( important );
		
	}
	
	// データベースとのやり取り時
	public PostBean( int id, String userId, String userName, String body, boolean important, Timestamp postdate ) {
		
		this.setId( id );
		this.setUserId( userId );
		this.setUserName( userName );
		this.setBody( body );
		this.setImportant( important );
		this.setPostdate( postdate );
		
	}
	
	
	public int getId() {
		return id;
	}
	public void setId( int id ) {
		this.id = id;
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

	public String getBody() {
		return body;
	}
	public void setBody( String body ) {
		this.body = body;
	}
	
	public boolean isImportant() {
		return important;
	}
	public void setImportant( boolean important ) {
		this.important = important;
	}

	public String getFormattedPostdate() {
		return postdate.format( FORMATTER );
	}
	public void setPostdate( Timestamp postdate ) {
		LocalDateTime postTiming = postdate.toLocalDateTime();
		this.postdate = postTiming;
	}
	
	
	

}