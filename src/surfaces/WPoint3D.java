package surfaces;
public class WPoint3D {

     private double x;
     private double y;
     private double z;
     private double weight;

     public WPoint3D(double x, double y, double z, double weight) {
          this.x = x; this.y = y; this.z = z; this.weight = weight;
     }

     public double getWeight() {
          return weight;
     }

     public void setWeight(double weight) {
          this.weight = weight;
     }

     public double getZ() {
          return z;
     }

     public void setZ(double z) {
          this.z = z;
     }

     public double getY() {
          return y;
     }

     public void setY(double y) {
          this.y = y;
     }

     public double getX() {
          return x;
     }

     public void setX(double x) {
          this.x = x;
     }

     public WPoint3D multiply(double factor) {
          return new WPoint3D(getX() * factor, getY() * factor, getZ() * factor, getWeight() * factor);
     }

     public WPoint3D add(double x, double y, double z, double weight) {
          return new WPoint3D(
                                      getX() + x,
                                      getY() + y,
                                      getZ() + z,
                                      getWeight() + weight);
     }

     public WPoint3D add(WPoint3D point) {
          return add(point.getX(), point.getY(), point.getZ(), point.getWeight());
     }

     public Point3D convert() {
          return new Point3D(x/weight, y/weight, z/weight);
     }

     public String toString() {
          return "[" + x + "][" + y + "][" + z + "][" + weight + "]";
     }
}