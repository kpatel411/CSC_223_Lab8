package preprocessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.Triangle;

public class TriangleIdentifier
{
	protected Set<Triangle>         _triangles;
	// The set of ALL segments for this figure:
	protected Map<Segment, Segment> _segments; 

	public TriangleIdentifier(Map<Segment, Segment> segments)
	{
		_segments = segments;
	}

	/*
	 * Compute the figure triangles on the fly when requested;
	 * memoize results for subsequent calls.
	 */
	public Set<Triangle> getTriangles()
	{
		if (_triangles != null) return _triangles;

		_triangles = new HashSet<Triangle>();

		computeTriangles();

		return _triangles;
	}

	private void computeTriangles()
	{
		//this makes the _segments iterable, and eliminates redundancy by calling keySet()
		List<Segment> segsAsList = _segments.keySet().stream().toList(); 
		
		//these three indexed for loops go over all possible combinations of segments
		for (int index_1 = 0; index_1 < segsAsList.size() - 2; index_1++) {
			for (int index_2 = index_1 + 1; index_2 < segsAsList.size() - 1; index_2++) {
				for (int index_3 = index_2 + 1; index_3 < segsAsList.size(); index_3++) {
					
					//here, all three segments are added to a list, where
					List<Segment> potentialTriSegs = new ArrayList<Segment>();
					potentialTriSegs.add(segsAsList.get(index_1));
					potentialTriSegs.add(segsAsList.get(index_2));
					potentialTriSegs.add(segsAsList.get(index_3));

					//they are used to make a triangle. Triangle() calls isValidTriangle(),
					//so by catching FactExceptions all invalid triangles are thrown out 
					try {
						_triangles.add(new Triangle(potentialTriSegs));
					}
					catch (FactException fe) {
						//this catch method obscures instances of invalid triangles
					}
					
					
				}
			}
		}
		
	}
}
