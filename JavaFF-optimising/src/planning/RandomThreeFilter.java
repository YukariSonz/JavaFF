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

package javaff.planning;

import javaff.data.Action;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import javaff.planning.HelpfulFilter;

public class RandomThreeFilter implements Filter
{
	private static RandomThreeFilter rf = null;

	protected HelpfulFilter hf;

	private RandomThreeFilter()
	{
		hf = HelpfulFilter.getInstance();
	}

	public static RandomThreeFilter getInstance()
	{
		if (rf == null) rf = new RandomThreeFilter(); // Singleton design pattern - return one central instance
		return rf;
	}

	public Set getActions(State S)
	{
		//Assumption Made: helpfulFiltered.size() > 3
		Set helpfulFiltered = hf.getActions(S);
		Set subset = new HashSet();
		int seed = helpfulFiltered.size();
		//Pretty Slow Approach
		if (helpfulFiltered.size() <=3){
			return helpfulFiltered;
		}
		else{
			while (subset.size() < 3){
				int randomNum = javaff.JavaFF.generator.nextInt(seed);
				int index = 0 ;
				for (Object state : helpfulFiltered){
					if (index == randomNum){
						subset.add(state);
					}
					index ++;
				}
			}

		}


		return subset;
	}

}
