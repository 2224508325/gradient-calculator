package util;

import java.util.HashMap;
import java.util.Iterator;

//import linalg.Matrix;

/** Implements a vector with *named* indices.  For example { x=1.0 y=2.0 } is a 2D
 *  vector with the first dimension named "x" and the second dimension named "y"
 *  and having respective values 1.0 and 2.0 in these dimensions.
 *  
 *  TODO: Implement all methods required to support the functionality of the project
 *        and that described in Vector.main(...) below.
 * 
 * @author ssanner@mie.utoronto.ca
 *
 */
public class Vector {

	public HashMap<String,Double> _hmVar2Value; // This maps dimension variable names to values
	
	/** Constructor of an initially empty Vector
	 * 
	 */
	public Vector() {
		// TODO: this method should not be empty! 
		// Hint: is there any memory you want to allocate?
		_hmVar2Value = new HashMap<String,Double>();
	}

	/** Constructor that parses a String s like "{ x=-1 y=-2.0 z=3d }" into 
	 *  the internal HashMap representation of the Vector.  See usage in main().
	 * 
	 * @param s
	 */
	public Vector(String s) {
		// TODO: this method should not be empty! 
		// Hint: you're going to have use String.split used in Project2.
		
		//s.trim();
		_hmVar2Value = new HashMap<String,Double>();
		String s2 = s.substring(1,s.length()-1);
		s2 = s2.trim();
		String[] split = s2.split("\\s");
		
		for (int i = 0; i < split.length; i ++) {
			String[] split2;
			double value;
			split2 = split[i].split("=");
			value = Double.parseDouble(split2[1]);
			_hmVar2Value.put(split2[0], value);
			
		}
		// Make vector: vec3[x y z] = vec4[x y z] = [-1 -2 -3]
		//Vector vec3 = new Vector("{ x=-1 y=-2.0 z=3d }");
		
	} 

	public String toString() {
		StringBuilder sb = new StringBuilder("{ ");
		Iterator var3 = this._hmVar2Value.keySet().iterator();

		while(var3.hasNext()) {
			String key = (String)var3.next();
			sb.append(String.format("%s=%6.4f ", key, this._hmVar2Value.get(key)));
		}

		sb.append("}");
		return sb.toString();
	}


	/** Removes (clears) all (key,value) pairs from the Vector representation
	 * 
	 */
	public void clear() {
		// TODO: this method should not be empty! 
		// Hint: look very carefully at the available methods of HashMap... this is a one liner!
		_hmVar2Value.clear();
	}

	/** Sets a specific var to the value val in *this*, i.e., var=val
	 * 
	 * @param var - label of Vector index to change
	 * @param val - value to change it to
	 */
	public void set(String var, double val) {
		// TODO: this method should not be empty! 
		// Hint: look very carefully at the available methods of HashMap... this is a one liner!
		//vec1.set("x", 1.0);
		_hmVar2Value.put(var,val);
	}

	/** Sets all entries in *this* Vector to match entries in x
	 *  (if additional variables are in *this*, they remain unchanged) 
	 * 
	 * @param x
	 */
	public void setAll(Vector x) {
		// TODO: this method should not be empty! 
		// Hint: look very carefully at the available methods of HashMap... this is a one liner!
		this._hmVar2Value = x._hmVar2Value;
	}

	/**
	 * Compute the norm value of the vectors
	 * @return double value represents the norm of the vector
	 */
	public double computeL2Norm() {
		// TODO: this method should not be empty!
		//return 0.0;
		double L2Norm = 0;
		for(String i : _hmVar2Value.keySet()) {
			L2Norm += _hmVar2Value.get(i)*_hmVar2Value.get(i);
		}
		L2Norm = Math.sqrt(L2Norm);
		return L2Norm;
	}

	/**
	 * perform vector sum and return a new vector as results
	 * @param y
	 * @return Vector
	 * @throws VectorException
	 */
	public Vector sum(Vector y) throws VectorException {
		// TODO: this method should not be empty!
		if (!this._hmVar2Value.keySet().equals(y._hmVar2Value.keySet())) throw new VectorException("dim is not the same");
			
		Vector v = new Vector();
		for (String i : _hmVar2Value.keySet()) {
			v._hmVar2Value.put(i,this._hmVar2Value.get(i)+y._hmVar2Value.get(i)); 
		}
		return v;
	}

