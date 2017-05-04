package model;

import java.util.Random;
import java.util.ArrayList;

public class Simulation1Plus1 extends Simulation{
	double sigma;
	int fi;
	int mIterations;
	double c1;
	double c2;
	double sigmaMin;
	
	public Simulation1Plus1(int dimension, double sigma0, double sigmaMin, int it, int m, double c1, double c2){
		sigma_0 = sigma0;
		sigma = sigma_0; // odchylenie standardowe
		fi = 0; // liczba wybranych y-kow w ciagu ostatnich m iteracji
		mIterations = m; // liczba iteracji po ktorych odbywa sie uaktualnienie wartosc sigmy
		kIterations = 0; // kolejna iteracja
		this.c1 = c1; //czynnik modyfikujacy sigme
		this.c2 = c2; // czynnik modyfikujacy sigme
		super.dimension = dimension;
		this.sigmaMin = sigmaMin;
		super.iterationsLimit = it;
	}
	
	public void firstGeneration(){
		populationP = new Population();
		ArrayList<Gen> gens = new ArrayList<Gen>();
		Random r = new Random();
		double x; 
		
		++kIterations;
		
		for(int i = 0; i < dimension; ++i){
			x = Global.rangeMin + (Global.rangeMax - Global.rangeMin) * r.nextDouble();
			gens.add(new Gen(x));
		}
		populationP.addIndividual(new Individual(gens));
	}
	
	public void nextGeneration(){
		Individual individualSecond;
		ArrayList<Gen> gens = new ArrayList<Gen>();
		Random r = new Random();
		
		++kIterations;
		
		for(Gen gen : populationP.getIndividuals().getFirst().getGens()){
			gens.add(new Gen(gen.getX() + this.sigma * r.nextGaussian())); 
		}
		
		individualSecond = new Individual(gens);
		selectBetterIndividual(individualSecond);
	}
	
	public void selectBetterIndividual(Individual individualSecond){
		if(Global.minimalizeFunction(populationP.getIndividuals().getFirst(), dimension) < Global.minimalizeFunction(individualSecond, dimension))
			changeSigma();
		else{
			populationP.getIndividuals().removeFirst();
			populationP.addIndividual(individualSecond);
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
		if (sigma < sigmaMin || kIterations >= iterationsLimit)
			return true;
		else
			return false;
	}
	
	public void runSimulation(){
		firstGeneration();
		//System.out.println(kIterations + ": " + Global.minimalizeFunction(individualFirst, dimension));
		//System.out.println(this.individualFirst.getGens().get(0).getX());
		do{
			nextGeneration();
			//System.out.println(kIterations + ": " + Global.minimalizeFunction(individualFirst, dimension));
			//System.out.println(this.individualFirst.getGens().get(0).getX());
		} while(!checkFinish());
		//population.getIndividuals().getFirst().setFinalFunctionValue(Global.minimalizeFunction(population.getIndividuals().getFirst(), dimension));
		//System.out.println(kIterations + ": " + Global.minimalizeFunction(individualFirst, dimension));
	}
	
}
