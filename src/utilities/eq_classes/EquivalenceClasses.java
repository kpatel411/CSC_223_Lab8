/**
 * @authors Khushi Patel, Grace Warren
 * Course: CSC-223
 */
package utilities.eq_classes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

//import input.components.LinkedList.LinkedList.Node; 
/**
 * public class EquivalenceClasses
 * @param <T> refers to the data types permitted within EquivalenceClasses
 * EquivalenceClasses contains instances of the LinkedEquivalence class
 * these instances are stored within an ArrayList
 */
public class EquivalenceClasses<T> {
	/**
	 * instantiation of instance variables
	 */
	protected Comparator<T> _comparator;
	protected List<LinkedEquivalence<T>> _classes;
	
	/**
	 * EquivalenceClasses constructor 
	 * @param m contains the comparator (canonical) value 
	 */
	public EquivalenceClasses(Comparator<T> m) {
		_comparator = m;
		_classes = new ArrayList<LinkedEquivalence<T>>();
	}
	
	/**
	 * add() method for EquivalenceClasses
	 * checks null cases first
	 * @param element contains the element to add
	 * @return true if the element is added successfully
	 */
	public boolean add(T element) {
		if (element == null) return false;
		int index = indexOfClass(element);
		if (index == -1) {
			_classes.add(new LinkedEquivalence<T>(_comparator));
			index = _classes.size()-1;
		}
		if (_classes.get(index).contains(element)) return false;
		_classes.get(index).add(element);
		return true;
	}
	
	/**
	 * contains() method for EquivalenceClasses 
	 * checks null cases first 
	 * @param target contains target to check for 
	 * @return true if any list in EquivalenceClasses contains the target 
	 */
	public boolean contains(T target) {
		if (target == null) return false;
		int index = indexOfClass(target);
		if (index == -1) return false;
		return _classes.get(index).contains(target);
	}
	
	/**
	 * size() method for EquivalenceClasses
	 * checks null cases first
	 * @return the size of all lists contained within EquivalenceClasses combines
	 */
	public int size() {
		if (_classes.contains(null)) return -1;
		int size = 0;
		for (LinkedEquivalence<T> list: _classes) {
			size += list.size();	
		}
		return size;
	}
	
	/**
	 * numClasses() method for EquivalenceClasses
	 * @return the number of classes (lists) within EquivalenceClasses
	 */
	public int numClasses() {
		return _classes.size();
	}
	
	/**
	 * indexOfClass() method for EquivalenceClasses
	 * @param element contains the element to check for 
	 * @return the index of the class (list) which contains the element, -1 if it's not found
	 */
	protected int indexOfClass(T element) {
		int index = 0;
		for (LinkedEquivalence<T> internalList: _classes) {
			if (internalList.belongs(element)) return index;
			index++;
		}
		return -1;
	}
	
	/**
	 * toString() method for EquivalenceClasses
	 * @return a string of all lists contained  in EquivalenceClasses
	 */
	public String toString() {		
		StringBuilder s = new StringBuilder();
	    for (LinkedEquivalence<T> stringList: _classes) {
	        s.append(stringList.toString());
	    }
	    return s.toString();
	}
	
	/**
	 * getList() method for EquivalenceClasses
	 * @param index contains the index of the desired class
	 * @return the class at that index 
	 */
	public LinkedEquivalence<T> getList(int index) {
		return _classes.get(index);
	}
}
