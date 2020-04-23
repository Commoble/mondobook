package com.github.commoble.mondobook.client.api.internal;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;

import net.minecraft.client.gui.AbstractGui;

public class PaddedDrawable implements Drawable
{
	private int bottomPadding;
	private int topPadding;
	private int leftPadding;
	private int rightPadding;
	private int bottomBorder;
	private int topBorder;
	private int leftBorder;
	private int rightBorder;
	private int borderColor;
	private final Drawable drawable;
	
	public static PaddedDrawable withoutPadding(Drawable drawable)
	{
		return PaddedDrawable.of(SideSizes.NONE, Borders.NONE, drawable);
	}
	
	public static PaddedDrawable of(SideSizes margins, Borders borders, Drawable drawable)
	{
		SideSizes borderSizes = borders.getSizes();
		return new PaddedDrawable(margins.bottom, margins.top, margins.left, margins.right, 
			borderSizes.bottom, borderSizes.top, borderSizes.left, borderSizes.right, borders.getColor(), drawable);
	}
	
	private PaddedDrawable(int bottomPadding, int topPadding, int leftPadding, int rightPadding,
		int bottomBorder, int topBorder, int leftBorder, int rightBorder, int borderColor, Drawable drawable)
	{
		this.bottomPadding = bottomPadding;
		this.topPadding = topPadding;
		this.leftPadding = leftPadding;
		this.rightPadding = rightPadding;
		this.bottomBorder = bottomBorder;
		this.topBorder = topBorder;
		this.leftBorder = leftBorder;
		this.rightBorder = rightBorder;
		this.borderColor = borderColor;
		this.drawable = drawable;
	}

	@Override
	public void render(DrawableRenderer renderer, int startX, int startY, int maxWidth)
	{
		int totalLeftPadding = this.leftPadding + this.leftBorder;
		int totalRightPadding = this.rightPadding + this.rightBorder;
		int totalTopPadding = this.topPadding + this.topBorder;
		int totalWidthPadding = totalLeftPadding + totalRightPadding;
		int height = this.getHeight();
		int color = this.borderColor | 0xFF000000; // TODO add transparency support
		// render borders
		if (this.topBorder > 0)
		{
			AbstractGui.fill(startX, startY, startX + maxWidth, startY + this.topBorder, color);
		}
		if (this.leftBorder > 0)
		{
			AbstractGui.fill(startX, startY, startX + this.leftBorder, startY + height, color);
		}
		if (this.rightBorder > 0)
		{
			AbstractGui.fill(startX + maxWidth - this.rightBorder, startY, startX + maxWidth, startY + height, color);
		}
		if (this.bottomBorder > 0)
		{
			AbstractGui.fill(startX, startY + height - this.bottomBorder, startX + maxWidth, startY + height, color);
		}
		
		// render child drawable
		this.drawable.render(renderer, startX + totalLeftPadding, startY + totalTopPadding, maxWidth - totalWidthPadding);
	}

	@Override
	public int getHeight()
	{
		return this.drawable.getHeight() + this.topPadding + this.topBorder + this.bottomPadding + this.bottomBorder;
	}

	@Override
	public int getWidth()
	{
		return this.drawable.getWidth() + this.leftPadding + this.leftBorder + this.rightPadding + this.rightBorder;
	}

	@Override
	public void renderTooltip(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
		int totalLeftPadding = this.leftPadding + this.leftBorder;
		int totalTopPadding = this.topPadding + this.topBorder;
		this.drawable.renderTooltip(renderer, startX + totalLeftPadding, startY + totalTopPadding, maxWidth, mouseX, mouseY);
	}

}
