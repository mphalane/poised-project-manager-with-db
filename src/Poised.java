/**
* This project management system for a small structural engineering firm called “Poised”
* <p> 
* Poised does the engineering needed to ensure the structural integrity of various buildings. 
* This Java program will allow them to keep track of the many projects on which they work.
* @author Matsobane Makhura
* @version 3
*/




import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.sql.*;



public class Poised {
		
	
/**Class Scanner Vars*/
	private static Scanner numIn =new Scanner(System.in);
	private static Scanner strIn =new Scanner(System.in);
	final static String tblProjects = "projects";
	final static String tblArchitects = "architect";
	final static String tblManagers = "manager";
	final static String tblEngineers = "structural_eng";
	final static String tblClients = "customer";
/**Create List of objects*/
	private static List<Project> projectlist = new ArrayList<Project>();
/**Create factory object*/
	private static StakeholderFactory stakeholderFactory = new StakeholderFactory();
/**Create stakeholder object list*/
	private static List<Stakeholders> archlist = new ArrayList<Stakeholders>();
	private static List<Stakeholders> englist = new ArrayList<Stakeholders>();
	private static List<Stakeholders> managerlist = new ArrayList<Stakeholders>();
	private static List<Stakeholders> custlist = new ArrayList<Stakeholders>();
	
	

	
/**Main Project	
 * @throws SQLException */
	public static void main(String[] args) throws SQLException {
		
		String strUserOpt;
		String strEditOpt;
		
	/**Project Local Attributes*/
	
		int projNum=0; 
		int projErf=0;
		int projCount=0;
		double totalFee=0;
		double totalPaid=0;
		String projDeadline="";
		String projName="";
		String projAdress="";
		String buildType="";
		String projComplete="";
		boolean projFinal=false;
		boolean projDefaultName=false;
		
	/**Stakeholder Local Attributes*/	
		
		String stkName="";
		String stkTelephone="";
		String stkMail="";
		String stkAddress="";
		
	/**Files and Temp lists and arrays*/
		
		List<String> prevProjList=new ArrayList<String>();
		File fullProjectList = null;
		File completeList = null;
		File formatedList = null;
 
   /**Create a new file to store all the projects captured by user*/
		 /*try {
			 
			 fullProjectList= new File("full_projectlist.txt");
			 completeList= new File("completed_project.txt");
			 formatedList= new File("formated_fullproject.txt"); 
			 
			 if (fullProjectList.createNewFile()&&completeList.createNewFile()&&formatedList.createNewFile()) {
				 System.out.println("File created: "+fullProjectList.getName());
			 }
			 else {
				 System.out.println("Files are ready to be used :)");
			 }
			 
					 
		 }
		 catch(Exception e){
			 e.printStackTrace();
			 
		 }*/
		
		
		 Connection connection=null;
		 Statement statement=null;
		 ResultSet results = null;
		 int iNumRows=0;
		 
		 //Get the number of Rows in the tables in the database
		 try {
				//Connect to database
	
				connection=getConnectionToDB();
				
				//get number of rows from the result set
	
				iNumRows=countRows(connection,"manager");
				System.out.println("Number of rows:"+iNumRows);
				 connection.close();
				
		 }catch(Exception e) {
			 
			 System.out.println("Error occured while trying to fetch previous projects captured");
			 e.printStackTrace();
		 }
		 
		 //If project table is not empty create new objects and extract information from the database
		 if(iNumRows>0) {
			 
			 //read from database and add fields to into projlist,archlist,englist,managerlist,custlist 
			 try {
				 	
				 	connection=getConnectionToDB();
				 	statement=connection.createStatement();
					int rowsAffected;
					
					
					//Lets do some string handling
					for(int i=0;i<iNumRows;i++) {
						
						
						if(i==0) {
							results = statement.executeQuery("SELECT * FROM projects LIMIT 1");
						}
						else {
							results = statement.executeQuery("SELECT * FROM projects LIMIT 1,"+i);
						}
						
						//create new project,architect,constructor and client objects	
						projectlist.add(new Project(projNum));	
						
						while (results.next()) {
						projNum=results.getInt("id");
						projectlist.get(i).setProjId(projNum);
						
						projErf=results.getInt("erf_num");
						projectlist.get(i).setProjErf(projErf);
						
						totalFee=results.getDouble("total_fee");
						projectlist.get(i).setTotalFee(totalFee);
						
						totalPaid=results.getDouble("total_paid");
						projectlist.get(i).setTotalPaid(totalPaid);
						
						projDeadline=results.getString("deadline_date");
						projectlist.get(i).setProjDeadline(projDeadline);
						
						projName=results.getString("name");
						projectlist.get(i).setProjName(projName);
						
						projAdress=results.getString("phys_addr");
						projectlist.get(i).setProjAdress(projAdress);
						
						buildType=results.getString("build_type");
						projectlist.get(i).setBuildType(buildType);
				
						projFinal=results.getBoolean("is_completed");
						projectlist.get(i).setProjFinal(projFinal);
						
						projComplete=results.getString("completed_date");
						projectlist.get(i).setProjComplete(projComplete);
						}
						
						
						//Store Architect data in archlist objects
						
						if(i==0) {
							results = statement.executeQuery("SELECT * FROM architect LIMIT 1");
						}
						else {
							results = statement.executeQuery("SELECT * FROM architect LIMIT 1,"+i);
						}
						
						
						archlist.add(stakeholderFactory.getStakeholder("ARCHITECT", projNum));
						
						while (results.next()) {
						projNum=results.getInt("id");
						archlist.get(i).setID(projNum);
						stkName=results.getString("name");
						archlist.get(i).setName(stkName);
						
						stkTelephone=results.getString("tel_num");
						archlist.get(i).setTelNum(stkTelephone);
						
						stkMail=results.getString("email");
						archlist.get(i).setEmail(stkMail);
													
						stkAddress=results.getString("phys_addr");
						archlist.get(i).setPhysAdrr(stkAddress);
						}
						
						
						//Store Structural Engineer's data in englist objects
						
						if(i==0) {
							results = statement.executeQuery("SELECT * FROM structural_eng LIMIT 1");
						}
						else {
							results = statement.executeQuery("SELECT * FROM structural_eng LIMIT 1,"+i);
						}
						
						englist.add(stakeholderFactory.getStakeholder("CONSTRUCTOR", projNum));
						
						while (results.next()) {
						projNum=results.getInt("id");
						englist.get(i).setID(projNum);
						stkName=results.getString("name");
						englist.get(i).setName(stkName);
						stkTelephone=results.getString("tel_num");
						englist.get(i).setTelNum(stkTelephone);
						stkMail=results.getString("email");
						englist.get(i).setEmail(stkMail);
						
						stkAddress=results.getString("phys_addr");
						englist.get(i).setPhysAdrr(stkAddress);
						}
						
						//Store Manager's data in managerlist objects
						
						if(i==0) {
							results = statement.executeQuery("SELECT * FROM manager LIMIT 1");
						}
						else {
							results = statement.executeQuery("SELECT * FROM manager LIMIT 1,"+i);
						}
						
						
						managerlist.add(stakeholderFactory.getStakeholder("CONSTRUCTOR", projNum));
						
						while (results.next()) {
						projNum=results.getInt("id");
						managerlist.get(i).setID(projNum);
						stkName=results.getString("name");
						managerlist.get(i).setName(stkName);
						stkTelephone=results.getString("tel_num");
						managerlist.get(i).setTelNum(stkTelephone);
						stkMail=results.getString("email");
						managerlist.get(i).setEmail(stkMail);
						stkAddress=results.getString("phys_addr");
						managerlist.get(i).setPhysAdrr(stkAddress);
						}
						
						
						//Store Customers's data in custlist objects
						
						if(i==0) {
							results = statement.executeQuery("SELECT * FROM customer LIMIT 1");
						}
						else {
							results = statement.executeQuery("SELECT * FROM customer LIMIT 1,"+i);
						}
						
						
						custlist.add(stakeholderFactory.getStakeholder("CLIENT", projNum));
						
						while (results.next()) {
						projNum=results.getInt("id");
						custlist.get(i).setID(projNum);
						stkName=results.getString("name");
						custlist.get(i).setName(stkName);
						stkTelephone=results.getString("tel_num");
						custlist.get(i).setTelNum(stkTelephone);
						stkMail=results.getString("email");
						custlist.get(i).setEmail(stkMail);
						stkAddress=results.getString("phys_addr");
						custlist.get(i).setPhysAdrr(stkAddress);
						}

					}
					

					projCount=projectlist.size();
					projNum=0;
					//in.close();
					//prevProjects.close();
				 
			 }
			catch(Exception e){
				 
				 System.out.println("Error occured while trying to fetch previous projects captured");
				
			}finally{
				results.close();
				statement.close();
			}
			 
		 }	
		 
		
   /**Program Loop*/
		outer: while(true) {
			
			System.out.println("\n=== Welcome to the Poised Project Management System ===\n");

				
			//Ask user to make a choice
				inner: while(true) {
					
				//Get user option	
					System.out.println(">>> Please select the options below to continue:\n--Enter cp  - to create a new project\n--Enter ep  - to edit existing projects\n--Enter fp  - to finalize projects\n--Enter sid - to find project by Project ID\n--Enter sna - to find projects by Project Name\n--Enter ex  - to exit");
					System.out.print("\n-- Enter your option here:");
					strUserOpt= strIn.nextLine();
					
				//Remove whitespaces and make user input lower
					strUserOpt.strip();
					strUserOpt.toLowerCase();
					
					
				int projLen;
				//Get user choice and prompt user accordingly 
				outerswitch:switch(strUserOpt) 
					{
						case "cp":
							
						//create new project,architect,constructor and client objects	
							projectlist.add(new Project(projNum));	
							archlist.add(stakeholderFactory.getStakeholder("ARCHITECT", projNum));
							englist.add(stakeholderFactory.getStakeholder("CONSTRUCTOR", projNum));
							managerlist.add(stakeholderFactory.getStakeholder("CONSTRUCTOR", projNum));
							custlist.add(stakeholderFactory.getStakeholder("CLIENT", projNum));
							
						//Intro Text
							System.out.println(">>> Lets get started ,");
							System.out.print("\n-- 1. Capture Project Info\n");
							
						//Get user input and:
							projDefaultName=false;
							//set proj num
							do {
								try {
									System.out.println("-- Please enter the project number here:");
									projNum=numIn.nextInt();
									projectlist.get(projCount).setProjId(projNum);
								}
								catch(Exception e) {
									numIn.next();
									System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must be a number !");
								}
							} while (!(projNum>0));
							
							//Ask user if the project has a name 
							String tempAns;
							boolean correctInput=false;
							do {
									
									System.out.println("-- Do you have a name for this project [Y/N]:");
									tempAns=strIn.nextLine();
									if(!(tempAns.equalsIgnoreCase("Y") || tempAns.equalsIgnoreCase("N")))
									{
										correctInput=false;
										System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
									}
									else {
										correctInput=true;
									}
										
							}while(!correctInput);
							
							if(tempAns.equalsIgnoreCase("Y"))
							{  
								//set proj name ask if the project has a name 
								System.out.println("-- Please enter the project name here:");
								projName=strIn.nextLine();
								projectlist.get(projCount).setProjName(projName);
								projDefaultName=true;
							}
		
							System.out.println("-- Probe 1 "+ projDefaultName);
							//set proj build type
							System.out.println("-- What type of building is being designed ?");
							buildType=strIn.nextLine();
							projectlist.get(projCount).setBuildType(buildType);
							
							
							//set proj adress
							System.out.println("-- Please enter the physical address for the project here :");
							projAdress=strIn.nextLine();
							projectlist.get(projCount).setProjAdress(projAdress);
							
							//set proj erf
							do {
								try {
									System.out.println("-- Please enter the project ERF number here:");
									projErf=numIn.nextInt();
									projectlist.get(projCount).setProjErf(projErf);
									//newproj.setProjErf(projErf);
								}
								catch(Exception e) {
									numIn.next();
									System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must be a number !");
								}
							} while (!(projErf>0));
							
							
							//set proj total fee
							do {
								try {
									System.out.println("-- Please enter the total fee being charged for the project here:");
									totalFee=numIn.nextDouble();
									projectlist.get(projCount).setTotalFee(totalFee);
									//newproj.setTotalFee(totalFee);
								}
								catch(Exception e) {
									numIn.next();
									System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must be a number !");
								}
							} while (!(totalFee>0));
							
							
							//set proj total paid
							do {
								try {
									System.out.println("-- Please enter the total amount paid to date here:");
									totalPaid=numIn.nextDouble();
									projectlist.get(projCount).setTotalPaid(totalPaid);
								}
								catch(Exception e) {
									numIn.next();
									System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must be a number !");
								}
							} while (!(totalPaid>0));
							
							//set proj dead line
							System.out.println("-- Please enter the project deadline here --- format (yyyy-mm-dd):");
							projDeadline=strIn.nextLine();
							projectlist.get(projCount).setProjDeadline(projDeadline);

						//Set "finalise" boolean to false and create new project object
							projFinal=false;
							projectlist.get(projCount).setProjFinal(projFinal);
							
						//Capture Manager Information
							managerlist.get(projCount).setID(projNum);
							System.out.print("\n-- 3. Capture Project Manager Details Here \n");	
							
							System.out.println("-- Please enter the name of the constructor here:");
							stkName=strIn.nextLine();
							managerlist.get(projCount).setName(stkName);
							
							
							System.out.println("-- Please enter the telephone number of the project manager here:");
							stkTelephone=strIn.nextLine();
							managerlist.get(projCount).setTelNum(stkTelephone);
							
							System.out.println("-- Please enter the email address of the project manager here:");
							stkMail=strIn.nextLine();
							managerlist.get(projCount).setEmail(stkMail);
							
							
							System.out.println("-- Please enter the physical address of the project manager here:");
							stkAddress=strIn.nextLine();
							managerlist.get(projCount).setPhysAdrr(stkAddress);	
							
						//Capture Architect Information
							archlist.get(projCount).setID(projNum);
							System.out.print("\n-- 2. Capture architect for the project \n");	
							
							System.out.println("-- Please enter the name of the architect here:");
							stkName=strIn.nextLine();
							archlist.get(projCount).setName(stkName);
							
							
							System.out.println("-- Please enter the telephone number of the architect here:");
							stkTelephone=strIn.nextLine();
							archlist.get(projCount).setTelNum(stkTelephone);
							
							System.out.println("-- Please enter the email address of the architect here:");
							stkMail=strIn.nextLine();
							archlist.get(projCount).setEmail(stkMail);
							
							
							System.out.println("-- Please enter the physical address of the architect here:");
							stkAddress=strIn.nextLine();
							archlist.get(projCount).setPhysAdrr(stkAddress);
							
							
						//Capture Structural Engineer Information
							englist.get(projCount).setID(projNum);
							System.out.print("\n-- 3. Capture The Structural Engineer for this project \n");	
							
							System.out.println("-- Please enter the name of the constructor here:");
							stkName=strIn.nextLine();
							englist.get(projCount).setName(stkName);
							
							
							System.out.println("-- Please enter the telephone number of the engineer here:");
							stkTelephone=strIn.nextLine();
							englist.get(projCount).setTelNum(stkTelephone);
							
							System.out.println("-- Please enter the email address of the engineer here:");
							stkMail=strIn.nextLine();
							englist.get(projCount).setEmail(stkMail);
							
							
							System.out.println("-- Please enter the physical address of the engineer here:");
							stkAddress=strIn.nextLine();
							englist.get(projCount).setPhysAdrr(stkAddress);
							
						//Capture Client Information
							custlist.get(projCount).setID(projNum);
							System.out.print("\n-- 3. Capture client for the project \n");	
							
							System.out.println("-- Please enter the name of the client here:");
							stkName=strIn.nextLine();
							custlist.get(projCount).setName(stkName);
							
							
							System.out.println("-- Please enter the telephone client of the constructor here:");
							stkTelephone=strIn.nextLine();
							custlist.get(projCount).setTelNum(stkTelephone);
							
							System.out.println("-- Please enter the email address of the client here:");
							stkMail=strIn.nextLine();
							custlist.get(projCount).setEmail(stkMail);
							
							
							System.out.println("-- Please enter the physical address of the client here:");
							stkAddress=strIn.nextLine();
							custlist.get(projCount).setPhysAdrr(stkAddress);
							
						
						//Check if the project name is assigned
							if(!projDefaultName) 
							{
								projName=projectlist.get(projCount).getBuildType()+" "+custlist.get(projCount).getName();
								projectlist.get(projCount).setProjName(projName);
								//System.out.println("-- Hi I am a robot that just created the name :"+ projectlist.get(projCount).getProjName());
								
							}
						
						//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								appendProject(connection,projCount);
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
							}
							

								
						// increase the project count 
							projCount++;
							
						
						// break from this switch and go back to the inner loop
							break outerswitch;
						
								
							
						case "ep": //edit user captured projects
							
							// Check if the project object details have been captured if not display error message
							if (projectlist.isEmpty()) {
								//Display error message 
									System.out.println("--> Error : You cannot edit project detailts because they do not exist!\nPlease follow the prompts below to add project details." );
								
								// break from this switch and go back to the inner loop
									break outerswitch;
							}
							else
							{
								//Do Loop	
								String updateField="Y";	
								correctInput=false;
								do {
								//Show all projects that can be edited
									System.out.println(">>>   List of all Poised Projects	<<<");
									//Display all projects in 
									projLen = projectlist.size();	
									for(int i =0 ; i<projLen;i++) 
									{
										projectlist.get(i).displayProjs();
									}
								
								//Ask to select which project they want to edit
									int iEdit=-1;
									do {
										try {
											System.out.print("\n-- Please enter the Project ID you want to select:");
											projNum=numIn.nextInt();
											for(int i =0 ; i<projLen;i++) 
											{
												if(projectlist.get(i).getProjId()==projNum) {
													iEdit=i;
												}
											}
											
											if(iEdit==-1) {
												System.out.println("\n-- Opps, \n You have enetered an invalid input :( -- The Project ID you entered does not exist !");
											}
										}
										catch(Exception e) {
											numIn.next();
											System.out.println("\n-- Opps, \n You have enetered an invalid input :( \n-- Input must be a number !\n Or the Project ID you entered does not exist !");
										}
									} while (!(projNum>0 && iEdit>-1));
								//Allows user to select option to edit
									System.out.println("\n>>> You have selected:");
									System.out.println("-- Project ID:    \t"+projectlist.get(iEdit).getProjId());
									System.out.println("-- Project Name:  \t"+projectlist.get(iEdit).getProjName());
									System.out.println("\n>>> Please select the options below to continue:\n--Enter up  - to update project detials\n--Enter uac - to update architect's details\n--Enter um - to update manager’s details\n--Enter ueng - to update engineer’s details\n--Enter ucl - to update client’s details");
									System.out.print("\n-- Enter your option here:");
									strEditOpt=strIn.nextLine();
								
								//Get user choice and prompt user accordingly 
									
									userEditChoice(strEditOpt,iEdit); 
									
								//Ask user if they still want to edit	
									do {
										
										System.out.print("\n-- Do you still want to edit something else (Y/N):");
										updateField=strIn.nextLine();
										if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
										{
											correctInput=false;
											System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
										}
										else {
											correctInput=true;
										}
											
									}while(!correctInput);
								}while(updateField.equalsIgnoreCase("Y"));	
								// break from this switch and go back to the inner loop
									break outerswitch;
							}
							
						case "fp": //finalize project
						{
							//Checks weather the the user has captured any projects 
							if (projectlist.isEmpty()) {
								
								//Display error message 
									System.out.println("--> Error : You cannot finalize the project because it does not exist!\nPlease follow the prompts below to add project details." );
								// break from this switch and go back to the inner loop
									break outerswitch;
							}
							else 
							{
								
								System.out.println("\n>>> Please select the options below to continue:\n--Enter pc - to view projects to be completed\n--Enter pd - to view projects past due date\n--Enter ex - to go back to the previous menu");
								System.out.print("\n-- Enter your option here:");
								strEditOpt=strIn.nextLine();
								
								finalizeswitch:switch(strEditOpt) {
								case "pc":
									projLen = projectlist.size();
									int iListCount=0;
									System.out.println(">>>   "+projLen);
									System.out.println(">>>   List of all Poised Projects to be completed	<<<");
									for(int i =0 ; i<projLen;i++) 
									{	
										if(!(projectlist.get(i).isProjFinal()))
										{
											projectlist.get(i).displayProjs();
											iListCount+=1;
										}
					
									}
									
									System.out.println(">>> Probe2   "+projLen);
									if(iListCount==0) {
										
										System.out.println("--- Is Empty !");
										break  finalizeswitch;
									}
									else{
										
										//Call Method to show workflow to guide user to finalize work
										finalizeMenu();
									}
									
									System.out.println(">>> Probe3  "+projLen);
									
									break finalizeswitch;
								case "pd":
									
									projLen = projectlist.size();
									SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
									Date date = new Date();
									String strToday=formatter.format(date);
									
									Date dtToday;
									Date dtProjDate;
									String strProjDate;
									iListCount=0;
									
									System.out.println(">>>   List of all Poised Projects to be past due date	<<<");
									for(int i =0 ; i<projLen;i++) 
									{	
										strProjDate=projectlist.get(i).getProjDeadline();
										try {
										
											dtToday= new SimpleDateFormat("yyyy-MM-dd").parse(strToday);
											dtProjDate=new SimpleDateFormat("yyyy-MM-dd").parse(strProjDate);
											
										
											if(dtProjDate.compareTo(dtToday)<0 && !(projectlist.get(i).isProjFinal()))
											{
												projectlist.get(i).displayProjs();
												iListCount+=1;
											}
										
										
										}catch(ParseException e)
										{
											e.printStackTrace();
										}
									}
									
									if(iListCount==0) {
										
										System.out.println("--- Is Empty !");
										break  finalizeswitch;
									}
									else{
										
										//Call Method to show workflow to guide user to finalize work
										finalizeMenu();
									}
									
									
									break finalizeswitch;
													
								default:
									break finalizeswitch;
				
								}
								
								// break the inner loop and go back to the outer loop
									break inner;
							}
						}
						
						case "sid":	
							 projLen=projectlist.size();
							
							//Ask to select which project they want to edit
								int iEdit=-1;
								do {
									try {
										System.out.print("\n-- Please enter the Project ID you want to select:");
										projNum=numIn.nextInt();
										for(int i =0 ; i<projLen;i++) 
										{
											if(projectlist.get(i).getProjId()==projNum) {
												iEdit=i;
											}
										}
										
										if(iEdit==-1) {
											System.out.println("\n-- Opps, \n You have enetered an invalid input :( -- The Project ID you entered does not exist !");
										}
									}
									catch(Exception e) {
										numIn.next();
										System.out.println("\n-- Opps, \n You have enetered an invalid input :( \n-- Input must be a number !\n Or the Project ID you entered does not exist !");
									}
								} while (!(projNum>0 && iEdit>-1));
						
								//Allows user to select option to edit
								System.out.println("\n>>> You have selected:");
								System.out.println("-- Project ID:    \t"+projectlist.get(iEdit).getProjId());
								System.out.println("-- Project Name:  \t"+projectlist.get(iEdit).getProjName());
								
								//Ask user if they want to edit or finalise?
										
								if(projectlist.get(iEdit).isProjFinal()) {
									System.out.println("\n>>> Please select the options below to continue:\n--Enter ep  - to edit this project\n--Enter ex  - to go back to previous menu");
								}	
								else {
									System.out.println("\n>>> Please select the options below to continue:\n--Enter ep  - to edit this project\n--Enter fp  - to finalise this project\n--Enter ex  - to go back to previous menu");
								}
								
								System.out.print("\n-- Enter your option here:");
								strEditOpt=strIn.nextLine();
								
									searchidswitch: switch(strEditOpt) {
									
									case "ep":
										System.out.println("\n>>> Please select the options below to continue:\n--Enter up  - to update project detials\n--Enter uac - to update architect's details\n--Enter um - to update manager’s details\n--Enter ueng - to update engineer’s details\n--Enter ucl - to update client’s details");
										System.out.print("\n-- Enter your option here:");
										strEditOpt=strIn.nextLine();
									
									//Get user choice and prompt user accordingly 
										userEditChoice(strEditOpt,iEdit); 
										
									//break	
										break searchidswitch;
									case "fp":
										int projDone=0;
										String userAns;
										correctInput=false;
										do {
											
											System.out.print("\n-- Do you to finalize this project? (Y/N):");
											userAns=strIn.nextLine();
											if(!(userAns.equalsIgnoreCase("Y") || userAns.equalsIgnoreCase("N")))
											{
												correctInput=false;
												System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
											}
											else {
												correctInput=true;
											}
												
										}while(!correctInput);
										
										if(userAns.equalsIgnoreCase("Y") ) {
											//call new project method to calculate remaining amount 
												String strfinal=projectlist.get(iEdit).finalizeProj();
											//Check if there is any remaining amount and print the respective msg
												String strInvoice="";
												strInvoice=remainingAmtCheck(strfinal,iEdit);
												System.out.println(strInvoice);
											//call method to update objects to tables 
												
												connection=null;
												try {
												 	connection=getConnectionToDB();
													updateStringOrDate(connection,tblProjects,"completed_date",projectlist.get(iEdit).getProjComplete(),projNum);
													if(projectlist.get(iEdit).isProjFinal()){
														projDone=1;
													}
													else {
														projDone=0;
													}
													updateIntOrBit(connection,tblProjects,"is_completed",projDone,projNum);
												}catch(Exception e) {
													 System.out.println("Error occured while trying to connected to database :(");
													 e.printStackTrace();
													 System.exit(0);
												}
											
											}
										//break	
										break searchidswitch;
									case "ex":
										// break the inner loop and go back to the outer loop
										break inner;
									default :
										System.out.println("\n-- Opps, You have enetered an invalid input :(");
										break searchidswitch;
									}
								
								// break the inner loop and go back to the outer loop
								break inner;
						case "sna":	
							
							projName="";
							projLen=projectlist.size();
							//Ask to select which project they want to edit
								iEdit=-1;
								do {
									try {
										System.out.print("\n-- Please enter the Project Name you want to select:");
										projName=strIn.nextLine();
										for(int i =0 ; i<projLen;i++) 
										{
											projName.trim();
											if(projectlist.get(i).getProjName().equalsIgnoreCase(projName)) {
												iEdit=i;
											}
										}
										
										if(iEdit==-1) {
											System.out.println("\n-- Opps, \n You have enetered an invalid input :( -- The Project Name you entered does not exist !");
										}
									}
									catch(Exception e) {
										strIn.next();
										System.out.println("\n-- Opps, \n You have enetered an invalid input :( \n-- Input must be a number !\n Or the Project Name you entered does not exist !");
									}
								} while (projName.isBlank() || projName.isEmpty() || !(iEdit>-1));
						
								//Allows user to select option to edit
								System.out.println("\n>>> You have selected:");
								System.out.println("-- Project ID:    \t"+projectlist.get(iEdit).getProjId());
								System.out.println("-- Project Name:  \t"+projectlist.get(iEdit).getProjName());
								
								//Ask user if they want to edit or finalise?
										
								if(projectlist.get(iEdit).isProjFinal()) {
									System.out.println("\n>>> Please select the options below to continue:\n--Enter ep  - to edit this project\n--Enter ex  - to go back to previous menu");
								}	
								else {
									System.out.println("\n>>> Please select the options below to continue:\n--Enter ep  - to edit this project\n--Enter fp  - to finalise this project\n--Enter ex  - to go back to previous menu");
								}
								
								System.out.print("\n-- Enter your option here:");
								strEditOpt=strIn.nextLine();
								searchswitch: switch(strEditOpt) {
								
								case "ep":
									System.out.println("\n>>> Please select the options below to continue:\n--Enter up  - to update project detials\n--Enter uac - to update architect's details\n--Enter um - to update manager’s details\n--Enter ueng - to update engineer’s details\n--Enter ucl - to update client’s details");
									System.out.print("\n-- Enter your option here:");
									strEditOpt=strIn.nextLine();
								
								//Get user choice and prompt user accordingly 
									userEditChoice(strEditOpt,iEdit); 
									
								//break	
									break searchswitch;
								case "fp":
									
									String userAns;
									int projDone=0;
									correctInput=false;
									do {
										
										System.out.print("\n-- Do you to finalize this project? (Y/N):");
										userAns=strIn.nextLine();
										if(!(userAns.equalsIgnoreCase("Y") || userAns.equalsIgnoreCase("N")))
										{
											correctInput=false;
											System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
										}
										else {
											correctInput=true;
										}
											
									}while(!correctInput);
									
									if(userAns.equalsIgnoreCase("Y") ) {
										//call new project method to calculate remaining amount 
											String strfinal=projectlist.get(iEdit).finalizeProj();
										//Check if there is any remaining amount and print the respective msg
											String strInvoice="";
											strInvoice=remainingAmtCheck(strfinal,iEdit);
											System.out.println(strInvoice);
										//call method to update objects to tables 
											
											connection=null;
											try {
											 	connection=getConnectionToDB();
												updateStringOrDate(connection,tblProjects,"completed_date",projectlist.get(iEdit).getProjComplete(),projectlist.get(iEdit).getProjId());
												if(projectlist.get(iEdit).isProjFinal()){
													projDone=1;
												}
												else {
													projDone=0;
												}
												updateIntOrBit(connection,tblProjects,"is_completed",projDone,projectlist.get(iEdit).getProjId());
											}catch(Exception e) {
												 System.out.println("Error occured while trying to connected to database :(");
												 e.printStackTrace();
												 System.exit(0);
											}
										
										}
									
									//break	
									break searchswitch;
								case "ex":
									// break the inner loop and go back to the outer loop
									break inner;
								default :
									System.out.println("\n-- Opps, You have enetered an invalid input :(");
									break searchswitch;
								}
								
								
								// break the inner loop and go back to the outer loop
								break inner;		
						case "ex": //exit
							
								// break everything
								System.exit(0);
								break outer;
								
						default: //if choice incorrect display this until correct choice chosen
			
							System.out.println("\n-- Option you've selected is incorrect,Please try again\n");
							// break from this switch and go back to the inner loop
							break outerswitch;
						
					}
				}
			}		 
			
			

		 
		 
		 
	}

