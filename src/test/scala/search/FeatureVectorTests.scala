package search

import org.junit.Assert._
import org.junit.Test

class FeatureVectorTests {
	
	@Test
	def emptyVectorHasNoFeatures() = {
		val vec = new FeatureVector(Map.empty[Token, Double])
		assertEquals(0.0, vec.get(new Token("this")), 0.0)
	}
	
	@Test
	def getPresentFeatureValue() = {
		val vec = new FeatureVector(Map[Token, Double](new Token("hammock") -> 12.3))
		assertEquals(12.3, vec.get(new Token("hammock")), 0.0)
	}
	
	@Test
	def addEmptyVectors() = {
		val v1 = new FeatureVector(Map.empty[Token, Double])
		val v2 = new FeatureVector(Map.empty[Token, Double])
		val v3 = v1.add(v2)
		assertEquals(v1, v3)
	}
	
	@Test
	def addFirstNonEmpty() = {
		val v1 = new FeatureVector(Map[Token, Double](new Token("to") -> 23.4, new Token("the") -> 52.0))
		val v2 = new FeatureVector(Map.empty[Token, Double])
		val v3 = v1.add(v2)
		assertEquals(v1, v3)
	}
	
	@Test
	def addSecondNonEmpty() = {
		val v1 = new FeatureVector(Map.empty[Token, Double])
		val v2 = new FeatureVector(Map[Token, Double](new Token("not") -> 3, new Token("is") -> 56.3))
		val v3 = v1.add(v2)
		assertEquals(v2, v3)
	}
	
	@Test
	def addBothNonEmpty() = {
		val v1 = new FeatureVector(Map[Token, Double](new Token("h") -> 2, new Token("t") -> -23.4))
		val v2 = new FeatureVector(Map[Token, Double](new Token("e") -> 3, new Token("t") -> 3.0))
		val correct = new FeatureVector(Map[Token, Double](new Token("h") -> 2, new Token("e") -> 3, new Token("t") -> -20.4))
		val v3 = v1.add(v2)
		assertEquals(correct, v3)
	}
	
	@Test
	def multByScalar() = {
		val v = new FeatureVector(Map[Token, Double](new Token("t") -> 34, new Token("h") -> 0.3, new Token("j") -> 5.6))
		val correct = new FeatureVector(Map[Token, Double](new Token("t") -> 68, new Token("h") -> 0.6, new Token("j") -> 11.2))
		val res = v.scalarMult(2.0)
	}
	
	@Test
	def subtract() = {
		val v1 = new FeatureVector(Map[Token, Double](new Token("b") -> 3, new Token("c") -> 6, new Token("d") -> 54))
		val v2 = new FeatureVector(Map[Token, Double](new Token("b") -> 2, new Token("c") -> -2.5))
		val diff = v1.sub(v2)
		val correct = new FeatureVector(Map[Token, Double](new Token("b") -> 1, new Token("c") -> 8.5, new Token("d") -> 54))
		assertEquals(correct, diff)
	}
	
	@Test
	def vectorDistanceFromItselfIsZero() = {
		val v1 = new FeatureVector(Map[Token, Double](new Token("is") -> 1.2, new Token("no") -> 3))
		assertEquals(0.0, v1.dist(v1), 0.0)
	}
	
	@Test
	def vectorDistanceFromEachOther() = {
		val v1 = new FeatureVector(Map[Token, Double](new Token("d") -> 2.0, new Token("r") -> 1.0))
		val v2 = new FeatureVector(Map[Token, Double](new Token("d") -> 1.0))
		val correct = Math.sqrt(1.0 + 1.0)
		assertEquals(correct, v1.dist(v2), Math.pow(10, -8))
	}
	
	@Test
	def emptyVectorMagnitudeIsZero() = {
		val v = new FeatureVector(Map.empty[Token, Double])
		assertEquals(0.0, v.magnitude, 0.0)
	}
	
	@Test
	def oneComponentVectorMag() = {
		val v = new FeatureVector(Map[Token, Double](new Token("d") -> 2.0))
		assertEquals(2.0, v.magnitude, 0.0)
	}
	
	@Test
	def cosSimOfZeroVecIsZero() = {
		val v = new FeatureVector(Map.empty[Token, Double])
		assertEquals(0.0, v.cosSim(v), 0.0)
	}
	
	@Test
	def cosSimToSelfIsOne() = {
		val v = new FeatureVector(Map[Token, Double](new Token("t") -> 2.3, new Token("q") -> 3.4))
		assertEquals(1.0, v.cosSim(v), Math.pow(10, -8))
	}
	
	@Test
	def cosSimOfDifferentVecs() = {
		val v1 = new FeatureVector(Map[Token, Double](new Token("v") -> 4.5, new Token("z") -> -2.1))
		val v2 = new FeatureVector(Map[Token, Double](new Token("v") -> -0.3, new Token("w") -> 3.4, new Token("z") -> 2.3))
		assertEquals(-0.30236758828, v1.cosSim(v2), Math.pow(10, -8))
	}
}