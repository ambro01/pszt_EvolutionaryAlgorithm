package model;

import java.util.Random;
import java.util.ArrayList;

public class Simulation1Plus1 {
	Individual individualFirst;
	Individual individualSecond;
	int dimension;
	double sigma;
	int fi;
	int mIterations;
	int kIterations;
	double c1;
	double c2;
	double sigmaMin;
	
	public Simulation1Plus1(){
		sigma = 1;
		fi = 0;
		mIterations = 10;
		kIterations = 0;
		c1 = 0.82;
		c2 = 0.12;
	}
	
	public void firstGeneration(){
		
		ArrayList<Gen> gens = new ArrayList<Gen>();
		Random r = new Random();
		double x; 
		
		++kIterations;
		
		for(int i = 0; i < dimension; ++i){
			x = Global.rangeMin + (Global.rangeMax - Global.rangeMin) * r.nextDouble();
			gens.add(new Gen(x));
		}
		
		individualFirst = new Individual(gens);
	}
	
	public void nextGeneration(){
		ArrayList<Gen> gens = new ArrayList<Gen>();
		Random r = new Random();
		
		++kIterations;
		
		for(Gen gen : individualFirst.getGens()){
			gens.add(new Gen(gen.getX() + this.sigma * r.nextGaussian())); 
		}
		
		individualSecond = new Individual(gens);
	}
	
	public void selectBetterIndividual(){
		if(Global.minimalizeFunction(individualFirst, dimension) > Global.minimalizeFunction(individualSecond, dimension))
			changeSigma();
		else{
			individualFirst = individualSecond;
			++fi;
			changeSigma();
		}
	}
	
	public void changeSigma(){
		if (kIterations % mIterations == 0){
			if(fi < 0.2)
				sigma = c1 * sigma;
			else if(fi > 0.2)
				sigma = c2 * sigma;
		}
	}
	
	public void checkFinish(){
	//	if (sigma < sigmaMin)
			
	}
}
