package com.github.commoble.mondobook.client.api;

import java.util.List;

import com.github.commoble.mondobook.client.book.RawElement;
import com.google.common.collect.ImmutableList;

import net.minecraft.util.ResourceLocation;

public abstract class Element
{
	public static final ResourceLocation NONE_LOCATION = new ResourceLocation("mondobook:none");
	
	private final RawElement raw;
	
	public Element(RawElement raw)
	{
		this.raw = raw;
	}
	
	public abstract List<Drawable> getAsDrawables(DrawableRenderer renderer, int textWidth);
	
	public ResourceLocation getTypeID()
	{
		return this.raw.getTypeID();
	}
	
	public String getID()
	{
		return this.raw.getID();
	}
	
	public List<String> getStyleClasses()
	{
		return this.raw.getStyleClasses();
	}
	
	public static final Element NONE = new Element(new RawElement())
	{
		@Override
		public List<Drawable> getAsDrawables(DrawableRenderer renderer, int textWidth)
		{
			return ImmutableList.of();
		}
		
		@Override
		public ResourceLocation getTypeID()
		{
			return NONE_LOCATION;
		}
		
		@Override
		public String getID()
		{
			return "";
		}
	};
}
