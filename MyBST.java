/**
 * This class is an implementation of a binary search tree. The ordering of this
 * binary search tree is based on comparisons made using the Comparable interface. 
 * This binary search tree does not accept duplicate values or null values. 
 * 
 * @author Leila Mardoum
 * @version 4/22/17
 * @param <E> type of data to be stored in nodes of binary search tree
 */

import java.util.NoSuchElementException;

public class MyBST<E extends Comparable<E>> {
	
	protected BSTNode<E> root;
	protected int size;
	
	/**
	 * Default constructor that creates an empty MyBST object
	 */
	public MyBST(){
		
	}
	
	/**
	 * Adds the specified element to this MyBST if it is not already present. If the 
	 * set already contains the element, the call leaves the set unchanged and 
	 * returns false. Does not permit duplicate elements or null element to be added.
	 * 
	 * @param data element to be added
	 * @return true if element was added; otherwise, return false
	 * @throws ClassCastException if the specified object cannot be compared with the
	 * elements currently in the BST
	 * @throws NullPointerException if the data being added to the BST is null
	 */
	public boolean add(E data) throws ClassCastException {
		if(data == null)
			throw new NullPointerException("Error: cannot add  null");
		//if element not already in BST, add it
		if(!contains(data)){
			BSTNode<E> newNode = new BSTNode<E>(data);
			root = add(newNode, root); //modified tree
			size++;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Helper add method. Adds a new node to the binary search tree
	 * in its correct position.
	 * 
	 * @param newNode new node to be added to BST
	 * @param current current node in search
	 * @return modified binary search tree after add is performed
	 */
	private BSTNode<E> add(BSTNode<E> newNode, BSTNode<E> current){
		//if list empty, add newNode as root
		if(current == null)
			return newNode;
		//if new node > current node, go right
		else if(newNode.compareTo(current) > 0)
			current.setRight(add(newNode, current.getRight()));
		//if new node < current node, go left
		else if(newNode.compareTo(current) < 0)
			current.setLeft(add(newNode, current.getLeft()));
		return current;
	}
	
	/**
	 * Removes the specified element from the BST if present. Returns true
	 * if this BST changed as a result of the call.
	 * 
	 * @param o Object to be removed from the BST
	 * @return true if object was removed from BST; otherwise, return false
	 * @throws ClassCastException if the specified object cannot be compared 
	 * with the elements currently in the MyBST object
	 * @throws NullPointerException if the specified element is null
	 */
	public boolean remove(Object o) throws ClassCastException {
		root = recRemove(root, (E) o);
		if(root == null){
			return false;
		} else
			return true;
	}
	
	/**
	 * Recursive helper method to remove. Finds desired element if it exists in the BST
	 * and removes it.
	 * 
	 * @param node current node in BST
	 * @param item element to be removed
	 * @return reference modified BST after removal (or null if node is null)
	 */
	private BSTNode<E> recRemove(BSTNode<E> node, E item){
		if(node == null)
			return null; //do nothing, the item is not in the tree
		else if(item.compareTo(node.getData()) < 0 ) //(item < node.data)
			node.setLeft(recRemove(node.getLeft(), item)); //node.left = recRemove(node.left, item) //search in the left subtree 
		else if(item.compareTo(node.getData()) > 0) //(item > node.data)
			node.setRight(recRemove(node.getRight(), item));//node.right = recRemove ( node.right, item ) //search in the right subtree
		else //found it!
			node = remove(node); //remove data stored in node
		return node;
	}
	
	/**
	 * Helper method to recRemove. Once the node to be removed is found through recRemove,
	 * this remove method actually removes the node from the tree and accounts for any 
	 * changes this causes in the rest of the tree.
	 * 
	 * @param node
	 * @return
	 */
	private BSTNode<E> remove(BSTNode<E> node){
		//handles 1 or no children
		if(node.getLeft() == null )
			return node.getRight();
		if(node.getRight() == null)
			return node.getRight();
		
		//otherwise have 2 children
		E newData = getPredecessor(node); //store data of predecessor
		//set the data of the node we want to remove to the data of the predecessor
		node.setData(newData); 
		node.setLeft(recRemove(node.getLeft(), newData)); //remove the original node for predecessor
		return node;
	}
	
	/**
	 * Given a node in the BST as a parameter, this method returns the next 
	 * greatest element to that node (if the nodes were listed in order, it 
	 * would be the previous node). If there is no lesser element than the given 
	 * node, returns null.
	 * 
	 * @param n a given node in MyBST
	 * @return the next greatest element in relation to the given node
	 */
	public E getPredecessor(BSTNode<E> n) {
		//if no lesser element, return null
		if(n.getLeft() == null)
			return null;
		else {
			//go one step left
			BSTNode<E> current = n.getLeft();
			//go as far right as possible
			while(current.getRight() != null)
				current = current.getRight();
			return current.getData(); //return predecessor
		}
	}
	
	/**
	 * Returns true if this set contains the specified element
	 * 
	 * @param o object to be checked for containment in this BST
	 * @return true if this BST contains the specified element
	 * @throws ClassCastException if the specified object cannot be compared with the
	 * element currently in the set
	 * @throws NullPointerException if the specified element is null
	 */
	public boolean contains(Object o) throws ClassCastException {
		BSTNode<E> nodeWithItem = new BSTNode<E>((E) o);
		return contains(nodeWithItem, root); 
	}
	
	/**
	 * Helper method to contains. Searches for desired element in binary search
	 * tree, and if it is found returns true.
	 * 
	 * @param nodeWithItem node containing same data as node being looked for (for 
	 * comparison purposes)
	 * @param currentNode current node in search of BST
	 * @return true if this BST contains the specified element
	 */
	private boolean contains (BSTNode<E> nodeWithItem, BSTNode<E> currentNode) {
		if(currentNode == null) return false;
	
		//if item < current node, go left
		else if(nodeWithItem.compareTo(currentNode) < 0)
			return contains(nodeWithItem, currentNode.getLeft());
		//if item > current node, go right
		else if(nodeWithItem.compareTo(currentNode) > 0)
			return contains(nodeWithItem, currentNode.getRight());
		//item is the same as current node
		else
			return true;
	}
	
	/**
	 * Returns the lowest value element currently in this set
	 * 
	 * @return the first (lowest value) element currently in this BST
	 * @throws NoSuchElementException if this BST is empty
	 */
	public E first(){
		if(root == null)
			throw new NoSuchElementException("There are no elements in this tree, and therefore, there is no first element");
		//if no lesser elements than root
		else if(root.getLeft() == null) 
			return root.getData(); //return root
		//otherwise, go as far left as possible
		else{ 
			BSTNode<E> current = root.getLeft();
			//advance current reference as far left as possible
			while(current.getLeft() != null)
				current = current.getLeft();
			return current.getData(); //return data from leftmost node
		}
	}
	
	/**
	 * Returns the greatest element currently in the MyBST
	 * 
	 * @return the last/highest/greatest element currently in this MyBST object
	 * @throws NoSuchElementException if this MyBST object is empty
	 */
	public E last(){
		if(root == null)
			throw new NoSuchElementException("There are no elements in this tree, and therefore, there is no last element");
		//if no greater elements than root
		else if(root.getRight() == null) 
			return root.getData(); //return root
		//otherwise, go as far right as possible
		else{ 
			BSTNode<E> current = root.getRight();
			//advance current reference as far right as possible
			while(current.getRight() != null)
				current = current.getRight();
			return current.getData(); //return data from rightmost node
		}
	}
	
	/**
	 * Returns a string representation of the MyBST object in the form of a in-order
	 * listing of the elements contained in the MyBST (obtained through an inorder 
	 * traversal of the BST).
	 * 
	 * @return string representation of the MyBST object (bracketed list of elements)
	 */
	public String toString(){
		String result = "[";
		result += recInOrderTraversal(root);
		result += "]";
		return result;
	}
	
	/**
	 * Recursive helper method to toString method. Completes the inorder traversal of 
	 * the tree, adding the elements of the tree to the string list of the items. 
	 * 
	 * @param current node in traversal
	 * @return String list of elements (as bracketed list)
	 */
	private String recInOrderTraversal(BSTNode<E> current){
		StringBuilder newPart = new StringBuilder(); //will store new part to be added to MyBST
		
		if(current != null){
			//traverse left side of MyBST
			newPart.append(recInOrderTraversal(current.getLeft()));
			//process current node
			newPart.append(String.valueOf(current));
			//unless current item is the last item, add comma between elements
			if(!(current.getData().compareTo(last()) == 0))
				newPart.append(", ");
			//traverse right side of MyBST
			newPart.append(recInOrderTraversal(current.getRight()));
		}
		return newPart.toString(); //add new part to list
	}
	
	/**
	 * Returns the size (number of elements) of this BST
	 * 
	 * @return number of elements in this BST
	 */
	public int size(){
		return size;
	}
}
