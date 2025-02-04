package models.feature;

public class Feature {
	private int id = 0;
	private String name = "";
	
	// Implicit Constructor
	public Feature() {
		this.id = 0;
		this.name = "";
	}
	
	// Explicit Constructor
	public Feature(int id, String name) {

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
