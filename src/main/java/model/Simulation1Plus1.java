package model;

import java.util.Random;
import java.util.ArrayList;

public class Simulation1Plus1 extends Simulation{
	Population population;
	double sigma;
	int fi;
	int mIterations;
	double c1;
	double c2;
	double sigmaMin;
	
	public Simulation1Plus1(int dimension, double sigma0, double sigmaMin, int it){
		sigma_0 = sigma0;
		sigma = sigma_0; // odchylenie standardowe
		fi = 0; // liczba wybranych y-kow w ciagu ostatnich m iteracji
		mIterations = 10; // liczba iteracji po ktorych odbywa sie uaktualnienie wartosc sigmy
		kIterations = 0; // kolejna iteracja
		c1 = 0.82; //czynnik modyfikujacy sigme
		c2 = 1.2; // czynnik modyfikujacy sigme
		this.dimension = dimension;
		this.sigmaMin = sigmaMin;
		super.iterationsLimit = it;
	}
	
	public void firstGeneration(){
		population = new Population();
		ArrayList<Gen> gens = new ArrayList<Gen>();
		Random r = new Random();
		double x; 
		
		++kIterations;
		
		for(int i = 0; i < dimension; ++i){
			x = Global.rangeMin + (Global.rangeMax - Global.rangeMin) * r.nextDouble();
			gens.add(new Gen(x));
		}
		population.addIndividual(new Individual(gens));
	}
	
	public void nextGeneration(){
		Individual individualSecond;
		ArrayList<Gen> gens = new ArrayList<Gen>();
		Random r = new Random();
		
		++kIterations;
		
		for(Gen gen : population.getIndividuals().getFirst().getGens()){
			gens.add(new Gen(gen.getX() + this.sigma * r.nextGaussian())); 
		}
		
		individualSecond = new Individual(gens);
		selectBetterIndividual(individualSecond);
	}
	
	public void selectBetterIndividual(Individual individualSecond){
		if(Global.minimalizeFunction(population.getIndividuals().getFirst(), dimension) < Global.minimalizeFunction(individualSecond, dimension))
			changeSigma();
		else{
			population.getIndividuals().removeFirst();
			population.addIndividual(individualSecond);
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
	
	public Individual runSimulation(){
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
		return population.getIndividuals().getFirst();
	}
	
}
