package com.github.commoble.mondobook.client.api;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.github.commoble.mondobook.client.api.internal.RawSelector;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import net.minecraft.util.ResourceLocation;

/** Wrapper class around the data from a RawSelector for selecting elements to apply styles to **/
public abstract class Selector implements Predicate<Element>
{
	public static final Predicate<Element> NONE = Predicates.alwaysFalse();
	public static final Predicate<Element> ALL = Predicates.alwaysTrue();
	
	private final ResourceLocation typeID;
	private final String matchString;
	private final List<Selector> children;
	
	public Selector(RawSelector raw)
	{
		if (raw == null)
		{
			this.typeID = new ResourceLocation("mondobook:all");
			this.matchString = "";
			this.children = ImmutableList.of();
		}
		else
		{
			this.typeID = raw.getTypeID();
			this.matchString = raw.getMatchString();
			this.children = raw.getChildList().stream()
				.map(AssetFactories.SELECTORS)
				.collect(Collectors.toList());
		}
	}
	
	@Override
	public abstract boolean test(Element element);
	
	public abstract Specificity getSpecificity(Element element);
	
	public ResourceLocation getTypeID()
	{
		return this.typeID;
	}
	
	public String getMatchString()
	{
		return this.matchString;
	}
	
	public List<Selector> getChildren()
	{
		return this.children;
	}
}
