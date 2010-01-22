/* *********************************************************************** *
 * project: org.matsim.*
 * ZoneLayer.java
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

package org.matsim.world;

import java.util.Map;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.core.facilities.ActivityFacilitiesImpl;
import org.matsim.core.gbl.Gbl;
import org.matsim.core.network.NetworkLayer;
import org.matsim.core.utils.geometry.CoordImpl;

/**
 * The collection of zone objects in MATSim.
 * @see LayerImpl
 * @see NetworkLayer
 * @see ActivityFacilitiesImpl
 * @author Michael Balmer
 */
public class ZoneLayer extends LayerImpl {

	//////////////////////////////////////////////////////////////////////
	// member variables
	//////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////
	// constructors
	//////////////////////////////////////////////////////////////////////

	protected ZoneLayer(final Id type, final String name) {
		super(type, name);
	}

	//////////////////////////////////////////////////////////////////////
	// create methods
	//////////////////////////////////////////////////////////////////////

	public final Zone createZone(final Id id, final String center_x, final String center_y,
	                             final String min_x, final String min_y, final String max_x, final String max_y,
	                             final String area, final String zoneName) {
		Id i = id;
		if (this.getLocations().containsKey(i)) { Gbl.errorMsg(this.toString() + "[zone id=" + id + " already exists]"); }
		Coord center = null;
		Coord min = null;
		Coord max = null;
		if ((center_x != null) && (center_y != null)) { center = new CoordImpl(center_x, center_y); }
		if ((min_x != null) && (min_y != null)) { min = new CoordImpl(min_x, min_y); }
		if ((max_x != null) && (max_y != null)) { max = new CoordImpl(max_x, max_y); }
		Zone z = new Zone(this, id, center, min, max, 0.0, zoneName);
		if (area != null) {
			z.setArea(Double.parseDouble(area));
		}
		Map<Id,MappedLocation> locations = (Map<Id, MappedLocation>) this.getLocations() ;
		locations.put(i,z);
		return z;
	}

	//////////////////////////////////////////////////////////////////////
	// query methods
	//////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////
	// print methods
	//////////////////////////////////////////////////////////////////////

	@Override
	public final String toString() {
		return super.toString();
	}
}
