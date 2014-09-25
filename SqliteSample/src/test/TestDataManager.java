package test;

import java.text.ParseException;
import java.util.Date;

import utility.DataManager;
import utility.Note;
import junit.framework.TestCase;

public class TestDataManager extends TestCase {

	private DataManager dataManager;
	
	public TestDataManager(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		dataManager = new DataManager();
		dataManager.loadData();
	}

	public void testAdd(){
		String cont = "first sample";
		Note note = new Note( cont , new Date());
		dataManager.addItem(note);
		assertEquals(cont , dataManager.findItem(cont).getContent());
	}
	
	public void testDelete(){
		String dateStr = "2014-09-24 11:30:20";
		try {
			Date date = Note.defaultDF.parse(dateStr);
			Note note = new Note("second sample" , date );
			dataManager.addItem(note);
			dataManager.deleteItem(dateStr);
			assertNull(dataManager.findItem(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	protected void tearDown() throws Exception {
		dataManager.closeDBConnection();
	}

}