	/**
	 * perform vector scalar multiplication and return a new vector as results
	 * @param scalar
	 * @return Vector
	 */
	public Vector scalarMult(double scalar) {
		// TODO: this method should not be empty!
		Vector v = new Vector();
		for(String i : this._hmVar2Value.keySet()) {
			v._hmVar2Value.put(i,this._hmVar2Value.get(i)*scalar);
		}
		return v;
	}

	///////////////////////////////////////////////////////////////////////////////
	// TODO: Add your methods here!  You'll need more than those above to make
	//       main() work below.
	///////////////////////////////////////////////////////////////////////////////
	public boolean equals(Object o) {
		
		if (o instanceof Vector) {
			Vector m = (Vector)o;
			if(_hmVar2Value.size() == m._hmVar2Value.size()) {
				
				for (String i : m._hmVar2Value.keySet()) {
					//if(_hmVar2Value.get(i) != (m._hmVar2Value.get(i))) {
					if(!this._hmVar2Value.get(i).equals(m._hmVar2Value.get(i))) {
						return false;
					}		
				}
			}
		}return true;	
	}
	
	/** Your Vector class should implement the core functionality below and produce
	 *  **all** of the expected outputs below.  **These will be tested for grading.**
	 * 
	 *  When initially developing the code, comment out lines below that you have
	 *  not implemented yet.  This will allow your code to compile for incremental
	 *  testing.
	 *  
	 * @param args (unused -- ignore)
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// Make vector: vec1[x y z] = [1 2 3]
		Vector vec1 = new Vector();
		vec1.set("x", 1.0);
		vec1.set("y", 2.0);
		vec1.set("z", 3.0);
		
		// Make vector: vec2[x y z] = [-3 -2 -1]
		Vector vec2 = new Vector();
		vec2.set("x", -3.0);
		vec2.set("y", -2.0);
		vec2.set("z", -1.0);
		
		// Make vector: vec3[x y z] = vec4[x y z] = [-1 -2 -3]
		Vector vec3 = new Vector("{ x=-1 y=-2.0 z=3d }");
		Vector vec4 = new Vector(vec3.toString());
		
		// Hint: all numbers below are formatted with String.format("%s=%6.4f ", var, val)
		//       ... you may want to use this in your Vector.toString() implementation!
		
		// Test cases: 
		System.out.println(vec1); // Should print: { x=1.0000 y=2.0000 z=3.0000 }
		System.out.println(vec2); // Should print: { x=-3.0000 y=-2.0000 z=-1.0000 }
		System.out.println(vec3); // Should print: { x=-1.0000 y=-2.0000 z=3.0000 }
		System.out.println(vec4); // Should print: { x=-1.0000 y=-2.0000 z=3.0000 }
		System.out.println(vec1.sum(vec1));        // Should print: { x=2.0000 y=4.0000 z=6.0000 }
		System.out.println(vec1.sum(vec2));        // Should print: { x=-2.0000 y=0.0000 z=2.0000 }
		System.out.println(vec1.sum(vec3));        // Should print: { x=0.0000 y=0.0000 z=6.0000 }
		System.out.println(vec1.scalarMult(0.5));  // Should print: { x=0.5000 y=1.0000 z=1.5000 }
		System.out.println(vec2.scalarMult(-1.0)); // Should print: { x=3.0000 y=2.0000 z=1.0000 }
		System.out.println(vec1.sum(vec2.scalarMult(-1.0))); // Should print: { x=4.0000 y=4.0000 z=4.0000 }
		System.out.format("%01.3f\n", vec1.computeL2Norm());           // Should print: 3.742
		System.out.format("%01.3f\n", vec2.sum(vec3).computeL2Norm()); // Should print: 6.000
		
		// If the following don't work, did you override equals()?  See Project 2 Vector and Matrix.
		System.out.println(vec3.equals(vec1)); // Should print: false
		System.out.println(vec3.equals(vec3)); // Should print: true
		System.out.println(vec3.equals(vec4)); // Should print: true
		System.out.println(vec1.sum(vec2).equals(vec2.sum(vec1))); // Should print: true
	}	
}
