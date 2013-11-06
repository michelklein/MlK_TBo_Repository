package de.uni.mannheim.semantic.jena;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

import de.uni.mannheim.semantic.FetchGeoData;
import de.uni.mannheim.semantic.model.Interest;
import de.uni.mannheim.semantic.model.Location;
import de.uni.mannheim.semantic.model.Person;
import de.uni.mannheim.semantic.util.PropertiesUtils;

public class CelebritiesFetcher {

	private FetchGeoData geocoding = new FetchGeoData();

	public static void main(String[] args) throws IOException {
<<<<<<< HEAD

		CelebritiesFetcher.get().getGenreFromFile("Red Heat (1988)");
=======
		// CelebritiesFetcher.get().getCelebrity("Arnold SchwarzeneggerKonrad Adenauer");
		Set<Location> celebrityLocations = CelebritiesFetcher.get()
				.getCelebrityLocations("Arnold Schwarzenegger");
		System.out.println("Arnold");
		for (Location loc : celebrityLocations) {
			System.out.println(loc.toString());
		}
		celebrityLocations = CelebritiesFetcher.get().getCelebrityLocations(
				"Konrad Adenauer");
		System.out.println("Konni");
		for (Location loc : celebrityLocations) {
			System.out.println(loc.toString());
		}
>>>>>>> refs/remotes/origin/master
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

	public Person createCel(String name) {
		Person p = null;
		String givenName = "";
		String surname = "";
		String date = "";
		String tn = "";
		Set<String> type = new HashSet<String>();
		String personIdent = "";
		RDFNode birthplace = null;
		boolean first = true;
		List<Interest> interests = new ArrayList<Interest>();
		ResultSet resSet = getCelebrityBasicInfo(name);

		while (resSet.hasNext()) {
			QuerySolution s = resSet.nextSolution();
			givenName = gll(s, "givenName");
			surname = gll(s, "surname");
			date = gll(s, "date");
			tn = g(s, "thumbnail");
			// String resName = s
			// .get("birthPlace")
			// .toString()
			// .substring(
			// s.get("birthPlace").toString().lastIndexOf("/") + 1);
			// Location location = geocoding.getLocation(resName);
			// home = new Institution(gll(s, "label"), location);
			if (first) {
				givenName = gll(s, "givenName");
				surname = gll(s, "surname");
				date = gll(s, "date");
				tn = g(s, "thumbnail");
				birthplace = s.get("birthPlace");
				personIdent = g(s, "p");
				first = false;
			}

			type.add(g(s, "sc"));
			if (is(type, "artist")) {
				System.out.println("artist?");
				resSet = getAlbums(personIdent);

				String oldLabel = "";
				Set<String> g = null;
				while (resSet.hasNext()) {
					s = resSet.nextSolution();
					System.out.println(s);
					String label = gll(s, "label");
					if (label.indexOf("(") != -1)
						label = label.substring(0, label.indexOf("("));

					if (!oldLabel.equals(label)) {
						System.out.println("new " + label);
						g = new HashSet<String>();
						Interest i = new Interest("music", "cover_url", g,
								"id", label);
						interests.add(i);
						oldLabel = label;
					} else {
						g.add(g(s, "genre"));
					}

				}
			}
			if (is(type, "actor")) {
				System.out.println("getMovies");
				resSet = getMovies(personIdent);
				while (resSet.hasNext()) {
					System.out.println("1");
					s = resSet.nextSolution();
					String label = gll(s, "label");
					String year = gll(s, "year");
					System.out.println("year"+year);
					if (label.indexOf("(") != -1)
						label = label.substring(0, label.indexOf("("));
					Interest i = new Interest("movie", "cover_url",
							getGenreFromFile(label), "id", label);
					interests.add(i);

				}

			}

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			try {
				p = new Person(givenName, surname, df.parse(date),
						getCelebrityLocations(name), interests, tn);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return p;
		}
		return p;
	}

	private boolean is(Set<String> type, String string) {
		for (String d : type) {
			if (d.toLowerCase().contains(string))
				return true;
		}
		return false;
	}

	private ResultSet getCelebrityBasicInfo(String celName) {
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
				.append("FILTER (?name='" + celName + "'@en)")
				.append("FILTER(NOT EXISTS { ?birthPlace a dbpedia-owl:Country} )")
				.append("}");
		ResultSet rs = execute("http://dbpedia.org/sparql", builder.toString());
		return rs;
	}

	private Set<Location> getCelebrityLocations(String celebrityName) {
		Set<Location> locations = new HashSet<Location>();
		ResultSet rs = execute("http://dbpedia.org/sparql",
				QueryHelper.getBirthplaceDeathplaceQuery(celebrityName));

		// extract birthplace and deathplace
		while (rs.hasNext()) {
			Location loc = null;
			QuerySolution s = rs.nextSolution();
			RDFNode birthPlaceNode = s.get("bname");
			if (birthPlaceNode != null) {
				loc = geocoding.getLocation(getNodeName(birthPlaceNode),
						Location.BIRTHPLACE);
				if (loc != null) {
					locations.add(loc);
				}
			}
			RDFNode deathPlaceNode = s.get("dname");
			if (deathPlaceNode != null) {
				loc = geocoding.getLocation(getNodeName(deathPlaceNode),
						Location.DEATHPLACE);
				if (loc != null) {
					locations.add(loc);
				}
			}
		}

		rs = execute("http://dbpedia.org/sparql",
				QueryHelper.getAlmaMataQuery(celebrityName));

		// extract university locations
		while (rs.hasNext()) {
			Location loc = null;
			QuerySolution s = rs.nextSolution();
			RDFNode birthPlaceNode = s.get("uniName");
			if (birthPlaceNode != null)
				loc = geocoding.getLocation(getNodeName(birthPlaceNode),
						Location.EDUCATIONPLACE);

			if (loc != null) {
				locations.add(loc);
			}
		}

		return locations;
	}

	private String getNodeName(RDFNode node) {
		if (node.isResource()) {
			return node.asResource().getLocalName();
		} else if (node.isLiteral()) {
			return node.asNode().getLiteralLexicalForm();
		}

		return null;
	}

	private ResultSet getMovies(String sameAs) {
		StringBuilder builder = new StringBuilder();
		builder.append("PREFIX ont: <http://dbpedia.org/ontology/>")
				.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/>")
				.append("PREFIX dbpprop: <http://dbpedia.org/property/>")
				.append("PREFIX owl: <http://www.w3.org/2002/07/owl#>")
				.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>")
				.append("PREFIX dcterms: <http://dublincore.org/documents/2012/06/14/dcmi-terms/>")
				.append("PREFIX skos: <http://www.w3.org/2004/02/skos/core#>")
				.append("SELECT Distinct ?label WHERE {")
				.append("?p rdfs:label ?label.")
				.append("?p dcterms:subject ?year.")
//				.append("?year skos:broader <http://dbpedia.org/resource/Category:Films_by_year>.")
				.append("?p ont:starring <" + sameAs + ">.")
				.append("FILTER (LANG(?label)='en')").append("}");
		ResultSet rs = execute("http://dbpedia.org/sparql", builder.toString());
		return rs;
	}

	private ResultSet getAlbums(String sameAs) {
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
		ResultSet rs = execute("http://dbpedia.org/sparql", builder.toString());
		return rs;
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

	private Set<String> getGenreFromFile(String ss) {
		Set<String> g = new HashSet<String>();
		try {
			String url = PropertiesUtils.class.getClassLoader()
					.getResource("genres.list").getFile();
			BufferedReader bf = new BufferedReader(new FileReader(url));
			String line;
			while ((line = bf.readLine()) != null) {
				int indexfound = line.indexOf(ss);
				if (indexfound > -1) {
					g.add(line.substring(line.lastIndexOf("\t") + 1));
				}
			}
			bf.close();
		} catch (IOException e) {
			System.out.println("IO Error Occurred: " + e.toString());
		}

		for (String string : g) {
			System.out.println(string);
		}
		return g;
	}

	private String gll(QuerySolution s, String string) {
		RDFNode x = s.get(string);
		if (x != null) {
			String result = x.asNode().getLiteralLexicalForm();
			return result;
		} else {
			return null;
		}
	}

	private String g(QuerySolution s, String string) {
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
				result.add(new Person(firstName, lastName, date, null, null,
						null));
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
			StringBuilder builder = new StringBuilder();
			builder.append(
					"PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> ")
					.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/> ")
					.append("PREFIX ont: <http://dbpedia.org/ontology/>")
					.append("PREFIX dbpprop: <http://dbpedia.org/property/>")
					.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>")
					.append("select DISTINCT ?firstname ?lastname where {")
					.append("?p foaf:givenName ?firstname.")
					.append("?p foaf:surname ?lastname.")
					.append("?p ont:thumbnail ?thumbnail.")
					.append("?p ont:birthPlace <http://dbpedia.org/resource/Germany>.")
					.append("?p ont:birthDate ?birthDate.")
					.append("?p a ?type.")
					.append("?type rdfs:subClassOf ?sc.")
					.append(" FILTER (!isBlank(?lastname) &&")
					.append("((?sc = <http://dbpedia.org/class/yago/Actor109765278>)||(?sc = <http://dbpedia.org/class/yago/Musician110339966>)||(?sc = <http://dbpedia.org/class/yago/Writer110794014>))")
					.append(")} ORDER BY ASC(?lastname) ");

			// Remote execution.
			QueryExecution qexec = QueryExecutionFactory.sparqlService(
					"http://dbpedia.org/sparql", builder.toString());
			// Set the DBpedia specific timeout.
			((QueryEngineHTTP) qexec).addParam("timeout", "10000");

			// Execute.
			ResultSet rs = qexec.execSelect();
			String firstName = null;
			String lastName = null;
			String fullname = null;
			while (rs.hasNext()) {

				QuerySolution soln = rs.nextSolution();
				firstName = gll(soln, "firstname");
				lastName = gll(soln, "lastname");
				fullname = String.format("%s %s", firstName, lastName);
				if (!celebrities.contains(fullname)) {
					System.out.println("\"" + fullname + "\",");
					celebrities.add(fullname);
				}
			}
			// System.out.println(celebrities.size());
			qexec.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return celebrities;
	}

	public List<String> getDummyCelebrities() {
		String[] persons = { "Ursula Acosta", "Konrad Adenauer",
				"Heinrich Cornelius Agrippa", "Fatih Akın", "Hans Albers",
				"Hugo Alpen", "Götz Alsmann", "Betty Amann", "Manuel Andrack",
				"Daniil Andreev", "Tom Angelripper", "Thomas Such Angelripper",
				"Fritz Anneke", "Hannah Arendt", "Ernst Arndt", "Carl Auen",
				"Anita Augspurg", "Kristina Bach", "Kerstin Bräuer Bach",
				"Hugo Ball", "Peter Baltes", "Briana Banks",
				"Patrice Bart-Williams", "Patrice Bart-Williams Bart-Williams",
				"Karl Bartos", "Albert Bassermann", "Adolf Bastian",
				"Gabi Bauer", "Asli Bayram", "Hartmut Becker", "Jens Becker",
				"Albrecht Becker", "Sybille Bedford", "Albrecht Behmel",
				"Peter Behrens", "Iris Berben", "Anita Berber",
				"Karl August Von Bergen", "Christian Berkel",
				"Herman Berlinski", "Paul Bern", "Heinz Bernard",
				"Dru Berrymore", "Sonja Bertram", "Ilka Bessin", "Frank Beyer",
				"Christian Biegai", "Josef Bierbichler", "Paul Bildt",
				"Heinrich Biltz", "Wilhelm Biltz", "Herman Bing",
				"Susan Blakely", "Mathilde Blind", "Phillip Boa",
				"Ernst Ulrich Figgen Boa", "Gero Boehm",
				"Dieter Bohlen Bohlen", "Dieter Bohlen", "Uwe Bohm",
				"Margarete Bohme", "Carsten Bohn", "Felix Bohnke Bohnke",
				"Felix Bohnke", "Wigald Boning", "Wolfgang Borchert",
				"Dieter Borsche", "Kate Bosse-Griffiths", "Antoinette Bower",
				"Eric Braeden", "Willy Brandt", "Vytas Brenner",
				"Rolf Dieter Brinkmann", "Clyde Jackson Browne Browne",
				"Jackson Browne", "Friederike Brun", "Natja Brunckhorst",
				"Till Brönner", "Peter Brötzmann", "Horst Buchholz",
				"Francis Buchholz", "Charles Bukowski", "Gedeon Burkhard",
				"Jochen Busse", "Otto Butschli", "Heinrich Böll",
				"Georg Büchner", "Joachim Camerarius", "Mandy Capristo",
				"Mandy Grace Capristo Capristo", "Peter Carsten",
				"Guy Chadwick", "Guy Stephen Chadwick Chadwick",
				"Janine Chang", "Sophie Charlotte", "Atul Chitnis",
				"Roger Cicero", "Roger Marcel Cicero Ciceu Cicero",
				"Sarah Connor", "Sarah Marianne Corina Lewe Connor",
				"Douglas Coupland", "Susanne Cramer", "Esra Dalfidan",
				"Max Davidson", "Jan Degenhardt", "Samy Deluxe",
				"Samy Sorge Deluxe", "Joy Denalane",
				"Joy Maureen Denalane Denalane", "Barbara Dennerlein",
				"Ulrich Deppendorf", "Andi Deris", "Andreas Deris Deris",
				"Paul Dessau", "Blerimos Destani",
				"Christopher von Deylen Deylen", "Christopher von Deylen",
				"August Diehl", "Marlene Dietrich", "Anton Diffring",
				"Klaus Dinger", "Udo Dirkschneider", "Olli Dittrich",
				"Ramin Djawadi", "Klaus Doldinger",
				"Klaus Erich Dieter Doldinger Doldinger", "Monty Don",
				"Heinz Drache", "Ken Duken", "Mario Duschenes",
				"Alexandra Von Dyhrn", "Stephan Ebn", "Stephan Ebn Ebn",
				"Nazan Eckes", "Arnold Ehret", "Julius Eichberg",
				"Annemarie Eilfeld", "Siegfried Einstein", "Alfred Einstein",
				"Scott Elrod", "Hannelore Elsner", "Michael Ende",
				"August Engelhardt", "Wera Engels", "John Ericson",
				"Andreas Eschbach", "Yona Ettlinger", "Herbert Eulenberg",
				"Tobias Exxel", "Tobias Exxel Exxel", "Carolina Eyck",
				"Peter Faber", "Frank Farian", "Franz Reuther Farian",
				"Michael Fassbender", "Alexander Fehling", "Fritz Feld",
				"Hansjörg Felmy", "Dirk Felsenheimer",
				"Dirk Felsenheimer Felsenheimer", "Heino Ferch",
				"Veronica Ferres", "Max Fiedler", "Tommy Finke",
				"Thomas David Finke Finke", "Helmut Fischer", "Wolfgang Flur",
				"Carolin Fortenbacher", "Walter Franck", "Horst Frank",
				"Andreas Frege", "Andreas Frege Frege", "Matthias Freihof",
				"Gert Fröbe", "Gustav Fröhlich", "Francis Fulton-Smith",
				"Benno Fürmann", "HARTMUT GRÜNDLER",
				"Günter Caspelherr Gabriel", "Gunter Gabriel",
				"Martina Gedeck", "Jasmin Gerat", "Henry Gerber",
				"Tom Gerhardt", "Dora Gerson", "Petra Gerster",
				"Sascha Gerstner", "Erwin Geschonneck", "Christian Gill",
				"Leopold Friedrich Gunther von Goeckingk", "Dero Goi",
				"Marian Gold", "Hartwig Schierbaum Gold", "Emmy Goring",
				"Walter Gotell", "Sonja Graf", "Roland Grapow", "Helmut Griem",
				"Herbert Arthur Wiglev Clamor Grönemeyer Grönemeyer",
				"Herbert Grönemeyer", "Senna Guemmour",
				"Senna Guemmour Guemmour", "Adem Gunes", "Karl Eugen Guthe",
				"Michael Gwisdek", "Max Gülstorff", "Egon Günther",
				"Käthe Haack", "Dolly Haas", "Karl Haas", "Eva Habermann",
				"Lutz Hachmeister", "Alexander von Borsig Hacke",
				"Alexander Hacke", "Sebastian Haffner", "Uta Hagen",
				"Hannelore Haller Haller", "Hanne Haller", "Charles Hallé",
				"Lars Halter", "Michael Haneke", "Corinna Harfouch",
				"Corinna Harney", "Silvia Hartman", "Walter Hasenclever",
				"Otto Eduard Hasse", "Wolke Hegenbarth", "Elke Heidenreich",
				"Benjamin Heisenberg", "Markus Heitz", "Brigitte Helm",
				"Michael Herbig", "Christoph Maria Herbst", "Philip A Herfort",
				"Eva Herman", "Judith Hermann", "Irm Hermann", "Klaus Heuser",
				"Klaus Heuser Heuser", "Werner R. Heymann",
				"Paul Johann Ludwig von Heyse", "Edgar Hilsenrath",
				"Volker Hinkel", "Gustav Hinrichs",
				"Johannes-Matthias Hoenscheid", "Johanna Hofer",
				"Wolf Hoffmann", "Wolfgang Hohlbein", "Felicitas Hoppe",
				"Nat Horler", "Natalie Christine Horler Horler", "Nina Hoss",
				"Henry Hubchen", "Robert Hubner", "Wilhelm Hunermann",
				"Hermann Häfker", "Ralf Illenberger", "Matthias Jabs",
				"Horst Janson", "Walter Janssen", "Julia Jentsch",
				"Jens Joneleit", "Steffen Jurgens", "Heidi Kabel",
				"Michael Kaeshammer", "Paul Kalkbrenner",
				"Koh Gabriel Kameda Kameda", "Koh Gabriel Kameda",
				"Alexandra Kamp", "Fritz Kampers", "Richard Kandt",
				"Friedrich Kapp", "Thomas Karaoglan", "Hellmuth Karasek",
				"Sigfrid Karg-Elert", "Maya Karin", "Bruno Kastner",
				"Kitty Kat", "Katharina Löwel Kat", "Bel Kaufman",
				"Günther Kaufmann", "Rick Kavanian", "John Kay",
				"Joachim Fritz Krauledat Kay", "Friedrich Kayßler",
				"Daniel Kehlmann", "Sibel Kekilli", "Bernhard Kellermann",
				"Navid Kermani", "Georg Michael Kerschensteiner",
				"Martin Kesici", "Martin Kesici Kesici", "Udo Kier",
				"Gershon Kingsley", "Götz Gustav Ksinski Kingsley",
				"Michael Kiske", "Bahar Kizil", "Bahar Kızıl Kizil",
				"Stefan Klein", "Rudolf Klein-Rogge", "Werner Klemperer",
				"Anja Kling", "Friedrich Gottlieb Klopstock",
				"Alexander Kluge", "Hildegard Frieda Albertine Knef",
				"Henny Koch", "Sebastian Koch", "Eberhard Koebel",
				"Juliane Kohler", "Wolfgang Kohlhaase", "Karl Korsch",
				"Siegfried Kracauer", "Mariom Kracht", "Till Kraemer",
				"Dagmar Krause", "Werner Krauss", "Nicolette Krebitz",
				"Kurt Kreuger", "Joachim Krol", "Mike Kruger", "Hardy Kruger",
				"Monika Kruse", "Johannes Kuhlo", "Rolf Kuhn",
				"Michael Kumpfmuller", "Andy Kuntz", "Karl Friedrich Kurz",
				"Petra Kusch-Luck", "Erich Kästner",
				"Abraham Gotthelf Kästner", "Hansi Kürsch",
				"Hans Jürgen Kürsch Kürsch", "Friedrich de La Motte Fouque",
				"Paul Landers", "Heiko Paul Hiersche Landers", "Max Lange",
				"Norman Langen", "Norman Langen Langen", "von der Lasa",
				"Else Lasker-Schueler", "Kurd Lasswitz", "Christine Laszar",
				"Friedrich Christian Laukhard", "Martin Lawrence",
				"Benjamin Lebert", "Volker Lechtenbrink", "Gretchen Lederer",
				"Sheryl Lee", "Louise-Rosalie Lefebvre", "Ute Lemper",
				"Michael Leuschner", "Jaki Liebezeit", "Harry Lietdke",
				"Claire Loewenfeld", "Wolf Von Lojewski", "Pietro Lombardi",
				"Susanne Lothar", "Shy Love", "Dirk von Lowtzow",
				"Ernst Lubitsch", "Jens Ludwig", "Jens Ludwig Ludwig",
				"Florian Lukas", "Wolfgang Lukschy", "Meyer Lutz",
				"Christopher Lutz", "Allison Mack", "Charlotte von Mahlsdorf",
				"Armin Maiwald", "Heike Makatsch", "Judith Malina",
				"Albert Mangelsdorff", "Erika Mann", "Klaus Mann",
				"Alexander Marcus", "Felix Rennefeld Marcus", "Carlos Marin",
				"Carlos Marín Marin", "George Markstein", "Sven Martinek",
				"Michael Mayer", "Dave McClain", "Logan McCree",
				"Audra Ann McDonald", "Maxim Mehmet", "Heinrich Meibom",
				"Klaus Meine", "Anton Memminger", "Nick Menza",
				"Reinhard Friedrich Michael Mey Mey", "Reinhard Mey",
				"Kuno Meyer", "Joseph Meyer", "Hubert von Meyerinck",
				"Malwida von Meysenbug", "Jörg Michael Michael",
				"Jorg Michael", "Jacques Mieses", "Bernhard Minetti",
				"Tyra Misoux", "Dominic Monaghan", "Philipp Moog",
				"Erna Morena", "Barbara Morgenstern", "Christian Morgenstern",
				"Irmtraud Morgner", "Julius Mosen", "Anna Maria Muhe",
				"Erich Muhsam", "Karsten Muller", "Richy Muller",
				"Heiner Muller", "Melanie Munch", "Melanie Münch Munch",
				"Roberto Munoz", "Brent Mydland Mydland", "Brent Mydland",
				"Ralf Möller", "Renate Müller", "Herbert Nachbar",
				"Peretz Naftali", "Thomas Nast", "Johann Gottlieb Naumann",
				"Uwe Nettelbeck", "Luise Neumann", "Mike Nichols",
				"Wolfgang Niedecken", "Wolfgang von Niedecken Niedecken",
				"Martin Niemöller", "Wolfgang Niersbach", "Jurgen Noldner",
				"Dieter Noll", "Klaus Sperber Nomi", "Klaus Nomi",
				"Charles Nordhoff", "Gabi Novak", "Mirjam Novak", "Erik Ode",
				"Gil Doron Reichstadt Ofarim Ofarim", "Gil Ofarim",
				"Andre Olbrich", "Evelyn Opela", "Paul Otto", "Gotz Otto",
				"Jana Pallaske", "Lee Parry", "Kathrin Passig",
				"Wilhelm Pauck", "Jean Paul", "Doro Pesch",
				"Dorothee Pesch Pesch", "Sven Pipien", "Frank Plasberg",
				"August Graf von Platen-Hallermunde", "Peter Pohl",
				"Ingo Pohlmann", "Ingo Pohlmann Pohlmann", "Orna Porat",
				"Henny Porten", "Franka Potente", "Axel Prahl",
				"Volker Prechtel", "Josefine Preuß", "Jurgen Prochnow",
				"Leonard Proxauf", "Stefan Konrad Raab", "Matthias Otto Raabe",
				"Max Raabe", "Peter Rabe", "Mady Rahl", "Judith Rakers",
				"Hanna Ralph", "Herman Rarebell", "Sigurd Rascher",
				"Hermann Friedrich Raupach", "Hans-Rolf Rippert Rebroff",
				"Ivan Rebroff", "Eberhard Rees", "Achim Reichel",
				"Hans Reichel", "Hedwiga Reicher", "Frank Reicher",
				"Carl Heinrich Carsten Reinecke", "Janin Reinhardt",
				"Walter Renneisen", "H.A. Rey", "Margret Elizabeth Rey",
				"Raúl Richter", "Karl Richter", "Amy Ried", "Katja Riemann",
				"Maria Riva", "Rudolf Rocker", "Hartmut Rohde",
				"Frederick Rolf", "Heinrich Roller", "Andreas Jakob Romberg",
				"Rosemarie Schwab Roos", "Mary Roos", "Timo Rose",
				"Eugen Rosenstock-Huessy", "Eddie Rosner",
				"Anneliese Rothenberger", "Anthony Rother", "Michael Rother",
				"Saul Rubinek", "Bernd Ruf", "Sig Ruman", "Kevin Russell",
				"Charles Régnier", "Wil Röttgen", "Heinz Rühmann",
				"Maya Saban", "Gunter Sachs", "Rudiger Safranski",
				"Marianne Sagebrecht", "Dirk Sager", "Salomé Salome",
				"Tobias Sammet Sammet", "Tobias Sammet", "Jeff Sampson",
				"Wolfgang W.E. Samuel", "Otto Sander", "Pierre Sanoussi-Bliss",
				"Kool Savas", "Sava? Yurderi Savas", "Andrea Sawatzki",
				"Betty Schade", "Yvonne Maria Schaefer", "Hannes Schafer",
				"Michaela Schaffrath", "Johanna Schall", "Angela Schanelec",
				"Frank Schatzing", "Ralf Scheepers", "Samuel Scheidt",
				"Rudolf Schenker", "Tom Schilling", "Marcel Schirmer",
				"Marcel Schirmer Schirmer", "Johannes Schlaf",
				"Jorn Schlonvoigt", "Uwe Schmidt", "Uwe H. Schmidt Schmidt",
				"Manni Schmidt", "Manfred Schmidt Schmidt",
				"Petra Schmidt-Schaller", "Christiane Schmidtmer",
				"Sybille Schmitz", "Ralf Schmitz", "Helmuth Schneider",
				"Manfred Schnelldorfer", "Margarete Schon", "Reiner Schone",
				"Barbara Schoneberger", "Maria Schrader", "Heiko Schramm",
				"Margarethe Schreinemakers", "Arthur Schroder",
				"Werner Schroeter", "Heinrich Schroth", "Greta Schröder",
				"Corona Elisabeth Wilhelmine Schröter", "Daniel Schuhmacher",
				"Ernst Friedrich Schumacher", "Petra Schurmann", "Carl Schurz",
				"Annette Schwarz", "Matthias Schweighofer", "Hanna Schygulla",
				"Walter Sedlmayr", "Bernhard Seeger", "Reinhard Seehafer",
				"Erna Sellmer", "Sabrina Setlur Setlur", "Sabrina Setlur",
				"Marcus Siepen", "Marcus Siepen Siepen", "Simone Signoret",
				"Victoria Sin", "Sabine Sinjen", "Jeff Sipe",
				"Jeffrey Lee Sipe Sipe", "Reinhard Johannes Sorge",
				"Edward Lee Spence", "Nick St. Nicholas",
				"Klaus Karl Kassbaum St. Nicholas", "Bernd Stelter",
				"Andreas Stenschke", "Fritz Richard Stern", "Wolfgang Stock",
				"Werner Stocker", "Theodor Storm", "Erwin Strittmatter",
				"Friedrich Armand Strubberg", "Ruben Studdard",
				"Christopher Theodore Ruben Studdard Studdard",
				"Muhammed Suicmez", "Tanja Szewczenko", "Hans Söhnker",
				"Richard Talmadge", "Horst Tappert", "Thomas Teige",
				"Uwe Tellkamp", "Yasser Thabet", "Katharina Thalbach",
				"Hilmar Thate", "Nyanaponika Thera", "Anna Thomas",
				"Harry Thumann", "Harald Thumann Thumann", "Paul Tillich",
				"Uwe Timm", "Branko Tomovic", "Antje Traue", "Theo Trebs",
				"Benjamin Trinks", "Kelly Trump", "Ulrich Tukur", "Tom Tykwer",
				"Thees Uhlmann", "Karl Heinrich Ulrichs", "Julius Urgiss",
				"Sven Vath", "Jan Ulrich Max Vetter Vetter",
				"Jan Ulrich Max Vetter", "Jurgen Vogel", "Rudiger Vogler",
				"Karl Vollmoller", "Jasmin Wagner", "Peter Wagner Wagner",
				"Peavy Wagner", "Rudolf von Waldenfels",
				"Gustav von Wangenheim", "Aribert Wascher",
				"Marsha Wattanapanich",
				"Reginald Lucien Frank Roger Watts Watts", "Reggie Watts",
				"Michael Ingo Joachim Weikath Weikath", "Michael Weikath",
				"Eleonore Weisgerber", "Peter Weiss", "Dorrit Weixler",
				"Daniel Welbat", "Daniel Welbat Welbat", "Guenter Wendt",
				"Fritz Wepper", "Ursula Werner", "Matt Damon",
				"Ruth Westheimer", "Charles Wheeler", "Herb Wiedoeft",
				"Karl von Wiegand", "Mathias Wieman", "Adolf Winkelmann",
				"Judy Winter", "Frank Wisbar", "Christian Wolff",
				"Alfred Wolfsohn", "Klaus Wunderlich", "Martin Wuttke",
				"William Wyler", "Dana Wynter", "Natalia Wörner", "Jerry Zaks",
				"Frank Zander", "Ruth Zechlin", "Michael Zittel",
				"Rolf Zuckowski", "Günter de Bruyn" };
		return new ArrayList<String>(Arrays.asList(persons));
	}

	public String getDummyCelebritiesAsJson() {
		JsonObject json = new JsonObject();
		JsonArray array = new JsonArray();
		for (String celebrity : getDummyCelebrities()) {
			json.put("celebritie", celebrity);
			array.add(celebrity);
		}
		return array.toString();
	}

	public String getCelebritiesAsJson() {
		JsonObject json = new JsonObject();
		JsonArray array = new JsonArray();
		for (String celebrity : getCelebrities()) {
			json.put("celebritie", celebrity);
			array.add(celebrity);
		}
		return array.toString();
	}
}
