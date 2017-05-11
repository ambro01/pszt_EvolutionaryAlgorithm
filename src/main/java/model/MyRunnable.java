package model;

import java.util.ArrayList;
import java.util.Collections;

public class MyRunnable implements Runnable{
	private Individual individual;
	private double [] results;
	private Function function;
	private double sigma;
	private int iterations;
	private int mIterations;
	private double c1;
	private double c2;
	private double sigmaMin;
	private double resultSigma;
	private int kIterations;
	private double bestValue;
	
	public MyRunnable(Function function, double sigma0, double sigmaMin, int it, int m, double c1, double c2){
		this.function = function;
		this.individual = null;
		this.sigmaMin = sigmaMin;
		this.sigma = sigma0;
		this.mIterations = m;
		this.c1 = c1;
		this.c2 = c2;
		this.iterations = it;
		this.results = new double[iterations];
		this.results[results.length-1] = 10;
		this.resultSigma = sigma0;
		this.kIterations = 0;
		this.bestValue = 1;
	}

	public void run() {
		Simulation1Plus1 sim = new Simulation1Plus1(function, sigma, sigmaMin, iterations, mIterations, c1, c2);
		sim.runSimulation();
		synchronized ((Double) bestValue) {
			if(sim.selectBest().getFinalFunctionValue() < bestValue){
				individual = sim.selectBest();
				bestValue = sim.selectBest().getFinalFunctionValue();
				results = sim.getResults();
				resultSigma = sim.getSigma();
				kIterations = sim.getKIeterations();
			}
		}

	}
	
	public Individual getBestIndividual(){
		return individual;
	}
	
	public double[] getResults(){
		return results;
	}
	
	public double getSigma(){
		return resultSigma;
	}
	
	public int getKIterations(){
		return kIterations;
	}

}
