package model;

import java.util.Random;
import java.util.ArrayList;

public class Simulation1Plus1 {
	Individual individualFirst;
	int dimension;
	double sigma;
	int fi;
	int mIterations;
	int kIterations;
	double c1;
	double c2;
	double sigmaMin;
	
	public Simulation1Plus1(int dimension, double sigmaMin){
		sigma = 10; // odchylenie standardowe
		fi = 0; // liczba wybranych y-kow w ciagu ostatnich m iteracji
		mIterations = 10; // liczba iteracji po ktorych odbywa sie uaktualnienie wartosc sigmy
		kIterations = 0; // kolejna iteracja
		c1 = 0.82; //czynnik modyfikujacy sigme
		c2 = 1.2; // czynnik modyfikujacy sigme
		this.dimension = dimension;
		this.sigmaMin = sigmaMin;
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
		Individual individualSecond;
		ArrayList<Gen> gens = new ArrayList<Gen>();
		Random r = new Random();
		
		++kIterations;
		
		for(Gen gen : individualFirst.getGens()){
			gens.add(new Gen(gen.getX() + this.sigma * r.nextGaussian())); 
		}
		
		individualSecond = new Individual(gens);
		
		selectBetterIndividual(individualSecond);
		
	}
	
	public void selectBetterIndividual(Individual individualSecond){
		if(Global.minimalizeFunction(individualFirst, dimension) < Global.minimalizeFunction(individualSecond, dimension))
			changeSigma();
		else{
			individualFirst = individualSecond;
			++fi;
			changeSigma();
		}
	}
	
	public void changeSigma(){
		if (kIterations % mIterations == 0){
			if(fi/mIterations < 0.2)
				sigma = c1 * sigma;
			else if(fi/mIterations > 0.2)
				sigma = c2 * sigma;
			fi = 0;
		}
	}
	
	public boolean checkFinish(){
		if (sigma < sigmaMin)
			return true;
		else
			return false;
	}
	
	public Individual runSimulation(){
		firstGeneration();
		//System.out.println(kIterations + ": " + Global.minimalizeFunction(individualFirst, dimension));
		//System.out.println(this.individualFirst.getGens().get(0).getX());
		do{
			nextGeneration();
			//System.out.println(kIterations + ": " + Global.minimalizeFunction(individualFirst, dimension));
			//System.out.println(this.individualFirst.getGens().get(0).getX());
		} while(!checkFinish());
		individualFirst.setFinalFunctionValue(Global.minimalizeFunction(individualFirst, dimension));
		//System.out.println(kIterations + ": " + Global.minimalizeFunction(individualFirst, dimension));
		
		return individualFirst;
	}
	
}
