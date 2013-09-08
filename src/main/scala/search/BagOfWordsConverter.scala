package search

class BagOfWordsConverter(tokenizer: Tokenizer) extends DocToFeatureVector {
	
	def convert(doc: Document): FeatureVector = {
	    val tokens = tokenizer.tokenize(doc)
	    var tokensToCounts = Map.empty[String, Double]
	    for (token <- tokens) {
	    	val tokenStr = token.toString
		tokensToCounts += tokenStr -> (tokensToCounts.getOrElse(tokenStr, 0.0) + 1.0)
	    }
	    return new FeatureVector(tokensToCounts)
	}
}