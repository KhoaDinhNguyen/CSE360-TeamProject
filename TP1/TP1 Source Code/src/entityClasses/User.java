package entityClasses;

/*******
 * <p>
 * Title: User Class
 * </p>
 * 
 * <p>
 * Description: This User class represents a user entity in the system. It
 * contains the user's details such as userName, password, and roles being
 * played.
 * </p>
 * 
 * <p>
 * Copyright: Lynn Robert Carter © 2025
 * </p>
 * 
 * @author Lynn Robert Carter
 * 
 * 
 */

public class User {

	/*
	 * These are the private attributes for this entity object
	 */
	private String userName;
	private String password;
	private String firstName;
	private String middleName;
	private String lastName;
	private String preferredFirstName;
	private String emailAddress;
	private boolean adminRole;
	private boolean student;
	private boolean staff;

	/*****
	 * <p>
	 * Method: User()
	 * </p>
	 * 
	 * <p>
	 * Description: This default constructor is not used in this system.
	 * </p>
	 */
	public User() {

	}

	/*****
	 * <p>
	 * Method: User(String userName, String password, boolean r1, boolean r2,
	 * boolean r3, boolean r4, boolean r5)
	 * </p>
	 * 
	 * <p>
	 * Description: This constructor is used to establish user entity objects.
	 * </p>
	 * 
	 * @param userName specifies the account userName for this user
	 * 
	 * @param password specifies the account password for this user
	 * 
	 * @param r1       specifies the the Admin attribute (TRUE or FALSE) for this
	 *                 user
	 * 
	 * @param r2       specifies the the Student attribute (TRUE or FALSE) for this
	 *                 user
	 * 
	 * @param r3       specifies the the Reviewer attribute (TRUE or FALSE) for this
	 *                 user
	 * 
	 */
	// Constructor to initialize a new User object with userName, password, and
	// role.
	public User(String userName, String password, String fn, String mn, String ln, String pfn, String ea, boolean r1,
			boolean r2, boolean r3) {
		this.userName = userName;
		this.password = password;
		this.firstName = fn;
		this.middleName = mn;
		this.lastName = ln;
		this.preferredFirstName = pfn;
		this.emailAddress = ea;
		this.adminRole = r1;
		this.student = r2;
		this.staff = r3;
	}

	/*****
	 * <p>
	 * Method: void setAdminRole(boolean role)
	 * </p>
	 * 
	 * <p>
	 * Description: This setter defines the Admin role attribute.
	 * </p>
	 * 
	 * @param role is a boolean that specifies if this user in playing the Admin
	 *             role.
	 * 
	 */
	// Sets the role of the Admin user.
	public void setAdminRole(boolean role) {
		this.adminRole = role;
	}

	/*****
	 * <p>
	 * Method: void setStudentUser(boolean role)
	 * </p>
	 * 
	 * <p>
	 * Description: This setter defines the student attribute.
	 * </p>
	 * 
	 * @param role is a boolean that specifies if this user in playing student.
	 * 
	 */
	// Sets the student user.
	public void setStudentUser(boolean role) {
		this.student = role;
	}

	/*****
	 * <p>
	 * Method: void setStaffUser(boolean role)
	 * </p>
	 * 
	 * <p>
	 * Description: This setter defines the staff attribute.
	 * </p>
	 * 
	 * @param role is a boolean that specifies if this user in playing staff.
	 * 
	 */
	// Sets the staff user.
	public void setStaffUser(boolean role) {
		this.staff = role;
	}

	/*****
	 * <p>
	 * Method: String getUserName()
	 * </p>
	 * 
	 * <p>
	 * Description: This getter returns the UserName.
	 * </p>
	 * 
	 * @return a String of the UserName
	 * 
	 */
	// Gets the current value of the UserName.
	public String getUserName() {
		return userName;
	}

	/*****
	 * <p>
	 * Method: String getPassword()
	 * </p>
	 * 
	 * <p>
	 * Description: This getter returns the Password.
	 * </p>
	 * 
	 * @return a String of the password
	 *
	 */
	// Gets the current value of the Password.
	public String getPassword() {
		return password;
	}

