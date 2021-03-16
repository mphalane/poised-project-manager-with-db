/**
 * 
 * @author Matsobane Makhura
 * {@inheritDoc}
 *
 */
public class StakeholderFactory {
	
	/**
	 * Factory Design Pattern To Create Stakeholder Objects
	 * @param stkType this is the stakeholder type
	 * @param id this is the project ID
	 * */
	public Stakeholders getStakeholder(String stkType,int id) {
		
		if(stkType == null) {
			return null;
		}
	    if(stkType.equalsIgnoreCase("ARCHITECT")){
	         return new Architect(id);
	         
	     } else if(stkType.equalsIgnoreCase("CONSTRUCTOR")){
	         return new Constructor(id);
	         
	     } else if(stkType.equalsIgnoreCase("CLIENT")){
	         return new Client(id);
	     }		
		
		
		return null;
	}

}
