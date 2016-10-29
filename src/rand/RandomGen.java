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
import java.util.Random;

import bleublack.BleuBlack;

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
	
	public int rand(BigInteger graine){
		BigInteger val = null;
		Random rand = new Random();
		int bitFort;
		for (int i = 0; i < 1000; i++) {
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
			System.out.println(val);	
		}
		return val.intValue();
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
	
	
	/*Generation de nbFile fichier contenant chacun 1000 points aleatoire */
	public void genPointsToFile(BigInteger graine, int nbFile, String fileName){
		BigInteger val = null;
		BigInteger x = null;
		BigInteger y = null;
		
		Random rand = new Random();
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
	}
	
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
			output.println("Resultat de S-MIS du fichier "+resultFilename);
			output.println("");

			
		for(int i=0; i<nbFile; i++){
			points = readFromFile(chemin+i);
			BleuBlack  algo = new BleuBlack(55);
			System.out.println("S-MIS = "+algo.calculSMIS(points).size());
			output.println("S-MIS = "+algo.calculSMIS(points).size());
			
		}
		output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void trie(String filename){
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
	}
	
	public static ArrayList<Point> rand48Valide(int size){
        ArrayList<Point> U = new ArrayList<Point>();
        ArrayList<Point> tmp = rand48(new BigInteger(size));
        isConnexe(tmp, U, tmp.get(0));
        while(U.size()  != size){
            U.clear();
            tmp = rand48(size);
            isConnexe(tmp, U, tmp.get(0));
        }
        return U ;
    }



	public static void isConnexe(ArrayList<Point> points , ArrayList<Point> U , Point p){
	        if(! U.contains(p)){
	            U.add(p);
	            ArrayList<MyPoint> list = new ArrayList<MyPoint>(points) ;
	            list.removeAll(U);
	            for (MyPoint v : neighborMy(p, list, 55 )) {
	                if(!U.contains(v)){
	                    isConnexe(points,U , v);
	                }
	            }
	        }
	    }
	
	public static void main(String[] args) {
		RandomGen r =new RandomGen();
		
		//Generer des points dans les fichiers
		//r.genPointsToFile(new BigInteger("123456789112348994"), 100, "./instance100/gen");
		//r.genPointsToFile(new BigInteger("975312478638992345"), 1000, "./instance1000/gen");
		//r.genPointsToFile(new BigInteger("536798334622387451"), 10000, "./instance10000/gen");
		
		//Faire des tests sur les fichiers generes
		//r.testInstance("instance100", 100, "./instance100/gen");
		//r.testInstance("instance1000", 1000, "./instance1000/gen");
		//r.testInstance("instance10000", 10000, "./instance10000/gen");
	}
	

}
