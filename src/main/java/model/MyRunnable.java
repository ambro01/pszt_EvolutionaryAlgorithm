package model;

import java.util.ArrayList;
import java.util.Collections;

public class MyRunnable implements Runnable{
	private ArrayList<Individual> individuals;
	private double [] results;
	private Function function;
	double sigma;
	int iterations;
	int mIterations;
	double c1;
	double c2;
	double sigmaMin;
	double resultSigma;
	
	public MyRunnable(Function function, double sigma0, double sigmaMin, int it, int m, double c1, double c2){
		this.function = function;
		this.individuals = new ArrayList<Individual>();
		this.sigmaMin = sigmaMin;
		this.sigma = sigma0;
		this.mIterations = m;
		this.c1 = c1;
		this.c2 = c2;
		this.iterations = it;
		this.results = new double[iterations];
		this.results[results.length-1] = 100;
		this.resultSigma = sigma0;
	}

	public void run() {
		Simulation1Plus1 sim = new Simulation1Plus1(function, sigma, sigmaMin, iterations, mIterations, c1, c2);
		sim.runSimulation();
		if(results[results.length-1] > sim.getResults()[results.length-1]){
			results = sim.getResults();
			resultSigma = sim.getSigma();
		}
		individuals.add(sim.selectBest());
	}
	
	public Individual selectBest(){
		Collections.sort(individuals, new MyComparator());
		return individuals.get(0);
	}
	
	public double[] getResults(){
		return results;
	}
	
	public double getSigma(){
		return resultSigma;
	}

}
