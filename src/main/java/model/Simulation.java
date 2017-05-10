package model;

import java.util.Collections;

public abstract class Simulation {
	protected Function myFunction;
	protected Population populationP;
	protected int kIterations; // licznik iteracji
	protected double sigma_0; // odchylenie standardowe
	protected int iterationsLimit;
	protected double [] results;
	
	public Simulation(Function function, int iterations, double sigma0){
		this.myFunction = function;
		this.iterationsLimit = iterations;
		this.results = new double[this.iterationsLimit];
		this.sigma_0 = sigma0;
		this.kIterations = 0;
	}
	
	public abstract void firstGeneration();
	public abstract void nextGeneration();
	public abstract boolean checkFinish();
	public abstract void runSimulation();
	
	public Individual selectBest(){
		Collections.sort(populationP.getIndividuals(), new MyComparator());
		return populationP.getIndividuals().getFirst();
	}
	
	public void updateResultsArray(){
		results[kIterations] = selectBest().getFinalFunctionValue();
	}
	
	public double [] getResults(){
		return results;
	}
}
