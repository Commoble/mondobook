package com.github.commoble.mondobook.client.api.internal;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import net.minecraft.util.ResourceLocation;

public class ElementPrimer
{
	private final RawElement raw;
	private final List<RawStyle> styles;
	
	public static final ElementPrimer NONE = new ElementPrimer(new RawElement(), ImmutableList.of());
	
	public ElementPrimer(RawElement raw, List<RawStyle> styles)
	{
		this.raw = raw;
		this.styles = styles;
	}
	
	public RawElement getRawElement()
	{
		return this.raw;
	}
	
	public List<RawStyle> getRawStyles()
	{
		return this.styles;
	}
	
	public ResourceLocation getTypeID()
	{
		return this.raw.getTypeID();
	}
	
	public String getData()
	{
		return this.raw.getData();
	}

	public Map<String, String> getAttributes()
	{
		return this.raw.getAttributes();
	}
}
