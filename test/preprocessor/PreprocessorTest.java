package preprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import geometry_objects.Segment;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import input.InputFacade;
import input.components.FigureNode;

class PreprocessorTest
{
	@Test
	void test_implicit_crossings()
	{
		FigureNode fig = InputFacade.extractFigure("fully_connected_irregular_polygon.json");

		Map.Entry<PointDatabase, Set<Segment>> pair = InputFacade.toGeometryRepresentation(fig);

		PointDatabase points = pair.getKey();

		Set<Segment> segments = pair.getValue();

		Preprocessor pp = new Preprocessor(points, segments);

		// 5 new implied points inside the pentagon
		assertEquals(5, pp._implicitPoints.size()); //pp._implicitPoints was previously iPoints


		//
		//
		//		               D(3, 7)
		//
		//
		//   E(-2,4)       D*      E*
		//		         C*          A*       C(6, 3)
		//                      B*
		//		       A(2,0)        B(4, 0)
		//
		//		    An irregular pentagon with 5 C 2 = 10 segments

		Point a_star = new Point(56.0 / 15, 28.0 / 15);
		Point b_star = new Point(16.0 / 7, 8.0 / 7);
		Point c_star = new Point(8.0 / 9, 56.0 / 27);
		Point d_star = new Point(90.0 / 59, 210.0 / 59);
		Point e_star = new Point(194.0 / 55, 182.0 / 55);

		
		assertTrue(pp._implicitPoints.contains(a_star));
		assertTrue(pp._implicitPoints.contains(b_star));
		assertTrue(pp._implicitPoints.contains(c_star));
		assertTrue(pp._implicitPoints.contains(d_star));
		assertTrue(pp._implicitPoints.contains(e_star));

		//
		// There are 15 implied segments inside the pentagon; see figure above
		//
		Set<Segment> iSegments = pp.computeImplicitBaseSegments(pp._implicitPoints);
		assertEquals(15, iSegments.size());

		List<Segment> expectedISegments = new ArrayList<Segment>();

		expectedISegments.add(new Segment(points.getPoint("A"), c_star));
		expectedISegments.add(new Segment(points.getPoint("A"), b_star));

		expectedISegments.add(new Segment(points.getPoint("B"), b_star));
		expectedISegments.add(new Segment(points.getPoint("B"), a_star));

		expectedISegments.add(new Segment(points.getPoint("C"), a_star));
		expectedISegments.add(new Segment(points.getPoint("C"), e_star));

		expectedISegments.add(new Segment(points.getPoint("D"), d_star));
		expectedISegments.add(new Segment(points.getPoint("D"), e_star));

		expectedISegments.add(new Segment(points.getPoint("E"), c_star));
		expectedISegments.add(new Segment(points.getPoint("E"), d_star));

		expectedISegments.add(new Segment(c_star, b_star));
		expectedISegments.add(new Segment(b_star, a_star));
		expectedISegments.add(new Segment(a_star, e_star));
		expectedISegments.add(new Segment(e_star, d_star));
		expectedISegments.add(new Segment(d_star, c_star));
		

		for (Segment iSegment : iSegments)
		{
			if (!expectedISegments.contains(iSegment)) {
				System.out.println(iSegment);
			}
			assertTrue(expectedISegments.contains(iSegment));
		}

		//
		// Ensure we have ALL minimal segments: 20 in this figure.
		//
		List<Segment> expectedMinimalSegments = new ArrayList<Segment>(iSegments);
		expectedMinimalSegments.add(new Segment(points.getPoint("A"), points.getPoint("B")));
		expectedMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("C")));
		expectedMinimalSegments.add(new Segment(points.getPoint("C"), points.getPoint("D")));
		expectedMinimalSegments.add(new Segment(points.getPoint("D"), points.getPoint("E")));
		expectedMinimalSegments.add(new Segment(points.getPoint("E"), points.getPoint("A")));
		
		Set<Segment> minimalSegments = pp.identifyAllMinimalSegments(pp._implicitPoints, segments, iSegments);
		assertEquals(expectedMinimalSegments.size(), minimalSegments.size());

		for (Segment minimalSeg : minimalSegments)
		{
			assertTrue(expectedMinimalSegments.contains(minimalSeg));
		}
		
		//
		// Construct ALL figure segments from the base segments
		//
		Set<Segment> computedNonMinimalSegments = pp.constructAllNonMinimalSegments(minimalSegments);
		
		//
		// All Segments will consist of the new 15 non-minimal segments.
		//		
		assertEquals(15, computedNonMinimalSegments.size());

		//
		// Ensure we have ALL minimal segments: 20 in this figure.
		//
		List<Segment> expectedNonMinimalSegments = new ArrayList<Segment>();
		expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), d_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("D"), c_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), points.getPoint("D")));
		
		expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), c_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("E"), b_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("E")));
		
		expectedNonMinimalSegments.add(new Segment(points.getPoint("C"), d_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("E"), e_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("C"), points.getPoint("E")));		

		expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), a_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("C"), b_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), points.getPoint("C")));
		
		expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), e_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("D"), a_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("D")));
		
		//
		// Check size and content equality
		//		
		assertEquals(expectedNonMinimalSegments.size(), computedNonMinimalSegments.size());

		for (Segment computedNonMinimalSegment : computedNonMinimalSegments)
		{
			assertTrue(expectedNonMinimalSegments.contains(computedNonMinimalSegment));
		}
	}
	
		
