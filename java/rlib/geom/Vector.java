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
package rlib.geom;

import rlib.util.ExtMath;

public final class Vector implements GamePoint
{
	public static final Vector ZERO = new Vector(0.0f, 0.0f, 0.0f);
	public static final Vector NAN = new Vector(Float.NaN, Float.NaN, Float.NaN);
	public static final Vector UNIT_X = new Vector(1.0f, 0.0f, 0.0f);
	public static final Vector UNIT_Y = new Vector(0.0f, 1.0f, 0.0f);
	public static final Vector UNIT_Z = new Vector(0.0f, 0.0f, 1.0f);
	public static final Vector UNIT_XYZ = new Vector(1.0f, 1.0f, 1.0f);
	public static final Vector POSITIVE_INFINITY = new Vector(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
	public static final Vector NEGATIVE_INFINITY = new Vector(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
	protected float x;
	protected float y;
	protected float z;
	
	public static Vector newInstance()
	{
		return new Vector();
	}
	
	public static Vector newInstance(float x, float y, float z)
	{
		return new Vector(x, y, z);
	}
	
	public static Vector newInstance(float[] vals)
	{
		return new Vector(vals[0], vals[1], vals[2]);
	}
	
	private Vector()
	{
	}
	
	private Vector(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector addLocal(float addX, float addY, float addZ)
	{
		this.x += addX;
		this.y += addY;
		this.z += addZ;
		return this;
	}
	
	public Vector addLocal(Vector vec)
	{
		return this.addLocal(vec.x, vec.y, vec.z);
	}
	
	public Vector cross(float otherX, float otherY, float otherZ, Vector result)
	{
		float resX = (this.y * otherZ) - (this.z * otherY);
		float resY = (this.z * otherX) - (this.x * otherZ);
		float resZ = (this.x * otherY) - (this.y * otherX);
		result.setXYZ(resX, resY, resZ);
		return result;
	}
	
	public Vector cross(Vector vector)
	{
		return this.cross(vector, Vector.newInstance());
	}
	
	public Vector cross(Vector vector, Vector result)
	{
		return this.cross(vector.x, vector.y, vector.z, result);
	}
	
	public float distance(Vector vector)
	{
		return ExtMath.sqrt(this.distanceSquared(vector));
	}
	
	public float distanceSquared(float targetX, float targetY, float targetZ)
	{
		float dx = this.x - targetX;
		float dy = this.y - targetY;
		float dz = this.z - targetZ;
		return (dx * dx) + (dy * dy) + (dz * dz);
	}
	
	public float distanceSquared(Vector vector)
	{
		return this.distanceSquared(vector.x, vector.y, vector.z);
	}
	
	public float dot(Vector vector)
	{
		return (this.x * vector.x) + (this.y * vector.y) + (this.z * vector.z);
	}
	
	@Override
	public int getHeading()
	{
		return 0;
	}
	
	@Override
	public float getX()
	{
		return this.x;
	}
	
	@Override
	public float getY()
	{
		return this.y;
	}
	
	@Override
	public float getZ()
	{
		return this.z;
	}
	
	public boolean isZero()
	{
		if ((this.x == 0.0f) && (this.y == 0.0f) && (this.z == 0.0f))
		{
			return true;
		}
		return false;
	}
	
	public Vector multLocal(float scalar)
	{
		return this.multLocal(scalar, scalar, scalar);
	}
	
	public Vector multLocal(float x, float y, float z)
	{
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}
	
	public Vector multLocal(Vector vec)
	{
		return this.multLocal(vec.x, vec.y, vec.z);
	}
	
	public Vector set(Vector vector)
	{
		return this.setXYZ(vector.x, vector.y, vector.z);
	}
	
	@Override
	public GamePoint setHeading(int heading)
	{
		return this;
	}
	
	@Override
	public Vector setX(float x)
	{
		this.x = x;
		return this;
	}
	
	@Override
	public Vector setXYZ(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	@Override
	public GamePoint setXYZH(float x, float y, float z, int heading)
	{
		return this;
	}
	
	@Override
	public Vector setY(float y)
	{
		this.y = y;
		return this;
	}
	
	@Override
	public Vector setZ(float z)
	{
		this.z = z;
		return this;
	}
	
	public Vector subtract(Vector vector, Vector result)
	{
		result.x = this.x - vector.x;
		result.y = this.y - vector.y;
		result.z = this.z - vector.z;
		return result;
	}
	
	public Vector subtractLocal(float subX, float subY, float subZ)
	{
		this.x -= subX;
		this.y -= subY;
		this.z -= subZ;
		return this;
	}
	
	public Vector subtractLocal(Vector vector)
	{
		return this.subtractLocal(vector.x, vector.y, vector.z);
	}
	
	public static boolean isValidVector(Vector vector)
	{
		if (vector == null)
		{
			return false;
		}
		if (Float.isNaN(vector.getX()) || Float.isNaN(vector.getY()) || Float.isNaN(vector.getZ()))
		{
			return false;
		}
		if (Float.isInfinite(vector.getX()) || Float.isInfinite(vector.getY()) || Float.isInfinite(vector.getZ()))
		{
			return false;
		}
		return true;
	}
	
	@Override
	public String toString()
	{
		return "Vector [x=" + this.x + ", y=" + this.y + ", z=" + this.z + "]";
	}
}
