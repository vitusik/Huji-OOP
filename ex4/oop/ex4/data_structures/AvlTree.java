package oop.ex4.data_structures;

import java.util.Iterator;


/**
 * This class represents an AVL tree including all the basic method's like add,
 * delete, contain
 */
public class AvlTree implements Iterable<Integer> {

	final private static int DOES_NOT_CONTAIN = -1;
	final private static int HEIGHT_CORRECTION = 1;
	final private static int AVL_GAP_MAX = 1;
	final private static int NULL_HEIGHT_DEFAULT = -1;
	
	private int size;
	private AvlNode root;
    
	/**
	 * A default constructor
	 */
	public AvlTree(){
        this.root = null;
        this.size = 0;
	}
	
	/**
	 * A data constructor
	 * a constructor that builds the tree by adding the elements
	 * in the input array one by one.
	 * if the same value appears twice (or more) in the list,
	 * it is ignored.
	 * @param data values to add to tree.
	 */
	public AvlTree(int[] data){
        this.root = null;
        this.size = 0;
		for(int value: data){
			add(value);
		}
	}
	
	/**
	 * A copy constructor
	 * a constructor that builds the tree from an existing copy of a tree.
	 * @param tree an AvlTree
	 */
	public AvlTree(AvlTree tree){
        this.root = null;
        this.size = 0;
        for(int val : tree){
            add(val);
        }
	}
	
	/**
	 * add a new node with key newValue into the tree
	 * 
	 * @param newValue new value to add to the tree
	 * @return false if newValue already exist in the tree
	 */
	 public boolean add(int newValue){
		 
		 AvlNode node = this.root;
		 AvlNode father;
		//finding the right place to insert
		 while(true){
			 father = node;
			 if(node == null){
                 this.root = new AvlNode(newValue);
				 break;
			 }
			 // adding the value to the right subtree of the current node
			 else if (node.value < newValue){
				 node = node.right;
				 if(node == null){
					 node = new AvlNode(newValue);
					 node.father = father;
					 father.right = node;
					 break;
				 }	
			 }
			// adding the value to the left subtree of the current node
			 else if (node.value > newValue){
				 node = node.left; 
				 if(node == null){
					 node = new AvlNode(newValue);
					 node.father = father;
					 father.left = node;
					 break;
				 }
			 }
			 // the only possibility left is that the new value exists in the tree, therefore will not be added
			 else return false;
		 }
		 // after a new node has been added there is a need to update the heights of the nodes
		 while(node != null){
             updateHeight(node);
			 if (Math.abs(node.rightHeight-node.leftHeight) > AVL_GAP_MAX)
                 Organize(node);
			 node = node.father;

		 }
         this.size++;
		 return true;
	 }
	 /**
	  * Check whether searchVal contained in the tree.
	  * 
	  * @param searchVal value to search for
	  * @return if searchVal is found in the tree,
	  *  return the depth of its node (where 0 is the root of the tree)
	  *  otherwise - return -1
	  */
	 public int contains(int searchVal){
		 AvlNode node = this.root;
		 int depth = 0;
		 while(node != null){
			 if (node.value == searchVal)
				 return depth;
			 else if (node.value < searchVal)
				 node = node.right;
			 else
				 node = node.left;
			 depth++;
		 }
		 return DOES_NOT_CONTAIN; 
	 }
	 
