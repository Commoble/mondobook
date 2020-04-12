package com.github.commoble.mondobook.client.book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.commoble.mondobook.client.api.AssetFactories;
import com.github.commoble.mondobook.client.api.Element;

/**
 * Instances of this class are intended to be deserialized from a mondobook json from a GSON deserializer in BookDataManager
 */
public class RawBook
{
	private RawStyle[] styles;
	private RawElement[] body;
	
	public List<RawElement> getElements()
	{
		return this.body != null? Arrays.asList(this.body) : new ArrayList<>();
	}
	
	public List<RawStyle> getStyles()
	{
		return this.styles != null ? Arrays.asList(this.styles) : new ArrayList<>();
	}

	/** Take the elements in a book and the styles in a book and return a list of elements with the appropriate styles applied to each **/
	public List<StyledElement> getStyledElements()
	{
		List<RawStyle> styles = this.getStyles();
		return this.getElements().stream()
			.map(getStyler(styles))
			.collect(Collectors.toList());
	}
	
	public static Function<RawElement, StyledElement> getStyler(List<RawStyle> styles)
	{
		return AssetFactories.ELEMENTS.andThen(element -> styleElement(styles, element));
	}
	
	/** Given all the styles in the book, filter them to this element, sort and merge, and wrap them up with the element **/
	public static StyledElement styleElement(List<RawStyle> styles, Element element)
	{
		RawStyle.StyleBuilder styleBuilder = new RawStyle.StyleBuilder();
		
		styles.stream()
			.filter(style -> style.getSelector().test(element))
			.sorted(element.getStyleComparator())
			.forEachOrdered(styleBuilder::add);
		
		return new StyledElement(styleBuilder.build(), element);
	}
}
