package preprocessor;

//import java.util.HashSet;
import java.util.List;
import java.util.Map;
//import java.util.Set;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.angle.AngleEquivalenceClasses;
import geometry_objects.points.*;
//import utilities.math.MathUtilities;

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

		_angles = new AngleEquivalenceClasses();

		computeAngles();

		return _angles;
	}

	/***
	 * computeAngles() method to create/find angles
	 * by creating a list of all segments, iterate to create valid angles
	 * based on endpoints using a combinations technique.
	 * Then get two segments and find the shared vertex between the two.
	 * If the vertex is valid/matching and the segemnts do not overlap create the angle and add it to the list. 
	 * @throws FactException n/a
	 */
	private void computeAngles() throws FactException
	{
		//	make the _segments iterable
		List<Segment> segsAsList = _segments.keySet().stream().toList(); 

		//	use indexed for loops to create combinations 
		for (int index_1 = 0; index_1 < segsAsList.size() - 1; index_1++) {
			for (int index_2 = index_1 + 1; index_2 < segsAsList.size(); index_2++) {

				Segment seg1 = segsAsList.get(index_1);
				Segment seg2 = segsAsList.get(index_2);
				Point vertex = seg1.sharedVertex(seg2);

				//	make sure the segments are valid to create an angle
				if ((vertex != null) && (seg1.other(vertex) != seg2.other(vertex)) && (!Segment.overlaysAsRay(seg1, seg2))) {

					_angles.add(new Angle(seg1, seg2));
				}	
			}
		}	
	}
}
