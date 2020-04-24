package com.github.commoble.mondobook.client.api.internal;

import java.util.List;
import java.util.function.BiFunction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.util.ListUtil;
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
	private final int backgroundColor;
	private final int foregroundHoverColor;
	private final int backgroundHoverColor;
	
	public BookStyle(ResourceLocation fontID, Style textStyle, int textColor,
		SideSizes margins, Alignment alignment, Borders borders,
		int backgroundColor, int foregroundHoverColor, int backgroundHoverColor)
	{
		this.fontID = fontID;
		this.textStyle = textStyle;
		this.textColor = textColor;
		this.margins = margins;
		this.alignment = alignment;
		this.borders = borders;
		this.backgroundColor = backgroundColor;
		this.foregroundHoverColor = foregroundHoverColor;
		this.backgroundHoverColor = backgroundHoverColor;
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
	
	public int getBackgroundColor()
	{
		return this.backgroundColor;
	}
	
	public int getForegroundHoverColor()
	{
		return this.foregroundHoverColor;
	}
	
	public int getBackgroundHoverColor()
	{
		return this.backgroundHoverColor;
	}
	
	public int getInteriorWidth(int totalWidth)
	{
		return totalWidth - this.margins.left - this.margins.right - this.borders.getSizes().left - this.borders.getSizes().right;
	}
	
	public List<Drawable> getSingleStyledDrawable(Drawable drawable)
	{
		return ImmutableList.of(
			PaddedDrawable.of(
				this.getMargins(), this.getBorders(), AlignedDrawable.of(this.getAlignment(), drawable)));
	}
	
	/**
	 * Given a list of things that can each be turned into a row of drawables, applies this style to each of them
	 * and returns a list of styled drawables.
	 * 
	 * The following style properties are applied: Alignment, borders, margins.
	 * Other styling is left up to the individual drawables.
	 */
	public <T> List<Drawable> styleMultipleDrawables(List<T> sources, BiFunction<T, BookStyle, Drawable> factory)
	{
		int sourceSize = sources.size();
		if (sourceSize < 1)
		{
			return ImmutableList.of();
		}
		
		SideSizes padding = this.getMargins();
		Alignment alignment = this.getAlignment();
		
		if (sourceSize == 1)
		{
			return ImmutableList.of(PaddedDrawable.of(padding, this.borders, AlignedDrawable.of(alignment, factory.apply(sources.get(0), this))));
		}
		
		// the first line has the top border and padding, but not the bottom
		// the middle lines have neither the top or bottom border/padding
		// the last line of text does not have the top padding, but has the bottom
		// all lines have the side padding
		SideSizes firstPadding = padding.without(BoxSide.BOTTOM);
		Borders firstBorders = this.borders.without(BoxSide.BOTTOM);
		SideSizes middlePadding = firstPadding.without(BoxSide.TOP);
		Borders middleBorders = firstBorders.without(BoxSide.TOP);
		SideSizes lastPadding = padding.without(BoxSide.TOP);
		Borders lastBorders = this.borders.without(BoxSide.TOP);
		
		return ListUtil.mapFirstMiddleLast(sources,
			x -> PaddedDrawable.of(firstPadding, firstBorders, AlignedDrawable.of(alignment, factory.apply(x, this))),
			x -> PaddedDrawable.of(middlePadding, middleBorders, AlignedDrawable.of(alignment, factory.apply(x, this))),
			x -> PaddedDrawable.of(lastPadding, lastBorders, AlignedDrawable.of(alignment, factory.apply(x, this))));
	
	}
}
