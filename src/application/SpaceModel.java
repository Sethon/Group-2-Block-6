package application;

import gui.Drawable;
import gui.GUIPoint;
import gui.GUITriangle;
import gui.GUITriangleColored;

import java.awt.Color;
import java.util.ArrayList;

import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import plyer.PLYWriter;
import surfaces.FVPolygonMesh;
import surfaces.Point3D;
import surfaces.Surface3D;
import surfaces.Triangle3D;

public class SpaceModel {
	private static final double PHI_X 		 = 0.05;
	private static final double PHI_Y		 = 0.05; 
	private static final double PHI_Z		 = 0.05;
	private static final double MOVE_FACTOR	 = 2;
	private static final int	ERROR	 	 = 5; 
	
	private Vector3D xVector				 = new Vector3D(1, 0, 0);
	private Vector3D yVector				 = new Vector3D(0, 0, 1); // Must be orthogonal plane of planeX
	private int x0							 = 500;
	private int y0			 				 = 500;
	private int res							 = 200;
	private ArrayList<Surface3D> surfaces;
	private ArrayList<Point3D> 	 controlPoints;
	
	public SpaceModel() {
		surfaces = new ArrayList<>();
		controlPoints = new ArrayList<>();
	}
	
	public void rotateX() {
		xVector = rotateVectorX(xVector, PHI_X);
		yVector = rotateVectorX(yVector, PHI_X);
	}
	
	public void rotateY() {
		xVector = rotateVectorY(xVector, PHI_Y);
		yVector = rotateVectorY(yVector, PHI_Y);
	}
	
	public void rotateZ() {
		xVector = rotateVectorZ(xVector, PHI_Z);
		yVector = rotateVectorZ(yVector, PHI_Z);
	}
	
	private Vector3D rotateVectorX(Vector3D v, double phi) {
		Vector3D tmp = new Vector3D(v.getX(), 
			Math.cos(phi)*v.getY() +  Math.sin(phi)*v.getZ(), 
		    -Math.sin(phi)*v.getY() + Math.cos(phi)*v.getZ());
		return tmp;
	}
	
	private Vector3D rotateVectorY(Vector3D v, double phi) {
		Vector3D tmp = new Vector3D(Math.cos(phi)*v.getX() - Math.sin(phi)*v.getZ(), 
			v.getY(), 
		    Math.sin(phi)*v.getX() + Math.cos(phi)*v.getZ());
		return tmp;
	}
	
	private Vector3D rotateVectorZ(Vector3D v, double phi) {
		Vector3D tmp = new Vector3D(Math.cos(phi)*v.getX() + Math.sin(phi)*v.getY(), 
			-Math.sin(phi)*v.getX() + Math.cos(phi)*v.getY(), 
		    v.getZ());
		return tmp;
	}
	
	public void updateOrX(double delta) {
		x0 += delta * res * MOVE_FACTOR;
	}
	
	public void updateOrY(double delta) {
		y0 += delta * res * MOVE_FACTOR;
	}
	
	public void addSurface(Surface3D s3d) {
		surfaces.add(s3d);
		controlPoints.addAll(s3d.vertices());
	}
	
	@SuppressWarnings("deprecation")
	public ArrayList<Drawable> getDrawables() {
		controlPoints = new ArrayList<>();
		
		Plane planeX = new Plane(xVector);
		Plane planeY = new Plane(yVector);
		ArrayList<Drawable> tmp = new ArrayList<>();
		
		for (Surface3D s : surfaces) {
			controlPoints.addAll(s.vertices());
			
			ArrayList<Triangle3D> triangles = s.triangulate();
			for (Triangle3D t : triangles) {
				ArrayList<Point3D> vertices = t.vertices();
				Point3D p3d1 = vertices.get(0);
				Point3D p3d2 = vertices.get(1);
				Point3D p3d3 = vertices.get(2);
				GUIPoint p1 = new GUIPoint((int) (x0 + res * planeX.getOffset(new Vector3D(p3d1.getX(), 
						p3d1.getY(), p3d1.getZ()))),
						(int) (y0 + res * planeY.getOffset(new Vector3D(p3d1.getX(), 
						p3d1.getY(), p3d1.getZ()))), 2);
				GUIPoint p2 = new GUIPoint((int) (x0 + res * planeX.getOffset(new Vector3D(p3d2.getX(), 
						p3d2.getY(), p3d2.getZ()))),
						(int) (y0 + res * planeY.getOffset(new Vector3D(p3d2.getX(), 
						p3d2.getY(), p3d2.getZ()))), 2);
				GUIPoint p3 = new GUIPoint((int) (x0 + res * planeX.getOffset(new Vector3D(p3d3.getX(), 
						p3d3.getY(), p3d3.getZ()))),
						(int) (y0 + res * planeY.getOffset(new Vector3D(p3d3.getX(), 
						p3d3.getY(), p3d3.getZ()))), 2);
				/*Color c = new Color((float)(1.0 * Math.abs(p3d1.getX())/10.0 % 1.0), 
						(float) (1.0 * Math.abs(p3d1.getY())/10.0 % 1.0), 
						(float) (1.0 * Math.abs(p3d1.getZ())/10.0 % 1.0), 
						(float) (0.2));*/
				//GUITriangleColored guiTr = new GUITriangleColored(p1, p2, p3, c); //colored mode 
				GUITriangle guiTr = new GUITriangle(p1, p2, p3);
				tmp.add(guiTr);
			}
		}
		
		/*for (Point3D p : controlPoints) {
			GUIPoint pG = new GUIPoint((int) (x0 + res * planeX.getOffset(new Vector3D(p.getX(), 
					p.getY(), p.getZ()))),
					(int) (y0 + res * planeY.getOffset(new Vector3D(p.getX(), 
					p.getY(), p.getZ()))), 2);
			tmp.add(pG);
		}*/
		
		return tmp;
	}
	
	public String getSurfaceInfo(GUIPoint pG) {
		for (Surface3D s : surfaces) {
			ArrayList<Point3D> vertices = s.vertices();
			for (Point3D p3d : vertices) {
				GUIPoint gP = projectPoint(p3d, 0);
				if ((pG.getX() <= gP.getX() + ERROR) && (pG.getX() >= gP.getX() - ERROR) 
						&& (pG.getY() <= gP.getY() + ERROR) && (pG.getY() >= gP.getY() - ERROR)) {
					return s.toString();
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	private GUIPoint projectPoint(Point3D p3d, int r) {
		Plane planeX = new Plane(xVector);
		Plane planeY = new Plane(yVector);
		GUIPoint pG = new GUIPoint((int) (x0 + res * planeX.getOffset(new Vector3D(p3d.getX(), 
				p3d.getY(), p3d.getZ()))),
				(int) (y0 + res * planeY.getOffset(new Vector3D(p3d.getX(), 
				p3d.getY(), p3d.getZ()))),r);
		return pG;
	}
	
	public void updateRes(double delta) {
		if (res > 2 && delta >= 0) {
			res = res - 2;
		}
		else if (res < 20000 && delta <= 0) {
			res = res + 2;
		}
	}
	
	//===========TEMPORARY===========
	public void addPointToMesh(Point3D p) {
		Surface3D s = surfaces.get(surfaces.size() - 1);
		FVPolygonMesh m = (FVPolygonMesh) s;
		m.addVertex(p);
	}
	
	//===========TEMPORARY===========
	public void saveMesh() {
		Surface3D s = surfaces.get(surfaces.size() - 1);
		FVPolygonMesh m = (FVPolygonMesh) s;
		PLYWriter wr = new PLYWriter();
		wr.writeFVMesh("mesh_trial", m);
	}
}
