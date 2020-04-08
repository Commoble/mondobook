package com.github.commoble.mondobook.client.content;

import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.Selector;

/** Methods to use with SimpleSelector for matching styles to elements **/
public class Selectors
{
	public static boolean neverMatch(Element element, Selector selector)
	{
		return false;
	}

	public static boolean alwaysMatch(Element element, Selector selector)
	{
		return true;
	}
	
	/** Matches the element if the selector's match string is the element's type ID **/
	public static boolean matchElement(Element element, Selector selector)
	{
		return element.getTypeID().toString().equals(selector.getMatchString());
	}
	
	/** Matches the element if the selector's match string is the element's ID string **/
	public static boolean matchID(Element element, Selector selector)
	{
		return element.getID().equals(selector.getMatchString());
	}
	
	/** Matches the element if any of the element's class strings are the selector's match string **/
	public static boolean matchClass(Element element, Selector selector)
	{
		return element.getStyleClasses().stream().anyMatch(selector.getMatchString()::equals);
	}

	/** Matches the element if the selector has at least one child and at least one of the selector's children matches the element **/
	public static boolean matchAnyChildren(Element element, Selector selector)
	{
		return selector.getChildren().stream().anyMatch(child -> child.test(element));
	}
	
	/** Matches the element if the selector has no children that do not match the element **/
	public static boolean matchAllChildren(Element element, Selector selector)
	{
		return selector.getChildren().stream().allMatch(child -> child.test(element));
	}
}
