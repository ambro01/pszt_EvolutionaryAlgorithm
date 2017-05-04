package model;

import java.util.ArrayList;
import java.util.Collections;

public class MyRunnable implements Runnable{
	private ArrayList<Individual> individuals;
	double sigma;
	int iterations;
	int mIterations;
	double c1;
	double c2;
	double sigmaMin;
	int dimension;
	
	public MyRunnable(int dimension, double sigma0, double sigmaMin, int it, int m, double c1, double c2){
		individuals = new ArrayList<Individual>();
		this.sigmaMin = sigmaMin;
		this.sigma = sigma0;
		this.mIterations = m;
		this.c1 = c1;
		this.c2 = c2;
		this.iterations = it;
		this.dimension = dimension;
	}

	public void run() {
		Simulation1Plus1 sim = new Simulation1Plus1(dimension, sigma, sigmaMin, iterations, mIterations, c1, c2);
		sim.runSimulation();
		individuals.add(sim.selectBest());
	}
	
	public Individual selectBest(){
		Collections.sort(individuals, new MyComparator());
		return individuals.get(0);
	}

}