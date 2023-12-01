package preprocessor;

import java.util.List;
import java.util.Map;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.angle.AngleEquivalenceClasses;

public class AngleIdentifier
{
	protected AngleEquivalenceClasses _angles;
	protected Map<Segment, Segment> _segments; // The set of ALL segments for this figure

	public AngleIdentifier(Map<Segment, Segment> segments)
	{
		_segments = segments;
	}

	/*
	 * Compute the figure triangles on the fly when requested; memoize results for subsequent calls.
	 */
	public AngleEquivalenceClasses getAngles() throws FactException
	{
		if (_angles != null) return _angles;

		_angles = new AngleEquivalenceClasses(); //we added the null

		computeAngles();

		return _angles;
	}

	private void computeAngles() throws FactException
	{
		//	make the _segments iterable
		List<Segment> segsAsList = _segments.keySet().stream().toList(); 
		
		//	use indexed for loops to create combinations 
		for (int index_1 = 0; index_1 < segsAsList.size() - 1; index_1++) {
			for (int index_2 = index_1; index_2 < segsAsList.size(); index_2++) {
				
				Segment seg1 = segsAsList.get(index_1);
				Segment seg2 = segsAsList.get(index_2);
				
				//	make sure the segments are valid to create an angle
				if ((seg1.sharedVertex(seg2) != null) &&
					(Segment.overlaysAsRay(seg1, seg2))) {
					
					_angles.add(new Angle(seg1, seg2));
				}	
			}
		}
	}
}
