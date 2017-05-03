package model;
import java.util.LinkedList;

public class Population {
	LinkedList<Individual> individuals;
	
	public Population(){
		individuals = new LinkedList<Individual>();
	}
	
	public void addIndividual(Individual ind){
		individuals.add(ind);
	}
	
	public LinkedList<Individual> getIndividuals(){
		return individuals;
	}
}
