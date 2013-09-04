package search

import org.junit.Assert._
import org.junit.Test

class FeatureVectorTests {
	
	@Test
	def emptyVectorHasNoFeatures() = {
		val vec = new FeatureVector(Map.empty[String, Double])
		assertEquals(0.0, vec.get("this"), 0.0)
	}
	
	@Test
	def getPresentFeatureValue() = {
		val vec = new FeatureVector(Map[String, Double]("hammock" -> 12.3))
		assertEquals(12.3, vec.get("hammock"), 0.0)
	}
	
	@Test
	def addEmptyVectors() = {
		val v1 = new FeatureVector(Map.empty[String, Double])
		val v2 = new FeatureVector(Map.empty[String, Double])
		val v3 = v1.add(v2)
		assertEquals(v1, v3)
	}
	
	@Test
	def addFirstNonEmpty() = {
		val v1 = new FeatureVector(Map[String, Double]("to" -> 23.4, "the" -> 52.0))
		val v2 = new FeatureVector(Map.empty[String, Double])
		val v3 = v1.add(v2)
		assertEquals(v1, v3)
	}
	
	@Test
	def addSecondNonEmpty() = {
		val v1 = new FeatureVector(Map.empty[String, Double])
		val v2 = new FeatureVector(Map[String, Double]("not" -> 3, "is" -> 56.3))
		val v3 = v1.add(v2)
		assertEquals(v2, v3)
	}
	
	@Test
	def addBothNonEmpty() = {
		val v1 = new FeatureVector(Map[String, Double]("h" -> 2, "t" -> -23.4))
		val v2 = new FeatureVector(Map[String, Double]("e" -> 3, "t" -> 3.0))
		val correct = new FeatureVector(Map[String, Double]("h" -> 2, "e" -> 3, "t" -> -20.4))
		val v3 = v1.add(v2)
		assertEquals(correct, v3)
	}
}