package com.github.commoble.mondobook.util;

public class MathUtil
{
	public static boolean isWithin(int x, int y, int xStart, int yStart, int xWidth, int yHeight)
	{
		return
			x >= xStart &&
			x < xStart + xWidth &&
			y >= yStart &&
			y < yStart + yHeight;
	}
}
