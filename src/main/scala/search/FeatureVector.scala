package search

class FeatureVector(namesToVals: Map[String, Double]) {
	
	private val namesToValues = namesToVals
	
	def get(featureName: String): Double = {
		return namesToValues.getOrElse(featureName, 0.0)
	}
	
	def add(other: FeatureVector): FeatureVector = {
		var sumFeatures = this.namesToValues
		for ((name, value) <- other.namesToValues) {
			sumFeatures += name -> (sumFeatures.getOrElse(name, 0.0) + value)
		}
		return new FeatureVector(sumFeatures)
	}
	
	override
	def equals(obj: Any): Boolean = {
		if (!obj.isInstanceOf[FeatureVector]) {
			return false
		}
		val other = obj.asInstanceOf[FeatureVector]
		return this.namesToValues.equals(other.namesToValues)
	}
	
	override
	def hashCode(): Int = {
		return this.namesToValues.hashCode()
	}
}