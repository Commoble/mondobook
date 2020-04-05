package com.github.commoble.mondobook.client.book;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.assets.RawElement;
import com.github.commoble.mondobook.client.api.DrawableRegistry;

public class BakedPage
{
	private final List<Drawable> drawables;
	
	public BakedPage(List<Drawable> drawables)
	{
		this.drawables = drawables;
	}
	
	public List<Drawable> getDrawables()
	{
		return this.drawables;
	}
	
	public static List<BakedPage> fromRaws(List<RawElement> raws, int pageHeightInPixels, int pageWidth, DrawableRenderer renderer)
	{
		List<BakedPage> pages = new ArrayList<>();
		Deque<Drawable> drawables = new ArrayDeque<>();

		raws.forEach(raw -> drawables.addAll(getDrawables(raw, renderer, pageWidth)));
//		elements.stream()
//			.map(raw -> getDrawables(raw, renderer, textWidth))
//			.forEach(drawables -> lineDeck.addAll(drawables));
		//elements.stream().forEach(paragraph -> lineDeck.addAll(getLinesFromParagraph(paragraph, textWidth, font)));
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
		if (!builder.lines.isEmpty())
		{
			pages.add(builder.build());
		}
		return pages;
	}
	
	public static List<Drawable> getDrawables(RawElement raw, DrawableRenderer renderer, int textWidth)
	{
		return DrawableRegistry.getFactory(raw.getTypeID()).apply(raw).getAsDrawables(renderer, textWidth);
	}
	
	public static class PageBuilder
	{
		private List<Drawable> lines = new ArrayList<>();
		private int currentHeight = 0;
		
		private final int maxPixelHeight;
		
		public PageBuilder(int maxPixelHeight)
		{
			this.maxPixelHeight = maxPixelHeight;
		}
		
		public boolean canAddDrawable(Drawable drawable)
		{
			return this.currentHeight == 0 || ( drawable.canAddToList(this.lines) && this.currentHeight + drawable.getHeight() <= this.maxPixelHeight);
		}
		
//		public boolean canAddLines(int numberOfLinesToAdd)
//		{
//			return this.currentHeight == 0 || this.currentHeight + numberOfLinesToAdd <= this.maxLinesOnPage;
//		}
//		
//		public boolean canAddLines(List<Drawable> lines)
//		{
//			return this.canAddLines(lines.size());
//		}
		
		public void addDrawable(Drawable drawable)
		{
			this.lines.add(drawable);
			this.currentHeight += drawable.getHeight();
		}
		
//		public void addLines(List<Drawable> lines)
//		{
//			this.lines.addAll(lines);
//			this.currentHeight += lines.size();
//		}
		
		public BakedPage build()
		{
			return new BakedPage(this.lines);
		}
	}

}
