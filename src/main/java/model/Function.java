package model;

public abstract class Function {
	protected int dimension;
	protected double rangeMin;
	protected double rangeMax;
	
	public Function(int dimension, double rangeMin, double rangeMax){
		this.dimension = dimension;
		this.rangeMin = rangeMin;
		this.rangeMax = rangeMax;
	}
	
	public int getDimension(){
		return dimension;
	}
	
	public abstract double minimalizeFunction(Individual individual);
}
