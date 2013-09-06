package search

import java.io.File

import scala.collection.immutable.Vector
import scala.io.Source

//Searches a directory of files
//and prints the first part of any 
//file that contains all the terms
//given by the user
object VerySimpleRetrieval {
	
	def main(args: Array[String]) = {
		if (args.length < 2) {
			println("ERROR: must supply a directory and at least one term")
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
			for (doc <- docsWithAllTerms) {
				println(doc.text.take(200))
				println()
			}
		}
	}
	
	def indexDocsInDir(dir: File): InvertedIndex = {
		val tokenizer = new WhitespaceTokenizer()
		val index = new InvertedIndex()
		for (filePath <- dir.list()) {
			val doc = new Document(Source.fromFile(dir.getAbsolutePath() + "/" + filePath).mkString)
			index.add(doc, tokenizer.tokenize(doc).toSet[Token])
		}
		return index
	}
}