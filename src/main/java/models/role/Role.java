/***********************************************************
* Name: Soe Zaw Aung, Scott
* Class: DIT/FT/2B/01
* Admin No: P2340474
* Description: Model class to store category information
************************************************************/
package models.role;

public class Role {
	private int roleId;
	private String roleName;

	//Default Constructors
	public Role() {
		this.roleId = 0;
		this.roleName = "";

	}

	//Explicit Constructors
	public Role(int roleId, String roleName) {
		this.roleId = roleId;
		this.roleName = roleName;
	}

	// Getters and Setters
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}