package org.swrltab.test;

import java.io.File;
import java.util.Scanner;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLAPIFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.parser.SWRLIncompleteRuleException;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.parser.SWRLParser;

/**
 * Example invocation: <code><pre>
 * java -jar ~/workspace/swrlapi/swrltab/target/swrltab-1.0-jar-with-dependencies.jar ~/workspace/swrlapi/swrltab/src/main/resources/projects/SWRLCoreTests.owl 
 * </pre></code>
 */
public class SWRLTabSWRLAPIRegressionTester
{
	public static void main(String[] args)
	{
		String testBase = "/Users/martin/workspace/swrlapi/swrltab/src/main/resources/projects/";
		// String owlFileName = testBase + "SWRLSimple.owl";
		// String owlFileName = testBase + "SQWRLCollectionsTests.owl";
		// String owlFileName = testBase + "SQWRLCoreTests.owl";
		// String owlFileName = testBase + "SWRLInferenceTests.owl";
		String owlFileName = testBase + "SWRLCoreTests.owl";
		File file = new File(owlFileName);

		try {
			OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
			OWLOntology ontology = SWRLAPIFactory.createOWLOntology(ontologyManager, file);
			DefaultPrefixManager prefixManager = SWRLAPIFactory.createPrefixManager(ontologyManager, ontology);
			SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOWLOntology(ontologyManager, ontology,
					prefixManager);

			SWRLParser parser = new SWRLParser(swrlapiOWLOntology, prefixManager);

			Scanner scanner = new Scanner(System.in);
			while (true) {
				String line = scanner.nextLine();
				try {
					System.out.println("Rule: " + line);
					parser.parseSWRLRule(line, true);
					System.out.println("Foine");
				} catch (SWRLIncompleteRuleException e) {
					System.err.println("Incomplete " + e.getMessage());
				} catch (SWRLParseException e) {
					System.err.println("Error " + e.getMessage());
				}
			}
			// SWRLRuleEngineFactory swrlRuleEngineFactory = SWRLAPIFactory.createSWRLRuleEngineFactory();
			// swrlRuleEngineFactory.registerRuleEngine(new DroolsSWRLRuleEngineCreator());
			//
			// SQWRLQueryEngine sqwrlQueryEngine = swrlRuleEngineFactory.createSQWRLQueryEngine(swrlapiOWLOntology);
			// SWRLAPIRegressionTester swrlapiRegressionTester = new SWRLAPIRegressionTester(sqwrlQueryEngine);
			//
			// swrlapiRegressionTester.run();
			// } catch (SWRLRuleEngineException e) {
			// e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static void Usage()
	{
		System.err.println("Usage: " + SWRLTabSWRLAPIRegressionTester.class.getName() + " <owlFileName>");
		System.exit(1);
	}
}