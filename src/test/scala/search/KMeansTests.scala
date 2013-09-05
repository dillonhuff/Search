package search

import org.junit.Assert._
import org.junit.Test

class KMeansTests {
		
		@Test
		def populateTwoClusters() = {
			val c1 = new FeatureVector(Map[String, Double]("y" -> 2))
			val c2 = new FeatureVector(Map[String, Double]("t" -> -3))
			val centroids = Vector[FeatureVector](c1, c2)
			val v1 = new FeatureVector(Map[String, Double]("y" -> 2.1))
			val v2 = new FeatureVector(Map[String, Double]("t" -> -3.02))
			val dataset = Vector[FeatureVector](v1, v2)
			val correct = Map[FeatureVector, Vector[FeatureVector]](c1 -> Vector[FeatureVector](v1), 
													c2 -> Vector[FeatureVector](v2))
			val res = KMeans.populateClusters(centroids, dataset)
			assertEquals(correct, res)
		}
		
		@Test
		def computeCentroid() = {
			val v1 = new FeatureVector(Map[String, Double]("t" -> -2))
			val v2 = new FeatureVector(Map[String, Double]("t" -> -4))
			val cluster = Vector(v1, v2)
			val correct = new FeatureVector(Map[String, Double]("t" -> -3))
			val res = KMeans.computeCentroid(cluster)
			assertEquals(correct, res)
		}
}