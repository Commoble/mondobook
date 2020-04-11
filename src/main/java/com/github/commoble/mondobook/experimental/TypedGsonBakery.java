package com.github.commoble.mondobook.experimental;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import net.minecraft.util.ResourceLocation;

/**
 * Class for streamlining the process of getting useful java objects from json.
 * The process goes something like this:
 * json file -> [GSON] -> raw gson-friendly object -> [BAKERY LOOKUP] -> baked instance 
 */
public class TypedGsonBakery<Raw, Baked> extends GsonBakery<Raw, Baked>
{	
	private final @Nonnull Function<Raw, ResourceLocation> keyFactory;
	
	/** Map of subtype ResourceLocation to subclass of Baked **/
	private final Map<ResourceLocation, Function<Raw, ? extends Baked>> typeFactories = new HashMap<>();

	private final @Nonnull Function<Raw, Baked> fallbackFactory;
	/**
	 * 
	 * @param folder This is the name of the folders that the resource loader looks in, e.g. assets/modid/FOLDER
	 * @param rawClass The .class object that the GSON parser will attempt to convert a JSON at the given location to
	 * @param keyFactory A function that gets a "typeKey" resourceLocation from a raw object. The typeKey will be used to get a factory that produces
	 * an instance of Baked or a subclass thereof.
	 * @param emptyFactory A function to default to when a baked-instance-factory is not present for a given typeKey. Invalid/missing typeKeys will be logged.
	 */
	public TypedGsonBakery(@Nonnull String folder, @Nonnull Class<Raw> rawClass, @Nonnull Function<Raw, ResourceLocation> keyFactory, @Nonnull Function<Raw, Baked> fallbackFactory, Supplier<Baked> fallbackSupplier)
	{
		super(folder, rawClass, fallbackSupplier);
		this.keyFactory = keyFactory;
		this.fallbackFactory = fallbackFactory;
	}

	/**
	 * @param typeKey The key for the given factory, typically one that is specified in a json
	 * @param factory A function that takes a raw json-derived object that would have the given type ID and returns something based on that object
	 * @return If the given id had previously been registered, returns the old factory that had been registered to it before we replaced it
	 */
	public Function<Raw, ? extends Baked> register(ResourceLocation typeKey, Function<Raw, ? extends Baked> factory)
	{
		return this.typeFactories.put(typeKey, factory);
	}
	
	@Override
	protected Function<Raw, ? extends Baked> getFactory(Raw raw)
	{
		return this.typeFactories.getOrDefault(this.keyFactory.apply(raw), this.fallbackFactory);
	}
}
