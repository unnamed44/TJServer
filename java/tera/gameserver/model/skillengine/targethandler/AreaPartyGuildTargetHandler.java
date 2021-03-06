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
package tera.gameserver.model.skillengine.targethandler;

import tera.gameserver.model.Character;
import tera.gameserver.model.Guild;
import tera.gameserver.model.Party;

/**
 * @author Ronn
 */
public class AreaPartyGuildTargetHandler extends AreaTargetHandler
{
	/**
	 * Method checkTarget.
	 * @param caster Character
	 * @param target Character
	 * @return boolean
	 */
	@Override
	protected boolean checkTarget(Character caster, Character target)
	{
		final Character owner = target.getOwner();
		
		if (owner != null)
		{
			target = owner;
		}
		
		final Guild guild = caster.getGuild();
		
		if ((guild != null) && (target.getGuild() == guild))
		{
			return true;
		}
		
		final Party party = caster.getParty();
		
		if ((party != null) && (caster.getParty() == target.getParty()))
		{
			return true;
		}
		
		return false;
	}
}