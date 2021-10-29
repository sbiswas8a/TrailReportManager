package edu.ncsu.csc316.trail.manager;

import java.io.FileNotFoundException;
import java.util.Iterator;

import edu.ncsu.csc316.dsa.Position;
import edu.ncsu.csc316.dsa.Weighted;
import edu.ncsu.csc316.dsa.graph.Graph;
import edu.ncsu.csc316.dsa.graph.Graph.Edge;
import edu.ncsu.csc316.dsa.graph.Graph.Vertex;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.list.positional.PositionalList;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.Map.Entry;
import edu.ncsu.csc316.dsa.set.Set;
import edu.ncsu.csc316.dsa.sorter.Sorter;
import edu.ncsu.csc316.trail.data.Attraction;
import edu.ncsu.csc316.trail.factory.DSAFactory;

/**
 * Creates String Reports of trail information for the user interface
 * 
 * @author Dr. King
 */
public class TrailReportManager {

	/** TrailManager instance */
	private TrailManager manager;
	/** Graph built from the potential trails */
	private static Graph<Attraction, Weighted> graph;
	
	/** Sets the graph in the TrailReportManager to the given graph
	 * 
	 * @param g is the given graph
	 */
	public static void setGraph(Graph<Attraction, Weighted> g) {
		graph = g;
	}

	/**
	 * Constructs a new TrailReportManager to facilitate generation of String
	 * reports for the TrailManager user interface.
	 * 
	 * @param pathToTrailFile the path to the file that contains trail data
	 * @throws FileNotFoundException    if the file does not exist or cannot be read
	 * @throws IllegalArgumentException if the file does not contain any trail data
	 */
	public TrailReportManager(String pathToTrailFile) throws FileNotFoundException {
		manager = new TrailManager(pathToTrailFile);
	}

	/**
	 * Returns a report of the least costly trails to connect all attractions
	 * 
	 * @param costPerFoot the cost (in USD $) per foot of trail construction
	 * @return a String report of the least costly trails to connect all attractions
	 */
	public String getLeastCostlyTrailsReport(double costPerFoot) {
		if (costPerFoot <= 0) {
			return "Construction cost per linear foot must be > $0.00.";
		}
		PositionalList<Edge<Weighted>> list = manager.getLeastCostlyTrails();
		Iterable<Position<Edge<Weighted>>> it = list.positions();
		List<Trail> tList = DSAFactory.getIndexedList();
		Sorter<Trail> sorter = DSAFactory.getComparisonSorter();
		String cost = String.format("%.2f", costPerFoot);
		
		for (Position<Edge<Weighted>> pos : it) {
			Vertex<Attraction>[] arr = graph.endVertices(pos.getElement());
			tList.addLast(new Trail(arr[0].getElement(), arr[1].getElement(), pos.getElement().getElement().getWeight(), costPerFoot));
		}
		
		sorter.sortList(tList);
		
		double total = 0;
		StringBuilder sb = new StringBuilder("Minimum Trails for $ ($");
		sb.append(cost);
		sb.append(" per linear foot) [\n");
		for (int i = 0; i < tList.size(); i++) {
			Trail t = tList.get(i);
			sb.append(t.toString());
			total += t.getCost();
		}
		sb.append("]");
		cost = String.format("%.2f", total);
		sb.insert(20, cost);
		
		return sb.toString();
	}

	/**
	 * Returns a report of the attractions that should be considered for restroom
	 * locations.
	 * 
	 * @param minTrailLength the minimum trail length to use as a threshold for
	 *                       determining restroom locations
	 * @return a String report of the attractions that have connecting trails with
	 *         lengths longer than the minimum trail length threshold
	 */
	public String getRestroomLocations(int minTrailLength) {
		if (minTrailLength <= 0) {
			return "Trail length must be > 0 feet.";
		}
		Set<Attraction> set = manager.getAttractionsWithLongTrails(minTrailLength);
		if (set.isEmpty()) {
			return "No attractions are endpoints of trails longer than " + minTrailLength + " feet.";
		}
		StringBuilder sb = new StringBuilder("Attractions with adjacent trails longer than " + minTrailLength + " feet [\n");
		Iterator<Attraction> it = set.iterator();
		Sorter<Attraction> sorter = DSAFactory.getComparisonSorter();
		List<Attraction> aList = DSAFactory.getIndexedList();
		while (it.hasNext()) {
			aList.addLast(it.next());
		}
		sorter.sortList(aList);
		for (int i = 0; i < aList.size(); i++) {
			sb.append("   ");
			sb.append(aList.get(i).getName());
			sb.append('\n');
		}
		sb.append(']');
		return sb.toString();
	}

