package search

import java.io.File

import scala.io.Source

//Almost the same as VerySimpleRetrieval,
//but results are grouped in to a fixed 
//number of clusters using K-Means
object ClusteredDirSearch {
	
	val numClusters = 4
	
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
			val docClusters = KMeans.cluster(docsWithAllTerms.toVector, numClusters)
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