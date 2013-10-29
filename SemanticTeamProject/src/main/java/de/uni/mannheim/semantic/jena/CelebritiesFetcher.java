package de.uni.mannheim.semantic.jena;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
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

import de.uni.mannheim.semantic.facebook.FacebookParser;
import de.uni.mannheim.semantic.model.CelPerson;
import de.uni.mannheim.semantic.model.FacebookPerson;
import de.uni.mannheim.semantic.model.Institution;
import de.uni.mannheim.semantic.model.Person;

public class CelebritiesFetcher {
	QuerySolution s;

	public static void main(String[] args) throws IOException {
//		 CelebritiesFetcher.get().getCelebrity("Arnold Schwarzenegger");
//		CelebritiesFetcher.get().getMovies("Arnold Schwarzenegger");
		CelebritiesFetcher.get().getMovies123("Batman: The Dark Knight");
	}

	private static CelebritiesFetcher instance;

	private CelebritiesFetcher() {

	}

	public static CelebritiesFetcher get() {
		if (instance == null) {
			instance = new CelebritiesFetcher();
		}
		return instance;
	}

	public CelPerson getCelebrity(String celName) {
	

			StringBuilder builder = new StringBuilder();
			builder.append("PREFIX ont: <http://dbpedia.org/ontology/>")
					.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/>")
					.append("PREFIX dbpprop: <http://dbpedia.org/property/>")
							.append("PREFIX owl: <http://www.w3.org/2002/07/owl#>")
					.append("SELECT  * WHERE {")
				
					.append("OPTIONAL { ?p foaf:name ?name.}")
					.append("OPTIONAL { ?p foaf:givenName ?givenName.}")
					.append("OPTIONAL { ?p foaf:surname ?surname.}")
					.append("OPTIONAL { ?p ont:thumbnail ?thumbnail.}")
					.append("OPTIONAL { ?p ont:birthPlace ?birthPlace.}")
					.append("OPTIONAL { ?p ont:birthDate ?date.}")
						.append("OPTIONAL { ?p dbpprop:relations ?child.}")
					.append("OPTIONAL { ?p dbpprop:profession ?profession.}")
						.append("OPTIONAL { ?p dbpprop:successor ?successor.}")
					.append("OPTIONAL { ?p ont:birthName ?birthName.}")
					.append("FILTER (?name='" + celName + "'@en)")
					.append("} LIMIT 10");
			System.out.println(builder.toString());
			ResultSet rs = execute("http://dbpedia.org/sparql",
					builder.toString());
			while (rs.hasNext()) {
				s = rs.nextSolution();
				System.out.println(s);
//				try {
//				 Iterator<String> varNames = s.varNames();
//				 while(varNames.hasNext()){
//				 System.out.println(varNames.next());
//				 }
//
//				 RDFNode asd = s.get("successor");
////				 System.out.println(s.get("successor").asLiteral().toString());
//				 
//				 
//				String givenName = gll("givenName");
//				String surname = gll("surname");
//				String date = gll("date");
//				String tn = g("thumbnail");
//				// createInstitution(s.get("birthPlace"));
//				getMovies(gll("name"));
//
//				// Institution hp = new Institution(g("birthPlace"))
//
//				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//
//				CelPerson p = new CelPerson(givenName, surname, null, null,
//						df.parse(date), null, null, null, null, tn);
//
//				// FBParser.TBoSuperDuperPrinter(p);
//				return p;
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			}
		
		return null;

	}
	private void getMovies(String n) {
		StringBuilder builder = new StringBuilder();
		builder.append("PREFIX ont: <http://dbpedia.org/ontology/>")
		.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/>")
		.append("PREFIX dbpprop: <http://dbpedia.org/property/>")
				.append("PREFIX owl: <http://www.w3.org/2002/07/owl#>")
							.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>")
		.append("SELECT Distinct * WHERE {")
	
		.append("?p rdfs:label ?label.")
		.append("?p ont:starring <http://dbpedia.org/resource/Arnold_Schwarzenegger>.")
		.append("FILTER (LANG(?label)='en')")
		.append("} LIMIT 10");
		ResultSet rs = execute("http://dbpedia.org/sparql",
				builder.toString());
		while (rs.hasNext()) {
			s = rs.nextSolution();
			System.out.println(s);
			// Iterator<String> varNames = s.varNames();
			// while (varNames.hasNext()) {
			// System.out.println(varNames.next());
			// }
		}
	}
	private void getMovies123(String n) {
		StringBuilder builder = new StringBuilder();
		builder.append("PREFIX ont: <http://dbpedia.org/ontology/>")
				.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/>")
				.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>")
				.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>")
				.append("PREFIX movie: <http://data.linkedmdb.org/resource/movie/>")
				.append("SELECT DISTINCT *").append("WHERE {")
				.append("?p rdfs:label ?label.")
				.append("FILTER (?label='" + n + "')")
				.append("}LIMIT 1");
		ResultSet rs = execute("http://data.linkedmdb.org/sparql",
				builder.toString());
		while (rs.hasNext()) {
			s = rs.nextSolution();
			System.out.println(s);
			// Iterator<String> varNames = s.varNames();
			// while (varNames.hasNext()) {
			// System.out.println(varNames.next());
			// }
		}
	}

