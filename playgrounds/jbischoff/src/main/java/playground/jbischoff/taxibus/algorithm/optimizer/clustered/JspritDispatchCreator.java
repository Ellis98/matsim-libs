/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2016 by the members listed in the COPYING,        *
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
package playground.jbischoff.taxibus.algorithm.optimizer.clustered;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.contrib.dvrp.data.Request;
import org.matsim.contrib.dvrp.data.Vehicle;
import org.matsim.contrib.dvrp.path.VrpPathWithTravelData;
import org.matsim.contrib.dvrp.path.VrpPaths;
import org.matsim.contrib.util.distance.DistanceUtils;
import org.matsim.core.router.Dijkstra;

import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.algorithm.box.SchrimpfFactory;
import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem.FleetSize;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.job.Shipment;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.solution.route.VehicleRoute;
import com.graphhopper.jsprit.core.problem.solution.route.activity.TourActivity;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleType;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.util.Coordinate;
import com.graphhopper.jsprit.core.util.Solutions;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl.Builder;
import playground.jbischoff.taxibus.algorithm.passenger.TaxibusRequest;
import playground.jbischoff.taxibus.algorithm.scheduler.vehreqpath.TaxibusDispatch;
import playground.jbischoff.utils.JbUtils;

/**
 * @author  jbischoff
 *
 */
/**
 *
 */
public class JspritDispatchCreator implements RequestDispatcher {
	private final ClusteringTaxibusOptimizerContext context;
	private final Dijkstra router;

	/**
	 * 
	 */
	public JspritDispatchCreator(ClusteringTaxibusOptimizerContext context) {
		this.context = context;
		this.router = new Dijkstra(context.scenario.getNetwork(), context.travelDisutility, context.travelTime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see playground.jbischoff.taxibus.algorithm.optimizer.clustered.
	 * RequestDispatcher#createDispatch(java.util.Set)
	 */
	@Override
	public TaxibusDispatch createDispatch(Set<TaxibusRequest> commonRequests) {
		Coord requestCentroid = calcRequestCentroid(commonRequests);
		Vehicle veh = findClosestIdleVehicle(requestCentroid);
		if (veh != null) {
			return createDispatchForVehicle(commonRequests, veh);
		} else
			return null;
	}

	/**
	 * @param commonRequests
	 * @param veh
	 * @return
	 */
	private TaxibusDispatch createDispatchForVehicle(Set<TaxibusRequest> commonRequests, Vehicle veh) {
		VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
		vrpBuilder.setFleetSize(FleetSize.FINITE);
		Map<String, TaxibusRequest> requests = new HashMap<>();
		VehicleTypeImpl.Builder vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance("vehicleType")
				.addCapacityDimension(0, commonRequests.size());
		VehicleType vehicleType = vehicleTypeBuilder.build();
		Coord startCoord = veh.getStartLink().getCoord();
		String vId = veh.getId().toString();
		Builder vehicleBuilder = VehicleImpl.Builder.newInstance(vId);
		vehicleBuilder.setStartLocation(Location.newInstance(startCoord.getX(), startCoord.getY()));
		vehicleBuilder.setType(vehicleType);
		VehicleImpl vehicle = vehicleBuilder.build();
		vrpBuilder.addVehicle(vehicle);

		for (TaxibusRequest req : commonRequests) {
			String rId = req.getId().toString();
			requests.put(rId, req);
			Location fromLoc = Location.Builder.newInstance()
					.setId(req.getFromLink().getId().toString()).setCoordinate(Coordinate
							.newInstance(req.getFromLink().getCoord().getX(), req.getFromLink().getCoord().getY()))
					.build();
			Location toLoc = Location.Builder.newInstance().setId(req.getToLink().getId().toString()).setCoordinate(
					Coordinate.newInstance(req.getToLink().getCoord().getX(), req.getToLink().getCoord().getY()))
					.build();
			Shipment shipment = Shipment.Builder.newInstance(rId).addSizeDimension(0, 1).setPickupLocation(fromLoc)
					.setDeliveryLocation(toLoc).build();
			vrpBuilder.addJob(shipment);
		}
		VehicleRoutingProblem problem = vrpBuilder.build();
		VehicleRoutingAlgorithm algorithm = new SchrimpfFactory().createAlgorithm(problem);
		Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();
		VehicleRoutingProblemSolution bestSolution = Solutions.bestOf(solutions);
		if (bestSolution.getRoutes().size() > 1) {
			Logger.getLogger(getClass()).error("Solution has more than one vehicle on dispatch???");
			throw new RuntimeException();
		}
		VehicleRoute vr = (VehicleRoute) bestSolution.getRoutes().toArray()[0];
		Iterator<TourActivity> it = vr.getActivities().iterator();
		TourActivity lastAct = it.next();
		Link lastDestination = context.scenario.getNetwork().getLinks()
				.get(Id.createLinkId(lastAct.getLocation().getId()));
		VrpPathWithTravelData path = VrpPaths.calcAndCreatePath(veh.getStartLink(), lastDestination,
				context.timer.getTimeOfDay(), router, context.travelTime);
		TaxibusDispatch dispatch = new TaxibusDispatch(veh, path);
		double d = context.timer.getTimeOfDay();
		while (it.hasNext()) {
			d += 60;
			TourActivity current = it.next();
			Link currentDestination = context.scenario.getNetwork().getLinks()
					.get(Id.createLinkId(current.getLocation().getId()));
			dispatch.addPath(
					VrpPaths.calcAndCreatePath(lastDestination, currentDestination, d, router, context.travelTime));
			lastAct = current;
			lastDestination = currentDestination;
		}
		dispatch.addPath(VrpPaths.calcAndCreatePath(lastDestination, veh.getStartLink(), d+60, router, context.travelTime));

		dispatch.addRequests(commonRequests);

		return dispatch;
	}

	/**
	 * @param coord
	 * @return
	 */
	private Vehicle findClosestIdleVehicle(Coord coord) {
		double bestDistance = Double.MAX_VALUE;
		Vehicle bestVehicle = null;
		for (Vehicle veh : this.context.vrpData.getVehicles().values()) {
			if (context.scheduler.isIdle(veh)) {
				double distance = DistanceUtils.calculateSquaredDistance(veh.getStartLink().getCoord(), coord);
				if (distance < bestDistance) {
					bestDistance = distance;
					bestVehicle = veh;
				}

			}

		}

		return bestVehicle;
	}

	/**
	 * @param commonRequests
	 * @return
	 */
	private Coord calcRequestCentroid(Set<TaxibusRequest> commonRequests) {
		Set<Coord> coords = new HashSet<>();
		for (TaxibusRequest r : commonRequests) {
			coords.add(r.getFromLink().getCoord());
		}
		return JbUtils.getCoordCentroid(coords);
	}

}
