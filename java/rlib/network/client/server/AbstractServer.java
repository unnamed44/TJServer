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
package rlib.network.client.server;

import java.nio.ByteBuffer;
import java.util.concurrent.locks.Lock;

import rlib.concurrent.Locks;
import rlib.logging.Logger;
import rlib.logging.Loggers;
import rlib.network.GameCrypt;
import rlib.network.packets.ReadeablePacket;
import rlib.network.packets.SendablePacket;

@SuppressWarnings("rawtypes")
public abstract class AbstractServer<C extends ServerConnection, T extends GameCrypt> implements Server<C>
{
	protected static final Logger log = Loggers.getLogger("Server");
	protected final Lock lock;
	protected final C connection;
	protected final T crypt;
	protected boolean closed;
	
	protected AbstractServer(C connection, T crypt)
	{
		this.connection = connection;
		this.lock = Locks.newLock();
		this.crypt = crypt;
	}
	
	@Override
	public void close()
	{
		C connection = this.getConnection();
		if (connection != null)
		{
			connection.close();
		}
	}
	
	@Override
	public void decrypt(ByteBuffer data, int offset, int length)
	{
		this.crypt.decrypt(data.array(), offset, length);
	}
	
	@Override
	public void encrypt(ByteBuffer data, int offset, int length)
	{
		this.crypt.encrypt(data.array(), offset, length);
	}
	
	protected abstract void executePacket(ReadeablePacket var1);
	
	@Override
	public C getConnection()
	{
		return this.connection;
	}
	
	@Override
	public boolean isConnected()
	{
		if (!this.closed && (this.connection != null) && !this.connection.isClosed())
		{
			return true;
		}
		return false;
	}
	
	@Override
	public final void lock()
	{
		this.lock.lock();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public final void readPacket(ReadeablePacket packet, ByteBuffer buffer)
	{
		if (packet != null)
		{
			packet.setBuffer(buffer);
			packet.setOwner(this);
			if (packet.read())
			{
				this.executePacket(packet);
			}
			packet.setBuffer(null);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public final void sendPacket(SendablePacket packet)
	{
		if (this.closed)
		{
			return;
		}
		this.lock.lock();
		try
		{
			C connection = this.getConnection();
			if (connection == null)
			{
				log.warning(this, new Exception("not found connection"));
				return;
			}
			connection.sendPacket(packet);
		}
		finally
		{
			this.lock.unlock();
		}
	}
	
	@Override
	public final void unlock()
	{
		this.lock.unlock();
	}
}
