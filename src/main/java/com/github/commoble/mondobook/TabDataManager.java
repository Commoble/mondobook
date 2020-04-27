package com.github.commoble.mondobook;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.item.ItemGroup;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class TabDataManager extends JsonReloadListener
{
	private static Type dataType = new TypeToken<Map<String, Set<String>>>(){}.getType();
	private static final Gson GSON = new Gson();
	
	public Map<ItemGroup, Set<ResourceLocation>> map;
	
	public Set<ResourceLocation> getBooksForItemGroup(ItemGroup group)
	{
		return this.map.get(group);
	}
	
	public TabDataManager()
	{
		super(GSON, "mondobooktabs");
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonObject> jsons, IResourceManager resourceManager, IProfiler profiler)
	{
		this.map = bakeMap(jsons.values().stream()
			.map(TabDataManager::getJsonAsData)
			.reduce(new HashMap<>(), TabDataManager::mergeData));
	}

	/** Use a json object (presumably one from an assets/modid/mondobooks folder) to generate a data object **/
	public static Map<String, Set<String>> getJsonAsData(JsonObject json)
	{
		return GSON.fromJson(json, dataType);
	}
	
	private static Map<String, Set<String>> mergeData(Map<String, Set<String>> baseMap, Map<String, Set<String>> newMap)
	{
		newMap.entrySet().forEach(entry -> baseMap.merge(entry.getKey(), entry.getValue(), Sets::union));
		return baseMap;
	}
	
	private static Map<ItemGroup, Set<ResourceLocation>> bakeMap(Map<String, Set<String>> raw)
	{
		Map<String, ItemGroup> groupsByName = Arrays.stream(ItemGroup.GROUPS)
			.collect(Collectors.toMap(group -> group.getPath(), group -> group));
		
		Set<ItemGroup> groups = raw.keySet().stream()
			.map(key -> groupsByName.get(key))
			.collect(Collectors.toSet());
		
		return Maps.asMap(groups, group ->
			raw.getOrDefault(group, ImmutableSet.of()).stream()
				.map(string -> new ResourceLocation(string))
				.collect(Collectors.toSet()));
	}
}
