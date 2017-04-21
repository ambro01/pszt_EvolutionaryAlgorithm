package app;

import model.Simulation1Plus1;

public class Run {

	public static void main(String[] args) {
		Simulation1Plus1 sim = new Simulation1Plus1(2, 0.0001);
		sim.runSimulation();
	}

}
