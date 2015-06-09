package surfaces;

import java.util.ArrayList;

public class Tetrahedron implements Surface3D {
	private Point3D p1;
	private Point3D p2;
	private Point3D p3;
	private Point3D p4;
	
	public Tetrahedron(Point3D v1, Point3D v2, Point3D v3, Point3D v4) {
		p1 = v1;
		p2 = v2;
		p3 = v3;
		p4 = v4;
	}
	
	@Override
	public ArrayList<Point3D> vertices() {
		ArrayList<Point3D> tetra = new ArrayList<>();
		tetra.add(p1);
		tetra.add(p2);
		tetra.add(p3);
		tetra.add(p4);
		
		return tetra;
	}

	@Override
	public ArrayList<Triangle3D> triangulate() {
		Triangle3D t1 = new Triangle3D(p1,p2,p3);
		Triangle3D t2 = new Triangle3D(p1,p2,p4);
		Triangle3D t3 = new Triangle3D(p1,p3,p4);
		Triangle3D t4 = new Triangle3D(p2,p3,p4);
		ArrayList<Triangle3D> tetra = new ArrayList<>();
		tetra.add(t1);
		tetra.add(t2);
		tetra.add(t3);
		tetra.add(t4);
		return tetra;
	}

	
}
