package search

class BagOfWordsConverter(tokenizer: Tokenizer) extends DocToFeatureVector {
	
	def convert(doc: Document): FeatureVector = {
	    val tokens = tokenizer.tokenize(doc)
	    val tokCounts = tokens.foldLeft(Map.empty[Token, Double])((tokToCount, token) => 
	    	   tokToCount + (token -> (tokToCount.getOrElse(token, 0.0) + 1.0)))
	    return new FeatureVector(tokCounts)
	}
}