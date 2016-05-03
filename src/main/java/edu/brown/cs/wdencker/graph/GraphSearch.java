package edu.brown.cs.wdencker.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Set;

import com.google.common.collect.Maps;

/**
 * Class to hold graph algorithms.
 */
public final class GraphSearch {
  /* Class cannot be instantiated */
  private GraphSearch() {
  };

  /**
   * Find the shortest path between two vertices in a graph.
   * @param start The starting vertex.
   * @param finish The ending vertex.
   * @param graph The graph on which to run Dijkstra's algorithm.
   * @param <V> The vertex type of the graph.
   * @return A List of Steps of the shortest path.
   */
  public static <V> List<Step<V, Double>> dijkstraShortestPath(V start,
      V finish, WeightedGraph<V, Double> graph) {
    if (!graph.isVertex(start) || !graph.isVertex(finish)) {
      throw new NoSuchElementException();
    }
    // Maintain a priority queue of possible shortest paths
    PriorityQueue<Entry<V, Double>> queue = new PriorityQueue<>(byEdgeWeight());

    // Keep track of current best distances
    Map<V, Double> distances = new HashMap<>();

    // Keep track of the vertices for which we've found the shortest path
    Set<V> found = new HashSet<>();

    // Keep track of path steps for each node
    Map<V, Entry<V, EdgeWeight<Double>>> previous = new HashMap<>();

    // Start is a distance of 0 from itself
    queue.add(Maps.immutableEntry(start, 0.0));

    double dist;
    while (!queue.isEmpty()) {
      Entry<V, Double> min = queue.remove();
      if (found.contains(min.getKey())) {
        continue;
      }
      // This is the shortest distance to some vertex
      distances.put(min.getKey(), min.getValue());
      found.add(min.getKey());

      if (min.getKey().equals(finish)) {
        // We're at the end vertex
        break;
      }

      for (Entry<V, EdgeWeight<Double>> edge : graph.getEdges(min.getKey())
          .entries()) {
        if (found.contains(edge.getKey())) {
          // Already found the shortest path for this node
          continue;
        }
        // Calculate the total distance
        dist = min.getValue() + edge.getValue().getWeight();
        if (!distances.containsKey(edge.getKey())
            || dist < distances.get(edge.getKey())) {
          // This distance is better than the currently known for some vertex
          queue.add(Maps.immutableEntry(edge.getKey(), dist));
          distances.put(edge.getKey(), dist);
          previous.put(edge.getKey(),
              Maps.immutableEntry(min.getKey(), edge.getValue()));
        }
      }
    }
    if (found.contains(finish)) {
      List<Step<V, Double>> steps = new ArrayList<>();
      V curr = finish;
      Entry<V, EdgeWeight<Double>> step = previous.get(finish);
      while (step != null) {
        // Piece together the steps of the path
        steps.add(new Step<V, Double>(step.getKey(), curr, step.getValue()));
        curr = step.getKey();
        step = previous.get(curr);
      }
      // Path currently in reverse order, reverse it
      Collections.reverse(steps);
      return steps;
    } else {
      // There is no path to the end node
      return Collections.emptyList();
    }
  }

  public static <V> List<Step<V, Double>> bellmanFord(V start, V finish,
      WeightedGraph<V, Double> graph) {
    Map<V, Double> distances = new HashMap<>();
    Map<V, Entry<V, EdgeWeight<Double>>> previous = new HashMap<>();
    for (V node : graph) {
      distances.put(node, Double.POSITIVE_INFINITY);
    }
    distances.put(start, 0.0);
    Map<V, Double> scratch = new HashMap<>();
    for (int k = 1; k <= graph.size(); k++) {
      scratch.putAll(distances);
      for (V node : graph) {
        for (Map.Entry<V, EdgeWeight<Double>> edge : graph.getEdges(node)
            .entries()) {
          double previousBest = scratch.get(edge.getKey());
          double check = edge.getValue().getWeight() + distances.get(node);
          if (check < previousBest) {
            // This distance is better than the currently known for some vertex
            scratch.put(edge.getKey(), check);
            previous.put(edge.getKey(),
                Maps.immutableEntry(node, edge.getValue()));
          } else {
            scratch.put(edge.getKey(), previousBest);
          }
        }
      }
      Map<V, Double> temp = distances;
      distances = scratch;
      scratch = temp;
    }
    List<Step<V, Double>> steps = new ArrayList<>();
    V curr = finish;
    Entry<V, EdgeWeight<Double>> step = previous.get(finish);
    while (step != null) {
      // Piece together the steps of the path
      steps.add(new Step<V, Double>(step.getKey(), curr, step.getValue()));
      curr = step.getKey();
      step = previous.get(curr);
    }
    // Path currently in reverse order, reverse it
    Collections.reverse(steps);
    return steps;
  }

  /*
   * Edge comaparator for the purposes of the PriorityQueue in Dijkstra's
   * algorithm.
   */
  private static <V, E extends Comparable<E>> Comparator<Entry<V, E>> byEdgeWeight() {
    return new Comparator<Map.Entry<V, E>>() {
      @Override
      public int compare(Entry<V, E> edge1, Entry<V, E> edge2) {
        return edge1.getValue().compareTo(edge2.getValue());
      }
    };
  }
}
