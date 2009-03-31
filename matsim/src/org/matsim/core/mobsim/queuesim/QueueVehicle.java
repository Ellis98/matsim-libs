/* *********************************************************************** *
 * project: org.matsim.*
 * Vehicle.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2007 by the members listed in the COPYING,        *
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

package org.matsim.core.mobsim.queuesim;

import org.matsim.api.basic.v01.Id;
import org.matsim.api.basic.v01.Identifiable;
import org.matsim.core.api.network.Link;
import org.matsim.core.api.population.Leg;
import org.matsim.vis.netvis.DrawableAgentI;

public class QueueVehicle implements Identifiable, DrawableAgentI {

	private double currentDepartureTime = 0;
	private double lastMovedTime = 0;

	private DriverAgent driver = null;

	private final Id id;

	public QueueVehicle(final Id id) {
		this.id = id;
	}

	public double getEarliestLinkExitTime() {
		return this.currentDepartureTime;
	}

	public void setEarliestLinkExitTime(final double time) {
		this.currentDepartureTime = time;
	}

	/**
	 * @return Returns the currentLink.
	 */
	public Link getCurrentLink() {
		return this.driver.getCurrentLink();
	}

	public Leg getCurrentLeg() {
		return this.driver.getCurrentLeg();
	}

	/**
	 * @return Returns the driver.
	 */
	public DriverAgent getDriver() {
		return this.driver;
	}

	/**
	 * @param driver The driver to set.
	 */
	public void setDriver(final DriverAgent driver) {
		this.driver = driver;
	}

	/**
	 * @return Returns the Id of the vehicle.
	 */
	public Id getId() {
		return this.id;
	}

	/**
	 * @return Returns the time the vehicle moved last.
	 */
	public double getLastMovedTime() {
		return this.lastMovedTime;
	}

	/**
	 * @param lastMovedTime The lastMovedTime to set.
	 */
	public void setLastMovedTime(final double lastMovedTime) {
		this.lastMovedTime = lastMovedTime;
	}


	@Override
	public String toString() {
		return "Vehicle Id " + getId() + ", driven by (personId) " + this.driver.getPerson().getId()
				+ ", on link " + this.driver.getCurrentLink().getId();
	}



	public double getPosInLink_m() {

		double dur = this.driver.getCurrentLink().getFreespeedTravelTime(SimulationTimer.getTime());
		double mytime = getEarliestLinkExitTime() - SimulationTimer.getTime();
		if (mytime<0) {
			mytime = 0.;
		}
		mytime/= dur;
		mytime = (1.-mytime)*this.driver.getCurrentLink().getLength();
		return mytime;
	}

	/** @return Always returns the value 1. */
	public int getLane() {
		return 1;
	}

}
