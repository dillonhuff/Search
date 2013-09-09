package search

import org.junit.Assert._
import org.junit.Test

class BagOfWordsConverterTests {
	
	@Test
	def emptyDocGivesEmptyVector() = {
		val doc = new Document("")
		val converter = new BagOfWordsConverter(new WhitespaceTokenizer())
		val correct = new FeatureVector(Map.empty[Token, Double])
		assertEquals(correct, converter.convert(doc))
	}
	
	@Test
	def oneTokenDocument() = {
		val doc = new Document("cats")
		val converter = new BagOfWordsConverter(new WhitespaceTokenizer())
		val correct = new FeatureVector(Map[Token, Double](new Token("cats") -> 1.0))
		assertEquals(correct, converter.convert(doc))
	}
	
	@Test
	def multipleTokens() = {
		val doc = new Document("\t\tthIS This is REally the stuff man!\nIS")
		val converter = new BagOfWordsConverter(new WhitespaceTokenizer())
		val correct = new FeatureVector(
			Map(new Token("this") -> 2.0, new Token("is") -> 2.0, new Token("really") -> 1.0, 
			new Token("the") -> 1.0, new Token("stuff") -> 1.0, new Token("man!") -> 1.0))
		assertEquals(correct, converter.convert(doc))
	}

}