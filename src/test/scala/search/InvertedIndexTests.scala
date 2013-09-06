package search

import scala.collection.immutable.Set

import org.junit.Assert._
import org.junit.Test

class InvertedIndexTests {

	@Test
	def indexNoDocs() = {
		val index = new InvertedIndex()
		assertEquals(Set.empty[Document], index.docsContaining(new Token("superman")))
	}

	@Test
	def indexOneDoc() = {
		val doc = new Document("the man fell")
		val index = new InvertedIndex()
		val tokenizer = new WhitespaceTokenizer()
		index.add(doc, tokenizer.tokenize(doc).toSet)
		val correct = Set[Document](doc)
		assertEquals(correct, index.docsContaining(new Token("the")))
	}
	
	@Test
	def indexTwoDocs() = {
		val doc1 = new Document("tHis never happened")
		val doc2 = new Document("I hate this")
		val index = new InvertedIndex()
		val tokenizer = new WhitespaceTokenizer()
		index.add(doc1, tokenizer.tokenize(doc1).toSet)
		index.add(doc2, tokenizer.tokenize(doc2).toSet)
		assertEquals(Set[Document](doc1, doc2), index.docsContaining(new Token("this")))
		assertEquals(Set[Document](doc1), index.docsContaining(new Token("never")))
		assertEquals(Set[Document](doc2), index.docsContaining(new Token("hate")))
	}
	
	@Test
	def docsContainingBothTokens() = {
		val doc1 = new Document("I am secretly building a satellite based mind control device")
		val doc2 = new Document("The officer was arresting a peanut butter and jelly sandwich")
		val doc3 = new Document("I just couldn't ever love the bomb.")
		val index = new InvertedIndex()
		val tokenizer = new WhitespaceTokenizer()
		index.add(doc1, tokenizer.tokenize(doc1).toSet)
		index.add(doc2, tokenizer.tokenize(doc2).toSet)
		index.add(doc3, tokenizer.tokenize(doc3).toSet)
		assertEquals(Set[Document](doc1), 
			index.docsContainingAll(Vector[Token](new Token("i"), new Token("mind"))))
		assertEquals(Set[Document](doc2),
			index.docsContainingAll(Vector[Token](new Token("the"), new Token("officer"))))
		assertEquals(Set[Document](doc3),
			index.docsContainingAll(Vector[Token](new Token("the"), new Token("love"))))
	}
	
	@Test
	def docsContainingAnyTokens() = {
		val doc1 = new Document("Killer whale massacre")
		val doc2 = new Document("Not a crab")
		val doc3 = new Document("A very secret man")
		val index = new InvertedIndex()
		val tokenizer = new WhitespaceTokenizer()
		index.add(doc1, tokenizer.tokenize(doc1).toSet)
		index.add(doc2, tokenizer.tokenize(doc2).toSet)
		index.add(doc3, tokenizer.tokenize(doc3).toSet)
		assertEquals(Set(doc2, doc3),
			index.docsContainingAny(Vector(new Token("crab"), new Token("man"))))
	}
}