/**
 * @authors Khushi Patel, Grace Warren
 * Course: CSC-223
 */
package utilities.eq_classes;
import java.util.Comparator;

import utilities.LinkedList; 

/**
 * public class LinkedEquivalence filters items with a _canonical value, 
 * and is implemented with an instance of LinkedList
 * @param <T> refers to the data types permitted within LinkedEquivalence
 */
public class LinkedEquivalence<T> {
	/**
	 * instantiation of instance variables
	 */
	protected T _canonical;
	protected Comparator<T> _comparator;
	protected LinkedList<T> _rest; 

	/**
	 * LinkedEquivalence() constructor 
	 * @param m contains the canonical value for the instance of the class being instantiated 
	 */
	public LinkedEquivalence(Comparator<T> m) {
		_canonical = null; 
		_comparator = m;
		_rest = new LinkedList<T>(); 
	}

	/**
	 * canonical() method for LinkedEquivalence class
	 * @return the _canonical value
	 */
	public T canonical() {
		//returns canon element
		return _canonical;
	}

	/**
	 * isEmpty() method for LinkedEquivalence class
	 * @return true if the LinkedEquivalence list is empty
	 */
	public boolean isEmpty() {
		if (_canonical != null) return false;
		return (_rest.isEmpty());
	}

	/**
	 * clear() method for LinkedEquivalence class
	 * sets _canonical to null, and empties the remaining list using the LinkedList clear() method
	 */
	public void clear() {
		_canonical = null;
		_rest.clear();
	}

	/**
	 * clearNonCanonical() method for LinkedEquivalence class
	 * removes every element in LinkedEquivalence except the canonical 
	 */
	public void clearNonCanonical() {
		_rest.clear(); 
	}

	/**
	 * size() method for LinkedEquivalence class
	 * @return the size of the list, including the canonical value, if not null
	 */
	public int size() {
		if (_canonical == null) {
			return _rest.size();
		}
		return _rest.size() + 1;
	}

	/**
	 * add() method for LinkedEquivalence class
	 * confirms the element belongs in the list before adding it
	 * @param element contains the value to add to the LinkedEquivalence list
	 * @return true if the item is successfully added 
	 */
	public boolean add(T element) {
		if (_canonical == null) {
			_canonical = element;
			_rest.remove(element);
			return true;
		}
		if (!belongs(element)) return false;
		if (_rest.contains(element)) return false;
		_rest.addToBack(element);
		return true;
	}

	/**
	 * contains() method for LinkedEquivalence class
	 * @param target contains the value you are checking for
	 * @return true if the value exists in the LinkedEquivalence list 
	 */
	public boolean contains(T target) {
		if (!belongs(target)) return false;
		if (_canonical.equals(target)) return true;
		return _rest.contains(target);
	}

	/**
	 * belongs() method for LinkedEquivalence class
	 * @param target contains the element you are checking 
	 * @return true if the element does belong in the list 
	 */
	public boolean belongs(T target) {
		//checks to see if number after comparator belongs
		//		return (_comparator.compare(target, _canonical) == _comparator.compare(_canonical, _canonical));
		if (_canonical == null) return false;
		return (_comparator.compare(_canonical, target) == 0);
	}

	/**
	 * remove() method for LinkedEquivalence class
	 * @param target contains the item to remove
	 * @return true if the item was successfully removes
	 */
	public boolean remove(T target) {
		if (_canonical.equals(target)) {
			removeCanonical();
			return true;
		}
		if (_rest.contains(target)) {
			_rest.remove(target);
			return true;
		}
		return false;
	}

	/**
	 * removeCanonical() method for LinkedEquivalence class
	 * @return true if the canonical is successfully removed 
	 */
	public boolean removeCanonical() {
		if (_canonical == null) return false;
		if (_rest.isEmpty()) {
			_canonical = null;
			return true;
		}
		_canonical = null;
		return (demoteAndSetCanonical(_rest.getFront()));
	}

	/**
	 * demoteAndSetCanonical() method for LinkedEquivalence class
	 * checks if the element is valid
	 * checks if the canonical is null, if so, immediately set the canonical to that value 
	 * checks if the element belongs, if so, 
	 * put the current canonical in the list, and make the canonical the element
	 * @param element contains the new canonical value
	 * @return true if the canonical is successfully changed 
	 */
	public boolean demoteAndSetCanonical(T element) {
		if (element == null) return false; 
		if (_canonical == null) { 
			_canonical = element;
			_rest.remove(element);
			return true;
		}
		if (!belongs(element)) return false; 
		if (_canonical.equals(element)) return false;
		if (_rest.contains(element)) _rest.remove(element); 
		_rest.addToFront(_canonical);
		_canonical = element;
		return true;
	}

	/**
	 * toString() method for LinkedEquivalence class
	 */
	public String toString() {
		if (_canonical == null) return "";
		return _canonical + " " + _rest.toString();
	}
}
