/**
 * @authors Khushi Patel, Grace Warren
 * Course: CSC-223
 */
package input.components.LinkedList;

/**
 * public class LinkedList implement a generic singly linked list 
 * @param <T> refers to the data types permitted within LinkedList
 */
public class LinkedList<T> {
	
	/**
	 * private class Node instantiates a node of API type Node 
	 * nodes are single elements contained within LinkedList
	 */
	private class Node {
		private T _item;
		private Node _next;
		
		public Node(T item, Node next) {
			_item = item;
			_next = next;
		}
	}
	
	/**
	 * instantiation of sentinel nodes head and tail
	 * instantiation of _size variable, which tracks the size of the list
	 */
	private Node _head;
	private Node _tail;
	private int _size;
	
	/**
	 * LinkedList() constructor for LinkedList
	 */
	public LinkedList() {
		_tail = new Node(null, null);
	    _head = new Node(null, _tail);
	    _size = 0;
	}
	
	/**
	 * isEmpty() method for LinkedList
	 * @return true if the linked list is empty (contains only the sentinel nodes)
	 */
	public boolean isEmpty() {
		return _head._next == _tail;
	}
	
	/**
	 * clear() method for LinkedList
	 * sets size to 0, and removes all elements in the list by pointing the head to the tail
	 */
	public void clear() {
		_head._next = _tail;
		_size = 0;
	}
	
	/**
	 * size() method for LinkedList
	 * @return the size of the list
	 */
	public int size() {
		return _size;
	}
	
	/**
	 * addToFront() method for LinkedList
	 * adds an element to the front of the list
	 * @param element contains the _item value for the new node
	 */
	public void addToFront(T element) {
		//new line:
		if (element == null) return;
		//
		_head._next = new Node(element, _head._next);
		_size++;
	}
	
	/**
	 * contains() method for LinkedList
	 * @param target contains the _item value of the node we are searching for
	 * @return true if we find a node that contains the value of target
	 */
	public boolean contains(T target) {
		for (Node n = _head._next; n != _tail; n = n._next) {
			if (n._item.equals(target)) return true;
		}
		return false;
	}
	
	/**
	 * previous() method for LinkedList
	 * @param target contains the _item value of the node we are searching for
	 * @return the node preceding the node containing the value of target
	 */
	public Node previous(T target) {
		for (Node n = _head; n._next != _tail; n = n._next) {
			if (n._next._item.equals(target)) return n;
		}
		return null;
	}
	
	/**
	 * remove() method for LinkedList
	 * @param target contains the _item value of the node we are to remove
	 * @return true if that node exists, and has been removed
	 */
	public boolean remove(T target) {
		if (!(contains(target))) return false;
		Node n = previous(target);
		n._next = n._next._next;
		_size--;
		return true;
	}
	
	/**
	 * last() method for LinkedList
	 * @return the last valid node in the list 
	 */
	protected Node last() {
		for (Node n = _head._next; n != _tail; n = n._next) {
			if (n._next == _tail) return n;
		}
		return null;
	}
	
	/**
	 * addToBack() method for LinkedList
	 * @param element contains the _item value for the node to add
	 * adds an element to the rear of the list
	 */
	public void addToBack(T element) {
		if (isEmpty()) {
			addToFront(element);
			return;
		}
		last()._next = new Node(element, _tail);
		_size++;
	}
	
	/**
	 * toString() method for LinkedList
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
	    for (Node n = _head._next; n != _tail; n = n._next) {
	        s.append(n._item + " ");
	    }
	    return s.toString();
	}
	
	/**
	 * reverse() public method for LinkedList
	 * @return a new list, reversed, which is a reversal of the current LinkedList
	 */
	public LinkedList<T> reverse() {
		return reverse(_head._next, new LinkedList<T>());
	}
	
	/**
	 * reverse() private method for LinkedList
	 * @param current contains the node we are currently analyzing
	 * @param reversed contains the reversed list, as we build it
	 * @return a new list, reversed, which is a reversal of the current LinkedList
	 */
	private LinkedList<T> reverse(Node current, LinkedList<T> reversed) {
		if (current == null) return reversed;
		reversed.addToFront(current._item);
		return this.reverse(current._next, reversed);
	}
	
	//helper method so i can test item values in my test class
	/**
	 * getItem() method for LinkedList
	 * @param n contains the node for which we with the _item value
	 * @return the _item value of that node
	 * this is a helper method that allows item value testing
	 */
	public T getItem(Node n) {
		return n._item;
	}
	
	//helper method to get the first item in the list
	/**
	 * getFront() method for LinkedList
	 * @return the _item value of the first valid node in the list 
	 */
	public T getFront() {
		return _head._next._item;
	}
	
}
