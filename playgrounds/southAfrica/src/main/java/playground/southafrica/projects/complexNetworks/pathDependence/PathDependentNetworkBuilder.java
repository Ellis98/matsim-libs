/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2013 by the members listed in the COPYING,     *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

/**
 * 
 */
package playground.southafrica.projects.complexNetworks.pathDependence;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.matsim.core.gbl.MatsimRandom;

import playground.southafrica.freight.digicore.containers.DigicoreVehicle;
import playground.southafrica.freight.digicore.utils.DigicoreChainCleaner;
import playground.southafrica.projects.complexNetworks.pathDependence.PathDependentNetwork.PathDependentNode;
import playground.southafrica.utilities.FileUtils;
import playground.southafrica.utilities.Header;

/** Class to build a path dependent complex network by reading all 
 *  the clean {@link DigicoreVehicle}s generated by executing the class 
 *  {@link DigicoreChainCleaner}. When building the {@link PathDependentNetwork},
 *  the next {@link PathDependentNode} is determined by taking into 
 *  account the previous {@link PathDependentNode}s connected 
 *  to the current {@link PathDependentNode}.
 * 
 * @author jwjoubert
 */
public class PathDependentNetworkBuilder {
	private final static Logger LOG = Logger.getLogger(PathDependentNetworkBuilder.class);
	private PathDependentNetwork network;

	/**
	 * @param path to the "clean/" folder containing clean vehicle xml files generated by 
	 * 		  executing the class {@link DigicoreChainCleaner}
	 * @param path to the pathDependentNetwork.xml.gz file
	 * @param description of the network, specifying the area that the network
	 * 	      covers (National/Gauteng/etc), and the clustering parameter
	 * 		  configuration (pmin=..., radius=...).
	 */
	public static void main(String[] args) {
		Header.printHeader(PathDependentNetworkBuilder.class.toString(), args);
		
		String vehicleFolder = args[0];
		String outputFile = args[1];
		
		
		PathDependentNetworkBuilder builder = new PathDependentNetworkBuilder(MatsimRandom.getRandom().nextLong());
		builder.setNetworkDescription(args[2]);
		
		List<File> vehicleFiles = FileUtils.sampleFiles(new File(vehicleFolder), Integer.MAX_VALUE, FileUtils.getFileFilter(".xml.gz"));
		builder.network.buildNetwork(vehicleFiles);
		
		new DigicorePathDependentNetworkWriter(builder.network).write(outputFile);
		
		Header.printFooter();
	}
	
	public PathDependentNetworkBuilder(long seed) {
		this.network = new PathDependentNetwork(seed);
	}
	
	public void setNetworkDescription(String string){
		this.network.setDescription(string);
	}
	

	


}