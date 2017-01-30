/*
 * This file is part of TJServer.
 * 
 * TJServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * TJServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package tera.gameserver.model.npc.spawn;

import tera.gameserver.model.npc.Npc;
import tera.util.Location;

/**
 * @author Ronn
 */
public interface Spawn
{
	/**
	 * Method doDie.
	 * @param npc Npc
	 */
	public void doDie(Npc npc);
	
	/**
	 * Method getLocation.
	 * @return Location
	 */
	public Location getLocation();
	
	/**
	 * Method getRoute.
	 * @return Location[]
	 */
	public Location[] getRoute();
	
	/**
	 * Method getTemplateId.
	 * @return int
	 */
	public int getTemplateId();
	
	/**
	 * Method getTemplateType.
	 * @return int
	 */
	public int getTemplateType();
	
	/**
	 * Method setLocation.
	 * @param location Location
	 */
	public void setLocation(Location location);
	
	public void start();
	
	public void stop();
}
