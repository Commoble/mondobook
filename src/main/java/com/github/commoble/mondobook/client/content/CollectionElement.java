package com.github.commoble.mondobook.client.content;

import java.util.List;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.internal.ElementPrimer;
import com.google.common.collect.ImmutableList;

public class CollectionElement extends Element
{

	public CollectionElement(ElementPrimer primer)
	{
		super(primer);
	}

	@Override
	public List<Drawable> getAsDrawables(DrawableRenderer renderer, int containerWidth)
	{
		return ImmutableList.of();
	}

}