	 /**
	  * remove node from the tree
	  * 
	  * @param toDelete value to delete
	  * @return true iff toDelete id found and deleted
	  */
	 public boolean delete(int toDelete){
		 AvlNode node = this.root;
		 AvlNode father;
		 int i = 0;
         int depth = this.contains(toDelete);
         if(depth == DOES_NOT_CONTAIN ){
             return false;
         }
         //finding the node that needs to be deleted
         while(i < depth){
             if(node.value < toDelete){
                 node = node.right;
             }
             else if(node.value > toDelete){
                 node = node.left;
             }
             i ++;
         }
		 father = node.father;
		 //breaking it to 4 possible cases

         // the deleted node is a leaf
		 if(node.right == null && node.left == null){
			 if (father == null){
				 root = null;
				 size--;
				 return true;
			 }
			 else{// father != null
				 if(father.right != null && father.right.equals(node)){
					 father.right = null;
					 father.rightHeight--;
				 }
				 else{
					 father.left = null;
					 father.leftHeight--;
				 }
			 }
		 }

		 // there is only a left child
		 else if (node.left != null && node.right == null){
			 if(father == null){
				 node.left.father = null;
				 root = node.left;
			 }		 
			 else if(father.right != null && father.right.equals(node)){
				 father.right = node.left;
				 node.left.father = father;

			 }
			 else{// father.left = node
				 father.left = node.left;
				 node.left.father = father;

			 }

		 }
		 
		// there is only a right child
		 else if (node.right != null && node.left == null){ 
			 if(father == null){
				 node.left.father = null;
				 root = node.right;
			 }
			 else if(father.right != null && 
					 father.right.equals(node)){
				 father.right = node.right;
				 node.right.father = father;
			 }
			 else{
				 father.left = node.right;
				 node.right.father = father;
			 }

		 }
		 // the node has 2 children
		 else if(node.right != null && node.left != null){
			 AvlNode successor = this.findSuccessor(node);
			 AvlNode successorFather = successor.father;
             // switching between the node's data members and his successor's
             successor.right = node.right;
             successor.left = node.left;
             successor.value = node.value;
             node = null;
		 }
		 // Organize the tree
		 while(node != null){
			 if (Math.abs(node.rightHeight-node.leftHeight) > AVL_GAP_MAX)
                 Organize(node);
			 node = node.father;
			 // update the heights
			 updateHeight(node);
		 }
		 size--;
		 return true;
	 }
	 
	 /**
	  * @return number of nodes in the tree
	  */
	 public int size(){
	 return size;	 
	 }
	 /**
	  * @return iterator to the avl tree. the returned iterator can pass over
	  * the tree nodes in ascending order.
	  */
	  public Iterator<Integer> iterator(){

          AvlNode node = root;
		  //finds the min node
		  if(node == null)
			  return null;
		  else while(node.left != null){
			  node = node.left;
		  }
		  
		  final AvlNode smallestAvlNode = node;
		  final int finalSize = size;
		  
		  Iterator<Integer> iterator = new Iterator<Integer>(){
			  
			  int sizeCounter = finalSize;
              AvlNode node = smallestAvlNode;
              AvlNode nodePredecessor;
			  
			  @Override
			  public boolean hasNext() {
				  if(sizeCounter > 0)
					  return true;
				  return false;
			  }
			  
			  @Override
			  public Integer next() {
				  if (!hasNext())
					  return null;
                  nodePredecessor = node;
				  node = findSuccessor(node);
				  sizeCounter--;
				  return nodePredecessor.value;
			  }
			  
			  @Override
			  public void remove() {
				  throw new UnsupportedOperationException();
			  }
		  };
		  return iterator;
	  }
	  
	  /**
	   * This method calculates the minimum number of nodes in an AVL tree of 
	   * height h.
	   * 
	   * @param h height of the tree (a non-negative number). 
	   * @return minimum number of nodes in the tree.
	   */
	  public static int findMinNodes(int h){
		  if(h == 0)
			  return 1;
		  if(h == 1)
			  return 2;
		  // formula is findMinNodes(h-1) + findMinNodes(h-2) + 1
		  return findMinNodes(h - 1) + findMinNodes(h - 2) + 1;
	  }
	  
	  /**
	   * This method defines which of the rotations the tree needs in a 
	   * specific node  
	   * 
	   * @param node the general node in which the rotations need to take act.
	   */
	  private void Organize(AvlNode node){
		  // Divide into 4 cases
		  if (node.leftHeight < node.rightHeight){
			  // needs a left rotation (RR rotation)
			  if(node.right.rightHeight >= node.right.leftHeight){
				  LeftRotation(node);
			  }
			  // needs a right rotation the a left rotation (RL rotation)
			  else{
				  RightRotation(node.right);
				  LeftRotation(node); 
			  }
		  }
		  else if(node.leftHeight > node.rightHeight){
			  // needs a right rotation (LL rotation)
			  if(node.left.rightHeight <= node.left.leftHeight){
				  RightRotation(node);
			  }
			  // needs a left rotation the a right rotation (LR rotation)
			  else{
				  LeftRotation(node.left);
				  RightRotation(node); 
			  }
		  }
	  }
	  
