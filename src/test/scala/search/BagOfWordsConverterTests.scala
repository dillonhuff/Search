package search

import org.junit.Assert._
import org.junit.Test

class BagOfWordsConverterTests {
	
	@Test
	def emptyDocGivesEmptyVector() = {
		val doc = new Document("")
		val converter = new BagOfWordsConverter(new WhitespaceTokenizer())
		val correct = new FeatureVector(Map.empty[String, Double])
		assertEquals(correct, converter.convert(doc))
	}
	
	@Test
	def oneTokenDocument() = {
		val doc = new Document("cats")
		val converter = new BagOfWordsConverter(new WhitespaceTokenizer())
		val correct = new FeatureVector(Map[String, Double]("cats" -> 1.0))
		assertEquals(correct, converter.convert(doc))
	}
	
	@Test
	def multipleTokens() = {
		val doc = new Document("\t\tthIS This is REally the stuff man!\nIS")
		val converter = new BagOfWordsConverter(new WhitespaceTokenizer())
		val correct = new FeatureVector(
			Map("this" -> 2.0, "is" -> 2.0, "really" -> 1.0, "the" -> 1.0, "stuff" -> 1.0, "man!" -> 1.0))
		assertEquals(correct, converter.convert(doc))
	}

}