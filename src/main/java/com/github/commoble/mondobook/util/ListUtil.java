package com.github.commoble.mondobook.util;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListUtil
{
	/** Runs the given index-aware consumer on each item in the list. Use list.foreach() instead if the items' indices are not needed. **/ 
	public static <T> void forEachIndex(List<T> list, BiConsumer<T, Integer> indexer)
	{
		int size = list.size();
		for (int i=0; i<size; i++)
		{
			indexer.accept(list.get(i), i);
		}
	}
	
	public static <In, Out> List<Out> map(List<In> list, Function<In, Out> mapper)
	{
		return list.stream().map(mapper).collect(Collectors.toList());
	}
}
