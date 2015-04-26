package common;

import model.Vector;

/** Alpha-vector class.
This class represents an alpha-vector based on a custom vector and an action. 
@author Mauricio Araya
*/
public class AlphaVector implements Comparable<AlphaVector> {
	
	protected Vector v;
	protected int a;
	

	/** Constructor using an existing vector. 
		@param v the reference of the vector to use
		@param a the action asociated to the vector v
	*/
	public AlphaVector(Vector v,int a){
		this.v=v;
		this.a=a;
	}
	

	/** Constructor by vector dimension. Creates a zero-vector asociated with the action -1
		@param dim the size of the zero-vector to create
	*/
	public AlphaVector(int dim) {
		this(new Vector(dim),-1);
	}


	/** Constructor by vector dimension and action. 
		@param dim the size of the zero-vector to create
		@param a the action asociated to the vector v
	*/
	public AlphaVector(int dim, int a) {
		this(new Vector(dim),a);
	}

	
	/** Evaluates a belief-point for this alpha. 
		@param bel the belief-state point
		@return the dot product between both vectors. 
	*/
	public double eval(BeliefState bel) {
		return(v.dotProduct(bel.getPoint()));
	}

	
	/** Get the asociated action
		@return the asociated action  
	*/
	public int getAction() {
		return a;
	}


	/** Create a proper copy of the alpha-vector.
		@return an alpha-vector copy
	*/
	public AlphaVector copy() {
		return(new AlphaVector(v.copy(),a));
	}


	/** Create a proper copy on the vector contents 
		@return a custom vector copy
	*/
	public Vector getVectorCopy() {
		return(v.copy());
	}
	

   	/** Size of the alpha-vector
                @return size of the vector
        */
	public int size(){
		return(v.getDimension());
	}


	/** Compare to an alpha-vector with delta tolerance.
		@param vec the vector to compare to
		@param delta maximum difference between them for considering them equqls.
		@return zero if (almost) equal, positive if is higher, and negative if is lower;
	*/
	public int compareTo(AlphaVector vec, double delta) {
		return(v.compareTo(vec.v));
	}
	

	/** Get the reference of the internal custom vector. Used for optimize read-only operations.
                @return a custom vector copy
        */
	public Vector getVectorRef() {
		return(v);
	}


	/** Compare to an alpha-vector with delta tolerance.
		@param vec the vector to compare to
		@return zero if equal, positive if is higher, and negative if is lower.
	*/
	public int compareTo(AlphaVector vec) {
		return (v.compareTo(vec.v));
	}


	/** Reset the vector reference to a new one.
  		@param v the new custom vector */
	public void setValues(Vector v) {
		this.v=v;
	}


	/** Action setter.
		@param a a valid action
	*/
	public void setAction(int a){
		this.a=a;
	}

	
	/** New values for the alpha vector.
		@param res the alpha vector to copy from
	*/
	public void set(AlphaVector res) {
		setValues(res.v.copy());
		setAction(res.a);
	}

	
	/** Change one value of the alpha-vector.
		@param idx the vector index
		@param value the nez value
	*/
	public void setValue(int idx, double value) {
		v.setEntry(idx, value);
	}


	/** Add the values of other alpha-vector. This does not modify the action value.
		@param alpha the alpha-vector to add
	*/
	public void add(AlphaVector alpha) {
		add(alpha.v);	
	}


	/** Add the values of a custom vector. 
		@param vec the custom vector to sum
	*/
	public void add(Vector vec) {
		v = new Vector(v.add(vec));
	}
}
