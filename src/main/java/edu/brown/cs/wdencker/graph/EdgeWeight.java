package edu.brown.cs.wdencker.graph;

/**
 * Class to represent an EdgeWeight in a graph.
 * @param <E> A type which is Comparable. If Dijkstra's algorithm functionality
 *          is desired, E should also be a Number.
 */
public class EdgeWeight<E extends Comparable<E>>
    implements Comparable<EdgeWeight<E>> {
  private E weight;

  /**
   * Create a new EdgeWeight.
   * @param weight The weight at which to set the edge.
   */
  public EdgeWeight(E weight) {
    this.weight = weight;
  }

  /**
   * Get the weight of the EdgeWeight.
   * @return The weight of the EdgeWeight.
   */
  public E getWeight() {
    return weight;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "EW: " + weight.toString();
  }

  /** {@inheritDoc} */
  @Override
  public int compareTo(EdgeWeight<E> edge) {
    return weight.compareTo(edge.getWeight());
  }
}
