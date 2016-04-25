package edu.brown.cs.nbrennan.graph;

import java.util.Objects;

/**
 * Class to represent a step in a path between two vertices in a graph.
 * @param <V> A vertex type.
 * @param <E> An edge type.
 */
public class Step<V, E extends Comparable<E>> {
  private V from, to;
  private EdgeWeight<E> distance;

  /**
   * Create a new Step.
   * @param from The from vertex.
   * @param to The to vertex.
   * @param distance The distance between the two vertices.
   */
  public Step(V from, V to, EdgeWeight<E> distance) {
    this.from = from;
    this.to = to;
    this.distance = distance;
  }

  /**
   * Return the from vertex.
   * @return The from vertex of the Step.
   */
  public V getFrom() {
    return from;
  }

  /**
   * Return the to vertex.
   * @return The to vertex of the Step.
   */
  public V getTo() {
    return to;
  }

  /**
   * Return the distance of the step.
   * @return The EdgeWeight representing the distance of the Step.
   */
  public EdgeWeight<E> getDistance() {
    return distance;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "(From: " + from + " To: " + to + " Distance: " + distance + ")";
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Step)) {
      return false;
    }
    @SuppressWarnings("rawtypes")
    Step s = (Step) obj;
    return s.getFrom().equals(from) && s.getTo().equals(to)
        && s.getDistance().getWeight().equals(distance.getWeight());
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(from, to, distance.getWeight());
  }
}
