package search

import org.junit.Assert._
import org.junit.Test

class DocumentCollectionTests {
	
	@Test
	def docFrequencyOfAbsentWordIsZero() = {
		val docs = new DocumentCollection(Vector.empty[Document])
		assertEquals(0, docs.docFrequency(new Token("the")))
	}

	@Test
	def docFrequencyOne() = {
		val docs = new DocumentCollection(Vector(new Document("super cool man")))
		assertEquals(1, docs.docFrequency(new Token("super")))
		assertEquals(1, docs.docFrequency(new Token("cool")))
		assertEquals(1, docs.docFrequency(new Token("man")))
	}

	@Test
	def docFrequencyNotTermFrequency() = {
		val docs = new DocumentCollection(Vector(new Document("this is the stuff the guy got")))
		assertEquals(1, docs.docFrequency(new Token("the")))
	}

    @Test
    def docFrequencyWithMultipleDocs() = {
		val docs = new DocumentCollection(Vector(new Document("never again man"),
		new Document("never this way too often"),
		new Document("never this is just too cool")))
		assertEquals(3, docs.docFrequency(new Token("never")))
		assertEquals(2, docs.docFrequency(new Token("this")))
		assertEquals(0, docs.docFrequency(new Token("cupids")))
	}
}