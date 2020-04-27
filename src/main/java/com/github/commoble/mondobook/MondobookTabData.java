package com.github.commoble.mondobook;

import java.util.Map;
import java.util.Set;

/**
 * To be deserialized via a basic GSON parser.
 **/
public class MondobookTabData
{
	public Map<String, Set<String>> tabMap;
	
	// empty constructor for GSON
	public MondobookTabData() {}
	
	public MondobookTabData merge(MondobookTabData other)
	{
		other.tabMap.
	}
}
