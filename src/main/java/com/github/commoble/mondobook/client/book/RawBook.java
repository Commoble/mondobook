package com.github.commoble.mondobook.client.book;

import java.util.ArrayList;
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
		return this.body != null? Arrays.asList(this.body) : new ArrayList<>();
	}
	
	public List<RawStyle> getStyles()
	{
		return this.styles != null ? Arrays.asList(this.styles) : new ArrayList<>();
	}
}
