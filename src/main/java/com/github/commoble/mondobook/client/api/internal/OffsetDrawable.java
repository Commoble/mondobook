package com.github.commoble.mondobook.client.api.internal;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;

/**
 * Drawable that renders a sub-drawable with a given x- and y- offset.
 * The y-offset will be included as part of the total height of this drawable.
 * When drawn, it will respect the start-and-end constraints given by the calling drawer,
 * offsetting the child drawable by the x-offset, but not drawing it further right than the constraint.
 */
public class OffsetDrawable implements Drawable
{
	private int x;
	private int y;
	private Drawable drawable;
	
	public OffsetDrawable(int x, int y, Drawable drawable)
	{
		this.x = x;
		this.y = y;
		this.drawable = drawable;
	}

	@Override
	public void render(DrawableRenderer renderer, int startX, int startY, int maxWidth)
	{
		this.drawable.render(renderer, startX + this.x, startY + this.y, maxWidth - this.x);
	}

	@Override
	public int getHeight()
	{
		return this.drawable.getHeight() + this.y;
	}

	@Override
	public int getWidth()
	{
		return this.drawable.getWidth() + this.x;
	}
	
	
}
