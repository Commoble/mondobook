package com.github.commoble.mondobook.client.util;

public class Point
{
	private final int x;
	private final int y;
	
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	@Override
	public int hashCode()
	{
		return this.x * 65535 + this.y;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (other instanceof Point)
		{
			Point point = (Point)other;
			return this.x == point.x && this.y == point.y;
		}
		else
		{
			return false;
		}
	}
}
