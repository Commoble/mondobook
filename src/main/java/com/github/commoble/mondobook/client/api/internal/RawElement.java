package com.github.commoble.mondobook.client.api.internal;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.util.ResourceLocation;

// to be deserialized via GSON
public class RawElement
{
	private List<RawElement> children;
	private String data;
	private String type;
	private String id;	// CSS-like optional identifier string for use with style selectors
	private List<String> classes;	// CSS-like style classes
	private Map<String, String> attributes;
	
	private transient ResourceLocation typeID;
	
	/** Argless constructor for GSON deserialization **/
	public RawElement() {}
	
	/** Returns a shallow copy of this instance **/
	public RawElement copy()
	{
		return new RawElement()
			.setType(this.getTypeID())
			.setData(this.getData())
			.setID(this.getID())
			.setClasses(this.getStyleClasses())
			.setAttributes(this.getAttributes())
			.setChildren(this.getChildren());
	}
	
	/** Sets the type of element this will be used to build; used for building Elements in-java **/
	public RawElement setType(ResourceLocation type)
	{
		this.typeID = type;
		return this;
	}
	
	/** Sets the ID string; used for style selecting. ID strings have a higher specificity than class strings or element types **/
	public RawElement setID(String id)
	{
		this.id = id;
		return this;
	}
	
	/** Set the primary data of this element **/
	public RawElement setData(String data)
	{
		this.data = data;
		return this;
	}
	
	/** Set the secondary data of this element **/
	public RawElement setAttributes(Map<String, String> attributes)
	{
		this.attributes = attributes;
		return this;
	}
	
	/** Set the class strings used by style selectors **/
	public RawElement setClasses(List<String> classes)
	{
		this.classes = classes;
		return this;
	}
	
	/** Set the sub-elements of this element **/
	public RawElement setChildren(List<RawElement> children)
	{
		this.children = children;
		return this;
	}
	
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
