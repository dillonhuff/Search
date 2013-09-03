package search

import java.io.File

import scala.collection.immutable.Vector
import scala.io.Source

//Searches a directory of files
//and prints a file that contains all
//of the search terms
object VerySimpleRetrieval {
	
	def main(args: Array[String]) = {
		if (args.length < 2) {
			println("ERROR: must supply a directory and at least one term")
		}
		val dir = new File(args(0))
		println("DIR NAME IS " + dir)
		println("DIR ENTRIES = " + dir.list().length)
		println("DIR ENTRY ONE = " + dir.list()(0))
		val index = indexDocsInDir(dir)
		var searchTerms = Vector.empty[Token]
		for (i <- 1 to args.length - 1) {
			searchTerms = searchTerms :+ (new Token(args(i)))
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