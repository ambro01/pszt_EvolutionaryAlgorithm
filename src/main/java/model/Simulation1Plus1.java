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
	
	public Simulation1Plus1(Function function, double sigma0, double sigmaMin, int iterations, int m, double c1, double c2){
		super(function, iterations, sigma0);
		this.sigma = sigma_0; // odchylenie standardowe
		this.fi = 0; // liczba wybranych y-kow w ciagu ostatnich m iteracji
		this.mIterations = m; // liczba iteracji po ktorych odbywa sie uaktualnienie wartosc sigmy
		this.c1 = c1; //czynnik modyfikujacy sigme
		this.c2 = c2; // czynnik modyfikujacy sigme
		this.sigmaMin = sigmaMin;
		
	}
	
	public double getSigma(){
		return sigma;
	}
	
	public void firstGeneration(){
		populationP = new Population();
		ArrayList<Gen> gens = new ArrayList<Gen>();
		Random r = new Random();
		double x; 
		
		for(int i = 0; i < super.myFunction.dimension; ++i){
			x = super.myFunction.rangeMin + (super.myFunction.rangeMax - super.myFunction.rangeMin) * r.nextDouble();
			gens.add(new Gen(x));
		}
		populationP.addIndividual(new Individual(gens));
		
		super.updateResultsArray();
		++kIterations;
	}
	
	public void nextGeneration(){
		Individual individualSecond;
		ArrayList<Gen> gens = new ArrayList<Gen>();
		Random r = new Random();
		
		for(Gen gen : populationP.getIndividuals().getFirst().getGens()){
			gens.add(new Gen(gen.getX() + this.sigma * r.nextGaussian())); 
		}
		
		individualSecond = new Individual(gens);
		selectBetterIndividual(individualSecond);
		
		super.updateResultsArray();
		++kIterations;
	}
	
	public void selectBetterIndividual(Individual individualSecond){
		if(super.myFunction.minimalizeFunction(populationP.getIndividuals().getFirst()) < super.myFunction.minimalizeFunction(individualSecond))
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
		if (sigma < sigmaMin || kIterations >= super.iterationsLimit)
			return true;
		else
			return false;
	}
	
	public void runSimulation(){
		firstGeneration();
		do{
			nextGeneration();
		} while(!checkFinish());
	}
	
}
