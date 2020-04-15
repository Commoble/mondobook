package com.github.commoble.mondobook.client;

import java.util.ArrayList;
import java.util.List;

import com.github.commoble.mondobook.client.api.Drawable;

public class PageBuilder
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
		
		public List<Drawable> getDrawables()
		{
			return this.lines;
		}
		
		public void addDrawable(Drawable drawable)
		{
			this.lines.add(drawable);
			this.currentHeight += drawable.getHeight();
		}
		
		public BakedPage build()
		{
			return new BakedPage(this.lines);
		}
	}