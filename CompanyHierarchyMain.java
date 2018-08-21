///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Program 2
// Files:            CompanyHierarchyMain.java,CompanyHierarchy.java, 
//                   TreeNode.java, Employee.java, CompanyHierarchyException.java
//					 
// Semester:         (course) Summer 2017
//
// Author:           Utkarsh Maheshwari 
// Email:            umaheshwari@wisc.edu
// CS Login:         maheshwari
// Lecturer's Name:  Meenakshi Syamkumar
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//                   CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If pair programming is allowed:
//                   1. Read PAIR-PROGRAMMING policy (in cs302 policy) 
//                   2. choose a partner wisely
//                   3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER 
//                      a. one partner creates the team
//                      b. the other partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     Jared Akers
// Email:            jakers@wisc.edu
// CS Login:         akers
// Lecturer's Name:  Meenakshi Syamkumar
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   must fully acknowledge and credit those sources of help.
//                   Instructors and TAs do not have to be credited here,
//                   but tutors, roommates, relatives, strangers, etc do.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////

/** The application program, CompanyHierarchyMain, creates and uses a 
 *  CompanyHierarchy to represent and process information about company employees.
 *  The employee information is read from a text file and then the program 
 *  processes user commands.
 *  The two methods in this class first add the Company Hierarchy to a tree, 
 *  with the root being the CEO of the tree. The user is then prompted to enter
 *  commands to perform operations on, or display information about the company.  
 */

import java.util.*;
import java.io.*;

public class CompanyHierarchyMain {

	/** This method check whether exactly one command-line argument is given;
	 * if not it displays "Usage: java -cp . CompanyHierarchyMain FileName" & quits.
	 * Then it checks whether the input file exists and is readable;
	 * if not, it displays "Error: Cannot access input file" and quits.
	 * Lastly it loads the data from the input file and uses it to construct a 
	 * company hierarchy tree.
	 * 
	 * @param args: contains the command-line arguments specified when program runs.
	 */
	private static <E> CompanyHierarchy<E> checkInputAndReturnTree (String [] args) {

		// ***  Check whether exactly one command-line argument is given *** 
		if (args.length > 1 || args.length == 0 ) {

			System.out.println("Usage: java -cp . CompanyHierarchyMain FileName");
			System.exit(0);
		}

		/** tree: Stores reference to company tree. */ 
		CompanyHierarchy<E> tree = new CompanyHierarchy<E>();

		try{
			// *** Step 2: Check whether the input file exists and is readable ***
			File inputFile = new File(args[0]);
			Scanner input = new Scanner (inputFile);

			/* Step 3: Load the data from the input file and use it to 
			 *  construct a company tree. Note: people are to be added to the 
			 *  company tree in the order in which they appear in the text file. 
			 */

			/**In the block of code below, we split the first line and
			 * the content between two commas is stored in its
			 * own separate array element. We know the format in which 
			 * the CEO's information is written, thus enabling us to 
			 * know exactly in which element each attribute lies, thus
			 * enabling easy retrieval. 
			 */
			String[] CEOdata = input.nextLine().split(",");

			/** Storing details in individual variables. */ 
			String CEOname = CEOdata[0].trim();
			int CEOid = Integer.parseInt(CEOdata[1].trim());
			String CEOjoinDate = CEOdata[2].trim();
			String CEOtitle = CEOdata[3].trim();

			/* Adding CEO to the tree*/
			Employee CEO = new Employee(CEOname,CEOid,CEOjoinDate,CEOtitle);
			tree.addEmployee(CEO, 0, null);

			/* Loading the rest of the employees into tree. */
			while(input.hasNextLine()){
				String[] data = input.nextLine().split(",");

				String name = data[0].trim();
				int id = Integer.parseInt(data[1].trim());
				String joinDate = data[2].trim();
				String title = data[3].trim();

				String supName = data[4].trim();
				int supId = Integer.parseInt(data[5].trim());

				Employee employee = new Employee(name,id,joinDate,title);

				tree.addEmployee(employee, supId, supName );
			}

		} catch(IOException excpt){ // Handling File exceptions
			System.out.println("Error: Cannot access input file");
		}

		return tree;
	}

