package com.github.commoble.mondobook.client.book.elements;

import java.util.List;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.google.common.collect.ImmutableList;

public class NewPageElement
{
	public static List<Drawable> getNewPage(DrawableRenderer renderer, int textWidth)
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
