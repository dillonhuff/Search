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
		index.add(doc)
		val correct = Set[Document](doc)
		assertEquals(correct, index.docsContaining(new Token("the")))
	}
	
	@Test
	def indexTwoDocs() = {
		val doc1 = new Document("tHis never happened")
		val doc2 = new Document("I hate this")
		val index = new InvertedIndex()
		index.add(doc1)
		index.add(doc2)
		assertEquals(Set[Document](doc1, doc2), index.docsContaining(new Token("this")))
		assertEquals(Set[Document](doc1), index.docsContaining(new Token("never")))
		assertEquals(Set[Document](doc2), index.docsContaining(new Token("hate")))
	}
	
	@Test
	def docsContainingBothWords() = {
		val doc1 = new Document("I am secretly building a satellite based mind control device")
		val doc2 = new Document("The officer was arresting a peanut butter and jelly sandwich")
		val doc3 = new Document("I just couldn't ever love the bomb.")
		val index = new InvertedIndex()
		index.add(doc1)
		index.add(doc2)
		index.add(doc3)
		assertEquals(Set[Document](doc1), 
			index.docsContainingAll(Vector[Token](new Token("i"), new Token("mind"))))
		assertEquals(Set[Document](doc2),
			index.docsContainingAll(Vector[Token](new Token("the"), new Token("officer"))))
		assertEquals(Set[Document](doc3),
			index.docsContainingAll(Vector[Token](new Token("the"), new Token("love"))))
	}

}