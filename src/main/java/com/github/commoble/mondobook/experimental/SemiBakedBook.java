package com.github.commoble.mondobook.experimental;

import java.util.List;

import com.github.commoble.mondobook.client.api.AssetFactories;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.book.BookStyle;
import com.github.commoble.mondobook.client.book.RawBook;
import com.github.commoble.mondobook.client.book.RawStyle;
import com.github.commoble.mondobook.util.ListUtil;

public class SemiBakedBook
{
	private final List<BookStyle> styles;
	private final List<Element> elements;
	
	public SemiBakedBook(RawBook raw)
	{
		this.elements = ListUtil.map(raw.getElements(), AssetFactories.ELEMENTS);
		this.styles = ListUtil.map(raw.getStyles(), RawStyle::toBookStyle);
	}
}
