package com.github.commoble.mondobook.client.api.internal;

import java.util.List;
import java.util.Optional;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.google.common.collect.ImmutableList;

import net.minecraft.client.gui.AbstractGui;

public class AlignedPaddedDrawable implements Drawable
{
	private final Alignment alignment;
	private final int bottomPadding;
	private final int topPadding;
	private final int leftPadding;
	private final int rightPadding;
	private final int bottomBorder;
	private final int topBorder;
	private final int leftBorder;
	private final int rightBorder;
	private final int borderColor;
	private final List<DrawableWithOffset> children;
	private final int totalWidth;
	private final int totalHeight;
	
	public AlignedPaddedDrawable(Alignment alignment, SideSizes margins, Borders borders, Drawable drawable, int containerWidth)
	{
		this.alignment = alignment;
		this.bottomPadding = margins.bottom;
		this.topPadding = margins.top;
		this.leftPadding = margins.left;
		this.rightPadding = margins.right;
		
		SideSizes borderSizes = borders.getSizes();
		this.bottomBorder = borderSizes.bottom;
		this.topBorder = borderSizes.top;
		this.leftBorder = borderSizes.left;
		this.rightBorder = borderSizes.right;
		this.borderColor = borders.getColor();
		
		int totalLeftPadding = this.leftPadding + this.leftBorder;
		int totalRightPadding = this.rightPadding + this.rightBorder;
		int totalTopPadding = this.topPadding + this.topBorder;
		int totalBottomPadding = this.bottomPadding + this.bottomBorder;
		int totalWidthPadding = totalLeftPadding + totalRightPadding;
		int totalHeightPadding = totalTopPadding + totalBottomPadding;
		int spaceForChild = containerWidth - totalWidthPadding;
		int childWidth = drawable.getWidth();
		int childHeight = drawable.getHeight();
		
		int alignedOffsetX = this.alignment.getLeft(0 + totalLeftPadding, 0 + containerWidth - totalRightPadding, childWidth);
		
		
		this.children = ImmutableList.of(new DrawableWithOffset(alignedOffsetX, totalTopPadding, childWidth, drawable));
		
		this.totalWidth = childWidth + totalWidthPadding;
		this.totalHeight = childHeight + totalHeightPadding;
	}

	@Override
	public Optional<BookStyle> getStyle()
	{
		return Optional.empty();
	}

	@Override
	public List<DrawableWithOffset> getChildren()
	{
		return this.children;
	}

	@Override
	public void renderSelf(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
		int height = this.getHeight();
		int color = this.borderColor;
		// render borders
		if (this.topBorder > 0)
		{
			AbstractGui.fill(startX, startY, startX + maxWidth, startY + this.topBorder, color);
		}
		if (this.leftBorder > 0)
		{
			AbstractGui.fill(startX, startY + this.topBorder, startX + this.leftBorder, startY + height - this.bottomBorder, color);
		}
		if (this.rightBorder > 0)
		{
			AbstractGui.fill(startX + maxWidth - this.rightBorder, startY + this.topBorder, startX + maxWidth, startY + height - this.bottomBorder, color);
		}
		if (this.bottomBorder > 0)
		{
			AbstractGui.fill(startX, startY + height - this.bottomBorder, startX + maxWidth, startY + height, color);
		}
		
		// render child drawable
//		this.drawable.render(renderer, startX + totalLeftPadding, startY + totalTopPadding, maxWidth - totalWidthPadding, mouseX, mouseY);
	}

	@Override
	public int getHeight()
	{
		return this.totalHeight;
	}

	@Override
	public int getWidth()
	{
		return this.totalWidth;
	}

}
