package search

import java.io.File

import scala.io.Source

object VectorSpaceDirRetrieval {
	
	def main(args: Array[String]): Unit = {
		if (args.length < 2) {
			println("ERROR: Must provide a directory and at least one search term")
			return
		}
		val dir = new File(args(0))
		val docs = docsInDir(dir)
		val queryBuilder = new StringBuilder()
		for (i <- 1 to args.length - 1) {
			queryBuilder.append(args(i) + " ")
		}
		val docCollection = new DocumentCollection(docs)
		val docVecSpace = new VectorSpaceRetrieval(docCollection, new WhitespaceTokenizer())
		val relevantDocs = docVecSpace.relevantDocs(new Document(queryBuilder.toString))
		if (relevantDocs.size < 1) {
			println("NO DOCUMENTS ARE RELEVANT TO QUERY")
		} else {
			val numDocsToShow = 10
			for (doc <- relevantDocs.take(10)) {
				println(doc.text.take(200))
				println()
			}
		}
	}
	
	def docsInDir(dir: File): Vector[Document] = {
		val tokenizer = new WhitespaceTokenizer()
		var docs = Vector.empty[Document]
		for (filePath <- dir.list()) {
			val doc = new Document(Source.fromFile(dir.getAbsolutePath() + "/" + filePath).mkString)
			docs = docs :+ doc
		}
		return docs
	}
}