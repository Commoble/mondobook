package com.github.commoble.mondobook.client.content;

import java.util.List;
import java.util.stream.Collectors;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.internal.ColumnDrawable;
import com.github.commoble.mondobook.client.api.internal.ElementPrimer;
import com.github.commoble.mondobook.client.api.internal.RowDrawable;

public class RowElement extends Element
{
	public RowElement(ElementPrimer primer)
	{
		super(primer);
	}

	@Override
	public List<Drawable> getColumnOfDrawables(DrawableRenderer renderer, int containerWidth)
	{
		int columnWidth = containerWidth / this.getChildren().size();	// TODO how to style or shrinkwrap this?
		
		List<Drawable> columns = this.getChildren().stream()
			.map(childElement -> this.getChildDrawable(childElement, renderer, columnWidth))
			.collect(Collectors.toList());
		
		return this.getStyle().getSingleStyledDrawable(new RowDrawable(columns), containerWidth);
	}

	protected Drawable getChildDrawable(Element childElement, DrawableRenderer renderer, int columnWidth)
	{
		return new ColumnDrawable(childElement.getColumnOfDrawables(renderer, columnWidth));
	}
}
