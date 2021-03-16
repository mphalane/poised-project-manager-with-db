/**
 * 
 * @author Matsobane Makhura
 *
 */
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *This is the Project class that is used to describe the project object used in project list
 */
public class Project implements ProjectId{
	
	//Attributes
	private int projErf;
	private double totalFee;
	private double totalPaid;
	private String projDeadline;
	private String projName;
	private String projAdress;
	private String buildType;
	private boolean projFinal;
	private String projComplete;
	
	
	
	private int projId=0;
	
	
	
	/** Build two constructors : a default one that sets the project ID to 1 
	 another one that allows the user to set the project ID*/
	public Project () {
		this.setProjId(1);
	}
	
	/** Build two constructors : a default one that sets the project ID to 1 
	 another one that allows the user to set the project ID*/
	public Project (int projnum) {
		this.setProjId(projnum);
	}
	
	

	
	
	/**Method to print invoice
	 * @return invoice string
	 * */
	public String finalizeProj() {
		  //Declare and init variables
		  double amtRemaining=getTotalFee()-getTotalPaid();
		  SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		  Date date = new Date();
		  setProjComplete(formatter.format(date));
		  setProjFinal(true); 
		  String output="";
		  
		  //Check if amtRemaining is 0 and store appropraite message 
		  if(amtRemaining==0) 
		  {
			  
			  output="Project Finalised , Thank You :)";
		  }
		  else if ( amtRemaining > 0) 
		  {
			  
		      output =  "\nTotal project costs to be paid by the client: R"+amtRemaining; 
		      output +=  "\n==========================================="; 
		      output += "\nProject #"+getProjId()+ " has been finalised on the date: "+getProjComplete();
		      output += "\n\nPlease make remaining payments asap.\nThanks for trusing Poised.";

		  
		  }
		  
	      return output; 
		
	}
	
	/**Method used to display project object in a formated way */
	public void displayProjs() {
		
		System.out.println("\n========= Project Name: " + getProjName() + " - Project ID:"+getProjId());
		System.out.println("-- Project Build Type:    \t"+getBuildType());
		System.out.println("-- Project ERF:           \t"+getProjErf());
		System.out.println("-- Project Adress:        \t"+getProjAdress());
		System.out.println("-- Total Fee:             \tR"+getTotalFee());
		System.out.println("-- Total Paid:            \tR"+getTotalPaid());
		System.out.println("-- Project Deadline:      \t"+getProjDeadline());
		System.out.println("-- Project Completed:     \t"+isProjFinal());
		System.out.println("...");
		System.out.println("--------------------------------------------------------------------");
	}
	
/** Project ID Getters and Setters*/ 
	
	@Override 
	public int getProjId() {
		return projId;
	}
	
	public void setProjId(int projNum) {
		this.projId=projNum;
	}	
	


/**Project Erf Getter and Setter*/	
	int getProjErf() {
		return projErf;
	}

	void setProjErf(int projErf) {
		this.projErf = projErf;
	}

/**Project Total Fee Getter and Setter*/	
	double getTotalFee() {
		return totalFee;
	}

	void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	
/**Project Total Fee Paid Getter and Setter*/	
	double getTotalPaid() {
		return totalPaid;
	}

	void setTotalPaid(double totalPaid) {
		this.totalPaid = totalPaid;
	}

/**Project Complete Getter and Setter*/	
	String getProjComplete() {
		return projComplete;
	}

	void setProjComplete(String projComplete) {
		this.projComplete = projComplete;
	}

/**Project Final Getter and Setter*/	
	boolean isProjFinal() {
		return projFinal;
	}

	void setProjFinal(boolean projFinal) {
		this.projFinal = projFinal;
	}

/**Project Deadline Getter and Setter*/		
	String getProjDeadline() {
		return projDeadline;
	}

	void setProjDeadline(String projDeadline) {
		this.projDeadline = projDeadline;
	}

/**Project Name Getter and Setter*/		
	String getProjName() {
		return projName;
	}

	void setProjName(String projName) {
		this.projName = projName;
	}

/**Project Adress Getter and Setter*/		
	String getProjAdress() {
		return projAdress;
	}

	void setProjAdress(String projAdress) {
		this.projAdress = projAdress;
	}

/**Project Build Getter and Setter*/		
	String getBuildType() {
		return buildType;
	}

	void setBuildType(String buildType) {
		this.buildType = buildType;
	}


}
