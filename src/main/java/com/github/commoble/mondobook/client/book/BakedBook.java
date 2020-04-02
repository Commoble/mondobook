package com.github.commoble.mondobook.client.book;

import java.util.List;

import com.github.commoble.mondobook.client.book.raw.RawBook;

import net.minecraft.client.gui.FontRenderer;

public class BakedBook
{
	private final List<BakedPage> pages;
	
	public BakedBook(RawBook rawBook, int maxLinesOnPage, int maxLineWidth, FontRenderer font)
	{
		this.pages = BakedPage.fromParagraphs(rawBook.getElements(), maxLinesOnPage, maxLineWidth, font);
	}
	
	public List<BakedPage> getPages()
	{
		return this.pages;
	}
}
