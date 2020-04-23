package com.github.commoble.mondobook.client.content;

import java.util.List;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.internal.BookStyle;
import com.github.commoble.mondobook.client.api.internal.RawElement;
import com.google.common.collect.ImmutableList;

public class CollectionElement extends Element
{

	public CollectionElement(RawElement raw)
	{
		super(raw);
	}

	@Override
	public List<Drawable> getAsDrawables(DrawableRenderer renderer, BookStyle style, int containerWidth)
	{
		return ImmutableList.of();
	}

}
