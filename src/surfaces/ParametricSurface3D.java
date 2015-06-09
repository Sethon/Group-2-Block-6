package surfaces;


import java.util.ArrayList;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public abstract class ParametricSurface3D implements Surface3D {
	//unit vectors
	protected static final Vector3D 	I_VECTOR = new Vector3D(1.0, 0.0, 0.0);
	protected static final Vector3D 	J_VECTOR = new Vector3D(0.0, 1.0, 0.0);
	protected static final Vector3D 	K_VECTOR = new Vector3D(0.0, 0.0, 1.0);
	
	protected double 	t0;
	protected double 	t1;
	protected double 	s0;
	protected double 	s1;
	protected int 		n1;
	protected int 		n2;
	
	public abstract double computeX(double t, double s);
	public abstract double computeY(double t, double s);
	public abstract double computeZ(double t, double s);
	
	public abstract double computeXt(double t, double s);
	public abstract double computeXs(double t, double s);
	
	public abstract double computeYt(double t, double s);
	public abstract double computeYs(double t, double s);
	
	public abstract double computeZt(double t, double s);
	public abstract double computeZs(double t, double s);
	
	public Vector3D computeR(double t, double s) {
		Vector3D a = I_VECTOR.scalarMultiply(computeX(t, s));
		Vector3D b = J_VECTOR.scalarMultiply(computeY(t, s));
		Vector3D c = K_VECTOR.scalarMultiply(computeZ(t, s));
		
		Vector3D d = a.add(b);
		
		return d.add(c);
	}
	
	public double surfaceArea() {
		return 0.0;
	}
	
	@Override
	public ArrayList<Triangle3D> triangulate() {
		ArrayList<Triangle3D> tmp = new ArrayList<>();
		for (double t = t0; t < t1 - 10E-14; t += (t1 - t0)/n1) {
	    	for (double s = s0; s < s1 - 10E-14; s += (s1 - s0)/n2) {
	    		Triangle3D tr = new Triangle3D(new Point3D(computeX(t, s), computeY(t, s), computeZ(t, s)),
	    				new Point3D(computeX(t + (t1 - t0)/n1, s), computeY(t + (t1 - t0)/n1, s), computeZ(t + (t1 - t0)/n1, s)),
	    				new Point3D(computeX(t, s + (s1 - s0)/n2), computeY(t, s + (s1 - s0)/n2), computeZ(t, s + (s1 - s0)/n2)));
	    		tmp.add(tr);
	    	}
	    }
		
		for (double t = t1; t > t0 + 10E-14; t -= (t1 - t0)/n1) {
	    	for (double s = s1; s > s0 + 10E-14; s -= (s1 - s0)/n2) {
	    		Triangle3D tr = new Triangle3D(new Point3D(computeX(t, s), computeY(t, s), computeZ(t, s)),
	    				new Point3D(computeX(t - (t1 - t0)/n1, s), computeY(t - (t1 - t0)/n1, s), computeZ(t - (t1 - t0)/n1, s)),
	    				new Point3D(computeX(t, s - (s1 - s0)/n2), computeY(t, s - (s1 - s0)/n2), computeZ(t, s - (s1 - s0)/n2)));
	    		tmp.add(tr);
	    	}
	    }
		return tmp;
	}
	
	@Override
	public ArrayList<Point3D> vertices() {
		ArrayList<Point3D> tmp = new ArrayList<>();
		for (double t = t0; t <= t1 + 10E-14; t += (t1 - t0)/n1) {
	    	for (double s = s0; s <= s1 + 10E-14; s += (s1 - s0)/n2) {
	    		tmp.add(new Point3D(computeX(t, s), computeY(t, s), computeZ(t, s)));
	    	}
		}
		return tmp;
	}
	
	@Override
	public String toString() {
		return "Parametric surface" + "\nt ∈ [" + t0 + "; " + t1 + "]" + "\ns ∈ [" + s0 + "; " + s1 + "]";
	}
}
