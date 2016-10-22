package algorithms;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import bleublack.BleuBlack;
import steiner.AlgoSteiner;
import steiner.Tree2D;

public class DefaultTeam {
	public ArrayList<Point> calculConnectedDominatingSet(ArrayList<Point> points, int edgeThreshold) {
		System.out.println("seuil " + edgeThreshold);
		ArrayList<Point> result = new ArrayList<Point>();
		BleuBlack  algo = new BleuBlack(edgeThreshold);
		return algo.calculSMIS(points);

	}
//	public ArrayList<Point> calculConnectedDominatingSet(ArrayList<Point> points, int edgeThreshold) {
//		System.out.println("seuil " + edgeThreshold);
//		ArrayList<Point> result = new ArrayList<Point>();
//		AlgoDominant algo = new AlgoDominant(edgeThreshold);
//		ArrayList<Point> dom = algo.calculDominatingSet(points);
//		AlgoSteiner algoSteiner = new AlgoSteiner();
//		Tree2D resultSteiner = algoSteiner.calculSteiner(points, edgeThreshold, dom);
//		result.addAll(dom);
//		result.addAll(resultSteiner.getList());
//		Set<Point> s = new HashSet<Point>(result);
//
//		return new ArrayList<Point>(s);
//
//	}


	//FILE PRINTER
	private void saveToFile(String filename,ArrayList<Point> result){
		int index=0;
		try {
			while(true){
				BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(filename+Integer.toString(index)+".points")));
				try {
					input.close();
				} catch (IOException e) {
					System.err.println("I/O exception: unable to close "+filename+Integer.toString(index)+".points");
				}
				index++;
			}
		} catch (FileNotFoundException e) {
			printToFile(filename+Integer.toString(index)+".points",result);
		}
	}
	private void printToFile(String filename,ArrayList<Point> points){
		try {
			PrintStream output = new PrintStream(new FileOutputStream(filename));
			int x,y;
			for (Point p:points) output.println(Integer.toString((int)p.getX())+" "+Integer.toString((int)p.getY()));
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("I/O exception: unable to create "+filename);
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
}
