package search

import org.junit.Assert._
import org.junit.Test

class VectorSpaceRetrievalTests {

	@Test
	def noRelevantDocsForEmptyQuery() = {
		val docs = new DocumentCollection(Vector(new Document("this is cool"),
			new Document("But its only a test")))
		val vecSpace = new VectorSpaceRetrieval(docs, new WhitespaceTokenizer())
		val correct = Vector.empty[Document]
		val query = new Document("")
		assertEquals(correct, vecSpace.relevantDocs(query))
	}
	
	@Test
	def oneRelevantDoc() = {
		val doc1 = new Document("Not arachnocentric")
		val doc2 = new Document("This is so Raven")
		val doc3 = new Document("Belgian Waffle massacre")
		val docs = new DocumentCollection(Vector(doc1, doc2, doc3))
		val vecSpace = new VectorSpaceRetrieval(docs, new WhitespaceTokenizer())
		val correct = Vector(doc1)
		val query = new Document("not")
		assertEquals(correct, vecSpace.relevantDocs(query))
	}
	
	@Test
	def rankedRelevance() = {
		val doc1 = new Document("Canada is so very lame")
		val doc2 = new Document("Canada is so lame")
		val doc3 = new Document("There is no excuse")
		val doc4 = new Document("Canada is so not perfect")
		val doc5 = new Document("Why all the hate bro?")
		val docs = new DocumentCollection(Vector(doc1, doc2, doc3, doc4, doc5))
		val vecSpace = new VectorSpaceRetrieval(docs, new WhitespaceTokenizer())
		val correct = Vector(doc1, doc2, doc4, doc3)
		val query = new Document("canada is so very lame")
		assertEquals(correct, vecSpace.relevantDocs(query))
	}
}