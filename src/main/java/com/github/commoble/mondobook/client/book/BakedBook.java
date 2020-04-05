package com.github.commoble.mondobook.client.book;

import java.util.List;

import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.assets.RawBook;

public class BakedBook
{
	private final List<BakedPage> pages;
	
	public BakedBook(RawBook rawBook, int pageHeightInPixels, int maxLineWidth, DrawableRenderer renderer)
	{
		this.pages = BakedPage.fromRaws(rawBook.getElements(), pageHeightInPixels, maxLineWidth, renderer);
	}
	
	public List<BakedPage> getPages()
	{
		return this.pages;
	}
}
