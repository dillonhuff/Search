package search

trait Tokenizer {
	def tokenize(doc: Document): Vector[Token]	
}