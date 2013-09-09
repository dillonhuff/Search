package search

class FeatureVector(namesToVals: Map[Token, Double]) {
	
	private val namesToValues = namesToVals
	
	def get(feature: Token): Double = {
		return namesToValues.getOrElse(feature, 0.0)
	}
	
	def add(other: FeatureVector): FeatureVector = {
		var sumFeatures = this.namesToValues
		for ((name, value) <- other.namesToValues) {
			sumFeatures += name -> (sumFeatures.getOrElse(name, 0.0) + value)
		}
		return new FeatureVector(sumFeatures)
	}
	
	def sub(other: FeatureVector): FeatureVector = {
		var diffFeatures = this.namesToValues
		for ((name, value) <- other.namesToValues) {
			diffFeatures += name -> (diffFeatures.getOrElse(name, 0.0) - value)
		}
		return new FeatureVector(diffFeatures)
	}
	
	def scalarMult(k: Double): FeatureVector = {
		return new FeatureVector(this.namesToValues.mapValues(_ * k))
	}
	
	def magnitude(): Double = {
		return Math.sqrt(namesToValues.mapValues(Math.pow(_, 2.0)).foldLeft(0.0)(_+_._2))
	}
	
	def dist(other: FeatureVector): Double = {
		return this.sub(other).magnitude
	}
	
	def cosSim(other: FeatureVector): Double = {
		val thisMag = this.magnitude
		val otherMag = other.magnitude
		if (thisMag == 0.0 || otherMag == 0.0) {
			return 0.0
		}
		var dotProd = 0.0
		for ((name, value) <- other.namesToValues) {
			dotProd += value * this.namesToValues.getOrElse(name, 0.0)
		}
		return dotProd / (thisMag * otherMag)
	}
	
	override
	def toString(): String = {
		return namesToValues.toString
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