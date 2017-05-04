package model;

import java.util.Collections;

public abstract class Simulation {
	protected Population populationP;
	protected int kIterations; // licznik iteracji
	protected int dimension;
	protected double sigma_0; // odchylenie standardowe
	protected int iterationsLimit;
	
	public abstract void firstGeneration();
	public abstract void nextGeneration();
	public abstract boolean checkFinish();
	public abstract void runSimulation();
	
	public Individual selectBest(){
		Collections.sort(populationP.getIndividuals(), new MyComparator());
		return populationP.getIndividuals().get(0);
	}
}