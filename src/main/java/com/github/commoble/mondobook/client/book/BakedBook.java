package com.github.commoble.mondobook.client.book;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.github.commoble.mondobook.client.api.AssetFactories;
import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;

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
		
		List<RawStyle> rawStyles = rawBook.getStyles();
		List<RawElement> rawElements = rawBook.getElements();

		rawElements.forEach(raw -> drawables.addAll(AssetFactories.ELEMENTS.apply(raw).getAsDrawables(renderer, maxLineWidth)));

		PageBuilder builder = new PageBuilder(pageHeightInPixels);
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
				builder = new PageBuilder(pageHeightInPixels);
				builder.addDrawable(drawable);
			}
		}
		// make sure we add the last page
		if (!builder.getDrawables().isEmpty())
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
