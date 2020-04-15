package com.github.commoble.mondobook.client.book;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.commoble.mondobook.client.api.internal.Margins;

public class MarginsTests
{
	/** The "expected" function is of the format (side, all) -> return expected value **/
	public static List<Pair<Integer, Margins>> setupMarginCases(Integer[] sides, Integer[] alls, BiFunction<Integer, Integer, Integer> expected)
	{
		List<Pair<Integer, Margins>> cases = new ArrayList<>();
		for (Integer side : sides)
		{
			for (Integer all : alls)
			{
				cases.add(Pair.of(expected.apply(side,all), new Margins(all, side, side, side, side)));
			}
		}
		return cases;
	}
	
	public static void testCases(List<Pair<Integer, Margins>> cases)
	{
		cases.stream()
		.forEach(pair -> {
			Integer expected = pair.getLeft();
			Margins margins = pair.getRight();
			Assertions.assertEquals(expected, margins.top);
			Assertions.assertEquals(expected, margins.bottom);
			Assertions.assertEquals(expected, margins.left);
			Assertions.assertEquals(expected, margins.right);
		});
	}
	
	@Test
	void should_ReturnSide_When_SideIsNotNegativeAndNotNull()
	{
		// given
		Integer[] sides = {0, 1, Integer.MAX_VALUE};
		Integer[] alls = {null, 0, 1, -1, Integer.MAX_VALUE, Integer.MIN_VALUE};
		
		// when
		List<Pair<Integer, Margins>> cases = setupMarginCases(sides, alls, (side,all) -> side);
			
		// then
		testCases(cases);
	}
	
	@Test
	void should_ReturnZero_When_SideIsNegative()
	{
		// given
		Integer[] sides = {-1, Integer.MIN_VALUE};
		Integer[] alls = {null, 0, 1, -1, Integer.MAX_VALUE, Integer.MIN_VALUE};

		// when
		List<Pair<Integer, Margins>> cases = setupMarginCases(sides, alls, (side,all) -> 0);

		// then
		testCases(cases);
	}
	
	@Test
	void should_ReturnAll_When_SideIsNullAndAllIsNotNegativeAndNotNull()
	{
		// given
		Integer[] sides = {null};
		Integer[] alls = {0, 1, Integer.MAX_VALUE};

		// when
		List<Pair<Integer, Margins>> cases = setupMarginCases(sides, alls, (side,all) -> all);

		// then
		testCases(cases);

	}
	
	@Test
	void should_ReturnZero_When_SideIsNullAndAllIsNegative()
	{
		// given
		Integer[] sides = {null};
		Integer[] alls = {-1, Integer.MIN_VALUE};

		// when
		List<Pair<Integer, Margins>> cases = setupMarginCases(sides, alls, (side,all) -> 0);

		// then
		testCases(cases);

	}
	
	@Test
	void should_ReturnZero_When_SideIsNullAndAllIsNull()
	{
		// given
		Integer[] sides = {null};
		Integer[] alls = {null};

		// when
		List<Pair<Integer, Margins>> cases = setupMarginCases(sides, alls, (side,all) -> 0);

		// then
		testCases(cases);

	}
}
