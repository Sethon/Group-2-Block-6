package surfaces;

import java.util.ArrayList;

public class FVPolygonMesh extends PolygonMesh {

	private ArrayList<Triangle3D> 				faces;
	private ArrayList<ArrayList<Triangle3D>> 	verticesToFaces;
	
	public FVPolygonMesh(ArrayList<Point3D> vertices, ArrayList<Triangle3D> faces,
			ArrayList<ArrayList<Triangle3D>> verticesToFaces) {
		super.vertices = vertices;
		this.faces = faces;
		this.verticesToFaces = verticesToFaces;
	}
	
	public FVPolygonMesh() {
		vertices = new ArrayList<>();
		faces = new ArrayList<>();
		verticesToFaces = new ArrayList<ArrayList<Triangle3D>>();
	}
	
	@Override
	public ArrayList<Triangle3D> triangulate() {
		ArrayList<Triangle3D> tmp = new ArrayList<>();
		if (vertices.size() == 1) {
			tmp.add(new Triangle3D(vertices.get(0), vertices.get(0), vertices.get(0)));
			return tmp;
		}
		else if (vertices.size() == 2) {
			tmp.add(new Triangle3D(vertices.get(0), vertices.get(1), vertices.get(0)));
			return tmp;
		} else {
			return faces;
		}
	}

	@Override
	public void addVertex(Point3D p) {
		if (vertices.size() <= 1) {
			vertices.add(p);
			verticesToFaces.add(new ArrayList<>());
		} else {
			ArrayList<Integer> tmp = find2ClosestPs(p);
			vertices.add(p);
			Triangle3D newFace = new Triangle3D(p, vertices.get(tmp.get(0)), 
					vertices.get(tmp.get(1)));
			faces.add(newFace);
			verticesToFaces.get(tmp.get(0)).add(newFace);
			verticesToFaces.get(tmp.get(1)).add(newFace);
			verticesToFaces.add(new ArrayList<>());
			verticesToFaces.get(verticesToFaces.size() - 1).add(newFace);
		}
	}
	
	private ArrayList<Integer> find2ClosestPs(Point3D p) {
		Point3D v1 = vertices.get(0);
		Point3D v2 = vertices.get(1);
		int v1Ind = 0;
		int v2Ind = 1;
		
		double diffV1 = Integer.MAX_VALUE;
		double diffV2 = Integer.MAX_VALUE;
		
		for (int i = 0; i < vertices.size(); i++) {
			double tmp = p.dist(vertices.get(i));
			if (tmp <= diffV1) {
				diffV1 = tmp;
				v1 = vertices.get(i);
				v1Ind = i;
			}
		}
		
		for (int i = 0; i < vertices.size(); i++) {
			double tmp = p.dist(vertices.get(i));
			if (tmp <= diffV2 && !vertices.get(i).equals(v1)) {
				diffV2 = tmp;
				v2 = vertices.get(i);
				v2Ind = i;
			}
		}
		
		ArrayList<Point3D> res1 = new ArrayList<Point3D>();
		res1.add(v1);
		res1.add(v2);
		
		
		ArrayList<Integer> res2 = new ArrayList<>();
		res2.add(v1Ind);
		res2.add(v2Ind);
		
		return res2;
	}
	
	@Override
	public String toString() {
		return "Face-Vertex Polygon Mesh";
	}

}
