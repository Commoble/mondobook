package com.github.commoble.mondobook.util;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ListUtilTests
{

	@Test
	void mapFirstMiddleLastTest()
	{
		// given these inputs
		List<Integer> inputList = Arrays.asList(10, 20, 30, 40, 50);
		List<Integer> expectedOutputs = Arrays.asList(-10, 40, 60, 80, 500);
		
		// when we multiply the first input by -1, the middle inputs by 2, and the last input by 10
		List<Integer> actualOutputs = ListUtil.mapFirstMiddleLast(inputList, i -> i*-1, i -> i*2, i -> i*10);
		
		// then they should match the expected outputs above
		Assertions.assertArrayEquals(expectedOutputs.toArray(), actualOutputs.toArray());
	}
}
