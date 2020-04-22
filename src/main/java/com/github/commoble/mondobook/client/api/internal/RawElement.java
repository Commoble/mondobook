package com.github.commoble.mondobook.client.api.internal;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.util.ResourceLocation;

// to be serialized via GSON
public class RawElement
{
	private List<RawElement> children;
	private String data;
	private String type;
	private String id;	// CSS-like optional identifier string for use with style selectors
	private List<String> classes;	// CSS-like style classes
	private Map<String, String> attributes;
	
	private transient ResourceLocation typeID;
	
	public String getData()
	{
		if (this.data == null)
		{
			this.data = "";
		}
		
		return this.data;
	}
	
	public ResourceLocation getTypeID()
	{
		if (this.typeID == null)
		{
			this.typeID = new ResourceLocation(this.getTypeString());
		}
		return this.typeID;
	}
	
	public List<RawElement> getChildren()
	{
		if (this.children == null)
		{
			this.children = ImmutableList.of();
		}
		return this.children;
	}
	
	public String getID()
	{
		if (this.id == null)
		{
			this.id = "";
		}
		return this.id;
	}
	
	public List<String> getStyleClasses()
	{
		if (this.classes == null)
		{
			this.classes = ImmutableList.of();
		}
		return this.classes;
	}
	
	public Map<String, String> getAttributes()
	{
		if (this.attributes == null)
		{
			this.attributes = ImmutableMap.of();
		}
		return this.attributes;
	}
	
	private String getTypeString()
	{
		if (this.type == null)
		{
			this.type = "mondobook:text";
		}
		return this.type;
	}
}
