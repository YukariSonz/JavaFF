//
//  BestSuccessorSelector.java
//  JavaFF
//
//  Created by Andrew Coles on Thu Jan 31 2008.
//

package javaff.search;

import javaff.planning.State;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;
import java.math.BigDecimal;

import java.util.LinkedList;

public class RouletteSuccessorSelector implements SuccessorSelector
{

	private static RouletteSuccessorSelector rs = null;

	public static RouletteSuccessorSelector getInstance()
	{
		if (rs == null)
			rs = new RouletteSuccessorSelector(); // Singleton, as in NullFilter
		return rs;
	}

	public State choose(Set toChooseFrom)
	{

		if (toChooseFrom.isEmpty())
			return null;

		BigDecimal bestHeuristic; // best heuristic seen

	  BigDecimal totalFitnessValue = new BigDecimal(0);
		LinkedList fitnessPairs = new LinkedList();

		Iterator itr = toChooseFrom.iterator();
		State curr = (State) itr.next();
		//When Reaching the goal state, the HValue will be 0 -- not divisible !
		if (curr.getHValue().doubleValue() == 0){
			return curr;
		}

		double fitnessDoubleValue = 1/curr.getHValue().doubleValue();
		BigDecimal fitnessValue = new BigDecimal(fitnessDoubleValue); // and has the best heuristic
		totalFitnessValue = totalFitnessValue.add(fitnessValue);
		LinkedList fitnessPair = new LinkedList();
		fitnessPair.add(curr);
		fitnessPair.add(fitnessValue);
		fitnessPairs.add(fitnessPair);

		while (itr.hasNext())
		{
			curr = (State) itr.next();
			fitnessDoubleValue = 1/curr.getHValue().doubleValue();
			fitnessValue = new BigDecimal(fitnessDoubleValue); // and has the best heuristic
			totalFitnessValue = totalFitnessValue.add(fitnessValue);
			fitnessPair = new LinkedList();
			fitnessPair.add(curr);
			fitnessPair.add(fitnessValue);
			fitnessPairs.add(fitnessPair);
		}

		LinkedList rouletteFactors = new LinkedList();
		BigDecimal startValue = new BigDecimal(0);
		BigDecimal endValue = new BigDecimal(0);
		BigDecimal valueRange = new BigDecimal(0);
		State current = null;
		LinkedList fitnessList = new LinkedList();
		double valueDoubleRange = 0;
		for (int i = 0; i < fitnessPairs.size(); i++){
			fitnessList = (LinkedList) fitnessPairs.get(i);
			current = (State) fitnessList.get(0);
			valueDoubleRange = ((BigDecimal) fitnessList.get(1)).doubleValue()/totalFitnessValue.doubleValue();
			valueRange = new BigDecimal(valueDoubleRange);
			endValue = endValue.add(valueRange);
			LinkedList rouletteFactor = new LinkedList();
			rouletteFactor.add(current);
			rouletteFactor.add(startValue);
			rouletteFactor.add(endValue);
			startValue = endValue;
			rouletteFactors.add(rouletteFactor);
		}

		double r = javaff.JavaFF.generator.nextDouble();
		BigDecimal startingValue = new BigDecimal(0);
		BigDecimal endingValue = new BigDecimal(0);
		State currentState = null;
		for (int k = 0; k < rouletteFactors.size(); k++){
			LinkedList rouletteFactor = (LinkedList) rouletteFactors.get(k);
			currentState = (State) rouletteFactor.get(0);
			startingValue = (BigDecimal) rouletteFactor.get(1);
			endingValue = (BigDecimal) rouletteFactor.get(2);
			if (r >= startingValue.doubleValue() && r < endingValue.doubleValue()){

				return currentState;
			}
		}

		return null; // return tmstate from set

	};

