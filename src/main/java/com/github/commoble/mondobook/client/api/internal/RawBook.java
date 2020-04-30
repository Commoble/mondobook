package com.github.commoble.mondobook.client.api.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.commoble.mondobook.client.api.AssetFactories;
import com.github.commoble.mondobook.client.api.AssetManagers;
import com.github.commoble.mondobook.client.api.Element;

import net.minecraft.util.ResourceLocation;

/**
 * Instances of this class are intended to be deserialized from a mondobook json from a GSON deserializer in BookDataManager
 */
public class RawBook
{
	private String format;
	private RawStyle[] styles;
	private RawElement[] body;
	
	private transient RawFormat rawFormat;
	
	public static final ResourceLocation DEFAULT_FORMAT = new ResourceLocation("mondobook:default");
	public RawFormat getFormat()
	{
		if (this.rawFormat == null)
		{
			if (this.format == null)
			{
				this.rawFormat = AssetManagers.BOOK_FORMATS.getData(DEFAULT_FORMAT);
			}
			else
			{
				RawFormat format = AssetManagers.BOOK_FORMATS.getData(new ResourceLocation(this.format));
				if (format == null)
				{
					this.rawFormat = AssetManagers.BOOK_FORMATS.getData(DEFAULT_FORMAT);
				}
				else
				{
					this.rawFormat = format;
				}
			}
		}
		
		return this.rawFormat;
	}
	
	public List<RawElement> getElements()
	{
		return this.body != null? Arrays.asList(this.body) : new ArrayList<>();
	}
	
	public List<RawStyle> getStyles()
	{
		return this.styles != null ? Arrays.asList(this.styles) : new ArrayList<>();
	}

	/** Take the elements in a book and the styles in a book and return a list of elements with the appropriate styles applied to each **/
	public List<Element> getBakedElements()
	{
		List<RawStyle> styles = this.getStyles();
		return this.getElements().stream()
			.map(getStyler(styles))
			.collect(Collectors.toList());
	}
	
	public static Function<RawElement, Element> getStyler(List<RawStyle> styles)
	{
		return raw -> AssetFactories.ELEMENTS.apply(new ElementPrimer(raw, styles));
	}
	
	/** Given all the styles in the book, filter them to this element, sort and merge, and wrap them up with the element **/
	public static BookStyle styleElement(List<RawStyle> styles, Element element)
	{
		RawStyle.StyleBuilder styleBuilder = new RawStyle.StyleBuilder();
		
		styles.stream()
			.filter(style -> style.getSelector().test(element))
			.sorted(element.getStyleComparator())
			.forEachOrdered(styleBuilder::add);
		
		return styleBuilder.build();
	}
}
