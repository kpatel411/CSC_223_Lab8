package geometry_objects.angle;

import geometry_objects.angle.comparators.AngleStructureComparator;
import utilities.eq_classes.EquivalenceClasses;

/**
 * Given the figure below:
 * 
 *    A-------B----C-----------D
 *     \
 *      \
 *       \
 *        E
 *         \
 *          \
 *           F
 * 
 * Equivalence classes structure we want:
 * 
 *   canonical = BAE
 *   rest = BAF, CAE, DAE, CAF, DAF
 */
public class AngleEquivalenceClasses extends EquivalenceClasses<Angle>
{
		
	public AngleEquivalenceClasses() {
		super(new AngleStructureComparator());
	}
	
	/**
	 * add() method for EquivalenceClasses
	 * @param element contains the element to add
	 * @return true if the element is added successfully
	 * Override ensures that the Equivalence Class implementation 
	 * is replaced with the proper type (Angle)
	 */
	@Override 
	public boolean add(Angle element) {	
		// get the index of the class for the given element, 
		// immediately add it if the equivalence class already exists 
		int index = indexOfClass(element);
		if (index != -1) return _classes.get(index).add(element);
		
		// otherwise, add a new equivalence class and add the element 
		_classes.add(new AngleLinkedEquivalenceClass(_comparator));
		return _classes.get(_classes.size() -1).add(element);
	}
}