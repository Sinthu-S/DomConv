package bleublack;

import java.awt.Point;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

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
			if (point.distance(p) < edgeThreshold && !point.equals(p) ){
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

	private void whiteToGrey(ArrayList<PointColor> whites) {
		

		for (PointColor point : whites)
			point.color =Color.GREY;

		
	}

	private ArrayList<PointColor> neighborWhite(PointColor p, ArrayList<PointColor> UDG) {
		ArrayList<PointColor> result = new ArrayList<PointColor>();

		for (PointColor point : UDG)
			if (point.distance(p) < edgeThreshold  && !point.equals(p) &&  point.color == Color.WHITE){
				//point.color=Color.GREY;
				result.add(point);

			}

		return result;
	}

	private PointColor getPPC(ArrayList<PointColor> points) {
		final Comparator<PointColor> comp = (p1, p2) -> Integer.compare(neighbor(p1, points).size(),
				neighbor(p2, points).size());
		PointColor p =points.stream().max(comp).get();
		return p;
	}

	private ArrayList<PointColor> getMIS(ArrayList<PointColor> UDG){
		ArrayList<PointColor> ps = new ArrayList<>(UDG);
		ArrayList<PointColor> MIS = new ArrayList<>();
		Random rand = new Random();
		PointColor p;
		do{
			p=getPPC(ps);
			p.color=Color.BLACK;
			MIS.add(p);
			ps.remove(p);
			ps.removeAll(this.neighborGrey(p, UDG));

		}while(!ps.isEmpty());

		return MIS;

	}

	private ArrayList<PointColor> getMIS2(ArrayList<PointColor> UDG){
		ArrayList<PointColor> ps = new ArrayList<>(UDG);
		ArrayList<PointColor> MIS = new ArrayList<>();
		ArrayList<PointColor> grey = new ArrayList<>();
		ArrayList<PointColor> white = new ArrayList<>();
		ArrayList<PointColor> whiteM = new ArrayList<>();
		int max=0;
		PointColor p = getPPC(ps);
		p.color=Color.BLACK;
		MIS.add(p);
		ps.remove(p);
		grey.addAll(this.neighborGrey(p, UDG));
		ps.removeAll(this.neighborGrey(p, UDG));

		do{
			System.out.println(p);
			max=0;
			p=null;
			for(PointColor g : grey){
				for(PointColor w : this.neighborWhite(g, UDG)){
					white = this.neighborWhite(w, UDG);
					if(max < white.size()){
						max = white.size();
						whiteM = white;
						p = w;
					}
				}
			}
			if(p != null){
				p.color=Color.BLACK;
				this.whiteToGrey(whiteM);
				System.out.println(whiteM.contains(p));
				
				MIS.add(p);
				//ps.remove(p);
				grey.addAll(whiteM);
				//ps.removeAll(whiteM);
			}

		}while(p != null);

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

		for (PointColor point : MIS)
			if (point.distance(p) < edgeThreshold)
				result.add(point);

		for (PointColor p1 : result)
			for (PointColor p2 : result)
				if(p2._id != p1._id)
					return result;



		//		Set<PointColor> s = new HashSet<>(result);
		//		if(s.size() > 1)
		//			return result;
		//		System.out.println("null");
		return new ArrayList<>();
	}

	private PointColor existeVoisin(ArrayList<PointColor> grey, int nb, ArrayList<PointColor> uDG){
		ArrayList<PointColor> result = new ArrayList<>();
		for(PointColor p : uDG){
			if(p.color == Color.GREY){
				result = this.neighborBlack(p);

				if(result.size() >= nb){
					this.transforme(p, result);
					return p;
				}
			}
		}

		if(!result.isEmpty())
			System.out.println("taille " + result.size());

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
		p._id=min;


	}



	private ArrayList<PointColor> getSMIS(ArrayList<PointColor> UDG){
		ArrayList<PointColor> greyPoint = this.getGrey(UDG);//OK
		ArrayList<PointColor> bleuPoint = new ArrayList<>();
		PointColor pBleu;
		SMIS.addAll(MIS);

		for (int i = 5; i > 1; i--) {
			while((pBleu =this.existeVoisin(greyPoint, i, UDG)) != null){
				greyPoint.remove(pBleu);
				SMIS.add(pBleu);
				if(greyPoint.isEmpty())
					System.out.println("vide");

			}
			System.out.println(bleuPoint.size());

		}

		System.out.println(UDG.size());



		MIS.addAll(bleuPoint);
		if(isValide())
			System.out.println("is valide");

		return SMIS;

	}

	private boolean existeGrey(ArrayList<PointColor> ps){
		for(PointColor p : ps)
			if(p.color == Color.GREY)
				return true;
		return false;
	}

	private boolean isValide(){
		int id = SMIS.get(0)._id;
		for(PointColor p : SMIS)
			if(id != p._id)
				return false;
		return true;
	}

	public ArrayList<Point> calculSMIS(ArrayList<Point> points) {
		ArrayList<PointColor> ps = PointColor.listePointColor(points);

		//MIS = this.getMIS2(ps);
		//System.out.println(MIS.size());
		//SMIS = new ArrayList<>();

		return PointColor.listePoint(this.getMIS2(ps));
	}



}
