/**
 * 
 * @author Matsobane Makhura
 * {@inheritDoc}
 *
 */
public class Constructor implements Stakeholders {
	
	//Attributes
	private String stkFunction;
	private String stkName;
	private String stkTelephone;
	private String stkMail;
	private String stkAddress;
	private int projNum;
	
	
	
	
	
	//constructor
	public Constructor(int id) {
		this.setRole();
		this.setID(id);
	}
	
	
	public int getProjNum() {
		return projNum;
	}
	
	public void setID(int id) {
		this.projNum=id;
	}
	
	
	@Override
	public String getRole() {
		return stkFunction;
	}
	
	private void setRole() {
		this.stkFunction="Constructor";
	}
	
	
	@Override
	public String getName() {
		return stkName;
	}
	@Override
	public void setName(String strName) {
		this.stkName=strName;
	}
	
	@Override
	public String getTelNum() {
		return stkTelephone;
	}
	@Override
	public void setTelNum(String strTelNum) {
		this.stkTelephone=strTelNum;
	}
	
	@Override
	public String getPhysAdrr() {
		return stkAddress;
	}
	@Override
	public void setPhysAdrr(String strAdrr) {
		this.stkAddress=strAdrr;
	}
	
	@Override
	public String getEmail() {
		return stkMail;
	}
	@Override
	public void setEmail(String strEmail) {
		this.stkMail=strEmail;
	}



}
