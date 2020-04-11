package com.github.commoble.mondobook.experimental;

import java.util.List;

import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.book.RawStyle;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;

public class StyledElement
{
	private Element element;
	private ResourceLocation font;
	private Style textStyle;
	
	
	public StyledElement(List<RawStyle> styles, Element element)
	{
		this.element = element;
	}
	
	public Element getElement()
	{
		return this.element;
	}
}
