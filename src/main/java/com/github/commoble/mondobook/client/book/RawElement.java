package com.github.commoble.mondobook.client.book;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.util.ResourceLocation;

// to be serialized via GSON
public class RawElement
{
	private RawElement[] children;
	private String data;
	private String type;
	private String id;	// CSS-like optional identifier string for use with style selectors
	private String[] classes;	// CSS-like style classes
	
	private transient ResourceLocation typeID;
	private transient List<RawElement> childList;
	private transient List<String> classList;
	
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
		if (this.childList == null)
		{
			this.childList = ImmutableList.copyOf(this.getChildrenArray());
		}
		return this.childList;
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
		if (this.classList == null)
		{
			this.classList = ImmutableList.copyOf(this.getStyleClassArray());
		}
		return this.classList;
	}
	
	private String getTypeString()
	{
		if (this.type == null)
		{
			this.type = "mondobook:text";
		}
		return this.type;
	}
	
	private RawElement[] getChildrenArray()
	{
		if (this.children == null)
		{
			this.children = new RawElement[0];
		}
		return this.children;
	}
	
	private String[] getStyleClassArray()
	{
		if (this.classes == null)
		{
			this.classes = new String[0];
		}
		return this.classes;
	}
}
