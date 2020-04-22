package com.github.commoble.mondobook.client.api.internal;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;

public class AlignedDrawable implements Drawable
{
	private final Alignment alignment;
	private final Drawable drawable;
	
	public static AlignedDrawable of(Alignment alignment, Drawable drawable)
	{
		return new AlignedDrawable(alignment, drawable);
	}
	
	private AlignedDrawable(Alignment alignment, Drawable drawable)
	{
		this.drawable = drawable;
		this.alignment = alignment;
	}
	
	@Override
	public void render(DrawableRenderer renderer, int startX, int startY, int maxWidth)
	{
		int alignedStartX = this.alignment.getLeft(startX, startX + maxWidth, this.drawable.getWidth());
		int offsetX = alignedStartX - startX;
		this.drawable.render(renderer, alignedStartX, startY, maxWidth - offsetX);
	}

	@Override
	public int getHeight()
	{
		return this.drawable.getHeight();
	}

	@Override
	public int getWidth()
	{
		return this.drawable.getWidth();
	}

}
