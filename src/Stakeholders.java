
public interface Stakeholders {
	
	/**
	 * Method to get name of the stake holder
	 * @return returns physical address of stake holder
	 */
	String getName();
	
	/**
	 * Method to get contact number of the stake holder
	 * @return returns physical address of stake holder
	 */
	String getTelNum();
	
	/**
	 * Method to get physical address of the stake holder
	 * @return returns physical address of stake holder
	 */
	String getPhysAdrr();
	
	/**
	 * Method to get email of the stake holder
	 * @return returns email of stake holder
	 */
	String getEmail();
	
	/**
	 * Method to get role of the stake holder
	 * @return returns role of stake holder
	 */
	String getRole();
	
	/**
	 * Method to get project number the stake holder is related to
	 * @return returns project number
	 */
	int getProjNum();
	
	/**
	 * Method to set contact number of the stake holder
	 * @param strTelNum 
	 */
	void setTelNum(String strTelNum);
	
	/**
	 * Method to set physical address of the stake holder
	 * @param strAdrr
	 */
	void setPhysAdrr(String strAdrr);
	
	/**
	 * Method to set email of the stake holder
	 * @param strEmail
	 */
	void setEmail(String strEmail);
	
	/**
	 * Method to set name of the stake holder
	 * @param strName
	 */
	void setName(String strName);
	
	/**
	 * Method to set the project ID
	 * @param id
	 */
	void setID(int id);

}