/** Method to Checks if there is any remaining amount to be paid*/
private static void finalizeMenu() {
	Connection connection=null;
	boolean correctInput;
	int projNum=0;
	int projLen=projectlist.size();
	int projDone=0;
	//Do Loop	
	String updateField="Y";	
	correctInput=false;
	
	//Do loop to check if the user has the correct input
	do {
		
		System.out.print("\n-- Do you to finalize a specific project (Y/N):");
		updateField=strIn.nextLine();
		if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
		{
			correctInput=false;
			System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
		}
		else {
			correctInput=true;
		}
			
	}while(!correctInput);
	
	
	//Ask to select which project they want to edit
	if(updateField.equalsIgnoreCase("Y")){	
		
		int iEdit=-1;
		do {
			try {
				System.out.print("\n-- Please enter the Project ID you want to select:");
				projNum=numIn.nextInt();
				for(int i =0 ; i<projLen;i++) 
				{
					if(projectlist.get(i).getProjId()==projNum) {
						iEdit=i;
					}
				}
				
				if(iEdit==-1) {
					System.out.println("\n-- Opps, \n You have enetered an invalid input :( -- The Project ID you entered does not exist !");
				}
			}
			catch(Exception e) {
				numIn.next();
				System.out.println("\n-- Opps, \n You have enetered an invalid input :( \n-- Input must be a number !\n Or the Project ID you entered does not exist !");
			}
		} while (!(projNum>0 && iEdit>-1));
		
		System.out.println("\n>>> You have selected:");
		System.out.println("-- Project ID:    \t"+projectlist.get(iEdit).getProjId());
		System.out.println("-- Project Name:  \t"+projectlist.get(iEdit).getProjName());
		
		String userAns;
		correctInput=false;
		do {
			
			System.out.print("\n-- Do you to finalize this project? (Y/N):");
			userAns=strIn.nextLine();
			if(!(userAns.equalsIgnoreCase("Y") || userAns.equalsIgnoreCase("N")))
			{
				correctInput=false;
				System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
			}
			else {
				correctInput=true;
			}
				
		}while(!correctInput);
		
		if(userAns.equalsIgnoreCase("Y") ) {
		//call new project method to calculate remaining amount 
			String strfinal=projectlist.get(iEdit).finalizeProj();
		//Check if there is any remaining amount and print the respective msg
			String strInvoice="";
			strInvoice=remainingAmtCheck(strfinal,iEdit);
			System.out.println(strInvoice);
		//call method to update objects to tables 
			
			connection=null;
			try {
			 	connection=getConnectionToDB();
				updateStringOrDate(connection,tblProjects,"completed_date",projectlist.get(iEdit).getProjComplete(),projNum);
				if(projectlist.get(iEdit).isProjFinal()){
					projDone=1;
				}
				else {
					projDone=0;
				}
				updateIntOrBit(connection,tblProjects,"is_completed",projDone,projNum);
			}catch(Exception e) {
				 System.out.println("Error occured while trying to connected to database :(");
				 e.printStackTrace();
				 System.exit(0);
			}
		
		}
		
		
	}
	
}

