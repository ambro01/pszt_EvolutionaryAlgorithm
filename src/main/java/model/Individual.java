package model;
import java.util.ArrayList;

public class Individual {
	private ArrayList<Gen> gens;
	
	public Individual(ArrayList<Gen> gens){
		this.gens = gens;
	}
	
	public ArrayList<Gen> getGens(){
		return gens;
	}
	
	public void setGens(ArrayList<Gen> gens){
		this.gens = gens;
	}
}