	/** This method prompts the user to enter command options to add,
	 * remove and replace employees, and to display information according to
	 * criteria, and processes these commands until the user types x for exit.
	 * 
	 * @param args: contains the command-line arguments specified when program runs.
	 */
	public static void main(String[] args) {

		CompanyHierarchy<TreeNode> tree = checkInputAndReturnTree(args);

		/* Step 4: Prompt the user to enter command options and 
		 *  process them until the user types x for exit. 
		 */
		boolean stop = false;

		Scanner stdin = new Scanner(System.in);
		while (!stop) {

			try{
				System.out.println("\nEnter Command: ");
				String input = stdin.nextLine();
				String remainder = null;

				/*if the user enters more than one letter
				 * the extra command is set as remainder.
				 */
				if (input.length() > 0) {
					char option = input.charAt(0);
					if (input.length() > 1) {
						remainder = input.substring(1).trim();
					}

					//switch statement for the letter/command entered from the user
					switch (option) {

					/** a newid,newname,DOJ,title,supervisorId,supervisorName		
					 * Add a new employee with given details to the company tree. 
					 * Display "Employee added" if the addition was successful. 
					 * If there is no such supervisor in the company tree, 
					 * display "Cannot add employee as supervisor was not found!"
					 */

					case 'a': {
						/**Splitting and storing remainder's content between 
						 * commas in separate array elements.
						 */
						String[] data = remainder.split(",");

						// Storing new employee's details.
						int id = Integer.parseInt(data[0].trim());
						String name =   data[1].trim();
						String doj = data[2].trim();
						String title = data[3].trim();

						// Storing supervisor's details.
						int supId = Integer.parseInt(data[4].trim());
						String supName = data[5].trim();

						// Handles adding an existing employee.
						if(tree.contains(id, name, "Id already used!")){
							System.out.println("Employee already exists!");
							break;
						}
						// Handles case of supervisor not found.
						if(!tree.contains(supId, supName,
								"Incorrect supervisor name for id!")){
							System.out.println
							("Cannot add employee as supervisor was not found!");
							break;
						}

						// Creating and adding employee to respective supervisor.
						Employee newEmployee = new Employee(name, id, doj, title);

						if(tree.addEmployee(newEmployee, supId, supName))
							System.out.println("Employee added");

						break;
					}

					/** s id name		Print the name(s) of all the 
					 * supervisors in the supervisor chain of the given 
					 * employee. Print the names on separate lines. 
					 * If no such employee is found, display 
					 * "Employee not found!"*/
					case 's':{

						/**Splitting and storing remainder's content between 
						 * commas in employeeId and employeeName.
						 */
						String [] employee = remainder.split(",");

						int employeeId = Integer.parseInt(employee[0].trim());
						String employeeName = employee[1].trim();

						// Gets and stores list of supervisors.
						List<Employee> supList = tree.getSupervisorChain
								(employeeId, employeeName);

						// Handles case if no such employee found.
						if(supList.isEmpty()) {
							System.out.println("Employee not found!");
							break;
						}

						Iterator<Employee> itr = supList.iterator();
						itr.next(); // Omits the "given employee" from the list.

						while(itr.hasNext()){
							System.out.println(itr.next().getName());
						}

						break;
					}

					/** d		Display information about the company tree 
					 * by doing the following:
					 * Display on a line: "# of employees in company tree: integer"
					 * This is the number of employees in this company tree.
					 *
					 * Display on a line: "max levels in company tree: integer"
					 * This is the maximum number of levels in the company tree.
					 *
					 * Display on a line: "CEO: name"
					 * This is the CEO in the company tree*/
					case 'd': {

						System.out.println("# of employees in company tree: " +
								tree.getNumEmployees());

						System.out.println("max levels in company tree: " +
								tree.getMaxLevels());

						System.out.println("CEO: " + tree.getCEO());

						break;
					}

					/** e title		Print the name(s) of the employee(s) 
					 *  that has the given title. Print the names on 
					 *  separate lines. If no such employee is found, 
					 *  display "Employee not found!" */
					case 'e': {

						// titleList: stores all employees with the given title.
						List<Employee> titleList = 
								tree.getEmployeeWithTitle(remainder);

						// Prints when there is no employee with given title.
						if(titleList == null){
							System.out.println("Employee not found!");
							break;
						}

						// Iterator to iterate and print list of employees.
						Iterator<Employee> itr = titleList.iterator();

						while(itr.hasNext())
							System.out.println(itr.next().getName());

						break;
					}

					/** r id name		Remove the employee with given id 
					 * and name from the company tree and re-assign the 
					 * worker's to the removed employee's supervisor. 
					 * Display "Employee removed" after the removal. 
					 * If there is no such employee in the company tree, 
					 * display "Employee not found!" */
					case 'r': {

						/**Splitting and storing remainder's content between 
						 * commas in employeeId and employeeName.
						 */
						String [] employee = remainder.split(",");

						int employeeId = Integer.parseInt(employee[0].trim());
						String employeeName = employee[1].trim();

						// Prints appropriate message based on success of removal.
						if(tree.removeEmployee(employeeId, employeeName))
							System.out.println("Employee removed");

						else
							System.out.println("Employee not found!");

						break;
					}

					/** c id name		Print the name(s) of the 
					 * co-employees(sharing the same supervisor) of the 
					 * employee with given id and name. Print the names on 
					 * separate lines. If no such employee is found, 
					 * display "Employee not found!". If the employee has 
					 * no co-employee under the same supervisor, display 
					 * "The employee has no co-workers." */
					case 'c': {

						/**Splitting and storing remainder's content between 
						 * commas in 'id' and 'name'.
						 */
						String[] data = remainder.split(",");
						int id = Integer.parseInt(data[0].trim());
						String name =   data[1].trim();

						// Executes if given employee is present in tree.
						if(tree.contains
								(id, name, "Incorrect employee name for id!")) {

							// Stores coworkers of given employee in a list.
							List<Employee> coWorkers = tree.getCoWorkers(id, name);

							// Printing coworkers.
							if(coWorkers.isEmpty() == false) {

								for(int i=0; i<coWorkers.size();i++)
									System.out.println(coWorkers.get(i).getName());
							}
							else // Executes if coWorkers is empty.
								System.out.println
								("The employee has no co-workers.");
						}
						else // Executes if tree.contains returns false.
							System.out.println("Employee not found!");

						break;
					}

					/** u id name newid newname DOJ title		Replace the 
					 * employee with give id and name from the company tree 
					 * with the provided employee details. 
					 * Display "Employee replaced" after the removal. If 
					 * there is no such employee in the company tree, 
					 * display "Employee not found!" */
					case 'u': {
						String [] replaceInfo = remainder.split(",");

						/**Splitting and storing remainder's content between 
						 * commas in respective variables.
						 */
						// Info of employee to be replaced.
						int employeeId = Integer.parseInt(replaceInfo[0].trim());
						String employeeName = replaceInfo[1].trim();

						// Info of new employee.
						int newEmplId = Integer.parseInt(replaceInfo[2].trim());
						String newEmplName = replaceInfo[3].trim();
						String newEmplDOJ = replaceInfo[4].trim(); 
						String newEmplTitle =replaceInfo[5].trim();

						Employee newEmployee = new Employee(newEmplName, newEmplId,
								newEmplDOJ, newEmplTitle);

						// Following code replaces the employee.
						if(tree.replaceEmployee
								(employeeId, employeeName, newEmployee))
							System.out.println("Employee replaced");

						else
							System.out.println("Employee not found!");

						break;
					}

					/** j startDate endDate		Print the name(s) of the 
					 * employee(s) whose date of joining are between 
					 * startDate and endDate(you may assume that startDate 
					 * is equal to or before end date). Print the names on 
					 * separate lines. If no such employee is found, 
					 * display "Employee not found!" */
					case 'j': {

						/**Splitting and storing remainder's content between 
						 * commas in startDate and endDate.
						 */
						String[] data = remainder.split(",");
						String startDate = data[0].trim();
						String endDate = data[1].trim();

						// Gets and stores employees who joined in given range. 
						List<Employee> employees = tree.
								getEmployeeInJoiningDateRange(startDate, endDate);

						// Prints employees, if any.
						if(employees.size()>0)
							for(int i=0; i<employees.size();i++)
								System.out.println(employees.get(i).getName());

						else
							System.out.println("Employee not found!");

						break;
					}

					//***exits program***
					case 'x':{
						stop = true;
						System.out.println("exit");
						break;
					}
					default:
						break;
					}
				}
			}	catch(CompanyHierarchyException excpt){
				System.out.println(excpt.getMessage());}
		}
	}
}
