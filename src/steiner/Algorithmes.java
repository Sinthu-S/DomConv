	package steiner;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;





public class Algorithmes {
	
	
	/*les plus court chemin*/
	static public int[][] calculShortestPaths(ArrayList<Point> points, int edgeThreshold) {
	     
	    double distTmp;
	    int dist;
	    int taille = points.size();
	    System.out.println("Taille : "+points.size());
	    int[][] tailleLiaison= new int[taille][taille];
	    int[][] paths=new int[taille][taille];
	   
	    for (int i=0;i<paths.length;i++){
	        for (int j=0;j<paths.length;j++){
	            paths[i][j]=j;
	            distTmp = points.get(i).distance(points.get(j));
	            if(distTmp < edgeThreshold){
	                tailleLiaison[i][j] = 1;
	            }else{
	                tailleLiaison[i][j] = edgeThreshold;
	               
	            }
	        }
	    }

	    for(int k=0; k<taille; k++){
	        for (int i=0; i<taille; i++){
	            for (int j=0; j<taille; j++) {
	                    dist = tailleLiaison[i][k] + tailleLiaison[k][j];
	                    if(tailleLiaison[i][j] > dist ){
	                        tailleLiaison[i][j] = dist;
	                        paths[i][j]= paths[i][k];
	                    }

	            }

	        }
	    }

	    return paths;
	}
	
	
	 static public ArrayList<Arete> kruskal(ArrayList<Point> points){

			int taille = points.size();
			int[] tabEtiquette = new int[taille];
			ArrayList<Arete> aretes = new ArrayList<Arete>();
			Arete tmpArete;

			ArrayList<Arete> solution = new ArrayList<Arete>();
			for(int i=0; i<taille; i++)
				tabEtiquette[i]=i;
			
			System.out.println("Fin intialisation etiquette ");

			for(int i=0; i<taille; i++){
				for (int j=0; j<taille; j++) {
					if(i!=j){
						tmpArete = new Arete(points.get(i), points.get(j));
						aretes.add(tmpArete);
					}
				}
			}
			
			
			Collections.sort(aretes, Collections.reverseOrder());
			System.out.println("Fin tri arete");
			
			int posA, posB, tmpVal;
			for(int i=0; i<aretes.size(); i++){
				tmpArete=aretes.get(i);
				//System.out.println(tmpArete.tailleArete());
				posA = points.indexOf(tmpArete.a);
				posB = points.indexOf(tmpArete.b);

				if(tabEtiquette[posA] != tabEtiquette[posB]){
					solution.add(tmpArete);
					tmpVal = tabEtiquette[posA];
					for(int j=0; j<taille; j++){
						if(tabEtiquette[j]== tmpVal)
							tabEtiquette[j]=tabEtiquette[posB];
					}
							

				}
				//System.out.println(Arrays.toString(tabEtiquette));

			}
			taille = solution.size();
			System.out.println("Fin solution arete");
			//System.out.println(taille);
			
			return solution;
	 }
	 
	 public ArrayList<Arete> solutionAMC(ArrayList<Arete> solutionKruskal, int edgeThreshold, ArrayList<Point> points){
		 
		 Arete areteTmp;
		 ArrayList<Arete> solutionTmp = new ArrayList<Arete>(solutionKruskal);
		 int posA, posB, posTmp;
		 int[][] paths = calculShortestPaths(points, edgeThreshold);
		 for(int i=0; i<solutionKruskal.size(); i++){
				areteTmp =solutionKruskal.get(i);
				if(areteTmp.tailleArete() >= edgeThreshold){
					solutionTmp.set(i, null);
					posA = points.indexOf(areteTmp.a);
					posB = points.indexOf(areteTmp.b);
					while(posA != posB){
						//System.out.println(posA + " " + posB);
						posTmp = posA;
						posA = paths[posA][posB];
						solutionTmp.add(new Arete(points.get(posTmp), points.get(posA)));
					}

				}
			}

			solutionTmp.removeAll(Collections.singleton(null));
			return solutionTmp;
	 }
	 public Point pointFermat(Point a, Point b, Point c){
		 Line l1, l2, l3;
		 
		 l1 = new Line(a, b);
		 l2 = new Line(a, c);
		 l3 = new Line(b, c);
		 
		 Point c2 = l1.equilateralePoint(l1.estAGauche(c));
		 Line cToc2 = new Line(c, c2);
		 
		 /***/
		 Point b2 = l2.equilateralePoint(l2.estAGauche(b));
		 Line bTob2 = new Line(b, b2);
		 
		 Point intersec = cToc2.intersection(bTob2);
		 
		 return intersec;
	 }
	 
