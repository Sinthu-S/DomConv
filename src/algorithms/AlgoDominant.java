package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AlgoDominant {

	private int edgeThreshold;

	public AlgoDominant(int edgeThreshold) {
		this.edgeThreshold = 55;
	}

	public boolean isValide(List<PointPond> points, List<PointPond> ensDominant) {
		List<PointPond> ps = new ArrayList<>(points);
		for (PointPond p : ensDominant) {
			ps.removeAll(neighbor(p, ps));
			ps.remove(p);
		}

		return ps.isEmpty();
	}

	private List<PointPond> neighbor(PointPond p, List<PointPond> vertices) {
		List<PointPond> result = new ArrayList<PointPond>();

		for (PointPond point : vertices)
			if (point.distance(p) < edgeThreshold && !point.equals(p))
				result.add((PointPond) point.clone());

		return result;
	}

	PointPond getPPC(List<PointPond> points) {
		final Comparator<PointPond> comp = (p1, p2) -> Integer.compare(neighbor(p1, points).size(),
				neighbor(p2, points).size());
		PointPond p =points.stream().max(comp).get();
		return p;
	}

	List<PointPond> getPPI(List<PointPond> points) {
		return points.stream().filter(p -> neighbor(p, points).isEmpty()).collect(Collectors.toList());
	}

	public ArrayList<PointPond> glouton(List<PointPond> points) {

		System.out.println("edgeThreshold ==" + edgeThreshold);

		List<PointPond> ps = new ArrayList<>(points);
		List<PointPond> ensDominant = getPPI(points);
		ps.removeAll(ensDominant);
		PointPond p;
		do {
			p = getPPC(ps);
			ensDominant.add(p);
			ps.removeAll(neighbor(p, ps));
			ps.remove(p);
		} while (!isValide(points, ensDominant));
		return (ArrayList<PointPond>) ensDominant;
	}

	public ArrayList<PointPond> calcul(List<PointPond> ps, ArrayList<PointPond> ensNo) {
		ArrayList<PointPond> tmpEnsNo = new ArrayList<>();
		ArrayList<PointPond> list1 = new ArrayList<>(ensNo);
		ArrayList<PointPond> list2 = new ArrayList<>(ensNo);
		tmpEnsNo.addAll(ensNo);
		PointPond p, q;
		Random rand = new Random();
		while(!list1.isEmpty()) {
			p=list1.get(rand.nextInt(list1.size()));
			while (!list2.isEmpty()) {

				q=list2.get(rand.nextInt(list2.size()));
				if (p.distance(q) <  edgeThreshold && !p.equals(q)) {

					for (PointPond k : ps) {
						tmpEnsNo.remove(p);
						tmpEnsNo.remove(q);
						tmpEnsNo.add(k);

						if (!(k.equals(p) || k.equals(q)) && ensNo.size() > tmpEnsNo.size() && isValide(ps, tmpEnsNo)) {
							return tmpEnsNo;
						}

						tmpEnsNo.add(p);
						tmpEnsNo.add(q);
						tmpEnsNo.remove(k);

					}

				}
				list2.remove(q);

			}
			list1.remove(p);

		}
		return ensNo;
	}
	//
	//	public ArrayList<PointPond> calcul(List<PointPond> ps, ArrayList<PointPond> ensNo) {
	//		ArrayList<PointPond> tmpEnsNo = new ArrayList<>();
	//		tmpEnsNo.addAll(ensNo);
	//
	//		for (PointPond p : ensNo) {
	//			for (PointPond q : ensNo) {
	//				if (p.distance(q) <  edgeThreshold && !p.equals(q)) {
	//					for (PointPond k : ps) {
	//						tmpEnsNo.remove(p);
	//						tmpEnsNo.remove(q);
	//						tmpEnsNo.add(k);
	//
	//						if (!(k.equals(p) || k.equals(q)) && ensNo.size() > tmpEnsNo.size() && isValide(ps, tmpEnsNo)) {
	//							return tmpEnsNo;
	//						}
	//
	//						tmpEnsNo.add(p);
	//						tmpEnsNo.add(q);
	//						tmpEnsNo.remove(k);
	//
	//					}
	//				}
	//			}
	//		}
	//		return ensNo;
	//	}

	public ArrayList<PointPond> localSearching(List<PointPond> points) {
		ArrayList<PointPond> ensNo = new ArrayList<>();
		ArrayList<PointPond> newEnsNo = glouton(points);

		do {
			ensNo = newEnsNo;
			newEnsNo = calcul(points, ensNo);

			System.out.print("ensNo.size() " + (ensNo.size()));
			System.out.println(", newEnsNo.size() " + (newEnsNo.size()));

		} while (ensNo.size() > newEnsNo.size());

		return ensNo;
	}

	public ArrayList<PointPond> optimsation(ArrayList<PointPond> points){
		ArrayList<PointPond> Result = glouton(points);
		ArrayList<PointPond> ResultTmp = new ArrayList<>(Result);
		ArrayList<PointPond> pointsTmp = new ArrayList<PointPond>(points);
		ArrayList<PointPond> pointExclus = new ArrayList<PointPond>();
		PointPond p;
		int bloq=0;
		Random rand = new Random();
		do {
			Result = ResultTmp;
			ResultTmp = new ArrayList<PointPond>(Result);
			pointsTmp = new ArrayList<PointPond>(points);
			Collections.shuffle(ResultTmp);
			Collections.shuffle(pointsTmp);
			ResultTmp = calculOptPos(points, Result);

			System.out.println("RESULT SIZE : " + Result.size());

		}while(Result.size() > ResultTmp.size());




		/*for (int i = 0; i < 5; i++) {
			ResultTmp = new ArrayList<PointPond>(Result);
			Collections.shuffle(ResultTmp);
			Collections.shuffle(pointsTmp);
			ResultTmp = calculOptPos(points, ResultTmp);

			if(ResultTmp != null){
				if(Result.size() > ResultTmp.size())
					Result = ResultTmp;
			}else{
				bloq++;
				if(bloq >2)
					break;
			}
			System.out.println("RESULT SIZE : " + Result.size());


		}*/
		return Result;
	}

	public ArrayList<PointPond> optimsationPlus(ArrayList<PointPond> points){
		ArrayList<PointPond> ensNo = new ArrayList<>();
		ArrayList<PointPond> newEnsNo = optimsation(points);
		ensNo = newEnsNo;
		for (int j = 0; j < 10; j++) {
			do {

				ensNo = newEnsNo;
				newEnsNo = new ArrayList<PointPond>(ensNo);
				newEnsNo = new ArrayList<PointPond>(points);
				Collections.shuffle(newEnsNo);
				Collections.shuffle(newEnsNo);
				newEnsNo = calculOptPos(points, ensNo);

				System.out.println("RESULT SIZE : " + ensNo.size());

			}while(ensNo.size() > newEnsNo.size());

		}


		return ensNo;
	}




	public ArrayList<PointPond> gloutonOpt(List<PointPond> points, ArrayList<PointPond> dominant) {



		List<PointPond> ps = new ArrayList<>(points);
		ps.removeAll(dominant);
		List<PointPond> ensDominant = new ArrayList<>(dominant);
		ps.removeAll(ensDominant);
		PointPond p;
		do {
			p = getPPC(ps);
			ensDominant.add(p);
			ps.removeAll(neighbor(p, ps));
			ps.remove(p);
		} while (!isValide(points, ensDominant));
		if(isValide(points, ensDominant))
			return (ArrayList<PointPond>) ensDominant;
		else
			return null;
	}

	private ArrayList<PointPond> neighborPlus(PointPond p, List<PointPond> vertices, int edge) {
		ArrayList<PointPond> result = new ArrayList<PointPond>();

		for (PointPond point : vertices)
			if (point.distance(p) < edge*edgeThreshold && !point.equals(p))
				result.add((PointPond) point.clone());

		return result;
	}

	public ArrayList<PointPond> calculOpt(List<PointPond> ps, ArrayList<PointPond> ensNo) {
		ArrayList<PointPond> tmpEnsNo = new ArrayList<>(ensNo);
		ArrayList<PointPond> listDom = new ArrayList<>(ensNo);
		ArrayList<PointPond> voisinDom, voisin;
		Random rand = new Random();

		rand = new Random();
		for (PointPond domA : ensNo){	

			if(domA.poid < 2000){
				voisinDom = neighborPlus(domA, ensNo, 2);
				if(voisinDom.isEmpty())
					continue;
				for (PointPond domB : voisinDom) {
					if(!domA.equals(domB)){
						voisin = neighborPlus(domB, ps, 2);
						voisin = neighborPlus(domA, voisin, 2);
						if(voisin.isEmpty())
							continue;
						for(PointPond p : voisin){
							tmpEnsNo.remove(domA);
							tmpEnsNo.remove(domB);
							tmpEnsNo.add(p);

							if (!(domB.equals(p) || domA.equals(p)) && ensNo.size() > tmpEnsNo.size() && isValide(ps, tmpEnsNo)) {

								return tmpEnsNo;
							}
							domA.poid++;
							tmpEnsNo.add(domA); 
							tmpEnsNo.add(domB);
							tmpEnsNo.remove(p);

						}
					}

				}
			}


		}
		return ensNo;
	}
	/*public ArrayList<PointPond> calculOpt(List<PointPond> ps, ArrayList<PointPond> ensNo) {
		ArrayList<PointPond> tmpEnsNo = new ArrayList<>(ensNo);
		ArrayList<PointPond> voisinDom, voisin;
		Random rand = new Random();
		PointPond domA, domB, domC;

		for (int i = 0; i < 5; i++) {	
			rand = new Random();
			domA = tmpEnsNo.get(rand.nextInt(tmpEnsNo.size()));
			if(domA.poid < 2000){
				voisinDom = neighborPlus(domA, ensNo, 2);
				if(voisinDom.isEmpty())
					continue;
				for (int j = 0; j < voisinDom.size(); j++) {
					domB = voisinDom.get(j);

					if(!domA.equals(domB)){
						voisin = neighborPlus(domB, ps, 2);
						voisin = neighborPlus(domA, voisin, 2);
						if(voisin.isEmpty())
							continue;
						for(PointPond p : voisin){
							tmpEnsNo.remove(domA);
							tmpEnsNo.remove(domB);
							tmpEnsNo.add(p);

							if (!(domB.equals(p) || domA.equals(p)) && ensNo.size() > tmpEnsNo.size() && isValide(ps, tmpEnsNo)) {

								return tmpEnsNo;
							}
							domA.poid++;
							tmpEnsNo.add(domA); 
							tmpEnsNo.add(domB);
							tmpEnsNo.remove(p);

						}
					}
				}
			}


		}
		return ensNo;
	}*/

	public ArrayList<PointPond> calculOptPos(List<PointPond> ps, ArrayList<PointPond> ensNo) {
		ArrayList<PointPond> tmpEnsNo = new ArrayList<>(ensNo);
		ArrayList<PointPond> tmpGlout, Glout;
		ArrayList<PointPond> voisinDom, voisin;
		Random rand = new Random();
		PointPond domA, domB, p;

		for (int i = 0; i < 5; i++) {	
			rand = new Random();
			domA = tmpEnsNo.get(rand.nextInt(tmpEnsNo.size()));
			if(domA.poid < 2000){
				voisinDom = neighborPlus(domA, ensNo, 2);
				if(voisinDom.isEmpty())
					continue;

				domB = voisinDom.get(rand.nextInt(voisinDom.size()));
				if(domB.poid < 2000){
					if(!domA.equals(domB)){
						voisin = neighborPlus(domB, ps, 1);
						//for(PointPond p : voisin){
						if(voisin.isEmpty())
							continue;
						p = voisin.get(rand.nextInt(voisin.size()));
						tmpEnsNo.remove(domA);
						tmpEnsNo.remove(domB);
						tmpEnsNo.add(p);

						if (!(domB.equals(p) || domA.equals(p)) && ensNo.size() > tmpEnsNo.size() && isValide(ps, tmpEnsNo)) {

							return tmpEnsNo;
						}else{
							tmpGlout = gloutonOpt(ps, tmpEnsNo);

							do {
								Glout = tmpGlout;
								tmpGlout = calculOpt(ps, Glout);

							} while (tmpGlout.size() < Glout.size());
							System.out.println("tmpGlout ; " + tmpGlout.size());
							if(tmpGlout != null){
								if(ensNo.size() > tmpGlout.size()){
									return tmpGlout;
								}
							}
						}
						domA.poid++;
						domB.poid++;
						tmpEnsNo.add(domA); 
						tmpEnsNo.add(domB);
						tmpEnsNo.remove(p);

						//}
					}
				}
			}




		}
		return ensNo;
	}


	public ArrayList<Point> calculDominatingSet(ArrayList<Point> points) {
		return PointPond.listePoint(optimsation(PointPond.listePondere(points)));
	}


}
