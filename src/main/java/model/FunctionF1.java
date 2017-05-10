package model;

public class FunctionF1 extends Function{
	
	public FunctionF1(int dimension, double rangeMin, double rangeMax){
		super(dimension, rangeMin, rangeMax);
	}
	
	
	public double minimalizeFunction(Individual individual){
		double f = 0;
		
		if (individual.getGens().size() != super.dimension)
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
		
		individual.setFinalFunctionValue(f);
		return f;
	}

}
