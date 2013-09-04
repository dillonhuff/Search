package search

object KMeans {
	
	def populateClusters(centroids: Vector[FeatureVector], dataset: Vector[FeatureVector]): 
		Map[FeatureVector, Vector[FeatureVector]] = {
		var clusters = Map.empty[FeatureVector, Vector[FeatureVector]]
		for (vec <- dataset) {
			
		}
		return clusters
	}
}