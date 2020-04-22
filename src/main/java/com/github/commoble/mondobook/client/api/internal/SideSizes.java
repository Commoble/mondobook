package com.github.commoble.mondobook.client.api.internal;

import java.util.Map;

/**
 * Data class containing the margins in pixels around an Element's content.
 * Elements may optionally specify a margin per side, or for all sides.
 * If a side margin is specified, it takes precedence over "all margins".
 * If neither are specified, the margin will be 0 pixels.
 * Negative margins will be converted to 0 pixels.
 */
public class SideSizes
{
	public static final SideSizes NONE = new SideSizes(0,0,0,0);

	public final int bottom;
	public final int top;
	public final int left;
	public final int right;
	
	public SideSizes(Map<RawBoxSide, Integer> sizeMap)
	{
		this(
			sizeMap.get(RawBoxSide.ALL),
			sizeMap.get(RawBoxSide.BOTTOM),
			sizeMap.get(RawBoxSide.TOP),
			sizeMap.get(RawBoxSide.LEFT),
			sizeMap.get(RawBoxSide.RIGHT)
		);
	}
	
	public SideSizes(Integer all, Integer bottom, Integer top, Integer left, Integer right)
	{
		this(
			calculateSize(all, bottom),
			calculateSize(all, top),
			calculateSize(all, left),
			calculateSize(all, right)
		);
	}
	
	public SideSizes(int bottom, int top, int left, int right)
	{
		this.bottom = bottom;
		this.top = top;
		this.left = left;
		this.right = right;
	}
	
	public SideSizes add(SideSizes other)
	{
		return new SideSizes(
			this.bottom + other.bottom,
			this.top + other.top,
			this.left + other.left,
			this.right + other.right
		);
	}
	
	public SideSizes with(BoxSide side, int size)
	{
		switch(side)
		{
			case BOTTOM:
				return new SideSizes(size, this.top, this.left, this.right);
			case TOP:
				return new SideSizes(this.bottom, size, this.left, this.right);
			case LEFT:
				return new SideSizes(this.bottom, this.top, size, this.right);
			case RIGHT:
				return new SideSizes(this.bottom, this.top, this.left, size);
			default:
				return this;
		}
	}
	
	public SideSizes without(BoxSide side)
	{
		return this.with(side, 0);
	}
	
	public int getWidthOnSide(BoxSide side)
	{
		switch(side)
		{
			case BOTTOM:
				return this.bottom;
			case TOP:
				return this.top;
			case LEFT:
				return this.left;
			case RIGHT:
				return this.right;
			default:
				return 0;
		}
	}
	
	/** side has higher priority than all; if both are null, let margin = 0 **/
	private static int calculateSize(Integer all, Integer side)
	{
		if (side != null)
		{
			return Math.max(side, 0);
		}
		else if (all != null)
		{
			return Math.max(all, 0);
		}
		else
		{
			return 0;
		}
	}
}
