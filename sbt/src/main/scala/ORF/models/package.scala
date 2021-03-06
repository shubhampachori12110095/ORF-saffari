package ORF
package object models {

  /** returns the range of the data*/
  def dataRange(X: Vector[Vector[Double]]) = 
    Vector.range(0,X(0).size) map {j => 
      val colj = X map {x => x(j)}
      (colj.min, colj.max)
    }

  /** returns the unique class labels. assumes if there are n classes, the labels are {0,1,...,n-1}. */
  def dataClasses(y: Vector[Double]) = y.toSet.size

  /** normalizes the data X */
  def normalize(X: Vector[Vector[Double]]) = {
    val rng = dataRange(X)
    val n = X.size
    val k = X(0).size
    Vector.range(0,n) map { i =>
      Vector.range(0,k) map { j => 
        ( X(i)(j) - rng(j)._1 ) / ( rng(j)._2 - rng(j)._1)
      }
    }
  }

  /** times the execution of a block and returns what the block returns*/
  def timer[R](block: => R): R = {  
    val t0 = System.nanoTime()
    val result = block
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0) / 1E9 + "s")
    result
  }

  /** Parameters for the OR-Tree, OR-Foest
   *  @constructor create a set of parameters for ORT / ORF
   *  @param numClasses  number of classes in response. e.g. if numClasses == 3, then the responses should be one of {0,1,2}. For regression, set to 0.
   *  @param minSamples  the minimum number of samples a node needs to see before it is permitted to split
   *  @param minGain  the minimum gain (based on metric) that a split needs to achieve before split can occur
   *  @param gamma  the temporal weighting learning rate (>0). if age of tree > 1/gamma, the tree is thrown away. (default=0)
   *  @param numTests  the number of tests (split dimension, location) each node does. (default=10)
   *  @param xrng the range of the data X
   *  @param maxDepth the maximum depth allowed for a tree. Defaults to 30.
   */
  case class Param(minSamples: Int, minGain: Double, xrng: Vector[(Double,Double)], numClasses: Int = 0, numTests: Int = 10, gamma: Double = 0, maxDepth: Int = 30)

}
