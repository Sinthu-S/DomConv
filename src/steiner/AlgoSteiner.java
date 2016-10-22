package steiner;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AlgoSteiner {

	public Tree2D calculSteiner(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
		
		Algorithmes algo = new Algorithmes();
		ListToTree conversion = new ListToTree();
		Tree2D result, resultTmp = null;
		ArrayList<Arete> solutionKruskal = algo.kruskal(hitPoints);
		ArrayList<Arete> solutionTmp = algo.solutionAMC(solutionKruskal, edgeThreshold, points);
		ArrayList<Point> fermat = new ArrayList<Point>();
		ArrayList<Point> pointsFermatGlobal;
		double distance, newDistance;
		distance = Double.MAX_VALUE;
		
		result = conversion.krukalToTree(solutionTmp);
		System.out.println();
		newDistance = algo.countDistance(result);
		
		/*calcul de fermat **/
		
		while(newDistance < distance){
			resultTmp = result;
			distance = newDistance;
			fermat.clear();
			System.out.println(hitPoints.size());
			/*algo.optimisationFermat(result, fermat);
			algo.ajoutPoint(hitPoints, fermat);
			algo.ajoutPoint(points, fermat);*/
			solutionKruskal = Algorithmes.kruskal(hitPoints);
			solutionTmp = algo.solutionAMC(solutionKruskal, edgeThreshold, points);
			result = conversion.krukalToTree(solutionTmp);
			
			newDistance = algo.countDistance(result);
			System.out.println(newDistance);
		}
		
		result = resultTmp;
		
		distance++;
		
		
		/*pointsFermatGlobal = algo.optimisationFermatGobal(hitPoints, edgeThreshold);
		System.out.println(pointsFermatGlobal.size());
		points.addAll(pointsFermatGlobal);*/
		
		while(newDistance < distance){
			resultTmp = result;
			distance = newDistance;
			fermat.clear();
			/*algo.optimisationFermat(result, fermat);
			hitPoints.addAll(fermat);
			points.addAll(fermat);*/
			solutionKruskal = Algorithmes.kruskal(hitPoints);
			solutionTmp = algo.solutionAMC(solutionKruskal, edgeThreshold, points);
			result = conversion.krukalToTree(solutionTmp);
			
			newDistance = algo.countDistance(result);
			System.out.println(newDistance);
		}
		
		
		System.out.println(algo.countDistance(resultTmp));
		return resultTmp;
		
	}
	
public Tree2D calculSteinerBudget(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints, int budget) {
		
		Algorithmes algo = new Algorithmes();
		ListToTree conversion = new ListToTree();
		Tree2D result, resultTmp = null;
		ArrayList<Arete> solutionKruskal = algo.kruskal(hitPoints);
		ArrayList<Arete> solutionTmp = algo.solutionAMC(solutionKruskal, edgeThreshold, points);
		ArrayList<Point> fermat = new ArrayList<Point>();
		ArrayList<Point> pointsFermatGlobal;
		double distance, newDistance;
		distance = Double.MAX_VALUE;
		
		result = conversion.krukalToTree(solutionTmp);
		System.out.println();
		newDistance = algo.countDistance(result);
		
		/*calcul de fermat **/
		
		while(newDistance < distance){
			resultTmp = result;
			distance = newDistance;
			fermat.clear();
			System.out.println(hitPoints.size());
			algo.optimisationFermat(result, fermat);
			algo.ajoutPoint(hitPoints, fermat);
			algo.ajoutPoint(points, fermat);
			solutionKruskal = Algorithmes.kruskal(hitPoints);
			solutionTmp = algo.solutionAMC(solutionKruskal, edgeThreshold, points);
			result = conversion.krukalToTree(solutionTmp);
			
			newDistance = algo.countDistance(result);
			System.out.println(newDistance);
		}
		
		result = resultTmp;
		if(algo.countDistance(result) < budget)
			return result;
		distance++;
		
		
		pointsFermatGlobal = algo.optimisationFermatGobal(hitPoints, edgeThreshold);
		System.out.println(pointsFermatGlobal.size());
		points.addAll(pointsFermatGlobal);
		
		while(newDistance < distance){
			resultTmp = result;
			distance = newDistance;
			fermat.clear();
			algo.optimisationFermat(result, fermat);
			hitPoints.addAll(fermat);
			points.addAll(fermat);
			solutionKruskal = Algorithmes.kruskal(hitPoints);
			solutionTmp = algo.solutionAMC(solutionKruskal, edgeThreshold, points);
			result = conversion.krukalToTree(solutionTmp);
			
			newDistance = algo.countDistance(result);
			System.out.println(newDistance);
		}
		
		
		System.out.println(algo.countDistance(resultTmp));
		return resultTmp;
		
	}


	public Tree2D calculSteinerBudget(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
	
		ArrayList<Point> newHitPoint = new ArrayList<Point>();
		ArrayList<Point> tmpHitPoint = new ArrayList<Point>(hitPoints);
		
		int i=0;
		Tree2D arbre = null, arbreTmp = null;
		double distance = 0;
		
		Algorithmes algo = new Algorithmes();
		
		newHitPoint.add(hitPoints.get(0));
		tmpHitPoint.remove(hitPoints.get(0));
		while(distance < 1664 && i< hitPoints.size()){
			i++;
			arbreTmp=arbre;
			algo.pointLePlusProche(tmpHitPoint, newHitPoint);
			
			arbre = calculSteinerBudget(points, edgeThreshold, newHitPoint, 1664);
			distance = algo.countDistance(arbre);
			System.out.println(distance);
		}
		

		return arbreTmp;
	}




}
