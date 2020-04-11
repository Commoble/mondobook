package com.github.commoble.mondobook.experimental;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import com.github.commoble.mondobook.util.MapUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public abstract class GsonBakery<Raw, Baked> extends JsonReloadListener implements Function<ResourceLocation, Baked>
{
	private static final Gson GSON = new GsonBuilder().create();
	
	private final @Nonnull Class<Raw> rawClass;
	private final @Nonnull Supplier<Baked> fallbackSupplier;
	
	/** The raw data that we parsed from json last time resources were reloaded **/
	protected Map<ResourceLocation, Baked> data = new HashMap<>();


	public GsonBakery(@Nonnull String folder, @Nonnull Class<Raw> rawClass, @Nonnull Supplier<Baked> fallbackSupplier)
	{
		super(GSON, folder);
		this.rawClass = rawClass;
		this.fallbackSupplier = fallbackSupplier;
	}
	
	protected abstract Function<Raw, ? extends Baked> getFactory(Raw raw);

	@Override
	public Baked apply(ResourceLocation resourceKey)
	{
		return this.data.getOrDefault(resourceKey, this.fallbackSupplier.get());
	}
	
	@Override
	protected void apply(Map<ResourceLocation, JsonObject> jsons, IResourceManager resourceManager, IProfiler profiler)
	{
		this.data = MapUtil.mapValues(jsons, this::bakeJson);
	}
	
	protected Baked bakeJson(JsonObject json)
	{
		Raw raw = this.getRaw(json);
		return this.getFactory(raw).apply(raw);
	}
	
	protected Raw getRaw(JsonObject json)
	{
		return GSON.fromJson(json, this.rawClass);
	}
	
}
