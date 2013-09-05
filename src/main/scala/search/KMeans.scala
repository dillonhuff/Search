package search

object KMeans {

	def cluster(vectors: Vector[FeatureVector], numCentroids: Int):
		Set[Vector[FeatureVector]] = {
		var oldCentroids = Vector.empty[FeatureVector]
		var newCentroids = randomInitialCentroids(vectors, numCentroids)
		do {
			oldCentroids = newCentroids
			val clusters = populateClusters(oldCentroids, vectors)
			newCentroids = clusters.mapValues(computeCentroid).values.toVector
		} while (newCentroids != oldCentroids)
		return populateClusters(newCentroids, vectors).values.toSet
	}

	def randomInitialCentroids(vectors: Vector[FeatureVector], numCentroids: Int):
		Vector[FeatureVector] = {
		return scala.util.Random.shuffle(vectors).take(numCentroids)
	}

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