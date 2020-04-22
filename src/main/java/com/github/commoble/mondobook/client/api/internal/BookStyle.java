package com.github.commoble.mondobook.client.api.internal;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.commoble.mondobook.client.api.Drawable;
import com.google.common.collect.ImmutableList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;

public class BookStyle
{
	private final @Nonnull ResourceLocation fontID;
	private final @Nullable Style textStyle;
	private final int textColor;
	private final SideSizes margins;
	private final Alignment alignment;
	private final Borders borders;
	
	public BookStyle(ResourceLocation fontID, Style textStyle, int textColor, SideSizes margins, Alignment alignment, Borders borders)
	{
		this.fontID = fontID;
		this.textStyle = textStyle;
		this.textColor = textColor;
		this.margins = margins;
		this.alignment = alignment;
		this.borders = borders;
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
	
	public SideSizes getMargins()
	{
		return this.margins;
	}
	
	public Alignment getAlignment()
	{
		return this.alignment;
	}
	
	public Borders getBorders()
	{
		return this.borders;
	}
	
	public List<Drawable> getSingleStyledDrawable(Drawable drawable)
	{
		return ImmutableList.of(
			PaddedDrawable.of(
				this.getMargins(), this.getBorders(), AlignedDrawable.of(this.getAlignment(), drawable)));
	}
}
