package com.github.commoble.mondobook.client.assets;

import java.util.Arrays;
import java.util.List;

/**
 * Instances of this class are intended to be deserialized from a mondobook json from a GSON deserializer in BookDataManager
 */
public class RawBook
{
	private Head head;
	private RawElement[] body;
	
	public List<RawElement> getElements()
	{
		return Arrays.asList(this.body);
	}
}
