package com.github.commoble.mondobook.data.raw;

// to be serialized via GSON
public class Element
{
	private boolean bind=false;	// if true, this and any child elements will be placed on the same page, if possible
	private Element[] children;
	private String text;
	
	public String getText()
	{
		return this.text;
	}
}
