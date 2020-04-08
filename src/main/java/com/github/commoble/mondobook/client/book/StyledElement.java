package com.github.commoble.mondobook.client.book;

import java.util.List;

import com.github.commoble.mondobook.client.api.Element;

public class StyledElement
{
	private List<BookStyle> styles;
	private Element element;
	
	public StyledElement(List<BookStyle> styles, Element element)
	{
		this.styles = styles;
		this.element = element;
	}
	
	public List<BookStyle> getStyles()
	{
		return this.styles;
	}
	
	public Element getElement()
	{
		return this.element;
	}
}
