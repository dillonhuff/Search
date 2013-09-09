package search

import org.junit.Assert._
import org.junit.Test

class KMeansTests {

	@Test
	def fullKMeansTest() = {
		val v1 = new FeatureVector(Map[Token, Double](new Token("x") -> 1.0))
		val v2 = new FeatureVector(Map[Token, Double](new Token("x") -> 1.2, new Token("y") -> 0.01))
		val v3 = new FeatureVector(Map[Token, Double](new Token("x") -> -12.2, new Token("y") -> -3.4))
		val v4 = new FeatureVector(Map[Token, Double](new Token("x") -> -11, new Token("y") -> -3.0))
		val vecs = Vector(v1, v2, v3, v4)
		val clusters = KMeans.cluster(vecs, 2)
		val correct = Set(Vector(v1, v2), Vector(v3, v4))
		assertEquals(correct, clusters)
	}
		
	@Test
	def populateTwoClusters() = {
		val c1 = new FeatureVector(Map[Token, Double](new Token("y") -> 2))
		val c2 = new FeatureVector(Map[Token, Double](new Token("t") -> -3))
		val centroids = Vector[FeatureVector](c1, c2)
		val v1 = new FeatureVector(Map[Token, Double](new Token("y") -> 2.1))
		val v2 = new FeatureVector(Map[Token, Double](new Token("t") -> -3.02))
		val dataset = Vector[FeatureVector](v1, v2)
		val correct = Map[FeatureVector, Vector[FeatureVector]](c1 -> Vector[FeatureVector](v1), 
												c2 -> Vector[FeatureVector](v2))
		val res = KMeans.populateClusters(centroids, dataset)
		assertEquals(correct, res)
	}
		
	@Test
	def computeCentroid() = {
		val v1 = new FeatureVector(Map[Token, Double](new Token("t") -> -2))
		val v2 = new FeatureVector(Map[Token, Double](new Token("t") -> -4))
		val cluster = Vector(v1, v2)
		val correct = new FeatureVector(Map[Token, Double](new Token("t") -> -3))
		val res = KMeans.computeCentroid(cluster)
		assertEquals(correct, res)
	}
}