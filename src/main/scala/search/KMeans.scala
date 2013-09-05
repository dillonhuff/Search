package search

object KMeans {

	def computeCentroid(cluster: Vector[FeatureVector]): FeatureVector = {
		var newCentroid = new FeatureVector(Map.empty[String, Double])
		for (vec <- cluster) {
			newCentroid = newCentroid.add(vec)
		}
		return newCentroid.scalarMult(1.0/cluster.size)
	}
	
	def populateClusters(centroids: Vector[FeatureVector], dataset: Vector[FeatureVector]): 
		Map[FeatureVector, Vector[FeatureVector]] = {
		var clusters = Map.empty[FeatureVector, Vector[FeatureVector]]
		for (vec <- dataset) {
			val cent = closestCentroid(vec, centroids)
			clusters += cent -> (clusters.getOrElse(cent, Vector.empty[FeatureVector]) :+ vec)
		}
		return clusters
	}
	
	def closestCentroid(vec: FeatureVector, centroids: Vector[FeatureVector]): FeatureVector = {
		val minIndex = centroids.map(vec.dist(_)).zipWithIndex.min._2
		return centroids(minIndex)
	}
}