package preprocessor;

import java.util.ArrayList;
//import java.util.Arrays;
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
	protected Map<Segment, Segment> _segments; // The set of ALL segments for this figure.

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

	/***
	 * computeTriangles() method to create/find triangles
	 * creates a list of all segments with the keySet(), 
	 * iterates using a combinations technique.
	 * Then get 3 segments and add it to as new Triangle object to the set.
	 * contains a throw catch for any obsure cases.
	 */
	private void computeTriangles()
	{
		List<Segment> segsAsList = _segments.keySet().stream().toList();
		
		for (int index_1 = 0; index_1 < segsAsList.size() - 2; index_1++) {
			for (int index_2 = index_1 + 1; index_2 < segsAsList.size() - 1; index_2++) {
				for (int index_3 = index_2 + 1; index_3 < segsAsList.size(); index_3++) {
					
					List<Segment> potentialTriSegs = new ArrayList<Segment>();
					potentialTriSegs.add(segsAsList.get(index_1));
					potentialTriSegs.add(segsAsList.get(index_2));
					potentialTriSegs.add(segsAsList.get(index_3));

					try {
						_triangles.add(new Triangle(potentialTriSegs));
					}
					catch (FactException fe) {
						//This catch method obscures instances of invalid triangles
					}
					
					
				}
			}
		}
		
	}
}
