package rdf4j.uniandes.edu.co;

import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

/**
 * RDF Tutorial example 04: Writing an RDF model in RDF/XML syntax
 *
 * In this example, we show how you can use the Rio Parser/writer toolkit to write your
 * model in RDF/XML syntax.
 *
 * @author Jeen Broekstra
 */
public class Example04WriteRdfXml {

	public static void main(String[] args) {

		ValueFactory vf = SimpleValueFactory.getInstance();
		BNode address = vf.createBNode();

		ModelBuilder builder = new ModelBuilder();
		builder
				.setNamespace("ex", "http://example.org/")
				.subject("ex:Picasso")
					.add(RDF.TYPE, "ex:Artist")
					.add(FOAF.FIRST_NAME, "Pablo")
					.add("ex:homeAddress", address) // link the blank node
				.subject(address)			// switch the subject
					.add("ex:street", "31 Art Gallery")
					.add("ex:city", "Madrid")
					.add("ex:country", "Spain");

		Model model = builder.build();

		// Instead of simply printing the statements to the screen, we use a Rio writer to
		// write the model in RDF/XML syntax:
		Rio.write(model, System.out, RDFFormat.RDFXML);

		// Note that instead of writing to the screen using `System.out` you could also provide
		// a java.io.FileOutputStream or a java.io.FileWriter to save the model to a file
	}
}