package search

class TFIDFConverter(tokenizer: Tokenizer, docFreqs: Map[Token, Int], numDocs: Int) extends DocToFeatureVector {

	def convert(doc: Document): FeatureVector = {
		val tokens = tokenizer.tokenize(doc)
		val tokenCounts = tokens.foldLeft(Map.empty[Token, Int])((counts, token) => 
	      counts + (token -> (counts.getOrElse(token, 0) + 1)))
		val tokensToTFIDFWeights = tokenCounts.map({case (token, termFreq) => 
	      (token, tfIdfWeight(termFreq.toDouble, docFreqs.getOrElse(token, 0).toDouble))})
      	return new FeatureVector(tokensToTFIDFWeights)
	}

	def tfIdfWeight(termFreq: Double, docFreq: Double): Double = {
		if (termFreq == 0 || docFreq == 0) {
			return 0.0
		}
		return termFreq*Math.log(numDocs/docFreq)/Math.log(2)
	}
}