	/*****
	 * <p>
	 * Method: String getFirstName()
	 * </p>
	 * 
	 * <p>
	 * Description: This getter returns the FirstName.
	 * </p>
	 * 
	 * @return a String of the FirstName
	 *
	 */
	// Gets the current value of the FirstName.
	public String getFirstName() {
		return firstName;
	}

	/*****
	 * <p>
	 * Method: String getMiddleName()
	 * </p>
	 * 
	 * <p>
	 * Description: This getter returns the MiddleName.
	 * </p>
	 * 
	 * @return a String of the MiddleName
	 *
	 */
	// Gets the current value of the Student role attribute.
	public String getMiddleName() {
		return middleName;
	}

	/*****
	 * <p>
	 * Method: String getLasteName()
	 * </p>
	 * 
	 * <p>
	 * Description: This getter returns the LastName.
	 * </p>
	 * 
	 * @return a String of the LastName
	 *
	 */
	// Gets the current value of the Student role attribute.
	public String getLastName() {
		return lastName;
	}

	/*****
	 * <p>
	 * Method: String getPreferredFirstName()
	 * </p>
	 * 
	 * <p>
	 * Description: This getter returns the PreferredFirstName.
	 * </p>
	 * 
	 * @return a String of the PreferredFirstName
	 *
	 */
	// Gets the current value of the Student role attribute.
	public String getPreferredFirstName() {
		return preferredFirstName;
	}

	/*****
	 * <p>
	 * Method: String getEmailAddress()
	 * </p>
	 * 
	 * <p>
	 * Description: This getter returns the EmailAddress.
	 * </p>
	 * 
	 * @return a String of the EmailAddress
	 *
	 */
	// Gets the current value of the Student role attribute.
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setUserName(String s) {
		userName = s;
	}

	public void setPassword(String s) {
		password = s;
	}

	public void setFirstName(String s) {
		firstName = s;
	}

	public void setMiddleName(String s) {
		middleName = s;
	}

	public void setLastName(String s) {
		lastName = s;
	}

	public void setPreferredFirstName(String s) {
		preferredFirstName = s;
	}

	public void setEmailAddress(String s) {
		emailAddress = s;
	}

	/*****
	 * <p>
	 * Method: String getAdminRole()
	 * </p>
	 * 
	 * <p>
	 * Description: This getter returns the value of the Admin role attribute.
	 * </p>
	 * 
	 * @return a String of "TRUE" or "FALSE" based on state of the attribute
	 *
	 */
	// Gets the current value of the Admin role attribute.
	public boolean getAdminRole() {
		return adminRole;
	}

	/*****
	 * <p>
	 * Method: String getStudent()
	 * </p>
	 * 
	 * <p>
	 * Description: This getter returns the value of the student attribute.
	 * </p>
	 * 
	 * @return a String of "TRUE" or "FALSE" based on state of the attribute
	 *
	 */
	// Gets the current value of the student attribute.
	public boolean getNewStudent() {
		return student;
	}

	public boolean getStudent() {
		return student;
	}

	/*****
	 * <p>
	 * Method: String getStaff()
	 * </p>
	 * 
	 * <p>
	 * Description: This getter returns the value of the staff attribute.
	 * </p>
	 * 
	 * @return a String of "TRUE" or "FALSE" based on state of the attribute
	 *
	 */
	// Gets the current value of the staff attribute.
	public boolean getNewStaff() {
		return staff;
	}

	public boolean getStaff() {
		return staff;
	}

	/*****
	 * <p>
	 * Method: int getNumRoles()
	 * </p>
	 * 
	 * <p>
	 * Description: This getter returns the number of roles this user plays (0 - 5).
	 * </p>
	 * 
	 * @return a value 0 - 5 of the number of roles this user plays
	 *
	 */
	// Gets the current value of the Staff role attribute.
	public int getNumRoles() {
		int numRoles = 0;
		if (adminRole)
			numRoles++;
		if (student)
			numRoles++;
		if (staff)
			numRoles++;
		return numRoles;
	}

	public String getRoles() {
		String roles = "";

		if (adminRole)
			roles += "Admin, ";
		if (student)
			roles += "Student, ";
		if (staff)
			roles += "Staff, ";

		roles = roles.substring(0, roles.length() - 2);

		return "<" + roles + ">";
	}
}
