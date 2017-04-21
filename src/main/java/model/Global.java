package model;
import model.Individual;
import java.lang.*;

public class Global {
	
	static public double rangeMin = -20;
	static public double rangeMax = 20;
	
	
	static public double minimalizeFunction(Individual individual, int dimension){
		double f = 0;
		
		if (individual.getGens().size() != dimension)
			return 0;
		
		double product = 1;
		for (Gen gen : individual.getGens()){
			product = product * Math.pow(Math.sin(gen.getX()), 2);
		}
		
		double sum = 0;
		int counter = 0;
		for (Gen gen : individual.getGens()){
			++counter;
			sum = sum + Math.pow(gen.getX(), 2)/counter;
		}
		
		f = product + 0.01 * sum;
		
		return f;
	}
	
	static public double testFunction(Individual individual, int dimension){
		double f = 0;
		
		if (individual.getGens().size() != dimension)
			return 0;
		
		if (dimension == 2) {
			for (Gen gen : individual.getGens()){
				f = f + Math.pow(Math.sin(gen.getX()), 2);
			}
		}
		
		return f;
	}
}