//		@Test
//		void test_three_point_on_paralle_lines() {
//
//			FigureNode fig = InputFacade.extractFigure("three_point_on_parallel_line.json");
//
//			Map.Entry<PointDatabase, Set<Segment>> pair = InputFacade.toGeometryRepresentation(fig);
//
//			PointDatabase points = pair.getKey();
//
//			Set<Segment> segments = pair.getValue();
//			
//			Preprocessor pp = new Preprocessor(points, segments);
//			
//			assertEquals(7, pp._implicitPoints.size());
//			
//			Point a_star = new Point(4, 4);
//			Point b_star = new Point(6 , 4);
//			Point c_star = new Point(8 , 4);
//			Point d_star = new Point(14.0/3 , 14.0/3);
//			Point e_star = new Point(14.0/3 , 10.0/3 );
//			Point f_star = new Point(22.0/3, 10./3); 
//			Point g_star = new Point(22.0/3, 14.0/3);
//			
//	
//			
//			assertTrue(pp._implicitPoints.contains(a_star));
//			assertTrue(pp._implicitPoints.contains(b_star));
//			assertTrue(pp._implicitPoints.contains(c_star));
//			assertTrue(pp._implicitPoints.contains(d_star));
//			assertTrue(pp._implicitPoints.contains(e_star));
//			assertTrue(pp._implicitPoints.contains(g_star));
//			assertTrue(pp._implicitPoints.contains(f_star));
//			
//			// There are 20 implied segments inside the pentagon; see figure above
//			//
//			Set<Segment> iSegments = pp.computeImplicitBaseSegments(pp._implicitPoints);
//			assertEquals(22, iSegments.size());
//
//			//
//			// Ensure we have ALL minimal segments: 22 in this figure.
//			//
//			List<Segment> expectedISegments = new ArrayList<Segment>();
//			
//			
//			expectedISegments.add(new Segment(points.getPoint("A"), d_star));
//			expectedISegments.add(new Segment(d_star, b_star));
//			expectedISegments.add(new Segment(b_star, f_star));
//			expectedISegments.add(new Segment(f_star, points.getPoint("F")));
//			
//			expectedISegments.add(new Segment(points.getPoint("A"), a_star));
//			expectedISegments.add(new Segment(a_star, e_star));
//			expectedISegments.add(new Segment(e_star, points.getPoint("E")));
//			
//			expectedISegments.add(new Segment(points.getPoint("B"), d_star));
//			expectedISegments.add(new Segment(d_star, a_star));
//			expectedISegments.add(new Segment(a_star, points.getPoint("D")));
//			
//			expectedISegments.add(new Segment(points.getPoint("B"), b_star));
//			expectedISegments.add(new Segment(b_star, points.getPoint("E")));
//			
//			expectedISegments.add(new Segment(points.getPoint("B"), g_star));
//			expectedISegments.add(new Segment(g_star, c_star));
//			expectedISegments.add(new Segment(c_star, points.getPoint("F")));
//			
//			expectedISegments.add(new Segment(points.getPoint("C"), g_star));
//			expectedISegments.add(new Segment(g_star, b_star));
//			expectedISegments.add(new Segment(b_star, e_star));
//			expectedISegments.add(new Segment(e_star, points.getPoint("D")));
//			
//			expectedISegments.add(new Segment(points.getPoint("C"), c_star));
//			expectedISegments.add(new Segment(c_star, f_star));
//			expectedISegments.add(new Segment(f_star, points.getPoint("E")));
//		
//			for (Segment iSegment : iSegments)
//			{
//				if (!expectedISegments.contains(iSegment)) {
//					System.out.println(iSegment);
//				}
//				assertTrue(expectedISegments.contains(iSegment));
//			}
//
//			//
//			// Ensure we have ALL minimal segments: 28 in this figure.
//			//
//			List<Segment> expectedMinimalSegments = new ArrayList<Segment>(iSegments);
//			expectedMinimalSegments.add(new Segment(points.getPoint("A"), points.getPoint("B")));
//			expectedMinimalSegments.add(new Segment(points.getPoint("A"), points.getPoint("D")));
//			expectedMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("C")));
//			expectedMinimalSegments.add(new Segment(points.getPoint("C"), points.getPoint("F")));
//			expectedMinimalSegments.add(new Segment(points.getPoint("D"), points.getPoint("E")));
//			expectedMinimalSegments.add(new Segment(points.getPoint("E"), points.getPoint("F")));
//			
//			Set<Segment> minimalSegments = pp.identifyAllMinimalSegments(pp._implicitPoints, segments, iSegments);
//			assertEquals(expectedMinimalSegments.size(), minimalSegments.size());
//
//			for (Segment minimalSeg : minimalSegments)
//			{
//				assertTrue(expectedMinimalSegments.contains(minimalSeg));
//			}
//			
//			//
//			// Construct ALL figure segments from the base segments
//			//
//			Set<Segment> computedNonMinimalSegments = pp.constructAllNonMinimalSegments(minimalSegments);
//			
//			//
//			// All Segments will consist of the new 15 non-minimal segments.
//			//		
//			assertEquals(27, computedNonMinimalSegments.size());
//
//			
//			
//			List<Segment> expectedNonMinimalSegments = new ArrayList<Segment>();
//			expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), b_star));
//			expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), f_star));
//			expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), points.getPoint("F")));
//			expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), points.getPoint("C")));
//			expectedNonMinimalSegments.add(new Segment(d_star, f_star));
//			expectedNonMinimalSegments.add(new Segment(d_star,points.getPoint("F")));  // okay
//			expectedNonMinimalSegments.add(new Segment(b_star,points.getPoint("F")));  // okay
//			
//			
//			expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), e_star));
//			expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), points.getPoint("E")));
//			expectedNonMinimalSegments.add(new Segment(a_star, points.getPoint("E")));
//			
//			expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), a_star));
//			expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("D")));
//			expectedNonMinimalSegments.add(new Segment(g_star, points.getPoint("D"))); //
//			
//			expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("E")));
//			
//			expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), c_star));
//			expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("F")));
//			expectedNonMinimalSegments.add(new Segment(g_star, points.getPoint("F"))); 
//			
//			expectedNonMinimalSegments.add(new Segment(points.getPoint("C"), b_star));
//			expectedNonMinimalSegments.add(new Segment(points.getPoint("C"), e_star));
//			expectedNonMinimalSegments.add(new Segment(points.getPoint("C"), points.getPoint("D")));
//			
//			expectedNonMinimalSegments.add(new Segment(g_star, e_star));
//			expectedNonMinimalSegments.add(new Segment(d_star,points.getPoint("D"))); // 
//			
//			expectedNonMinimalSegments.add(new Segment(points.getPoint("C"), f_star));
//			expectedNonMinimalSegments.add(new Segment(points.getPoint("C"), points.getPoint("E")));
//			expectedNonMinimalSegments.add(new Segment(c_star, points.getPoint("E")));
//			expectedNonMinimalSegments.add(new Segment(points.getPoint("D"), points.getPoint("F")));
//			expectedNonMinimalSegments.add(new Segment(b_star,points.getPoint("D")));
//			
//			
//			//
//			// Check size and content equality
//			//		
//			assertEquals(expectedNonMinimalSegments.size(), computedNonMinimalSegments.size());
//
//			for (Segment computedNonMinimalSegment : computedNonMinimalSegments)
//			{
//				if(!expectedNonMinimalSegments.contains(computedNonMinimalSegment)) {
//					System.out.println(computedNonMinimalSegment);
//				}
//				assertTrue(expectedNonMinimalSegments.contains(computedNonMinimalSegment));
//			}
//			
//			
//			
//		}
		
	
}