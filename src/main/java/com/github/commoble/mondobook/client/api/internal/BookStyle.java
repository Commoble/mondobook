package com.github.commoble.mondobook.client.api.internal;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;

public class BookStyle
{
	private final @Nonnull ResourceLocation fontID;
	private final @Nullable Style textStyle;
	private final int textColor;
	private final Margins margins;
	private final Alignment alignment;
	
	public BookStyle(ResourceLocation fontID, Style textStyle, int textColor, Margins margins, Alignment alignment)
	{
		this.fontID = fontID;
		this.textStyle = textStyle;
		this.textColor = textColor;
		this.margins = margins;
		this.alignment = alignment;
	}
	
	public ResourceLocation getFont()
	{
		return this.fontID;
	}
	
	public FontRenderer getFontRenderer()
	{
		return Minecraft.getInstance().getFontResourceManager().getFontRenderer(this.fontID);
	}
	
	public Style getTextStyle()
	{
		return this.textStyle;
	}
	
	public int getTextColor()
	{
		return this.textColor;
	}
	
	public Margins getMargins()
	{
		return this.margins;
	}
	
	public Alignment getAlignment()
	{
		return this.alignment;
	}
}
