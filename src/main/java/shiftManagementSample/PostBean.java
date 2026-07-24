package shiftManagementSample;

import java.io.Serializable;
import java.sql.Timestamp;

public class PostBean implements Serializable {
	
	
	private int id;
	private String userName;
	private String body;
	private Timestamp postdate;
	
	public PostBean() {}
	
	// 投稿時
	public PostBean( String userName, String body ) {
		
		this.setUserName( userName );
		this.setBody( body );
		
	}
	
	// データベースとのやり取り時
	public PostBean( int id, String userName, String body, Timestamp postdate ) {
		
		this.setId( id );
		this.setUserName( userName );
		this.setBody( body );
		this.setPostdate( postdate );
		
	}
	
	
	public int getId() {
		return id;
	}
	public void setId( int id ) {
		this.id = id;
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
	
	public Timestamp getPostdate() {
		return postdate;
	}
	public void setPostdate( Timestamp postdate ) {
		this.postdate = postdate;
	}
	
	
	

}