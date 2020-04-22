package com.github.commoble.mondobook.client.api;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.github.commoble.mondobook.client.api.internal.BookStyle;
import com.github.commoble.mondobook.client.api.internal.RawElement;
import com.github.commoble.mondobook.client.api.internal.RawStyle;
import com.github.commoble.mondobook.util.ListUtil;
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
	
	public abstract List<Drawable> getAsDrawables(DrawableRenderer renderer, BookStyle style, int containerWidth);
	
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
	
	public List<Element> getChildren()
	{
		return ListUtil.map(this.raw.getChildren(), AssetFactories.ELEMENTS);
	}
	
	public Map<String, String> Attributes()
	{
		return this.raw.getAttributes();
	}
	
	public Comparator<RawStyle> getStyleComparator()
	{
		return (styleA, styleB) -> styleA.getSelector()
			.getSpecificity(this)
			.compareTo(styleB.getSelector().getSpecificity(this));
	}
	
	public static final Element NONE = new Element(new RawElement())
	{
		@Override
		public List<Drawable> getAsDrawables(DrawableRenderer renderer, BookStyle style, int containerWidth)
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
