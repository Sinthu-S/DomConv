package steiner;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

public class ListToTree {
	
	public void krukalToTree2D(Tree2D solution, ArrayList<Arete> aretes){

		if(aretes.isEmpty())
			return;
		Point p= solution.getRoot();
		Arete tmp;
		for(int i=0; i<aretes.size(); i++){
			tmp=aretes.get(i);
			if(tmp.extremite(p)){
				solution.getSubTrees().add(new Tree2D(tmp.getOtherExtremitie(p), new ArrayList<Tree2D>()));
				aretes.set(i, null);
			}
		}
		aretes.removeAll(Collections.singleton(null));

		
		for(int i=0; i<solution.getSubTrees().size(); i++)
			krukalToTree2D(solution.getSubTrees().get(i), aretes);
		return;

	}
	
	public Tree2D krukalToTree(ArrayList<Arete> aretes){
		Tree2D arbre = new Tree2D(aretes.get(0).a, new ArrayList<Tree2D>());
		this.krukalToTree2D(arbre, aretes);
		return arbre;
	}

}
