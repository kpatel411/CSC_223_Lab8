package geometry_objects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import exceptions.FactException;
import geometry_objects.angle.Angle;
import geometry_objects.points.PointDatabase;

public class AngleClassTest {

	@Test 
	void equalsTest() throws FactException {
		
		PointDatabase pdb = new PointDatabase();
		pdb.put("A", 4, 12);
		pdb.put("B", 3, 7);
		pdb.put("C", 2, 2);
		pdb.put("D", 4, 3);
		pdb.put("E",  6,  4);
		pdb.put("F", 0, 5);
		pdb.put("G", 3.5, 5);
		
		Segment segCB = new Segment(pdb.getPoint("C"), pdb.getPoint("B"));
		Segment segCA = new Segment(pdb.getPoint("C"), pdb.getPoint("A"));
		Segment segCD = new Segment(pdb.getPoint("C"), pdb.getPoint("D"));
		Segment segCE = new Segment(pdb.getPoint("C"), pdb.getPoint("E"));
		Segment segCG = new Segment(pdb.getPoint("C"), pdb.getPoint("G"));
		Segment segCF = new Segment(pdb.getPoint("C"), pdb.getPoint("F"));
		
		List<Segment> segsAsList = new ArrayList<Segment>();
		segsAsList.add(segCB);
		segsAsList.add(segCA);
		segsAsList.add(segCD);
		segsAsList.add(segCE);
		segsAsList.add(segCG);
		segsAsList.add(segCF);
		
		assertTrue(new Angle(segCF, segCA).equals(new Angle(segCA, segCF)));
		assertTrue(new Angle(segCB, segCE).equals(new Angle(segCE, segCB)));
		assertFalse(new Angle(segCF, segCA).equals(new Angle(segCB, segCE)));
		assertFalse(new Angle(segCB, segCA).equals(new Angle(segCA, segCG)));

	}
	
}
