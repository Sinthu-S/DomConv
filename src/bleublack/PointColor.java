package bleublack;

import java.awt.Point;
import java.util.ArrayList;

import algorithms.PointPond;

public class PointColor extends Point{
	public int _id;
	public Color color;
	
	public PointColor(Point p, int i) {
		super(p);
		_id = i;
		color = Color.WHITE;
		
	}

	public static ArrayList<PointColor> listePointColor(ArrayList<Point> points){
		ArrayList<PointColor> list = new ArrayList<PointColor>();
		for(Point p:points){
			list.add(new PointColor(p, points.indexOf(p)));
		}
		return list;
	}
	
	@Override
	public String toString() {
		return "PointColor [_id=" + _id + ", color=" + color + "]";
	}

	public static ArrayList<Point> listePoint(ArrayList<PointColor> points){
		ArrayList<Point> list = new ArrayList<Point>();
		for(PointColor p:points){
		list.add(new Point(p.x, p.y));
			
		}
		return list;
	}
	
	@Override
	public int hashCode() {
		return _id;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PointColor other = (PointColor) obj;
		if (_id != other._id)
			return false;
		return true;
	}
	
	

}
