package search

class VectorSpaceRetrieval(documentCollection: DocumentCollection, tokenizer: Tokenizer) {
	
	val index = indexCollection()
	val converter = new TFIDFConverter(
		tokenizer, documentCollection.documentFrequencies, documentCollection.documents.size)
	val docsToFeatureVecs = buildDocsToVecsMap()
	
	def indexCollection(): InvertedIndex = {
		val index = new InvertedIndex()
		for (doc <- documentCollection.documents) {
			val tokens = tokenizer.tokenize(doc).toSet[Token]
			index.add(doc, tokens)
		}
		return index
	}
	
	def buildDocsToVecsMap(): Map[Document, FeatureVector] = {
		return documentCollection.documents.map((doc) => doc -> converter.convert(doc)).toMap
	}
	
	def relevantDocs(query: Document): Vector[Document] = {
		val queryTokens = tokenizer.uniqueTokens(query)
		val possibleDocs = index.docsContainingAny(queryTokens.toVector).toVector
		return rankedByRelevance(possibleDocs, query)
	}
	
	def rankedByRelevance(docs: Vector[Document], query: Document): Vector[Document] = {
		val queryVec = converter.convert(query)
		val docsToVecs = docs.map(
			(doc) => doc -> docsToFeatureVecs.getOrElse(doc, new FeatureVector(Map.empty[Token, Double])))
		return docsToVecs.map({case (doc, vec) => (doc, queryVec.cosSim(vec))}).sortBy({_._2}).map(_._1).reverse
	}
}