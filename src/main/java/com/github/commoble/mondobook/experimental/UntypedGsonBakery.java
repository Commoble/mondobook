package com.github.commoble.mondobook.experimental;


import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

public class UntypedGsonBakery<Raw, Baked> extends GsonBakery<Raw, Baked>
{
	private final @Nonnull Function<Raw, Baked> defaultFactory;
	
	
	/**
	 * 
	 * @param folder This is the name of the folders that the resource loader looks in, e.g. assets/modid/FOLDER
	 * @param rawClass The .class object that the GSON parser will attempt to convert a JSON at the given location to
	 * @param defaultFactory The thing that converts a raw instance to a baked instance
	 */
	public UntypedGsonBakery(@Nonnull String folder, @Nonnull Class<Raw> rawClass, @Nonnull Function<Raw, Baked> defaultFactory, @Nonnull Supplier<Baked> fallbackSupplier)
	{
		super(folder, rawClass, fallbackSupplier);
		this.defaultFactory = defaultFactory;
	}
	
	
	@Override
	protected Function<Raw, ? extends Baked> getFactory(Raw raw)
	{
		return this.defaultFactory;
	}
}
