package geometry_objects;

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
		
		List<Angle> angles = new ArrayList<Angle>();
		for (Segment seg_1 : segsAsList) {
			for (Segment seg_2 : segsAsList) {
				if (!seg_1.equals(seg_2)) {
					angles.add(new Angle(seg_1, seg_2));
				}
			}
		}
		
		for (Angle angle_1 : angles) {
			for (Angle angle_2 : angles) {
				System.out.println(angle_1 + "\n" + angle_2 + "\n" + angle_1.equals(angle_2));
				System.out.println(angle_1.compareTo(angle_2));
				System.out.println(" ");
			}
		}
		
	}
	
}
