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
package tera.gameserver.network.serverpackets;

import java.nio.ByteBuffer;

import tera.gameserver.model.Character;
import tera.gameserver.model.items.ItemInstance;
import tera.gameserver.network.ServerPacketType;

/**
 * @author Ronn
 */
public class CharPickUpItem extends ServerPacket
{
	private static final ServerPacket instance = new CharPickUpItem();
	
	/**
	 * Method getInstance.
	 * @param character Character
	 * @param item ItemInstance
	 * @return CharPickUpItem
	 */
	public static CharPickUpItem getInstance(Character character, ItemInstance item)
	{
		final CharPickUpItem packet = (CharPickUpItem) instance.newInstance();
		packet.charId = character.getObjectId();
		packet.charSubId = character.getSubId();
		packet.itemId = item.getObjectId();
		packet.itemSubId = character.getSubId();
		packet.itemCount = (int) item.getItemCount();
		return packet;
	}
	
	private int charId;
	private int charSubId;
	private int itemId;
	private int itemSubId;
	private int itemCount;
	
	/**
	 * Method getPacketType.
	 * @return ServerPacketType
	 */
	@Override
	public ServerPacketType getPacketType()
	{
		return ServerPacketType.CHAR_PICK_UP_ITEM;
	}
	
	/**
	 * Method isSynchronized.
	 * @return boolean
	 * @see rlib.network.packets.SendablePacket#isSynchronized()
	 */
	@Override
	public boolean isSynchronized()
	{
		return false;
	}
	
	/**
	 * Method writeImpl.
	 * @param buffer ByteBuffer
	 */
	@Override
	protected void writeImpl(ByteBuffer buffer)
	{
		writeOpcode(buffer);
		writeInt(buffer, charId);
		writeInt(buffer, charSubId);
		writeInt(buffer, itemId);
		writeInt(buffer, itemSubId);
		writeByte(buffer, itemCount);
	}
}