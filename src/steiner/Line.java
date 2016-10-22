package steiner;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Line {

	public Point a;
	public Point b;


	public Line(Point a, Point b) {
		super();
		this.a = a;
		this.b = b;
	}

	public Point intersection(Line l) {

		double x1 = a.getX();
		double y1 = a.getY();
		double x2 = b.getX();
		double y2 = b.getY();
		double x3 = l.a.getX();
		double y3 = l.a.getY();
		double x4 = l.b.getX();
		double y4 = l.b.getY();

		double d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
		if (d == 0) return null;

		double xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
		double yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;

		Point intersec = new Point();
		intersec.setLocation(xi, yi);
		return intersec;
	}
	
	public double angleBetween2Lines(Line line)
	{
	    double angle1 = Math.atan2(a.y - b.y,
	                               a.x - b.x);
	    double angle2 = Math.atan2(line.a.y - line.b.y,
	                               line.a.x - line.b.x);
	    return angle1-angle2;
	}


	public boolean estAGauche(Point c){
		double x1 = a.getX();
		double y1 = a.getY();
		double x2 = b.getX();
		double y2 = b.getY();
		double x3 = c.getX();
		double y3 = c.getY();
		return ((x2 - x1)*(y3 - y1) - (y2 - y1)*(x3 - x1)) > 0;
	}

	public Point equilateralePoint(boolean estAGauche){
		
		double x1 = a.getX();
		double y1 = a.getY();
		double x2 = b.getX();
		double y2 = b.getY();

		double longeur = a.distance(b);
		double hauteur = longeur * Math.sqrt(3)/2;


		double milieuX = x1 + (x2 - x1) * 0.5;
		double milieuY = y1 + (y2 - y1) * 0.5;

		double rapportX = (x2 - x1) / longeur;
		double rapportY = (y2 - y1) / longeur;


		double x, y;

		if(estAGauche){

			x = milieuX - hauteur * (-rapportY);
			y = milieuY - hauteur * (rapportX);
		}else{
			x = milieuX + hauteur * (-rapportY);
			y = milieuY + hauteur * (rapportX);
		}

		Point equi = new Point();
		equi.setLocation(x, y);
		return equi;


	}

	public static void main(String[] args){
		Point a, b, d;
		Point2D c;
		Line l1, l2;
		a = new Point(0,0);
		b = new Point(0,4);
		d = new Point(4, 0); 
		l1= new Line(a,b);
		l2 = new Line(a,d);
		
			Point c2 = l1.equilateralePoint(false);
		 Line cToc2 = new Line(d, c2);
		 
		 /***/
		 
		 
		 Point b2 = l2.equilateralePoint(true);
		 Line bTob2 = new Line(b, b2);
		 
		 Point intersec = cToc2.intersection(bTob2);
		 
		System.out.println(c2 +" "+b2); 
		
		System.out.println(intersec);
		
		System.out.println(l2.angleBetween2Lines(l1));
		
		

	}


}
