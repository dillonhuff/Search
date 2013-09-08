package search

class DocumentCollection(docs: Vector[Document]) {
	val documents = docs
	val tokenizer = new WhitespaceTokenizer()

	val documentFrequencies = computeDocFrequencies(docs)
	
	def docFrequency(token: Token): Int = {
	    documentFrequencies.getOrElse(token, 0)
	}

	def computeDocFrequencies(docs: Vector[Document]): Map[Token, Int] = {
	    val docUniqueTokens = docs.map(tokenizer.tokenize(_).distinct.toSet)
	    return docUniqueTokens.foldLeft(Map.empty[Token, Int])(addCounts)
	}

	def addCounts(tokenDocFrequencies: Map[Token, Int], tokens: Set[Token]): Map[Token, Int] = 
	    tokens.foldLeft(tokenDocFrequencies)((tokenCounts, token) => tokenCounts + (token -> (tokenCounts.getOrElse(token, 0) + 1)))
}