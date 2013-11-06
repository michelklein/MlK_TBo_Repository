package de.uni.mannheim.semantic.jena;

public class QueryHelper {

	public static String getBirthplaceDeathplaceQuery(String celebrityName) {
		StringBuilder builder = new StringBuilder();
		builder.append("PREFIX dbpprop: <http://dbpedia.org/property/>")
				.append("PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>")
				.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>")
				.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/>")
				.append("SELECT DISTINCT ?bname ?dname ")
				.append("WHERE { ?p a dbpedia-owl:Person. ?p rdfs:label ?name.")
				.append("OPTIONAL {{?p dbpedia-owl:birthPlace ?birthPlace.}")
				.append("UNION {?p dbpprop:birthPlace ?birthPlace.} ")
				.append("?birthPlace rdfs:label ?bname")
				.append("}")
				.append("OPTIONAL {{?p dbpedia-owl:deathPlace ?deathPlace.}")
				.append("UNION {?p dbpprop:deathPlace ?deathPlace.} ")
				.append("?deathPlace rdfs:label ?dname")
				.append("}")
				.append("FILTER (?name='" + celebrityName + "'@en)")
				.append("FILTER(NOT EXISTS {?birthPlace a dbpedia-owl:Country}) ")
				.append("FILTER(NOT EXISTS {?deathPlace a dbpedia-owl:Country})")
				.append("FILTER (LANG(?bname) = \"\" || langMatches(lang(?bname), 'EN' )) ")
				.append("FILTER (LANG(?dname) = \"\" || langMatches(lang(?dname), 'EN' ))")
				.append("}");
		return builder.toString();
	}
	
//	public static String getAlmaMataQuery(String celebrityName) {
//		StringBuilder builder = new StringBuilder();
//		builder.append("PREFIX dbpprop: <http://dbpedia.org/property/>")
//				.append("PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>")
//				.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>")
//				.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/>")
//				.append("SELECT DISTINCT ?city ")
//				.append("WHERE { ?p a dbpedia-owl:Person. ?p rdfs:label ?name. ?p dbpedia-owl:almaMater ?uni. ")
//				.append("{ ?uni dbpprop:city ?c } UNION {?uni dbpedia-owl:city ?c} ?c rdfs:label ?city. ")
//				.append("FILTER (?name='" + celebrityName + "'@en)")
//				.append("FILTER langMatches( lang(?city), 'EN' )")
//				.append("}");
//		return builder.toString();
//	}
	
	public static String getAlmaMataQuery(String celebrityName) {
		StringBuilder builder = new StringBuilder();
		builder.append("PREFIX dbpprop: <http://dbpedia.org/property/>")
				.append("PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>")
				.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>")
				.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/>")
				.append("SELECT DISTINCT ?uniName ")
				.append("WHERE { ?p a dbpedia-owl:Person. ?p rdfs:label ?name. ?p dbpedia-owl:almaMater ?uni. ")
				.append("?uni rdfs:label ?uniName ")
				.append("FILTER (?name='" + celebrityName + "'@en)")
				.append("FILTER langMatches( lang(?uniName), 'EN' )")
				.append("}");
		return builder.toString();
	}

}
