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
	
	/**
	 * Given a list of things that can each be turned into a row of drawables, applies this style to each of them
	 * and returns a list of styled drawables.
	 * 
	 * The following style properties are applied: Alignment, borders, margins.
	 * Other styling is left up to the individual drawables.
	 */
	public <T> List<Drawable> styleMultipleDrawables(List<T> sources, BiFunction<T, BookStyle, Drawable> factory)
	{
		SideSizes padding = this.getMargins();
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
		Alignment alignment = this.getAlignment();
		
		return ListUtil.mapFirstMiddleLast(sources,
			x -> PaddedDrawable.of(firstPadding, firstBorders, AlignedDrawable.of(alignment, factory.apply(x, this))),
			x -> PaddedDrawable.of(middlePadding, middleBorders, AlignedDrawable.of(alignment, factory.apply(x, this))),
			x -> PaddedDrawable.of(lastPadding, lastBorders, AlignedDrawable.of(alignment, factory.apply(x, this))));
	
	}
}
