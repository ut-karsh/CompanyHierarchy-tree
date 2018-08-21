///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  CompanyHierarchyMain.java
// File:             CompanyHierarchyException.java
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

/** An instance of CmpanyHierarchyException class will be thrown if there is a 
 *  problem during CompanyHierarchy tree construction related to the 
 *  relationship between nodes or employees, or if the parameters passed into a 
 *  public CompanyHeirarchy method do not match the information of any node on 
 *  the tree.  
 */

public class CompanyHierarchyException extends RuntimeException {
	
	/* Message that will be displayed to user when a 
	CompanyHierarchyException is thrown. */
	String message;
	
	/** Default constructor */
	public CompanyHierarchyException(){
		message = null;
	}
	
	/**
	 * Constructor for exception class.
	 * 
	 * @param string Exception message.
	 */
	public CompanyHierarchyException(String string ){
	 message = string;
	}
	
	/**
	 * Getter method.
	 * 
	 * @return String message.
	 */
	public String getMessage(){
		return message;
	}
	
	
}


