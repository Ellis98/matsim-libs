/* *********************************************************************** *
 * project: org.matsim.*
 * PersonCreatePlanFromKnowledge.java
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

package playground.balmermi.census2000v2.modules;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.matsim.basic.v01.Id;
import org.matsim.plans.Act;
import org.matsim.plans.Person;
import org.matsim.plans.Plan;
import org.matsim.plans.Plans;
import org.matsim.utils.geometry.CoordI;

import playground.balmermi.census2000v2.data.CAtts;
import playground.balmermi.census2000v2.data.Household;


public class PlansScenarioCut {

	//////////////////////////////////////////////////////////////////////
	// member variables
	//////////////////////////////////////////////////////////////////////

	private final static Logger log = Logger.getLogger(PlansScenarioCut.class);

	private final CoordI min;
	private final CoordI max;
	
	//////////////////////////////////////////////////////////////////////
	// constructors
	//////////////////////////////////////////////////////////////////////

	public PlansScenarioCut(final CoordI min, final CoordI max) {
		log.info("    init " + this.getClass().getName() + " module...");
		this.min = min;
		this.max = max;
		log.info("      cut area: min="+this.min+", max="+this.max);
		log.info("    done.");
	}

	//////////////////////////////////////////////////////////////////////
	// private methods
	//////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////
	// run methods
	//////////////////////////////////////////////////////////////////////
	
	public final void run(Plans plans) {
		log.info("    running " + this.getClass().getName() + " module...");
		Set<Id> removeids = new TreeSet<Id>();
		Map<Id,Person> persons = plans.getPersons();
		for (Person p : persons.values()) {
			Plan plan = p.getSelectedPlan();
			for (int i=0; i<plan.getActsLegs().size(); i=i+2) {
				Act act = (Act)plan.getActsLegs().get(i);
				CoordI c = act.getCoord();
				if ((c.getX()<min.getX()) || (c.getX()>max.getX()) || (c.getY()<min.getY()) || (c.getY()>max.getY())) {
					removeids.add(p.getId());
				}
			}
		}
		log.info("      # persons: " + persons.size());
		log.info("      # persons to remove: " + removeids.size());
		for (Id pid : removeids) {
			Person p = persons.get(pid);
			Household hh_w = (Household)p.getCustomAttributes().get(CAtts.HH_W);
			if (hh_w != null) { hh_w.getPersonsW().remove(pid); }
			Household hh_z = (Household)p.getCustomAttributes().get(CAtts.HH_Z);
			if (hh_z != null) { hh_z.getPersonsZ().remove(pid); }
			persons.remove(pid);
		}
		log.info("      # persons left: " + persons.size());
		log.info("    done.");
	}
}
