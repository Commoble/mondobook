package com.github.commoble.mondobook.client.api;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.commoble.mondobook.client.api.internal.BookStyle;
import com.github.commoble.mondobook.client.api.internal.ElementPrimer;
import com.github.commoble.mondobook.client.api.internal.RawBook;
import com.github.commoble.mondobook.client.api.internal.RawElement;
import com.github.commoble.mondobook.client.api.internal.RawStyle;
import com.google.common.collect.ImmutableList;

import net.minecraft.util.ResourceLocation;

public abstract class Element
{
	public static final ResourceLocation NONE_LOCATION = new ResourceLocation("mondobook:none");
	
	private final RawElement raw;
	private final List<RawStyle> styleList;	// the master style list, used for styling sub-elements
	private final BookStyle style;
	private final List<Element> children;
	
	public Element(ElementPrimer primer)
	{
		this.raw = primer.getRawElement();
		this.styleList = primer.getRawStyles();
		this.style = RawBook.styleElement(this.styleList, this);
		this.children = this.raw.getChildren().stream()
			.map(rawChild -> AssetFactories.ELEMENTS.apply(new ElementPrimer(rawChild, this.styleList)))
			.collect(Collectors.toList());
	}
	
	/** if shrinkwrap is true, alignment will be ignored **/
	public abstract List<Drawable> getColumnOfDrawables(DrawableRenderer renderer, int containerWidth, boolean shrinkwrap);
	
	public ResourceLocation getTypeID()
	{
		return this.raw.getTypeID();
	}
	
	public String getID()
	{
		return this.raw.getID();
	}
	
	public BookStyle getStyle()
	{
		return this.style;
	}
	
	public List<String> getStyleClasses()
	{
		return this.raw.getStyleClasses();
	}
	
	public List<Element> getChildren()
	{
		return this.children;
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
	
	public static final Element NONE = new Element(ElementPrimer.NONE)
	{
		@Override
		public List<Drawable> getColumnOfDrawables(DrawableRenderer renderer, int containerWidth, boolean shrinkwrap)
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
