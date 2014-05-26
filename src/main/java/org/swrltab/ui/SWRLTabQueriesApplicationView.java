package org.swrltab.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.JFrame;

import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLAPIFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.drools.DroolsFactory;
import org.swrlapi.drools.DroolsSWRLRuleEngineCreator;
import org.swrlapi.ui.model.SWRLAPIApplicationModel;
import org.swrlapi.ui.view.queries.SWRLAPIQueriesView;

public class SWRLTabQueriesApplicationView extends JFrame
{
	private static final long serialVersionUID = 1L;

	private static final String APPLICATION_NAME = "SWRLTabQueries";
	private static final int APPLICATION_WIDTH = 1000;
	private static final int APPLICATION_HEIGHT = 580;

	public static void main(String[] args)
	{
		String owlFileName = "";

		if (args.length == 1) {
			owlFileName = args[0];
		} else
			Usage();

		try {
			DefaultPrefixManager prefixManager = SWRLAPIFactory.createPrefixManager();
			SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOWLOntology(owlFileName, prefixManager);
			SWRLRuleEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(swrlapiOWLOntology,
					new DroolsSWRLRuleEngineCreator());
			SWRLAPIApplicationModel applicationModel = SWRLAPIFactory.createSWRLAPIApplicationModel(swrlapiOWLOntology,
					queryEngine, prefixManager);
			SWRLTabQueriesApplicationView applicationView = new SWRLTabQueriesApplicationView(applicationModel);

			applicationView.setVisible(true);
		} catch (RuntimeException e) {
			System.err.println("Error starting application: " + e.getMessage());
			System.exit(-1);
		}
	}

	public SWRLTabQueriesApplicationView(SWRLAPIApplicationModel applicationModel)
	{
		super(APPLICATION_NAME);
		createAndAddSWRLAPIQueriesView(applicationModel);
	}

	private void createAndAddSWRLAPIQueriesView(SWRLAPIApplicationModel applicationModel)
	{
		Icon ruleEngineIcon = DroolsFactory.getSWRLRuleEngineIcon();
		SWRLAPIQueriesView queriesView = new SWRLAPIQueriesView(applicationModel, ruleEngineIcon, null);
		Container contentPane = getContentPane();

		contentPane.setLayout(new BorderLayout());
		contentPane.add(queriesView);
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
	}

	@Override
	protected void processWindowEvent(WindowEvent e)
	{
		super.processWindowEvent(e);

		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.setVisible(false);
			System.exit(0);
		}
	}

	private static void Usage()
	{
		System.err.println("Usage: " + SWRLTabQueriesApplicationView.class.getName() + " <owlFileName>");
		System.exit(1);
	}
}
