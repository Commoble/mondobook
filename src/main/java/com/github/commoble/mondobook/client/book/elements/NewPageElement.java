package com.github.commoble.mondobook.client.book.elements;

import java.util.List;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.assets.RawElement;
import com.google.common.collect.ImmutableList;

public class NewPageElement extends Element
{
	public NewPageElement(RawElement raw)
	{
		super(raw);
	}
	
	@Override
	public List<Drawable> getAsDrawables(DrawableRenderer renderer, int textWidth)
	{
		return NEW_PAGE_DRAWABLE;
	}
	
	public static final List<Drawable> NEW_PAGE_DRAWABLE = ImmutableList.of(new Drawable()
	{
		@Override
		public void render(DrawableRenderer renderer, int startX, int startY)
		{
		}

		@Override
		public int getHeight()
		{
			return 0;
		}
		
		@Override
		public boolean canAddToList(List<Drawable> drawables)
		{
			return drawables.size() == 0;
		}
	});
}