/**
 * Method to connect to database
 * @return returns connection
 */
public static Connection getConnectionToDB() throws Exception{
	
	//Connection Variables
	
	String url="jdbc:mysql://localhost:3306/PoisePMS?allowPublicKeyRetrieval=true&useSSL=false";
	String username="tsobi";
	String password="swordfish";
	
	//Load Driver for portability here : Class.forName(driverString)
	
	//Connect to database
	Connection conn=DriverManager.getConnection(url,username,password);
	
	return conn;		
	
} 
/**
 * Method to get number of rows in the database
 * @param conn jdbc conncetion string
 * @param tableName name of table in database
 * @return returns rowCount of the specified table
 */
public static int countRows(Connection conn, String tableName) throws SQLException{
	Statement stmt=null;
	ResultSet rs=null;
	int rowCount=-1;
	
	try {
		stmt=conn.createStatement();
		rs=stmt.executeQuery("SELECT COUNT(*) FROM "+tableName);
		
		//get number of rows from the result set
		while (rs.next()) {
		rowCount=rs.getInt(1);
		}
	}finally {
		rs.close();
		stmt.close();
	}
	return rowCount;
}

/**
 * Method to Checks if there is any remaining amount to be paid
 * @param strfinal string input
 * @param i index of selected project
 * @return returns invoice
 */
