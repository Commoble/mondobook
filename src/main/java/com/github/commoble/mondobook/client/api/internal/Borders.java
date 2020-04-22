package com.github.commoble.mondobook.client.api.internal;

public class Borders
{
	public static final Borders NONE = new Borders(SideSizes.NONE, 0);
	
	private SideSizes sizes;
	private int color;
	
	public Borders(SideSizes sizes, int color)
	{
		this.sizes = sizes;
		this.color = color;
	}
	
	public Borders withSideSize(BoxSide side, int size)
	{
		return new Borders(this.sizes.with(side, size), this.color);
	}
	
	public Borders without(BoxSide side)
	{
		return this.withSideSize(side, 0);
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
