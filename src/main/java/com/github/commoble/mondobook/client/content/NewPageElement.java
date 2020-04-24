package com.github.commoble.mondobook.client.content;

import java.util.List;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.internal.ElementPrimer;
import com.google.common.collect.ImmutableList;

public class NewPageElement extends Element
{
	public NewPageElement(ElementPrimer primer)
	{
		super(primer);
	}
	
	@Override
	public List<Drawable> getColumnOfDrawables(DrawableRenderer renderer, int containerWidth)
	{
		return NEW_PAGE_DRAWABLE;
	}
	
	public static final List<Drawable> NEW_PAGE_DRAWABLE = ImmutableList.of(new Drawable()
	{
		@Override
		public void render(DrawableRenderer renderer, int startX, int startY, int maxWidth)
		{
		}

		@Override
		public int getHeight()
		{
			return 0;
		}

		@Override
		public int getWidth()
		{
			return 0;
		}
		
		@Override
		public boolean canAddToList(List<Drawable> drawables)
		{
			return drawables.size() == 0;
		}

		@Override
		public void renderTooltip(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
		{
		}
	});
}
