package search

import scala.collection.immutable.Map
import scala.collection.immutable.Set

class InvertedIndex() {

	private var postings = Map.empty[Token, Set[Document]]

	def add(doc: Document) = {
		val uniqueToks = doc.text.toLowerCase().split("\\s").distinct
		for (tok <- uniqueToks) {
			val token = new Token(tok)
			postings += token -> (postings.getOrElse(token, Set.empty[Document]) + doc) 
		}
	}
	
	def docsContaining(token: Token): Set[Document] = {
		return postings.getOrElse(token, Set.empty[Document])
	}
	
	def docsContainingAll(tokens: Vector[Token]): Set[Document] = {
		if (tokens.size < 1) {
			return Set.empty[Document]
		}
		var docsWithAll = docsContaining(tokens(0))
		for (token <- tokens) {
			docsWithAll = docsWithAll.intersect(docsContaining(token))
		}
		return docsWithAll
	}
}

class Document(txt: String) {
	val text: String = txt
	
	override
	def equals(obj: Any): Boolean = {
		if (!obj.isInstanceOf[Document]) {
			return false
		}
		val other = obj.asInstanceOf[Document]
		return this.text.equals(other.text)
	}
	
	override
	def hashCode(): Int = {
		return text.hashCode
	}
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
}