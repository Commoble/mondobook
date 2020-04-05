package com.github.commoble.mondobook.client.api;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.github.commoble.mondobook.client.assets.RawElement;

import net.minecraft.util.ResourceLocation;

/**
 * A clientside registry for registering factories for producing drawables from RawElements.
 * FMLClientSetup would be a good event to use for adding factories in
 */
public class DrawableRegistry
{
	private static final Map<ResourceLocation, Function<RawElement, DrawableFactory>> factories = new HashMap<>();
	
	public static final Function<RawElement, DrawableFactory> NO_FACTORY = element -> DrawableFactory.NONE;
	
	/**
	 * @param id A resourcelocation specified in an element object from a book json
	 * @return A factory that takes the given RawElement where that type was found and produces a metafactory
	 */
	public static Function<RawElement, DrawableFactory> getFactory(ResourceLocation id)
	{
		return factories.getOrDefault(id, NO_FACTORY);
	}
	
	/**
	 * @param id A ResourceLocation that can be specified as an element object's type in a book json
	 * @param factory A function that takes a RawElement that would have the given type ID and returns a metafactory based on that RawElement
	 * @return If the given id had previously been registered, returns the old factory that had been registered to it before we replaced it
	 */
	public static Function<RawElement, DrawableFactory> register(ResourceLocation id, Function<RawElement, DrawableFactory> factory)
	{
		return factories.put(id, factory);
	}
	
	/**
	 * As register(ResourceLocation, Function), but takes a string and converts it to a ResourceLocation for you.
	 * Strings with no namespace will default to "minecraft:id"! beware
	 * @param id A string in the format "namespace:thing"
	 * @param factory A metafactory that takes a RawElement that would have the given type ID and returns a factory based on that RawElement
	 * @return If the given id had previously been registered, returns the old factory that had been registered to it before we replaced it
	 */
	public static Function<RawElement, DrawableFactory> register(String id, Function<RawElement, DrawableFactory> factory)
	{
		return register(new ResourceLocation(id), factory);
	}
	
	/**
	 * As register(ResourceLocation, Function), but takes a modid and path and assembles them into the ResourceLocation for you.
	 * @param modid
	 * @param id
	 * @param factory A metafactory that takes a RawElement that would have the given type ID and returns a factory based on that RawElement
	 * @return If the given id had previously been registered, returns the old metafactory that had been registered to it before we replaced it
	 */
	public static Function<RawElement, DrawableFactory> register(String modid, String path, Function<RawElement, DrawableFactory> factory)
	{
		return register(new ResourceLocation(modid, path), factory);
	}
	
	
	
	/** Returns the factory registry map for more complicated operations. **/
	public static Map<ResourceLocation, Function<RawElement, DrawableFactory>> getRegistry()
	{
		return factories;
	}
}
