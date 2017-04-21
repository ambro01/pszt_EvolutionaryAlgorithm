package model;
import java.util.ArrayList;

public class Individual {
	private ArrayList<Gen> gens;
	private double finalFunctionValue;
	
	public Individual(ArrayList<Gen> gens){
		this.gens = gens;
	}
	
	public ArrayList<Gen> getGens(){
		return gens;
	}
	
	public void setGens(ArrayList<Gen> gens){
		this.gens = gens;
	}
	
	public double getFinalFunctionValue(){
		return finalFunctionValue;
	}
	
	public void setFinalFunctionValue(double value){
		finalFunctionValue = value;
	}
}
