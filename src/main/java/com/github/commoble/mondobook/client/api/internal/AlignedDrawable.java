package com.github.commoble.mondobook.client.api.internal;

import java.util.List;
import java.util.Optional;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.google.common.collect.ImmutableList;

public class AlignedDrawable implements Drawable
{
	private final Alignment alignment;
	private final List<DrawableWithOffset> children;
	private final int width;
	private final int height;
	
	public static AlignedDrawable of(Alignment alignment, Drawable drawable, int containerWidth)
	{
		return new AlignedDrawable(alignment, drawable, containerWidth);
	}
	
	private AlignedDrawable(Alignment alignment, Drawable drawable, int containerWidth)
	{
		this.alignment = alignment;
		int childWidth = drawable.getWidth();
		int alignedStartX = this.alignment.getLeft(0, 0 + containerWidth, childWidth);
		this.children = ImmutableList.of(new DrawableWithOffset(alignedStartX, 0, childWidth, drawable));
		this.height = drawable.getHeight();
		this.width = drawable.getWidth() + alignedStartX;
	}
	
	@Override
	public void renderSelf(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
//		int alignedStartX = this.alignment.getLeft(startX, startX + maxWidth, this.drawable.getWidth());
//		this.drawable.render(renderer, alignedStartX, startY, this.drawable.getWidth(), mouseX, mouseY);
	}

	@Override
	public int getHeight()
	{
		return this.height;
	}

	@Override
	public int getWidth()
	{
		return this.width;
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

}
