package search

import scala.io.Source

object ClusteredDirSearch {
	
	def main(args: Array[String]): Unit = {
		if (args.length < 2) {
			println("ERROR: need a file path and at least one keyword")
		}
		
		val dir = new File(args(0))
		val index = indexDocsInDir(dir)
		var searchTerms = Vector.empty[Token]
		for (i <- 1 to args.length - 1) {
			searchTerms = searchTerms :+ (new Token(args(i)))
		}
		val docsWithAllTerms = index.docsContainingAll(searchTerms)
		if (docsWithAllTerms.size < 1) {
			println("NO DOCUMENTS WITH ALL SEARCH TERMS")
		} else {
			
		}
	}
	
	def indexDocsInDir(dir: File): InvertedIndex = {
		val index = new InvertedIndex()
		for (filePath <- dir.list()) {
			index.add(new Document(Source.fromFile(dir.getAbsolutePath() + "/" + filePath).mkString))
		}
		return index
	}
}