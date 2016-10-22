package bleublack;

import java.awt.Point;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import algorithms.PointPond;


public class BleuBlack {
	int edgeThreshold;
	ArrayList<PointColor> MIS;
	ArrayList<PointColor> SMIS;

	public BleuBlack(int edgeThreshold) {
		super();
		this.edgeThreshold = edgeThreshold;
	}
	
	private ArrayList<PointColor> neighbor(PointColor p, ArrayList<PointColor> UDG) {
		ArrayList<PointColor> result = new ArrayList<PointColor>();

		for (PointColor point : UDG)
			if (point.distance(p) < edgeThreshold && !point.equals(p)){
				point.color=Color.GREY;
				result.add(point);

			}

		return result;
	}

	private ArrayList<PointColor> neighborGrey(PointColor p, ArrayList<PointColor> UDG) {
		ArrayList<PointColor> result = new ArrayList<PointColor>();

		for (PointColor point : UDG)
			if (point.distance(p) < edgeThreshold && !point.equals(p)){
				point.color=Color.GREY;
				result.add(point);

			}

		return result;
	}

	private ArrayList<PointColor> getMIS(ArrayList<PointColor> UDG){
		ArrayList<PointColor> ps = new ArrayList<>(UDG);
		ArrayList<PointColor> MIS = new ArrayList<>();
		Random rand = new Random();
		PointColor p;
		do{
			p=ps.get(rand.nextInt(ps.size()));
			p.color=Color.BLACK;
			MIS.add(p);
			ps.remove(p);
			ps.removeAll(this.neighborGrey(p, UDG));

		}while(!ps.isEmpty());

		return MIS;

	}

	private ArrayList<PointColor> getGrey(ArrayList<PointColor> UDG){
		ArrayList<PointColor> grey = new ArrayList<>();
		for(PointColor p : UDG)
			if(p.color ==  Color.GREY)
				grey.add(p);
		return grey;
	}

	private ArrayList<PointColor> neighborBlack(PointColor p){
		ArrayList<PointColor> result = new ArrayList<PointColor>();
		int i=0;
		for (PointColor point : MIS)
			if (point.distance(p) < edgeThreshold)
				result.add(point);
		
		for (PointColor p1 : result)
			for (PointColor p2 : result)
				if(p2._id != p1._id)
					if(++i > 1)
						return result;
				
			
			
//		Set<PointColor> s = new HashSet<>(result);
//		if(s.size() > 1)
//			return result;
//		System.out.println("null");
		return null;
	}

	private PointColor existeVoisin(ArrayList<PointColor> grey, int nb, ArrayList<PointColor> uDG){
		ArrayList<PointColor> result;
		for(PointColor p : grey){
			result = this.neighborBlack(p);
			if(result != null)
				if(result.size() == nb){
					this.transforme(p, result);
					return p;
				}

		}

		return null;


	}
	
	private ArrayList<PointColor> getListByID(int id){
		ArrayList<PointColor> result = new ArrayList<>();
		for(PointColor p : MIS)
			if(p._id == id)
				result.add(p);
		return result;
	}

	private void transforme(PointColor p, ArrayList<PointColor> result){

		ArrayList<ArrayList<PointColor>> grille = new ArrayList<>();
		PointColor b = null;
		p.color = Color.BLEU;
		int min=Integer.MAX_VALUE;
		
		for(PointColor black : result){
			if(black._id<min){
				min=black._id;
				b=black;
						
			}
			
			grille.add(this.getListByID(black._id));
		}
		for(int i=0; i<grille.size(); i++){
			if(grille.get(i).contains(b))
				continue;
			for(PointColor dom : grille.get(i)){
				dom._id=min;
			}
		}
			

	}



	private ArrayList<PointColor> getSMIS(ArrayList<PointColor> UDG){
		ArrayList<PointColor> greyPoint = this.getGrey(UDG);//OK
		ArrayList<PointColor> bleuPoint = new ArrayList<>();
		PointColor pBleu;
		SMIS.addAll(MIS);
		for (int i = 5; i > 1; i--) {
			while((pBleu =this.existeVoisin(greyPoint, i, UDG)) != null){
				greyPoint.remove(pBleu);
				bleuPoint.add(pBleu);
			}

		}

		MIS.addAll(bleuPoint);
		return MIS;

	}

	public ArrayList<Point> calculSMIS(ArrayList<Point> points) {
		ArrayList<PointColor> ps = PointColor.listePointColor(points);
		MIS = this.getMIS(ps);
		SMIS = new ArrayList<>();
		return PointColor.listePoint(this.getSMIS(ps));
	}



}
