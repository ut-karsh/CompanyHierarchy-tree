///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  CompanyHierarchyMain.java
// File:             CompanyHierarchy.java
// Semester:         CS 367 Summer 2017
//
// Author:           Utkarsh Maheshwari umaheshwari@wisc.edu
// CS Login:         maheshwari
// Lecturer's Name:  Meenakshi Syamkumar
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     Jared Akers
// Email:            jakers@wisc.edu
// CS Login:         akers
// Lecturer's Name:  Meenakshi Syamkumar
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////

/** Instances of the CompanyHierarchy class form a tree data structure
 *  representing a company and is composed of instances of the TreeNode class. 
 *  Class saves an instance of the root TreeNode which holds the CEO's employee
 *  information. Methods within this class can be used to obtain data contained
 *  within a node's employee field, get information on the macro-structure
 *  of the tree, and get employee's information relative to other members of 
 *  the tree.  
 */

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CompanyHierarchy <E>{
	private TreeNode root;
	//Used for recursive search of node
	private TreeNode nodeForSearching;
	//Used for recursive search of lists of nodes
	private List<Employee> nodeList;
	// Updated if a specific employee exist with the tree
	private boolean emplExist;
	// Updated if a specific employee was replaced within the tree
	private boolean emplReplaced;
	// Updated if a specific was successfully placed into the tree
	private boolean emplAdded;

	/** Constructs a CompanyHierarchy tree  */
	public CompanyHierarchy(){
		root = null;
		nodeForSearching = null;
		nodeList = new ArrayList<Employee>();
		emplExist = false;
		emplReplaced = false;
		emplAdded = false;
	}

	/** Get the name of the CEO in this company tree 
	 * @return Name of CEO.
	 */	
	public String getCEO(){
		return root.getEmployee().getName();
	}

	/** Return the number of employees in this company tree.
	 * @return Number of employees in company.
	 */
	public int getNumEmployees() {
		if (root == null) // No employees in company.
			return 0;
		else{
			return getNumEmployees(root);
		}
	}

	/**
	 * Private helper method for getNumEmployees(), also overloads 
	 * "getNumEmployees". Method uses recursion to find the total number of 
	 * nodes within the tree.
	 * 
	 * @param n Instance of TreeNode class.
	 * @return totEmployees returns number of employees in company.
	 */
	private int getNumEmployees(TreeNode n){
		// Base case: if node is not a supervisor add 1.
		if (n.getWorkers() == null)
			return 1;  

		// Reset number of employees under supervisor node before next call.
		int totEmployees = 0; 

		Iterator itr = n.getWorkers().iterator();

		// Call getNumEmployees(node) on each child of TreeNode 'n'.
		while(itr.hasNext()){
			totEmployees = totEmployees + 
					getNumEmployees((TreeNode) itr.next());
		}

		/* 1 counts the current node (n) and totEmployees is the sum
		 * of all the workers underneath it. */
		return 1 + totEmployees; 
	}

	/** Return the number of levels in the tree : 0+ values
	 * @return Returns maximum number of levels in tree.
	 */
	public int getMaxLevels() {
		return getMaxLevels(root);
	}

	/**
	 * Private helper method for getMaxlevls(), also overloads 
	 * "getMaxLevels". Method uses recursion to find the total height of 
	 * the tree.
	 * 
	 * @param n Instance of TreeNode class
	 * @return maxHeight Height of tree.
	 */
	private int getMaxLevels(TreeNode n){
		int maxHeight = 0;

		// Returns 0 if root is null.
		if (n == null){
			return 0;
		}

		// Base case: If n is a leaf return 1.
		if(n.getWorkers().size() == 0){
			return 1;
		}

		// Iterator for TreeNode's worker list. 
		Iterator<TreeNode> itr = n.getWorkers().iterator();

		/* Iterates through worker list of node 'n' and calls the 
		getMaxLevelMethod on each child. */
		while(itr.hasNext()){
			// Recursive call with worker node as a parameter. 
			int childHeight = getMaxLevels(itr.next());
			
			// Make child node of 'n' with greatest height the max height
			if(childHeight > maxHeight)
				maxHeight = childHeight;
		}
		
		/* 1 counts the current node (n) and maxHeight is the sum
		 * of all the workers underneath it. */
		return 1 + maxHeight;
	}

	/** Return the employee details of given employee id and name; return null 
	 * if no such employee was found.
	 * 
	 * @param id Employee ID number to search for.
	 * @param name Employee name to search for.
	 * @return Employee being asked for.
	 */
	public Employee getEmployee(int id, String name) {
		
		// Handling null and illegal arguments.
		if (id<0 || name == null)
			throw new IllegalArgumentException();

		// Reset nodeForSearching before utilizing it.
		nodeForSearching = null;
		
		// Call helper method.
		getEmployeeNode(root, id, name);
		
		/* NodeForSearhing will be update after calling the helper method.
		 * If helper method does not find a matching ID than nodeForSearching
		 * will remain null.
		 */
		TreeNode employeeNode = nodeForSearching;

		return employeeNode.getEmployee();
	}
	/**
	 * Private helper method for getEmployee(), removeEmployee(), getCoWorkers(),
	 * and getSupervisorChain(), also overloads "getMaxLevels". Method uses 
	 * recursion to find the node whose employee field contains the given
	 * id and name. If a match between IDs is not found, node for searching 
	 * will not be changed.
	 * 
	 * @param n Instance of class TreeNode.
	 * @param id Employee ID number to search for.
	 * @param name Employee name to search for.
	 */
	private void getEmployeeNode(TreeNode n, int id, String name){
		
		// Node is found whose ID field matches the given 'id'
		if(n.getEmployee().getId() == id){
			
			/* If both the Employee ID and the Employee name match with the 'id'
			and 'name' given as parameters. */
			if (n.getEmployee().getName().equals(name)){
				nodeForSearching = n;
			}
			
			// In this case the ID's are a match and the names are not.
			else
				throw new CompanyHierarchyException  
				("Incorrect employee name for id!");
		}
		
		// Iterator for TreeNode's worker list. 
		Iterator<TreeNode> itr = n.getWorkers().iterator();
		
		/* Iterate through worker list and call getEmployeeNode(TreeNode)
		on all children of 'n'. */
		while (itr.hasNext()){
			getEmployeeNode((TreeNode) itr.next(), id, name);
		}
	}

	/** Adds employee as a child to the given supervisor node if supervisor 
	 * exists on tree; adds employee as root node if root node is null.
	 * 
	 * @param employee Employee that is trying to be added to tree.
	 * @param supervisorId ID number of the new employee's supervisor.  
	 * @param supervisorName Name of the new employee's supervisor.
	 * @return true if employee added successfully, false otherwise.
	 */
	public boolean addEmployee(Employee employee, int supervisorId, 
			String supervisorName) {
		
		// If the root does not exist then make the employee to be added the root  
		if(root == null){
			root = new TreeNode(employee, null);
			return true;
		}
		
		// Handling null and illegal arguments.
		if (employee == null || supervisorId<0 || supervisorName == null)
			throw new IllegalArgumentException();
		
		// Reset employee added to false. 
		emplAdded = false;
		
		// Call to addEmployee's helper method.
		helpAddEmpl(root, employee, supervisorId, supervisorName);
		
		return emplAdded;
	}

	/**
	 * Private helper method for addEmployee().
	 * Method uses recursion to find the node whose employee field contains the 
	 * given id and name. If this node is found it will add "employee" to its 
	 * worker list and update addEmployee to true. If a match is not found 
	 * addEmployee will not be updated.
	 * 
	 * @param n Instance of class TreeNode.
	 * @param employee Employee that is trying to be added to tree.
	 * @param supervisorId ID number of the new employee's supervisor.  
	 * @param supervisorName Name of the new employee's supervisor.
	 */
	private void helpAddEmpl(TreeNode n, Employee employee, int supervisorId, 
			String supervisorName ) {
		
		/* Iterate through worker list and call helpAddEmpl(TreeNode)
		on all children of 'n'. */ 
		Iterator<TreeNode> itr = n.getWorkers().iterator();
		while (itr.hasNext()){
			helpAddEmpl((TreeNode) itr.next(), employee,
					supervisorId, supervisorName);
		}

		// Check supervisorID against given nodes Employee ID.
		if (n.getEmployee().getId() == supervisorId){
			
			// Check supervisorName against given nodes Employee name.
			if(n.getEmployee().getName().equals(supervisorName)){
				
				// Go through worker list of node 'n'.
				Iterator itrs = n.getWorkers().iterator();
				while (itrs.hasNext()){
					TreeNode worker = (TreeNode) itrs.next();
					
					/* If ID of employee to be added matches with a 
					pre-existing employee throw exception */ 
					if(employee.getId() == worker.getEmployee().getId()){	
						throw new CompanyHierarchyException  
						("Id already used!");
					}
				}
				
				// Create a new TreeNode for the new employee
				TreeNode newNode = new TreeNode(employee, n);
				// Add the new employee to it's supervisor's worker list
				n.addWorker(newNode);
				
				// Update emplAdded
				emplAdded = true;
				
				// Stop future recursive iterations.
				return;
			}

			else {
				throw new CompanyHierarchyException (
						"Incorrect supervisor name for id!");
			}
		}
	}

	/** Returns true/false based on whether the given employee exists on the 
	 * tree.
	 * 
	 * @param id Employee ID number to search for.
	 * @param name Employee name to search for.
	 * @param exceptionMessage Exception message passed in.
	 * @throws CompanyHierarchyException */
	public boolean contains(int id, String name, String exceptionMessage) 
			throws CompanyHierarchyException {

		// Handling null and illegal arguments.
		if (id<0 || name == null)
			throw new IllegalArgumentException();
		
		// Reset emplExist to false
		emplExist = false;
		
		// Call helper method, helper method will update emplExist accordingly
		contains(root, id, name, exceptionMessage);
		
		return emplExist;
	}
	
	/**
	 * Private helper method for contains().
	 * Method uses recursion to find the node whose employee field contains the 
	 * given id and name. If this node is found emplExist will be set to true. 
	 * If a match is not found emplExist will not be updated.
	 *
	 * @param n Instance of class TreeNode.
	 * @param id Employee ID number to search for.
	 * @param name Employee name to search for.
	 * @param exceptionMessage Exception message passed in.
	 * @throws CompanyHierarchyException
	 */
	public void contains(TreeNode n, int id, String name, 
			String exceptionMessage) {
		
		// If node's employee ID and 'id' match.
		if(n.getEmployee().getId() == id){
			
			// If node's employee name and 'name' match.
			if (n.getEmployee().getName().equals(name)){
				emplExist = true;
				return;
			}
			
			// Case in which IDs match but names do not.
			else
				throw new CompanyHierarchyException  // No return type, instance variable - nodeforSearching
				(exceptionMessage);
		}
		
		// Iterate through children of n and recursively call contains.
		Iterator<TreeNode> itr = n.getWorkers().iterator();
		while (itr.hasNext()){
			contains((TreeNode) itr.next(), id, name, exceptionMessage);
		}
	}

	/** Removes the given employee(if found on the tree) and updates all the 
	 *  workers to report to the given employee's supervisor; Returns true or 
	 *  false accordingly.
	 *  
	 *  @param id: ID of employee to be removed.
	 *  @param name: Name of employee to be removed.
	 *  @return True if employee is successfully removed, false otherwise.
	 *  @throws CompanyHierarchyException
	 */	
	public boolean removeEmployee(int id, String name)
			throws CompanyHierarchyException {
		
		// Handling null and illegal arguments.
		if (id<0 || name == null)
			throw new IllegalArgumentException();
		
		// Reset nodeForSearching.
		nodeForSearching = null;

		// Updates nodeForSearching to the node of Employee being removed.
		getEmployeeNode(root, id, name); 

		// Root of tree cannot be removed.
		if (nodeForSearching == root) {
			throw new CompanyHierarchyException(
					"Cannot remove CEO of the company!");
		}

		// Returns false if employee not found.
		if(nodeForSearching == null)
			return false;

		if (nodeForSearching.getWorkers() != null) {
			
			Iterator itr = nodeForSearching.getWorkers().iterator();

			/* Changing supervisor of the workers of node being removed to
			 * its supervisor.
			 */
			while(itr.hasNext()){
				TreeNode worker = (TreeNode) itr.next();
	
				nodeForSearching.getSupervisor().addWorker((TreeNode) worker);
				worker.updateSupervisor(nodeForSearching.getSupervisor());
			}
		}

		// Removing nodeForSearching from its supervisor's worker list.
		nodeForSearching.getSupervisor().getWorkers().remove(nodeForSearching);
		nodeForSearching.updateSupervisor(null);

		return true;
	}

	/** Replaces the given employee(if found on the tree) and if title of old
	 *  and new employee match; Returns true or false accordingly.
	 *  
	 *  @param id: ID of employee to be replaced.
	 *  @param name: Name of employee to be replaced.
	 *  @param newEmployee: Employee object of new employee to be added.
	 *  @return True if employee is successfully replaced, false otherwise.
	 */
	public boolean replaceEmployee(int id, String name, Employee newEmployee) {
		
		// Handling null and illegal arguments.
		if (id<0 || name == null || newEmployee == null)
			throw new IllegalArgumentException();
		
		// Checker whose value is returned depending on success of replacement. 
		emplReplaced = false;

		// Calling helper method.
		replaceEmployee(id, name, newEmployee, root);

		return emplReplaced;
	}

	/** 
	 * Helper method of replaceEmployee(). 
	 * Goes through list using recursion until it reaches the node (n0) 
	 * containing the specified params. Will then set the supervisor of n0's 
	 * worker list to n0's supervisor. n0's previous workers will also be 
	 * added n0's supervisor's worker list. 
	 * 
	 * @param id :ID of employee to be replaced.
	 * @param name: Name of employee to be replaced.
	 * @param newEmployee: Employee object of new employee to be added.
	 * @param n: Supervisor node of employee to be replaced.
	 */
	private void replaceEmployee(int id, String name, Employee newEmployee, 
			TreeNode n){

		// Recursively iterating through entire tree.
		Iterator<TreeNode> itr = n.getWorkers().iterator();

		while (itr.hasNext()) {  
			replaceEmployee(id, name, newEmployee, itr.next());
		}

		// If new employee's ID and Name match with an employee on tree.
		if(newEmployee.getId() == n.getEmployee().getId()) {
			if (newEmployee.getName().equals(n.getEmployee().getName())) {

				throw new CompanyHierarchyException  
				("Replacing employee already exists on the Company Tree!");
			}
			else // If only new ID matches with that of an employee on tree.
				throw new CompanyHierarchyException  
				("Id already used!");
		}

		// Replacing employee if details match.
		if(n.getEmployee().getName().equals(name)) {

			if(n.getEmployee().getId() == id) {

				if(n.getEmployee().getTitle().equals(newEmployee.getTitle())) {

					n.updateEmployee(newEmployee);
					emplReplaced = true;
				}
				else // Handles case if everything but title matches.
					throw new CompanyHierarchyException  
					("Replacement title does not match existing title!");
			}
		}
	}

	/** Searches and returns the list of employees with the provided title.
	 *  If none found, returns null.
	 *  
	 *  @param title: Employee title to be checked.
	 *  @return List of employees with given title.
	 */
	public List<Employee> getEmployeeWithTitle(String title) {
		
		// Handling null and illegal arguments.
		if (title == null)
			throw new IllegalArgumentException();

		// Reset nodeList. 
		nodeList = new ArrayList<Employee>();

		// Calling helper method.
		getEmployeeWithTitle(root, title);

		// Returns null if no employee with given title is found.
		if( nodeList.isEmpty())
			return null;

		return nodeList;
	}

	/** Helper method of getEmployeeWithTitle().
	 * 
	 *  @param n: TreeNode of employee to check title.
	 *  @param title: Title to be checked.
	 */
	private void getEmployeeWithTitle(TreeNode n, String title) {

		// If employee with given title is found, adds to nodeList.
		if (n.getEmployee().getTitle().equals(title))
			nodeList.add(n.getEmployee());

		// Recursively iterating through the entire tree.
		if(n.getWorkers() != null) {

			Iterator<TreeNode> itr = n.getWorkers().iterator();

			while (itr.hasNext()){
				getEmployeeWithTitle((TreeNode) itr.next(), title);
			}
		}
	}

	/** Search and return the list of employees with date of joining within the 
	 *  provided range; if none found return null.
	 *  Goes through every node in the tree and checks the employee field 
	 *  for the title. If node's employee has the specified title then
	 *  add to the class's 'nodeList' variable.
	 *  
	 *  @param startDate: Lower bound of joining date range.
	 *  @param endDate: Upper bound of joining date range.
	 *  @return List of employees within given joining date range.
	 *  @throws ParseException
	 */
	public List<Employee> getEmployeeInJoiningDateRange(String startDate, 
			String endDate) {
		
		// Handling null and illegal arguments.
		if (startDate == null || endDate == null)
			throw new IllegalArgumentException();
		
		try {
			// dateFormat stores the format in which dates are input.
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

			// Converting 'String' dates to 'Date' dates.
			Date strtDate = dateFormat.parse(startDate);
			Date stopDate = dateFormat.parse(endDate);

			// Reset nodeList.
			nodeList = new ArrayList<Employee>();

			// Calling helped method.
			getEmployeeInJoiningDateRange( strtDate, stopDate, root);

			return nodeList;

		} catch (ParseException excpt) {
			throw new CompanyHierarchyException("Date parsing failed!");
		}
	}

	/** Helper method of getEmployeeInJoiningDateRange().
	 *  Goes through every node in the tree and checks the employee field 
	 *  for the joining date. If node's employee joined within the specified 
	 *  dates then add that employee to the class's 'nodeList' variable.
	 * 
	 *  @param startDate: Lower bound of joining date range.
	 *  @param endDate: Upper bound of joining date range.
	 *  @param n: TreeNode of employee for checking joining date.
	 *  @throws parseException
	 */
	public void getEmployeeInJoiningDateRange(Date startDate, 
			Date endDate, TreeNode n ) throws ParseException {

		// dateFormat stores the format of the dates input. 
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		// Converting date of joining from String to Date and storing in joinDate. 
		Date joinDate = dateFormat.parse(n.getEmployee().getDateOfJoining());

		// Adds employee to nodeList if date of joining is within range.
		if(joinDate.after(startDate) && joinDate.before(endDate))
			nodeList.add(n.getEmployee());

		// Recursively iterating through entire tree.
		if(n.getWorkers() != null){

			Iterator<TreeNode> itr = n.getWorkers().iterator();

			while (itr.hasNext()){
				getEmployeeInJoiningDateRange( startDate, endDate, itr.next());
			}
		}
	}

	/** Return the list of employees who are in the same level as the given 
	 *  employees share the same supervisor.
	 *  
	 *  @param id: ID of employee whose Co-Worker list is needed.
	 *  @param name: Name of employee whose Co-Worker list is needed.
	 *  @return List of Co-Workers of given employee.
	 */
	public List<Employee> getCoWorkers(int id, String name) {
		
		// Handling null and illegal arguments.
		if (id<0 || name == null)
			throw new IllegalArgumentException();
		
		nodeForSearching = null; // Reset nodeForSearching.
		nodeList = new ArrayList<Employee>();  // Reset nodeList.

		// Converting raw employee data into usable node.
		getEmployeeNode(root, id, name);

		// Since root (CEO) has no coworkers, returns empty nodeList.
		if (root.getEmployee().getName().equals(name) &&
				root.getEmployee().getId() == id) 
			return nodeList;

		// Getting employee's supervisor node to use its workers list.
		TreeNode parentNode = nodeForSearching.getSupervisor();

		// Iterator to iterate through worker list of supervisor.
		Iterator childIter = parentNode.getWorkers().iterator();
		while (childIter.hasNext()){
			TreeNode coworker = (TreeNode) childIter.next();
			if(coworker == nodeForSearching) 
				continue; // Skips employee whose coworkers are to be found.
			else
				// Adds Co-Workers to 'coworker' list.
				nodeList.add(coworker.getEmployee());
		}
		return nodeList;
	}

	/** Returns the supervisor list(till CEO) for a given employee.
	 * 
	 * @param id: ID of employee whose supervisor list is needed.
	 * @param name: Name of employee whose supervisor list is needed.
	 * @return List of supervisors of given employee.
	 * @throws CompanyHierarchyException
	 */
	public List<Employee> getSupervisorChain(int id, String name) 
			throws CompanyHierarchyException {
		
		// Handling null and illegal arguments.
		if (id<0 || name == null)
			throw new IllegalArgumentException();
		
		nodeForSearching = null; // Reset nodeForSearching
		nodeList = new ArrayList<Employee>();  // Reset nodeList

		// Handles if supervisor chain of root is asked.
		if ((root.getEmployee().getId() == id) 
				&& root.getEmployee().getName().equals(name)){
			throw new CompanyHierarchyException
			("No Supervisor Chain found for that employee!");
		}

		// Converts employee into to node to pass into helper method.
		getEmployeeNode(root, id, name);

		// Calling helper method.
		getSupervisorChain(nodeForSearching);

		return nodeList;
	}

	/** Helper method of getSupervisorChain().
	 * 
	 * @param n: TreeNode of employee whose supervisor list is needed.
	 */
	private void getSupervisorChain(TreeNode n) {

		//Returns null if employee is not found.
		if(n == null)
			return;
		// Recursively getting individual supervisors and storing in nodeList.
		nodeList.add(n.getEmployee());
		getSupervisorChain(n.getSupervisor());
	}
}
