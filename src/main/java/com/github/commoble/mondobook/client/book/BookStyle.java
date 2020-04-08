package com.github.commoble.mondobook.client.book;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;

public class BookStyle
{
	private final ResourceLocation fontID;
	private final Style textStyle;
	
	public BookStyle(ResourceLocation fontID, Style textStyle)
	{
		this.fontID = fontID;
		this.textStyle = textStyle;
	}
}
