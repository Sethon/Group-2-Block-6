package surfaces;


import java.util.ArrayList;

public abstract class ParametricSurface3D implements Surface3D {
	//unit vectors
	protected static final double[] 	I_VECTOR = new double[] {1.0, 0.0, 0.0};
	protected static final double[] 	J_VECTOR = new double[] {0.0, 1.0, 0.0};
	protected static final double[] 	K_VECTOR = new double[] {0.0, 0.0, 1.0};
	
	protected double 	t0;
	protected double 	t1;
	protected double 	s0;
	protected double 	s1;
	protected int 		n1;
	protected int 		n2;
	
	public abstract double computeX(double t, double s);
	public abstract double computeY(double t, double s);
	public abstract double computeZ(double t, double s);
	
	public double[] computeR(double t, double s) {
		double[] r = new double[3];
		
		for (int i = 0; i < 3; i++) {
			r[i] = computeX(t, s) * I_VECTOR[i] + 
					computeY(t, s) * J_VECTOR[i] + computeZ(t, s) * K_VECTOR[i];
		}
		
		return r;
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
