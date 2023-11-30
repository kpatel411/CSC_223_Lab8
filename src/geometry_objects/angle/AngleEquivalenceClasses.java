package geometry_objects.angle;

import java.util.Comparator;
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

	public AngleEquivalenceClasses(Comparator<Angle> m) {
		super(m);
	}
	
	@Override 
	/**
	 * add() method for EquivalenceClasses
	 * checks null cases first
	 * @param element contains the element to add
	 * @return true if the element is added successfully
	 */
	public boolean add(Angle element) {
		if (element == null) return false;
		int index = indexOfClass(element);
		if (index == -1) {
			_classes.add(new LinkedEquivalence<Angle>(_comparator));
			index = _classes.size()-1;
		}
		if (_classes.get(index).contains(element)) return false;
		_classes.get(index).add(element);
		return true;
	}
	
}