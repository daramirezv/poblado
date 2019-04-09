package rdf4j.uniandes.edu.co;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryResult;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

/**
 * RDF Tutorial example 07: Adding an RDF Model to a database
 *
 * @author Jeen Broekstra
 */
public class Example07AddRDFToDatabase {

	public static void main(String[] args)
			throws IOException
	{

		// First load our RDF file as a Model.
		String filename = "example-data-artists.ttl";
		InputStream input = Example07AddRDFToDatabase.class.getResourceAsStream("/" + filename);
		Model model = Rio.parse(input, "", RDFFormat.TURTLE);

		// Create a new Repository. Here, we choose a database implementation
		// that simply stores everything in main memory. Obviously, for most real-life applications, you will
		// want a different database implementation, that can handle large amounts of data without running
		// out of memory and keeps data safely on disk.
		// See http://docs.rdf4j.org/programming/#_the_repository_api for more extensive examples on
		// how to create and maintain different types of databases.
		Repository db = new SailRepository(new MemoryStore());
		db.initialize();

		// Open a connection to the database
		try (RepositoryConnection conn = db.getConnection()) {
			// add the model
			conn.add(model);

			// let's check that our data is actually in the database
			try (RepositoryResult<Statement> result = conn.getStatements(null, null, null);) {
				while (result.hasNext()) {
					Statement st = result.next();
					System.out.println("db contains: " + st);
				}
			}
		}
		finally {
			// before our program exits, make sure the database is properly shut down.
			db.shutDown();
		}
	}
}