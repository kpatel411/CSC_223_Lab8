package geometry_objects.angle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BooleanSupplier;

import geometry_objects.angle.comparators.AngleStructureComparator;
import utilities.eq_classes.EquivalenceClasses;
import utilities.eq_classes.LinkedEquivalence;

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
	 * checks null cases first
	 * @param element contains the element to add
	 * @return true if the element is added successfully
	 */
	@Override 
	public boolean add(Angle element) {
		//THIS IS THE PROBLEM
		
		int index = indexOfClass(element);
		if (index != -1) return _classes.get(index).add(element);
		
		_classes.add(new AngleLinkedEquivalenceClass(_comparator));
		return _classes.get(_classes.size() -1).add(element);
	}
}