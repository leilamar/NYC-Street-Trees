/**
 * This class defines the BSTNode, the building blocks of the binary search tree used
 * the MyBST and TreeCollection classes. The class contains a reference to the data inside
 * the node as well references to a right and left child node. There are methods in this
 * class to get and set these references.
 * 
 * @author Leila Mardoum
 * @version 4/22/17
 * @param <E> type of data to be stored in node
 */

public class BSTNode<E extends Comparable<E>> implements Comparable<BSTNode<E>> {
	
	private E data;
	private BSTNode<E> left; //reference to left child
	private BSTNode<E> right; //reference to right child
	
	/**
	 * Constructor that takes an item as parameter and stores that item in a new node.
	 * Does not accept null items.
	 * 
	 * @param data data to be stored in node
	 * @throws IllegalArgumentException if the item to be added is null
	 */
	public BSTNode(E data){
		if(data == null)
			throw new IllegalArgumentException("Error: may not instatiate a node with null data");
		this.data = data;
	}
	
	/**
	 * Returns the reference to the right child node
	 * 
	 * @return reference to the right child node
	 */
	public BSTNode<E> getRight(){
		return right;
	}
	
	/**
	 * Sets right child reference to the node given in the parameter
	 * 
	 * @param newRight node that will become the new right child of this node
	 */
	public void setRight(BSTNode<E> newRight){
		right = newRight;
	}
	
	/**
	 * Returns the reference to the left child node
	 * 
	 * @return reference to the left child node
	 */
	public BSTNode<E> getLeft(){
		return left;
	}
	
	/**
	 * Sets left child reference to the node given in the parameter
	 * 
	 * @param newRight node that will become the new left child of this node
	 */
	public void setLeft(BSTNode<E> newLeft){
		left = newLeft;
	}
	
	/**
	 * Returns item stored in this node
	 * 
	 * @return item stored in this node
	 */
	public E getData(){
		return data;
	}
	
	/**
	 * Sets item stored in this node to the item given in the parameter
	 * 
	 * @param data item that will become the new data for this node
	 */
	public void setData(E data){
		this.data = data;
	}
	
	/**
	 * Returns a comparison of two nodes using the comparison between the data in the
	 * two nodes.
	 * 
	 * @return an integer greater than 0 if this node is greater than parameter, return an
	 * integer less than 0 if this node is less than the parameter, and 0 if this node is
	 * equal to the parameter
	 */
	public int compareTo(BSTNode<E> other){
		return this.data.compareTo(other.getData());
	}
}
