package com.github.commoble.mondobook.client.book;

import java.util.Arrays;
import java.util.List;

/**
 * Instances of this class are intended to be deserialized from a mondobook json from a GSON deserializer in BookDataManager
 */
public class RawBook
{
	private RawStyle[] styles;
	private RawElement[] body;
	
	public List<RawElement> getElements()
	{
		return Arrays.asList(this.body);
	}
	
	public List<RawStyle> getStyles()
	{
		return Arrays.asList(this.styles);
	}
}
