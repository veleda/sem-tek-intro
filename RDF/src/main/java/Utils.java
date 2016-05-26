import org.apache.jena.rdf.model.Model;

/**
 * Created by veronika on 26.05.16.
 *
 * Helper stuff for the methods in Main.
 *
 */
public class Utils {

    public static final String datafile = "/home/veronika/Foredrag/javaBin/RDF/src/main/resources/data.ttl";
    public static final String schemafile = "/home/veronika/Foredrag/javaBin/RDF/src/main/resources/rdfs_example.ttl";

    public static void printModel(Model model, String syntax) {
        model.write(System.out, syntax);
    }

    public static void printModelWithSeveralSyntaxes(Model model) {

        System.out.println("** RDF/XML **");
        printModel(model, "RDF/XML");

        System.out.println("\n** TURTLE **");
        printModel(model, "TURTLE");

        System.out.println("\n** N-TRIPLES **");
        printModel(model, "N-TRIPLES");

        System.out.println("\n** JSON-LD **");
        printModel(model, "JSON-LD");
    }
}
