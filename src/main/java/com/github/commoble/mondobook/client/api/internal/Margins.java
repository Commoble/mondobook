package com.github.commoble.mondobook.client.api.internal;

/**
 * Data class containing the margins in pixels around an Element's content.
 * Elements may optionally specify a margin per side, or for all sides.
 * If a side margin is specified, it takes precedence over "all margins".
 * If neither are specified, the margin will be 0 pixels.
 * Negative margins will be converted to 0 pixels.
 */
public class Margins
{
	public final int bottom;
	public final int top;
	public final int left;
	public final int right;
	
	public Margins(Integer all, Integer bottom, Integer top, Integer left, Integer right)
	{
		this.bottom = calculateMargin(all, bottom);
		this.top = calculateMargin(all, top);
		this.left = calculateMargin(all, left);
		this.right = calculateMargin(all, right);
	}
	
	public int getMarginOnSide(MarginSide side)
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
	private static int calculateMargin(Integer all, Integer side)
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
