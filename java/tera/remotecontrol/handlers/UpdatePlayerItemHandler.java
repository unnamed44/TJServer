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
package tera.remotecontrol.handlers;

import tera.gameserver.manager.ObjectEventManager;
import tera.gameserver.model.World;
import tera.gameserver.model.equipment.Equipment;
import tera.gameserver.model.equipment.Slot;
import tera.gameserver.model.inventory.Inventory;
import tera.gameserver.model.items.ItemInstance;
import tera.gameserver.model.playable.Player;
import tera.remotecontrol.Packet;
import tera.remotecontrol.PacketHandler;
import tera.remotecontrol.PacketType;

import rlib.concurrent.Locks;

/**
 * @author Ronn
 * @created 09.04.2012
 */
public class UpdatePlayerItemHandler implements PacketHandler
{
	public static final UpdatePlayerItemHandler instance = new UpdatePlayerItemHandler();
	
	/**
	 * Method processing.
	 * @param packet Packet
	 * @return Packet
	 * @see tera.remotecontrol.PacketHandler#processing(Packet)
	 */
	@Override
	public Packet processing(Packet packet)
	{
		final Player player = World.getPlayer(packet.nextString());
		
		if (player == null)
		{
			return null;
		}
		
		final int objectId = packet.nextInt();
		final long newCount = packet.nextLong();
		final Inventory inventory = player.getInventory();
		final Equipment equipment = player.getEquipment();
		
		if ((inventory == null) || (equipment == null))
		{
			return null;
		}
		
		Locks.lock(inventory, equipment);
		
		try
		{
			ItemInstance item = inventory.getItemForObjectId(objectId);
			
			if (item == null)
			{
				final Slot slot = equipment.getSlotForObjectId(objectId);
				
				if (slot != null)
				{
					item = slot.getItem();
				}
			}
			
			if ((item == null) || !item.isStackable())
			{
				return null;
			}
			
			item.setItemCount(newCount);
			final ObjectEventManager eventManager = ObjectEventManager.getInstance();
			eventManager.notifyInventoryChanged(player);
			return new Packet(PacketType.RESPONSE);
		}
		
		finally
		{
			Locks.unlock(inventory, equipment);
		}
	}
}
