package model;

import java.util.Comparator;

public class MyComparator implements Comparator<Individual> {
	
	public int compare(Individual o1, Individual o2) {
		if(o2 == null) 
			return -1;
        if(o1.getFinalFunctionValue() > o2.getFinalFunctionValue()) 
        	return 1;
        else if(o1.getFinalFunctionValue() < o2.getFinalFunctionValue()) 
        	return -1;
        else 
        	return 0;
	}
}