private static String remainingAmtCheck(String strfinal,int i) {
		//Checks if there is any remaining amount to be paid if not don't print invoice
		//if true print invoice
		String output = "";
		if(strfinal=="Project Finalised , Thank You :)") {
			output=strfinal;
		}
		else { 
		output="\nProject Name: " + projectlist.get(i).getProjName() + " - Project ID:"+projectlist.get(i).getProjId()+"=";	
		output+="\n\nInvoice for " + custlist.get(i).getName();
		output+="\n==========================================="; 
		output+="\nClient's contact number:  \t"+custlist.get(i).getTelNum();
		output+="\nClient's contact email:   \t"+custlist.get(i).getEmail();
		output+="\n==========================================="; 
		output+="\n"+strfinal;
		}
		
		//Append this to external file
		//appendCompProj("completed_project.txt",i);
		
		return output;
	}	

/**
 * Method to append project object in json style
 * @param conn name of the connection string that connects to the database
 * @param i index of selected project
 * @throws SQLException 
 */

private static void appendProject(Connection conn ,int i) throws SQLException {
	
	
		Statement stmt=null;
		
		 int rowsAffected;
		try {
			 //Connect to database
				stmt=conn.createStatement();
			 //Statements To Add Value into tables 
				
				int isFinal=0;
				if(projectlist.get(i).isProjFinal()) {
					isFinal=1;
				}
				else {
					isFinal=0;
				}
				rowsAffected = stmt.executeUpdate("INSERT INTO projects(id,name,build_type,erf_num,phys_addr,total_fee,total_paid,deadline_date,completed_date,is_completed) VALUES ("+projectlist.get(i).getProjId()+",'"+projectlist.get(i).getProjName()+"','"+projectlist.get(i).getBuildType()+"',"+projectlist.get(i).getProjErf()+",'"+projectlist.get(i).getProjAdress()+"',"+projectlist.get(i).getTotalFee()+","+projectlist.get(i).getTotalPaid()+",'"+projectlist.get(i).getProjDeadline()+"',"+projectlist.get(i).getProjComplete()+","+isFinal+")");
				 
				System.out.println("\n Successfully uploaded :" +rowsAffected+" row into projects table :)");	
				
				rowsAffected = stmt.executeUpdate("INSERT INTO manager VALUES ("+managerlist.get(i).getProjNum()+",'"+managerlist.get(i).getName()+"','"+managerlist.get(i).getTelNum()+"','"+managerlist.get(i).getEmail()+"','"+managerlist.get(i).getPhysAdrr()+"')");

				System.out.println("\n Successfully uploaded :" +rowsAffected+" row into manager table :)");	
			 
				rowsAffected = stmt.executeUpdate("INSERT INTO architect VALUES ("+archlist.get(i).getProjNum()+",'"+archlist.get(i).getName()+"','"+archlist.get(i).getTelNum()+"','"+archlist.get(i).getEmail()+"','"+archlist.get(i).getPhysAdrr()+"')");

				System.out.println("\n Successfully uploaded :" +rowsAffected+" row into architect table :)");	 
				
				rowsAffected = stmt.executeUpdate("INSERT INTO structural_eng VALUES ("+englist.get(i).getProjNum()+",'"+englist.get(i).getName()+"','"+englist.get(i).getTelNum()+"','"+englist.get(i).getEmail()+"','"+englist.get(i).getPhysAdrr()+"')");

				System.out.println("\n Successfully uploaded :" +rowsAffected+" row into structural_eng table :)");	
				
				rowsAffected = stmt.executeUpdate("INSERT INTO customer VALUES ("+custlist.get(i).getProjNum()+",'"+custlist.get(i).getName()+"','"+custlist.get(i).getTelNum()+"','"+custlist.get(i).getEmail()+"','"+custlist.get(i).getPhysAdrr()+"')");

				System.out.println("\n Successfully uploaded :" +rowsAffected+" row into customer table :)");	
				
		 }catch(Exception e){
			 e.printStackTrace();
			 System.out.println("An error has occured while trying to upload the project :(");							
		}finally {
			 stmt.close();
		}

	}

