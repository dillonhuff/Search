package search

import org.junit.Assert._
import org.junit.Test

class TFIDFConverterTests {
      
      @Test
      def convertEmptyDoc() = {
      	  val doc = new Document("")
	  val tfIdfConv = new TFIDFConverter(new WhitespaceTokenizer(), Map.empty[Token, Int], 1)
	  val correct = new FeatureVector(Map.empty[Token, Double])
	  assertEquals(correct, tfIdfConv.convert(doc))
      }

      @Test
      def convertNonEmptyDoc() = {
      	  val doc = new Document("the man is the hat")
	  val totalDocs = 100
	  val docFreqs = Map(new Token("the") -> 100, new Token("man") -> 4, new Token("is") -> 99, new Token("hat") -> 1)
	  val tfIdfConv = new TFIDFConverter(new WhitespaceTokenizer(), docFreqs, totalDocs)
	  val correct = new FeatureVector(Map(new Token("the") -> Math.log(100.0/100.0)/Math.log(2.0),
	      new Token("man") -> Math.log(100.0/4.0)/Math.log(2.0), new Token("is") -> Math.log(100.0/99.0)/Math.log(2.0),
	      new Token("hat") -> Math.log(100.0/1.0)/Math.log(2.0)))
	  assertEquals(correct, tfIdfConv.convert(doc))
      }
}