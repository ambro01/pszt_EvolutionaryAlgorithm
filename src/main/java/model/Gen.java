package model;

public class Gen {
	private double x;
	private double sigma;
	
	public Gen(double x){
		this.x = x;
	}
	
	public Gen(double x, double sigma){
		this.x = x;
		this.sigma = sigma;
	}
	
	public void setX(double value){
		x = value;
	}
	
	public double getX(){
		return x;
	}
	
	public void setSigma(double sigma){
		this.sigma = sigma;
	}
	
	public double getSigma(){
		return sigma;
	}
}
