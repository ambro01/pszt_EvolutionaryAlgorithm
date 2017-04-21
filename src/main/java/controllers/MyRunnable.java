package controllers;

import java.util.ArrayList;
import java.util.Collections;

import model.Individual;
import model.Simulation1Plus1;

public class MyRunnable implements Runnable{
	private ArrayList<Individual> individuals;
	
	public MyRunnable(){
		individuals = new ArrayList<Individual>();
	}

	public void run() {
		Simulation1Plus1 sim = new Simulation1Plus1(10, 0.00001);
		individuals.add(sim.runSimulation());
	}
	
	public Individual selectBest(){
		Collections.sort(individuals, new MyComparator());
		return individuals.get(0);
	}

}
