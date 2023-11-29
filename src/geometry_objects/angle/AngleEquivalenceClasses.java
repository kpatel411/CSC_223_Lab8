package geometry_objects.angle;

import java.util.function.BooleanSupplier;

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

	public Object numClasses() {
		// TODO Auto-generated method stub
		return null;
	}
	// TODO

	public Object size() {
		// TODO Auto-generated method stub
		return null;
	}

	public BooleanSupplier contains(Angle expected) {
		// TODO Auto-generated method stub
		return null;
	}
}