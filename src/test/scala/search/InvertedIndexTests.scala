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

}