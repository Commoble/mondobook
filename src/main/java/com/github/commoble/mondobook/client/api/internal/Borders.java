package com.github.commoble.mondobook.client.api.internal;

public class Borders
{
	private SideSizes sizes;
	private int color;
	
	public Borders(SideSizes sizes, int color)
	{
		this.sizes = sizes;
		this.color = color;
	}
	
	public SideSizes getSizes()
	{
		return this.sizes;
	}
	
	public int getColor()
	{
		return this.color;
	}
}
