package steiner;

import java.awt.Point;
import java.util.ArrayList;

public class Arete implements Comparable<Arete>{
	public Point a;
	public Point b;
	
	public Arete(Point a, Point b) {
		super();
		this.a = a;
		this.b = b;
	}
	
	public double tailleArete(){
		return a.distance(b);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arete other = (Arete) obj;
			if(a.equals(other.a) && b.equals(other.b))
				return true;
			if(a.equals(other.b) && b.equals(other.a))
				return true;
			return false;
	}
	


	@Override
	public int compareTo(Arete o) {
		double p, q;
		p = this.tailleArete();
		q = o.tailleArete();
		if(p == q)
			return 0;
		else if(p > q)
			return -1;
		else
			return 1;
	}
	
	public boolean extremite(Point p){
		if(a.equals(p) || b.equals(p))
			return true;
		return false;
	}
	
	public Point getOtherExtremitie(Point p){
		if(a.equals(p))
			return b;
		if(b.equals(p))
			return a;
		return null;
	}
	
	
	
	
	
}

	
