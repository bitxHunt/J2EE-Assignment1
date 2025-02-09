/***********************************************************
* Name: Thiha Swan Htet, Harry
* Class: DIT/FT/2B/01
* Admin No: P2336671
************************************************************/

package models.status;

public class Status {
	private int id = 0;
	private String name = "";
	
	// Implicit Constructor
	public Status() {
		this.id = 0;
		this.name = "";
	}
	
	// Explicit Constructor
	public Status(int id, String name) {

		// Booting Up
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
