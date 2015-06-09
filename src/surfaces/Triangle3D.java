package surfaces;

import java.util.ArrayList;


public class Triangle3D implements Surface3D {
	private Point3D p1;
	private Point3D p2;
	private Point3D p3;
	
	public Triangle3D(Point3D p1, Point3D p2, Point3D p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	@Override
	public ArrayList<Point3D> vertices() {
		ArrayList<Point3D> tmp = new ArrayList<>();
		tmp.add(p1);
		tmp.add(p2);
		tmp.add(p3);
		return tmp;
	}

	@Override
	public ArrayList<Triangle3D> triangulate() {
		ArrayList<Triangle3D> tmp = new ArrayList<>();
		tmp.add(this);
		return tmp;
	}
}
