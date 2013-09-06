package search

class WhitespaceTokenizer extends Tokenizer {
	
	def tokenize(doc: Document): Vector[Token] = {
		return doc.text.toLowerCase().split("\\s").map(toToken).toVector
	}
	
	def toToken(str: String) = new Token(str)
}