	//This method is just for beam search
	public LinkedList chooseMulti(LinkedList toChooseFrom, int listSize)
	{
		HashSet selectedStates = new HashSet();
		HashSet remained = new HashSet();
		LinkedList toReturn = new LinkedList();
		if (toChooseFrom.isEmpty())
			return null;

		if (toChooseFrom.size() <= listSize){
			selectedStates.addAll(toChooseFrom);
			toReturn.add(selectedStates);
			toReturn.add(remained);
			return toReturn;
		}


		BigDecimal bestHeuristic; // best heuristic seen

	  BigDecimal totalFitnessValue = new BigDecimal(0);
		LinkedList fitnessPairs = new LinkedList();

		Iterator itr = toChooseFrom.iterator();
		State curr = (State) itr.next();
		//When Reaching the goal state, the HValue will be 0 -- not divisible !
		if (curr.getHValue().doubleValue() == 0){
			selectedStates.add(curr);
			toReturn.add(selectedStates);
			toReturn.add(remained);
			return toReturn;
		}

		double fitnessDoubleValue = 1/curr.getHValue().doubleValue();
		BigDecimal fitnessValue = new BigDecimal(fitnessDoubleValue); // and has the best heuristic
		totalFitnessValue = totalFitnessValue.add(fitnessValue);
		LinkedList fitnessPair = new LinkedList();
		fitnessPair.add(curr);
		fitnessPair.add(fitnessValue);
		fitnessPairs.add(fitnessPair);

		/**
		 * Iterate all sucessors and find their fitnessvalue, store them in pair (state,fitValue)
		*/
		while (itr.hasNext())
		{
			curr = (State) itr.next();
			fitnessDoubleValue = 1/curr.getHValue().doubleValue();
			fitnessValue = new BigDecimal(fitnessDoubleValue); // and has the best heuristic
			totalFitnessValue = totalFitnessValue.add(fitnessValue);
			fitnessPair = new LinkedList();
			fitnessPair.add(curr);
			fitnessPair.add(fitnessValue);
			fitnessPairs.add(fitnessPair);
		}

		/**
		 * Calculate the probability for each state
		 * In this random process simulation, I used the value range to implement
		*/
		LinkedList rouletteFactors = new LinkedList();
		BigDecimal startValue = new BigDecimal(0);
		BigDecimal endValue = new BigDecimal(0);
		BigDecimal valueRange = new BigDecimal(0);
		State current = null;
		LinkedList fitnessList = new LinkedList();
		double valueDoubleRange = 0;
		for (int i = 0; i < fitnessPairs.size(); i++){
			fitnessList = (LinkedList) fitnessPairs.get(i);
			current = (State) fitnessList.get(0);
			valueDoubleRange = ((BigDecimal) fitnessList.get(1)).doubleValue()/totalFitnessValue.doubleValue();
			valueRange = new BigDecimal(valueDoubleRange);
			endValue = endValue.add(valueRange);
			LinkedList rouletteFactor = new LinkedList();
			rouletteFactor.add(current);
			rouletteFactor.add(startValue);
			rouletteFactor.add(endValue);
			startValue = endValue;
			rouletteFactors.add(rouletteFactor);
		}

		/**
		 * Make selection
		 */
		while (selectedStates.size() < listSize)
		{
			double r = javaff.JavaFF.generator.nextDouble();
			BigDecimal startingValue = new BigDecimal(0);
			BigDecimal endingValue = new BigDecimal(0);
			State currentState = null;
			//System.out.println(r);
			for (int k = 0; k < rouletteFactors.size(); k++){
				LinkedList rouletteFactor = (LinkedList) rouletteFactors.get(k);
				currentState = (State) rouletteFactor.get(0);
				startingValue = (BigDecimal) rouletteFactor.get(1);
				endingValue = (BigDecimal) rouletteFactor.get(2);
				if (r >= startingValue.doubleValue() && r < endingValue.doubleValue()){
					selectedStates.add(currentState);
				}
				else{
					remained.add(currentState);
				}
			}
		}

		toReturn.add(selectedStates);
		toReturn.add(remained);
		return toReturn; // return tmstate from set

	};

};
