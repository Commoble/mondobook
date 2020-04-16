package com.github.commoble.mondobook.client;

import java.util.ArrayList;
import java.util.List;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.internal.PaddedDrawable;

public class PageBuilder
	{
		private int currentHeight = 0;
		
		private final int maxPixelHeight;
		private final int maxPixelWidth;
		
		private final List<Drawable> children = new ArrayList<>();
		
		public PageBuilder(int maxPixelHeight, int maxPixelWidth)
		{
			this.maxPixelHeight = maxPixelHeight;
			this.maxPixelWidth = maxPixelWidth;
		}
		
		public boolean canAddDrawable(Drawable drawable)
		{
			return this.currentHeight == 0 || ( drawable.canAddToList(this.children) && this.currentHeight + drawable.getHeight() <= this.maxPixelHeight);
		}
		
		public int getDrawableCount()
		{
			return this.children.size();
		}
		
		public void addDrawable(Drawable drawable)
		{
			this.children.add(PaddedDrawable.of(0, this.currentHeight, 0, 0, drawable));
			this.currentHeight += drawable.getHeight();
		}
		
		public BakedPage build()
		{
			return new BakedPage(this.maxPixelHeight, this.maxPixelWidth, this.children);
		}
	}