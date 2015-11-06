/* *********************************************************************** *
* project: org.matsim.*
* firstControler
* *
* *********************************************************************** *
* *
* copyright : (C) 2007 by the members listed in the COPYING, *
* LICENSE and WARRANTY file. *
* email : info at matsim dot org *
* *
* *********************************************************************** *
* *
* This program is free software; you can redistribute it and/or modify *
* it under the terms of the GNU General Public License as published by *
* the Free Software Foundation; either version 2 of the License, or *
* (at your option) any later version. *
* See also COPYING, LICENSE and WARRANTY file *
* *
* *********************************************************************** */ 

package playground.ikaddoura.analysis.linkDemand;

import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;
import org.matsim.core.scenario.MutableScenario;
import org.matsim.core.scenario.ScenarioUtils;

public class LinkDemandAnalysisMain {
	
//	private static String eventsFile = "/Users/ihab/Documents/workspace/runs-svn/berlin_internalization_noise_averageVSmarginal/output/int_1_marginalCost/ITERS/it.100/100.events.xml.gz";
//	private static String netFile = "/Users/ihab/Documents/workspace/runs-svn/berlin_internalization_noise_averageVSmarginal/output/int_1_marginalCost/output_network.xml.gz";
//	private static String outputFile = "/Users/ihab/Documents/workspace/runs-svn/berlin_internalization_noise_averageVSmarginal/output/int_1_marginalCost/ITERS/it.100/link_demand.csv";
	
//	private static String eventsFile = "/Users/ihab/Documents/workspace/runs-svn/berlin_internalization_noise_averageVSmarginal/output/int_1_averageCost/ITERS/it.100/100.events.xml.gz";
//	private static String netFile = "/Users/ihab/Documents/workspace/runs-svn/berlin_internalization_noise_averageVSmarginal/output/int_1_averageCost/output_network.xml.gz";
//	private static String outputFile = "/Users/ihab/Documents/workspace/runs-svn/berlin_internalization_noise_averageVSmarginal/output/int_1_averageCost/ITERS/it.100/link_demand.csv";
	
	private static String eventsFile = "/Users/ihab/Documents/workspace/runs-svn/berlin_internalization_noise_averageVSmarginal/output/baseCase/ITERS/it.100/100.events.xml.gz";
	private static String netFile = "/Users/ihab/Documents/workspace/runs-svn/berlin_internalization_noise_averageVSmarginal/output/baseCase/output_network.xml.gz";
	private static String outputFile = "/Users/ihab/Documents/workspace/runs-svn/berlin_internalization_noise_averageVSmarginal/output/baseCase/ITERS/it.100/link_demand.csv";
	
	public static void main(String[] args) {
		LinkDemandAnalysisMain anaMain = new LinkDemandAnalysisMain();
		anaMain.run();
	}

	private void run() {
	
		Config config = ConfigUtils.createConfig();
		config.network().setInputFile(netFile);
		MutableScenario scenario = (MutableScenario) ScenarioUtils.loadScenario(config);
		EventsManager events = EventsUtils.createEventsManager();
				
		LinkDemandEventHandler handler = new LinkDemandEventHandler(scenario.getNetwork());
		events.addHandler(handler);
		
		MatsimEventsReader reader = new MatsimEventsReader(events);
		reader.readFile(eventsFile);
		
		handler.printResults(outputFile);
	}
			 
}
		
