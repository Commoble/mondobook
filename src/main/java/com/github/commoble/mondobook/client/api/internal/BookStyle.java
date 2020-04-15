package com.github.commoble.mondobook.client.book;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;

public class BookStyle
{
	private final @Nonnull ResourceLocation fontID;
	private final @Nullable Style textStyle;
	private final int color;
	
	public BookStyle(ResourceLocation fontID, Style textStyle, int color)
	{
		this.fontID = fontID;
		this.textStyle = textStyle;
		this.color = color;
	}
	
	public ResourceLocation getFont()
	{
		return this.fontID;
	}
	
	public Style getTextStyle()
	{
		return this.textStyle;
	}
	
	public int getTextColor()
	{
		return this.color;
	}
}
