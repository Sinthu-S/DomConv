package rand;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import algorithms.AlgoDominant;
import bleublack.BleuBlack;
import bleublack.PointColor;
import steiner.AlgoSteiner;
import steiner.Tree2D;

public class RandomGen {
	
	private BigInteger rand(BigInteger a, BigInteger c, BigInteger m, BigInteger graine){
		BigInteger val = graine.multiply(a);
		val = val.add(c);
		return val.mod(m);
	}
	
	private void rand48(BigInteger graine){
		for (int i = 0; i < 10; i++) {
			graine = rand(new BigInteger("25214903917"), new BigInteger("11"), new BigInteger("2").pow(48), graine);
			System.out.println(graine);
		}
	}
	
	public ArrayList<Point> rand(BigInteger graine, int nbPoint){
		BigInteger val = null;
		ArrayList<Point> result = new ArrayList<>();
		int x = 0;
		int bitFort;
		nbPoint *= 2;
		for (int i = 1; i <= nbPoint; i++) {
			graine = rand(new BigInteger("25214903917"), new BigInteger("11"), new BigInteger("2").pow(48), graine);
			val = graine.divide(new BigInteger("2").pow(16));
			bitFort = val.divide(new BigInteger("2").pow(31)).intValue();
			//System.out.println(bitFort);
			if(bitFort == 0){
				val = val.mod(new BigInteger("2").pow(10));
			}
			else{
				//System.out.println("fort");
				val = val.add(graine);
				val = val.mod(new BigInteger("2").pow(10));
				val = val.add(new BigInteger("100"));
			}
			if(i%2 != 0)
				x = val.intValue();
			else
				result.add(new Point(x, val.intValue()));
			
		}
		return result;
	}
	
	private void printToFile(String filename,ArrayList<Point> points){
		try {
			PrintStream output = new PrintStream(new FileOutputStream(filename));
			for (Point p:points) output.println(Integer.toString((int)p.getX())+" "+Integer.toString((int)p.getY()));
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("I/O exception: unable to create "+filename);
		}
	}
	
	public void genPointsToFile(BigInteger graine, int nbPoint, int nbFile, String fileName){
		ArrayList<Point> points;
		

		for(int j = 0; j<nbFile; j++){
			
			points = rand48Valide(nbPoint);
			System.out.println("SIZEEE = "+points.size());
			printToFile(fileName+j, points);
		}
	}
	
	
	/*Generation de nbFile fichier contenant chacun 1000 points aleatoire */
	/*public void genPointsToFile(BigInteger graine, int nbFile, String fileName){
		BigInteger val = null;
		BigInteger x = null;
		BigInteger y = null;
		
		Random rand = new Random(System.currentTimeMillis());
		int bitFort;

		for(int j = 0; j<nbFile; j++){
			PrintStream output;
			try {
				output = new PrintStream(new FileOutputStream(fileName+j));

				for (int i = 0; i < 1000; i++) {
					graine = rand(new BigInteger("25214903917"), new BigInteger("11"), new BigInteger("2").pow(48), graine);
					//System.out.println(graine);
					val = graine.divide(new BigInteger("2").pow(16));
					bitFort = val.divide(new BigInteger("2").pow(31)).intValue();
					//System.out.println(bitFort);
					if(bitFort == 0){
						val = val.mod(new BigInteger("2").pow(10));
					}
					else{
						//System.out.println("fort");
						val = val.add(graine);
						val = val.mod(new BigInteger("2").pow(10));
						val = val.add(new BigInteger("100"));
					}
					x=val;
					
					graine = rand(new BigInteger("25214903917"), new BigInteger("11"), new BigInteger("2").pow(48), graine);
					//System.out.println(graine);
					val = graine.divide(new BigInteger("2").pow(16));
					bitFort = val.divide(new BigInteger("2").pow(31)).intValue();
					//System.out.println(bitFort);
					if(bitFort == 0){
						val = val.mod(new BigInteger("2").pow(10));
					}
					else{
						//System.out.println("fort");
						val = val.add(graine);
						val = val.mod(new BigInteger("2").pow(10));
						val = val.add(new BigInteger("100"));
					}
					y=val;
					if(i==999) output.print(x+" "+y);
					else output.println(x+" "+y);
				}
				output.close();

			} catch (FileNotFoundException e) {
				System.err.println("I/O exception: unable to create "+fileName+j);
			}
		}				
	}*/
	
