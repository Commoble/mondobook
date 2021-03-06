package com.github.commoble.mondobook.util;

import java.util.ArrayList;
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
	
	/**
	 * Applies one function to the first item in a list,
	 * another function to the rest of the list except for the last item,
	 * and a third function to the last item,
	 * 
	 * and returns a list of the mapped items
	 */
	public static <In, Out> List<Out> mapFirstMiddleLast(List<In> inputs, Function<In, Out> firstFunction, Function<In, Out> middleFunction, Function<In, Out> lastFunction)
	{
		List<Out> outputs = new ArrayList<>();
		int last = inputs.size()-1;
		ListUtil.forEachIndex(inputs, (input, i) ->{
			if (i == 0)
			{
				outputs.add(firstFunction.apply(input));
			}
			else if (i < last)
			{
				outputs.add(middleFunction.apply(input));
			}
			else
			{
				outputs.add(lastFunction.apply(input));
			}
		});
		return outputs;
	}
}
