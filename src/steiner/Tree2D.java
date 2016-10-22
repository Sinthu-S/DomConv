package steiner;

import java.awt.Point;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

public class Tree2D {
  private Point root;
  private ArrayList<Tree2D> subtrees;

  public Tree2D (Point p, ArrayList<Tree2D> trees){
    this.root=p;
    this.subtrees=trees;
  }
  public Point getRoot(){
    return this.root;
  }
  public ArrayList<Tree2D> getSubTrees(){
    return this.subtrees;
  }
  public double distanceRootToSubTrees(){
    double d=0;
    for (int i=0;i<this.subtrees.size();i++) d+=subtrees.get(i).getRoot().distance(root);
    return d;
  }
  public ArrayList<Point> getList(){
	  ArrayList<Point> list = new ArrayList<Point>();
	  list.add(root);
	  
	  for(Tree2D t : subtrees){
		  list.addAll(new ArrayList<Point>(t.getList()));
	  }
	  return list;
  }
}
