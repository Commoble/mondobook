package com.github.commoble.mondobook.client;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.internal.RawBook;
import com.github.commoble.mondobook.client.api.internal.RawFormat;

public class BakedBook
{
	private final List<BakedPage> pages;
	public final RawFormat format;
	
	public BakedBook(RawBook rawBook, DrawableRenderer renderer)
	{
		this.format = rawBook.getFormat();
		this.pages = this.bakePages(rawBook, this.format.content_width, this.format.content_height, renderer);
	}
	
	public List<BakedPage> bakePages(RawBook rawBook, int maxLineWidth, int pageHeightInPixels, DrawableRenderer renderer)
	{
		List<BakedPage> pages = new ArrayList<>();
		Deque<Drawable> drawables = new ArrayDeque<>();
		
		List<Element> bakedElements = rawBook.getBakedElements();
		bakedElements.forEach(element -> drawables.addAll(element.getColumnOfDrawables(renderer, maxLineWidth, false)));

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