	private ResultSet execute(String endPoint, String q) {
		// create SPARQL Query for getting celebrities
		Query query = QueryFactory.create(q);

		// Remote execution.
		QueryExecution qexec = QueryExecutionFactory.sparqlService(endPoint,
				query);
		// Set the DBpedia specific timeout.
		((QueryEngineHTTP) qexec).addParam("timeout", "10000");

		// Execute.
		ResultSet rs = qexec.execSelect();
		// qexec.close();
		return rs;
	}

	private Institution createInstitution(RDFNode rdfNode) {

		String resName = rdfNode.toString().substring(
				rdfNode.toString().lastIndexOf("/") + 1);
		StringBuilder builder = new StringBuilder();
		builder.append("PREFIX ont: <http://dbpedia.org/ontology/>")
				.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/>")
				.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>")
				// .append("PREFIX bif: <http://www.openlinksw.com/schema/sparql/extensions#>")
				.append("Prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>")
				.append("prefix dbpedia2: <http://dbpedia.org/property/>")
				.append("SELECT DISTINCT *").append("WHERE {")
				.append("?p geo:long ?long.").append("?p geo:lat ?lat.")
				.append("?p rdfs:label ?label.")
				.append("FILTER (?label='" + resName + "'@en)")
				.append("}LIMIT 1");
		ResultSet rs = execute("http://dbpedia.org/sparql", builder.toString());
		while (rs.hasNext()) {
			s = rs.nextSolution();
			// Iterator<String> varNames = s.varNames();
			// while (varNames.hasNext()) {
			// System.out.println(varNames.next());
			// }
		}
		return new Institution(gll("label"), gll("long"), gll("lat"));
	}

	private String gll(String string) {
		RDFNode x = s.get(string);
		if (x != null) {
			String result = x.asNode().getLiteralLexicalForm();
			return result;
		} else {
			return null;
		}
	}

	private String g(String string) {
		RDFNode x = s.get(string);
		return x.toString();
	}

