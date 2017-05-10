package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SimulationMiLambda extends Simulation{
	private int lambda; // ilosc osobnokow w poczatkowej sekwencji
	private int mi; // ilosc osobnikow utrzymywanych w kazdej populacji
	private Population populationT; // populacja tymczasowa lamda - elementowa
	private Population populationR; // populucja potomna (po krzyzowaniu i mutacji) - lamda elementowa
	
	public SimulationMiLambda(Function function, double sigma0, int lambda, int mi, int iterations){
		super(function, iterations, sigma0);
		this.lambda = lambda;
		this.mi = mi;
	}
	
	public void firstGeneration(){
		populationP = new Population();
		
		for(int i = 0; i < mi; ++i){
			ArrayList<Gen> gens = new ArrayList<Gen>();
			Random r = new Random();
			double x; 
			for(int j = 0; j < super.myFunction.dimension; ++j){
				x = super.myFunction.rangeMin + (super.myFunction.rangeMax - super.myFunction.rangeMin) * r.nextDouble();
				gens.add(new Gen(x, super.sigma_0));
			}
			populationP.addIndividual(new Individual(gens));
		}
		updateResultsArray();
		++kIterations;
	}
	
	public void sortPopulation(Population p){
		for(Individual individual : p.getIndividuals()){
			super.myFunction.minimalizeFunction(individual);
		}
		Collections.sort(p.getIndividuals(), new MyComparator());
	}	
	
	public void rankMethod(){
		sortPopulation(populationP);
		
		double sumOfPlacesInRank = 0;
		int sizeOfRank = populationP.getIndividuals().size();
		
		for(int i = 1; i <= sizeOfRank; i++){
			sumOfPlacesInRank = sumOfPlacesInRank + i;
		}
		
		for(int i = 0; i < sizeOfRank; i++){
			populationP.getIndividuals().get(i).setProbability((sizeOfRank-i)/sumOfPlacesInRank);;
		}
	}
	
	public void selectLambdaIndividualsWithProbability(){
		populationT = new Population();
		for (int i = 0; i < lambda; i++){
			double p = Math.random();
			double cumulativeProbability = 0.0;
			for (Individual item : populationP.getIndividuals()){
			    cumulativeProbability += item.getProbability();
			    if (p <= cumulativeProbability) {
			        populationT.addIndividual(item);
			    	break;
			    }
			}
		}
	}
	
	public void nextGeneration(){
		rankMethod();
		selectLambdaIndividualsWithProbability();
		crossing();
		mutation();
		sortPopulation(populationR);
		populationP = new Population();
		for(int i = 0; i < mi; i++){
			populationP.addIndividual(populationR.getIndividuals().get(i));
		}
		updateResultsArray();
		kIterations++;
	}
	
	public void crossing(){
		populationR = new Population();
		Individual first;
		Individual second;
		for (int i = 0; i < lambda; i++){
			first = populationT.getIndividuals().get(i);
			if (i == lambda-1)
				second = populationT.getIndividuals().getFirst();
			else
				second = populationT.getIndividuals().get(i+1);
			
			ArrayList<Gen> gens = new ArrayList<Gen>();
			for(int j = 0; j < super.myFunction.dimension; ++j){
				gens.add(new Gen((first.getGens().get(j).getX() + second.getGens().get(j).getX())/2, (first.getGens().get(j).getSigma() + second.getGens().get(j).getSigma())/2));
			}
			populationR.addIndividual(new Individual(gens));
		}
	}
	
	public void mutation(){
		double tau = 1/Math.sqrt(2*super.myFunction.dimension); // parametr mutacji
		double tau_prim = 1/Math.sqrt(2*Math.sqrt(super.myFunction.dimension)); // parametr mutacji
		Random r = new Random();
		double ksi; // zmienna losowa o tej samej wartosci dla kazdego wymiaru
		double ksi_i; // zmienna losowa niezalezna dla kazdego wymiaru
		
		for(Individual ind : populationR.getIndividuals()){
			ksi = r.nextGaussian();
			for(Gen gen : ind.getGens()){
				ksi_i = r.nextGaussian();
				// mutacja wartosci odchylen standardowych
				gen.setSigma(gen.getSigma()*Math.exp(tau_prim*ksi + tau*ksi_i));
				// mutacja wartosci w danym wymiarze
				gen.setX(gen.getX() + gen.getSigma()*r.nextGaussian());
			}
		}
	}
	
	public boolean checkFinish(){
		if (kIterations >= iterationsLimit)
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
