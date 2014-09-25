package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

// This class handles the communication with the database

public class DataManager {
	
	private Note[] notesList;    //list of notes stored in database
	private int size;            // length of NotesList
	private Connection connection;  // database connection
	private Statement statment;     // database statment
	
	
	// Constructor 
	public DataManager(){
		init();
	}
	
	public void openDBConnection()
    {
       try {
           Class.forName("org.sqlite.JDBC");
           connection = DriverManager.getConnection("jdbc:sqlite:test.db");
           System.out.println("Opened database successfully");
       } catch (Exception ex) {
           System.out.println(ex.getLocalizedMessage());
       }
      
    }
    
    public void closeDBConnection()
    {
       try {
           connection.close();
       } catch (SQLException ex) {
           System.out.println(ex.getLocalizedMessage());
       }
    }
	
	//create tables if not already exist
	private void init(){
		openDBConnection();
		try
        {
            statment = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS NOTE " +
                       "( NOTE_CONTENT        TEXT, " + 
                       " NOTE_TIME         DATETIME)";                        
            statment.executeUpdate(sql);
            statment.close();
        } catch ( Exception e ) {
          System.err.println( e.getLocalizedMessage() );
        }
        System.out.println("NOTE Table created successfully");
	}
	
	// load data stored in database
	public void loadData(){
		ArrayList all = new ArrayList();
	     try {

	      statment = connection.createStatement();
	      ResultSet rs = statment.executeQuery( "SELECT * FROM NOTE;" );
	      Note myNote = new Note();
	      while ( rs.next() ) {
	    	  myNote.setContent(rs.getString("NOTE_CONTENT"));
	    	  myNote.setTime(rs.getDate("NOTE_TIME"));
	         
	         all.add(myNote);
	         myNote = new Note();
	      }
	      rs.close();
	      statment.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    }
	     
	    size = all.size(); 
	    notesList = new Note[size];
	    notesList = (Note[]) all.toArray(notesList);
	}
	
	
	//delete item from database
	public void deleteItem(String date){
		try{
		      statment = connection.createStatement();
		      String sql = "DELETE FROM NOTE WHERE NOTE_TIME = '"+ date + "';";		      
		      statment.executeUpdate(sql);
		      statment.close();
		    }catch(Exception e)
		    {
		        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    }
		    System.out.println("Note deleted successfully");
		    loadData();
	}
	
	//add item to database
	public void addItem(Note newNote){
		try{
		      statment = connection.createStatement();
		      String sql = "INSERT INTO NOTE (NOTE_CONTENT, NOTE_TIME) " +
		                   "VALUES ( '"+newNote.getContent()+"', '"+newNote.getTime()+"' );"; 
		      
		      statment.executeUpdate(sql);
		      statment.close();
		    }catch(Exception e)
		    {
		        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    }
		    System.out.println("Note added successfully");
		    loadData();
	}
	
	// check if there is any note with following content
	public Note findItem(String content){
		for(int i=0 ; i<size ; i++)
		{
			if(notesList[i].getContent().contains(content))
				return notesList[i];
		}
		return null;
	}
	
	// check if there is any note with following date
		public Note findItem(Date date){
			for(int i=0 ; i<size ; i++)
			{
				if(notesList[i].getTime().equals(Note.defaultDF.format(date)))
					return notesList[i];
			}
			return null;
		}
	

	public Note[] getNotesList() {
		return notesList;
	}

	public void setNotesList(Note[] notesList) {
		this.notesList = notesList;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
