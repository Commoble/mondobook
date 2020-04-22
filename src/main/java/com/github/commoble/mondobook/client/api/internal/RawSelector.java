package com.github.commoble.mondobook.client.api.internal;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.util.ResourceLocation;

// to be serialzed via a basic GSON parser
public class RawSelector
{
	private String type;	// the registered selector ResourceLocation
	private String match;	// the string that the type will use to match against a given element
	private List<RawSelector> children;	// OPTIONAL list of child selectors that the selector type may use
	
	private transient ResourceLocation typeID;
	
	public String getMatchString()
	{
		if (this.match == null)
		{
			this.match = "";
		}
		
		return this.match;
	}
	
	public ResourceLocation getTypeID()
	{
		if (this.typeID == null)
		{
			this.typeID = new ResourceLocation(this.getTypeString());
		}
		return this.typeID;
	}
	
	public List<RawSelector> getChildList()
	{
		if (this.children == null)
		{
			this.children = ImmutableList.of();
		}
		return this.children;
	}
	
	private String getTypeString()
	{
		if (this.type == null)
		{
			this.type = "mondobook:none";
		}
		return this.type;
	}
}