	public List<Person> getCelebritiePersons() {
		List<Person> result = new ArrayList<Person>();
		try {
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
				RDFNode x = soln.get("firstname"); // Get a result variable by
													// name.
				firstName = x.asNode().getLiteralLexicalForm();
				x = soln.get("lastname"); // Get a result variable by name.
				lastName = x.asNode().getLiteralLexicalForm();
				x = soln.get("birthdate"); // Get a result variable by name.
				date = x.asNode().getLiteralLexicalForm();
				result.add(new Person(firstName, lastName, null, null, date,
						null, null, null));
			}
			ResultSetFormatter.out(System.out, rs, query);
			// System.out.println(result);
			qexec.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<String> getCelebrities() {
		List<String> celebrities = new ArrayList<String>();
		try {
			// create SPARQL Query for getting celebrities
			Query query = QueryFactory.create(getQuery());

			// Remote execution.
			QueryExecution qexec = QueryExecutionFactory.sparqlService(
					"http://dbpedia.org/sparql", query);
			// Set the DBpedia specific timeout.
			((QueryEngineHTTP) qexec).addParam("timeout", "10000");

			// Execute.
			ResultSet rs = qexec.execSelect();
			String firstName = null;
			String lastName = null;
			String fullname = null;
			while (rs.hasNext()) {
				QuerySolution soln = rs.nextSolution();
				RDFNode x = soln.get("firstname"); // Get a result variable by
													// name.
				firstName = x.asNode().getLiteralLexicalForm();
				x = soln.get("lastname"); // Get a result variable by name.
				// if(x == null || x.asNode() == null) {
				// lastName = "unnamed";
				// } else {
				lastName = x.asNode().getLiteralLexicalForm();
				// }
				fullname = String.format("%s %s", firstName, lastName);
				celebrities.add(fullname);
				// System.out.print("\"" + fullname + "\",");
			}
			// System.out.println(celebrities.size());
			qexec.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return celebrities;
	}

	private String getQuery() {
		StringBuilder builder = new StringBuilder();
		builder.append("PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> ")
				.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/> ")
				.append("select DISTINCT ?firstname ?lastname ")
				.append("where {?p a dbpedia-owl:Bodybuilder.")
				.append("?p foaf:givenName ?firstname. ")
				.append("?p foaf:surname ?lastname")
				.append(" FILTER (!isBlank(?lastname))")
				.append("} ORDER BY ASC(?lastname) ");
		return builder.toString();
	}

	public List<String> getDummyCelebrities() {
		String[] persons = { "Jelena Abbou", "Tevita Aholelei",
				"Dina Al-Sabah", "Achim Albrecht", "Melvin Anthony",
				"Heather Armbrust", "Art Atwood", "Gustavo Badell",
				"Mohammad Bannout", "Samir Bannout", "Fannie Barrios",
				"Christa Bauch", "Kay Baxter", "Shelley Beattie",
				"Lauren Beckham", "Mohammed Benaziza", "Stacey Bentley",
				"Juliette Bergmann", "Laura Binetti", "Andrulla Blanchette",
				"Sheila Bleck", "Fabiola Boulanger", "Debbie Bramwell",
				"Monica Brant", "Brigita Brezovac", "Sharon Bruneau",
				"Dayana Cadeau", "Rene Campbell", "Candice Carr-Archer",
				"Marcos Chacon MCwenney", "Robert Cheeke", "Valentina Chepiga",
				"Kim Chizevsky", "Bob Cicherillo", "Ronnie Coleman",
				"Franco Columbu", "Laura Combes", "Lynn Conkwright",
				"Laura Creavalle", "Lisa Cross", "Candy Csencsits",
				"Susie Curry", "Jay Cutler", "Roland Cziurlock", "Bill Davey",
				"Angela Debatin", "Johanna Dejager", "Mahadev Deka",
				"Diana Dennis", "Chris Dickerson", "Paul Dillett", "Kris Dim",
				"Debbie Dobbins", "Carla Dunlap", "Alexis Ellis",
				"Kike Elomaa", "Cory Everson", "Erik Fankhouser",
				"George Farah", "Lou Ferrigno", "Bertil Fox", "Bev Francis",
				"Jaime Franklin", "Tony Freeman", "Anne Freitas",
				"Georgia Fudge", "Sue Gafner", "Adela Garcia", "Connie Garner",
				"Rich Gaspari", "Vickie Gates", "Erika Giesen",
				"Elaine Goodlad", "Kai Greene", "Tracey Greenwood",
				"John Carroll Grimek", "Colette Guimond", "Marcus Haley",
				"Harry Haureliuk", "Kristy Hawkins", "Phil Heath",
				"Jen Hendershott", "Soleivi Hernandez", "Utti Hietala",
				"Fikret Hodzic", "Ray Hollitt", "Frantisek Huf",
				"Yolanda Hughes", "Dexter Jackson", "Johnnie Jackson",
				"Rusty Jeffers", "Tanji Johnson", "Monique Jones",
				"Gautam Kalita", "Michael Kefalianos", "Suhas Khamkar",
				"Mir Mohtesham Ali Khan", "Tonya Knight", "Irvin Koszewski",
				"Greg Kovacs", "Iris Kyle", "Lee Labrada",
				"Mary Elizabeth Lado", "Anja Langer", "Debi Laszewski",
				"Cathy LeFrancois", "Tammie Leady", "Kevin Levrone",
				"Nancy Lewis", "Wendy Lindquist", "Aaron Links",
				"Amber Littlejohn", "Julie Lohre", "Cammie Lusko", "Lisa Lyon",
				"Marie Mahabir", "Timea Majorova", "Ellen van Maris",
				"Victor Martinez", "Denise Masino", "Rachel McLish",
				"Stan McQuay", "Davana Medina", "Jodi Miller", "Linda Minard",
				"Evgeny Mishin", "Gayle Moher", "Geraldine Morgan",
				"Lenda Murray", "Sergei Ogorodnikov", "Sergio Oliva",
				"Dona Oliveira", "Yaxeni Oriquen", "Lora Ottenad",
				"Jackie Paisley", "Ben Pakulski", "Cathey Palyo",
				"Elena Panova", "Betty Pariso", "Reg Park", "Jessie Pavelka",
				"Bill Pearl", "Tony Pearson", "Sha-ri Pendleton",
				"Robert Piotrkowicz", "Alina Popa", "Gladys Portugues",
				"Lauren Powers", "Rhonda Lee Quaresma", "Brenda Raganot",
				"Shawn Ray", "Larissa Reis", "Charlene Rink", "Mary Roberts",
				"Eddie Robinson", "Robby Robinson", "Ronny Rockel",
				"Kelly Ann Ryan", "Angie Salvagno", "Ofer Samra",
				"Silvio Samuel", "Richard Sandrak", "Ursula Sarcev",
				"Gunter Schlierkamp", "Armin Scholz", "Peggy Schoolcraft",
				"Anja Schreiner", "Larry Scott", "Kathy Segal", "Carol Semple",
				"Krisztina Sereny", "Alex Shabunya", "Sergey Shelestov",
				"Elena Shportun", "Nasser El Sonbaty", "Pudgy Stockton",
				"Giuliano Stroe", "Gary Strydom", "Becca Swanson",
				"Kristi Tauti", "Joanna Thomas", "Dennis Tinerino",
				"Craig Titus", "Isabelle Turell", "Murali Vijayakumar",
				"Branch Warren", "Dena Westerfield", "Flex Wheeler",
				"Claudia Wilbourn", "Latisha Wilder", "Kate Williams",
				"Christi Wolf", "Lyen Wong", "Jenny Worth", "Dorian Yates",
				"Don Youngblood", "Frank Zane", "Arnold Schwarzenegger" };
		return new ArrayList<String>(Arrays.asList(persons));
	}

}
