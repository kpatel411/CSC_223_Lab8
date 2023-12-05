package geometry_objects.angle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

import geometry_objects.angle.comparators.AngleStructureComparator;
import utilities.eq_classes.LinkedEquivalence;

/**
 * This implementation requires greater knowledge of the implementing Comparator.
 * 
 * According to our specifications for the AngleStructureComparator, we have
 * the following cases:
 *
 *    Consider Angles A and B
 *    * Integer.MAX_VALUE -- indicates that A and B are completely incomparable
                             STRUCTURALLY (have different measure, don't share sides, etc. )
 *    * 0 -- The result is indeterminate:
 *           A and B are structurally the same, but it is not clear one is structurally
 *           smaller (or larger) than another
 *    * 1 -- A > B structurally
 *    * -1 -- A < B structurally
 *    
 *    We want the 'smallest' angle structurally to be the canonical element of an
 *    equivalence class.
 * 
 * @author XXX
 */
public class AngleLinkedEquivalenceClass extends LinkedEquivalence<Angle>
{
	public AngleLinkedEquivalenceClass(Comparator<Angle> m) {
		super(m);
	}

	/**
	 * belongs() method for LinkedEquivalence class
	 * @param target contains the element you are checking 
	 * @return true if the element does belong in the list 
	 */
	@Override 
	public boolean belongs(Angle target) {
		//checks to see if number after comparator belongs
		if (_canonical == null) return false;
		return (_comparator.compare(_canonical, target) != AngleStructureComparator.STRUCTURALLY_INCOMPARABLE);
	}

	//TODO: 
	/**
	 * add() method for LinkedEquivalence class
	 * confirms the element belongs in the list before adding it
	 * @param element contains the value to add to the LinkedEquivalence list
	 * @return true if the item is successfully added 
	 */
	@Override
	public boolean add(Angle element) {
		if (_canonical == null) {
			_canonical = element;
			return true;
		}
		
		if (!belongs(element)) {
			return false;
		}
		
		_rest.addToFront(element);
		
		if (_comparator.compare(element, _canonical) < 0) {
			demoteAndSetCanonical(element);
		}
		return true;
	}
	
	//TODO:
	/**
	 * demoteAndSetCanonical() method for LinkedEquivalence class
	 * checks if the element is valid
	 * checks if the canonical is null, if so, immediately set the canonical to that value 
	 * checks if the element belongs, if so, 
	 * put the current canonical in the list, and make the canonical the element
	 * @param element contains the new canonical value
	 * @return true if the canonical is successfully changed 
	 */
	@Override
	public boolean demoteAndSetCanonical(Angle element) {
		if (!belongs(element)) return false;
		
		boolean removed = _rest.remove(element);
		if (removed) {
			_rest.addToBack(_canonical);
			_canonical = element;
			return true;
		}
		
		return false;
	}
	
	@Override 
	public boolean removeCanonical() {
		if (isEmpty()) return false;
		
		if (_rest.isEmpty()) _canonical = null;
		else {
			_canonical = _rest.getFront();
			_rest.remove(_canonical);
		}
		return true;
	}
}