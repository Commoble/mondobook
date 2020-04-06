package com.github.commoble.mondobook.client.selectors;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.util.ResourceLocation;

// to be serialzed via a basic GSON parser
public class RawSelector
{
	private String type;	// the registered selector ResourceLocation
	private String match;	// the string that the type will use to match against a given element
	private RawSelector[] children;	// OPTIONAL list of child selectors that the selector type may use
	
	private transient ResourceLocation typeID;
	private transient List<RawSelector> childList;
	
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
		if (this.childList == null)
		{
			this.childList = ImmutableList.copyOf(this.getChildrenArray());
		}
		return this.childList;
	}
	
	private String getTypeString()
	{
		if (this.type == null)
		{
			this.type = "mondobook:none";
		}
		return this.type;
	}
	
	private RawSelector[] getChildrenArray()
	{
		if (this.children == null)
		{
			this.children = new RawSelector[0];
		}
		return this.children;
	}
}
