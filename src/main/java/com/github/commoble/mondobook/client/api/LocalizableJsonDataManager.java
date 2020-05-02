package com.github.commoble.mondobook.client.api;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.commoble.mondobook.SimpleJsonDataManager;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class LocalizableJsonDataManager<T> extends SimpleJsonDataManager<T>
{

	public LocalizableJsonDataManager(String folder, Class<T> dataClass)
	{
		super(folder, dataClass);
	}



	/** Called on resource reload, the jsons have already been found for us and we just need to parse them in here **/
	@Override
	protected void apply(Map<ResourceLocation, JsonObject> jsons, IResourceManager manager, IProfiler profiler)
	{
		@SuppressWarnings("resource")
		String languageCode = Minecraft.getInstance().gameSettings.language;
		
		// use english as the fallback if a localized book is not available
		Map<ResourceLocation, JsonObject> englishMap = this.getLocalizedMap(jsons, "en_us");	// "en_us/id" key becomes"id"
		
		// if the user is using english localization, just use the english map
		if (languageCode.equals("en_us"))
		{
			super.apply(englishMap, manager, profiler);
			return;
		}
		// if the user is using a different language, get the localized jsons and merge with the fallback map
		// not present in english + not present in user lang = doesn't exist
		// not present in english + present in user lang = use user lang
		// present in english + not present in user lang = use english
		// present in english + present in user lang = use user lang
		else
		{
			Map<ResourceLocation, JsonObject> localMap = this.getLocalizedMap(jsons, languageCode);
			
			// use all entries in localMap, plus all entries in englishMap whose keys are not in localMap
			Map<ResourceLocation, JsonObject> finalMap = new HashMap<>();
			
			localMap.entrySet().forEach(entry -> finalMap.put(entry.getKey(), entry.getValue()));
			englishMap.entrySet().stream()
				.filter(entry -> !localMap.keySet().contains(entry.getKey()))
				.forEach(entry -> finalMap.put(entry.getKey(), entry.getValue()));
			
			super.apply(finalMap, manager, profiler);
			return;
		}
	}
	
	private Map<ResourceLocation, JsonObject> getLocalizedMap(Map<ResourceLocation, JsonObject> jsons, String languageCode)
	{
		String folder = languageCode + "/";
		int prefixLength = folder.length();
		
		Map<ResourceLocation, ResourceLocation> deprefixedKeys = Maps.asMap(
			jsons.keySet().stream().filter(key -> key.getPath().startsWith(folder)).collect(Collectors.toSet()),
			key -> new ResourceLocation(key.getNamespace(), key.getPath().substring(prefixLength)));
		
		Map<ResourceLocation, JsonObject> localizedMap = jsons.entrySet().stream()
			.filter(entry -> deprefixedKeys.containsKey(entry.getKey()))
			.collect(Collectors.toMap(entry -> deprefixedKeys.get(entry.getKey()), entry -> entry.getValue()));
		
		return localizedMap;
	}
}
