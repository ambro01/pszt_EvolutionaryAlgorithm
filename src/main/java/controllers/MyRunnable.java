package controllers;

import java.util.ArrayList;
import java.util.Collections;

import model.Individual;
import model.Simulation1Plus1;
import model.SimulationMiLambda;

public class MyRunnable implements Runnable{
	private ArrayList<Individual> individuals;
	
	public MyRunnable(){
		individuals = new ArrayList<Individual>();
	}

	public void run() {
		//Simulation1Plus1 sim = new Simulation1Plus1(10, 10, 0.00001, 100);
		SimulationMiLambda sim = new SimulationMiLambda(10, 1, 70, 10, 100);
		individuals.add(sim.runSimulation());
	}
	
	public Individual selectBest(){
		Collections.sort(individuals, new MyComparator());
		return individuals.get(0);
	}

}
