package utility;

import java.text.SimpleDateFormat;
import java.util.Date;


// Simple note object has content, date

public class Note {
	private String content;
	private Date time;
	public static SimpleDateFormat defaultDF = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
	
	
	public Note() {
	}


	public Note(String content, Date time) {
		this.content = content;
		this.time = time;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getTime() {
		return defaultDF.format(time);
	}


	public void setTime(Date time) {
		this.time = time;
	}
	
	

}
