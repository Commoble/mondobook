package com.github.commoble.mondobook.client.book.raw;

import java.util.Arrays;
import java.util.List;

/**
 * Instances of this class are intended to be deserialized from a mondobook json from a GSON deserializer in BookDataManager
 */
public class RawBook
{
	private Head head;
	private Paragraph[] body;
	
	public List<Paragraph> getElements()
	{
		return Arrays.asList(this.body);
	}
}
