package com.github.commoble.mondobook.experimental;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.commoble.mondobook.client.api.AssetFactories;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.book.RawBook;
import com.github.commoble.mondobook.client.book.RawElement;
import com.github.commoble.mondobook.client.book.RawStyle;

public class ElementStyler
{
	public static List<StyledElement> styleElements(RawBook book)
	{
		List<RawStyle> styles = book.getStyles();
		return book.getElements().stream()
			.map(getStyler(styles))
			.collect(Collectors.toList());
	}
	
	public static Function<RawElement, StyledElement> getStyler(List<RawStyle> styles)
	{
		return AssetFactories.ELEMENTS.andThen(element -> styleElement(styles, element));
	}
	
	public static StyledElement styleElement(List<RawStyle> styles, Element element)
	{
		List<RawStyle> sortedStyles = styles.stream()
			.sorted(element.getStyleComparator())
			.collect(Collectors.toList());
		
		for (RawStyle style : sortedStyles)
		{
			
		}
	}
}