	/**
	 * Returns a report of the attractions that should be considered for trail
	 * navigation sign locations.
	 * 
	 * @return a String report of the attractions that have at least 3 connecting
	 *         trails in the set of least costly trails
	 */
	public String getTrailSignLocations() {
		Map<Attraction, Integer> map = manager.getTrailIntersectionFrequencies();
		int count = 0;
		Iterable<Entry<Attraction, Integer>> it = map.entrySet();
		List<SignLocation> sList = DSAFactory.getIndexedList();
		for (Entry<Attraction, Integer> entry : it) {
			int val = entry.getValue();
			if (val > 2) {
				count++;
				sList.addLast(new SignLocation(entry));
			}	
		}
		if (count == 0) {
			return "No attractions need trail navigation signs.";
		}
		Sorter<SignLocation> sorter = DSAFactory.getComparisonSorter();
		sorter.sortList(sList);
		StringBuilder sb = new StringBuilder("Attractions that need a trail navigation sign [\n");
		for (int i = 0; i < sList.size(); i++) {
			sb.append("   ");
			sb.append(sList.get(i).toString());
		}
		sb.append(']');
		return sb.toString();
	}
	
	/** Private class that represents Attractions with Trail Signs
	 * 
	 * @author Sumit Biswas
	 *
	 */
	private class SignLocation implements Comparable<SignLocation> {
		
		/** Name of the attraction */
		private String name;
		/** Number of intersecting trails at the location */
		private int numTrails;
		
		/** Constructor for SignLocation
		 * 
		 * @param entry is the MapEntry containing the Attraction
		 * and the number of intersecting trails at that Attraction
		 */
		public SignLocation(Entry<Attraction, Integer> entry) {
			this.name = entry.getKey().getName();
			this.numTrails = entry.getValue();
		}
		
		/** Returns the number of intersecting trails at this location
		 * 
		 * @return returns the number of intersecting trails at this location
		 */
		public int getNumTrails() {
			return this.numTrails;
		}
		
		/** Returns the name of the attraction at this location
		 * 
		 * @return returns the name of the attraction at this location
		 */
		public String getName() {
			return this.name;
		}
		
		@Override
		public int compareTo(SignLocation other) {
			if (other.getNumTrails() > this.numTrails) {
				return 1;
			} else if (other.getNumTrails() < this.numTrails) {
				return -1;
			} else {
				return this.name.compareTo(other.getName());
			}
		}
		
		@Override
		public String toString() {
			return this.name + ": " + this.numTrails + " intersecting trails\n";
		}
	}
	
	/** Private class that represents a Trail
	 * 
	 * @author Sumit Biswas
	 *
	 */
	private class Trail implements Comparable<Trail> {
		
		/** Name of the attraction on one end of the Trail */
		private String attractionOne;
		/** Name of the attraction on the other end of the Trail */
		private String attractionTwo;
		/** Length of the trail in feet*/
		private int trailLength;
		/** Cost per foot for the construction of the trail */
		private double costPerFoot;
		/** Integer that keeps track of the main attraction */
		private int main;
		
		public Trail(Attraction one, Attraction two, int trailLength, double costPerFoot) {
			this.attractionOne = one.getName();
			this.attractionTwo = two.getName();
			this.trailLength = trailLength;
			this.costPerFoot = costPerFoot;
			if (attractionOne.compareTo(attractionTwo) < 1) {
				main = 1;
			} else {
				main = 2;
			}
		}
		
		/** Returns the name of the attraction which would come earlier if sorted alphabetically
		 * 
		 * @return returns the name of the attraction
		 */
		public String getMainAttraction() {
			if (attractionOne.compareTo(attractionTwo) < 1) {
				return attractionOne;
			}
			return attractionTwo;
		}
		
		/** Returns the length of the trail
		 * 
		 * @return returns the length of the trail
		 */
		public int getLength() {
			return this.trailLength;
		}
		
		/** Returns the cost for constructing this trail
		 * 
		 * @return returns the cost for constructing this trail
		 */
		public double getCost() {
			return this.costPerFoot * this.trailLength;
		}
		
		@Override
		public int compareTo(Trail o) {
			if (o.getLength() < this.trailLength) {
				return 1;
			} else if (o.getLength() > this.trailLength) {
				return -1;
			} else {
				return getMainAttraction().compareTo(o.getMainAttraction());
			}
		}
		
		@Override
		public String toString() {
			String c = String.format("%.2f", this.trailLength * this.costPerFoot);
			StringBuilder tS = new StringBuilder("   from ");
			if (main == 1) {
				tS.append(attractionOne);
			} else {
				tS.append(attractionTwo);
			}
			tS.append(" to ");
			if (main == 1) {
				tS.append(attractionTwo);
			} else {
				tS.append(attractionOne);
			}
			tS.append(" (");
			tS.append(this.trailLength);
			tS.append(" feet for $");
			tS.append(c);
			tS.append(")\n");
			return tS.toString();
		}
	}
}