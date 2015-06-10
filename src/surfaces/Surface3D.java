package surfaces;

import java.util.ArrayList;

public interface Surface3D {
	public ArrayList<Point3D> vertices();
	public ArrayList<Triangle3D> triangulate();
	public double surfaceArea();
}
