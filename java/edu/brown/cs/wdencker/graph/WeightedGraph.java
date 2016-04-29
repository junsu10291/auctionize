package edu.brown.cs.wdencker.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

/**
 * Class to represent a weighted graph. This graph can hold vertices and edges
 * of any type.
 * @param <V> A vertex type.
 * @param <E> An edge type, which should be Comparable.
 */
public class WeightedGraph<V, E extends Comparable<E>> implements Iterable<V> {
  private Map<V, Multimap<V, EdgeWeight<E>>> graph;

  /**
   * Create a new WeightedGraph of an arbitrary type.
   */
  public WeightedGraph() {
    graph = new HashMap<>();
  }

  /**
   * Add a vertex to the graph.
   * @param node The vertex to add to the graph.
   */
  public void addVertex(V node) {
    graph.put(node, HashMultimap.create());
  }

  /**
   * Add an undirected edge between two vertices.
   * @param first The first vertex.
   * @param second The second vertex.
   * @param weight The EdgeWeight between the two vertices.
   */
  public void addUndirectedEdge(V first, V second, EdgeWeight<E> weight) {
    if (!graph.containsKey(first)) {
      addVertex(first);
    }
    if (!graph.containsKey(second)) {
      addVertex(second);
    }
    graph.get(first).put(second, weight);
    graph.get(second).put(first, weight);
  }

  /**
   * Add an undirected edge between two vertices.
   * @param first The first vertex.
   * @param second The second vertex.
   * @param weight A weight of any Comparable type. (Must be a Number as well
   *          for Dijkstra's algorithm).
   */
  public void addUndirectedEdge(V first, V second, E weight) {
    addUndirectedEdge(first, second, new EdgeWeight<E>(weight));
  }

  /**
   * Add a directed edge between two vertices.
   * @param from The first vertex.
   * @param to The second vertex.
   * @param weight The EdgeWeight between the two vertices.
   */
  public void addDirectedEdge(V from, V to, EdgeWeight<E> weight) {
    if (!graph.containsKey(from)) {
      addVertex(from);
    }
    if (!graph.containsKey(to)) {
      addVertex(to);
    }
    graph.get(from).put(to, weight);
  }

  /**
   * Add a directed edge between two vertices.
   * @param from The first vertex.
   * @param to The second vertex.
   * @param weight A weight of any Comparable type. (Must be a Number as well
   *          for Dijkstra's algorithm).
   */
  public void addDirectedEdge(V from, V to, E weight) {
    addDirectedEdge(from, to, new EdgeWeight<E>(weight));
  }

  /**
   * Remove an edge between two vertices.
   * @param first The first vertex.
   * @param second The second vertex.
   * @param weight The weight to remove.
   */
  public void removeEdge(V first, V second, EdgeWeight<E> weight) {
    if (graph.containsKey(first) && graph.containsKey(second)) {
      graph.get(first).remove(second, weight);
      graph.get(second).remove(first, weight);
    }
  }

  /**
   * Remove an edge between two vertices.
   * @param first The first vertex.
   * @param second The second vertex.
   * @param weight The weight to remove, represented by a Comparable type.
   */
  public void removeEdge(V first, V second, E weight) {
    removeEdge(first, second, new EdgeWeight<E>(weight));
  }

  /**
   * Remove a vertex in the graph.
   * @param vertex The vertex to remove.
   */
  public void removeVertex(V vertex) {
    graph.remove(vertex);
    for (V other : graph.keySet()) {
      graph.get(other).removeAll(vertex);
    }
  }

  /**
   * Return a safe Multimap representing the edges from a given vertex.
   * @param from The vertex for which to return edges.
   * @return An ImmutableMultimap representing the edges from the vertex, or
   *         null if the vertex is not in the graph or has no edges.
   */
  public Multimap<V, EdgeWeight<E>> getEdges(V from) {
    Multimap<V, EdgeWeight<E>> edges = graph.get(from);
    if (edges == null) {
      return null;
    } else {
      // Return a safe version
      return ImmutableMultimap.copyOf(edges);
    }
  }

  /**
   * Return whether the input is a vertex in the graph.
   * @param vertex The input to check for membership.
   * @return True if the input is a vertex, and false otherwise.
   */
  public boolean isVertex(V vertex) {
    return graph.containsKey(vertex);
  }

  public int size() {
    return graph.keySet().size();
  }

  @Override
  public Iterator<V> iterator() {
    return graph.keySet().iterator();
  }

}