	  /**
	   * This method is called by the reOrganize method. It only makes one
	   * change, a Right Rotation change, in the AVL tree
	   * 
	   * @param node the node in which the rotations need to be.
	   */
	  private void RightRotation(AvlNode node){
          AvlNode nodeFather = node.father;
          AvlNode nodeLeft = node.left;
          AvlNode RightAvlNodeOflLeft = nodeLeft.right;
		  
		  // adjust nodeLeft
		  if(nodeFather != null){
			  if (nodeFather.right != null && 
					  nodeFather.right.equals(node))
				  nodeFather.right = nodeLeft;
			  else
				  nodeFather.left = nodeLeft;
		  }
		  else
			  root = nodeLeft;
		  nodeLeft.father = nodeFather;
		  
		  // adjust node
		  node.father = nodeLeft;
		  nodeLeft.right = node;
		  
		  // adjust RightAvlNodeOflLeft
		  if(RightAvlNodeOflLeft != null)
			  RightAvlNodeOflLeft.father = node;
		  node.left = RightAvlNodeOflLeft;
		  
		  // update height by depth
		  updateHeight(RightAvlNodeOflLeft);
		  updateHeight(nodeFather);
		  updateHeight(nodeLeft);
		  updateHeight(node);
	  }
	  
	  /**
	   * This method is called by the reOrganize method. It only makes one
	   * change, a Left Rotation change, in the AVL tree
	   * 
	   * @param node the node in which the rotations need to be.
	   */
	  private void LeftRotation(AvlNode node){
		  
		// this 3 node's plus node will be adjusted 
          AvlNode nodeFather = node.father;
          AvlNode nodeRight = node.right;
          AvlNode LeftAvlNodeOfRight = nodeRight.left;
		  
		  // adjust nodeRight
		  if(nodeFather != null){
			  if (nodeFather.right != null && 
					  nodeFather.right.equals(node))
				  nodeFather.right = nodeRight;
			  else
				  nodeFather.left = nodeRight;
		  }
		  else
			  root = nodeRight;
		  nodeRight.father = nodeFather;
		  
		  // adjust node
		  node.father = nodeRight;
		  nodeRight.left = node;
		  
		  // adjust LeftAvlNodeOfRight
		  if(LeftAvlNodeOfRight != null)
			  LeftAvlNodeOfRight.father = node;
		  node.right = LeftAvlNodeOfRight;
		  
		  // update height by depth
		  updateHeight(LeftAvlNodeOfRight);
		  updateHeight(node);
		  updateHeight(nodeRight);
		  updateHeight(nodeFather);
	  }
	  
	  /**
	   * Method that updates the height of each node after adding or deleting a 
	   * node.
	   * @param node from which the correction of the update will take place.
	   */
	  private void updateHeight(AvlNode node){
		  if(node == null)
			  return;
		  if(node.left != null && node.right != null){
			  node.leftHeight = node.left.getHeight();
			  node.rightHeight = node.right.getHeight();
		  }
		  else if(node.left != null){
			  node.leftHeight = node.left.getHeight();
			  node.rightHeight = NULL_HEIGHT_DEFAULT;
		  }
		  else if(node.right != null){
			  node.leftHeight = NULL_HEIGHT_DEFAULT;
			  node.rightHeight = node.right.getHeight();
		  }
		  else{
			  node.leftHeight = NULL_HEIGHT_DEFAULT;
			  node.rightHeight = NULL_HEIGHT_DEFAULT;
		  }
	  }
	  
	  private AvlNode findSuccessor(AvlNode node){
		  if(node.right == null){
			  if(node.father != null && node.father.left != null && node.father.left.equals(node))
				  node = node.father;
			  else while(node.father != null && node.father.right.equals(node)){
				  node = node.father;
				  if(node == null)
					  return null;
			  }
		  }
		  else if(node.right != null){
			  node = node.right;
			  while(node.left != null){
				  node = node.left;
			  }
		  }
		  return node;
	  }
	  
	  /**
	   * This inner class represents the nodes of the AVL tree.
	   * The class represents the characteristics of the node like the father,
	   * right, left, value etc...
	   */
	  private class AvlNode{

		  private AvlNode father;
		  private AvlNode right;
		  private AvlNode left;
		  private int value;
		  private int leftHeight;
		  private int rightHeight;
		  
		  private AvlNode(){
			  father = null;
			  right = null;
			  left = null;
			  this.value = 0;
			  leftHeight = NULL_HEIGHT_DEFAULT;
			  rightHeight = NULL_HEIGHT_DEFAULT;
		  }
		  
		  private AvlNode(int value){
			  father = null;
			  right = null;
			  left = null;
			  this.value = value;
			  leftHeight = NULL_HEIGHT_DEFAULT;
			  rightHeight = NULL_HEIGHT_DEFAULT;
		  }
		  
		  private int getHeight(){
			  return Math.max(rightHeight, leftHeight) + HEIGHT_CORRECTION;
		  }
	  }
}