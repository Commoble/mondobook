package com.github.commoble.mondobook.client.api;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nonnull;

import net.minecraft.util.ResourceLocation;

/**
 * Clientside registry for registering factories for producing things from raw json-derived data classes
 * FMLClientSetup would be a good place to add things to such a registry in
 */
public class AssetFactoryRegistry<Raw, Fine> implements Function<Raw, Fine>
{
	private final Map<ResourceLocation, Function<Raw, ? extends Fine>> factories = new HashMap<>();
	
	@Nonnull
	private final Function<Raw, Fine> emptyFactory;
	@Nonnull
	private final Function<Raw, ResourceLocation> keyFactory;
	
	/**
	 * @param keyFactory The factory to use to determine how to get the key that turns a raw into a fine, e.g.
	 * 	let's say Raw is a json-based data class with a resourcelocation in it, we want to be able to get that out of it
	 * 	so that we can take the resourcelocation and the raw and use them to get the thing to turn the raw into a fine
	 * @param emptyFactory The factory to return when an invalid ResourceLocation is used as a key. Should be non-null and not return null.
	 */
	public AssetFactoryRegistry(@Nonnull Function<Raw, ResourceLocation> keyFactory, @Nonnull Function<Raw, Fine> emptyFactory)
	{
		this.keyFactory = keyFactory;
		this.emptyFactory = emptyFactory;
	}
	
	@Override
	public Fine apply(Raw raw)
	{
		return this.getFactory(raw).apply(raw);
	}
	
	public Function<Raw, ? extends Fine> getFactory(Raw raw)
	{
		return this.getFactory(this.keyFactory.apply(raw));
	}
	
	/**
	 * @param id A resourcelocation to use as the key, typically one specified in a raw data object derived from json
	 * @return A factory that takes the given RawElement where that type was found and produces an element
	 */
	public Function<Raw, ? extends Fine> getFactory(ResourceLocation id)
	{
		return this.factories.getOrDefault(id, this.emptyFactory);
	}

	/**
	 * @param id The key for the given factory, typically one that is specified in a json
	 * @param factory A function that takes a raw json-derived object that would have the given type ID and returns something based on that object
	 * @return If the given id had previously been registered, returns the old factory that had been registered to it before we replaced it
	 */
	public Function<Raw, ? extends Fine> register(ResourceLocation id, Function<Raw, ? extends Fine> factory)
	{
		return this.factories.put(id, factory);
	}	
	/**
	 * As register(ResourceLocation, Function), but takes a string and converts it to a ResourceLocation for you.
	 * Strings with no namespace will default to "minecraft:id"! beware
	 * @param id A string in the format "namespace:thing"
	 * @param factory A function that takes a raw object that would have the given type ID and returns something based on it
	 * @return If the given id had previously been registered, returns the old factory that had been registered to it before we replaced it
	 */
	public Function<Raw, ? extends Fine> register(String id, Function<Raw, ? extends Fine> factory)
	{
		return this.register(new ResourceLocation(id), factory);
	}
	
	/**
	 * As register(ResourceLocation, Function), but takes a modid and path and assembles them into the ResourceLocation for you.
	 * @param modid
	 * @param id
	 * @param factory A function that takes a raw object that would have the given type ID and returns something based on it
	 * @return If the given id had previously been registered, returns the old factory that had been registered to it before we replaced it
	 */
	public Function<Raw, ? extends Fine> register(String modid, String path, Function<Raw, ? extends Fine> factory)
	{
		return this.register(new ResourceLocation(modid, path), factory);
	}
	
	
	
	/** Returns the factory registry map for more complicated operations. **/
	public Map<ResourceLocation, Function<Raw, ? extends Fine>> getRegistry()
	{
		return this.factories;
	}
}
