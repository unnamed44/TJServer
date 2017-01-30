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
package tera.gameserver.model.npc.interaction.replyes;

import org.w3c.dom.Node;

import tera.gameserver.model.npc.Npc;
import tera.gameserver.model.npc.interaction.Link;
import tera.gameserver.model.npc.interaction.dialogs.CreateGuildDialog;
import tera.gameserver.model.npc.interaction.dialogs.Dialog;
import tera.gameserver.model.playable.Player;

import rlib.util.VarTable;

/**
 * @author Ronn
 */
public final class ReplyCreateGuild extends AbstractReply
{
	private final int price;
	private final int minLevel;
	
	/**
	 * Constructor for ReplyCreateGuild.
	 * @param node Node
	 */
	public ReplyCreateGuild(Node node)
	{
		super(node);
		final VarTable vars = VarTable.newInstance(node);
		price = vars.getInteger("price");
		minLevel = vars.getInteger("minLevel");
	}
	
	/**
	 * Method reply.
	 * @param npc Npc
	 * @param player Player
	 * @param link Link
	 * @see tera.gameserver.model.npc.interaction.replyes.Reply#reply(Npc, Player, Link)
	 */
	@Override
	public void reply(Npc npc, Player player, Link link)
	{
		if (player.getLevel() < minLevel)
		{
			player.sendMessage("No suitable level.");
			return;
		}
		
		final Dialog dialog = CreateGuildDialog.newInstance(npc, player, price, minLevel);
		
		if (!dialog.init())
		{
			dialog.close();
		}
	}
}