/**
 * Method to update string or date field of the table
 * @param conn to connect to the table in the database
 * @param tableName the name of table in the database
 * @param colName the name of the column in the database that we want to update 
 * @param newValue stores the new Value
 * @param id the id of the row we want to update 
 * @throws SQLException 
 */
private static void updateStringOrDate(Connection conn ,String tableName,String colName,String newValue,int id) throws SQLException {
	
		Statement stmt=null;
	
	 int rowsAffected;
	try {
		 //Connect to database
			stmt=conn.createStatement();
		 //Statements To Add Value into tables 
			rowsAffected = stmt.executeUpdate("UPDATE "+ tableName +" SET "+colName+"= '"+newValue+"' WHERE id="+id); 
			System.out.println("\n Successfully uploaded :" +rowsAffected+" row into "+tableName+":)");	
			
	 }catch(Exception e){
		 e.printStackTrace();
		 System.out.println("An error has occured while trying to update the field in the database :(");							
	}finally {
		 stmt.close();
	}
}

/**
 * Method to update string or date field of the table
 * @param conn to connect to the table in the database
 * @param tableName the name of table in the database
 * @param colName the name of the column in the database that we want to update 
 * @param newValue stores the new Value
 * @param id the id of the row we want to update 
 * @throws SQLException 
 */
