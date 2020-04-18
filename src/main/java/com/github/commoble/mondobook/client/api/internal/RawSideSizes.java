package com.github.commoble.mondobook.client.api.internal;

import java.util.Map;

public enum RawSideSizes
{
	ALL,
	BOTTOM,
	TOP,
	LEFT,
	RIGHT;
	
	public void merge(Map<RawSideSizes, Integer> map, Integer value)
	{
		if (value != null)
		{
			map.put(this, value);
		}
	}
	
	public static void mergeAll(Map<RawSideSizes, Integer> map, Integer all, Integer bottom, Integer top, Integer left, Integer right)
	{
		ALL.merge(map, all);
		BOTTOM.merge(map, bottom);
		TOP.merge(map, top);
		LEFT.merge(map, left);
		RIGHT.merge(map, right);
	}
}
