package algorithms;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PointPond extends Point{
	int poid;

	public PointPond(Point p) {
		super(p);
		poid=0;
	}
	
	public static ArrayList<PointPond> listePondere(ArrayList<Point> points){
		ArrayList<PointPond> list = new ArrayList<PointPond>();
		for(Point p:points){
			list.add(new PointPond(p));
		}
		return list;
	}
	
	public static ArrayList<Point> listePoint(ArrayList<PointPond> points){
		ArrayList<Point> list = new ArrayList<Point>();
		for(PointPond p:points){
		list.add(new Point(p.x, p.y));
			
		}
		return list;
	}

	@Override
	public String toString() {
		return "PointPond [poid=" + poid + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + poid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PointPond other = (PointPond) obj;
		if (poid != other.poid)
			return false;
		return true;
	}
	
	
}


