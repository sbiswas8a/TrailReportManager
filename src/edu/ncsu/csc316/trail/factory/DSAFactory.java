package edu.ncsu.csc316.trail.factory;

//import edu.ncsu.csc316.dsa.data.Identifiable;
//import edu.ncsu.csc316.dsa.disjoint_set.DisjointSetForest;
//import edu.ncsu.csc316.dsa.disjoint_set.UpTreeDisjointSetForest;
//import edu.ncsu.csc316.dsa.graph.AdjacencyListGraph;
import edu.ncsu.csc316.dsa.graph.AdjacencyMapGraph;
//import edu.ncsu.csc316.dsa.graph.EdgeListGraph;
import edu.ncsu.csc316.dsa.graph.Graph;
import edu.ncsu.csc316.dsa.list.ArrayBasedList;
import edu.ncsu.csc316.dsa.list.List;
//import edu.ncsu.csc316.dsa.list.SinglyLinkedList;
//import edu.ncsu.csc316.dsa.list.positional.PositionalLinkedList;
//import edu.ncsu.csc316.dsa.list.positional.PositionalList;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.hashing.LinearProbingHashMap;
//import edu.ncsu.csc316.dsa.priority_queue.AdaptablePriorityQueue;
//import edu.ncsu.csc316.dsa.priority_queue.HeapAdaptablePriorityQueue;
//import edu.ncsu.csc316.dsa.priority_queue.HeapPriorityQueue;
//import edu.ncsu.csc316.dsa.priority_queue.PriorityQueue;
//import edu.ncsu.csc316.dsa.queue.ArrayBasedQueue;
//import edu.ncsu.csc316.dsa.queue.Queue;
import edu.ncsu.csc316.dsa.set.HashSet;
import edu.ncsu.csc316.dsa.set.Set;
import edu.ncsu.csc316.dsa.sorter.MergeSorter;
//import edu.ncsu.csc316.dsa.sorter.RadixSorter;
import edu.ncsu.csc316.dsa.sorter.Sorter;
//import edu.ncsu.csc316.dsa.stack.LinkedStack;
//import edu.ncsu.csc316.dsa.stack.Stack;

/**
 * Factory for creating new data structure and algorithm instances
 * 
 * @author Dr. King
 *
 */
public class DSAFactory {

	/**
	 * Returns a data structure that implements a map
	 * 
	 * @param <K>
	 *            - the key type
	 * @param <V>
	 *            - the value type
	 * @return a data structure that implements a map
	 */
	public static <K, V> Map<K, V> getMap() {
		return new LinearProbingHashMap<K, V>();
	}

	/**
	 * Returns a data structure that implements an index-based list
	 * 
	 * @param <E>
	 *            - the element type
	 * @return an index-based list
	 */
	public static <E> List<E> getIndexedList() {
		return getArrayBasedList();
	}

	/**
	 * Returns a data structure that implements an positional list
	 * 
	 * @param <E>
	 *            - the element type
	 * @return a positional list
	 */
//	public static <E> PositionalList<E> getPositionalList() {
//		return getPositionalLinkedList();
//	}

	/**
	 * Returns a comparison based sorter
	 * 
	 * @param <E>
	 *            - the element type
	 * @return a comparison based sorter
	 */
	public static <E extends Comparable<E>> Sorter<E> getComparisonSorter() {
		return getMergeSorter();
	}

	/**
	 * Returns a non-comparison based sorter
	 * 
	 * @param <E>
	 *            - the element type
	 * @return a non-comparison based sorter
	 */
//	public static <E extends Identifiable> Sorter<E> getNonComparisonSorter() {
//		return getRadixSorter();
//	}

	/**
	 * Returns a data structure that implements a stack
	 * 
	 * @param <E>
	 *            - the element type
	 * @return a stack
	 */
//	public static <E> Stack<E> getStack() {
//		return getLinkedStack();
//	}

	/**
	 * Returns a data structure that implements a queue
	 * 
	 * @param <E>
	 *            - the element type
	 * @return a stack
	 */
//	public static <E> Queue<E> getQueue() {
//		return getArrayBasedQueue();
//	}
	
	/**
	 * Returns a data structure that implements a Priority Queue
	 * @return a priority queue
	 * @param <K> is the generic type for the key
	 * @param <V> is the generic type for the value
	 */
//	public static <K extends Comparable<K>, V> PriorityQueue<K, V> getPriorityQueue() {
//		return new HeapPriorityQueue<K, V>();
//	}

	/**
	 * Returns a data structure that implements an Adaptable Priority Queue
	 * @return an adaptable priority queue
	 * @param <K> is the generic type for the key
	 * @param <V> is the generic type for the value
	 */
//	public static <K extends Comparable<K>, V> AdaptablePriorityQueue<K, V> getAdaptablePriorityQueue() {
//		return new HeapAdaptablePriorityQueue<K, V>();
//	}
	
	/**
	 * Returns a data structure that implements a Set
	 * @return a set
	 * @param <E> is the generic type
	 */
	public static <E> Set<E> getSet() {
		return new HashSet<E>();
	}
	
	/**
	 * Returns a data structure that implements a Disjoint Set
	 * @return a disjoint set
	 * @param <E> is the generic type
	 */
//	public static <E> DisjointSetForest<E> getDisjointSet() {
//		return new UpTreeDisjointSetForest<E>();
//	}
	
	/**
	 * Returns a data structure that implements an Undirected Graph
	 * @return an undirected graph
	 * @param <V> is the generic type for vertex
	 * @param <E> is the generic type for edge
	 */
	public static <V, E> Graph<V, E> getUndirectedGraph() {
		return new AdjacencyMapGraph<V, E>();
	}
	
	/**
	 * Returns a data structure that implements a directed graph
	 * @return a directed graph
	 * @param <V> is the generic type for vertex
	 * @param <E> is the generic type for edge
	 */
//	public static <V, E> Graph<V, E> getDirectedGraph() {
//		return new AdjacencyMapGraph<V, E>(true);
//	}
	
	/**
	 * Returns a singly linked list with front pointer
	 * 
	 * @return a singly linked list with front pointer
	 */
	private static <E> ArrayBasedList<E> getArrayBasedList() {
		return new ArrayBasedList<E>();
	}


//	/**
//	 * Returns a singly linked list with front pointer
//	 * 
//	 * @return a singly linked list with front pointer
//	 */
//	private static <E> SinglyLinkedList<E> getSinglyLinkedList() {
//		return new SinglyLinkedList<E>();
//	}

	/**
	 * Returns a positional linked list with a front pointer
	 * 
	 * @return a positional linked list with a front pointer
	 */
//	private static <E> PositionalLinkedList<E> getPositionalLinkedList() {
//		return new PositionalLinkedList<E>();
//	}

	/**
	 * Returns a mergesorter
	 * 
	 * @return a mergesorter
	 */
	private static <E extends Comparable<E>> Sorter<E> getMergeSorter() {
		return new MergeSorter<E>();
	}

	/**
	 * Returns a radix sorter
	 * 
	 * @return a radix sorter
	 */
//	private static <E extends Identifiable> Sorter<E> getRadixSorter() {
//		return new RadixSorter<E>();
//	}

	/**
	 * Returns a linked stack
	 * 
	 * @return a linked stack
	 */
//	private static <E> LinkedStack<E> getLinkedStack() {
//		return new LinkedStack<E>();
//	}

	/**
	 * Returns a linked queue
	 * 
	 * @return a linked queue
	 */
//	private static <E> ArrayBasedQueue<E> getArrayBasedQueue() {
//		return new ArrayBasedQueue<E>();
//	}
}