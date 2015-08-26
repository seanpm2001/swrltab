package org.swrltab.test;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.test.RegressionTester;

import java.io.File;

/**
 * Uses a {@link RegressionTester} to individually execute all SQWRL queries in an ontology and
 * compare the generated result with the expected result stored in the <code>rdfs:comment</code> annotation associated
 * with each query.
 * 
 * @see RegressionTester
 */
public class SWRLTabRegressionTester
{
  public static void main(@NonNull String[] args)
  {
    if (args.length != 1)
      Usage();

    String owlFileName = args[0];
    File owlFile = new File(owlFileName);

    try {
      OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
      OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(owlFile);
      DefaultPrefixManager prefixManager = new DefaultPrefixManager();
      OWLDocumentFormat format = ontology.getOWLOntologyManager().getOntologyFormat(ontology);

      if (format.isPrefixOWLOntologyFormat())
        prefixManager.copyPrefixesFrom(format.asPrefixOWLOntologyFormat().getPrefixName2PrefixMap());

      SQWRLQueryEngine sqwrlQueryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

      RegressionTester regressionTester = new RegressionTester(sqwrlQueryEngine);

      regressionTester.run();
    } catch (OWLOntologyCreationException e) {
      System.err.println("Error creating OWL ontology from file " + owlFile.getAbsolutePath() + ": " + e.getMessage());
      System.exit(-1);
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
  }

  private static void Usage()
  {
    System.err.println("Usage: " + SWRLTabRegressionTester.class.getName() + " <owlFileName>");
    System.exit(1);
  }
}

// SWRLRuleRenderer renderer = swrlapiOWLOntology.createSWRLRuleRenderer();
// for (SWRLAPIRule rule : swrlapiOWLOntology.getSWRLAPIRules())
// System.out.println(renderer.renderSWRLRule(rule));

// SWRLParser parser = new SWRLParser(swrlapiOWLOntology);
// Scanner scanner = new Scanner(System.in);
// while (true) {
// String line = scanner.nextLine();
// try {
// System.out.println("Rule: " + line);
// parser.parseSWRLRule(line, true);
// System.out.println("Fine");
// } catch (SWRLIncompleteRuleException e) {
// System.err.println("Incomplete " + e.getMessage());
// } catch (SWRLParseException e) {
// System.err.println("Error " + e.getMessage());
// }
// }