	//FILE LOADER
	private ArrayList<Point> readFromFile(String filename) {
		String line;
		String[] coordinates;
		ArrayList<Point> points=new ArrayList<Point>();
		try {
			BufferedReader input = new BufferedReader(
					new InputStreamReader(new FileInputStream(filename))
					);
			try {
				while ((line=input.readLine())!=null) {
					coordinates=line.split("\\s+");
					points.add(new Point(Integer.parseInt(coordinates[0]),
							Integer.parseInt(coordinates[1])));
				}
			} catch (IOException e) {
				System.err.println("Exception: interrupted I/O.");
			} finally {
				try {
					input.close();
				} catch (IOException e) {
					System.err.println("I/O exception: unable to close "+filename);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Input file not found.");
		}
		return points;
	}
	
	public void testInstance(String resultFilename, int nbFile, String chemin){
		ArrayList<Point> points = null;
		try {
			PrintStream output = new PrintStream(new FileOutputStream("./resultatsInstances/"+resultFilename));
			PrintStream outputTime = new PrintStream(new FileOutputStream("./resultatsInstances/"+resultFilename+"Time"));

			
		for(int i=0; i<nbFile; i++){
			long debut = System.currentTimeMillis();
			points = readFromFile(chemin+i);
			BleuBlack  algo = new BleuBlack(55);
			output.println(algo.calculSMIS(points).size());
			outputTime.println(System.currentTimeMillis()-debut);
		}
		output.close();
		outputTime.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testInstanceSteiner(String resultFilename, int nbFile, String chemin){
		ArrayList<Point> points = null;
		try {
			PrintStream output = new PrintStream(new FileOutputStream("./resultatsInstancesSteiner/"+resultFilename));
			PrintStream outputTime = new PrintStream(new FileOutputStream("./resultatsInstancesSteiner/"+resultFilename+"Time"));

			
		for(int i=0; i<nbFile; i++){
			long debut = System.currentTimeMillis();
			points = readFromFile(chemin+i);
			ArrayList<Point> result = new ArrayList<Point>();
			AlgoDominant algo = new AlgoDominant(55);
			ArrayList<Point> dom = algo.calculDominatingSet(points);
			AlgoSteiner algoSteiner = new AlgoSteiner();
			Tree2D resultSteiner = algoSteiner.calculSteiner(points, 55, dom);
			result.addAll(dom);
			result.addAll(resultSteiner.getList());
			Set<Point> s = new HashSet<Point>(result);
			points = new ArrayList<Point>(s);
			output.println(points.size());
			outputTime.println(System.currentTimeMillis()-debut);
		}
		output.close();
		outputTime.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public ArrayList<Point> rand48Valide(int nbPoint){
        ArrayList<Point> U = new ArrayList<Point>();
        ArrayList<Point> tmp = this.rand(BigInteger.valueOf(System.currentTimeMillis()), nbPoint);
        isConnexe(tmp, U, tmp.get(0));
        while(U.size()  != nbPoint){
        	System.out.println("nonValide " + U.size());
        	tmp = new ArrayList<>(U);
            tmp.addAll(this.rand(BigInteger.valueOf(System.currentTimeMillis()), nbPoint - U.size()));
            U.clear();
            isConnexe(tmp, U, tmp.get(0));
        }
        return U ;
    }


	public static ArrayList<Point> neighbor(Point p, ArrayList<Point> UDG) {
		ArrayList<Point> result = new ArrayList<Point>();

		for (Point point : UDG)
			if (point.distance(p) < 55 && !point.equals(p) ){
				result.add(point);

			}

		return result;
	}
	
	public static void isConnexe(ArrayList<Point> points , ArrayList<Point> U , Point p){
	        if(! U.contains(p)){
	            U.add(p);
	            ArrayList<Point> list = new ArrayList<Point>(points) ;
	            list.removeAll(U);
	            for (Point v : neighbor(p, points)) {
	                if(!U.contains(v)){
	                    isConnexe(points,U , v);
	                }
	            }
	        }
	    }
	
	public static void main(String[] args) {
		RandomGen r =new RandomGen();
		
		//Generer des points dans les fichiers
		//r.genPointsToFile(new BigInteger("975312478638992345"), 1000, 100, "./instanceUPMC/upmc");;
		//r.genPointsToFile(new BigInteger("486547957562865834"), 10000, 100, "./instanceUPMC1000/upmc");
		
		//Faire des tests sur les fichiers generes
		r.testInstance("upmc100", 100, "./instanceUPMC/upmc");
		r.testInstanceSteiner("upmc100", 100, "./instanceUPMC/upmc");
		//r.testInstance("upmc1000", 100, "./instanceUPMC1000/upmc");
	}
	

}
