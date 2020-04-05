package com.github.commoble.mondobook.client.assets;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.util.ResourceLocation;

// to be serialized via GSON
public class RawElement
{
	private RawElement[] children;
	private String data;
	private String type;
	
	private transient ResourceLocation typeID;
	private transient List<RawElement> childList;
	
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
}
