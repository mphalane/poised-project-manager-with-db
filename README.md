# Poised Project Manager ( WITH DB - JDBC)
This a basic JAVA OOP project management system for a small structural engineering firm called “Poised”. 
Poised does the engineering needed to ensure the structural integrity of various buildings.
This program will aid them to keep track of the many projects on which they work.
## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development:
1) First follow the instruction [here](https://www.tutorialspoint.com/eclipse/eclipse_installation.htm) to install JAVA IDE
2) Second click on "[Downlaod Zip](https://www.instructables.com/Downloading-Code-From-GitHub/)" to get a full copy of this project including the resources used.
## Code Style and Example
The code style used is standard OOP.

![](https://github.com/mphalane/final-poised-project-manager/blob/master/class_diagram.jpg)

1) One main classes :
	1) Poised
2) Two interfaces:
	1) Project ID
	2) Stakeholders
3) One constructor class:
	1) Projects
4) One Factroy creational class:
  	1) StakeholderFactory   
5) Three classes of type Stakeholder:
  	1) Architect
 	2) Client
  	3) Constructor  

The example of the code is shown below:


Main Program Sample
```java
public class Poised {
		
	
//Class Scanner Vars
	private static Scanner numIn =new Scanner(System.in);
	private static Scanner strIn =new Scanner(System.in);
/**Create List of objects*/
	private static List<Project> projectlist = new ArrayList<Project>();
/**Create factory object*/
	private static StakeholderFactory stakeholderFactory = new StakeholderFactory();
/**Create stakeholder object list*/
	private static List<Stakeholders> archlist = new ArrayList<Stakeholders>();
	private static List<Stakeholders> conslist = new ArrayList<Stakeholders>();
	private static List<Stakeholders> custlist = new ArrayList<Stakeholders>();

	
/**Main Project	*/
	public static void main(String[] args) {
```

## How To Use ?

1) Open the Poised.java file in the src
2) In the java terminal you will be prompted with respective instructions.