private static void updateIntOrBit(Connection conn ,String tableName,String colName,int newValue,int id) throws SQLException {
	
	Statement stmt=null;

	int rowsAffected;
	try {
		 //Connect to database
			stmt=conn.createStatement();
		 //Statements To Add Value into tables 
			rowsAffected = stmt.executeUpdate("UPDATE "+ tableName +" SET "+colName+"="+newValue+" WHERE id="+id); 
			System.out.println("\n Successfully uploaded :" +rowsAffected+" row into"+tableName+":)");	
			
	 }catch(Exception e){
		 e.printStackTrace();
		 System.out.println("An error has occured while trying to upload the project :(");							
	}finally {
		 stmt.close();
	}
}

/**
 * Method to update string or date field of the table
 * @param conn to connect to the table in the database
 * @param tableName the name of table in the database
 * @param colName the name of the column in the database that we want to update 
 * @param newValue stores the new Value
 * @param id the id of the row we want to update 
 * @throws SQLException 
 */
private static void updateDouble(Connection conn ,String tableName,String colName,double newValue,int id) throws SQLException {
	
	Statement stmt=null;

	int rowsAffected;
	try {
		 //Connect to database
			stmt=conn.createStatement();
		 //Statements To Add Value into tables 
			rowsAffected = stmt.executeUpdate("UPDATE "+ tableName +" SET "+colName+"="+newValue+" WHERE id="+id); 
			System.out.println("\n Successfully uploaded :" +rowsAffected+" row into"+tableName+":)");	
			
	 }catch(Exception e){
		 e.printStackTrace();
		 System.out.println("An error has occured while trying to upload the project :(");							
	}finally {
		 stmt.close();
	}
}


/**
 * Method for user edit Options
 * @param strFileName name of the text file to store info
 * @param i index of selected project 
 */
