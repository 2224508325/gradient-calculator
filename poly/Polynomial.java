package poly;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.TreeSet;

import util.Vector;

/** Implements a polynomial as a sum of terms.  If 5x^2 + 3xy is a polynomial,
 *  it has two Terms 5x^2 and 2xy, each stored in the member list _terms.
 * 
 * @author ssanner@mie.utoronto.ca
 *
 */
public class Polynomial {

	public ArrayList<Term> _terms; // The Polynomial is the sum of these Terms

	/** This constructor has been implemented for you.  It simply initializes an
	 *  empty term list.
	 * 
	 */
	public Polynomial() {
		_terms = new ArrayList<Term>();
	}
	
	/** This constructor has been implemented for you -- it parses a term 
	 *  representation from a String into the format required by this class.
	 * 
	 * @param s -- String to parse
	 * @throws PolyException if s is malformed
	 */
	public Polynomial(String s) throws PolyException {

		if (s == null || s.trim().equals(""))
			throw new PolyException("Empty Polynomial, cannot read");
		_terms = new ArrayList<Term>();
		String[] terms = s.split("\\+");
		for (String term : terms)
			_terms.add(new Term(term));
	}
	
	/** Produce a re-parseable representation of this Polynomial as a String.  This
	 *  has been done for you.
	 * 
	 */
	public String toString() {
		// Using "+" to append Strings involves a lot of String copies since Strings are 
		// immutable.  StringBuilder is much more efficient for append.
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Term term : _terms) {
			sb.append((first ? "" : " + ") + term);
			first = false;
		}
		return sb.toString();
	}
	
	///////////////////////////////////////////////////////////////////////////////
	// TODO: Your methods here!  You should add some helper methods that facilitate
	//       the implementation of the methods below.
	///////////////////////////////////////////////////////////////////////////////

	/** This method takes a file (e.g., new File("files/poly1.txt")) which on its
	 *  first line should contain a syntactically correct Polynomial as parsed by 
	 *  new Polynomial(String s) above and return that Polynomial.
	 *  
	 *  You need to implement this method... it requires file I/O!
	 * 
	 * @param file
	 * @return
	 * @throws PolyException if there were any errors reading or parsing the file
	 * @throws IOException 
	 */
	public static Polynomial ReadPolynomial(File file) throws PolyException, IOException {
		boolean exist = file.exists();
		if (! exist) 
			throw new PolyException("file does not exist");
		BufferedReader fil = new BufferedReader(new FileReader(file));
		String st = fil.readLine();
		fil.close();
		Polynomial poly = new Polynomial(st);
		// TODO: Should not return null!
		
		return poly;
	}
	
	/** Returns all of the variables used in this Polynomial as a sorted set (TreeSet).
	 * 
	 * @return (TreeSet of Strings as defined above)
	 */
	public TreeSet<String> getAllVars() {

		// TODO: Should not return null!
		TreeSet<String> tree = new TreeSet<String>();
		for (int i = 0; i < _terms.size();i++) {
			for(int j = 0; j < _terms.get(i)._vars.size();j++)
				tree.add(_terms.get(i)._vars.get(j).toString());
		}
		return tree;
	}
	
	/** If Polynomial defines f(x,y) = 2xy^2 + xy and assignments is { x=2.0 y=3.0 } 
	 *  then this method returns 42.0, which is the evaluation of f(2.0,3.0).  
	 *  Incidentally, this is also the "Answer to the Ultimate Question of Life, the 
	 *  Universe, and Everything" in case you were wondering.
	 * 
	 * @param assignments
	 * @return
	 * @throws Exception 
	 * (can throw either a VectorException or a PolyException -- Exception is a superclass)
	 */
	public double evaluate(Vector assignments) throws Exception {

		// TODO: Should not return 0!
		//ArrayList<Integer> boo = new ArrayList<Integer>(); 
		//for( int k = 0; k < _terms.size(); k++) {
			//for(int j = 0; j < _terms.get(i)._vars.size();j++) {
		//		if(_terms.get(k)._vars.contains(assignments._hmVar2Value.keySet())) {
		//			boo.add(1);
		//		}
		//		else {
		//			boo.add(0);
		//		}
		//	}
		//if(!boo.contains(1))
		//	throw new Exception("the dimention is not the same");
		//}
		if (assignments._hmVar2Value.size() == 0) {
			throw new Exception("the vector is 0");
		}
		for(String i:assignments._hmVar2Value.keySet())
			if(assignments._hmVar2Value.get(i).equals(0))
				throw new Exception("The value for the variable is 0");
		double result = 0;
		for(int i = 0; i < _terms.size();i++) {
			result += _terms.get(i).evaluate(assignments);
		}
		return result;
	}

	/** If Polynomial defines a function f(.) then this method returns the **symbolic**
	 *  partial derivative (which you can verify from calculus is still a Polynomial):
	 *  
	 *    partial f(1.0,2.0) / partial var.
	 * 
	 *  Specifically, if Polynomial defines f(x,y) = 2xy^2 + xy and var = "x"
	 *  then this method returns a **new** Polynomial 2y^2 + y and if var = "y" 
	 *  then it instead returns a **new** Polynomial 4xy + x.
	 * 
	 * @param var
	 * @return partial derivative of this w.r.t. var as a new Term
	 */
	public Polynomial differentiate(String var) {

		// TODO: Should not return null!
		
		Polynomial p = new Polynomial();
		//Polynomial p2 = new Polynomial();
		
		for(int i = 0; i < _terms.size();i++) {
			if(_terms.get(i)._vars.contains(var))
			p._terms.add(_terms.get(i).differentiate(var));
			//else {
			//	continue;
			//}
			//if(p._terms.get(i)._vars.size() == 0)
			//	p._terms.remove(i);
		}
		return p;
	}

	/** Some examples testing the Polynomial and Term classes with expected output.
	 *  The functionality below will be tested standalone for grading.
	 *  
	 *  When initially developing the code, comment out lines below that you have
	 *  not implemented yet.  This will allow your code to compile for incremental
	 *  testing.
	 * 
	 * @param args
	 * @throws Exception if any errors occur (when implemented correctly, there should
	 *         be no Exceptions/errors below)
	 */
	public static void main(String[] args) throws Exception {
		Polynomial p3  = new Polynomial("x^2 + 2*x*y^2+ 3");
		Vector x1 = new Vector();
		x1.set("x", 1.0);
		x1.set("y", 3.0);
		x1.set("z", 3.0);
		System.out.println("p(x0)     = " + p3.evaluate(x1));
		//Polynomial p4  = new Polynomial(ReadPolynomial("poly1.txt"));
		
		
		Polynomial p  = new Polynomial("x^2 + y^2 + -4*x*y + 8");
		Polynomial p2 = new Polynomial(p.toString()); // See if we can reparse p.toString()
		Polynomial dp_dx = p.differentiate("x");
		Polynomial dp_dy = p.differentiate("y");

		// Build a point vector (HashMap) of numerical assignments for variables
		Vector x0 = new Vector();
		x0.set("x", 1.0);
		x0.set("y", 2.0);
		//x0.set("z", 2.0);
		
		// Test polynomial functionality
		System.out.println("Polynomial: " + p);     // Should print "1.000*x^2 + 1.000*y^2 + -4.000*x*y + 8.000"
		System.out.println("Re-parsed:  " + p2);    // Should print "1.000*x^2 + 1.000*y^2 + -4.000*x*y + 8.000"
		System.out.println("dp/dx:      " + dp_dx); // Should print "2.000*x + -4.000*y"
		System.out.println("dp/dy:      " + dp_dy); // Should print "2.000*y + -4.000*x"
		System.out.println("Free vars:  " + p.getAllVars()); // Should print "[x, y]"
		System.out.println("p(x0)     = " + p.evaluate(x0));     // Should print "5.0"
		System.out.println("dp/dx(x0) = " + dp_dx.evaluate(x0)); // Should print "-6.0"
		System.out.println("dp/dy(x0) = " + dp_dy.evaluate(x0)); // Should print "0.0"
	}
}