package com.github.commoble.mondobook.client.api.internal;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.commoble.mondobook.MondobookMod;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.client.util.IMutableSearchTree;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class TabDataManager extends JsonReloadListener
{
	public static final Type DATA_TYPE = new TypeToken<Map<String, Set<String>>>(){}.getType();
	public static final Gson GSON = new Gson();
	
	public Map<ItemGroup, Set<ResourceLocation>> map = new HashMap<>();
	
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
		MondobookMod.LOGGER.info(String.format("Loaded mondobook creative tab data from %s files", jsons.size()));
		
		IMutableSearchTree<ItemStack> tree = Minecraft.getInstance().getSearchTreeManager().get(SearchTreeManager.ITEMS);
		tree.clear();

		// from Minecraft::populateSearchTreeManager
		NonNullList<ItemStack> nonnulllist = NonNullList.create();
		for (Item item : ForgeRegistries.ITEMS.getValues())
		{
			item.fillItemGroup(ItemGroup.SEARCH, nonnulllist);
		}

		nonnulllist.forEach((item) -> tree.func_217872_a(item));
		
		Minecraft.getInstance().getSearchTreeManager().add(SearchTreeManager.ITEMS, tree);
	}

	/** Use a json object (presumably one from an assets/modid/mondobooks folder) to generate a data object **/
	public static Map<String, Set<String>> getJsonAsData(JsonObject json)
	{
		return GSON.fromJson(json, DATA_TYPE);
	}
	
	public static Map<String, Set<String>> mergeData(Map<String, Set<String>> baseMap, Map<String, Set<String>> newMap)
	{
		newMap.entrySet().forEach(entry -> baseMap.merge(entry.getKey(), entry.getValue(), Sets::union));
		return baseMap;
	}
	
	public static Map<ItemGroup, Set<ResourceLocation>> bakeMap(Map<String, Set<String>> raw)
	{
		Map<String, ItemGroup> groupsByName = Arrays.stream(ItemGroup.GROUPS)
			.collect(Collectors.toMap(group -> group.getPath(), group -> group));
		
		Set<ItemGroup> groups = raw.keySet().stream()
			.map(key -> groupsByName.get(key))
			.collect(Collectors.toSet());
		
		Map<ItemGroup, Set<ResourceLocation>> out = new HashMap<>();
		
		for (ItemGroup group : groups)
		{
			Set<ResourceLocation> ids = raw.getOrDefault(group.getPath(), ImmutableSet.of()).stream().map(ResourceLocation::new).collect(Collectors.toSet());
			out.put(group, ids);
		}
		
		return out;
	}
}