private static void userEditChoice(String strEditOpt,int i) {
	
		Connection connection=null;
		innerswitch: switch(strEditOpt){
			case "up": //update project details
			{

				
				String updateField;
				updateField="Y";
				String strUpdateOpt;
				System.out.println("\n>>> You have chosen to edit the project detials: ");
				
				//new menu 
				do {
					//show old project details
					System.out.println("\n========= Project Name: " + projectlist.get(i).getProjName() + " - Project ID:"+projectlist.get(i).getProjId());
					System.out.println("-- Project Build Type:    \t"+projectlist.get(i).getBuildType());
					System.out.println("-- Project ERF:           \t"+projectlist.get(i).getProjErf());
					System.out.println("-- Project Adress:        \t"+projectlist.get(i).getProjAdress());
					System.out.println("-- Total Fee:             \tR"+projectlist.get(i).getTotalFee());
					System.out.println("-- Total Paid:            \tR"+projectlist.get(i).getTotalPaid());
					System.out.println("-- Project Deadline:      \t"+projectlist.get(i).getProjDeadline());
					System.out.println("-- Project Completed:     \t"+projectlist.get(i).isProjFinal());
					System.out.println("...");
					System.out.println("--------------------------------------------------------------------");
					
					System.out.println("\n>>> Please select the options below to continue:\n--Enter pn  - to update project name\n--Enter pt - to update the total fee being charged for the project\n--Enter ptd - to update the total amount paid to date\n--Enter pd - to update the deadline for the project\n--Enter ex - to go back to the previous menu");
					System.out.print("\n-- Enter your option here:");
					strUpdateOpt=strIn.nextLine();
				
					boolean correctInput=false;
					
						projectswitch:switch(strUpdateOpt) {
						case "pn":
							System.out.println("-- Please enter the new project name here: ");
							strUpdateOpt=strIn.nextLine();
							projectlist.get(i).setProjName(strUpdateOpt); 
							
							
							//call method to update objects to tables 
								
								connection=null;
								try {
								 	connection=getConnectionToDB();
									updateStringOrDate(connection,tblProjects,"name",projectlist.get(i).getProjName(),projectlist.get(i).getProjId());
									
								}catch(Exception e) {
									 System.out.println("Error occured while trying to connected to database :(");
									 e.printStackTrace();
									 System.exit(0);
								}
									
							//Ask user if they want to update another field
							do {
								
								System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
								updateField=strIn.nextLine();
								if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
								{
									correctInput=false;
									System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
								}
								else {
									correctInput=true;
								}
									
							}while(!correctInput);
							
							break projectswitch;
						case "pt":	
							
							//set proj total fee
							double rtotalFee=-1;
							do {
								try {
									System.out.println("-- Please enter the new  total fee being charged for the project here:");
									rtotalFee=numIn.nextDouble();
									projectlist.get(i).setTotalFee(rtotalFee);
									//newproj.setTotalFee(totalFee);
								}
								catch(Exception e) {
									numIn.next();
									System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must be a number !");
								}
							} while (!(rtotalFee>0));

							//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								updateDouble(connection,tblProjects,"total_fee",projectlist.get(i).getTotalFee(),projectlist.get(i).getProjId());
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
								 System.exit(0);
							}
							
							//Ask user if they want to update another field
							correctInput=false;
							do {
								
								System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
								updateField=strIn.nextLine();
								if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
								{
									correctInput=false;
									System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
								}
								else {
									correctInput=true;
								}
									
							}while(!correctInput);
						
							break projectswitch;
						case "ptd":
							//set proj total fee paid
							double rtotalPaid=-1;
							do {
								try {
									System.out.println("-- Please enter the new  total fee to be paid for the project here:");
									rtotalPaid=numIn.nextDouble();
									projectlist.get(i).setTotalPaid(rtotalPaid);
									//newproj.setTotalFee(totalFee);
								}
								catch(Exception e) {
									numIn.next();
									System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must be a number !");
								}
							} while (!(rtotalPaid>0));

							//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								updateDouble(connection,tblProjects,"total_paid",projectlist.get(i).getTotalPaid(),projectlist.get(i).getProjId());
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
								 System.exit(0);
							}
							
							//Ask user if they want to update another field
							correctInput=false;
							do {
								
								System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
								updateField=strIn.nextLine();
								if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
								{
									correctInput=false;
									System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
								}
								else {
									correctInput=true;
								}
									
							}while(!correctInput);
						
							
							break projectswitch;
						case "pd":
							String projDeadline;
							System.out.println("-- Please enter the project deadline here --- format (yyyy-mm-dd):");
							projDeadline=strIn.nextLine();
							projectlist.get(i).setProjDeadline(projDeadline);
							
							//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								updateStringOrDate(connection,tblProjects,"deadline_date",projectlist.get(i).getProjDeadline(),projectlist.get(i).getProjId());
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
								 System.exit(0);
							}
							
							//Ask user if they want to update another field
							do {
									
									System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
									updateField=strIn.nextLine();
									if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
									{
										correctInput=false;
										System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
									}
									else {
										correctInput=true;
									}
										
							}while(!correctInput);
							
							break projectswitch;
						
				 	default:
				 			updateField="N";
							break projectswitch;
				}
						
				}while(updateField.equalsIgnoreCase("Y"));	
				
				break innerswitch;
				
			}
			case "uac"://update architect
			{
				
				String updateField;
				updateField="Y";
				String strUpdateOpt;
				System.out.println("\n>>> You have chosen to edit the architect detials of Project Name: " +projectlist.get(i).getProjName());
				
				//call method that clear text file and update

				//new menu 
				do {
					//show old architect details
					System.out.println("\n========= Architect Details");
					System.out.println("-- Architect Name:                        \t"+archlist.get(i).getName());
					System.out.println("-- Architect Telephone Number:            \t"+archlist.get(i).getTelNum());
					System.out.println("-- Architect Email Adress:                \t"+archlist.get(i).getEmail());
					System.out.println("-- Architect Physical Adress:             \t"+archlist.get(i).getPhysAdrr());
					System.out.println("...");
					System.out.println("--------------------------------------------------------------------");
					
					System.out.println("\n>>> Please select the options below to continue:\n--Enter anam  - to update architect's name\n--Enter atel - to update the architect's telephone number\n--Enter aema - to update architect's email adress\n--Enter aphy - to update architect's physical adress\n--Enter ex - to go back to the previous menu");
					System.out.print("\n-- Enter your option here:");
					strUpdateOpt=strIn.nextLine();
					boolean correctInput=false;
						archswitch:switch(strUpdateOpt) {
						
						case"anam":
							
							System.out.println("-- Please enter the new architect's name here:");
							String stkName=strIn.nextLine();
							archlist.get(i).setName(stkName); 
							
							//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								updateStringOrDate(connection,tblArchitects,"name",archlist.get(i).getName(),archlist.get(i).getProjNum());
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
								 System.exit(0);
							}
							
							//Ask user if they want to update another field
							do {
									
									System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
									updateField=strIn.nextLine();
									if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
									{
										correctInput=false;
										System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
									}
									else {
										correctInput=true;
									}
										
							}while(!correctInput);
							
							break archswitch;
						case"atel":
							System.out.println("-- Please enter the new telephone number of the architect here:");
							archlist.get(i).setTelNum(strIn.nextLine()); 
							
							//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								updateStringOrDate(connection,tblArchitects,"tel_num",archlist.get(i).getTelNum(),archlist.get(i).getProjNum());
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
								 System.exit(0);
							}
							
							//Ask user if they want to update another field
							do {
									
									System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
									updateField=strIn.nextLine();
									if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
									{
										correctInput=false;
										System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
									}
									else {
										correctInput=true;
									}
										
							}while(!correctInput);
							break archswitch;
						case"aema":
							System.out.println("-- Please enter the new email adress of the architect here:");
							archlist.get(i).setEmail(strIn.nextLine()); 
							
							//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								updateStringOrDate(connection,tblArchitects,"email",archlist.get(i).getEmail(),archlist.get(i).getProjNum());
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
								 System.exit(0);
							}
							
							//Ask user if they want to update another field
							do {
									
									System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
									updateField=strIn.nextLine();
									if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
									{
										correctInput=false;
										System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
									}
									else {
										correctInput=true;
									}
										
							}while(!correctInput);
							break archswitch;
						case"aphy":
							System.out.println("-- Please enter the new physical adress of the architect here:");
							archlist.get(i).setPhysAdrr(strIn.nextLine()); 
							
							//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								updateStringOrDate(connection,tblArchitects,"phys_addr",archlist.get(i).getPhysAdrr(),archlist.get(i).getProjNum());
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
								 System.exit(0);
							}
							
							//Ask user if they want to update another field
							do {
									
									System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
									updateField=strIn.nextLine();
									if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
									{
										correctInput=false;
										System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
									}
									else {
										correctInput=true;
									}
										
							}while(!correctInput);
							break archswitch;
						default:
							updateField="N";
							break archswitch;
						}
				}while(updateField.equalsIgnoreCase("Y"));
				
				break innerswitch;
			}
			case "um"://update manager details
			{
				String updateField;
				updateField="Y";
				String strUpdateOpt;
				System.out.println("\n>>> You have chosen to edit the manager detials of Project Name: " +projectlist.get(i).getProjName());
				
				//call method that clear text file and update

				//new menu 
				do {
					//show old architect details
					System.out.println("\n========= Manager Details");
					System.out.println("-- Manager Name:                        \t"+managerlist.get(i).getName());
					System.out.println("-- Manager Telephone Number:            \t"+managerlist.get(i).getTelNum());
					System.out.println("-- Manager Email Adress:                \t"+managerlist.get(i).getEmail());
					System.out.println("-- Manager Physical Adress:             \t"+managerlist.get(i).getPhysAdrr());
					System.out.println("...");
					System.out.println("--------------------------------------------------------------------");
					
					System.out.println("\n>>> Please select the options below to continue:\n--Enter cnam  - to update manager's name\n--Enter ctel - to update the manager's telephone number\n--Enter cema - to update manager's email adress\n--Enter cphy - to update manager's physical adress\n--Enter ex - to go back to the previous menu");
					System.out.print("\n-- Enter your option here:");
					strUpdateOpt=strIn.nextLine();
					boolean correctInput=false;
						conswitch:switch(strUpdateOpt) {
						
						case"cnam":
							System.out.println("-- Please enter the new manager's name here:");
							managerlist.get(i).setName(strIn.nextLine()); 
							
							//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								updateStringOrDate(connection,tblManagers,"name",managerlist.get(i).getName(),managerlist.get(i).getProjNum());
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
								 System.exit(0);
							}
							
							//Ask user if they want to update another field
							do {
									
									System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
									updateField=strIn.nextLine();
									if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
									{
										correctInput=false;
										System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
									}
									else {
										correctInput=true;
									}
										
							}while(!correctInput);
							
							break conswitch;
						case"ctel":
							System.out.println("-- Please enter the new telephone number of the manager here:");
							managerlist.get(i).setTelNum(strIn.nextLine()); 
							
							//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								updateStringOrDate(connection,tblManagers,"tel_num",managerlist.get(i).getTelNum(),managerlist.get(i).getProjNum());
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
								 System.exit(0);
							}
							
							//Ask user if they want to update another field
							do {
									
									System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
									updateField=strIn.nextLine();
									if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
									{
										correctInput=false;
										System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
									}
									else {
										correctInput=true;
									}
										
							}while(!correctInput);
							break conswitch;
						case"cema":
							System.out.println("-- Please enter the new email adress of the manager here:");
							managerlist.get(i).setEmail(strIn.nextLine()); 
							
							//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								updateStringOrDate(connection,tblManagers,"email",managerlist.get(i).getEmail(),managerlist.get(i).getProjNum());
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
								 System.exit(0);
							}
							//Ask user if they want to update another field
							do {
									
									System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
									updateField=strIn.nextLine();
									if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
									{
										correctInput=false;
										System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
									}
									else {
										correctInput=true;
									}
										
							}while(!correctInput);
							break conswitch;
						case"cphy":
							System.out.println("-- Please enter the new physical adress of the manager here:");
							managerlist.get(i).setPhysAdrr(strIn.nextLine()); 
							
							//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								updateStringOrDate(connection,tblManagers,"phys_addr",managerlist.get(i).getPhysAdrr(),managerlist.get(i).getProjNum());
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
								 System.exit(0);
							}
							//Ask user if they want to update another field
							do {
									
									System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
									updateField=strIn.nextLine();
									if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
									{
										correctInput=false;
										System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
									}
									else {
										correctInput=true;
									}
										
							}while(!correctInput);
							break conswitch;
						default:
							updateField="N";
							break conswitch;
						}
				}while(updateField.equalsIgnoreCase("Y"));
				
				break innerswitch;
			}
			
			case "ueng"://update engineer details
			{
				String updateField;
				updateField="Y";
				String strUpdateOpt;
				System.out.println("\n>>> You have chosen to edit the structural engineer detials of Project Name: " +projectlist.get(i).getProjName());
				
				//call method that clear text file and update

				//new menu 
				do {
					//show old architect details
					System.out.println("\n========= Enginee Details");
					System.out.println("-- Engineer Name:                        \t"+englist.get(i).getName());
					System.out.println("-- Engineer Telephone Number:            \t"+englist.get(i).getTelNum());
					System.out.println("-- Engineer Email Adress:                \t"+englist.get(i).getEmail());
					System.out.println("-- Engineer Physical Adress:             \t"+englist.get(i).getPhysAdrr());
					System.out.println("...");
					System.out.println("--------------------------------------------------------------------");
					
					System.out.println("\n>>> Please select the options below to continue:\n--Enter cnam  - to update engineer's name\n--Enter ctel - to update the engineer's telephone number\n--Enter cema - to update engineer's email adress\n--Enter cphy - to update engineer's physical adress\n--Enter ex - to go back to the previous menu");
					System.out.print("\n-- Enter your option here:");
					strUpdateOpt=strIn.nextLine();
					boolean correctInput=false;
						conswitch:switch(strUpdateOpt) {
						
						case"cnam":
							System.out.println("-- Please enter the new engineer's name here:");
							englist.get(i).setName(strIn.nextLine()); 
							
							//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								updateStringOrDate(connection,tblEngineers,"name",englist.get(i).getName(),englist.get(i).getProjNum());
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
								 System.exit(0);
							}
							
							//Ask user if they want to update another field
							do {
									
									System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
									updateField=strIn.nextLine();
									if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
									{
										correctInput=false;
										System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
									}
									else {
										correctInput=true;
									}
										
							}while(!correctInput);
							
							break conswitch;
						case"ctel":
							System.out.println("-- Please enter the new telephone number of the engineer here:");
							englist.get(i).setTelNum(strIn.nextLine()); 
							
							//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								updateStringOrDate(connection,tblEngineers,"tel_num",englist.get(i).getTelNum(),englist.get(i).getProjNum());
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
								 System.exit(0);
							}
							
							//Ask user if they want to update another field
							do {
									
									System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
									updateField=strIn.nextLine();
									if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
									{
										correctInput=false;
										System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
									}
									else {
										correctInput=true;
									}
										
							}while(!correctInput);
							break conswitch;
						case"cema":
							System.out.println("-- Please enter the new email adress of the engineer here:");
							englist.get(i).setEmail(strIn.nextLine()); 
							
							//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								updateStringOrDate(connection,tblEngineers,"email",englist.get(i).getEmail(),englist.get(i).getProjNum());
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
								 System.exit(0);
							}
							//Ask user if they want to update another field
							do {
									
									System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
									updateField=strIn.nextLine();
									if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
									{
										correctInput=false;
										System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
									}
									else {
										correctInput=true;
									}
										
							}while(!correctInput);
							break conswitch;
						case"cphy":
							System.out.println("-- Please enter the new physical adress of the engineer here:");
							englist.get(i).setPhysAdrr(strIn.nextLine()); 
							
							//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								updateStringOrDate(connection,tblEngineers,"phys_addr",englist.get(i).getPhysAdrr(),englist.get(i).getProjNum());
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
								 System.exit(0);
							}
							//Ask user if they want to update another field
							do {
									
									System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
									updateField=strIn.nextLine();
									if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
									{
										correctInput=false;
										System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
									}
									else {
										correctInput=true;
									}
										
							}while(!correctInput);
							break conswitch;
						default:
							updateField="N";
							break conswitch;
						}
				}while(updateField.equalsIgnoreCase("Y"));
				
				break innerswitch;
			}
			
			case "ucl"://update client details
			{
				String updateField;
				updateField="Y";
				String strUpdateOpt;
				System.out.println("\n>>> You have chosen to edit the client's detials of Project Name: " +projectlist.get(i).getProjName());
				
				//call method that clear text file and update

				//new menu 
				do {
					//show old architect details
					System.out.println("\n========= Client Details");
					System.out.println("-- Client Name:                        \t"+custlist.get(i).getName());
					System.out.println("-- Client Telephone Number:            \t"+custlist.get(i).getTelNum());
					System.out.println("-- Client Email Adress:                \t"+custlist.get(i).getEmail());
					System.out.println("-- Client Physical Adress:             \t"+custlist.get(i).getPhysAdrr());
					System.out.println("...");
					System.out.println("--------------------------------------------------------------------");
					
					System.out.println("\n>>> Please select the options below to continue:\n--Enter clnam  - to update client's name\n--Enter cltel - to update the client's telephone number\n--Enter clema - to update client's email adress\n--Enter clphy - to update client's physical adress\n--Enter ex - to go back to the previous menu");
					System.out.print("\n-- Enter your option here:");
					strUpdateOpt=strIn.nextLine();
					boolean correctInput=false;
						custswitch:switch(strUpdateOpt) {
						
						case"clnam":
							System.out.println("-- Please enter the new client's name here:");
							String stkName=strIn.nextLine();
							custlist.get(i).setName(stkName); 
							
							//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								updateStringOrDate(connection,tblClients,"name",custlist.get(i).getName(),custlist.get(i).getProjNum());
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
								 System.exit(0);
							}
							
							//Ask user if they want to update another field
							do {
									
									System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
									updateField=strIn.nextLine();
									if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
									{
										correctInput=false;
										System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
									}
									else {
										correctInput=true;
									}
										
							}while(!correctInput);
							break custswitch;
						case"cltel":
							System.out.println("-- Please enter the new telephone number of the client here:");
							custlist.get(i).setTelNum(strIn.nextLine()); 
							
							//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								updateStringOrDate(connection,tblClients,"tel_num",custlist.get(i).getTelNum(),custlist.get(i).getProjNum());
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
								 System.exit(0);
							}
							
							//Ask user if they want to update another field
							do {
									
									System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
									updateField=strIn.nextLine();
									if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
									{
										correctInput=false;
										System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
									}
									else {
										correctInput=true;
									}
										
							}while(!correctInput);
							break custswitch;
						case"clema":
							System.out.println("-- Please enter the new email adress of the client here:");
							custlist.get(i).setEmail(strIn.nextLine()); 
							
							//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								updateStringOrDate(connection,tblClients,"email",custlist.get(i).getEmail(),custlist.get(i).getProjNum());
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
								 System.exit(0);
							}
							
							//Ask user if they want to update another field
							do {
									
									System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
									updateField=strIn.nextLine();
									if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
									{
										correctInput=false;
										System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
									}
									else {
										correctInput=true;
									}
										
							}while(!correctInput);
							break custswitch;
						case"clphy":
							System.out.println("-- Please enter the new physical adress of the client here:");
							custlist.get(i).setEmail(strIn.nextLine()); 
							
							//call method to append objects to tables 
							
							connection=null;
							try {
							 	connection=getConnectionToDB();
								updateStringOrDate(connection,tblClients,"phys_addr",custlist.get(i).getPhysAdrr(),custlist.get(i).getProjNum());
								
							}catch(Exception e) {
								 System.out.println("Error occured while trying to connected to database :(");
								 e.printStackTrace();
								 System.exit(0);
							}
							
							//Ask user if they want to update another field
							do {
									
									System.out.print("\n-- Updated Field Succesfully, Do you want to update another field ? (Y/N) :");
									updateField=strIn.nextLine();
									if(!(updateField.equalsIgnoreCase("Y") || updateField.equalsIgnoreCase("N")))
									{
										correctInput=false;
										System.out.println("\n-- Opps, You have enetered an invalid input :( \n-- Input must Y = yes or N = no");
									}
									else {
										correctInput=true;
									}
										
							}while(!correctInput);
							break custswitch;
						default:
							updateField="N";
							break custswitch;
						}
				}while(updateField.equalsIgnoreCase("Y"));
				
				break innerswitch;
			}
			default:
			{
				System.out.println("\n-- Option you've selected is incorrect,Please try again\n");
				break innerswitch;
			}
		}
	}


}

