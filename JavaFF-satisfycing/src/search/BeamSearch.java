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
import java.util.Collections;

import javaff.search.SuccessorSelector;
import javaff.search.RouletteSuccessorSelector; //Special Case Here

public class BeamSearch extends Search
{
	protected BigDecimal bestHValue;

	protected Hashtable closed;
	protected LinkedList open;
	protected Filter filter = null;

	//protected SuccessorSelector successorSelector = null; //N/A in this searching
	protected RouletteSuccessorSelector rs = null;
	protected int depthBound = 10000;
	protected int restartBound = 10000;
	protected int beamSize = 5;

	//protected State bestState = null;


	public BeamSearch(State s)
	{
		this(s, new HValueComparator());
	}

	public BeamSearch(State s, Comparator c)
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

	public void setSelector(RouletteSuccessorSelector s)
	{
		rs = s;
	}

	public void setDepthBound(int db)
	{
		depthBound = db;
	}

	public void setRestartBound(int rb)
	{
		restartBound = rb;
	}

	public void setBeamSize(int bs)
	{
		beamSize = bs;
	}



	public State search() {
		int depth = 0;
		int restarted = 0;


		if (start.goalReached()) { // wishful thinking
			return start;
		}
		//BigDecimal bestHValue = null;
		needToVisit(start); // dummy call (adds start to the list of 'closed' states so we don't visit it again

		open.add(start); // add it to the open lbestHValueist
		//bestHValue = start.getHValue(); // and take its heuristic value as the best so far
		//javaff.JavaFF.infoOutput.println("initial heuristic: " + bestHValue);
		LinkedList remainBeam = new LinkedList();
		while (restarted < restartBound)
		{
			while (!open.isEmpty() && depth < depthBound) // whilst still states to consider
			{
				LinkedList currentBeam = new LinkedList();
				for (Object state : open){
					State s = (State) state;
					Set successors = s.getNextStates(filter.getActions(s)); // and find its neighbourhood
						Iterator succItr = successors.iterator();
						//BigDecimal bestHValue = s.getHValue(); //Need to check if the heuristic we want be inf or the heuristic value of current node
						while (succItr.hasNext()) {
							State succ = (State) succItr.next(); // next successor

						//NOTE: It's possible that the heuristic value of current successor is greater than bestHValue i.e.  succ.getHValue().compareTo(bestHValue) == 1
							if (needToVisit(succ)) {
								if (succ.goalReached()) { // if we've found a goal state - return it as the solution
									return succ;
								}
								else{
									currentBeam.add(succ);
								}
							}
						}
					}


					if (currentBeam.size() <= 0){
						if (remainBeam.size() > 0){
							open = remainBeam; // Restart from the remainBeam nodes
						}
						else{
							return null;
						}
						//return null;
					}
					else{
						if (rs == null){
							Collections.sort(currentBeam,comp);
							open = new LinkedList();
							if (currentBeam.size() >= beamSize){
								for (int i = 0; i < beamSize; i++){
									open.add(currentBeam.removeFirst());
								}
								remainBeam = currentBeam;
							}
							else{
								open = currentBeam;
							}
						}
						else{
							LinkedList rsResult = rs.chooseMulti(currentBeam, beamSize);
							open = new LinkedList();
							remainBeam = new LinkedList();
							Set selected = (Set) rsResult.removeFirst();
							Set remained = (Set) rsResult.removeFirst();
							open.addAll(selected);
							remainBeam.addAll(remained);
							//remainBeam = currentBeam - open;
						}
				}
				depth++;
			}
			restarted++;
		}
		return null;
	}

}
