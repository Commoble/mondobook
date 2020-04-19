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
	public final int bottom;
	public final int top;
	public final int left;
	public final int right;

	public SideSizes(Map<RawSideSizes, Integer> sizeMap)
	{
		this(
			sizeMap.get(RawSideSizes.ALL),
			sizeMap.get(RawSideSizes.BOTTOM),
			sizeMap.get(RawSideSizes.TOP),
			sizeMap.get(RawSideSizes.LEFT),
			sizeMap.get(RawSideSizes.RIGHT)
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
