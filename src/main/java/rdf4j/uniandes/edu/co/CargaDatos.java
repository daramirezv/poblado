package rdf4j.uniandes.edu.co;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.sparql.core.DatasetImpl;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.impl.TreeModel;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryResult;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

public class CargaDatos {
	
	
	public static void main(String[] args) throws IOException {
		
		ParameterizedSparqlString qs = new ParameterizedSparqlString(""
                + "prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "PREFIX dbo:     <http://dbpedia.org/ontology/>"
                + "\n"
                + "select distinct ?resource ?abstract where {\n"
                + "  ?resource rdfs:label 'University of Los Andes (Colombia)'@en.\n"
                + "  ?resource dbo:abstract ?abstract.\n"
                + "  FILTER (lang(?abstract) = 'en')}");
		

        QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", qs.asQuery());

        ResultSet results = exec.execSelect();

        while (results.hasNext()) {

            System.out.println(results.next().get("abstract").toString());
        }

        ResultSetFormatter.out(results);
        
//		// First load our RDF file as a Model.
//				InputStream inputstream = new FileInputStream("C:\\Users\\usuario\\Downloads\\final2.owl");
//				Model model = Rio.parse(inputstream, "", RDFFormat.RDFXML);
//
//				ValueFactory vf = SimpleValueFactory.getInstance();
//
//				// We want to reuse this namespace when creating several building blocks.
//				String ex = "http://www.grupo7.semanticweb.uniandes.edu.co/curso/articles/";
//
////				try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\usuario\\Downloads\\scholar1"))) {
////				    String line;
////				    while ((line = br.readLine()) != null) {
////				        String[] values = line.split(COMMA_DELIMITER);
////				        records.add(Arrays.asList(values));
////				    }
////				}
////				
//				// Create IRIs for the resources we want to add.
//				IRI picasso = vf.createIRI(ex, "Picasso");
//				IRI artist = vf.createIRI(ex, "Artist");
//				IRI picasso2 = vf.createIRI(ex, "Picasso");
//
//				// add our first statement: Picasso is an Artist
//				model.add(picasso, RDF.TYPE, artist);
//
//				// second statement: Picasso's first name is "Pablo".
//				model.add(picasso2, FOAF.FIRST_NAME, vf.createLiteral("Pablo"));
//				
//				System.out.println("wololo" + FOAF.FIRST_NAME);
//				// Create a new Repository. Here, we choose a database implementation
//				// that simply stores everything in main memory. Obviously, for most real-life applications, you will
//				// want a different database implementation, that can handle large amounts of data without running
//				// out of memory and keeps data safely on disk.
//				// See http://docs.rdf4j.org/programming/#_the_repository_api for more extensive examples on
//				// how to create and maintain different types of databases.
//				Repository db = new SailRepository(new MemoryStore());
//				db.initialize();
//
//				// Open a connection to the database
//				try (RepositoryConnection conn = db.getConnection()) {
//					// add the model
//					conn.add(model);
//
//					// let's check that our data is actually in the database
//					try (RepositoryResult<Statement> result = conn.getStatements(null, null, null);) {
//						while (result.hasNext()) {
//							Statement st = result.next();
//							System.out.println("db contains: " + st);
//						}
//					}
//					
//					//EL MIO
//					FileOutputStream fop = null;
//					File file;
//					String content = "This is the text content";
//
//					FileWriter fw = new FileWriter("C:\\Users\\usuario\\Downloads\\res.owl");
//
//					file = new File("C:\\Users\\usuario\\Downloads\\newfile.owl");
//					fop = new FileOutputStream(file);
//						
//					// Instead of simply printing the statements to the screen, we use a Rio writer to
//					// write the model in RDF/XML syntax:
//					Rio.write(model, System.out, RDFFormat.RDFXML);
//
//					// Note that instead of writing to the screen using `System.out` you could also provide
//					// a java.io.FileOutputStream or a java.io.FileWriter to save the model to a file
//					
//				}
//				
//				finally {
//					// before our program exits, make sure the database is properly shut down.
//					db.shutDown();
//				}
	}
}
