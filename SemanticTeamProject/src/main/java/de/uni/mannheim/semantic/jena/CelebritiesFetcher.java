package de.uni.mannheim.semantic.jena;

import java.util.ArrayList;
import java.util.Arrays;
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

public class CelebritiesFetcher {

	private static CelebritiesFetcher instance;

	private CelebritiesFetcher() {

	}

	public static CelebritiesFetcher get() {
		if (instance == null) {
			instance = new CelebritiesFetcher();
		}
		return instance;
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
			System.out.println(result);
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
				System.out.print("\"" + fullname + "\",");
			}
			System.out.println(celebrities.size());
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
				"Don Youngblood", "Frank Zane" };
		return new ArrayList<String>(Arrays.asList(persons));
	}

}
