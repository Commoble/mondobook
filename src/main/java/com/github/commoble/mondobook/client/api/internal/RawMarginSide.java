package com.github.commoble.mondobook.client.api.internal;

import java.util.Map;

public enum RawMarginSide
{
	ALL,
	BOTTOM,
	TOP,
	LEFT,
	RIGHT;
	
	public void merge(Map<RawMarginSide, Integer> map, Integer value)
	{
		if (value != null)
		{
			map.put(this, value);
		}
	}
	
	public static void mergeAll(Map<RawMarginSide, Integer> map, Integer all, Integer bottom, Integer top, Integer left, Integer right)
	{
		ALL.merge(map, all);
		BOTTOM.merge(map, bottom);
		TOP.merge(map, top);
		LEFT.merge(map, left);
		RIGHT.merge(map, right);
	}
}
