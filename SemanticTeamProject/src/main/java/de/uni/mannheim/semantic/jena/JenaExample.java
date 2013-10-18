package de.uni.mannheim.semantic.jena;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

import de.uni.mannheim.semantic.model.Person;

public class JenaExample {

	public static void main(String[] args) throws IOException {
		getCelebrities();

	}

	public static List<Person> getCelebrities() {
		List<Person> result = new ArrayList<Person>();
		try {
			String queryStr = "select distinct ?Concept where {[] a ?Concept} LIMIT 10";
			String queryStr2 = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> PREFIX foaf: <http://xmlns.com/foaf/0.1/> select ?firstname ?lastname ?birthdate where {?p a dbpedia-owl:Bodybuilder. ?p foaf:givenName ?firstname. ?p foaf:surname ?lastname. ?p dbpedia-owl:birthDate ?birthdate} ORDER BY ASC(?lastname) ";
			Query query = QueryFactory.create(queryStr2);

			// Remote execution.
			QueryExecution qexec = QueryExecutionFactory.sparqlService(
					"http://dbpedia.org/sparql", query);
			// Set the DBpedia specific timeout.
			((QueryEngineHTTP) qexec).addParam("timeout", "10000");

			// Execute.
			ResultSet rs = qexec.execSelect();
			String firstName = null;
			String lastName = null;
			String date = null;
			while (rs.hasNext()) {
				QuerySolution soln = rs.nextSolution();
				RDFNode x = soln.get("firstname"); // Get a result variable by name.
				firstName = x.asNode().getLiteralLexicalForm();
				x = soln.get("lastname"); // Get a result variable by name.
				lastName = x.asNode().getLiteralLexicalForm();
				x = soln.get("birthdate"); // Get a result variable by name.
				date = x.asNode().getLiteralLexicalForm();
				result.add(new Person(firstName, lastName,null , null, date, null, null, null));
			}
			ResultSetFormatter.out(System.out, rs, query);
			System.out.println(result);
			qexec.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// private static String getQuery() {
	// StringBuilder builder = new StringBuilder();
	// builder.append(
	// "PREFIX imdb: <http://data.linkedmdb.org/resource/movie/>")
	// .append("PREFIX dcterms: <http://purl.org/dc/terms/>")
	// .append("PREFIX dbpo: <http://dbpedia.org/ontology/>")
	// .append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>")
	// .append("SELECT ?birthDate ?spouseName ?movieTitle ?movieDate {")
	// .append("{ SERVICE <http://dbpedia.org/sparql>")
	// .append("{ SELECT ?birthDate ?spouseName WHERE {")
	// .append("?actor rdfs:label 'Arnold Schwarzenegger'@en ;")
	// .append("dbpo:birthDate ?birthDate ;")
	// .append("dbpo:spouse ?spouseURI .")
	// .append(" ?spouseURI rdfs:label ?spouseName .")
	// .append(" FILTER ( lang(?spouseName) = 'en' )}}}")
	// .append("{ SERVICE <http://data.linkedmdb.org/sparql>")
	// .append("{ SELECT ?actor ?movieTitle ?movieDate WHERE {")
	// .append("?actor imdb:actor_name 'Arnold Schwarzenegger'.")
	// .append("?movie imdb:actor ?actor ;")
	// .append("dcterms:title ?movieTitle ;")
	// .append("dcterms:date ?movieDate .}}}}");
	// return builder.toString();
	// }
}
