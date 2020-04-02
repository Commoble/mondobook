package com.github.commoble.mondobook.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MapUtil
{
	/** Converts all the values in a map to new values; the new map uses the same keys as the old map **/
	public static <Key, In, Out> Map<Key, Out> mapValues(Map<Key,In> inputs, Function<In, Out> mapper)
	{
		Map<Key,Out> newMap = new HashMap<>();
		
		inputs.forEach((key, input) -> newMap.put(key, mapper.apply(input)));
		
		return newMap;
	}
}
