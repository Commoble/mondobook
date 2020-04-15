package com.github.commoble.mondobook.client.api.internal;

import java.util.List;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;

public class StyledElement
{
	private BookStyle style;
	private Element element;
	
	
	public StyledElement(BookStyle style, Element element)
	{
		this.style = style;
		this.element = element;
	}
	
	public BookStyle getStyle()
	{
		return this.style;
	}
	
	public Element getElement()
	{
		return this.element;
	}
	
	public List<Drawable> getAsDrawables(DrawableRenderer renderer, int drawingWidth)
	{
		return this.element.getAsDrawables(renderer, this.style, drawingWidth);
	}
}
