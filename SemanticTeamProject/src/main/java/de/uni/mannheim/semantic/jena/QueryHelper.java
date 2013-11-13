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

	// public static String getAlmaMataQuery(String celebrityName) {
	// StringBuilder builder = new StringBuilder();
	// builder.append("PREFIX dbpprop: <http://dbpedia.org/property/>")
	// .append("PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>")
	// .append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>")
	// .append("PREFIX foaf: <http://xmlns.com/foaf/0.1/>")
	// .append("SELECT DISTINCT ?city ")
	// .append("WHERE { ?p a dbpedia-owl:Person. ?p rdfs:label ?name. ?p dbpedia-owl:almaMater ?uni. ")
	// .append("{ ?uni dbpprop:city ?c } UNION {?uni dbpedia-owl:city ?c} ?c rdfs:label ?city. ")
	// .append("FILTER (?name='" + celebrityName + "'@en)")
	// .append("FILTER langMatches( lang(?city), 'EN' )")
	// .append("}");
	// return builder.toString();
	// }

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

	public static String getBasicInfoQuery(String celebrityName) {
		StringBuilder builder = new StringBuilder();
		builder.append("PREFIX ont: <http://dbpedia.org/ontology/>")
				.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/>")
				.append("PREFIX dbpprop: <http://dbpedia.org/property/>")
				.append("PREFIX owl: <http://www.w3.org/2002/07/owl#>")
				.append("PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>")
				.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>")
				.append("SELECT DISTINCT * WHERE {")
				// .append("?p owl:sameAs ?sa.")
				.append("?p foaf:name ?name.")
				.append("?p a ?type.")
				.append("?type rdfs:subClassOf ?sc.")
				.append("?p foaf:givenName ?givenName.")
				.append("?p foaf:surname ?surname.")
				.append("?p ont:thumbnail ?thumbnail.")
				.append("?p ont:birthPlace ?birthPlace.")
				.append("?p ont:birthDate ?date.")
				.append("FILTER (?name='" + celebrityName + "'@en)")
				.append("FILTER(NOT EXISTS { ?birthPlace a dbpedia-owl:Country} )")
				.append("}");
		return builder.toString();
	}

	public static String getMoviesQuery(String sameAs) {
		StringBuilder builder = new StringBuilder();
		builder.append("PREFIX ont: <http://dbpedia.org/ontology/>")
				.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/>")
				.append("PREFIX dbpprop: <http://dbpedia.org/property/>")
				.append("PREFIX owl: <http://www.w3.org/2002/07/owl#>")
				.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>")
				.append("PREFIX dcterms: <http://purl.org/dc/terms/>")
				.append("PREFIX skos: <http://www.w3.org/2004/02/skos/core#>")
				.append("SELECT Distinct ?label ?year ?p WHERE {")
				.append("?p rdfs:label ?label.")
				.append("?p dcterms:subject ?year.")
				.append("?year skos:broader <http://dbpedia.org/resource/Category:Films_by_year>.")
				.append("?p ont:starring <" + sameAs + ">.")
				.append("FILTER (LANG(?label)='en')").append("}");
		return builder.toString();
	}

	public static String getIMDBQuery(String sameAs) {
		sameAs = sameAs.replaceAll("\\(", "%28");
		sameAs = sameAs.replaceAll("\\)", "%29");
		StringBuilder builder = new StringBuilder();
		builder.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/>")
				.append("PREFIX owl: <http://www.w3.org/2002/07/owl#>")
				.append("PREFIX movie: <http://data.linkedmdb.org/resource/movie/>")
				.append("SELECT Distinct ?page WHERE {")
				.append("?film foaf:page ?page.").append("?film a movie:film.")
				.append("  ?film owl:sameAs <" + sameAs + ">.").append("}");
		return builder.toString();
	}

	public static String getAlbumsQuery(String sameAs) {
		StringBuilder builder = new StringBuilder();
		builder.append("PREFIX ont: <http://dbpedia.org/ontology/>")
				.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/>")
				.append("PREFIX dbpprop: <http://dbpedia.org/property/>")
				.append("PREFIX owl: <http://www.w3.org/2002/07/owl#>")
				.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>")
				.append("SELECT Distinct * WHERE {")
				.append("?p rdfs:label ?label.")
				.append("?p ont:artist <" + sameAs + ">.")
				.append("?p a <http://dbpedia.org/ontology/MusicalWork>.")
				.append("?p ont:genre ?genre.")
				.append("FILTER (LANG(?label)='en')").append("}");
		return builder.toString();
	}

}
