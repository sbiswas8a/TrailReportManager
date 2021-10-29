package edu.ncsu.csc316.trail.manager;

import java.io.FileNotFoundException;
import java.util.Iterator;

import edu.ncsu.csc316.dsa.Weighted;
import edu.ncsu.csc316.dsa.graph.Graph;
import edu.ncsu.csc316.dsa.graph.Graph.Edge;
import edu.ncsu.csc316.dsa.graph.Graph.Vertex;
import edu.ncsu.csc316.dsa.graph.MinimumSpanningTreeUtil;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.list.positional.PositionalList;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.set.Set;
import edu.ncsu.csc316.trail.data.Attraction;
import edu.ncsu.csc316.trail.data.PotentialTrail;
import edu.ncsu.csc316.trail.factory.DSAFactory;
import edu.ncsu.csc316.trail.io.TrailReader;

/**
 * Manages analysis of potential trails to connect attractions.
 * 
 * @author Dr. King
 */
public class TrailManager {
	
//	/** Private Trail class that implements the weighted interface
//	 * 
//	 * @author Sumit Biswas
//	 *
//	 */
//	private static class Trail implements Weighted {
//
//		/** Constructor
//		 * @param length is the length of the trail
//		 */
//		public Trail(int length) {
//			this.length = length;
//		}
//		
//		/** Length of the trail */
//		private int length;
//		
//		@Override
//		public int getWeight() {
//			return length;
//		}
//		
//	}

	/** List containing the potential trails */
	private List<PotentialTrail> list;
	/** Graph built from the potential trails */
	private Graph<Attraction, Weighted> graph;
	
	/**
	 * Creates a new TrailManager
	 * 
	 * @param pathToTrailFile the path to the file containing trail data
	 * @throws FileNotFoundException    if the input file does not exist or cannot
	 *                                  be opened
	 * @throws IllegalArgumentException if the input file does not contain any trail
	 *                                  data
	 */
	public TrailManager(String pathToTrailFile) throws FileNotFoundException {
		list = TrailReader.loadPotentialTrails(pathToTrailFile);
		graph = buildGraph(list);
		TrailReportManager.setGraph(graph);
	}

	/**
	 * Builds a graph from the provided input list. Vertices represent Attractions
	 * Edges represent PotentialTrails
	 * 
	 * @param trailData the list of data for potential trails
	 * @return a Graph that models the provided trail data
	 */
	private static Graph<Attraction, Weighted> buildGraph(List<PotentialTrail> trailData) {
		Graph<Attraction, Weighted> toReturn = DSAFactory.getUndirectedGraph();
		Map<Attraction, Vertex<Attraction>> covered = DSAFactory.getMap();
		for (int i = 0; i < trailData.size(); i++) {
			PotentialTrail potential = trailData.get(i);
			Attraction one = potential.getAttractionOne();
			Attraction two = potential.getAttractionTwo();
			Vertex<Attraction> v1 = null, v2 = null;
			if (covered.get(one) == null) {
				v1 = toReturn.insertVertex(one);
				covered.put(one, v1);
			} else {
				v1 = covered.get(one);
			}
			if (covered.get(two) == null) {
				v2 = toReturn.insertVertex(two);
				covered.put(two, v2);
			} else {
				v2 = covered.get(two);
			}
			if (toReturn.getEdge(v1, v2) == null) {
				toReturn.insertEdge(v1, v2, potential);
			}
		}
		return toReturn;
	}

	/**
	 * Returns a Set of Attractions for which all connecting trails are longer than
	 * minTrailLength ( greater than (but not equal to) minTrailLength ).
	 * 
	 * @param minTrailLength the minimum trail length to use as a threshold
	 * @return a Set of Attractions for which restrooms should be located
	 */
	public Set<Attraction> getAttractionsWithLongTrails(int minTrailLength) {
		Set<Attraction> toReturn = DSAFactory.getSet();
		Iterable<Vertex<Attraction>> it = graph.vertices();
		for (Vertex<Attraction> ver : it) {
			boolean toAdd = true;
			Iterable<Edge<Weighted>> secondIt = graph.outgoingEdges(ver);
			for (Edge<Weighted> edge : secondIt) {
				if (edge.getElement().getWeight() <= minTrailLength) {
					toAdd = false;
					break;
				}
			}
			if (toAdd) {
				toReturn.add(ver.getElement());
			}
		}
		return toReturn;
	}

	/**
	 * Returns a PositionalList of Weighted edges that represents trails that
	 * produce the least costly solution to connect all attractions
	 * 
	 * @return a PositionalList of Weighted Edges represent the least costly trails
	 */
	public PositionalList<Edge<Weighted>> getLeastCostlyTrails() {
		return MinimumSpanningTreeUtil.kruskal(graph);
	}

	/**
	 * Return a Map that represents the number of trails that intersect at each
	 * specific attraction
	 * 
	 * @return a Map that represents the number of trails that intersect at each
	 *         specific attraction
	 */
	public Map<Attraction, Integer> getTrailIntersectionFrequencies() {
		Map<Attraction, Integer> toReturn = DSAFactory.getMap();
		PositionalList<Edge<Weighted>> edges = MinimumSpanningTreeUtil.kruskal(graph);
		Iterator<Edge<Weighted>> it = edges.iterator();
		while (it.hasNext()) {
			Vertex<Attraction>[] arr = graph.endVertices(it.next());
			Attraction one = arr[0].getElement();
			Attraction two = arr[1].getElement();
			Integer rOne = toReturn.get(one);
			Integer rTwo = toReturn.get(two);
			if (rOne == null) {
				toReturn.put(one, 1);
			} else {
				toReturn.put(one, rOne + 1);
			}
			if (rTwo == null) {
				toReturn.put(two, 1);
			} else {
				toReturn.put(two, rTwo + 1);
			}
		}
		return toReturn;
	}
}