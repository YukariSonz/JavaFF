/************************************************************************
 * Strathclyde Planning Group,
 * Department of Computer and Information Sciences,
 * University of Strathclyde, Glasgow, UK
 * http://planning.cis.strath.ac.uk/
 *
 * Copyright 2007, Keith Halsey
 * Copyright 2008, Andrew Coles and Amanda Smith
 *
 * (Questions/bug reports now to be sent to Andrew Coles)
 *
 * This file is part of JavaFF.
 *
 * JavaFF is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * JavaFF is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JavaFF.  If not, see <http://www.gnu.org/licenses/>.
 *
 ************************************************************************/

package javaff.search;

import javaff.planning.State;
import javaff.planning.Filter;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.LinkedList;
import java.util.Comparator;
import java.math.BigDecimal;

import java.util.Hashtable;
import java.util.Iterator;
import javaff.search.SuccessorSelector;


public class HillClimbingSearch extends Search
{
	//protected BigDecimal bestHValue;

	protected Hashtable closed;
	protected LinkedList open;
	protected Filter filter = null;
	protected int maxDepth = 30; //Default Value
	protected SuccessorSelector successorSelector = null;

	public HillClimbingSearch(State s)
	{
		this(s, new HValueComparator());
	}

	public HillClimbingSearch(State s, Comparator c)
	{
		super(s);
		setComparator(c);

		closed = new Hashtable();
		open = new LinkedList();
	}

	public void setFilter(Filter f)
	{
		filter = f;
	}

	public void setMaxDepth(int md)
	{
		maxDepth = md;
	}

	public void setSelector(SuccessorSelector s)
	{
		successorSelector = s;
	}

	public State removeNext()
	{

		return (State) ((LinkedList) open).removeFirst();
	}

	public boolean needToVisit(State s) {
		Integer Shash = new Integer(s.hashCode()); // compute hash for state
		State D = (State) closed.get(Shash); // see if its on the closed list

		if (closed.containsKey(Shash) && D.equals(s)) return false;  // if it is return false

		closed.put(Shash, s); // otherwise put it on
		return true; // and return true
	}

	public State search() {
		int depth = 0;

		if (start.goalReached()) { // wishful thinking
			return start;
		}
		//BigDecimal bestHValue = null;
		needToVisit(start); // dummy call (adds start to the list of 'closed' states so we don't visit it again

		open.add(start); // add it to the open lbestHValueist
		//bestHValue = start.getHValue(); // and take its heuristic value as the best so far

		//javaff.JavaFF.infoOutput.println("initial heuristic: " + bestHValue);

		while (!open.isEmpty() && depth < maxDepth) // whilst still states to consider
		{
			State s = removeNext(); // get the next one

			Set successors = s.getNextStates(filter.getActions(s)); // and find its neighbourhood

			if (successorSelector == null){
				Iterator succItr = successors.iterator();
				BigDecimal bestHValue = s.getHValue(); //Need to check if the heuristic we want be inf or the heuristic value of current node

				while (succItr.hasNext()) {
					State succ = (State) succItr.next(); // next successor

				//NOTE: It's possible that the heuristic value of current successor is greater than bestHValue i.e.  succ.getHValue().compareTo(bestHValue) == 1
					if (needToVisit(succ)) {
						if (succ.goalReached()) { // if we've found a goal state - return it as the solution
							return succ;
						} else if (succ.getHValue().compareTo(bestHValue) < 0) {
							// if we've found a state with a better heuristic value than the best seen so far

							bestHValue = succ.getHValue(); // note the new best avlue
							javaff.JavaFF.infoOutput.println(bestHValue);
							open = new LinkedList(); // clear the open list
							open.add(succ); // put this on it
						} else if (succ.getHValue().compareTo(bestHValue) == 0){
							open.add(succ); // otherwise, add to the open list
						}
						else{
							continue;
						}
					}
				}

				if (open.size() <= 0){
					open = new LinkedList(); // Alternative solution: open.clear(), actually I don't really want to create that many LinkedList instances
				}
				else{
					int seed = open.size();
					int randomNum = javaff.JavaFF.generator.nextInt(seed);
					State selected = (State) open.get(randomNum);
					open = new LinkedList();
					open.add(selected);
				}

			}
			else{
				/**
				if (successors.size() == 0){
					break;              // Prevent null pointer
				}
				*/
				State selected_succ = successorSelector.choose(successors);
				//successors.add(selected_succ);               // Should I ?
				open = new LinkedList();
				open.add(selected_succ);
				if (selected_succ.goalReached()){
					return selected_succ;
				}
			}
			/**
			if (open.size() <= 0){
				open = new LinkedList(); // Alternative solution: open.clear(), actually I don't really want to create that many LinkedList instances
			}
			else{
				int seed = open.size();
				int randomNum = javaff.JavaFF.generator.nextInt(seed);
				State selected = (State) open.get(randomNum);
				open = new LinkedList();
				open.add(selected);
			}
			*/


			depth++;

			//End of finding neighborhood of current successor

		}


		return null;
	}
}
