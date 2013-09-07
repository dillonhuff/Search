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
	
	@Test
	def multByScalar() = {
		val v = new FeatureVector(Map[String, Double]("t" -> 34, "h" -> 0.3, "j" -> 5.6))
		val correct = new FeatureVector(Map[String, Double]("t" -> 68, "h" -> 0.6, "j" -> 11.2))
		val res = v.scalarMult(2.0)
	}
	
	@Test
	def subtract() = {
		val v1 = new FeatureVector(Map[String, Double]("b" -> 3, "c" -> 6, "d" -> 54))
		val v2 = new FeatureVector(Map[String, Double]("b" -> 2, "c" -> -2.5))
		val diff = v1.sub(v2)
		val correct = new FeatureVector(Map[String, Double]("b" -> 1, "c" -> 8.5, "d" -> 54))
		assertEquals(correct, diff)
	}
	
	@Test
	def vectorDistanceFromItselfIsZero() = {
		val v1 = new FeatureVector(Map[String, Double]("is" -> 1.2, "no" -> 3))
		assertEquals(0.0, v1.dist(v1), 0.0)
	}
	
	@Test
	def vectorDistanceFromEachOther() = {
		val v1 = new FeatureVector(Map[String, Double]("d" -> 2.0, "r" -> 1.0))
		val v2 = new FeatureVector(Map[String, Double]("d" -> 1.0))
		val correct = Math.sqrt(1.0 + 1.0)
		assertEquals(correct, v1.dist(v2), Math.pow(10, -8))
	}
	
	@Test
	def emptyVectorMagnitudeIsZero() = {
		val v = new FeatureVector(Map.empty[String, Double])
		assertEquals(0.0, v.magnitude, 0.0)
	}
	
	@Test
	def oneComponentVectorMag() = {
		val v = new FeatureVector(Map[String, Double]("d" -> 2.0))
		assertEquals(2.0, v.magnitude, 0.0)
	}
	
	@Test
	def cosSimOfZeroVecIsZero() = {
		val v = new FeatureVector(Map.empty[String, Double])
		assertEquals(0.0, v.cosSim(v), 0.0)
	}
	
	@Test
	def cosSimToSelfIsOne() = {
		val v = new FeatureVector(Map[String, Double]("t" -> 2.3, "q" -> 3.4))
		assertEquals(1.0, v.cosSim(v), Math.pow(10, -8))
	}
	
	@Test
	def cosSimOfDifferentVecs() = {
		val v1 = new FeatureVector(Map[String, Double]("v" -> 4.5, "z" -> -2.1))
		val v2 = new FeatureVector(Map[String, Double]("v" -> -0.3, "w" -> 3.4, "z" -> 2.3))
		assertEquals(-0.30236758828, v1.cosSim(v2), Math.pow(10, -8))
	}
}