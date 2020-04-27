package com.github.commoble.mondobook.client.content;

import java.util.List;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.internal.ColumnDrawable;
import com.github.commoble.mondobook.client.api.internal.ElementPrimer;

public class ColumnElement extends Element
{

	public ColumnElement(ElementPrimer primer)
	{
		super(primer);
	}

	@Override
	public List<Drawable> getColumnOfDrawables(DrawableRenderer renderer, int containerWidth)
	{
		int interiorSpace = this.getStyle().getInteriorWidth(containerWidth);
		return this.getStyle().styleMultipleDrawables(this.getChildren(),
			(childElement, parentStyle) -> new ColumnDrawable(childElement.getColumnOfDrawables(renderer, interiorSpace)), containerWidth);
	}


}
