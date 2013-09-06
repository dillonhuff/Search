package search

trait Tokenizer {
	def tokenize(doc: Document): Vector[Token]	
}

class Token(s: String) {
	val str: String = s
	
	override
	def equals(obj: Any): Boolean = {
		if (!obj.isInstanceOf[Token]) {
			return false
		}
		val other = obj.asInstanceOf[Token]
		return this.str.equals(other.str)
	}
	
	override
	def hashCode(): Int = {
		return str.hashCode
	}
	
	override
	def toString(): String = {
		return str
	}
}