	 public void optimisationFermat(Tree2D arbre, ArrayList<Point> pointsFeramt){
		 
		 Point racine = arbre.getRoot();
		 ArrayList<Tree2D> fils = arbre.getSubTrees();
		 
		 if(fils.isEmpty())
			 return;
		 
		 if(fils.size() == 1){
			 optimisationFermat(fils.get(0) , pointsFeramt);
		 }else{
			 Point b=fils.get(0).getRoot();
			 Point c=fils.get(1).getRoot();
			 
			 Point insection = pointFermat(racine, b, c);
			 
			 pointsFeramt.add(insection);
			 
			 for(int i=0; i<fils.size(); i++)
				 optimisationFermat(fils.get(i),pointsFeramt);
			 
		 }
	 }
	 
	 public void optimisationFermat(Tree2D arbre){
		 
		 Point racine = arbre.getRoot();
		 ArrayList<Tree2D> fils = arbre.getSubTrees();
		 
		 if(fils.isEmpty())
			 return;
		 
		 if(fils.size() == 1){
			 optimisationFermat(fils.get(0));
		 }else{
			 Point b=fils.get(0).getRoot();
			 Point c=fils.get(1).getRoot();
			 
			 Point insection = pointFermat(racine, b, c);
			 Tree2D fermat = new Tree2D(insection, new ArrayList<Tree2D>());
			 
			 fermat.getSubTrees().add(fils.get(0));
			 fermat.getSubTrees().add(fils.get(1));
			 
			 fils.set(0, fermat);
			 fils.remove(1);
			 
			 for(int i=1; i<fils.size(); i++)
				 optimisationFermat(fils.get(i));
			 
			 optimisationFermat(fermat.getSubTrees().get(0));
			 optimisationFermat(fermat.getSubTrees().get(1));
		 }
			 
		 
	 }
	 
	 public ArrayList<Point> optimisationFermatGobal(ArrayList<Point> pointKruskal, int edge){
		
		 ArrayList<Point> pointsFermat = new ArrayList<Point>();
		 Point a, b, c;
		 Point p = null;
		 for (int i = 0; i < pointKruskal.size(); i++) {
			for (int j = 0; j < pointKruskal.size(); j++) {
				for (int k = 0; k < pointKruskal.size(); k++) {
					a = pointKruskal.get(i);
					b = pointKruskal.get(j);
					c = pointKruskal.get(k);
					if(a.distance(b) < 5*edge && a.distance(c) < 5*edge){
						p = pointFermat(a,b,c);
						if(!pointsFermat.contains(p))
							pointsFermat.add(p);
					}
				}
				
			}
		}
		 
		 pointsFermat.removeAll(Collections.singleton(null));
		 return pointsFermat;
		 
	 }
	 
	 public double countDistance(Tree2D arbre){
		 
		 double distance = 0;
		 Point racine = arbre.getRoot();
		 ArrayList<Tree2D> fils = arbre.getSubTrees();
		 if(fils.isEmpty())
			 return 0;
		 for(int i=0; i<fils.size(); i++){
			 distance += racine.distance(fils.get(i).getRoot());
			 distance += countDistance(fils.get(i));
		 }
		 
		 return distance;
	 }
	 
	 public void tree2DToPoint(Tree2D arbre, ArrayList<Point> points){
		 points.add(arbre.getRoot());
		 
		 for(int i=0; i<arbre.getSubTrees().size(); i++)
			 tree2DToPoint(arbre.getSubTrees().get(i),points);
			 
	 }
	 
	 public void pointLePlusProche(ArrayList<Point> points,  ArrayList<Point> pointsRef){
		 double distance= Double.MAX_VALUE;
		 Point solution = null;
		 Point ref;
		 for(int i=0; i<pointsRef.size(); i++){
			 for(int j=0; j<points.size(); j++){
				 ref = pointsRef.get(i);
				 if(ref.distance(points.get(j)) < distance){
					 distance= ref.distance(points.get(j));
					 solution = points.get(j);
				 }
			 }
			
		 }
		 points.remove(solution);
		 pointsRef.add(solution);
	 }
	 
	 public void ajoutPoint(ArrayList<Point> list,  ArrayList<Point> points){
		 
		 for(int i=0; i<points.size(); i++){
			 if(!list.contains(points.get(i)))
				 list.add(points.get(i));
		 }
	 }
	 
	

}
