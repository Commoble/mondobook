package com.github.commoble.mondobook;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.commoble.mondobook.client.api.internal.TabDataManager;
import com.google.common.collect.Sets;

import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class TabDataManagerTests
{
	@Test
	void assembleJsonTest()
	{
		// these json string should result in two books in the misc tab and two books in the redstone tab
		String rawA = "{\r\n" + 
			"	\"misc\":\r\n" + 
			"	[\r\n" + 
			"		\"mondobook:debug\"\r\n" + 
			"	]\r\n" + 
			"}";
		String rawB = "{\r\n" + 
			"	\"misc\":\r\n" + 
			"	[\r\n" + 
			"		\"mondobook:lorem_ipsum\"\r\n" + 
			"	]\r\n" + 
			"}";
		String rawC = "{\r\n" + 
			"	\"redstone\":\r\n" + 
			"	[\r\n" + 
			"		\"mondobook:lorem_ipsum\",\r\n" + 
			"		\"mondobook:debug\"\r\n" + 
			"	]\r\n" + 
			"}";
		String[] raws = {rawA, rawB, rawC};
		Function<String, Map<String, Set<String>>> mapper = json -> TabDataManager.GSON.fromJson(json, TabDataManager.DATA_TYPE);
		Map<ItemGroup, Set<ResourceLocation>> actualMap = TabDataManager.bakeMap(Arrays.stream(raws)
			.map(mapper)
			.reduce(new HashMap<>(), (baseMap, newMap) -> TabDataManager.mergeData(baseMap, newMap)));
		
		Map<ItemGroup, Set<ResourceLocation>> expectedMap = new HashMap<>();
		ResourceLocation[] setA = {new ResourceLocation("mondobook:debug"), new ResourceLocation("mondobook:lorem_ipsum")};
		expectedMap.put(ItemGroup.MISC, Sets.newHashSet(setA));
		expectedMap.put(ItemGroup.REDSTONE, Sets.newHashSet(setA));
		
		Assertions.assertEquals(expectedMap, actualMap);
	}
}
