package com.github.commoble.mondobook.client.content;

import java.util.List;
import java.util.Optional;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.internal.BookStyle;
import com.github.commoble.mondobook.client.api.internal.DrawableWithOffset;
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
		public void renderSelf(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
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
		public Optional<BookStyle> getStyle()
		{
			return Optional.empty();
		}

		@Override
		public List<DrawableWithOffset> getChildren()
		{
			return ImmutableList.of();
		}
	});
}
