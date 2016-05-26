import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.*;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XSD;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by veronika on 25.05.16.
 *
 * An introduction to the Jena framework. Showing off handling RDF data and some simple RDFS reasoning.
 *
 */
public class Main {

    public static void main(String[] args) throws Exception {

        Model model = readFromFile(Utils.datafile);
        //Utils.printModelWithSeveralSyntaxes(model);

        Model rich = createNewStatements(model);
        //queryModel(rich);

        InfModel inferredModel = inferenceWithJena(rich, readFromFile(Utils.schemafile));
        //queryModel(inferredModel);
        Utils.printModel(inferredModel, "TURTLE");
    }

    public static Model readFromFile(String filename) throws FileNotFoundException {

        Model model = ModelFactory.createDefaultModel();
        model.read(new FileInputStream(filename), null, "TURTLE");

        return model;
    }

    public static Model createNewStatements(Model model) throws IOException {

        addPrefixes(model);

        // Adding a new resource
        Resource acando = model.createResource(Vocabulary.Acando)
                .addProperty(RDF.type, ResourceFactory.createResource(Vocabulary.Company))
                .addProperty(RDFS.label, "Acando er et flotters sted å jobbe!");

        // Creating properties
        model.createResource(Vocabulary.Veronika)
                .addProperty( model.createProperty(Vocabulary.worksAt), acando );

        // Literals
        model.createResource(Vocabulary.Veronika)
                .addProperty(FOAF.birthday, ResourceFactory.createTypedLiteral("1991-09-13", XSDDatatype.XSDdate))
                .addProperty(RDFS.label, ResourceFactory.createLangLiteral("Heisann", "no"));

        writeModelToFile(model);

        return model;
    }

    private static void addPrefixes(Model model) {
        Map<String, String> prefixes = new HashMap<>();
        prefixes.put("", Vocabulary.NS);
        prefixes.put("rdfs", RDFS.getURI());
        prefixes.put("foaf", FOAF.getURI());
        prefixes.put("xsd", XSD.getURI());

        model.setNsPrefixes(prefixes);
    }

    public static void writeModelToFile(Model model) throws IOException {
        model.write(new FileWriter("/home/veronika/Foredrag/javaBin/RDF/src/main/resources/output.ttl"), "TURTLE");
    }

    public static void queryModel(Model model) {

        ResIterator resourceIterator = model.listResourcesWithProperty(RDF.type);

        while (resourceIterator.hasNext()) {
            Resource resource = resourceIterator.next();
            if (resource.getNameSpace().equals(Vocabulary.NS)) {
                System.out.println(resource);
            }
        }
        System.out.println("\n");
    }

    public static InfModel inferenceWithJena(Model data, Model schema) {
        return ModelFactory.createRDFSModel(data, schema);
    }
}
