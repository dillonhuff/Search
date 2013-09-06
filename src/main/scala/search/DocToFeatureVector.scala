package search

//Trait for converting documents to FeatureVectors
//for use in clustering, classification or related
//tasks
trait DocToFeatureVector {
	def convert(doc: Document): FeatureVector
}