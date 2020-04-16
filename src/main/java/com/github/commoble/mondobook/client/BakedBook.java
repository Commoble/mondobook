package com.github.commoble.mondobook.client;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.internal.RawBook;
import com.github.commoble.mondobook.client.api.internal.StyledElement;

public class BakedBook
{
	private final List<BakedPage> pages;
	
	public BakedBook(RawBook rawBook, int pageHeightInPixels, int maxLineWidth, DrawableRenderer renderer)
	{
		this.pages = this.bakePages(rawBook, pageHeightInPixels, maxLineWidth, renderer);
	}
	
	public List<BakedPage> bakePages(RawBook rawBook, int pageHeightInPixels, int maxLineWidth, DrawableRenderer renderer)
	{
		List<BakedPage> pages = new ArrayList<>();
		Deque<Drawable> drawables = new ArrayDeque<>();
		
		List<StyledElement> styledElements = rawBook.getStyledElements();
		styledElements.forEach(styledElement -> drawables.addAll(styledElement.getAsDrawables(renderer, maxLineWidth)));

		PageBuilder builder = new PageBuilder(pageHeightInPixels, maxLineWidth);
		while (!drawables.isEmpty())
		{
			Drawable drawable = drawables.pollFirst();
			if (builder.canAddDrawable(drawable))
			{
				builder.addDrawable(drawable);
			}
			else
			{
				pages.add(builder.build());
				builder = new PageBuilder(pageHeightInPixels, maxLineWidth);
				builder.addDrawable(drawable);
			}
		}
		// make sure we add the last page
		if (builder.getDrawableCount() > 0)
		{
			pages.add(builder.build());
		}
		return pages;
	}
	
	public List<BakedPage> getPages()
	{
		return this.pages;
	}
}
