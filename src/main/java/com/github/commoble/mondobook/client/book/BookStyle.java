package com.github.commoble.mondobook.client.book;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;

public class BookStyle
{
	private final @Nonnull ResourceLocation fontID;
	private final @Nullable Style textStyle;
	
	public BookStyle(ResourceLocation fontID, Style textStyle)
	{
		this.fontID = fontID;
		this.textStyle = textStyle;
	}
	
	public ResourceLocation getFont()
	{
		return this.fontID;
	}
	
	public Style getTextStyle()
	{
		return this.textStyle;
	}
	
}
