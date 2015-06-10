package surfaces;


import java.util.ArrayList;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public abstract class ParametricSurface3D implements Surface3D {
	//unit vectors
	protected static final Vector3D 	I_VECTOR = new Vector3D(1.0, 0.0, 0.0);
	protected static final Vector3D 	J_VECTOR = new Vector3D(0.0, 1.0, 0.0);
	protected static final Vector3D 	K_VECTOR = new Vector3D(0.0, 0.0, 1.0);
	private static final int   INTEGRATION_STEPS = 100;
	
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
	
	public Vector3D computeRt(double t, double s) {
		Vector3D a = I_VECTOR.scalarMultiply(computeXt(t, s));
		Vector3D b = J_VECTOR.scalarMultiply(computeYt(t, s));
		Vector3D c = K_VECTOR.scalarMultiply(computeZt(t, s));
		
		Vector3D d = a.add(b);
		
		return d.add(c);
	}
	
	public Vector3D computeRs(double t, double s) {
		Vector3D a = I_VECTOR.scalarMultiply(computeXs(t, s));
		Vector3D b = J_VECTOR.scalarMultiply(computeYs(t, s));
		Vector3D c = K_VECTOR.scalarMultiply(computeZs(t, s));
		
		Vector3D d = a.add(b);
		
		return d.add(c);
	}
	
	//IIr||RtxRs||dtds
	public double surfaceArea() {
		double[] It = new double[INTEGRATION_STEPS + 1]; //Divide s in 100 parts
		double stepsize = (t1-t0)/INTEGRATION_STEPS;
		for(int i = 0; i<It.length; i++) {
			double valS = s0 + (s1-s0)/INTEGRATION_STEPS*i;
			It[i] = trapezoid(stepsize,valS,t0,t1);
		}
		double Is = simpson(It, (s1 - s0)/INTEGRATION_STEPS);
		return Is;
	}
	
	private double trapezoid(double step, double x, double t0, double tn) {
		double I = 0;
		for(double i = t0; i <= tn + 10E-14; i+=step) {
			if(i==t0 || (i <= tn + 10E-14 && i >= tn - 10E-14)) {
				Vector3D Rt = computeRt(i,x); 
				Vector3D Rs = computeRs(i,x);
				Vector3D R = Rt.crossProduct(Rs);
				double norm = R.getNorm();
				I += 0.5*norm;
			}
			else {
				Vector3D Rt = computeRt(i,x); 
				Vector3D Rs = computeRs(i,x);
				Vector3D R = Rt.crossProduct(Rs);
				double norm = R.getNorm();
				I += norm;
			}
				
		}
		return step * I;
	}
	
	private double simpson(double[] It, double stepsize) {
		double Is = 0;
		for(int i = 0; i <It.length; i++) {
			if(i==0 || i==It.length-1) {
				Is += It[i];
			}
			else if(i%2 == 0) {
				Is += (2*It[i]);
			}
			else {
				Is += (4*It[i]);
			}
		}
		Is = Is * (stepsize/3);
		return Is;
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
