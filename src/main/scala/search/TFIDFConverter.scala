package search

class TFIDFConverter(tokenizer: Tokenizer, docFreqs: Map[Token, Int], numDocs: Int) extends DocToFeatureVector {
      
      def convert(doc: Document): FeatureVector = {
      	  return new FeatureVector(Map.empty[Token, Double])
      }
}